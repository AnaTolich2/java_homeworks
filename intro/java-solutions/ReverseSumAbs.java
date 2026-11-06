import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseSumAbs {
    static int[] addlen1(int[] mas, int len) {
        if (len > mas.length) {
            int[] b = Arrays.copyOf(mas, (len*3)/2);
            return b;
        } else {
            return mas;
        }
    }

    static int[][] addlen2(int[][] mas, int len) {
        if (len > mas.length) {
            int[][] b = Arrays.copyOf(mas, (len*3)/2);
            return b;
        } else {
            return mas;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        String cur;
        int maxlen = 0;
        int value = 0;
        int[][] list = new int[1][];
        int[] listoflen = new int[1];
        int[] sumLines = new int[2];
        int[] sumcols = new int[2];
        list[0] = new int[2];
        Scanner sc = new Scanner(System.in);
        int lenl, lens;
        lenl = 0;
        while (sc.hasNextLine()) {
            lenl++;
            cur = sc.nextLine();
            lens = 1;
            list = addlen2(list, lenl);
            list[lenl - 1] = new int[2];
            sumLines = addlen1(sumLines, lenl);
            listoflen = addlen1(listoflen, lenl);
            Scanner curr = new Scanner(cur);
            while (curr.hasNext()) {
                list[lenl - 1] = addlen1(list[lenl - 1], lens);
                sumcols = addlen1(sumcols, lens);
                value = Math.abs(Integer.parseInt(curr.next()));
                sumLines[lenl - 1] += value;
                sumcols[lens - 1] += value;
                list[lenl - 1][lens - 1] = value;
                lens++;
            }
            maxlen = Math.max(lens - 1, maxlen);
            listoflen[lenl - 1] = lens - 1;
        }

        for (int j = 0; j < lenl; j++) {
            if (listoflen[j] != 0) {
                for (int t = 0; t < listoflen[j]; t++) {
                    System.out.print((sumLines[j] + sumcols[t] - list[j][t]) + " ");
                }
            }
            System.out.println();
        }
    }
}