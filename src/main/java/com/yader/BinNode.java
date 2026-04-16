package com.yader;

import java.util.Map;

/**
 * Узел дерева для бинарной операции (+, -, *, /, ^).
 */
public class BinNode implements INode {

    private final INode left;
    private final String operator;
    private final INode right;

    /**
     * Создаёт узел бинарной операции.
     *
     * @param left     левый операнд
     * @param operator оператор (+, -, *, /, ^)
     * @param right    правый операнд
     */
    public BinNode(INode left, String operator, INode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    /**
     * Вычисляет результат бинарной операции.
     *
     * @param vars словарь переменных
     * @return результат операции
     * @throws ArithmeticException      при делении на ноль
     * @throws IllegalArgumentException при неизвестном операторе
     */
    @Override
    public double calc(Map<String, Double> vars) {
        double l = left.calc(vars);
        double r = right.calc(vars);
        return switch (operator) {
            case "+" -> l + r;
            case "-" -> l - r;
            case "*" -> l * r;
            case "/" -> {
                if (r == 0) throw new ArithmeticException("Division by zero");
                yield l / r;
            }
            case "^" -> Math.pow(l, r);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}