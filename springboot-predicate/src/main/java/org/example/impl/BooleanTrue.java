
package org.example.impl;


import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;

/**
 * 布尔真表达式
 */
public final class BooleanTrue extends BooleanExpression {
    /**
     * Factory method to create an instance of BooleanTrue
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     * @return an instance of the concrete BooleanExpression
     */
    public Expression create(final List expr) throws PredicateException {
        return new BooleanTrue();
    }

    /**
     * Basic Constructor primarily used by the prototype instance of each concrete BooleanExpression
     */
    public BooleanTrue() {
    }

    /**
     * Evaluate "true" expression against a QmfData instance.
     *
     * @param data the object to evaluate the expression against
     * @return true.
     */
    public boolean evaluate(final Map<String, Object> data) {
        return true;
    }
}

