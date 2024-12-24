
package org.example;


import org.example.exception.PredicateException;
import org.example.impl.BooleanEquals;
import org.example.impl.BooleanExists;
import org.example.impl.BooleanFalse;
import org.example.impl.BooleanGreaterEqual;
import org.example.impl.BooleanGreaterThan;
import org.example.impl.BooleanLessEqual;
import org.example.impl.BooleanLessThan;
import org.example.impl.BooleanNotEquals;
import org.example.impl.BooleanRegexMatch;
import org.example.impl.BooleanTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 布尔表达式的基类
 */
public abstract class BooleanExpression extends Expression {
    private static Map<String, BooleanExpression> _factories = new HashMap<String, BooleanExpression>();
    protected String[] _operands;
    private String[] _keys;

    /**
     * 初始化 _factories 映射，包含每个具体布尔表达式的原型实例，键为运算符字符串。
     */
    static {
        _factories.put("eq", new BooleanEquals());
        _factories.put("ne", new BooleanNotEquals());
        _factories.put("lt", new BooleanLessThan());
        _factories.put("le", new BooleanLessEqual());
        _factories.put("gt", new BooleanGreaterThan());
        _factories.put("ge", new BooleanGreaterEqual());
        _factories.put("re_match", new BooleanRegexMatch());
        _factories.put("exists", new BooleanExists());
        _factories.put("true", new BooleanTrue());
        _factories.put("false", new BooleanFalse());
    }

    /**
     * 工厂方法
     * 此方法将使用从 _factories 映射中获得的原型，根据运算符（如 "eq"、"ne"、"lt" 等）创建一个布尔表达式（BooleanExpression）
     *
     * @param expr  通过解析查询谓词提取的表达式列表
     */
    public static Expression createExpression(final List expr) throws PredicateException {
        Iterator iter = expr.listIterator();
        if (!iter.hasNext()) {
            throw new PredicateException("Missing operator in predicate expression");
        }

        String op = (String) iter.next();
        BooleanExpression factory = _factories.get(op);
        if (factory == null) {
            throw new PredicateException("Unknown operator in predicate expression." + "msg:" + expr);
        }

        return factory.create(expr);
    }

    /**
     * 工厂方法，用于创建一个具体的布尔表达式实例
     *
     * @param expr 通过解析查询谓词提取的表达式列表
     * @return 一个具体布尔表达式的实例
     */
    public abstract Expression create(final List expr) throws PredicateException;


    /**
     * 基本构造函数，主要由每个具体布尔表达式的原型实例使用。
     */
    protected BooleanExpression() {
    }

    /**
     * 遍历输入的表达式列表，根据对象类型来填充未评估的操作数。
     * 如果对象是字符串，则作为键用于从对象获取属性；如果是子列表，且包含引号字符串，则将该字符串作为操作数；否则直接使用列表中的对象作为操作数
     *
     * "quote":字面量字符串，或转义标记。 为了表示一个字符串应该被当作字面量处理，而不是变量，该字符串必须使用 quote 表达式进行引用。
     * 如："eq" "employee" ["quote" "Tom"]] 表示employee的值是否为“Tom”
     *    "eq" "employee" "Tom" 表示employee的值是否和Tom的“值”一样
     *
     * @param operandCount 表达式中的操作数数量，通常由子类传递该值.
     * @param expr         是一个表达式列表，通过解析查询谓词（query predicate）提取出来的
     */
    protected BooleanExpression(final int operandCount, final List expr) throws PredicateException {
        Iterator iter = expr.listIterator();
        String op = (String) iter.next(); // We've already tested for hasNext() in the factory

        _operands = new String[operandCount];
        _keys = new String[operandCount];

        for (int i = 0; i < operandCount; i++) {
            if (!iter.hasNext()) {
                throw new PredicateException("Too few operands for operation: " + op);
            }

            Object object = iter.next();
            _operands[i] = object.toString();

            if (object instanceof String) {
                _keys[i] = _operands[i];
                _operands[i] = null;
            } else if (object instanceof List) {
                List sublist = (List) object;
                Iterator subiter = sublist.listIterator();

                if (subiter.hasNext() && ((String) subiter.next()).equals("quote")) {
                    if (subiter.hasNext()) {
                        _operands[i] = subiter.next().toString();
                        if (subiter.hasNext()) {
                            throw new PredicateException("Extra tokens at end of 'quote'");
                        }
                    }
                } else {
                    throw new PredicateException("Expected '[quote, <token>]'");
                }
            }
        }

        if (iter.hasNext()) {
            throw new PredicateException("Too many operands for operation: " + op);
        }
    }

    /**
     * 填充在评估时获得的操作数
     *
     * @param data 提取操作数的对象
     */
    protected void populateOperands(final Map<String, Object> data) {
        for (int i = 0; i < _operands.length; i++) {
            String key = _keys[i];
            if (key != null) {
                _operands[i] = getString(data.get(key));
            }
        }
    }

    private static String getString(final Object p) {
        if (p == null) {
            return null;
        } else if (p instanceof String) {
            return (String) p;
        } else if (p instanceof byte[]) {
            return new String((byte[]) p);
        } else return p.toString();
    }

    /**
     * 评估
     *
     * @param data 用于评估表达式的对象
     * @return 匹配，则返回 true；否则返回 false
     */
    public abstract boolean evaluate(final Map<String, Object> data);
}

