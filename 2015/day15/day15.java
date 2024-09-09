import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    a + b + c + d = 100
                    capacity    durability  flavor  texture calories
    Frosting        4           -2          0       0       5
    Candy           0           5           -1      0       8
    Butterscotch    -1          0           5       0       6
    Sugar           0           0           -2      2       1

    Capacity = (a * 4) + (b * 0) + (c * -1) + (d * 0)
    Durability = (a * -2) + (b * 5) + (c * 0) + (d * 0)
    Flavor = (a * 0) + (b * -1) + (c * 5) + (d * -2)
    Texture = (a * 0) + (b * 0) + (c * 0) + (d * 2) 

 */

public class day15 {
    public static void main(String[] args) {
        final int total = 100;     // total number of teaspoons allowed
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter file name: ");
            String fileName = scanner.nextLine();

            List<String> data = getFile(fileName);

            List<List<Integer>> processedData = processFile(fileName);

            int q1 = bruteForce(processedData, 1);
            int q2 = bruteForce(processedData, 2);

            System.out.println(q1);
            System.out.println(q2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int bruteForce(List<List<Integer>> data, int question) {
        int max_q1 = 0;
        int max_q2 = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < (100 - i); j++) {
                for (int k = 0; k < (100 - i - j); k++) {
                    int h = 100 - i - j - k;
                    int capacity = Math.max(0, data.get(0).get(0) * i + data.get(1).get(0) * j + data.get(2).get(0) * k + data.get(3).get(0) * h);
                    int durability = Math.max(0, data.get(0).get(1) * i + data.get(1).get(1) * j + data.get(2).get(1) * k + data.get(3).get(1) * h);
                    int flavor = Math.max(0, data.get(0).get(2) * i + data.get(1).get(2) * j + data.get(2).get(2) * k + data.get(3).get(2) * h);
                    int texture = Math.max(0, data.get(0).get(3) * i + data.get(1).get(3) * j + data.get(2).get(3) * k + data.get(3).get(3) * h);
                    int calories = Math.max(0, data.get(0).get(4) * i + data.get(1).get(4) * j + data.get(2).get(4) * k + data.get(3).get(4) * h);

                    int score = capacity * durability * flavor * texture;

                    if (calories == 500) {
                        max_q2 = Math.max(max_q2, score);
                    }

                    max_q1 = Math.max(max_q1, score);
                }
            }
        }
        if (question == 1) {
            return max_q1;
        } else if (question == 2) {
            return max_q2;
        } return 0;
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

    static List<List<Integer>> processFile(String fileName) {
        List<String> fileData = getFile(fileName);
        List<List<Integer>> result = new ArrayList<>();

        for (String lineData : fileData) {
            if (!lineData.isEmpty()) {
                List<Integer> newLine = new ArrayList<>();
                List<String> line = List.of(lineData.split(" "));

                Integer capacity = Integer.parseInt(line.get(2).replace(",", ""));     // capacity index 1
                Integer durability = Integer.parseInt(line.get(4).replace(",", ""));   // durability index 2
                Integer flavor = Integer.parseInt(line.get(6).replace(",", ""));       // flavor index 3
                Integer texture = Integer.parseInt(line.get(8).replace(",", ""));      // texture index 4
                Integer calories = Integer.parseInt(line.get(10).replace(",", ""));    // calories index 5

                newLine.add(capacity);
                newLine.add(durability);
                newLine.add(flavor);
                newLine.add(texture);
                newLine.add(calories);

                result.add(newLine);
            }
        }

        return result;
    }
}
