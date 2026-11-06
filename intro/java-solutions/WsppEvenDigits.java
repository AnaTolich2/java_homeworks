import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppEvenDigits {
    public static boolean isWsppSpecial(int x) {
        return Character.isLetter(x) || x == '\'' || Character.getType(x) == Character.DASH_PUNCTUATION || Character.isDigit(x);
    }
    public static void main(String[] args){
        Map<String, IntList> list = new LinkedHashMap<>();
        IntList thisLine = new IntList();
        int difWords = 0;
        String source = args[0];
        String output = args[1];
        try (Reader reader = new FileReader(source, StandardCharsets.UTF_8)) {
            MyScan sc = new MyScan(reader);
            int wordCounter = 0;
            String word;
            while (sc.hasNext()) {
                word = sc.next(WsppEvenDigits::isWsppSpecial);
                if (sc.isPassedNewLine()) {
                    thisLine = new IntList(list.size());
                    wordCounter = 0;
                }
                if (!word.isEmpty()) {
                    wordCounter++;
                    String key = (word).toLowerCase();
                    if (list.containsKey(key)) {
                        IntList b = list.get(key);
                        b.addToIndex(1, 1);
                        thisLine.addToIndex(list.get(key).get(0), 1);
                        if (thisLine.get(list.get(key).get(0)) % 2 == 0) {
                            b.append(wordCounter);
                        }
                        list.put(key, b);
                    } else {
                        IntList b = new IntList();
                        b.append(difWords);
                        b.append(1);
                        thisLine.append(1);
                        list.put(key, b);
                        difWords++;
                    }
                }
            }
        }catch (FileNotFoundException e) {
            System.err.println("Input file not found!" + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Failed to read input file" + e.getMessage());
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, IntList> item : list.entrySet()) {
                    writer.write(item.getKey() + " ");
                    for (int i = 1; i < item.getValue().size(); i++) {
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