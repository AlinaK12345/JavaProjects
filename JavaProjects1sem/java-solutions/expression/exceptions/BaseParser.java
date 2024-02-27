package expression.exceptions;

public class BaseParser {
    private static final char END = '\0';
    private CharSource source;
    protected boolean space = false;
    private char ch;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take(){

        char result = ch;
        space = Character.isWhitespace(ch) || ch == '(' || ch == ')';
        ch = source.hasNext() ? source.next() : END;
        //space = Character.isWhitespace(ch) || ch == '(' || ch == ')';
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected char test(){
        return ch;
    }

    protected boolean eof() {
        return take(END);
    }

    protected void skipSpaces(){
        while (Character.isWhitespace(ch)){
            space = true;
            take();
        }
    }

    protected boolean testDigit(){
        return Character.isDigit(ch);/////////////////////
    }

    protected boolean testVariable(){
        return ch == 'x' || ch == 'y' || ch == 'z';
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }
}
