/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab_01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md. Rayed Bin Wahed
 *         16141024
 *         CS 
 */
public class Lexer {

    private final String PATH = "C:\\Users\\moham\\Documents\\NetBeansProjects\\Compiler Labs\\src\\Lab_01\\input.txt";

    private final List<String> keywords;
    private final List<String> identifiers;
    private final List<String> math_operators;
    private final List<String> logical_operators;
    private final List<String> numeric_values;
    private final List<String> others;

    private final EnumMap<Type, List<String>> table;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Lexer().parse();
    }

    public Lexer() {
        this.keywords = new ArrayList<>();
        this.identifiers = new ArrayList<>();
        this.math_operators = new ArrayList<>();
        this.logical_operators = new ArrayList<>();
        this.numeric_values = new ArrayList<>();
        this.others = new ArrayList<>();
        
        this.table = new EnumMap<>(Type.class);
    }

    private void parse() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lexemes = line.split("\\s");
                for (int i = 0; i < lexemes.length; ++i) {
                    analyse(lexemes[i].trim());
                }
            }
            display();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analyse(String lexeme) {
        System.out.println(lexeme);
        switch (lexeme) {
            case "int":
            case "string":
            case "float":
            case "double":
            case "char":
            case "if":
            case "else":
                if (!keywords.contains(lexeme)) {
                    keywords.add(lexeme);
                }
                break;
            case "+":
            case "-":
            case "/":
            case "*":
            case "=":
                if (!math_operators.contains(lexeme)) {
                    math_operators.add(lexeme);
                }
                break;
            case ">":
            case "<":
            case "&&":
            case "||":
            case ">=":
            case "<=":
                if (!logical_operators.contains(lexeme)) {
                    logical_operators.add(lexeme);
                }
                break;
            default:
                String[] lexemes = lexeme.split("(?=[;,)])");
                for (String lexeme1 : lexemes) {
                    if (lexeme1.matches("\\d+(.)\\d+|\\d")) {
                        if (!numeric_values.contains(lexeme1)) {
                            numeric_values.add(lexeme1);
                        }
                    }
                    if (lexeme1.matches("[a-zA-Z]")) {
                        if (!identifiers.contains(lexeme1)) {
                            identifiers.add(lexeme1);
                        }
                    }
                    if (lexeme1.matches("\\p{Punct}")) {
                        if (!others.contains(lexeme1)) {
                            others.add(lexeme1);
                        }
                    }
                }
        }
    }

    private void display() {
        table.put(Type.KEYWORDS, keywords);
        table.put(Type.IDENTIFIERS, identifiers);
        table.put(Type.MATHEMATICAL_OPERATORS, math_operators);
        table.put(Type.LOGICAL_OPERATORS, logical_operators);
        table.put(Type.NUMERIC_VALUES, numeric_values);
        table.put(Type.OTHERS, others);
        table.entrySet().stream().forEach((entry) -> {
            Type key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println(key + ": " + value);
        });
    }

}
