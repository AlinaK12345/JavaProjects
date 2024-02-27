package markup;

public interface Element {
    void toMarkdown(StringBuilder a);

    void toBBCode(StringBuilder a);
}
