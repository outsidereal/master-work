package com.diploma.classdiagram.relationships;

import com.diploma.classdiagram.enumerates.RelationshipsType;
import com.diploma.global.XMLElement;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 25.08.12
 * Time: 17:42
 */
public class Relationship extends XMLElement implements IRelationship {

    private String source;
    private String destination;
    private boolean isSingleElement = false;
    private RelationshipsType relationshipsType;


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

    public RelationshipsType getRelationshipType() {
        return relationshipsType;
    }

    public void setRelationshipType(RelationshipsType type) {
        this.relationshipsType = type;
    }

    @Override
    public String toString() {
        String relationshipInfo =
                "Relationship name : " + getName() + "\n" +
                        "Relationship type : " + getRelationshipType() + "\n" +
                        "source : " + getSource() + "\n" +
                        "destination : " + getDestination() + "\n" +
                        "isSingle : " + isSingleElement() + "\n" +
                        "ID : " + getId() + "\n";

        return relationshipInfo;
    }

}
