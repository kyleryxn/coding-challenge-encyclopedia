public class Solution {
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";

        // Step 1: Preprocess the input string to insert delimiters
        StringBuilder transformed = new StringBuilder("^");
        for (char character : s.toCharArray()) {
            transformed.append("#").append(character);
        }
        transformed.append("#$");

        char[] processedChars = transformed.toString().toCharArray();
        int processedLength = processedChars.length;

        int[] palindromeRadii = new int[processedLength]; // Lengths of palindromes centered at each index
        int currentCenter = 0;  // Center of the current longest palindrome
        int rightBoundary = 0;  // Right boundary of the current longest palindrome

        // Step 2: Loop through the processed characters
        for (int position = 1; position < processedLength - 1; position++) {
            int mirrorPosition = 2 * currentCenter - position;

            // Initialize radius using mirror value if within right boundary
            if (position < rightBoundary) {
                palindromeRadii[position] = Math.min(rightBoundary - position, palindromeRadii[mirrorPosition]
                );
            }

            // Expand palindrome centered at current position
            while (processedChars[position + (1 + palindromeRadii[position])] ==
                   processedChars[position - (1 + palindromeRadii[position])]) {
                palindromeRadii[position]++;
            }

            // Update center and boundary if the palindrome expanded past current boundary
            if (position + palindromeRadii[position] > rightBoundary) {
                currentCenter = position;
                rightBoundary = position + palindromeRadii[position];
            }
        }

        // Step 3: Find longest palindrome info
        int maxRadius = 0;
        int centerOfLongest = 0;
        for (int i = 1; i < processedLength - 1; i++) {
            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                centerOfLongest = i;
            }
        }

        // Step 4: Calculate start index in original string and return result
        int originalStartIndex = (centerOfLongest - maxRadius) / 2;
        return s.substring(originalStartIndex, originalStartIndex + maxRadius);
    }
}
