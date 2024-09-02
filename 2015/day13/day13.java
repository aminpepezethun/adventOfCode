import java.io.File;
import java.util.*;

/*
    Problem involved finding the maximum possible weight in a cycle graph
 */


public class day13 extends Graph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String inputFile = scanner.nextLine();

        int longestChange = Approach(inputFile, "BruteForce", 0);
        int longestChangeWithSelf = Approach(inputFile,"BruteForce",1 );

        System.out.println(longestChange);
        System.out.println(longestChangeWithSelf);
    }

    public static int Approach(String fileName, String approach, int mode) {
        // processData from file into List<List<String>>
        List<List<String>> processedData = null;
        List<List<String>> curatedData = null;

        // Mode 0: normal
        if (mode == 0) {
            processedData = processData(fileName);
            // Get unique pairs of elements (formula: n * (n-1) / 2) where n is the number of elements
            curatedData = curateData(processedData);
        }
        // Mode 1: with myself
        else if (mode == 1) {
            processedData = processDataMyself(fileName);
            // Get unique pairs of elements (formula: n * (n-1) / 2) where n is the number of elements
            curatedData = curateData(processedData);
        }

        // Initialise graph
        Graph graph = new Graph();

        // Add edges to the bidirectional weighted graph using the curatedData
        for (List<String> line: curatedData) {
            String src = line.get(0);
            String dest = line.get(1);
            int dist = Integer.parseInt(line.get(2));
            graph.addEdge(src, dest, dist);  // add edge to the graph
        }

        if (Objects.equals(approach, "BruteForce")) {
            return BruteForce(graph);
        } else if (Objects.equals(approach, "TSP")) {
            return 0;
        } else {
            return 0;
        }
    }

//    // Not implemented yet
//    static int TSP(Graph graph) {
//        int longestChange = Integer.MIN_VALUE;
//
//
//
//        return longestChange;
//    }

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
    static List<List<String>> curateData(List<List<String>> data) {
            List<List<String>> result = new ArrayList<>();

            // Set to find uniquePath
            Set<String> visitedSet = new HashSet<>();

            for (List<String> line: data) {
                String src = line.get(0);
                String dest = line.get(1);
                String weight = line.get(2);

                // Having two-way pair to check with the visitedSet if they existed
                String pair = src + '-' + dest;
                String reversePair = dest + '-' + src;

                if (!visitedSet.contains(pair) && !visitedSet.contains(reversePair)) {
                    // uniquePath = {src, dest, accumulated_weight}
                    List<String> uniquePath = getUniquePath(data, src, dest);
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
