public class SumLongPunct {
    public static void main(String[] args) {
        long sum = 0;
        int end;
        for (String arg : args) {
            int start = 0;
            while (start < arg.length()) {
                if (!Character.isWhitespace(arg.charAt(start))
                        && (Character.getType(arg.charAt(start)) != Character.START_PUNCTUATION)
                        && (Character.getType(arg.charAt(start)) != Character.END_PUNCTUATION)) {
                    end = start;
                    while (end < arg.length() && (Character.getType(arg.charAt(end)) != Character.START_PUNCTUATION)
                            && !Character.isWhitespace(arg.charAt(end))
                            && (Character.getType(arg.charAt(end)) != Character.END_PUNCTUATION)) {
                        end++;
                    }
                    sum += Long.parseLong(arg.substring(start, end));
                    start = end;
                }
                start += 1;
            }
        }
        System.out.println(sum);
    }
}