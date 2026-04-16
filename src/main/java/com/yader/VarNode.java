package com.yader;

import java.util.Map;

/**
 * Узел дерева для переменной (например, x).
 */
public class VarNode implements INode {

    private final String name;

    /**
     * Создаёт узел переменной.
     *
     * @param name имя переменной
     */
    public VarNode(String name) {
        this.name = name;
    }

    /**
     * Возвращает значение переменной из словаря.
     *
     * @param vars словарь переменных
     * @return значение переменной
     * @throws IllegalArgumentException если переменная не найдена
     */
    @Override
    public double calc(Map<String, Double> vars) {
        if (!vars.containsKey(name)) {
            throw new IllegalArgumentException("Unknown variable: " + name);
        }
        return vars.get(name);
    }
}