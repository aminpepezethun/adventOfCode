import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class day9 extends Graph {
    public static void main(String[] args) {
        List<List<String>> processData = processFile("input.txt");
        Graph graph = new Graph();

        // Add edges to the graph
        for (List<String> line : processData) {
            String src = line.get(0); // get source node
            String dest = line.get(2);  // get destination node
            int dist = Integer.parseInt(line.get(4));  // get distance between 2 nodes
            graph.addEdge(src, dest, dist);  // add edge to the graph
        }

        // Find the shortest route that visits all nodes
        Set<String> vertices = graph.getVertices();  // Get unique vertices values
        List<String> vertexList = new ArrayList<>(vertices);  // Convert into list

        // Initialize shortest and longest distance
        int shortestDistance = Integer.MAX_VALUE;
        int longestDistance = Integer.MIN_VALUE;

        // Generate all permutations of the vertices
        List<List<String>> permutations = generatePermutations(vertexList);

        // Find the shortest distance to every vertices
        for (List<String> perm : permutations) {
            int currentDistance = calculateTotalDistance(perm, graph);
            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
            }
            if (currentDistance > longestDistance) {
                longestDistance = currentDistance;
            }
        }


        System.out.println("Shortest total distance to visit all nodes: " + shortestDistance);
        System.out.println("Longest total distance to visit all nodes: " + longestDistance);
    }

    // Generate all permutations of a list
    // Brute force approach: find all possible permutations
    public static List<List<String>> generatePermutations(List<String> list) {
        // Base case: empty list; return empty List
        if (list.isEmpty()) {
            List<List<String>> emptyResult = new ArrayList<>();
            emptyResult.add(new ArrayList<>());
            return emptyResult;
        }

        // Else
        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < list.size();i++) {
            // temp current element
            String temp = list.get(i);

            // clone list into remainingElement list
            List<String> remainingElement = new ArrayList<>(list);

            // Remove current element from list
            remainingElement.remove(i);

            // Recursion to generate permutation for the remaining elements
            List<List<String>> perms = generatePermutations(remainingElement);

            // for element in List<List<String>> perms
                // <List<String>> perm
            for (List<String> perm : perms) {

                List<String> permList = new ArrayList<>();
                permList.add(temp);
                permList.addAll(perm);

                // result.append([element] + perm)
                result.add(permList);
            }
        }
        return result;

    }

    // Calculate the total distance of a given route
    public static int calculateTotalDistance(List<String> route, Graph graph) {
        int totalDistance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int distance = graph.getDistance(route.get(i), route.get(i + 1));
            if (distance == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE; // If any edge is missing, the route is invalid
            }
            totalDistance += distance;
        }
        return totalDistance;
    }

    // Read file and process into List of Lists
    static List<List<String>> processFile(String nameOfFile) {
        List<String> data = readFile(nameOfFile);
        List<List<String>> processData = new ArrayList<>();
        for (String line : data) {
            String[] words = line.split(" ");
            processData.add(Arrays.asList(words));
        }
        return processData;
    }

    // Read file and return List of Strings
    public static List<String> readFile(String nameOfFile) {
        try {
            return Files.readAllLines(Paths.get(nameOfFile));
        } catch (IOException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}