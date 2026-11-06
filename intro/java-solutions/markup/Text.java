package markup;

public class Text extends AbstractInlineObject {
    StringBuilder string;
    public Text(String string) {
        this.string =new StringBuilder(string);
    }
    public void  toMarkdown(StringBuilder s){
        s.append(string);
    }
    public void toDocBook(StringBuilder string) {
        string.append(this.string);
    }
    public void toHtml(StringBuilder string) {
        string.append(this.string);
    }
}
