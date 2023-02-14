package org.laba;
import org.laba.algorithm.*;

public class Main {
  public static int X = Integer.MAX_VALUE;

  public static void main(String[] args) {
//sample graph
    int [][] graph = {
        { 0, 3, X, 7 },
        { 8, 0, 2, X},
        { 5, X, 0, 1 },
        { 2, X, X, 0 }
    };

    FloydAlg floyd = new FloydAlg(graph, X);
    System.out.println(floyd.getPath(3, 2));
  }
}