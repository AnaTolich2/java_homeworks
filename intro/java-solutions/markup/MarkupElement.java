package markup;

public interface MarkupElement {
    void toMarkdown(StringBuilder string);
    void toDocBook(StringBuilder string);
    void toHtml(StringBuilder string);
}
