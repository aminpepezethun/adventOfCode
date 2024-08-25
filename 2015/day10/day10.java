import java.util.*;

public class day10 {
    public static void main(String[] args) {
        // Getting input from user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scanner.nextLine();

        // Turn input into list of words
        List<String> wordList = new ArrayList<>(Arrays.asList(input.split("")));

        // Turn list of words into list of Integer
        List<Integer> numList1 = wordList.stream().map(Integer::valueOf).toList();
        List<Integer> numList2 = new ArrayList<>(numList1);


        for (int i = 0; i < 40; i++) {
            numList1 = process(numList1);
        }

        for (int i = 0; i < 50; i++) {
            numList2 = process(numList2);
        }

        System.out.println("Part 1: " + numList1.size());
        System.out.println("Part 2: " + numList2.size());
    }

    // process function to apply Conway's method
    /*
        pseudocode:
            if the list is empty:
                return an empty list
            - initialize count = 1 for counting the same element
            - initialize an empty list for result
            - iterate through the length of list, start at index 1:
                compare if the element at index 1 is equal to index i - 1:
                if !:
                    add the count value to the result
                    add the value at index i - 1 to the result
                else:
                    count++
            - add the rest count into result
            - add the value at the end of the list to the result
     */
    public static List<Integer> process(List<Integer> numList) {
        if (numList.isEmpty()) {
            return new ArrayList<>();
        }

        // initialize count = 1
        int count = 1;

        // initialize result list
        List<Integer> result = new ArrayList<>();

        // iterate over the number of element in the list, start at index 1
        for (int i = 1; i < numList.size(); i++) {
            // compare the value at index i to value at index i - 1
            // if they're not equal
            if (!Objects.equals(numList.get(i), numList.get(i - 1))) {
                // add the count to result
                result.add(count);
                // add the element to result
                result.add(numList.get(i - 1));
                // reset the count to 1
                count = 1;
            // else if the value are equals
            } else {
                // increase count by 1
                count++;
            }
        }

        // add the rest count of elements to result
        result.add(count);
        // add the final element to result
        result.add(numList.get(numList.size()-1));

        return result;
    }

}

