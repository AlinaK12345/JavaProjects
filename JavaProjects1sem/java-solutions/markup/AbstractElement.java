package markup;


import java.util.List;

public abstract class AbstractElement {
    protected List<Element> list;

    protected AbstractElement(List<Element> list) {
        this.list = list;
    }

    public void toMarkdown(StringBuilder a) {
        for (Element element : list) {
            element.toMarkdown(a);
        }
    }

    public void toBBCode(StringBuilder a) {
        for (Element element : list) {
            element.toBBCode(a);
        }
    }


}
