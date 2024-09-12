import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day16 {
    public static void main(String[] args) {
        final List<String> comparision = List.of("children 3",
        "cats 7",
        "samoyeds 2",
        "pomeranians 3",
        "akitas 0",
        "vizslas 0",
        "goldfish 5",
        "trees 3",
        "cars 2",
        "perfumes 1"
        );
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = scanner.nextLine();

        List<List<String>> data = processFile(fileName);

        Map<String, Integer> comp = convertComparisonToMap(comparision);

        Integer q1 = loopForAunty(data, comparision, 1);
        Integer q2 = loopForAunty(data, comparision, 2);

        System.out.println(q1);
        System.out.println(q2);

    }

    static Map<String, Integer> convertComparisonToMap(List<String> comparison) {
        Map<String, Integer> compMap = new HashMap<>();
        for (String line: comparison) {
            String[] value = line.split(" ");
            String name = value[0];
            Integer number = Integer.parseInt(value[1]);
            compMap.put(name, number);
        }

        return compMap;
    }


    static Integer loopForAunty(List<List<String>> data, List<String> comparison, int mode) {
        if (mode == 1) {
            int i = 1;
            for (List<String> line : data) {
                if (!(comparison.contains(line.get(0)) && comparison.contains(line.get(1)) && comparison.contains(line.get(2)))) {
                    i++;
                } else {
                    return i;
                }
            }
        }
        else if (mode == 2) {
            Map<String, Integer> comparisonMap = convertComparisonToMap(comparison);

            Set<String> comparisonItems = comparisonMap.keySet();
            List<String> preCondition = List.of("cats", "trees","pomeranians", "goldfish");
            List<String> greaterThan = List.of("cats", "trees");


            int count = 1;
            // Each line has 3 items
            for (List<String> line: data) {
                // convert line to map
                Map<String, Integer> lineMap = convertComparisonToMap(line);
                // get key set
                Set<String> lineItems = lineMap.keySet();
                List<String> lineItemsList = new ArrayList<>(lineItems);
                List<Boolean> isItem = new ArrayList<>(List.of(false, false, false));


                // evaluate
                for (int i = 0; i < 3; i++) {
                    // if item is in comparisonItems
                    if (comparisonItems.contains(lineItemsList.get(i))) {
                        // name of the current item
                        String currentItem = lineItemsList.get(i);

                        // if current item is in preCondition
                        if (preCondition.contains(currentItem)) {
                            // if current item is in greaterThan
                            if (isGreater(currentItem, greaterThan)) {
                                // if the value is larger than the comparison value
                                if (lineMap.get(currentItem) >= comparisonMap.get(currentItem)) {
                                    isItem.set(i, true);
                                }
                            // else if current item is not in greaterThan
                            } else {
                                // if the value is smaller than the comparison value
                                if (lineMap.get(currentItem) <= comparisonMap.get(currentItem)) {
                                    isItem.set(i, true);
                                }
                            }
                        }
                        // else if current item has the same value as comparisonMap[currentItem]
                        else if (Objects.equals(lineMap.get(currentItem), comparisonMap.get(currentItem))) {
                            isItem.set(i, true);
                        }

                    }
                }

                if (!(isItem.get(0) && isItem.get(1) && isItem.get(2))) {
                    count++;
                } else {
                    return count;
                }
            }
        }
        return null;
    }

    static boolean isGreater(String item, List<String> greaterThan) {
        return greaterThan.contains(item);
    }

    private static List<String> getFile(String nameOfFile) {
        try {
            return Files.readAllLines(Paths.get(nameOfFile));
        } catch (IOException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    static List<List<String>> processFile(String fileName) {
        List<String> fileData = getFile(fileName);
        List<List<String>> data = new ArrayList<>();

        for (String line: fileData) {
            if (!line.isEmpty()) {
                List<String> newLine = new ArrayList<>();
                List<String> lineList = List.of(line.split(" "));

                String item1 = lineList.get(2).replace(":", "");
                String nItem1 = lineList.get(3).replace(",", "");
                String it1 = item1 + " " + nItem1;

                String item2 = lineList.get(4).replace(":", "");
                String nItem2 = lineList.get(5).replace(",", "");
                String it2 = item2 + " " + nItem2;

                String item3 = lineList.get(6).replace(":", "");
                String nItem3 = lineList.get(7);
                String it3 = item3 + " " + nItem3;

                newLine.add(it1);
                newLine.add(it2);
                newLine.add(it3);

                data.add(newLine);
            }
        }
        return data;
    }

}
