
import java.util.HashMap;
import java.util.Scanner;

public class ManagingDifficulties {
    public static void main(String[] args) {
        HashMap<Integer, Integer> array = new HashMap<>();
        int res;

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int[] results = new int[t];
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            int[] tempArr = new int[n];
            res = 0;
            for (int j = 0; j < n; j++) {
                tempArr[j] = sc.nextInt();
            }
            for (int j = n - 1; j >= 1; j--) {
                if (j != n - 1) {
                    for (int p = 0; p < j; p++) {
                        res += array.getOrDefault(2 * tempArr[j] - tempArr[p], 0);
                    }
                }
                array.put(tempArr[j], array.getOrDefault(tempArr[j], 0) + 1);
            }
            results[i] = res;
            array = new HashMap<>();
        }
        for (int i = 0; i < t; i++) {
            System.out.println(results[i]);
        }
    }
}
