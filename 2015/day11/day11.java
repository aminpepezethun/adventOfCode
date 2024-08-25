import java.util.*;

public class day11 {
    public static void main(String[] args) {
        System.out.println("Enter the initial password: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        List<Character> psswd = convertToChar(input);

        do {
            psswd = increment(psswd);
        } while (!validity(psswd));

        System.out.println(stringConverter(psswd));
    }

    static List<Character> increment(List<Character> psswd) {
        int n = psswd.size();
        if (n == 0) {
            System.out.println("No value in password");
            return psswd;
        }

        // 'z' char ASCII value is 122
        // 'a; char ASCII value is 97
        for (int i = n-1; i >= 0; i--) {
            if (psswd.get(i) == 'z') {
                psswd.set(i, 'a');
            } else {
                char newChar = (char) (((int) psswd.get(i)) + 1);
                psswd.set(i, newChar);
                break;
            }
        }
        return psswd;
    }

    // Wrapper method to valid the list of characters
    static boolean validity(List<Character> psswd) {
        return size_validity(psswd)
                && exclude_i_o_l(psswd) // CHANGE ORDER INCREASE PROCESSING TIME
                && three_element_increase(psswd)
                && pair_exist(psswd);
    }

    // If there's 3 elements increase in the list
    static boolean three_element_increase(List<Character> psswd) {
        int n = psswd.size();
        int count = 1;
        int i = 0;
        while (i < n - 1) {
            int dif = (int) psswd.get(i+1) - (int) psswd.get(i);
            if (dif == 1) {
                count++;
                if (count >= 3) {
                    return true;
                }
            } else {
                count = 1;
            }
            i++;
        }
        return false;
    }

    static boolean exclude_i_o_l(List<Character> psswd) {
        final List<Character> exclude = Arrays.asList('i','o','l');
        for (Character character : psswd) {
            if (exclude.contains(character)) {
                return false;
            }
        }
        return true;
    }

    // If there's a non-overlapping pairs
    static boolean pair_exist(List<Character> psswd) {
        int n = psswd.size();
        int pairs = 0;
        int i = 0;

        while (i < n-1) {
            if (psswd.get(i) == psswd.get(i+1)) {
                pairs++;
                i+=2;
            } else {
                i++;
            }
        }

        return pairs >= 2;
    }

    // Check for size
    static boolean size_validity(List<Character> psswd) {
        return psswd.size() == 8;
    }

    // Convert List of String to List to Char
    static List<Character> convertToChar(String str) {
        List<Character> charList = new ArrayList<>();

        for (char ch: str.toCharArray()) {
            charList.add(ch);
        }

        return charList;
    }

    // Convert List of Character to a single String
    static String stringConverter(List<Character> psswd) {
        StringBuilder sb = new StringBuilder();
        for (char c : psswd) {
            sb.append(c);
        }
        return sb.toString();
    }
}
