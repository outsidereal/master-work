package com.diploma.global;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 18.08.12
 * Time: 16:54
 */
public interface IElement {

    /**
     * @param id - Unique identifier of element.
     */
    public void setId(String id);

    /**
     * This method should return unique identifier of element.
     *
     * @return - Unique identifier.
     */
    public String getId();

    /**
     * This method set the name of element.
     *
     * @param name - Name of element.
     */
    public void setName(String name);

    /**
     * This method should return name of current element.
     *
     * @return - Name of element.
     */
    public String getName();
}