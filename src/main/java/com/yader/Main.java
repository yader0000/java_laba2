package com.yader;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Точка входа программы-калькулятора.
 * Программа работает в цикле: принимает математическое выражение,
 * запрашивает значения переменных если они есть,
 * вычисляет результат и выводит его на экран.
 * Для выхода введите 'exit'.
 */
public class Main {

    /**
     * Конструктор по умолчанию.
     */
    public Main() {}

    /**
     * Главный метод программы.
     * Запускает бесконечный цикл обработки выражений.
     * Значения переменных сохраняются между вычислениями —
     * если переменная уже была введена, повторно спрашивать не будет.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Хранит значения переменных между вычислениями
        Map<String, Double> varValues = new HashMap<>();

        System.out.println("Калькулятор выражений. Введите 'exit' для выхода.");
        System.out.println("Поддерживаются: +, -, *, /, ^ и функции sin, cos, tan, sqrt, log, exp, abs");

        while (true) {
            System.out.print("\nВведите выражение: ");
            String expression = scanner.nextLine().trim();

            // Выход из программы
            if (expression.equalsIgnoreCase("exit")) {
                System.out.println("До свидания!");
                break;
            }

            // Пропускаем пустой ввод
            if (expression.isEmpty()) {
                continue;
            }

            // Находим все переменные в выражении
            Set<String> variables = ExpressionParser.findVariables(expression);

            // Запрашиваем значения только для новых переменных
            for (String var : variables) {
                if (!varValues.containsKey(var)) {
                    while (true) {
                        System.out.print("Значение переменной " + var + ": ");
                        String input = scanner.nextLine().trim();
                        try {
                            varValues.put(var, Double.parseDouble(input));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: введите число!");
                        }
                    }
                }
            }

            // Вычисляем выражение и выводим результат
            try {
                ExpressionParser parser = new ExpressionParser();
                INode root = parser.parse(expression);
                double result = root.calc(varValues);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка в выражении: " + e.getMessage());
            }
        }
    }
}