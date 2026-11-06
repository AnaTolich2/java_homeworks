package markup;
import java.util.List;
public class Emphasis extends AbstractMarkupObject {

    public Emphasis(List<AbstractInlineObject> list) {
        super(list);
    }
    @Override
    public void markupImpl(StringBuilder string) {
        string.append("*");
    }
    @Override
    public void markupDoc1(StringBuilder string){
        string.append("<emphasis>");
    }
    @Override
    public void markupDoc2(StringBuilder string){
        string.append("</emphasis>");
    }
    @Override
    public void markupHtml1(StringBuilder string){
        string.append("<em>");
    }
    @Override
    public void markupHtml2(StringBuilder string){
        string.append("</em>");
    }
}
