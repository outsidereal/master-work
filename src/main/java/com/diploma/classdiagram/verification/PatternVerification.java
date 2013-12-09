package com.diploma.classdiagram.verification;

import com.diploma.classdiagram.model.Class;
import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.classdiagram.model.relationships.Cardinality;
import com.diploma.classdiagram.model.relationships.CardinalityRelationship;
import com.diploma.classdiagram.model.relationships.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: d.ulanovych
 * Date: 04.03.13
 * Time: 10:06
 */

/**
 * Простой множественный цикл
 */
public class PatternVerification implements Verification {
    private final Map<String, Relationship> relationships;
    private final Map<String, Class> classes;

    public PatternVerification(Map<String, Class> classes, Map<String, Relationship> relationships) {
        this.relationships = relationships;
        this.classes = classes;
    }

    @Override
    public boolean verify() {
        boolean isValid = true;
        isValid &= simpleMultipleCyclePattern();

        return isValid;
    }

    /**
     * Паттерн №1 : Простой множественный цикл.
     * TODO: should be formed as pattern library.
     */
    public boolean simpleMultipleCyclePattern() {
        boolean isValid = true;
        List<Cardinality> associations = getAssociations();
        for (Cardinality first : associations) {
            List<Cardinality> associationsBetweenSameClasses = new ArrayList<Cardinality>();
            for (Cardinality second : associations) {
                if (first != second) {
                    if (((CardinalityRelationship) second).getSource().equals(((CardinalityRelationship) first).getSource())
                            && ((CardinalityRelationship) second).getDestination().equals(((CardinalityRelationship) first).getDestination())) {
                        associationsBetweenSameClasses.add(second);
                    }
                }
            }
            if (associationsBetweenSameClasses.size() > 1 && !compareCardinalityAssociation(associationsBetweenSameClasses)) {
                isValid = false;
            }
        }
        return false;
    }

    private List<Cardinality> getAssociations() {
        List<Cardinality> associations = new ArrayList<Cardinality>();
        for (Relationship relationship : relationships.values()) {
            if (RelationshipType.ASSOCIATION.equals(relationship.getRelationshipType())) {
                associations.add((CardinalityRelationship) relationship);
            }
        }
        return associations;
    }

    private boolean compareCardinalityAssociation(List<Cardinality> associations) {
        boolean isValid = true;
        for (Cardinality first : associations) {
            for (Cardinality second : associations) {
                if (!first.equals(second) && !compare(first, second)) {
                    LOGGER.error(PatternVerification.class.getName() + " Association mistake. " + ((CardinalityRelationship) first).getName() +
                            " and " + ((CardinalityRelationship) second).getName() + " are incorrect!");
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    private boolean compare(Cardinality first, Cardinality second) {
        if (first.getSourceMinimum() >= second.getSourceMinimum() && first.getSourceMaximum() >= second.getSourceMaximum()) {
            //do nothing, everything is ok
        } else if (first.getSourceMinimum() <= second.getSourceMinimum() && first.getSourceMaximum() <= second.getSourceMaximum()) {
            //do nothing, everything is ok
        } else {
            return false;
        }

        if (first.getDestinationMinimum() >= second.getDestinationMinimum() && first.getDestinationMaximum() >= second.getDestinationMaximum()) {
            //do nothing, everything is ok
        } else if (first.getDestinationMinimum() <= second.getDestinationMinimum() && first.getDestinationMaximum() <= second.getDestinationMaximum()) {
            //do nothing, everything is ok
        } else {
            return false;
        }
        return true;
    }
}
