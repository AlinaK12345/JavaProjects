package markup;

import java.util.List;

abstract class MarkupElement extends AbstractElement implements Element {
    public MarkupElement(List<Element> list) {
        super(list);


    }

    public void toMarkdown(StringBuilder a) {
        a.append(mark());
        super.toMarkdown(a);
        a.append(mark());

    }
    public void toBBCode(StringBuilder a) {
        a.append("[").append(teg()).append("]");
        super.toBBCode(a);
        a.append("[/").append(teg()).append("]");
    }

    abstract protected String teg();
    abstract protected String mark();

}
