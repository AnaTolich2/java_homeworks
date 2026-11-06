import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class WordStatWordsSuffix
{
    public static boolean isPartOfWord(char x){
        if (Character.isLetter(x) || x == '\'' || Character.getType(x) == Character.DASH_PUNCTUATION){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        Reader fr = null;
        SortedMap<String, Integer> list = new TreeMap<>(Collections.reverseOrder());
        try {
            fr = new FileReader(args[0], StandardCharsets.UTF_8);
            try {
                int read, end, i;
                char[] buffer = new char[1024];
                read = fr.read(buffer);
                i = 0;
                StringBuilder word = new StringBuilder();
                while (i < read) {
                    if (isPartOfWord(buffer[i])) {
                        end = i;
                        while (end < read && (isPartOfWord(buffer[end]))) {
                            word.append(buffer[end]);
                            end++;
                            if (end >= read) {
                                read = fr.read(buffer);
                                end = 0;
                                if (read < 0) {
                                    break;
                                }
                            }
                        }
                        if (!word.isEmpty()) {
                            i = end;
                            String key = String.valueOf(word.substring(Math.max(0, word.length() - 3))).toLowerCase();
                            list.put(key,list.getOrDefault(key,0)+1);
                            word.setLength(0);
                        }
                    }
                    i++;
                    if (i >= read) {
                        read = fr.read(buffer);
                        i = 0;
                        if (read < 0) {
                            break;
                        }
                    }
                }

            } catch (IOException e) {
                System.err.println("Unable to read" + e.getMessage());
            } finally {
                fr.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found!" + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Failed to read input file" + e.getMessage());
            return;
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, Integer> item : list.entrySet()) {
                    writer.write(item.getKey() + " " + item.getValue());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Unable to write" + e.getMessage());
                System.exit(1);
            } // same
        } catch (FileNotFoundException e) {
            System.err.println("Invalid output file" + e.getMessage());
        } finally {
            if (writer!=null) {
                try {
                    writer.close();
                } catch (IOException e){
                    System.err.println("Can't close output file" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }
}
