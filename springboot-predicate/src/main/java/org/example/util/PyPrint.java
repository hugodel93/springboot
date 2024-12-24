
package org.example.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * PyPrint
 */

public class PyPrint {
    private PyPrint() {
    }

    public static String pprint(Object obj) {
        if (obj instanceof Map) {
            return pprint_map((Map) obj);
        } else if (obj instanceof List) {
            return pprint_list((List) obj);
        } else if (obj instanceof String) {
            return pprint_string((String) obj);
        } else if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue() ? "True" : "False";
        } else if (obj == null) {
            return "None";
        } else {
            return obj.toString();
        }
    }

    private static String indent(String st) {
        return "  " + st.replace("\n", "\n  ");
    }

    private static String pprint_map(Map<Object, Object> map) {
        List<String> items = new ArrayList<String>();
        for (Map.Entry me : map.entrySet()) {
            items.add(String.format("%s: %s", pprint(me.getKey()),
                    pprint(me.getValue())));
        }
        Collections.sort(items);
        return pprint_items("{", items, "}");
    }

    private static String pprint_list(List list) {
        List<String> items = new ArrayList<String>();
        for (Object o : list) {
            items.add(pprint(o));
        }
        return pprint_items("[", items, "]");
    }

    private static String pprint_items(String start, List<String> items,
                                       String end) {
        StringBuilder result = new StringBuilder();
        for (String item : items) {
            if (result.length() > 0) {
                result.append(",\n");
            }
            result.append(indent(item));
        }

        if (result.length() > 0) {
            return String.format("%s\n%s\n%s", start, result, end);
        } else {
            return String.format("%s%s", start, end);
        }
    }

    private static String pprint_string(String st) {
        StringBuilder result = new StringBuilder();
        result.append('\'');
        for (int i = 0; i < st.length(); i++) {
            char c = st.charAt(i);
            switch (c) {
                case '\'':
                    result.append("\\'");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                default:
                    if (c >= 0x80) {
                        result.append(String.format("\\u%04x", (int) c));
                    } else {
                        result.append(c);
                    }
                    break;
            }
        }
        result.append('\'');
        return result.toString();
    }

}
