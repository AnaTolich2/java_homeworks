
import java.util.Scanner;

public class IdealPyramid {
    public static int ceil(int x, int y){
        if (x % y ==0){
            return x/ y;
        } else {
            return x/y + 1;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n, xr, xl, yr, yl, x,y,h;
        xr = ((int)Math.pow(10,8))*(-1);
        xl = ((int)Math.pow(10,8));
        yl = ((int)Math.pow(10,8));
        yr = ((int)Math.pow(10,8))*(-1);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            x = sc.nextInt();
            y = sc.nextInt();
            h = sc.nextInt();
            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }
        System.out.println((xl+xr)/2 + " " + (yl + yr)/2 + " " + ceil(Math.max(xr - xl, yr - yl),2));
    }
}
