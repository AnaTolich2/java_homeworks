
import java.util.Scanner;

public class AccurateMovement {
    public static void main(String[] args) {
        int a,b,n;
        Scanner sc = new Scanner(System.in);
        a = sc.nextInt();
        b = sc.nextInt();
        n = sc.nextInt();
        if ((n-b)%(b-a) ==0) {
            System.out.println(2 * ((n - b) / (b - a)) + 1);
        } else {
            System.out.println(2 * (((n - b) / (b - a)) + 1) + 1);
        }
    }
}
