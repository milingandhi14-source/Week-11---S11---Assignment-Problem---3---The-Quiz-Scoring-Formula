
import java.util.Stack;

public class q3 {

    public static int evaluate(String expr) {

        Stack<Integer> operand = new Stack<>();
        Stack<Character> operator = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {

            char ch = expr.charAt(i);

            // Ignore spaces
            if (ch == ' ') {
                continue;
            }

            // Read multi-digit numbers
            if (Character.isDigit(ch)) {

                int num = 0;

                while (i < expr.length() &&
                       Character.isDigit(expr.charAt(i))) {

                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }

                i--;
                operand.push(num);
            }

            // Opening bracket
            else if (ch == '(') {

                operator.push(ch);
            }

            // Closing bracket
            else if (ch == ')') {

                while (!operator.isEmpty() &&
                       operator.peek() != '(') {

                    calculate(operand, operator);
                }

                operator.pop();
            }

            // Operators: +, -, *, /
            else if (ch == '+' || ch == '-' ||
                     ch == '*' || ch == '/') {

                while (!operator.isEmpty() &&
                       operator.peek() != '(' &&
                       precedence(operator.peek()) >= precedence(ch)) {

                    calculate(operand, operator);
                }

                operator.push(ch);
            }
        }

        // Apply remaining operators
        while (!operator.isEmpty()) {

            calculate(operand, operator);
        }

        return operand.pop();
    }

    // Return operator precedence
    public static int precedence(char op) {

        if (op == '*' || op == '/') {
            return 2;
        }

        if (op == '+' || op == '-') {
            return 1;
        }

        return 0;
    }

    // Perform calculation using two operands
    public static void calculate(Stack<Integer> operand,
                                 Stack<Character> operator) {

        int b = operand.pop();
        int a = operand.pop();

        char op = operator.pop();

        int result = 0;

        switch (op) {

            case '+':
                result = a + b;
                break;

            case '-':
                result = a - b;
                break;

            case '*':
                result = a * b;
                break;

            case '/':
                result = a / b;
                break;
        }

        operand.push(result);
    }

    public static void main(String[] args) {

        String expr1 = "2 * (3 + 4) - 10 / 5";
        String expr2 = "(8 - 2) * (1 + 2) / 4";

        System.out.println("Expression: " + expr1);
        System.out.println("Result: " + evaluate(expr1));

        System.out.println();

        System.out.println("Expression: " + expr2);
        System.out.println("Result: " + evaluate(expr2));
    }
}
