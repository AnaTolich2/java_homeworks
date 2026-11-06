package markup;

import java.util.List;

public class UnorderedList extends Lists {
    public UnorderedList(List<ListItem> list){
        super(list);
    }
    @Override
    public void toMarkdown(StringBuilder string) {
        return;
    }
    @Override
    public void toHtml(StringBuilder string) {
        return;
    }
    public void markupDoc1(StringBuilder sb){
        sb.append("<itemizedlist>");
    }
    public void markupDoc2(StringBuilder sb){
        sb.append("</itemizedlist>");
    }
}
