package markup;

import java.util.List;

public class Code extends AbstractMarkupObject {
    public Code(List<AbstractInlineObject> list){
        super(list);
    }
    @Override
    public void markupImpl(StringBuilder string) {
    }
    @Override
    public void markupDoc1(StringBuilder string){
    }
    @Override
    public void markupDoc2(StringBuilder string){
    }
    @Override
    public void markupHtml1(StringBuilder string){
        string.append("<code>");
    }
    @Override
    public void markupHtml2(StringBuilder string){
        string.append("</code>");
    }
}
