/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Md. Rayed Bin Wahed 16141024 CS
 */
public class Main {

    private final Map<String, Integer> symbolTable;
    private final ArrayList<Integer> results;
    private final List<String> operators;

    public Main() {
        symbolTable = new HashMap<>();
        results = new ArrayList<>();
        operators = Arrays.asList("+", "-", "*", "/");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().input();
    }

    private void input() {
        Scanner sc = new Scanner(System.in);
        //System.out.println("Input infix expression");
        //toPostFix(sc.nextLine());
        System.out.println("Enter the number of variables you want to declare and initialize:");
        int n = Integer.parseInt(sc.nextLine());
        System.out.println("Initialize using <identifier, space, equals sign, space, value>:");
        for (int i = 0; i < n; i++) {
            enterSymbolTable(sc.nextLine());
        }
        System.out.println("Enter the number of expressions you want to evaluate:");
        int m = Integer.parseInt(sc.nextLine());
        System.out.println("Enter expressions:");
        for (int i = 0; i < m; i++) {
            parse(sc.nextLine().replaceAll("\\s", ""));
        }
        display();
    }

    private void enterSymbolTable(String nextLine) {
        String[] s = nextLine.split("=");
        symbolTable.put(s[0].trim(), Integer.parseInt(s[1].trim()));
    }

    private void parse(String expression) {
        //System.out.println(expression);
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for (int i = 0; i < expression.length(); i++) {
            String s = String.valueOf(expression.charAt(i));
            //System.out.println(s);
            if (symbolTable.containsKey(s)) {
                sb.append(s);
            } else if (operators.contains(s)) {
                sb.append(s);
            } else {
                results.add(Integer.MAX_VALUE);
                flag = false;
                break;
            }
        }
        System.out.println(sb.toString());
        if (flag) {
            toPostFix(sb.toString());
        }
    }

    private void toPostFix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operator_stack = new Stack<>();
        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);
            if (Character.isAlphabetic(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                operator_stack.push(ch);
            } else if (ch == ')') {
                while (operator_stack.peek() != '(') {
                    postfix.append(operator_stack.pop());
                }
                operator_stack.pop();
            } else {
                while (!operator_stack.isEmpty() && !(operator_stack.peek() == '(') && precedence(ch) <= precedence(operator_stack.peek())) {
                    postfix.append(operator_stack.pop());
                }
                operator_stack.push(ch);
            }
        }
        while (!operator_stack.isEmpty()) {
            postfix.append(operator_stack.pop());
        }
        System.out.println(postfix);
        evaluate(postfix.toString());
    }

    private int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }

    }

    private void evaluate(String postfix) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < postfix.length(); ++i) {
            char ch = postfix.charAt(i);
            if (Character.isAlphabetic(ch)) {
                stack.push(symbolTable.get(String.valueOf(ch)));
            } else if (ch == '+') {
                stack.push(stack.pop() + stack.pop());
            } else if (ch == '-'){
                int n1 = stack.pop();
                int n2 = stack.pop();
                stack.push(n2 - n1);
            } else if (ch == '*'){
                stack.push(stack.pop() * stack.pop());
            } else {
                int n1 = stack.pop();
                int n2 = stack.pop();
                stack.push(n2 / n1);
            }
        }
        if (stack.isEmpty()){
            results.add(Integer.MAX_VALUE);
        } else {
            results.add(stack.pop());
        }
    }

    private void display() {
        System.out.println("Displaying results:");
        results.stream().forEach((r) -> {
            if (r == Integer.MAX_VALUE)
                System.out.println("Compilation Error!");
            else
                System.out.println(r);
        });
    }

}
