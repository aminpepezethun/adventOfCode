import java.io.File;
import java.util.*;

/*
    Problem involved finding the maximum Hamiltonian cycle in an undirected graph
 */


public class day13 extends Graph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String inputFile = scanner.nextLine();

        int longestChange = Approach(inputFile, "BruteForce", 0);
        int longestChangeWithSelf = Approach(inputFile,"BruteForce",1 );
        int longestChangeTSP = Approach(inputFile, "TSP", 0);

        System.out.println("Bruteforce result question 1: " + longestChange);
        System.out.println("Bruteforce result question 2: " + longestChangeWithSelf);
        System.out.println("TSP result question 1: " + longestChangeTSP);
    }

    public static int Approach(String fileName, String approach, int mode) {
        List<List<String>> curatedData = curateData(fileName, mode);

        // Create a graph from curatedData
        Graph graph = grapify(curatedData);

        if ("BruteForce".equals(approach)) {
            return BruteForce(graph);
        } else if ("TSP".equals(approach)) {
            Set<String> uniqueVertices = graph.getVertices();
            List<String> listUniqueVertices = new ArrayList<>(uniqueVertices);
            String startVertex = listUniqueVertices.get(0);
            return TSP_DP(graph, startVertex, listUniqueVertices);
        }
        return 0;
    }

    // TSP: greedy approach: not correct result
