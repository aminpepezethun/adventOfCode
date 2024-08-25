import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class day12 {
    public static void main(String[] args) {
        /*
            Use Jackson's library to read and process JSON file as JsonNode data structure
         */

        // Taking input
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter file name");
        String fileName = myObj.nextLine();  // Read user input

        int result1 = sumNum(fileName,1);
        int result2 = sumNum(fileName, 2);


        System.out.println(result1);
        System.out.println(result2);
    }

    static int sumNum(String fileName, int questionNumber) {
        ObjectMapper objectMapper = new ObjectMapper();
        int sum_1 = 0;
        int sum_2 = 0;

        try {
            // Parse the JSON file
            // Having objectMapper for Java type conversion
            JsonNode rootNode = objectMapper.readTree(new File(fileName));

            // Calculate the sum of all objects and arrays
            sum_1 = sumNumbers(rootNode);

            // Calculate the sum of non-red objects and arrays
            sum_2 = sumNumberExcludeRed(rootNode);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (questionNumber == 1) {
            return sum_1;
        } else if (questionNumber == 2) {
            return sum_2;
        } else {
            return 0;
        }
    }

    // sumNumberExcludeRed: recursion function
    /*
        Ignore any object (and all of its children) which has any property with the value "red". Do this only for objects ({...}), not arrays ([...]).

        [1,2,3] still has a sum of 6.
        [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
        {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
        [1,"red",5] has a sum of 6, because "red" in an array has no effect.

    */
    private static int sumNumberExcludeRed(JsonNode node) {
        int sum = 0;
        String red = "red";
        // if we're encountering an Object, checks for its elements
        if (node.isObject()) {
            boolean hasRed = false;

            // Using Iterator for element iteration
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

            // Check if there's a next value
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                // if value in Map is a String and equals "red", stop the function and return 0;
                if (field.getValue().isTextual() && field.getValue().asText().equals("red")) {
                    hasRed = true;
                    break;
                }
            }

            // If there are no value in Map has "red" value
            if (!hasRed) {
                fields = node.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    // Recursion **
                    // Add the result of recursive function of the field's value to sum
                    sum += sumNumberExcludeRed(field.getValue());
                }
            }

        // if node is an array
        } else if (node.isArray()) {
            for (JsonNode element : node) {
                if (element.isTextual() && element.asText().equals("red")) {
                    // skip the "red" element
                    continue;
                }
                // Recursion **
                // Add the result of recursive function on node's children to sum
                sum += sumNumberExcludeRed(element);
            }

        // if node itself is a digit/integer/number
        } else if (node.isNumber()) {
            // Add the node value to sum
            sum += node.intValue();
        }


        // return total sum
        return sum;
    }


    // sumNumbers : Recursion function : count int value in JSON node tree data structure
    private static int sumNumbers(JsonNode node) {
        int sum = 0;

        if (node.isNumber()) {
            sum += node.intValue();
        } else if (node.isObject() || node.isArray()) {
            for (JsonNode child : node) {
                // Recursion to find value in children branches
                sum += sumNumbers(child);
            }
        }

        return sum;
    }
}
