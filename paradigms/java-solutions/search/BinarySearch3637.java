package search;

public class BinarySearch3637 {
    //Pred: at least one i, that a[i] != a[i + 1] || a.length == 0
    //Post: (a[R] < a[R + 1] || R == a.length - 1) && (a[R - 1] > a[R] || R == 0)
    private static int findBoundary(String[] a) {
        //true
        int l = 1, r = a.length - 1;
        //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0)
        while (l < r) {
            //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0) && l' < r'
            int m = (l + r) / 2;
            //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0) && l' < r'
            if (Integer.parseInt(a[m]) < Integer.parseInt(a[m + 1])) {
                //(a[m'] < a[m' + 1]) && (a[l' - 1] > a[l'] || l' == 0)
                r = m;
                //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0)
            } else {
                //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[m'] > a[m' - 1])
                l = m + 1;
                //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0) ]
            }
            //(a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0)
        }
        //l' == r' && (a[r'] < a[r' + 1] || r' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0)
        //l' == r' && (a[l'] < a[l' + 1] || l' == a.length - 1) && (a[l' - 1] > a[l'] || l' == 0)
        return l;
    }

    //Pred: (for each i < a.length, a[i] < a[i + 1] && l < r && l == 0 && ascending) || (for each i < a.length, a[i] > a[i + 1] && l < r && l == 0 && !ascending)
    //Post: (a[R] == x) || (R == -1 && (x > a[l] || x < a[r]) && ascending) || (R == -1 && (x < a[l] || x > a[r]) && !ascending)
    private static int binarySearchUnited(String[] a, int x, int l, int r, boolean ascending) {
        //invar: ((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending) || ((a[l' - 1] < x || l' == 0) && (a[r'] > x) && !ascending)
        while (l < r) {
            // (((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending) || ((a[l' - 1] < x || l' == 0) && (a[r'] > x) && !ascending)) && l' < r'
            int m = (l + r) / 2;
            // (((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending) || ((a[l' - 1] < x || l' == 0) && (a[r'] > x) && !ascending)) && l' < r'
            if (ascending) {
                if (Integer.parseInt(a[m]) < x) {
                    // ((a[m'] < x) && (a[r'] > x) && ascending)
                    l = m + 1;
                    // ((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending)
                } else {
                    // ((a[l' - 1] < x || l' == 0) && (a[m'] > x) && ascending)
                    r = m;
                    // ((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending)
                }
            } else {
                if (Integer.parseInt(a[m]) > x) {
                    // ((a[m'] > x) && (a[r'] < x) && !ascending)
                    l = m + 1;
                    // (a[l' - 1] > x || l' == 0) && (a[r'] < x)
                } else {
                    // (a[l' - 1] > x || l' == 0) && (a[r'] < x)
                    r = m;
                    // (a[l' - 1] > x || l' == 0) && (a[r'] < x)
                }
            }
            // (((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending) || ((a[l' - 1] > x || l' == 0) && (a[r'] < x) && !ascending))
        }
        // (((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending) || ((a[l' - 1] > x || l' == 0) && (a[r'] < x) && !ascending)) && l' == r'
        if (Integer.parseInt(a[l]) == x) {
            // (((a[l' - 1] < x || l' == 0) && (a[r'] > x) && ascending) || ((a[l' - 1] > x || l' == 0) && (a[l'] < x) && !ascending)) && l' == r'
            return l;
        } else {
            // (((a[l' - 1] < x || l' == 0) && (a[l'] > x) && ascending) || ((a[l' - 1]  x || l' == 0) && (a[l'] < x) && !ascending)) && l' == r'
            return -1;
        }
    }

    //Pred: args.length > 0 && for each i, a[i] can be parsed into number
    //Post: displays such R, that a[R] == x || (R == -1 || !(x contains in a))
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int boundary = findBoundary(args);
        int index = binarySearchUnited(args, x, 1, boundary, false);
        if (index == -1) {
            index = binarySearchUnited(args, x, boundary, args.length - 1, true);
        }
        if (index != -1) {
            index--;
        }
        System.out.println(index);
    }
}