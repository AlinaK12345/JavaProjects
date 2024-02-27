package markup;

public class Text implements Element {
    String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder a) {
        a.append(text);
    }

    public void toBBCode(StringBuilder a) {
        a.append(text);
    }

}
