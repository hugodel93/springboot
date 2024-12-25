package org.example.impl;

import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/***
 * @Title BoolenIn
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/24 18:00
 * @version: 1.0.0
 **/
public class BooleanBetween extends BooleanExpression {

    public BooleanBetween() {
    }

    public BooleanBetween(List expr) throws PredicateException {
        super(3, expr);
    }

    @Override
    public Expression create(List expr) throws PredicateException {
        return new BooleanBetween(expr);
    }

    @Override
    public boolean evaluate(Map<String, Object> data) {
        populateOperands(data);
        if (_operands[0] == null || _operands[1] == null || _operands[2] == null) {
            return false;
        }
        try {
            double v = Double.parseDouble(_operands[0]);
            double l = Double.parseDouble(_operands[1]);
            double r = Double.parseDouble(_operands[2]);
            return  (v >= l && v <= r);
        } catch (NumberFormatException nfe) {
            // If converting to double fails try a lexicographic comparison
            return _operands[0].compareTo(_operands[1]) >= 0 && _operands[0].compareTo(_operands[2]) <= 0;
        }
    }
}
