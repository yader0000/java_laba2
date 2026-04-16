package com.yader;

import java.util.Map;

/**
 * Узел дерева для хранения числового значения.
 */
public class NumNode implements INode {

    private final double value;

    /**
     * Создаёт узел с заданным числом.
     *
     * @param value числовое значение
     */
    public NumNode(double value) {
        this.value = value;
    }

    /**
     * Возвращает числовое значение узла.
     *
     * @param vars словарь переменных (не используется)
     * @return числовое значение
     */
    @Override
    public double calc(Map<String, Double> vars) {
        return value;
    }
}