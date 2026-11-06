import java.util.Arrays;

public class ReverseMaxAbsOctDec {
    static int[] addlen1(int[] mas, int len) {
        if (len > mas.length) {
            int[] b = Arrays.copyOf(mas, (len*3)/2);
            return b;
        } else {
            return mas;
        }
    }
    public static int toDec(String bin){
        int res = 0;
        int i = bin.length()-1;
        while  (i >=0){
            res += ((bin.charAt(i)-48) << ((bin.length()-1 - i)*3));
            i--;
        }
        return res;
    }
    public static void main(String[] args) {
        String cur;
        int value = 0;
        int[] listoflen = new int[1];
        int[] mxLines = new int[2];
        int[] mxCols = new int[2];
        MyScan sc = new MyScan(System.in);
        int lenl = 1, lens = 1;
        listoflen = addlen1(listoflen, lenl);
        while (sc.hasNext()) {
            if (sc.isPassedNewLine()) {
                lenl++;
                lens = 1;
                mxLines = addlen1(mxLines, lenl);
                listoflen = addlen1(listoflen, lenl);
            }
            String p = sc.next();
            if (!p.isBlank()){
                mxCols = addlen1(mxCols, lens);
                if (p.charAt(p.length() - 1) == 'o' || p.charAt(p.length() - 1) == 'O') {
                    value = toDec(p.substring(0, p.length() - 1));
                } else {
                    value = Integer.parseInt(p);
                }
                if (Math.abs(value) > Math.abs(mxLines[lenl - 1])) {
                    mxLines[lenl - 1] = value;
                }
                if (Math.abs(value) > Math.abs(mxCols[lens - 1])) {
                    mxCols[lens - 1] = value;
                }
                lens++;
            }
            listoflen[lenl - 1] = lens - 1;
        }
        for (int j = 0; j < lenl; j++) {
            if (listoflen[j] != 0) {
                for (int t = 0; t < listoflen[j]; t++) {
                    if( Math.abs(mxLines[j]) > Math.abs(mxCols[t])) {
                        System.out.print(Integer.toOctalString( mxLines[j]) + "O" + " ");
                    } else {
                        System.out.print(Integer.toOctalString( mxCols[t]) + "O" + " ");
                    }
                }
            }
            System.out.println();
        }

    }
}