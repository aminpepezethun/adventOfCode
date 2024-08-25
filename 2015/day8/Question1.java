package adventofcode.haikhongmuoilam.day8.day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

public class Question1 {
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("input.txt"));

            // hexContent List to store possible hex characters
            List<Character> hexContent = Arrays.asList('0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f');

            int result = 0;
            
            for (String line : lines) {
                int n = line.length();
                int count = 0;
                int i = 1; // start after the first double quotation

                // end before the last double quotation
                while (i<n-1) {
                    // the current position is a backslash
                    if (line.charAt(i) == '\\') {
                        // case 1: the second,third and forth is x,hex_content and hex_content respectively
                        if (i+3 < n && line.charAt(i+1) == 'x' && hexContent.contains(line.charAt(i+2)) && hexContent.contains(Character.toLowerCase(line.charAt(i+3)))) {
                            i+=4;
                            count++;
                        // case 2: the second is either another backslash or a double quotation
                        } else if (i+1<n && (line.charAt(i+1) == '\\' || line.charAt(i+1) == '"')) {
                            i += 2;
                            count++;
                        // case 3: if none of the above, then it's a character
                        } else {
                            // skip the backslash
                            i += 2;
                            count++;
                        }
                    // the current position is a normal character
                    } else {
                        i++;
                        count++;
                    }
                }
                // accumulate the differences between the line's length and the valid count of that line
                result += n - count;

            }
            // print the result
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}