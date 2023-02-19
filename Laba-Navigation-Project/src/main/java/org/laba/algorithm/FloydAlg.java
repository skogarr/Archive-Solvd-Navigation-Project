package org.laba.algorithm;

import java.util.*;
import org.laba.model.DistanceBetween;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;

public class FloydAlg {

  private final int X;
  private double[][] distance;
  private double[][] next;
  private double[][] graph;

  public FloydAlg(double[][] graph, int inf) {
    this.X = inf;
    this.graph = graph;
    compute();
  }

  public FloydAlg() {
    this.graph = null;
    this.X = Integer.MAX_VALUE;
  }

  private void compute() {
    this.distance = new double[graph.length][graph.length];
    this.next = new double[graph.length][graph.length];
    init(graph);
    floyd(graph.length);
  }

  public double[][] getGraph() {
    return graph;
  }

  public void setGraph(double[][] graph) {
    this.graph = graph;
    compute();
  }

  private void init(double[][] graph) {
    int V = graph.length;
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        distance[i][j] = graph[i][j];
        if (graph[i][j] == X) {
          next[i][j] = -1;
        } else {
          next[i][j] = j;
        }
      }
    }
  }

  private ArrayList<Integer> constructPath(int from, int to) {
    if (this.graph == null){
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

  private void floyd(int V) {
    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (distance[i][k] == X || distance[k][j] == X) {
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

  public ArrayList<Integer> getPath(int fromLoc, int toLoc) {
    return constructPath(fromLoc, toLoc);
  }
  public double[][] buildGraph(List<TransitPoint> allTransitPoints,
      List<DistanceBetween> allDistances, List<TravelWeight> allTravelWeights) {
    int size = allTransitPoints.size();
    double[][] graph = new double[size + 1][size + 1];
    for (int i = 0; i < graph.length; i++) {
      for (int j = 0; j < graph[i].length; j++) {
        if (i == j) {
          graph[i][j] = 0;
          continue;
        }
        double weight = getWeight(i, j, allTravelWeights);
        if (weight == -1d) {
          graph[i][j] = Integer.MAX_VALUE;
          continue;
        }
        double distance = getDistance(i, j, allDistances);
        if (distance == -1d) {
          graph[i][j] = Integer.MAX_VALUE;
          continue;
        }
        graph[i][j] = distance * weight;
      }
    }
    return graph;
  }
  public double getFinalTripTime(ArrayList<Integer> path,
      List<DistanceBetween> allDistances, List<TravelWeight> allTravelWeights) {
    double sum = 0d;
    for (int i = 0; i < path.size() - 1; i++) {
      Integer transitPointA = path.get(i);
      Integer transitPointB = path.get(i + 1);
      double distance = getDistance(transitPointA, transitPointB, allDistances);
      double weight = getWeight(transitPointA, transitPointB, allTravelWeights);
      sum += distance * weight;
    }
    return sum;
  }

  private double getDistance(int i, int j, List<DistanceBetween> allDistances) {
    double distance = -1d;
    for (DistanceBetween distanceBetween : allDistances) {
      if (distanceBetween.getTransitPointA().getId() == i
          && distanceBetween.getTransitPointB().getId() == j) {
        distance = distanceBetween.getDistance();
      }
    }
    return distance;
  }

  private double getWeight(int i, int j, List<TravelWeight> allTravelWeights) {
    double weight = -1d;
    for (TravelWeight travelWeight : allTravelWeights) {
      if (travelWeight.getTransitPointA().getId() == i
          && travelWeight.getTransitPointB().getId() == j) {
        weight = travelWeight.getCarWeight();
      }
    }
    return weight;
  }
}
