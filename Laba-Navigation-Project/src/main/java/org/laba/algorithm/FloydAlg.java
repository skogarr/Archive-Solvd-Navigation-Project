package org.laba.algorithm;

import java.util.*;
import java.util.stream.Collectors;
import org.laba.model.BusStop;
import org.laba.model.DistanceBetween;
import org.laba.model.MetroStop;
import org.laba.model.TramStop;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;
import org.laba.model.TripType;
import org.laba.service.my_batis.BusStopService;
import org.laba.service.my_batis.MetroStopService;
import org.laba.service.my_batis.TramStopService;

public class FloydAlg {

  private static final BusStopService busStopService = new BusStopService();
  private static final TramStopService tramStopService = new TramStopService();
  private static final MetroStopService metroStopService = new MetroStopService();
  private final int blockedPath;
  private List<TravelWeight> allTravelWeights;
  private List<DistanceBetween> allDistances;
  private List<TransitPoint> allTransitPoints;
  private double[][] distance;
  private double[][] next;
  private double[][] graph;

  public FloydAlg(double[][] graph, int inf, List<TravelWeight> allTravelWeights,
      List<DistanceBetween> allDistances,
      List<TransitPoint> allTransitPoints) {
    this.blockedPath = inf;
    this.graph = graph;
    this.allTravelWeights = allTravelWeights;
    this.allDistances = allDistances;
    this.allTransitPoints = allTransitPoints;
    compute();
  }

  public FloydAlg(List<TravelWeight> allTravelWeights, List<DistanceBetween> allDistances,
      List<TransitPoint> allTransitPoints) {
    this.graph = null;
    this.blockedPath = Integer.MAX_VALUE;
    this.allTravelWeights = allTravelWeights;
    this.allDistances = allDistances;
    this.allTransitPoints = allTransitPoints;
  }

  public void setGraph(double[][] graph) {
    this.graph = graph;
    compute();
  }

