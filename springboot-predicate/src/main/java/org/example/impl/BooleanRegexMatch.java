package org.example.impl;

import org.example.BooleanExpression;
import org.example.Expression;
import org.example.exception.PredicateException;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 布尔正则匹配表达式
 */
public final class BooleanRegexMatch extends BooleanExpression {
    private final Pattern _pattern;

    /**
     * Factory method to create an instance of BooleanRegexMatch
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     * @return an instance of the concrete BooleanExpression
     */
    public Expression create(final List expr) throws PredicateException {
        return new BooleanRegexMatch(expr);
    }

    /**
     * Basic Constructor primarily used by the prototype instance of each concrete BooleanExpression
     */
    public BooleanRegexMatch() {
        _pattern = null;
    }

    /**
     * Main Constructor, uses base class constructor to populate unevaluated operands
     *
     * @param expr the List of Expressions extracted by parsing the Query predicate
     */
    public BooleanRegexMatch(final List expr) throws PredicateException {
        super(2, expr);

        try {
            _pattern = Pattern.compile(_operands[1]);
        } catch (PatternSyntaxException pse) {
            throw new PredicateException("Error in regular expression " + pse.getMessage());
        }
    }

    /**
     * Evaluate "regex match" expression against a QmfData instance.
     * N.B. to avoid complexities with types this class treats operands as Strings performing an appropriate evaluation
     * of the String that makes sense for a given expression e.g. parsing as a double for {@literal >, >=, <, <= }
     *
     * @param data the object to evaluate the expression against
     * @return true if query matches the QmfData instance, else false.
     */
    public boolean evaluate(final Map<String, Object> data) {
        populateOperands(data);

        if (_operands[0] == null || _operands[1] == null || _pattern == null)
        {
            return false;
        }

        Matcher matcher = _pattern.matcher(_operands[0]);
        return matcher.find();
    }
}

