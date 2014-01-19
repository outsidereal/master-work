package com.diploma.classdiagram.verification;

import com.diploma.classdiagram.model.*;
import com.diploma.classdiagram.model.Class;
import com.diploma.classdiagram.model.relationships.RelationshipType;
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
    private final Map<String, Class> classes;
    private final Map<String, Relationship> relationships;

    public GraphVerification(Map<String, Class> classes, Map<String, Relationship> relationships) {
        this.relationships = relationships;
        this.classes = classes;
    }

    @Override
    public boolean verify() {
        boolean isValid = true;
        Iterator iterator = relationships.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Relationship relationship = (Relationship) pair.getValue();
            if (RelationshipType.ASSOCIATION.equals(relationship.getRelationshipType())) {
                final CardinalityRelationship rel = (CardinalityRelationship) relationship;
                final Double cardinality = calculateCardinality(rel);
                if (cardinality < 1) {
                    isValid = false;
                    LOGGER.error(GraphVerification.class.getSimpleName()
                            + ". Invalid multiplicity at association between classes:"
                            + classes.get(rel.getSource()).getName() + " and "
                            + classes.get(rel.getDestination()).getName() +
                            ".\nGraph weight = " + cardinality + ". Must be >= 1. \n\n");
                }
            }
        }
        if (isValid){
            LOGGER.info("Graph Verification: The diagram is correct!\n");
        }

        return isValid;
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
