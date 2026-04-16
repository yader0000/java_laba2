package com.yader;

import java.util.Map;

/**
 * Интерфейс узла дерева выражений.
 * Каждый узел умеет вычислять своё значение.
 */
public interface INode {
    /**
     * Вычисляет значение узла.
     *
     * @param vars словарь переменных и их значений
     * @return результат вычисления
     */
    double calc(Map<String, Double> vars);
}