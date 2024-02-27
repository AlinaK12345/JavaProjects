package markup;


import java.util.List;

public class Strikeout extends MarkupElement {

    public Strikeout(List<Element> list) {
        super(list);
    }

    protected String mark() {
        return "~";
    }

    protected String teg() {
        return "s";
    }

}
