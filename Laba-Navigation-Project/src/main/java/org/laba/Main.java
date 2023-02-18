package org.laba;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.laba.algorithm.FloydAlg;
import org.laba.model.DistanceBetween;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;
import org.laba.service.my_batis.DistanceBetweenService;
import org.laba.service.my_batis.TransitPointService;
import org.laba.service.my_batis.TravelWeightService;

public class Main {
  static TransitPointService service = new TransitPointService();

  static TravelWeightService service1 = new TravelWeightService();

  static DistanceBetweenService service2 = new DistanceBetweenService();

  public static void main(String[] args) {
    List<TransitPoint> allTransitPoints = service.getAllTransitPoints();
/*     System.out.println(service1.getById(1L).toString());
     System.out.println(service2.getById(1L).toString());*/
    FloydAlg floydAlg = new FloydAlg();
    double[][] graph = buildGraph(allTransitPoints);
    floydAlg.setGraph(graph);
    ArrayList<Integer> path = floydAlg.getPath(1, 50);
    System.out.println(path);
    double finalTripLength = getFinalTripLength(path);
    System.out.println(finalTripLength);
  }
  private static double [][] buildGraph(List<TransitPoint> allTransitPoints){
    int size = allTransitPoints.size();
    double [][] graph = new double[size+1][size+1];
    for (int i = 1; i < graph.length; i++) {
      for (int j = 1; j < graph[i].length; j++) {
        if (i==j){
          graph[i][j] = 0;
          continue;
        }
        DistanceBetween distance = service2.getDistanceByTransitPointAidAndTransitPointBid(i, j);
        TravelWeight weight = service1.getTravelWeightByTransitPointAidAndTransitPointBid(i, j);
        if (weight.getCarWeight() != 0){
          graph[i][j] = distance.getDistance() * weight.getCarWeight();
          continue;
        }
        graph[i][j] = Integer.MAX_VALUE;
      }
    }
    return graph;
  }

  private static double getFinalTripLength(ArrayList<Integer> path){
    double sum = 0d;
    for (int i = 0; i < path.size()-1; i++) {
      Integer transitPointA = path.get(i);
      Integer transitPointB = path.get(i+1);
      DistanceBetween distance = service2.getDistanceByTransitPointAidAndTransitPointBid
          (transitPointA, transitPointB);
      TravelWeight weight = service1.getTravelWeightByTransitPointAidAndTransitPointBid
          (transitPointA, transitPointB);
      sum += distance.getDistance() * weight.getCarWeight();
    }
    return sum;
  }
}