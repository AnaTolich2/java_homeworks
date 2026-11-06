package markup;
import java.util.Collection;
import java.util.List;
public class Paragraph extends AbstractHOObject{
    List<AbstractInlineObject> list;
    public Paragraph(List<AbstractInlineObject> list){
        this.list = list;
    }
    public void  toMarkdown(StringBuilder string){
        markupImpl(string);
        for (MarkupElement o : list) {
            StringBuilder p = new StringBuilder();
            o.toMarkdown(p);
            string.append(p);
        }
        markupImpl(string);
    }

    protected void markupImpl(StringBuilder string){
    }

    public void  toDocBook(StringBuilder string){
        markupDoc1(string);
        for (MarkupElement o : list) {
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
    private void markupDoc1(StringBuilder string){
        string.append("<para>");
    }
    private void markupDoc2(StringBuilder string){
        string.append("</para>");
    }
    private void markupHtml1(StringBuilder string){
        string.append("<p>");
    }
    private void markupHtml2(StringBuilder string){
        string.append("</p>");
    }
}
