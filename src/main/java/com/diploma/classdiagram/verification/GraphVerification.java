package com.diploma.classdiagram.verification;

import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.classdiagram.model.relationships.CardinalityRelationship;
import com.diploma.classdiagram.model.relationships.Relationship;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 26.01.13
 * Time: 15:10
 */
public class GraphVerification implements Verification {
    private final Map<String, Relationship> relationships;

    public GraphVerification(Map<String, Relationship> relationships) {
        this.relationships = relationships;
    }

    @Override
    public boolean verify() {
        Iterator iterator = relationships.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Relationship relationship = (Relationship) pair.getValue();
            if (RelationshipType.ASSOCIATION.equals(relationship.getRelationshipType())) {
                final CardinalityRelationship rel = (CardinalityRelationship) relationship;
                final Double cardinality = calculateCardinality(rel);
                if (cardinality < 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private Double calculateCardinality(final CardinalityRelationship relationship) {
        Double sourceMinimum = Double.valueOf(relationship.getSourceMinimum());
        Double sourceMaximum = Double.valueOf(relationship.getSourceMaximum());
        Double destinationMinimum = Double.valueOf(relationship.getDestinationMinimum());
        Double destinationMaximum = Double.valueOf(relationship.getDestinationMaximum());
        Double cardinality = destinationMaximum / (1.0d / sourceMinimum) * sourceMaximum * (1.0d / destinationMinimum);
        return cardinality;
    }
}
