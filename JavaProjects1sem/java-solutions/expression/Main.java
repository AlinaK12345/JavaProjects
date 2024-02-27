package expression;

import expression.parser.ExpressionParser;

public class Main {

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        //String str = " 1000000*x*x*x*x*x/(x-1)  ";
        //System.out.println(new ExpressionParser().parse(str).evaluate(1));
        System.out.println(new Add( new Subtract(
                new Multiply(new Variable("x"), new Variable( "x")),
                new Multiply( new Const(2), new Variable( "x"))),
                new Const(1)).evaluate(x));

    }
}
