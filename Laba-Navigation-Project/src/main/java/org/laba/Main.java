package org.laba;
import java.util.ArrayList;
import org.laba.algorithm.*;

public class Main {
  public static int X = Integer.MAX_VALUE;

  public static void main(String[] args) {
//sample graph
    double [][] graph = {
        { 0, 3, X, 7 },
        { 8, 0, 2, X},
        { 5, X, 0, 1 },
        { 2, X, X, 0 }
    };

    FloydAlg floyd = new FloydAlg();
    floyd.setGraph(graph);
    ArrayList<Integer> path = floyd.getPath(3, 2);
    System.out.println(path);
  }
}