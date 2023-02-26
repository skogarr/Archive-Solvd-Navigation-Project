package org.laba;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.*;
import org.laba.algorithm.FloydAlg;
import org.laba.model.DistanceBetween;
import org.laba.model.TransitPoint;
import org.laba.model.TravelWeight;
import org.laba.model.TripType;
import org.laba.service.my_batis.DistanceBetweenService;
import org.laba.service.my_batis.TransitPointService;
import org.laba.service.my_batis.TravelWeightService;

public class Main {
  private static TransitPointService transitPointService = new TransitPointService();
  private static TravelWeightService travelWeightService = new TravelWeightService();
  private static DistanceBetweenService distanceBetweenService = new DistanceBetweenService();
  private static List<TravelWeight> allTravelWeights;
  private static List<DistanceBetween> allDistances;
  private static List<TransitPoint> allTransitPoints;
  private static int source;
  private static int destination;
  private static TripType tripType;

  public static void main(String[] args) {

    Options options = new Options();
    options.addOption("h", "help", false, "Print help message");
    options.addOption("s", "source", true, "Input source transit-point id");
    options.addOption("d", "destination", true, "Input destination transit-point id");
    options.addOption("t", "type", true, "Input travel type public/private");

    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine cmd = parser.parse(options, args);
      if (cmd.hasOption("help")) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar Navigator.jar", options);
        return;
      }
      source = Integer.parseInt(cmd.getOptionValue("source"));
      destination = Integer.parseInt(cmd.getOptionValue("destination"));
      String type = cmd.getOptionValue("type");
      switch (type){
        case "private" -> tripType = TripType.PRIVATE;
        case "public" -> tripType = TripType.PUBLIC;
      }
    } catch (ParseException e) {
      System.err.println("Error parsing command-line arguments: " + e.getMessage());
    }
    allTravelWeights = travelWeightService.getAllTravelWeights();
    allDistances = distanceBetweenService.getAllDistances();
    allTransitPoints = transitPointService.getAllTransitPoints();
    switch (tripType){
      case PRIVATE -> {
        FloydAlg carTransport = new FloydAlg(allTravelWeights,allDistances,allTransitPoints);
        double[][] graph = carTransport.buildGraphCar();
        carTransport.setGraph(graph);
        ArrayList<Integer> pathCar = carTransport.getPath(source, destination);
        System.out.println(pathCar);
        double finalTripTime = carTransport.getFinalTripTime(pathCar, tripType);
        System.out.println(finalTripTime);
      }
      case PUBLIC -> {
        FloydAlg publicTransport = new FloydAlg(allTravelWeights,allDistances,allTransitPoints);
        double[][] graph = publicTransport.buildGraphPublic();
        publicTransport.setGraph(graph);
        ArrayList<Integer> pathPublic = publicTransport.getPath(source, destination);
        System.out.println(pathPublic);
        double publicTransportFinalTripTime = publicTransport.getFinalTripTime(pathPublic, tripType);
        System.out.println(publicTransportFinalTripTime);

        publicTransport.prettyPrintPublic(pathPublic);
      }
    }
  }
}