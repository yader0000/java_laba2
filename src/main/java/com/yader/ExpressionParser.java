package com.yader;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Парсер математических выражений.
 * Преобразует строку в дерево узлов для последующего вычисления.
 */
public class ExpressionParser {

    private String input;
    private int pos;

    private static final Set<String> FUNCTIONS = Set.of(
            "sin", "cos", "tan", "sqrt", "log", "exp", "abs"
    );

    /**
     * Извлекает имена переменных из выражения.
     *
     * @param expression входное выражение
     * @return множество имён переменных
     */
    public static Set<String> findVariables(String expression) {
        String cleaned = expression.replaceAll("\\s+", "");
        Set<String> vars = new HashSet<>();
        int i = 0;
        while (i < cleaned.length()) {
            if (Character.isLetter(cleaned.charAt(i))) {
                int start = i;
                while (i < cleaned.length() && Character.isLetter(cleaned.charAt(i))) {
                    i++;
                }
                String token = cleaned.substring(start, i);
                boolean isFunc = i < cleaned.length()
                        && cleaned.charAt(i) == '('
                        && FUNCTIONS.contains(token.toLowerCase());
                if (!isFunc) {
                    vars.add(token);
                }
            } else {
                i++;
            }
        }
        return vars;
    }

    /**
     * Разбирает выражение и строит дерево узлов.
     *
     * @param expression входное выражение
     * @return корень дерева узлов
     * @throws IllegalArgumentException если выражение некорректно
     */
    public INode parse(String expression) {
        this.input = expression.replaceAll("\\s+", "");
        this.pos = 0;
        INode result = parseExpression();
        if (pos < input.length()) {
            throw new IllegalArgumentException(
                    "Неожиданный символ: " + input.charAt(pos));
        }
        return result;
    }

    /**
     * Разбирает выражение со сложением и вычитанием.
     */
    private INode parseExpression() {
        INode node = parseTerm();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c == '+' || c == '-') {
                pos++;
                node = new BinNode(node, String.valueOf(c), parseTerm());
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * Разбирает термы с умножением и делением.
     */
    private INode parseTerm() {
        INode node = parsePower();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c == '*' || c == '/') {
                pos++;
                node = new BinNode(node, String.valueOf(c), parsePower());
            } else {
                break;
            }
        }
        return node;
    }

    /**
     * Разбирает возведение в степень.
     */
    private INode parsePower() {
        INode node = parsePrimary();
        if (pos < input.length() && input.charAt(pos) == '^') {
            pos++;
            node = new BinNode(node, "^", parsePower());
        }
        return node;
    }

    /**
     * Разбирает первичные элементы: числа, переменные, функции, скобки.
     *
     * @throws IllegalArgumentException если встречен неожиданный символ
     */
    private INode parsePrimary() {
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Неожиданный конец выражения");
        }
        char c = input.charAt(pos);
        if (Character.isDigit(c) || c == '.') {
            return parseNumber();
        }
        if (Character.isLetter(c)) {
            return parseIdentifier();
        }
        if (c == '(') {
            pos++;
            INode node = parseExpression();
            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new IllegalArgumentException("Ожидается закрывающая скобка");
            }
            pos++;
            return node;
        }
        if (c == '-') {
            pos++;
            return new UnNode(parsePrimary());
        }
        if (c == '+') {
            pos++;
            return parsePrimary();
        }
        throw new IllegalArgumentException("Неожиданный символ: " + c);
    }

    /**
     * Разбирает число (целое или дробное).
     */
    private INode parseNumber() {
        int start = pos;
        boolean hasDot = false;
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (Character.isDigit(c)) {
                pos++;
            } else if (c == '.' && !hasDot) {
                hasDot = true;
                pos++;
            } else {
                break;
            }
        }
        try {
            return new NumNode(Double.parseDouble(input.substring(start, pos)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Неверный формат числа: " + input.substring(start, pos));
        }
    }

    /**
     * Разбирает идентификатор: переменную или функцию.
     */
    private INode parseIdentifier() {
        int start = pos;
        while (pos < input.length() && Character.isLetter(input.charAt(pos))) {
            pos++;
        }
        String token = input.substring(start, pos);
        if (pos < input.length() && input.charAt(pos) == '(') {
            if (!FUNCTIONS.contains(token.toLowerCase())) {
                throw new IllegalArgumentException("Неизвестная функция: " + token);
            }
            pos++;
            INode arg = parseExpression();
            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new IllegalArgumentException("Ожидается ')' после аргумента функции");
            }
            pos++;
            return new FuncNode(token.toLowerCase(), arg);
        }
        return new VarNode(token);
    }
}