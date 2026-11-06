package markup;

import java.util.Collection;
import java.util.List;

public class ListItem {
    List<AbstractHOObject> list;
    public ListItem(List<AbstractHOObject> list){
        this.list = list;
    }



    public void toDocBook(StringBuilder sb){
        markupDoc1(sb);
        for (AbstractHOObject o : list) {
            StringBuilder p = new StringBuilder();
            o.toDocBook(p);
            sb.append(p);
        }
        markupDoc2(sb);
    }
    protected void markupDoc1(StringBuilder sb){
        sb.append("<listitem>");
    }
    protected void markupDoc2(StringBuilder sb){
        sb.append("</listitem>");
    }
}
