public class JumbledNumbers {

    public String findDigits(String s) {
        int[] array = new int[10];
        StringBuilder answer = new StringBuilder();

        int size = s.length();

        for (int i = 0; i < size; i++) {
            if (s.charAt(i) == 'z')
                array[0]++;
            if (s.charAt(i) == 'w')
                array[2]++;
            if (s.charAt(i) == 'g')
                array[8]++;
            if (s.charAt(i) == 'x')
                array[6]++;
            if (s.charAt(i) == 'v')
                array[5]++;
            if (s.charAt(i) == 'o')
                array[1]++;
            if (s.charAt(i) == 's')
                array[7]++;
            if (s.charAt(i) == 'f')
                array[4]++;
            if (s.charAt(i) == 'h')
                array[3]++;
            if (s.charAt(i) == 'i')
                array[9]++;
        }

        // Update the elements of the vector
        array[7] -= array[6];
        array[5] -= array[7];
        array[4] -= array[5];
        array[1] -= (array[2] + array[4] + array[0]);
        array[3] -= array[8];
        array[9] -= (array[5] + array[6] + array[8]);

        for (int i = 0; i < 10; i++) {
            answer.append(String.valueOf((char) (i + '0')).repeat(Math.max(0, array[i])));
        }

        return answer.toString();
    }
}
