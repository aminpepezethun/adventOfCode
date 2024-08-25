package adventofcode.haikhongmuoilam.day8.day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

public class day8 {
    public static void main(String[] args) {
        List<String> lines = getFile("input.txt");

        int rq1 = 0;
        int rq2 = 0;

        for (String line : lines) {
            int re1 = SolveP1(line);
            int re2 = SolveP2(line);

            // Accumulate difference line by line to rq1
            rq1 += re1;
            // Accumulate difference line by line to rq2;
            rq2 += re2;
        }
        System.out.println("Day 8 Part 1 answer: " + rq1);
        System.out.println("Day 8 Part 2 answer: " + rq2);
    }

    public static int SolveP1(String line) {
        List<Character> hexContent = Arrays.asList('0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f');

        int n = line.length();
        int count = 0;
        int i = 1; // pointer

        while (i<n-1) {
            // the current position is a backslash
            if (line.charAt(i) == '\\') {
                // case 1: the second,third and forth is x,hex_content and hex_content respectively
                if (i+3 < n && line.charAt(i+1) == 'x' && hexContent.contains(line.charAt(i+2)) && hexContent.contains(Character.toLowerCase(line.charAt(i+3)))) {
                    i+=4;
                    // +1 for the encode backslash behind backslash
                    count++;
                // case 2: the second is either another backslash or a double quotation
                } else if (i+1<n && (line.charAt(i+1) == '\\' || line.charAt(i+1) == '"')) {
                    i += 2;
                    count++;
                // case 3: if none of the above, then it's a character
                } else {
                    // skip the backslash
                    i += 2;
                    // plus 1 for the character behind the backslash
                    count++;
                }
            // the current position is a normal character
            } else {
                i++;
                count++;
            }
        }
        return n - count;
    }
    public static int SolveP2(String line) {

        int n = line.length();
        int encodedLength = 2;
        int i = 0;

        while (i<n) {
            if (line.charAt(i) == '\\') {
                encodedLength+=2;
                i++;
            } else if (line.charAt(i) == '"') {
                encodedLength+=2;
                i++;
            } else {
                encodedLength++;
                i++;
            }
        }
        return encodedLength - n;
    }

    public static List<String> getFile(String nameOfFile) {
        List<String> result;
        try {
            result = Files.readAllLines(Paths.get(nameOfFile));
        } catch (IOException e) {
            System.out.println("No file exists with the name: " + nameOfFile + ". Error: " + e);
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
