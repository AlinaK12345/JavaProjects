package expression.exceptions;



public class Main {

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        //String str = "-2147483647 / -1 ";
        //System.out.println(new ExpressionParser().parse(str).evaluate(1, 1, 0));

        System.out.println(new CheckedAdd( new CheckedSubtract(
                new CheckedMultiply(new Variable("x"), new Variable( "x")),
                new CheckedMultiply( new Const(2), new Variable( "x"))),
                new Const(1)).evaluate(x));



    }
}
