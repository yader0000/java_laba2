package com.yader;

import java.util.Map;

/**
 * Узел дерева для математической функции (sin, cos, sqrt и др.).
 */
public class FuncNode implements INode {

    private final String name;
    private final INode arg;

    /**
     * Создаёт узел функции.
     *
     * @param name имя функции
     * @param arg  аргумент функции
     */
    public FuncNode(String name, INode arg) {
        this.name = name;
        this.arg = arg;
    }

    /**
     * Вычисляет значение функции.
     *
     * @param vars словарь переменных
     * @return результат функции
     * @throws IllegalArgumentException при неизвестной функции
     */
    @Override
    public double calc(Map<String, Double> vars) {
        double v = arg.calc(vars);
        return switch (name) {
            case "sin"  -> Math.sin(v);
            case "cos"  -> Math.cos(v);
            case "tan"  -> Math.tan(v);
            case "sqrt" -> Math.sqrt(v);
            case "log"  -> Math.log(v);
            case "exp"  -> Math.exp(v);
            case "abs"  -> Math.abs(v);
            default -> throw new IllegalArgumentException("Unknown function: " + name);
        };
    }
}