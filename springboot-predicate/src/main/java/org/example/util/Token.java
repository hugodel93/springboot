
package org.example.util;


/**
 * 词法单元
 */

public class Token {

    public static class Type {

        private String name;
        private String pattern;

        Type(String name, String pattern) {
            this.name = name;
            this.pattern = pattern;
        }

        public String getName() {
            return name;
        }

        public String getPattern() {
            return pattern;
        }

        public String toString() {
            return name;
        }

    }

    private Type type;
    private String value;
    private String input;
    private int position;

    Token(Type type, String value, String input, int position) {
        this.type = type;
        this.value = value;
        this.input = input;
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    public LineInfo getLineInfo() {
        return LineInfo.get(input, position);
    }

    public String toString() {
        if (value == null) {
            return type.toString();
        } else {
            return String.format("%s(%s)", type, value);
        }
    }

}
