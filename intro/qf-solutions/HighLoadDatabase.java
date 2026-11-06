

import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.Scanner;

public class HighLoadDatabase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int am = 0;
        int sum = 0;
        int b = 0;
        HashMap<Integer, String> ready = new HashMap<>();
        int[] queries = new int[n];
        int[] lastInd = new int[n];
        for (int i = 0; i < n; i++) {
            queries[i] = sc.nextInt();
            am = Math.max(am, queries[i]);
            sum += queries[i];
            lastInd[i] = sum;
        }
        int prev = 0;
        int[] timeline = new int[sum];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < queries[i]; j++) {
                timeline[prev] = i;
                prev++;
            }
        }
        int q = sc.nextInt();
        String[] results = new String[q];
        for (int i = 0; i < q; i++) {
            int t = sc.nextInt();
            if (t < am){
                results[i] = "Impossible";
            } else {
                int k = 0;
                b = 0;
                if (!ready.containsKey(t)) {
                    while (b != sum) {
                        if (k == 0) {
                            if (t < sum) {
                                b = lastInd[timeline[t] - 1];
                            } else {
                                b = sum;
                            }
                        } else {
                            if (t + b < sum) {
                                b = lastInd[timeline[t + b] - 1];
                            } else {
                                b = sum;
                            }
                        }
                        k++;
                    }
                    results[i] = String.valueOf(k);
                    ready.put(t, String.valueOf(k));
                } else {
                    results[i] = ready.get(t);
                }
            }
        }
        for (int i = 0; i < q; i++) {
            System.out.println(results[i]);
        }
    }
}

