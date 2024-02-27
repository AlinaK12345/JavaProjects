package markup;

import java.util.List;

public abstract class ListElement implements ElementsContainers {

    protected List<ElementsList> list;

    protected ListElement(List<ElementsList> list) {
        this.list = list;
    }

    public void toBBCode(StringBuilder a) {
        a.append(openTeg());
        for (ElementsList element : list) {
            element.toBBCode(a);
        }
        a.append(closeTeg());
    }

    abstract protected String openTeg();
    abstract protected String closeTeg();
}
