import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class test {
    public static void main(String[] args) {
        String input = "1113122113";
        List<String> StringList = Arrays.asList(input.split(""));
        List<Integer> numList = StringList.stream().map(Integer::valueOf).toList(); // convert list of String to list of int

        for (int i = 0; i < 40; i++) {
            numList = process(numList);
        }


        System.out.println(numList.size());


    }
    public static List<Integer> process(List<Integer> numList) {
        if (numList.isEmpty()) {
            return new ArrayList<>(); // r if numList is empty
        }

        int count = 1;
        List<Integer> processedNumList = new ArrayList<>();

        for (int i = 1; i < numList.size(); i++) {
            if (!Objects.equals(numList.get(i), numList.get(i - 1))) {
                processedNumList.add(count);
                processedNumList.add(numList.get(i-1));
                count = 1;
            } else {
                count++;
            }
        }

        processedNumList.add(count);
        processedNumList.add(numList.get(numList.size() - 1));

        return processedNumList;
    }
}

