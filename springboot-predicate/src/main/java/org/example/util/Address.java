package org.example.util;


import java.io.Serializable;
import java.util.Map;


/**
 * Address
 */

public class Address implements Serializable {

    private static final long serialVersionUID = 6096143531336726036L;

    private String _name;
    private String _subject;
    private Map _options;
    private final String _myToString;

    public static Address parse(String address) {
        return new AddressParser(address).parse();
    }

    public Address(String name, String subject, Map options) {
        this._name = name;
        this._subject = subject;
        this._options = options;
        this._myToString = String.format("%s/%s; %s", PyPrint.pprint(_name), PyPrint.pprint(_subject), PyPrint.pprint(_options));
    }

    public String getName() {
        return _name;
    }

    public String getSubject() {
        return _subject;
    }

    public Map getOptions() {
        return _options;
    }

    public String toString() {
        return _myToString;
    }

}
