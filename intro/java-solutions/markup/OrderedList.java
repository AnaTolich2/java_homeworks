package markup;

import java.util.List;

public class OrderedList extends Lists{
    public OrderedList(List<ListItem> list){
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
        sb.append("<orderedlist>");
    }
    public void markupDoc2(StringBuilder sb){
        sb.append("</orderedlist>");
    }
}
