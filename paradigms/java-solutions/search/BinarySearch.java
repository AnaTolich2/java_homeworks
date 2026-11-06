package search;

public class BinarySearch {
    //Pred: a.length % 2 == 0 && for each 0 < i < a.length - 1, a[i] >= a[i + 1]
    //Post: (a[R - 1] > x || R == 1) && (a[R] <= x || R == a.length)
    public static int iterativeBinarySearch(String[] a, int x) {
        // true
        int l = 1, r = a.length;
        // (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length)
        while (l < r) {
            // (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length) && l' - r' < 0
            int m = (l + r) / 2;
            // (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length) && l' - r' < 0
            if (Integer.parseInt(a[m]) > x) {
                // a[m'] > x && (a[r'] <= x || r' == a.length)
                l = m + 1;
                // (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length)
                // assuming r' = l' + d', m' + 1 > l' <=> l' + (d' / 2) + 1 > l' <=> (d' / 2) + 1 > 0 -
                // true for any values of l', d'. So l' always increases
            } else {
                // (a[l'- 1] > x || l' == 1) && a[m'] <= x
                r = m;
                // (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length)
                // assuming r' = l' + d', m' < r' <=> l' + (d' / 2) < l' + d' <=> 0 < (d' / 2) -
                // is false only for d' = 1 (d' > 0, as l' < r'), but when r' = l' + 1
            }
            // (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length)
        }
        // l' == r' && (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length), so ->
        // l' == r' && (a[l'- 1] > x || l' == 1) && (a[l'] <= x || l' == a.length)
        return l;
    }

    //Pred: a.length % 2 == 1 && for each 0 < i < a.length - 1, a[i] >= a[i + 1] && l <= r
    // && (a[l'- 1] > x || l' == 0) && (a[r'] <= x || r' == a.length)
    //Post: (a[R - 1] > x || R == 1) && (a[R] <= x || R == a.length)
    public static int recursiveBinarySearch(String[] a, int x, int l, int r) {
        //(a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length) && l <= r
        if (l == r) {
            //l' == r' && (a[l'- 1] > x || l' == 1) && (a[l'] <= x || l' == a.length)
            return l;
        } //else
        // l < r && (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length)
        int m = (l + r) / 2;
        // l < r && (a[l'- 1] > x || l' == 1) && (a[r'] <= x || r' == a.length)
        if (Integer.parseInt(a[m]) > x) {
            // l < r && (a[m'] > x) && (a[r'] <= x || r' == a.length)
            return recursiveBinarySearch(a, x, m + 1, r);
        } else {
            // l < r && (a[l' - 1] > x || l' == 1) && (a[m'] <= x)
            return recursiveBinarySearch(a, x, l, m);
        }
    }

    //Pred: args.length > 0 && for each i, a[i] can be parsed into number
    //Post: displays such R, that R == 0 && a[R] <= x || R == args.length - 1 && a[R - 1] > x || a[R] <= x && a[R - 1] > x
    public static void main(String[] args) {
        //true
        int x = Integer.parseInt(args[0]);
        //true
        if (args.length % 2 == 0) {
            //args.length % 2 == 0
            System.out.println(iterativeBinarySearch(args, x) - 1);
        } else {
            //args.length % 2 != 0
            System.out.println(recursiveBinarySearch(args, x, 1, args.length) - 1);
        }
    }
}

