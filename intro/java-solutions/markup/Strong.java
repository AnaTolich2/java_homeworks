package markup;
import java.util.List;
public class Strong extends AbstractMarkupObject {
    public Strong(List<AbstractInlineObject> list){
        super(list);
    }
    @Override
    public void markupImpl(StringBuilder string) {
        string.append("__");
    }
    @Override
    public void markupDoc1(StringBuilder string){
        string.append("<emphasis role='bold'>");
    }
    @Override
    public void markupDoc2(StringBuilder string){
        string.append("</emphasis>");
    }
    @Override
    public void markupHtml1(StringBuilder string){
        string.append("<strong>");
    }
    @Override
    public void markupHtml2(StringBuilder string){
        string.append("</strong>");
    }
}
