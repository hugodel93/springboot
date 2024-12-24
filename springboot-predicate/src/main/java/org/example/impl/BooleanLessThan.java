
package org.example.impl;


import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;

/**
 * 布尔小于表达式
 */
public final class BooleanLessThan extends BooleanExpression {
    /**
     * Factory method to create an instance of BooleanLessThan
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     * @return an instance of the concrete BooleanExpression
     */
    public Expression create(final List expr) throws PredicateException {
        return new BooleanLessThan(expr);
    }

    /**
     * Basic Constructor primarily used by the prototype instance of each concrete BooleanExpression
     */
    public BooleanLessThan() {
    }

    /**
     * Main Constructor, uses base class constructor to populate unevaluated operands
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     */
    public BooleanLessThan(final List expr) throws PredicateException {
        super(2, expr);
    }

    /**
     * Evaluate "less than" expression against a QmfData instance.
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

        try
        {
            double l = Double.parseDouble(_operands[0]);
            double r = Double.parseDouble(_operands[1]);
            return l < r;
        }
        catch (NumberFormatException nfe)
        {
            // If converting to double fails try a lexicographic comparison
            return _operands[0].compareTo(_operands[1]) < 0;
        }
    }
}

