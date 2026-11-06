
import java.util.Scanner;

public class BadTreap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int st = -250000;
        for (int i = 0; i < n; i++) {
            System.out.println((st+i)*710);
        }
    }
}
