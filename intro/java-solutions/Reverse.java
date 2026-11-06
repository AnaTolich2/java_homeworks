public class Reverse {
    static int[] addlen1(int[] mas, int len) {
        if (len > mas.length) {
            int[] b = new int[len * 2];
            System.arraycopy(mas, 0, b, 0, len - 1);
            return b;
        } else {
            return mas;
        }
    }

    static int[][] addlen2(int[][] mas, int len) {
        if (len > mas.length) {
            int[][] b = new int[len * 2][2];
            System.arraycopy(mas, 0, b, 0, len - 1);
            return b;
        } else {
            return mas;
        }
    }

    public static void main(String[] args) {
        int[][] list = new int[1][];
        int[] listoflen = new int[1];
        list[0] = new int[2];
        MyScan sc = new MyScan(System.in);
        int lenl = 1, lens = 1;
        listoflen = addlen1(listoflen, lenl);
        while (sc.hasNext()) {
            if (sc.isPassedNewLine()) {
                    listoflen[lenl - 1] = lens - 1;
                    lenl++;
                    lens = 1;
                    list = addlen2(list, lenl);
                    listoflen = addlen1(listoflen, lenl);
            }
            String p = sc.next();
            if (!p.isBlank()){
                list[lenl - 1][lens - 1] = Integer.parseInt(p);
                lens++;
            }
            if (lens > list[lenl - 1].length) {
                list[lenl - 1] = addlen1(list[lenl - 1], lens);
            }
        }
        listoflen[lenl - 1] = lens - 1;
        for (int j = lenl - 1; j > -1; j--) {
            if (listoflen[j] != 0) {
                for (int t = listoflen[j] - 1; t > -1; t--) {
                    System.out.print(list[j][t] + " ");
                }
            }
            System.out.println();
        }
    }
}
