package com.yader;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Точка входа программы.
 * Запрашивает у пользователя выражения в цикле до команды выхода.
 */
public class Main {

    /**
     * Конструктор по умолчанию.
     */
    public Main() {}

    /**
     * Главный метод программы.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> varValues = new HashMap<>();
        System.out.println("Калькулятор выражений. Введите 'exit' для выхода.");

        while (true) {
            System.out.print("\nВведите выражение: ");
            String expression = scanner.nextLine().trim();

            if (expression.equalsIgnoreCase("exit")) {
                System.out.println("До свидания!");
                break;
            }

            if (expression.isEmpty()) {
                continue;
            }

            Set<String> variables = ExpressionParser.findVariables(expression);

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