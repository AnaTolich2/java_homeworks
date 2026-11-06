package md2html;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    static StringBuilder source;
    static StringBuilder result;
    static int pointer;
    static private boolean image;
    static List<String> last;
    static int stackLen;
    private final static String sp = "*_-`!])";
    private final static String imgS = "![]()";
    public static StringBuilder parse(StringBuilder source) {
        Paragraph.source = source;
        result = new StringBuilder();
        pointer = 0;
        stackLen = 0;
        last = new ArrayList<>();
        StringBuilder symbol = new StringBuilder();
        while (pointer < source.length()) {
            if (isSpecialSymbol(source.charAt(pointer))) {
                completeSymbol(symbol);
                String end = endSymbol(symbol.toString());
                if (!symbol.toString().equals("![")) {
                    expect(symbol.toString(), end);
                } else {
                    expectImage(symbol.toString());
                }
                symbol.setLength(0);
                pointer++;
            } else {
                if (source.charAt(pointer) != '\\') {
                    result.append(escapeHtml(source.charAt(pointer)));
                }
                pointer++;
            }
        }
        return result;
    }
    private static void completeSymbol(StringBuilder symbol){
        symbol.append(source.charAt(pointer));
        if (pointer < source.length() - 1 && isSSymbol(source.substring(pointer, pointer + 2))) {
            symbol.append(source.charAt(pointer + 1));
            pointer++;
        }
    }
    private static void expect(String symbol, String end){
        if (isSSymbol(symbol)) {
            if ((stackLen <= 0 || (!last.get(stackLen - 1).equals(symbol) && imageCheck(symbol)))&&fitsInstack()) {
                if (imgS.contains(symbol) && image || !imgS.contains(symbol) && !image) {
                    last.add(end);
                    stackLen++;
                    result.append(mkpS(symbol));
                } else {
                    result.append(symbol);
                }
            } else if (stackLen > 0 && last.get(stackLen - 1).equals(symbol)) {
                stackLen--;
                if (imageFirstPart(symbol)) {
                    pointer -= 2;
                }
                if (symbol.equals(")")){
                    image = false;
                }
                last.remove(stackLen);
                result.append(markupEnd(symbol));
            } else {
                result.append(symbol);
            }
        } else {
            result.append(symbol);
        }
    }
    private static void expectImage(String symbol){
        StringBuilder checker = new StringBuilder(symbol);
        int newPointer = pointer+1;
        StringBuilder newRes = new StringBuilder();
        while (newPointer < source.length() && (!checker.toString().equals(imgS))){
            if (isImgSymbol(source.charAt(newPointer))){
                checker.append(source.charAt(newPointer));
            }
            if (source.charAt(newPointer) == '\\'){
                break;
            }
            newRes.append(escapeHtml(source.charAt(newPointer)));
            newPointer++;
        }
        if (checker.toString().equals(imgS)){
            image = true;
            expect(symbol, endSymbol(symbol));
        } else {
            result.append(symbol);
        }
    }
    private static boolean fitsInstack(){
        return pointer >= source.length() - 1 || !Character.isWhitespace(source.charAt(pointer + 1));
    }
    private static boolean imageFirstPart(String symbol){
        return symbol.equals("](");
    }
    private static boolean imageCheck(String symbol){
        return  (pointer > 0 && image && !last.get(stackLen - 1).equals("](") && !last.get(stackLen - 1).equals(")")) || !imgS.contains(symbol);
    }
    private static boolean isSpecialSymbol(char x) {
        return (sp.contains(String.valueOf(x))) && (pointer < 1 || (source.charAt(pointer - 1) != '\\'));
    }
    private static boolean isImgSymbol(char x) {
        return (imgS.contains(String.valueOf(x))) && (pointer < 1 || (source.charAt(pointer - 1) != '\\'));
    }
    private static String mkpS(String str) {
        return switch (str) {
            case "*", "_" -> "<em>";
            case "**", "__" -> "<strong>";
            case "--" -> "<s>";
            case "`" -> "<code>";
            case "![" -> "<img alt='";
            case "](" -> " src='";
            default -> "";
        };
    }

    private static boolean isSSymbol(String str) {
        return switch (str) {
            case "*", "**", "--", "`", "_", "__", "![", "](", ")" -> true;
            default -> false;
        };
    }

    private static String endSymbol(String symbol) {
        return switch (symbol) {
            case "*", "**", "--", "`", "_", "__" -> symbol;
            case "![" -> "](";
            case "](" -> ")";
            default -> "";
        };
    }

    private static String escapeHtml(char c) {
        return switch (c) {
            case '<' -> "&lt;";
            case '>' -> "&Gt;";
            case '&' -> "&amp;";
            case '\"' -> "&quot;";
            default -> String.valueOf(c);
        };
    }
    private static String markupEnd(String str) {
        return switch (str) {
            case "*", "_" -> "</em>";
            case "**", "__" -> "</strong>";
            case "--" -> "</s>";
            case "`" -> "</code>";
            case "](" -> "'";
            case ")" -> "'>";
            default -> "";
        };
    }
}
