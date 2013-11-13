package com.diploma.classdiagram.relationships;

import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.global.XMLElement;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 25.08.12
 * Time: 17:42
 */
public class RelationshipImpl extends XMLElement implements Relationship {
    private String source;
    private String destination;
    private boolean isSingleElement = false;
    private RelationshipType relationshipType;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isSingleElement() {
        return isSingleElement;
    }

    public void setSingleElement(boolean isSingleElement) {
        this.isSingleElement = isSingleElement;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType type) {
        this.relationshipType = type;
    }

    @Override
    public String toString() {
        String relationshipInfo =
                "RelationshipImpl name : " + getName() + "\n" +
                        "RelationshipImpl type : " + getRelationshipType() + "\n" +
                        "source : " + getSource() + "\n" +
                        "destination : " + getDestination() + "\n" +
                        "isSingle : " + isSingleElement() + "\n" +
                        "ID : " + getId() + "\n";

        return relationshipInfo;
    }

}
