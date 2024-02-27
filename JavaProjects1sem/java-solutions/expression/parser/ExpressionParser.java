package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {

    public Elements parse(String source) {
        //System.out.println(source); ///////////////
        return parse(new StringSource(source));
    }

    public Elements parse(CharSource source) {
        return new Parser(source).myParse();

    }

    private static class Parser extends BaseParser {
        String nextOperation = "";

        public Parser(CharSource source) {
            super(source);
        }

        public Elements myParse() {
            Elements result = parseElement();
            if (eof()) {
                return result;
            }
            throw new RuntimeException("Expected END");
        }

        private Elements parseElement() {
            skipSpaces();
            Elements result = parseValue();
            skipSpaces();
            while (!take(')') && !eof()) {
                skipSpaces();
                String op = getOperation();     // ((2 + 2) + (((2 * 3) * 3) * 3) + 2)
                skipSpaces();
                Elements res2 = makeAction(getPriority(op));
                result = makeBinAction(result, res2, op);
            }
            return result; // 5 / 5 / 5
        }

        private Elements makeAction(int priority) {
            skipSpaces();
            Elements elem = parseValue();
            skipSpaces();
            if (eof() || test(')')) {
                return elem;
            }
            String op = getOperation();
            while (getPriority(op) < priority) {
                skipSpaces();
                Elements exp = makeAction(getPriority(op));
                elem = makeBinAction(elem, exp, op);
                if (eof() || test(')')) {
                    return elem;
                }
                skipSpaces();
                op = getOperation();
            }
            this.nextOperation = op;
            return elem;
        }

        private int getPriority(String operation) {
            return switch (operation) {
                case "+", "-" -> 2;
                case "*", "/" -> 1;
                case "&" -> 3;
                case "^" -> 4;
                case "|" -> 5;
                default -> throw new UnsupportedOperationException("Not correct operation: \"" + operation + "\"");
            };
        }

        private Elements makeBinAction(Elements el1, Elements el2, String operation) {
            return switch (operation) {
                case "+" -> new Add(el1, el2);
                case "-" -> new Subtract(el1, el2);
                case "*" -> new Multiply(el1, el2);
                case "/" -> new Divide(el1, el2);
                case "&" -> new BitAnd(el1, el2);
                case "^" -> new Xor(el1, el2);
                case "|" -> new Or(el1, el2);
                default -> throw new UnsupportedOperationException("Not correct operation: \"" + operation + "\"");
            };
        }

        private String getOperation() {
            String oper;
            if (!nextOperation.isEmpty()) {
                oper = nextOperation;
                nextOperation = "";
            } else {
                oper = Character.toString(take());
            }
            return oper;
        }

        private Elements parseValue() {
            int sign = 1; //
            Elements result; ///

            skipSpaces();

            if (take('l')) {
                if (take('1')) {
                    return new UnaryL1(parseValue());
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            if (take('t')) {
                if (take('1')) {
                    return new UnaryT1(parseValue());
                } else {
                    throw new UnsupportedOperationException();
                }
            }

            if (take('-')) {
                sign = -1;
                if (testDigit()) {
                    return makeDigit(sign);
                } else {
                    return new Minus(parseValue());
                }

                //return new Minus(parseValue()); ////???
            }
            if (take('(')) {
                return parseElement();
            }
            if (testDigit()) {
                return makeDigit(1);
            }
            if (testVariable()) {
                return new Variable(Character.toString(take()));
            }
            throw new UnsupportedOperationException("hjk");
        }

        private Elements makeDigit(int sign) {
            StringBuilder ans = new StringBuilder();
            if (sign == -1) {
                ans.append("-");
            }
            while (testDigit()) {
                ans.append(take());
            }
            return new Const(Integer.parseInt(ans.toString()));
        }


    }
}
