package markup;

import java.util.List;

public class OrderedList extends ListElement {
    public OrderedList(List<ElementsList> list){
        super(list);
    }
    protected String openTeg(){
        return "[list=1]";
    }
    protected String closeTeg(){
        return "[/list]";
    }

}
