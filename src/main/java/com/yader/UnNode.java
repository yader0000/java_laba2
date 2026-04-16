package com.yader;

import java.util.Map;

/**
 * Узел дерева для унарного минуса (например, -5).
 */
public class UnNode implements INode {

    private final INode operand;

    /**
     * Создаёт узел унарного минуса.
     *
     * @param operand операнд
     */
    public UnNode(INode operand) {
        this.operand = operand;
    }

    /**
     * Возвращает отрицательное значение операнда.
     *
     * @param vars словарь переменных
     * @return отрицательное значение
     */
    @Override
    public double calc(Map<String, Double> vars) {
        return -operand.calc(vars);
    }
}