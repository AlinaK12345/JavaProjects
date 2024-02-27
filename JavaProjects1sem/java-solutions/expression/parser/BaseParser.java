package expression.parser;

public class BaseParser {
    private static final char END = '\0';
    private CharSource source;
    private char ch;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take(){
        char result = ch;
        ch = source.hasNext() ? source.next() : END;
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
            take();
        }
    }

    protected boolean testDigit(){
        return Character.isDigit(ch);
    }

    protected boolean testVariable(){
        return ch == 'x' || ch == 'y' || ch == 'z';
    }
}
