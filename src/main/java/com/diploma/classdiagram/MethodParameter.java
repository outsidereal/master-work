package com.diploma.classdiagram;

import com.diploma.global.XMLElement;

/**
 * User: ZIM
 * Date: 18.08.12
 * Time: 21:55
 */
public class MethodParameter extends XMLElement {
    private String type;
    private String value;

    /**
     * @return - Data type of parameter.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type - Data type of parameter.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return - Value of parameter.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value - Value of parameter
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodParameter that = (MethodParameter) o;

        if (!type.equals(that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String paramInfo =
                "Parameter name : " + getName() + "\n" +
                        "Type : " + getType() + "\n" +
                        "ID : " + getId() + "\n" +
                        "Value : " + getValue() + "\n";
        return paramInfo;
    }
}
