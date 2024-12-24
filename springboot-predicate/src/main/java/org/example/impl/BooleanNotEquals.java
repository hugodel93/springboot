
package org.example.impl;


import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;

/**
 * 布尔不等于表达式
 */
public final class BooleanNotEquals extends BooleanExpression {
    /**
     * Factory method to create an instance of BooleanNotEquals
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     * @return an instance of the concrete BooleanExpression
     */
    public Expression create(final List expr) throws PredicateException {
        return new BooleanNotEquals(expr);
    }

    /**
     * Basic Constructor primarily used by the prototype instance of each concrete BooleanExpression
     */
    public BooleanNotEquals() {
    }

    /**
     * Main Constructor, uses base class constructor to populate unevaluated operands
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     */
    public BooleanNotEquals(final List expr) throws PredicateException {
        super(2, expr);
    }

    /**
     * Evaluate "not equal to" expression against a QmfData instance.
     * N.B. to avoid complexities with types this class treats operands as Strings performing an appropriate evaluation
     * of the String that makes sense for a given expression e.g. parsing as a double for {@literal >, >=, <, <= }
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

        return !_operands[0].equals(_operands[1]);
    }
}

