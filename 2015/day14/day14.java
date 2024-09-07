import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day14 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter file name: ");
            String fileName = scanner.nextLine();
            System.out.println("Enter time in seconds: ");
            Integer time = Integer.parseInt(scanner.nextLine());

            List<List<String>> reeindersList = processFile(fileName);

            int q1 = findMaxLength(reeindersList, time);
            int q2 = findMaxScore(reeindersList, time);

            System.out.println("Max length after " + time + " seconds is " + q1);
            System.out.println("Max score after " + time + " seconds is " + q2);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for time limit. Please enter a valid integer.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Wrapper method for question 2
    static int findMaxScore(List<List<String>> reindeersList, int time) {
        List<Integer> scoreList = calculateScore(reindeersList, time);
        return Collections.max(scoreList);
    }

    static List<Integer> calculateScore(List<List<String>> reindeersList, int time) {
        int n = reindeersList.size();
        List<Integer> distanceList = new ArrayList<>(Collections.nCopies(n, 0));
        List<Integer> scoreList = new ArrayList<>(Collections.nCopies(n, 0));
        List<Integer> timeCounter = new ArrayList<>(Collections.nCopies(n, 0));
        List<Boolean> isFlyingList = new ArrayList<>(Collections.nCopies(n, true));

        for (int i = 0; i < time; i++) {
            increment(reindeersList, distanceList, timeCounter, isFlyingList);
            int maxValue = Collections.max(distanceList);
            for (int j = 0; j < n; j++) {
                if (distanceList.get(j) == maxValue) {
                    scoreList.set(j, scoreList.get(j) + 1);
                }
            }
        }

        return scoreList;
    }

    // reindeerData [speed, speedTime, restTime]
    // currentData  [distance, speedTimeAccumulation, restTimeAccumulation, isRest]
    static void increment(List<List<String>> reindeersList, List<Integer> distanceList, List<Integer> timeCounter, List<Boolean> isFlyingList) {
        int n = distanceList.size();

        for (int i = 0; i < n; i++) {
            int speed = Integer.parseInt(reindeersList.get(i).get(1));
            int speedTime = Integer.parseInt(reindeersList.get(i).get(2));
            int restTime = Integer.parseInt(reindeersList.get(i).get(3));

            if (isFlyingList.get(i)) {
                // increment distance
                distanceList.set(i, distanceList.get(i) + speed);
                // increment time
                timeCounter.set(i, timeCounter.get(i) + 1);
                // if time is equals to speedTime
                if (timeCounter.get(i) == speedTime) {
                    // set flying state to rest/ false
                    isFlyingList.set(i, false);
                    // reset time counter
                    timeCounter.set(i, 0);
                }
            }
            else {
                // increment time
                timeCounter.set(i, timeCounter.get(i) + 1);
                // if time is equals to restTime
                if (timeCounter.get(i) == restTime) {
                    // set flying state to flying/ true
                    isFlyingList.set(i, true);
                    // reset timeCounter
                    timeCounter.set(i,0);
                }
            }
        }
    }


    // Wrapper method for question 1
    static int findMaxLength(List<List<String>> reeindeersList, int time) {
        List<Integer> distanceList = new ArrayList<>();

        for (List<String> reeindeer: reeindeersList) {
            // get distance of each reindeer after 'time' second
            int distance = calculateDistance(time, Integer.parseInt(reeindeer.get(1)), Integer.parseInt(reeindeer.get(2)), Integer.parseInt(reeindeer.get(3)));
            distanceList.add(distance);
        }

        // get the max distance
        return Collections.max(distanceList);
    }

    static int calculateDistance(int time, int speed, int speedTime, int restTime) {
        if (time < 0 || speed < 0 || speedTime <= 0 || restTime < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        // (a+b) * n + c = totalTime
        // n = totalTime / (a+b)  where n is the number of cycles occurred
        int totalDistance = 0;
        int cycleTime = speedTime + restTime;
        int fullCycle = time / cycleTime;
        int remainingTime = time % cycleTime;

        totalDistance += fullCycle * speedTime * speed;

        for (int i=0; i < remainingTime; i++) {
            if (i < speedTime) {
                totalDistance += speed;
            }
        }

        return totalDistance;
    }

    // getFile function
    static List<String> getFile(String nameOfFile) {
        try {
            return Files.readAllLines(Paths.get(nameOfFile));
        } catch (IOException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // processFile to List<List<String>>
    static List<List<String>> processFile(String nameOfFile) {
        List<String> fileData = getFile(nameOfFile);
        int numberOfReindeer = fileData.size();
        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < numberOfReindeer; i++) {
            List<String> line = List.of(fileData.get(i).split(" "));
            List<String> newLine = new ArrayList<>();
            String name = line.get(0);          // nameOfReindeer
            String speed = line.get(3);         // speedPerSec
            String speedTime = line.get(6);     // Seconds to keep speed
            String restTime = line.get(13);     // Seconds to rest after speedTime

            newLine.add(name);
            newLine.add(speed);
            newLine.add(speedTime);
            newLine.add(restTime);

            result.add(newLine);
        }

        return result;
    }
}
