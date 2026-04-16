package com.yader;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void testSimpleAddition() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("2+3");
        assertEquals(5.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testOperatorPrecedence() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("2+3*4");
        assertEquals(14.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testParentheses() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("(2+3)*4");
        assertEquals(20.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testUnaryMinus() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("-5+3");
        assertEquals(-2.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testDivisionByZero() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("1/0");
        assertThrows(ArithmeticException.class, () -> root.calc(new HashMap<>()));
    }

    @Test
    void testVariable() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("x*2");
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 5.0);
        assertEquals(10.0, root.calc(vars), 0.0001);
    }

    @Test
    void testSinFunction() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("sin(0)");
        assertEquals(0.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testSqrtFunction() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("sqrt(16)");
        assertEquals(4.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testPower() {
        ExpressionParser parser = new ExpressionParser();
        INode root = parser.parse("2^10");
        assertEquals(1024.0, root.calc(new HashMap<>()), 0.0001);
    }

    @Test
    void testInvalidExpression() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("2*/3"));
    }

    @Test
    void testUnbalancedParentheses() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(2+3"));
    }

    @Test
    void testFindVariables() {
        Set<String> vars = ExpressionParser.findVariables("x + y * sin(z)");
        assertEquals(3, vars.size());
        assertTrue(vars.contains("x"));
        assertTrue(vars.contains("y"));
        assertTrue(vars.contains("z"));
    }
}