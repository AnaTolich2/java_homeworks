import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
    public static boolean isPartOfWord(int x){
        return Character.isLetter(x) || x == '\'' || Character.getType(x) == Character.DASH_PUNCTUATION;
    }
    public static void main(String[] args) {
        try {
            try (Reader fr = new FileReader(args[0], StandardCharsets.UTF_8)) {
                MyScan sc = new MyScan(fr);
                LinkedHashMap<String, Integer> list = new LinkedHashMap<>();
                String word;
                while (sc.hasNext()) {
                    word = sc.next(WordStatInput::isPartOfWord).toLowerCase();
                    if (!word.isEmpty()) {
                        list.put(word, list.getOrDefault(word, 0) + 1);
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                    for (Map.Entry<String, Integer> item : list.entrySet()) {
                        writer.write(item.getKey() + " " + item.getValue());
                        writer.newLine();
                    }
                }
            }

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

