
package org.example.util;

import org.example.exception.ParseError;

import java.util.ArrayList;
import java.util.List;


/**
 * Parser
 */

class Parser {

    private List<Token> tokens;
    private int idx = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.idx = 0;
    }

    Token next() {
        return tokens.get(idx);
    }

    boolean matches(Token.Type... types) {
        for (Token.Type t : types) {
            if (next().getType() == t) {
                return true;
            }
        }
        return false;
    }

    Token eat(Token.Type... types) {
        if (types.length > 0 && !matches(types)) {
            throw new ParseError(next(), types);
        } else {
            Token t = next();
            idx += 1;
            return t;
        }
    }

    List<Token> eat_until(Token.Type... types) {
        List<Token> result = new ArrayList<Token>();
        while (!matches(types)) {
            result.add(eat());
        }
        return result;
    }

    /**
     * Returns the remaining list of tokens, without eating them
     */
    List<Token> remainder() {
        List<Token> result = new ArrayList<Token>();
        for (int i = idx; i < tokens.size(); i++) {
            result.add(tokens.get(i));
        }
        return result;
    }
}
