package markup;

import java.util.List;

public abstract class AbstractMarkupObject extends AbstractInlineObject {
    protected List<AbstractInlineObject> list;
    public AbstractMarkupObject(List<AbstractInlineObject> text){
        this.list=(text);
    }
    public void  toMarkdown(StringBuilder string){
        markupImpl(string);
        for (AbstractInlineObject o : list) {
            StringBuilder p = new StringBuilder();
            o.toMarkdown(p);
            string.append(p);
        }
        markupImpl(string);
    }
    public void  toDocBook(StringBuilder string){
        markupDoc1(string);
        for (AbstractInlineObject o : list) {
            StringBuilder p = new StringBuilder();
            o.toDocBook(p);
            string.append(p);
        }
        markupDoc2(string);
    }
    public void toHtml(StringBuilder string){
        markupHtml1(string);
        for (MarkupElement o : list) {
            StringBuilder p = new StringBuilder();
            o.toHtml(p);
            string.append(p);
        }
        markupHtml2(string);
    }
    protected AbstractInlineObject tryParse(String symbol, StringBuilder paragraph, int pointer){
        String end = endSymbol(symbol);
        StringBuilder result = new StringBuilder();
        while (pointer < paragraph.length()){
            if (pointer <= paragraph.length()-end.length()){
                if (paragraph.substring(pointer, pointer+end.length()).equals(end)){
                    return convert(result.toString());
                }
            }
            pointer++;
        }
        return new Text(result.toString());
    }
    protected AbstractInlineObject convert(String result){
        return new Text(result);
    }
    protected String endSymbol(String symbol){
        return "";
    }
    protected void markupHtml1(StringBuilder string){
    }
    protected void markupHtml2(StringBuilder string){
    }
    protected void markupImpl(StringBuilder string){
    }
    protected void markupDoc1(StringBuilder string){
    }
    protected void markupDoc2(StringBuilder string){
    }
}
