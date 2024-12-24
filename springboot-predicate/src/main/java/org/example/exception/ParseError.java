
package org.example.exception;


import org.example.util.LineInfo;
import org.example.util.Token;

import java.util.Arrays;

/**
 * ParseError
 */

public class ParseError extends RuntimeException {

    private static String msg(Token token, Token.Type... expected) {
        LineInfo li = token.getLineInfo();
        String exp = join(", ", Arrays.asList(expected));
        if (expected.length > 1) {
            exp = String.format("(%s)", exp);
        }

        if (expected.length > 0) {
            return String.format("expecting %s, got %s line:%s", exp, token, li);
        } else {
            return String.format("unexpected token %s line:%s", token, li);
        }
    }

    public ParseError(Token token, Token.Type... expected) {
        super(msg(token, expected));
    }

    private static String join(String sep, Iterable items) {
        StringBuilder result = new StringBuilder();
        for (Object o : items) {
            if (result.length() > 0) {
                result.append(sep);
            }
            result.append(o.toString());
        }
        return result.toString();
    }

}
