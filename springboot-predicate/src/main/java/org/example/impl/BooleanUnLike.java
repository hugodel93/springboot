package org.example.impl;

import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;

/**
 * 布尔相等表达式
 *
 */
public final class BooleanUnLike extends BooleanExpression {
    /**
     * Factory method to create an instance of BooleanEquals
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     * @return an instance of the concrete BooleanExpression
     */
    public Expression create(final List expr) throws PredicateException {
        return new BooleanUnLike(expr);
    }

    /**
     * Basic Constructor primarily used by the prototype instance of each concrete BooleanExpression
     */
    public BooleanUnLike() {
    }

    /**
     * Main Constructor, uses base class constructor to populate unevaluated operands
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     */
    public BooleanUnLike(final List expr) throws PredicateException {
        super(2, expr);
    }

    /**
     * like:
     * % 表示任意字符，后面可以是任何内容
     * _ 需要匹配一个字符
     *
     *
     * @param data the object to evaluate the expression against
     * @return true if query matches the QmfData instance, else false.
     */
    public boolean evaluate(final Map<String, Object> data) {
        populateOperands(data);

        if (_operands[0] == null || _operands[1] == null)
        {
            return false;
        }

        return !match(_operands[0],_operands[1],0,0);
    }

    private boolean match(String input, String pattern, int i, int j) {
        // 如果模式遍历完了
        if (j == pattern.length()) {
            // 如果输入也遍历完了，匹配成功
            return i == input.length();
        }

        char pChar = pattern.charAt(j);

        // 如果模式字符是 '%'
        if (pChar == '%') {
            // 尝试两种情况：
            // 1. 不匹配任何字符，即跳过 '%'，继续比较后续部分
            // 2. 匹配至少一个字符，即从当前输入位置开始，递归尝试匹配
            // 跳过 '%' 后继续匹配模式的其余部分
            if (match(input, pattern, i, j + 1)) {
                return true;
            }

            // 如果遇到 '%'，就尝试匹配一个字符后继续
            if (i < input.length() && match(input, pattern, i + 1, j)) {
                return true;
            }

            return false; // 如果两种情况都失败，返回 false
        }

        // 如果模式字符是 '_'
        if (pChar == '_') {
            // 匹配一个字符，继续比较下一个字符
            if (i < input.length()) {
                return match(input, pattern, i + 1, j + 1);
            }
            return false;
        }

        // 其他字符的匹配
        if (i < input.length() && input.charAt(i) == pChar) {
            return match(input, pattern, i + 1, j + 1);
        }

        // 如果字符不匹配，返回 false
        return false;
    }

}

