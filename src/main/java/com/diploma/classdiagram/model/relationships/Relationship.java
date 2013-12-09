package com.diploma.classdiagram.model.relationships;

import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.classdiagram.model.XMLElement;

/**
 * User: ZIM
 * Date: 25.08.12
 * Time: 16:19
 */

public interface Relationship extends XMLElement {

    /**
     * This method should return identifier
     * of source (also known as parent or supplier)
     * element of relationships.
     * <p/>
     * For example, if class A generalizing class B
     * method should return identifier of class B.
     *
     * @return - Source (also known as parent or supplier) identifier of relationship.
     */
    public String getSource();

    /**
     * @param source - Set the source (also known as parent or supplier) of relationship
     * @see #setSource(String) for detail information.
     */
    public void setSource(String source);

    /**
     * This method should return identifier
     * of destination (also known as child or client)
     * element of relationship.
     * <p/>
     * For example, if class A generalizing class B
     * method should return identifier of class A.
     *
     * @return - Destination (also known as child or client) identifier of relationship.
     */
    public String getDestination();

    /**
     * @param destination - Set the destination (also known as child or client) identifier of relationship.
     * @see #setDestination(String) for detail information.
     */
    public void setDestination(String destination);

    /**
     * This method should return
     * <b>true</b> if element is single in xml file and
     * <b>false</b> if element if a part(child) of owner packaged element.
     */
    public boolean isSingleElement();

    /**
     * @param isSingleElement - Set <b>true</b> if it is single and <b>false</b> if not.
     * @see #isSingleElement() for detail information.
     */
    public void setSingleElement(boolean isSingleElement);

    /**
     * Method should return relationship type
     * witch is one of:
     *
     * @return - RelationshipImpl type.
     * @see com.diploma.classdiagram.enumerates.RelationshipType
     */
    public RelationshipType getRelationshipType();

    /**
     * Method set the relationship type.
     *
     * @param type - RelationshipImpl type.
     */
    public void setRelationshipType(RelationshipType type);

}
