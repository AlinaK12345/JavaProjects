package markup;


import java.util.List;

public class Strong extends MarkupElement {

    public Strong(List<Element> list) {
        super(list);
    }
    protected String mark(){
        return "__";
    }
    protected String teg(){
        return "b";
    }
}
