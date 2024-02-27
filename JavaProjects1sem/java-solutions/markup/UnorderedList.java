package markup;

import java.util.List;

public class UnorderedList extends ListElement {

    public UnorderedList(List<ElementsList> list){
        super(list);
    }

    protected String openTeg(){
        return "[list]";
    }
    protected String closeTeg(){
        return "[/list]";
    }
}
