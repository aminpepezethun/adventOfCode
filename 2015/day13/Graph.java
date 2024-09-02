import java.util.*;

class Graph {
    /*
        Graph is represent as following format:
            {
              "Tristram": [Edge("AlphaCentauri", 34), Edge("Snowdin", 100), ...],
              "AlphaCentauri": [Edge("Tristram", 34), Edge("Snowdin", 4), ...],
              ...
            }
    */

    // Represent adjacency list as HashMap
    private final Map<String, List<Edge>> adjList;

    // Inner Edge class
    static class Edge {
        // Field: String dest, int weight
        String dest;
        int weight;

        // Constructor: Edge(String d, int w)
        Edge(String d, int w) {
            dest = d;
            weight = w;
        }
    }

    // Constructor: Graph()
    Graph() {
        adjList = new HashMap<>();
    }

    // Method addVertex (use by addEdge method):
    // Add a vertex into Graph
    void addVertex(String v) {
        if (!adjList.containsKey(v)) {
            adjList.put(v, new ArrayList<>());
        }
    }

    // Method addEdge(String u, String v, int w):
    // Adding src, dest and weight into graph as an undirected weighted edge.
    void addEdge(String u, String v, int w) {
        addVertex(u);
        addVertex(v);
        adjList.get(u).add(new Edge(v, w));
        adjList.get(v).add(new Edge(u, w));
    }

    // Method getDistance(String u, String v):
    // Getting dist/ weight between 2 vertices in the graph
    int getDistance(String u, String v) {
        for (Edge edge : adjList.get(u)) {
            if (edge.dest.equals(v)) {
                return edge.weight;
            }
        }
        return Integer.MAX_VALUE; // If no edge exists, return a large value
    }

    // Method getVertices():
    // Get all unique vertices from adjacency list.
    Set<String> getVertices() {
        return adjList.keySet();
    }
}