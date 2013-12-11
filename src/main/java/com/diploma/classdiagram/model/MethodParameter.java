package com.diploma.classdiagram.model;

/**
 * User: ZIM
 * Date: 18.08.12
 * Time: 21:55
 */
public class MethodParameter implements XMLElement {
    private String id;
    private String name;
    private String type;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
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
