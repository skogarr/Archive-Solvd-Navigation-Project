package org.laba;

import java.util.ArrayList;
import java.util.List;
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
    List<TravelWeight> allTravelWeights = service1.getAllTravelWeights();
    List<DistanceBetween> allDistances = service2.getAllDistances();
    List<TransitPoint> allTransitPoints = service.getAllTransitPoints();
    System.out.println(allTransitPoints.size());
    FloydAlg floydAlg = new FloydAlg();
    double[][] graph = floydAlg.buildGraph(allTransitPoints, allDistances, allTravelWeights);
    floydAlg.setGraph(graph);
    ArrayList<Integer> path = floydAlg.getPath(1, 100);
    System.out.println(path);
    double finalTripTime = floydAlg.getFinalTripTime(path, allDistances, allTravelWeights);
    System.out.println(finalTripTime);
  }




}