import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wspp {
    public static boolean isPartOfWord(int x){
        return Character.isLetter(x) || x == '\'' || Character.getType(x) == Character.DASH_PUNCTUATION;
    }
    public static void main(String[] args){
        Map<String, IntList> list = new LinkedHashMap<>();
        String source = args[0];
        String output = args[1];
        try (Reader reader = new FileReader(source, StandardCharsets.UTF_8)) {
            int read, end, i;
            MyScan sc = new MyScan(reader);
            int word_counter = 0;
            i = 0;
            String word;
            while (sc.hasNext()) {
                word = sc.next(Wspp:: isPartOfWord);
                if (!word.isEmpty()) {
                    word_counter++;
                    String key = word.toLowerCase();
                    if (list.containsKey(key)) {
                        IntList b = list.get(key);
                        b.addToIndex(0, 1);
                        b.append(word_counter);
                        list.put(key, b);
                    } else {
                        IntList b = new IntList();
                        b.append(1);
                        b.append(word_counter);
                        list.put(key, b);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found!" + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Failed to read input file" + e.getMessage());
            return;
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, IntList> item : list.entrySet()) {
                    writer.write(item.getKey() + " ");
                    for (int i = 0; i < item.getValue().size(); i++) {
                        writer.write(item.getValue().get(i) + "");
                        if (i != item.getValue().size()-1){
                            writer.write(" ");
                        }
                    }
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Unable to write" + e.getMessage());
                System.exit(1);
            }finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Invalid output file" + e.getMessage());
        }
    }

}
