package com.diploma.verification.classdiagram;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.enumerates.RelationshipsType;
import com.diploma.classdiagram.relationships.CardinalityRelationship;
import com.diploma.classdiagram.relationships.IRelationship;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 26.01.13
 * Time: 15:10
 */
public class GraphVerification implements Verification {
    private final Map<String, Class> classes;
    private final List<IRelationship> relationships;

    public GraphVerification(Map<String, Class> classes, List<IRelationship> relationships) {
        this.classes = classes;
        this.relationships = relationships;
    }

    @Override
    public boolean verify() {
        for (IRelationship relationship : relationships) {
            if (relationship.getRelationshipType() == RelationshipsType.ASSOCIATION) {
                final CardinalityRelationship rel = (CardinalityRelationship) relationship;
                final double cardinality = calculateCardinality(rel);
                if (cardinality < 1)
                    return false;
            }
        }
        return true;
    }

    private double calculateCardinality(final CardinalityRelationship relationship) {
        double cardinality = relationship.getDestinationMaximum() / (1 / relationship.getSourceMinimum()) *
                relationship.getSourceMaximum() * (1 / relationship.getDestinationMinimum());
        return cardinality;
    }
}
