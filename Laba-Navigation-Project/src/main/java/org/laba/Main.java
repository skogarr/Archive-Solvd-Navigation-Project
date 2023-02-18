package org.laba;

import org.laba.service.my_batis.DistanceBetweenService;
import org.laba.service.my_batis.TransitPointService;
import org.laba.service.my_batis.TravelWeightService;

public class Main {

  public static void main(String[] args) {

    TransitPointService service = new TransitPointService();

    //TravelWeightService service1 = new TravelWeightService();

    //DistanceBetweenService service2 = new DistanceBetweenService();

    System.out.println(service.getAllTransitPoints());
   // System.out.println(service1.getById(1L).toString());
   // System.out.println(service2.getById(1L).toString());

  }
}