//    static int TSP_Greedy(Graph graph, String startVertex) {
//        Set<String> unvisited = new HashSet<>(graph.getVertices());
//        String currentVertex = startVertex;
//        int totalDistance = 0;
//
//        unvisited.remove(startVertex);
//
//        while (!unvisited.isEmpty()) {
//            int maxDistance = Integer.MIN_VALUE;
//            String nextVertex = null;
//
//            for (Edge vertices: graph.getAdjVertices(currentVertex)) {
//                if (unvisited.contains(vertices.dest) && vertices.weight > maxDistance) {
//                    maxDistance = vertices.weight;
//                    nextVertex = vertices.dest;
//                }
//
//                if (nextVertex == null) {
//                    break;
//                }
//            }
//
//            totalDistance += maxDistance;
//            unvisited.remove(nextVertex);
//            currentVertex = nextVertex;
//        }
//
//        totalDistance += graph.getDistance(currentVertex, startVertex);
//
//        return totalDistance;
//    }

    // TSP using Held Karp's algorithm
    // Complexity: O(n^2 * 2^n)
    public static int TSP_DP(Graph graph, String startVertex, List<String> vertexList) {
        int n = vertexList.size();
        int startIndex = vertexList.indexOf(startVertex);

        // Create a map to assign each vertex an index
        Map<String, Integer> vertexToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            vertexToIndex.put(vertexList.get(i), i);
        }

        // Initialize the DP table
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MIN_VALUE / 2);
        }

        // Base case: start vertex to itself
        dp[1 << startIndex][startIndex] = 0;

        // Iterate through all subsets of vertices
        for (int mask = 1; mask < (1 << n); mask++) {
            for (int end = 0; end < n; end++) {
                if ((mask & (1 << end)) == 0) continue;

                for (int next = 0; next < n; next++) {
                    if (end == next || (mask & (1 << next)) > 0) continue;

                    int newMask = mask | (1 << next);
                    int distance = graph.getDistance(vertexList.get(end), vertexList.get(next));
                    dp[newMask][next] = Math.max(dp[newMask][next], dp[mask][end] + distance);
                }
            }
        }

        // Find the maximum cost to complete the tour
        int finalMask = (1 << n) - 1;
        int maxCost = Integer.MIN_VALUE;
        for (int end = 0; end < n; end++) {
            if (end == startIndex) continue;
            int tourCost = dp[finalMask][end] + graph.getDistance(vertexList.get(end), startVertex);
            maxCost = Math.max(maxCost, tourCost);
        }

        return maxCost;
    }

    static int BruteForce(Graph graph) {
        int longestChange = Integer.MIN_VALUE;

        // Get a set of unique persons to generate permutations
        Set<String> vertices = graph.getVertices();

        // Turn the set into a list
        List<String> vertexList = new ArrayList<>(vertices);

        // Generate permutations from the list
        List<List<String>> permutations = generatePermutations(vertexList);

        // Find the largest happiness among the permutation
        for (List<String> perm: permutations) {
            int currentChange = calculateTotalHappiness(perm, graph);
            if (currentChange > longestChange) {
                longestChange = currentChange;
            }
        }

        return longestChange;
    }

    static Graph grapify(List<List<String>> data) {
        // Initialise graph
        Graph graph = new Graph();

        // Add edges to the bidirectional weighted graph using the curatedData
        for (List<String> line: data) {
            String src = line.get(0);
            String dest = line.get(1);
            int dist = Integer.parseInt(line.get(2));
            graph.addEdge(src, dest, dist);  // add edge to the graph
        }

        return graph;
    }

    static List<String> getUnique(List<List<String>> list) {
        List<String> uniqueElement = new ArrayList<>();
        for (List<String> line: list) {
            String src = line.get(0);
            String dest = line.get(1);
            if (!uniqueElement.contains(src)) {
                uniqueElement.add(src);
            }
            if (!uniqueElement.contains(dest)) {
                uniqueElement.add(dest);
            }
        }
        return uniqueElement;
    }

    static List<List<String>> generatePermutations(List<String> list) {
        // Base case: if list is empty, return empty list of list
        if (list.isEmpty()) {
            List<List<String>> emptyResult = new ArrayList<>();
            emptyResult.add(new ArrayList<>());
            return emptyResult;
        }

        // Else
        List<List<String>> result = new ArrayList<>();

        // getting permutation by iterating through the list and perform recursion
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

    // Calculate total weight in an arrangement back to the origin
    public static int calculateTotalHappiness(List<String> arrangement, Graph graph) {
        int n = arrangement.size();

        // initiate totalChange as the weight of last and first
        int totalChange = graph.getDistance(arrangement.get(n-1), arrangement.get(0));
        int i = 0;

        while (i < n-1) {
            int change = graph.getDistance(arrangement.get(i), arrangement.get(i+1));

            if (change == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            totalChange += change;

            i++;
        }

        return totalChange;
    }


    // Return a list with bidirectional path between 2 vertices and their accumulate weight
    static List<List<String>> curateData(String name, int mode) {
        List<List<String>> processedData = null;
        List<List<String>> result = new ArrayList<>();

        if (mode == 0) {
            processedData = processData(name);
        } else if (mode == 1) {
            processedData = processDataMyself(name);
        }

        // Set to find uniquePath
        Set<String> visitedSet = new HashSet<>();

        for (List<String> line: processedData) {
            String src = line.get(0);
            String dest = line.get(1);
            String weight = line.get(2);

            // Having two-way pair to check with the visitedSet if they existed
            String pair = src + '-' + dest;
            String reversePair = dest + '-' + src;

            if (!visitedSet.contains(pair) && !visitedSet.contains(reversePair)) {
                // uniquePath = {src, dest, accumulated_weight}
                List<String> uniquePath = getUniquePath(processedData, src, dest);
                if (!uniquePath.isEmpty()) {
                    result.add(uniquePath);    // add to the result the uniquePath
                    visitedSet.add(pair);      // add pair to the visitedSet
                    visitedSet.add(reversePair);  // add reversePair to the visitedSet
                }
            }
        }

        return result;
    }

    // Return uniquePath containing src, dest and their accumulated weight
    static List<String> getUniquePath(List<List<String>> data, String src, String dest) {
        List<String> uniquePath = new ArrayList<>();
        int totalWeight = 0;
        boolean pathFound = false;

        for (List<String> line: data) {
            String node1 = line.get(0);
            String node2 = line.get(1);
            int weight = Integer.parseInt(line.get(2));

            if (node1.equals(src) && node2.equals(dest) || node1.equals(dest) && node2.equals(src)) {
                if (!pathFound) {
                    uniquePath.add(src);
                    uniquePath.add(dest);
                    pathFound = true;
                }
                totalWeight += weight;
            }
        }

        if (pathFound) {
            uniquePath.add(String.valueOf(totalWeight));
        }

        return uniquePath;
    }

    static List<List<String>> processDataMyself(String fileName) {
        List<List<String>> processedData = processData(fileName);
        List<String> uniqueElement = getUnique(processedData);
        String myself = "Myself";
        for (String element : uniqueElement) {
            List<String> line = new ArrayList<>();
            line.add(0, myself);
            line.add(1, element);
            line.add(2, "0");
            processedData.add(line);
        }

        return processedData;
    }

    static List<List<String>> processData(String name) {
        List<List<String>> data = new ArrayList<>();

        try {
            File file = new File(name);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                // split line into list of string
                String[] words = reader.nextLine().split(" ");

                List<String> formatList = new ArrayList<>();

                formatList.add(words[0]); // get source
                formatList.add(words[10].replace(".","")); // get dest, remove . add the end of the char
                if (Objects.equals(words[2], "gain")) {
                    formatList.add(words[3]); // add positive weight
                } else if (Objects.equals(words[2], "lose")) {
                    formatList.add(String.valueOf(-Integer.parseInt(words[3]))); // add negative weight
                }

                data.add(formatList); // add to final result arrayList
            }
            return data;
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        return data;
    }
}
