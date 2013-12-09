package com.diploma.classdiagram.model.relationships;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 23.09.12
 * Time: 13:04
 */
public class CardinalityRelationship implements Relationship, Cardinality {
    private String id;
    private String name;
    private String source;
    private String destination;
    private boolean isSingleElement = false;
    private RelationshipType relationshipType;

    private Integer srcMinCardinality;
    private Integer srcMaxCardinality;
    private Integer dstMinCardinality;
    private Integer dstMaxCardinality;

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

    public Integer getSourceMinimum() {
        return srcMinCardinality;
    }

    public Integer getSourceMaximum() {
        return srcMaxCardinality;
    }

    public Integer getDestinationMinimum() {
        return dstMinCardinality;
    }

    public Integer getDestinationMaximum() {
        return dstMaxCardinality;
    }

    public void setSourceMinimum(Integer value) {
        if (value == -1) {
            srcMinCardinality = INFINITY;
            return;
        }
        srcMinCardinality = value;
    }

    public void setSourceMaximum(Integer value) {
        if (value == -1) {
            srcMaxCardinality = INFINITY;
            return;
        }
        srcMaxCardinality = value;
    }

    public void setDestinationMinimum(Integer value) {
        if (value == -1) {
            dstMinCardinality = INFINITY;
            return;
        }
        dstMinCardinality = value;
    }

    public void setDestinationMaximum(Integer value) {
        if (value == -1) {
            dstMaxCardinality = INFINITY;
            return;
        }
        dstMaxCardinality = value;
    }

    public void setSourceMinimum(String value) {
        if (value.equals("*")) {
            srcMinCardinality = INFINITY;
            return;
        }
        srcMinCardinality = Integer.valueOf(value);
    }

    public void setSourceMaximum(String value) {
        if (value.equals("*")) {
            srcMaxCardinality = INFINITY;
            return;
        }
        srcMaxCardinality = Integer.valueOf(value);
    }

    public void setDestinationMinimum(String value) {
        if (value.equals("*")) {
            dstMinCardinality = INFINITY;
            return;
        }
        dstMinCardinality = Integer.valueOf(value);
    }

    public void setDestinationMaximum(String value) {
        if (value.equals("*")) {
            dstMaxCardinality = INFINITY;
            return;
        }
        dstMaxCardinality = Integer.valueOf(value);
    }

    @Override
    public String toString() {
        String relationshipInfo =
                "RelationshipImpl name : " + getName() + "\n" +
                        "RelationshipImpl type : " + getRelationshipType() + "\n" +
                        "source : " + getSource() + "\n" +
                        "destination : " + getDestination() + "\n" +
                        "isSingle : " + isSingleElement() + "\n" +
                        "srcMin = " + getSourceMinimum() + "\n" +
                        "srcMax = " + getSourceMaximum() + "\n" +
                        "dstMin = " + getDestinationMinimum() + "\n" +
                        "dstMax = " + getDestinationMaximum() + "\n" +
                        "ID : " + getId() + "\n";

        return relationshipInfo;
    }

}
