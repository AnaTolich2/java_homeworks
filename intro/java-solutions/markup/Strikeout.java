package markup;
import java.util.List;
public class Strikeout extends AbstractMarkupObject {
    public Strikeout(List<AbstractInlineObject> list){
        super(list);
    }
    @Override
    public void markupImpl(StringBuilder string) {
        string.append("~");
    }
    @Override
    public void markupDoc1(StringBuilder string){
        string.append("<emphasis role='strikeout'>");
    }
    @Override
    public void markupDoc2(StringBuilder string){
        string.append("</emphasis>");
    }
    @Override
    public void markupHtml1(StringBuilder string){
        string.append("<s>");
    }
    @Override
    public void markupHtml2(StringBuilder string){
        string.append("</s>");
    }
    @Override
    protected AbstractInlineObject convert(String result){
        return new Text(result);
    }
}
