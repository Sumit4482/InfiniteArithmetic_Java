import java.util.Arrays;

public class Addition {
    public static String addition(int[] arr1, int[] arr2) throws Exception {
        //To check if every number of the array is 0
        boolean allZeros1 = Arrays.stream(arr1).allMatch(num -> num == 0);
        boolean allZeros2 = Arrays.stream(arr2).allMatch(num -> num == 0);

        if (allZeros1 && allZeros2) {
            throw new Exception("Both input arrays contain all zeros.");
        }
        // Validate input arrays
        if (arr1.length == 0 || arr2.length == 0) {
            throw new Exception("Empty arrays are not allowed.");
        }

        // Resultant array to store the sum of digits
        StringBuilder res = new StringBuilder();

        // Initialize indices for iterating through the arrays
        int m = arr1.length - 1;
        int n = arr2.length - 1;

        // Initialize carry for addition
        int carry = 0;

        // Iterate through the arrays and add corresponding digits
        while (m >= 0 || n >= 0 || carry > 0) {
            // Calculate the sum of two digits and the carry
            int sum = (m >= 0 ? arr1[m] : 0) + (n >= 0 ? arr2[n] : 0) + carry;

            // Calculate the result digit
            int resultDigit = sum % 10;

            // Calculate the carry for the next addition
            carry = sum / 10;

            // Append the result digit to the result string
            res.append(resultDigit);

            // Move to the next position in both arrays
            m--;
            n--;
        }

        // Check if the length exceeds a threshold
        if (res.length() > 1000000) {
            throw new Exception("Array length overflow: The resulting sum array is too large.");
        }

        // Return the sum as a string, reversing the result string to get the correct order of digits
        return res.reverse().toString();
    }

    public static void main(String[] args) {
        testingFunction();
    }

    public static void testingFunction() {
        try {
            System.out.println(addition(new int[]{9, 9}, new int[]{0, 1, 1}));

            System.out.println(
                    addition(
                            new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                            new int[]{1}
                    )
            );

            System.out.println(addition(new int[]{0, 0, 0}, new int[]{0, 0, 0}));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}