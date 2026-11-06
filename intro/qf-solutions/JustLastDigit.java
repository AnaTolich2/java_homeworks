
import java.util.Scanner;

public class JustLastDigit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String p = sc.nextLine();
        String[] output = new String[n];
        int[][] trails = new int[n][n];
        for (int i = 0; i < n; i++) {
            p = sc.nextLine();
            for (int j = 0; j < n; j++) {
                trails[i][j] = p.charAt(j) - '0';
            }
        }
        int k = 0;
        sc.close(); // :NOTE: сканнер не закрывается сканнер

        for (int i = 0; i < n; i++) {
            StringBuilder current = new StringBuilder("0".repeat(i + 1));
            k = i + 1;
            while (k < n) {
                if (trails[i][k] % 10 == 0) {
                    current.append("0");
                } else {
                    current.append("1");
                    for (int j = k + 1; j < n; j++) {
                        trails[i][j] = (trails[i][j] - trails[k][j]) % 10;
                    }
                }
                k++;
            }
            output[i] = current.toString();
        }
        for (int i = 0; i < n; i++) {
            System.out.println(output[i]);
        }
    }
}
