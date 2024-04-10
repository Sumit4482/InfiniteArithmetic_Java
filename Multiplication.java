import java.util.Arrays;

public class Multiplication {
    public static String multiplyArrays(int[] numArr1, int[] numArr2) throws Exception {
        // Check for inputs
        if (numArr1.length == 0 || numArr2.length == 0 ||
                Arrays.stream(numArr1).anyMatch(num -> num < 0) ||
                Arrays.stream(numArr2).anyMatch(num -> num < 0)) {
            throw new Exception("Invalid input arrays");
        }

        // Reverse both arrays to simplify the multiplication process
        reverseArray(numArr1);
        reverseArray(numArr2);

        int maxLength = numArr1.length + numArr2.length;
        int[] result = new int[maxLength];

        // Initialize the result array with zeros
        for (int i = 0; i < maxLength; i++) {
            result[i] = 0;
        }

        // Perform multiplication
        for (int i = 0; i < numArr1.length; i++) {
            for (int j = 0; j < numArr2.length; j++) {
                // Multiply digits at position i of numArr1 and position j of numArr2
                // and add the result to position (i+j) in the result array
                result[i + j] += numArr1[i] * numArr2[j];
            }
        }

        // Perform carryover
        for (int i = 0; i < maxLength - 1; i++) {
            // Calculate the carryover by dividing the current digit by 10
            int carryOver = result[i] / 10;
            // Update the current digit to be the remainder of the division
            result[i] %= 10;
            // Add the carryover to the next digit in the result array
            result[i + 1] += carryOver;
        }

        // Remove leading zeros and reverse the result
        int i = maxLength - 1;
        while (i >= 0 && result[i] == 0) {
            i--; // Move to the next digit
        }

        if (i == -1) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        while (i >= 0) {
            sb.append(result[i--]);
        }

        return sb.toString();
    }

    // Helper method to reverse an array
    private static void reverseArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        testingFunction();
    }

    public static void testingFunction() {
        try {
            System.out.println(multiplyArrays(new int[]{9, 9, 9}, new int[]{1, 1, 1}));
            System.out.println(multiplyArrays(new int[]{1, 2, 3, 5}, new int[]{4, 5, 6}));
            System.out.println(multiplyArrays(new int[]{5}, new int[]{2, 1, 3}));
            System.out.println(multiplyArrays(new int[]{2, 1, 2}, new int[]{2, 1, 2}));
            System.out.println(multiplyArrays(new int[]{1, 2, 3}, new int[]{0}));

            System.out.println(multiplyArrays(
                    new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3},
                    new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 8, 9, 1, 2, 3}));
            System.out.println(multiplyArrays(
                    new int[]{3, 7, 5, 9, 1, 6, 2, 3, 2, 3},
                    new int[]{6, 8, 0, 2, 5, 3, 8, 1, 2}));
            System.out.println(multiplyArrays(new int[]{}, new int[]{}));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
