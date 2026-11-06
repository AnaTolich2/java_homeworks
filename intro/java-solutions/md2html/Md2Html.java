package md2html;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
public class Md2Html {
    public static boolean isHeader(StringBuilder sb) {
        return (sb.charAt(0) == '#');
    }

    public static StringBuilder header(StringBuilder sb) {
        int i = 0;
        int cnt = 0;
        while (!Character.isWhitespace(sb.charAt(i))) {
            if (sb.charAt(i) == '#') {
                cnt++;
            } else {
                return notHeader(sb);
            }
            i++;
        }
        StringBuilder result = new StringBuilder("<h" + cnt + ">");
        result.append(sb, cnt + 1, sb.length() - System.lineSeparator().length());
        result.append("</h").append(cnt).append(">").append(System.lineSeparator());
        return result;
    }

    private static StringBuilder notHeader(StringBuilder sb) {
        StringBuilder result = new StringBuilder("<p>");
        result.append(sb, 0, sb.length() - System.lineSeparator().length());
        result.append("</p>").append(System.lineSeparator());
        return result;
    }
    public static void main(String[] args) {
        try (Reader reader = new FileReader(args[0], StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
                     (new FileOutputStream(args[1]), StandardCharsets.UTF_8));) {
            //note: close me
            Scanner sc = new Scanner(reader);
            StringBuilder paragraph = new StringBuilder();
            while (sc.hasNextLine()) {
                String cur = sc.nextLine();
                if (!cur.isEmpty()) {
                    paragraph.append(cur);
                    paragraph.append(System.lineSeparator());
                } else {
                    if (!paragraph.isEmpty()) {
                        if (isHeader(paragraph)) {
                            writer.write(header(Paragraph.parse(paragraph)).toString());

                        } else {
                            writer.write(notHeader(Paragraph.parse(paragraph)).toString());
                        }
                        paragraph.setLength(0);
                    }

                }
            }
            if (!paragraph.isEmpty()) {
                if (isHeader(paragraph)) {
                    writer.write(header(Paragraph.parse(paragraph)).toString());
                } else {
                    writer.write(notHeader(Paragraph.parse(paragraph)).toString());
                }
                paragraph.setLength(0);
            }
        //note: but what if input and ou[ut file are the same
        } catch (FileNotFoundException e) {
            System.err.println("Input/Output file not found!" + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to operate with input/output file" + e.getMessage());
        }
    }
}
