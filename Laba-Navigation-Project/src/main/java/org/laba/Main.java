package org.laba;

import java.util.ArrayList;
import java.util.List;
import org.laba.algorithm.FloydAlg;
import org.laba.model.DistanceBetween;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;
import org.laba.model.TripType;
import org.laba.service.my_batis.BusStopService;
import org.laba.service.my_batis.DistanceBetweenService;
import org.laba.service.my_batis.TransitPointService;
import org.laba.service.my_batis.TravelWeightService;

public class Main {
  static TransitPointService transitPointService = new TransitPointService();
  static TravelWeightService travelWeightService = new TravelWeightService();
  static DistanceBetweenService distanceBetweenService = new DistanceBetweenService();

  public static void main(String[] args) {
    List<TravelWeight> allTravelWeights = travelWeightService.getAllTravelWeights();
    List<DistanceBetween> allDistances = distanceBetweenService.getAllDistances();
    List<TransitPoint> allTransitPoints = transitPointService.getAllTransitPoints();

    FloydAlg carTransport = new FloydAlg(allTravelWeights,allDistances,allTransitPoints);
    double[][] graph = carTransport.buildGraphCar();
    carTransport.setGraph(graph);
    ArrayList<Integer> pathCar = carTransport.getPath(1, 100);
    System.out.println(pathCar);
    double finalTripTime = carTransport.getFinalTripTime(pathCar, TripType.PRIVATE);
    System.out.println(finalTripTime);

    FloydAlg publicTransport = new FloydAlg(allTravelWeights,allDistances,allTransitPoints);
    double[][] doubles = publicTransport.buildGraphPublic();
    publicTransport.setGraph(doubles);
    ArrayList<Integer> pathPublic = publicTransport.getPath(1, 100);
    System.out.println(pathPublic);
    double publicTransportFinalTripTime = publicTransport.getFinalTripTime(pathPublic, TripType.PUBLIC);
    System.out.println(publicTransportFinalTripTime);

    publicTransport.prettyPrintPublic(pathPublic);

  }
}