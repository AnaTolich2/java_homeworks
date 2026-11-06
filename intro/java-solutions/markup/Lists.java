package markup;

import java.util.List;
import java.util.Map;

public abstract class Lists extends AbstractHOObject{
    protected List<ListItem> list;
    public Lists(List<ListItem> list){
        this.list = list;
    }
    public void toDocBook(StringBuilder sb){
        markupDoc1(sb);
        for (ListItem o : list) {
            StringBuilder p = new StringBuilder();
            o.toDocBook(p);
            sb.append(p);
        }
        markupDoc2(sb);
    }
    protected void markupDoc1(StringBuilder sb){
    }
    protected void markupDoc2(StringBuilder sb){
    }
}
