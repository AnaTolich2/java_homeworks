public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        int end;
        for (String arg : args) {
            int start = 0;
            while (start < arg.length()) {
                if (!Character.isWhitespace(arg.charAt(start)) && !(Character.getType(arg.charAt(start)) == 21) && !(Character.getType(arg.charAt(start)) == 22)) {
                    end = start;
                    while (end < arg.length() && !Character.isWhitespace(arg.charAt(end))) {
                        end++;
                    }
                    sum += Integer.parseInt(arg.substring(start, end));
                    start = end;
                }
                start += 1;
            }
        }
        System.out.println(sum);
    }
}