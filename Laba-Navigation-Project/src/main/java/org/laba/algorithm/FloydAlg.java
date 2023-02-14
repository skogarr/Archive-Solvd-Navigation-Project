package org.laba.algorithm;

import java.util.*;

public class FloydAlg {

  private static final int MAXN = 100;

  private final int X;

  private final int[][] distance = new int[MAXN][MAXN];
  private final int[][] next = new int[MAXN][MAXN];
  private int[][] graph;

  public FloydAlg(int[][] graph, int inf) {
    this.X = inf;
    this.graph = graph;
    compute();
  }

  private void compute() {
    init(graph.length, graph);
    floyd(graph.length);
  }

  public int[][] getGraph() {
    return graph;
  }

  public void setGraph(int[][] graph) {
    this.graph = graph;
  }

  private void init(int V,
      int[][] graph) {
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

  private Vector<Integer> constructPath(int from, int to) {
    if (next[from][to] == -1) {
      return null;
    }
    Vector<Integer> path = new Vector<>();
    path.add(from);
    while (from != to) {
      from = next[from][to];
      path.add(from);
    }
  return path;
  }

  private void floyd(int V) {
    for (int k = 0; k < V; k++) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
          if (distance[i][k] == X ||
              distance[k][j] == X) {
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

  private String buildPath(Vector<Integer> path) {
    int n = path.size();
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < n - 1; i++) {
      s.append(path.get(i)).append(" -> ");
    }
    s.append(path.get(n - 1));
    return s.toString();
  }

  public String getPath(int fromLoc, int toLoc) {
    return buildPath(constructPath(fromLoc, toLoc));
  }
}
