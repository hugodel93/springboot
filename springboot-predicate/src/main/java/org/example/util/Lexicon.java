
package org.example.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 词汇表
 */

public class Lexicon {

    private List<Token.Type> types;
    private Token.Type eof;

    public Lexicon() {
        this.types = new ArrayList<Token.Type>();
        this.eof = null;
    }

    public Token.Type define(String name, String pattern) {
        Token.Type t = new Token.Type(name, pattern);
        types.add(t);
        return t;
    }

    public Token.Type eof(String name) {
        Token.Type t = new Token.Type(name, null);
        eof = t;
        return t;
    }

    public Lexer compile() {
        StringBuilder joined = new StringBuilder();
        for (Token.Type t : types) {
            if (joined.length() > 0) {
                joined.append('|');
            }
            joined.append('(').append(t.getPattern()).append(')');
        }
        Pattern rexp = Pattern.compile(joined.toString());
        return new Lexer(new ArrayList<Token.Type>(types), eof, rexp);
    }
}
