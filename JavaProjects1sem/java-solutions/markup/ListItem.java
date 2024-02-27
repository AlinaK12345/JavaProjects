package markup;

import java.util.List;

public class ListItem implements ElementsList {
    List<ElementsContainers> list;

    public ListItem(List<ElementsContainers> list) {
        this.list = list;
    }

    public void toBBCode(StringBuilder a) {
        a.append("[*]");
        for (ElementsContainers element : list) {
            element.toBBCode(a);
        }

    }
}
