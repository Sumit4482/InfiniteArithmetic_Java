import java.util.Arrays;

public class Subtraction {
    /**
     * Function to pad the smaller number array with zeros
     * to make both arrays of equal length.
     * @param numberArray1 - The first number array.
     * @param numberArray2 - The second number array.
     */
    public static void padArrays(int[] numberArray1, int[] numberArray2) {
        // Determine the maximum length between the two arrays
        int maxLength = Math.max(numberArray1.length, numberArray2.length);

        // If numberArray1 is smaller, add leading zeros to numberArray1
        if (numberArray1.length < maxLength) {
            int[] paddedArray = new int[maxLength];
            System.arraycopy(numberArray1, 0, paddedArray, maxLength - numberArray1.length, numberArray1.length);
            Arrays.fill(numberArray1, 0, maxLength - numberArray1.length, 0);
            numberArray1 = paddedArray;
        } else if (numberArray2.length < maxLength) {
            // If numberArray2 is smaller, add leading zeros to numberArray2
            int[] paddedArray = new int[maxLength];
            System.arraycopy(numberArray2, 0, paddedArray, maxLength - numberArray2.length, numberArray2.length);
            Arrays.fill(numberArray2, 0, maxLength - numberArray2.length, 0);
            numberArray2 = paddedArray;
        }
    }

    /**
     * Function to subtract two digits with borrow.
     * @param digit1 - The first digit.
     * @param digit2 - The second digit.
     * @param borrow - The borrow from the previous subtraction.
     * @return An object containing the result digit and the borrow for the next subtraction.
     * @throws IllegalArgumentException If any input is invalid.
     */
    public static ResultAndBorrow subtractDigitsWithBorrow(int digit1, int digit2, int borrow) {
        // Validate input: digit1, digit2, and borrow must be non-negative
        if (digit1 < 0 || digit2 < 0 || borrow < 0) {
            throw new IllegalArgumentException("Invalid input: digit1, digit2, and borrow must be non-negative.");
        }

        // Validate input: digit1 and digit2 must be between 0 and 9
        if (digit1 > 9 || digit2 > 9 || borrow > 1) {
            throw new IllegalArgumentException("Invalid input: digit1, digit2 must be between 0 and 9.");
        }

        // Calculate the difference between digit1 and digit2 with borrow
        int difference = (digit1 != 0 ? digit1 : 0) - (digit2 != 0 ? digit2 : 0) - borrow;

        // Adjust difference and borrow if the result is negative
        if (difference < 0) {
            difference += 10;
            borrow = 1;
        } else {
            borrow = 0;
        }

        // Return the result digit and the borrow for the next subtraction
        return new ResultAndBorrow(difference, borrow);
    }

    /**
     * Function to find the index of the first non-zero digit in the result.
     * @param resultString - The result string.
     * @return The index of the first non-zero digit.
     */
    public static int findFirstNonZeroIndex(String resultString) {
        // Initialize the index of the first non-zero digit
        int index = 0;

        // Find the index of the first non-zero digit in the result string
        while (index < resultString.length() && resultString.charAt(index) == '0') {
            index++;
        }

        // Return the index of the first non-zero digit
        return index;
    }

    /**
     * Function to perform subtraction of two arrays representing numbers.
     * @param numberArray1 - The first number array.
     * @param numberArray2 - The second number array.
     * @return The subtraction result as a string.
     * @throws IllegalArgumentException If any input is invalid.
     */
    public static String subtract(int[] numberArray1, int[] numberArray2) {
        // Validate input: numberArray1 and numberArray2 must be arrays
        if (numberArray1 == null || numberArray2 == null) {
            throw new IllegalArgumentException("Invalid input: numberArray1 and numberArray2 must not be null.");
        }

        // Validate input: numberArray1 and numberArray2 must contain non-negative numbers
        if (Arrays.stream(numberArray1).anyMatch(num -> num < 0) || Arrays.stream(numberArray2).anyMatch(num -> num < 0)) {
            throw new IllegalArgumentException("Invalid input: numberArray1 and numberArray2 must contain non-negative numbers.");
        }

        // Pad the arrays to make them of equal length
        padArrays(numberArray1, numberArray2);

        // Determine the sign of the result by comparing the arrays digit by digit
        int comparison = Long.parseLong(Arrays.toString(numberArray1).replaceAll("[^0-9]", "")) >
                Long.parseLong(Arrays.toString(numberArray2).replaceAll("[^0-9]", "")) ? 1 : -1;
        if (Arrays.equals(numberArray1, numberArray2)) {
            return "0";
        }

        // If numberArray1 is smaller, swap numberArray1 and numberArray2
        if (comparison == -1) {
            int[] temp = numberArray1;
            numberArray1 = numberArray2;
            numberArray2 = temp;
        }

        // Initialize variables for the subtraction algorithm
        StringBuilder result = new StringBuilder(); // Resultant array
        int indexA = numberArray1.length - 1; // Index of the last digit in numberArray1
        int indexB = numberArray2.length - 1; // Index of the last digit in numberArray2
        int borrow = 0; // Borrow from the previous subtraction

        // Iterate through the arrays and subtract corresponding digits
        while (indexA >= 0 || indexB >= 0) {
            // Subtract digits with borrow for the current position
            ResultAndBorrow resultAndBorrow = subtractDigitsWithBorrow(
                    numberArray1[indexA], indexB >= 0 ? numberArray2[indexB] : 0, borrow);

            // Append the result digit to the result string
            result.insert(0, resultAndBorrow.resultDigit);

            // Update the borrow for the next subtraction
            borrow = resultAndBorrow.nextBorrow;

            // Move to the next position in both arrays
            indexA--;
            indexB--;
        }

        // Add negative sign if necessary
        if (comparison == -1) {
            result.insert(0, "-"); // Add negative sign to the beginning of the result string
        }

        // Find the index of the first non-zero digit
        int firstNonZeroIndex = findFirstNonZeroIndex(result.toString());

        // Return the substring starting from the first non-zero digit
        // (remove leading zeros)
        return firstNonZeroIndex == result.length() ? "0" : result.substring(firstNonZeroIndex);
    }

    // Helper class to hold result digit and borrow
    static class ResultAndBorrow {
        int resultDigit;
        int nextBorrow;

        ResultAndBorrow(int resultDigit, int nextBorrow) {
            this.resultDigit = resultDigit;
            this.nextBorrow = nextBorrow;
        }
    }

    /**
     * Function to test the 'subtraction' function with different scenarios.
     */
    public static void testingFunction() {
        try {
            System.out.println(subtract(new int[]{0, 1, 1}, new int[]{7, 7}));
            System.out.println(subtract(new int[]{1, 2, 3, 5}, new int[]{4, 5, 6}));
            System.out.println(subtract(new int[]{5}, new int[]{2, 1, 3}));
            System.out.println(subtract(new int[]{2, 1, 2}, new int[]{2, 1, 2}));
            System.out.println(subtract(new int[]{10, 5, 6}, new int[]{2, 1}));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        testingFunction();
    }
}