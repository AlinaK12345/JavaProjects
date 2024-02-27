package expression.exceptions;

import expression.Add;
import expression.BitAnd;
import expression.Const;
import expression.Divide;
import expression.Elements;
import expression.Minus;
import expression.Multiply;
import expression.Or;
import expression.Subtract;
import expression.UnaryL1;
import expression.UnaryT1;
import expression.Variable;
import expression.Xor;
import expression.parser.TripleParser;

public class ExpressionParser implements TripleParser {

    public expression.Elements parse(String source){
        //System.out.println(source); ///////////////
        return parse(new StringSource(source));
    }

    public expression.Elements parse(CharSource source){
        return new Parser(source).myParse();

    }

    private static class Parser extends BaseParser {

        public int parenthesisCount = 0;
        String nextOperation = "";

        public Parser(CharSource source) {
            super(source);
        }

        public expression.Elements myParse(){
            expression.Elements result = parseElement();
            //System.out.println(parenthesisCount);
            if (parenthesisCount != 0){
                throw error("not correct (), no closing parenthesis");
            }
            if (eof()) {

                return result;
            }
            throw error("Expected END");

        }

        private expression.Elements parseElement() {
            skipSpaces();
            expression.Elements result = parseValue();
            skipSpaces();
            testCloseParenthesis();
            while (!take(')') && !eof()) {
                skipSpaces();
                String op = getOperation();// ((2 + 2) + (((2 * 3) * 3) * 3) + 2)
                skipSpaces();
                expression.Elements res2 = makeAction(getPriority(op));
                result = makeBinAction(result, res2, op);
            }
            //testCloseParenthesis();
            return result; // 5 / 5 / 5
        }

        private expression.Elements makeAction(int priority){
            skipSpaces();
            expression.Elements elem = parseValue();
            skipSpaces();
            if (eof() || test(')')){
                testCloseParenthesis();
                //take(')');////////////////////
                return elem;
            }
            String op = getOperation();
            while (getPriority(op) < priority){
                skipSpaces();
                expression.Elements exp = makeAction(getPriority(op));
                elem = makeBinAction(elem, exp, op);
                skipSpaces();
                if (eof() || test(')')){
                    //testCloseParenthesis();
                    return elem;
                }
                skipSpaces();
                op = getOperation();
            }
            this.nextOperation = op;
            return elem;
        }

        private int getPriority(String operation){
            return switch (operation){
                case "+", "-" -> 2;
                case "*", "/" -> 1;
                case "&" -> 3;
                case "^" -> 4;
                case "|" -> 5;
                case "min" -> 7;
                case "max" -> 7;
                default -> throw error("Not correct operation: \"" + operation + "\"");
            };
        }
        private expression.Elements makeBinAction(expression.Elements el1, expression.Elements el2, String operation){
            return switch (operation){
                case "+" -> new CheckedAdd(el1, el2);
                case "-" -> new CheckedSubtract(el1, el2);
                case "*" -> new CheckedMultiply(el1, el2);
                case "/" -> new CheckedDivide(el1, el2);
                case "&" -> new BitAnd(el1, el2);
                case "^" -> new Xor(el1, el2);
                case "|" -> new Or(el1, el2);
                case "min" -> new Min(el1, el2);
                case "max" -> new Max(el1, el2);
                default -> throw error("Not correct operation: \"" + operation + "\"");
            };
        }

        private boolean checkInAx(String a){
            int i = 1;
            while (i <= a.length() - 1 && take(a.charAt(i))){
                i++;
            }
            if (i == a.length()){
                //skipSpaces();

                return true;
            }
            return false;
        }
        private String checkMinMax(String oper){
            String a = "min";
            String b = "max";
            if (oper.equals("m")){
                if (checkInAx(a)){
                    return "min";
                }
                if (checkInAx(b)){
                    return "max";
                }
                throw error("not correct min/max operation: " + oper + test());
            }
            return oper;
        }

        private String getOperation(){
            String oper;
            if (!nextOperation.isEmpty()){
                oper = nextOperation;
                nextOperation = "";
            } else {
                boolean isSpace = space;
                oper = Character.toString(take());
                if (isSpace ) {
                    oper = checkMinMax(oper);
                }
            }
            return oper;
        }
        private expression.Elements parseValue(){
            int sign = 1; //
            expression.Elements result; ///

            skipSpaces();

            if (take('l') ){
                if (take('1')){
                    return new UnaryL1(parseValue());
                }
                else{
                    throw error("expected l1, found: " + test());
                }
            }
            if (take('t') ){
                if (take('1')){
                    return new UnaryT1(parseValue());
                }
                else{
                    throw error("expected t1, found: " + test());
                }
            }

            if (take('-')){
                sign = -1;
                if (testDigit()) {
                    return makeDigit(sign);
                } else {
                    return new CheckedNegate(parseValue());
                }

            }
            if (take('(')){
                parenthesisCount++;
                return parseElement();
            }
            if (testDigit()) {
                return makeDigit(1);
            }
            if (testVariable()){
                return new Variable(Character.toString(take()));
            }
            space = false;
            throw error("Not correct args; found: " + test());
        }

        private Elements makeDigit(int sign){
            StringBuilder ans = new StringBuilder();
            if (sign == -1){
                ans.append("-");
            }
            while (testDigit()){
                ans.append(take());
            }
            return new Const(Integer.parseInt(ans.toString()));
        }

        private void testCloseParenthesis(){
            if (test(')')){
                parenthesisCount --;
                if (parenthesisCount < 0){
                    throw error("not correct (), no opening parenthesis");
                }
            }
        }


    }
}
