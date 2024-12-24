
package org.example.util;

import org.example.exception.LexError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 词法分析器
 */

public class Lexer {

    private List<Token.Type> types;
    private Token.Type eof;
    private Pattern rexp;

    public Lexer(List<Token.Type> types, Token.Type eof, Pattern rexp) {
        this.types = types;
        this.eof = eof;
        this.rexp = rexp;
    }

    public List<Token> lex(final String st) {
        List<Token> tokens = new ArrayList<Token>();

        int position = 0;
        Matcher m = rexp.matcher(st);
        OUTER:
        while (position < st.length()) {
            m.region(position, st.length());
            if (m.lookingAt()) {
                for (int i = 1; i <= m.groupCount(); i++) {
                    String value = m.group(i);
                    if (value != null) {
                        tokens.add(new Token(types.get(i - 1), value, st, m.start(i)));
                        position = m.end(i);
                        continue OUTER;
                    }
                }
                throw new RuntimeException("no group matched");
            } else {
                throw new LexError("unrecognized characters line:" + LineInfo.get(st, position));
            }
        }

        tokens.add(new Token(eof, null, st, position));

        return tokens;
    }

}
