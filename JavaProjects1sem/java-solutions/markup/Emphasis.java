package markup;

import java.util.List;

public class Emphasis extends MarkupElement {

    public Emphasis(List<Element> list){
        super(list);
    }

    protected String teg(){
        return "i";
    }
    protected String mark(){
        return "*";
    }

}
