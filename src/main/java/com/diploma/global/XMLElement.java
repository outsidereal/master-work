package com.diploma.global;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 18.08.12
 * Time: 17:12
 */
public abstract class XMLElement implements Element {
    private String id;
    private String name;

    /**
     * This method should return the name element.
     *
     * @return - The name of element.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of element.
     *
     * @param name - Name of element.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method should return the identifier of current element.
     *
     * @return - Identifier of element.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the identifier of element.
     *
     * @param id - Unique identifier of element.
     */
    public void setId(String id) {
        this.id = id;
    }
}