  public double[][] buildGraphCar() {
    int size = allTransitPoints.size();
    double[][] graph = new double[size + 1][size + 1];
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[i].length; j++) {
        if (i == j) {
          graph[i][j] = 0.0d;
          continue;
        }
        double weightab = getCarWeight(i, j);
        if (weightab == -1d) {
          graph[i][j] = Integer.MAX_VALUE;
          continue;
        }
        double weightba = getCarWeight(j, i);
        if (weightba == -1d) {
          graph[i][j] = Integer.MAX_VALUE;
          continue;
        }
        double distance = getDistance(i, j);
        if (distance == -1d) {
          graph[i][j] = Integer.MAX_VALUE;
          continue;
        }
        graph[i][j] = weightab * distance;
        graph[j][i] = weightba * distance;
      }
    }
    return graph;
  }

  public double[][] buildGraphPublic() {
    int size = allTransitPoints.size();
    double[][] graph = new double[size + 1][size + 1];
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[i].length; j++) {
        if (i == j) {
          graph[i][j] = 0.0d;
          continue;
        }
        double distance = getDistance(i, j);
        double busWeightab = getBusWeight(i, j);
        if (busWeightab == -1d || busWeightab == 0.0) {
          busWeightab = Double.MAX_VALUE;
        }
        double tramWeightab = getTramWeight(i, j);
        if (tramWeightab == -1d || tramWeightab == 0.0) {
          tramWeightab = Double.MAX_VALUE;
        }
        double metroWeightab = getMetroWeight(i, j);
        if (metroWeightab == -1d || metroWeightab == 0.0) {
          metroWeightab = Double.MAX_VALUE;
        }
        double busWeightba = getBusWeight(j, i);
        if (busWeightba == -1d || busWeightba == 0.0) {
          busWeightba = Double.MAX_VALUE;
        }
        double tramWeightba = getTramWeight(j, i);
        if (tramWeightba == -1d || tramWeightba == 0.0) {
          tramWeightba = Double.MAX_VALUE;
        }
        double metroWeightba = getMetroWeight(j, i);
        if (metroWeightba == -1d || metroWeightba == 0.0) {
          metroWeightba = Double.MAX_VALUE;
        }
        if (Math.min(Math.min(busWeightab, tramWeightab), metroWeightab) == Double.MAX_VALUE) {
          graph[i][j] = Integer.MAX_VALUE;
          continue;
        }
        if (Math.min(Math.min(busWeightba, tramWeightba), metroWeightba) == Double.MAX_VALUE) {
          graph[j][i] = Integer.MAX_VALUE;
          continue;
        }
        double valueab = Math.min(busWeightab * distance,
            Math.min(tramWeightab * distance, metroWeightab * distance));

        double valueba = Math.min(busWeightba * distance,
            Math.min(tramWeightba * distance, metroWeightba * distance));
        graph[i][j] = valueab;
        graph[j][i] = valueba;
      }
    }
    return graph;
  }

  public ArrayList<Integer> constructPath(int from, int to) {
    if (this.graph == null) {
      throw new NullPointerException("Variable \"graph\" wasn't set");
    }
    if (next[from][to] == -1) {
      return null;
    }
    ArrayList<Integer> path = new ArrayList<>();
    path.add(from);
    while (from != to) {
      from = (int) next[from][to];
      path.add(from);
    }
    return path;
  }

  public double getFinalTripTime(ArrayList<Integer> path, TripType tripType) {
    double sum = 0d;
    double value = 0d;
    for (int i = 0; i < path.size() - 1; i++) {
      Integer transitPointA = path.get(i);
      Integer transitPointB = path.get(i + 1);
      double distance = getDistance(transitPointA, transitPointB);
      switch (tripType) {
        case PRIVATE -> {
          value = getCarWeight(transitPointA, transitPointB);
        }
        case PUBLIC -> {
          double busWeight = getBusWeight(transitPointA, transitPointB);
          if (busWeight == -1d || busWeight == 0.0) {
            busWeight = Double.MAX_VALUE;
          }
          double tramWeight = getTramWeight(transitPointA, transitPointB);
          if (tramWeight == -1d || tramWeight == 0.0) {
            tramWeight = Double.MAX_VALUE;
          }
          double metroWeight = getMetroWeight(transitPointA, transitPointB);
          if (metroWeight == -1d || metroWeight == 0.0) {
            metroWeight = Double.MAX_VALUE;
          }
          value = Math.min(Math.min(busWeight, tramWeight), metroWeight);
        }
      }
      sum += value * distance;
    }
    return Double.parseDouble(String.format(Locale.US, "%.2f", sum));
  }

  public void prettyPrintPublic(ArrayList<Integer> path) {
    for (int i = 0; i < path.size() - 1; i++) {
      Integer transitPointA = path.get(i);
      Integer transitPointB = path.get(i + 1);
      double busWeight = getBusWeight(transitPointA, transitPointB);
      if (busWeight == 0.0d) {
        busWeight = Double.MAX_VALUE;
      }
      double tramWeight = getTramWeight(transitPointA, transitPointB);
      if (tramWeight == 0.0d) {
        tramWeight = Double.MAX_VALUE;
      }
      double metroWeight = getMetroWeight(transitPointA, transitPointB);
      if (metroWeight == 0.0d) {
        metroWeight = Double.MAX_VALUE;
      }
      if ((busWeight < tramWeight) && (busWeight < metroWeight)) {
        List<BusStop> allByTransitPointAId = busStopService.getAllByTransitPointId(
            Long.valueOf(transitPointA));
        List<BusStop> allByTransitPointBId = busStopService.getAllByTransitPointId(
            Long.valueOf(transitPointB));
        long busRoute = allByTransitPointAId.stream()
            .map(x -> x.getBusRoute().getId())
            .filter(allByTransitPointBId.stream().map(x -> x.getBusRoute().getId())
                .collect(Collectors.toSet())::contains)
            .findFirst()
            .orElse(-1L);
        String stopNo1 = allByTransitPointAId.stream()
            .filter(x -> x.getBusRoute().getId() == busRoute)
            .map(BusStop::getStopNo)
            .findFirst()
            .orElse("UNKNOWN");
        String stopNo2 = allByTransitPointBId.stream()
            .filter(x -> x.getBusRoute().getId() == busRoute)
            .map(BusStop::getStopNo)
            .findFirst()
            .orElse("UNKNOWN");
        System.out.printf("Go from transit point %s, bus stop number %s, bus route %s,"
                + "to transit point %s, bus stop number %s\n",
            transitPointA, stopNo1, busRoute, transitPointB, stopNo2);
      } else if ((tramWeight < busWeight) && (tramWeight < metroWeight)) {
        List<TramStop> allByTransitPointAId = tramStopService.getAllByTransitPointId(
            Long.valueOf(transitPointA));
        List<TramStop> allByTransitPointBId = tramStopService.getAllByTransitPointId(
            Long.valueOf(transitPointB));
        long tramRoute = allByTransitPointAId.stream()
            .map(x -> x.getTramRoute().getId())
            .filter(allByTransitPointBId.stream().map(x -> x.getTramRoute().getId())
                .collect(Collectors.toSet())::contains)
            .findFirst()
            .orElse(-1L);
        int stopNo1 = allByTransitPointAId.stream()
            .filter(x -> x.getTramRoute().getId() == tramRoute)
            .map(TramStop::getStopNo)
            .findFirst()
            .orElse(-1);
        int stopNo2 = allByTransitPointBId.stream()
            .filter(x -> x.getTramRoute().getId() == tramRoute)
            .map(TramStop::getStopNo)
            .findFirst()
            .orElse(-1);
        System.out.printf("Go from transit point %s, tram stop number %s, tram route %s,"
                + "to transit point %s, tram stop number %s\n",
            transitPointA, stopNo1, tramRoute, transitPointB, stopNo2);
      } else if ((metroWeight < busWeight) && (metroWeight < tramWeight)) {
        List<MetroStop> allByTransitPointAId = metroStopService.getAllByTransitPointId(
            Long.valueOf(transitPointA));
        List<MetroStop> allByTransitPointBId = metroStopService.getAllByTransitPointId(
            Long.valueOf(transitPointB));
        long metroRoute = allByTransitPointAId.stream()
            .map(x -> x.getMetroRoute().getId())
            .filter(allByTransitPointBId.stream().map(x -> x.getMetroRoute().getId())
                .collect(Collectors.toSet())::contains)
            .findFirst()
            .orElse(-1L);
        int stopNo1 = allByTransitPointAId.stream()
            .filter(x -> x.getMetroRoute().getId() == metroRoute)
            .map(MetroStop::getStopNo)
            .findFirst()
            .orElse(-1);
        int stopNo2 = allByTransitPointBId.stream()
            .filter(x -> x.getMetroRoute().getId() == metroRoute)
            .map(MetroStop::getStopNo)
            .findFirst()
            .orElse(-1);
        System.out.printf("Go from transit point %s, metro stop number %s, metro route %s,"
                + "to transit point %s, metro stop number %s\n",
            transitPointA, stopNo1, metroRoute, transitPointB, stopNo2);
      } else {
        System.out.print("nothing -> ");
      }
    }
    System.out.println("You have arrived at destination");
  }

  private double getDistance(int i, int j) {
    double distance = -1d;
    for (DistanceBetween distanceBetween : allDistances) {
      if (distanceBetween.getTransitPointA().getId() == i
          && distanceBetween.getTransitPointB().getId() == j) {
        distance = distanceBetween.getDistance();
      } else if (distanceBetween.getTransitPointA().getId() == j
          && distanceBetween.getTransitPointB().getId() == i) {
        distance = distanceBetween.getDistance();
      }
    }
    return distance;
  }

  private double getCarWeight(int i, int j) {
    double weight = -1d;
    for (TravelWeight travelWeight : allTravelWeights) {
      if (travelWeight.getTransitPointA().getId() == i
          && travelWeight.getTransitPointB().getId() == j) {
        weight = travelWeight.getCarWeight();
      }
    }
    return weight;
  }

  private double getBusWeight(int i, int j) {
    double weight = -1d;
    for (TravelWeight travelWeight : allTravelWeights) {
      if (travelWeight.getTransitPointA().getId() == i
          && travelWeight.getTransitPointB().getId() == j) {
        weight = travelWeight.getBusWeight();
      }
    }
    return weight;
  }

  private double getTramWeight(int i, int j) {
    double weight = -1d;
    for (TravelWeight travelWeight : allTravelWeights) {
      if (travelWeight.getTransitPointA().getId() == i
          && travelWeight.getTransitPointB().getId() == j) {
        weight = travelWeight.getTramWeight();
      }
    }
    return weight;
  }

  private double getMetroWeight(int i, int j) {
    double weight = -1d;
    for (TravelWeight travelWeight : allTravelWeights) {
      if (travelWeight.getTransitPointA().getId() == i
          && travelWeight.getTransitPointB().getId() == j) {
        weight = travelWeight.getMetroWeight();
      }
    }
    return weight;
  }

  private void initialize(double[][] graph) {
    int V = graph.length;
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        distance[i][j] = graph[i][j];
        if (graph[i][j] == blockedPath) {
          next[i][j] = -1;
        } else {
          next[i][j] = j;
        }
      }
    }
  }

  private void floyd(int V) {
    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (distance[i][k] == blockedPath || distance[k][j] == blockedPath) {
            continue;
          }
          if (distance[i][j] > distance[i][k] + distance[k][j]) {
            distance[i][j] = distance[i][k] + distance[k][j];
            next[i][j] = next[i][k];
          }
        }
      }
    }
  }

  private void compute() {
    this.distance = new double[graph.length][graph.length];
    this.next = new double[graph.length][graph.length];
    initialize(graph);
    floyd(graph.length);
  }
}