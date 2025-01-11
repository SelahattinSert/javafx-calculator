package com.example.calculator.util;

import java.util.Arrays;

/**
 * Utility class for performing mathematical calculations and validating expressions.
 */
public class CalculatorUtil {

    /**
     * Calculates the factorial of a given number.
     *
     * @param number The number for which the factorial is to be calculated.
     * @return The factorial of the given number.
     */
    public double factorial(double number) {
        double res = 1;
        for (double i = number; i > 0; i--) {
            res *= i;
        }
        return res;
    }

    /**
     * Validates the bracket structure in a mathematical expression.
     * Ensures that every opening bracket has a corresponding closing bracket and the order is correct.
     *
     * @param expression The mathematical expression to validate.
     * @return {@code true} if the brackets are balanced, {@code false} otherwise.
     */
    public boolean validateBrackets(String expression) {
        int openBrackets = 0;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                openBrackets++;
            } else if (ch == ')') {
                openBrackets--;
                if (openBrackets < 0) {
                    return false;
                }
            }
        }
        return openBrackets == 0;
    }

    /**
     * Validates the correctness of a mathematical expression.
     * Checks for proper usage of operators, digits, functions, and brackets.
     *
     * @param expression The mathematical expression to validate.
     * @return {@code true} if the expression is valid, {@code false} otherwise.
     */
    public boolean validateExpression(String expression) {
        if (!validateBrackets(expression)) {
            return false;
        }

        String validOperators = "+-*/^!";
        String validChars = "0123456789().";
        String[] validFunctions = {"sin", "cos", "tan", "sqrt"};
        char lastChar = ' ';
        boolean hasNumber = false;
        boolean decimalPointAllowed = true;

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (!(validOperators.indexOf(currentChar) >= 0 || validChars.indexOf(currentChar) >= 0 || Character.isLetter(currentChar))) {
                return false;
            }

            if (validOperators.indexOf(currentChar) >= 0 && validOperators.indexOf(lastChar) >= 0 && currentChar != '!') {
                return false;
            }

            if (Character.isLetter(currentChar)) {
                int startPos = i;
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    i++;
                }
                String func = expression.substring(startPos, i);
                if (!Arrays.asList(validFunctions).contains(func)) {
                    return false;
                }
                i--;
            }

            if (currentChar == '.') {
                if (!Character.isDigit(lastChar) || !decimalPointAllowed) {
                    return false;
                }
                decimalPointAllowed = false;
            }

            if (Character.isDigit(currentChar)) {
                hasNumber = true;
            }

            if (validOperators.indexOf(currentChar) >= 0 || currentChar == '(' || currentChar == ')') {
                decimalPointAllowed = true;
            }

            lastChar = currentChar;
        }

        if (validOperators.indexOf(lastChar) >= 0 && lastChar != '!') {
            return false;
        }

        return hasNumber;
    }

    /**
     * Evaluates a mathematical expression and returns the result.
     * Supports basic arithmetic operations, trigonometric functions, square root, factorial, and exponentiation.
     *
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluated expression.
     * @throws RuntimeException If the expression is invalid or cannot be evaluated.
     */
    public double evaluateMathExpression(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else if (eat('!')) x = factorial(x);
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = expression.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    x = switch (func) {
                        case "sqrt" -> Math.sqrt(x);
                        case "sin" -> Math.sin(Math.toRadians(x));
                        case "cos" -> Math.cos(Math.toRadians(x));
                        case "tan" -> Math.tan(Math.toRadians(x));
                        default -> throw new RuntimeException("Unknown function: " + func);
                    };
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }
}
