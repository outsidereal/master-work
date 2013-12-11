package com.diploma.classdiagram.verification;

import com.diploma.classdiagram.model.Class;
import com.diploma.classdiagram.model.relationships.RelationshipType;
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
        List<Cardinality> associationsBetweenSameClasses = new ArrayList<Cardinality>();
        for (int i = 0; i < associations.size(); i++) {
            CardinalityRelationship first = (CardinalityRelationship) associations.get(i);
            associationsBetweenSameClasses.add(first);
            for (int j = i + 1; j < associations.size(); j++) {
                CardinalityRelationship second = (CardinalityRelationship) associations.get(j);
                if ((first.getSource().equals(second.getSource()) || first.getSource().equals(second.getDestination()))
                        && (first.getDestination().equals(second.getDestination()) || first.getDestination().equals(second.getSource()))
                        && !first.getSource().equals(first.getDestination()) && !second.getSource().equals(second.getDestination())) {
                    associationsBetweenSameClasses.add(second);

                }
            }

            if (associationsBetweenSameClasses.size() > 1 && !compareCardinalityAssociation(associationsBetweenSameClasses)) {
                isValid = false;
            }
            associationsBetweenSameClasses.clear();
        }

        return isValid;
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
        for (int i = 0; i < associations.size(); i++) {
            Cardinality first = associations.get(i);
            for (int j = i + 1; j < associations.size(); j++) {
                Cardinality second = associations.get(j);
                if (!compare(first, second)) {
                    LOGGER.error(PatternVerification.class.getSimpleName() +
                            ". Associations incompatibility between classes : " +
                            classes.get(((Relationship) first).getSource()).getName() + " and " +
                            classes.get(((Relationship) first).getDestination()).getName() + " .\n" +
                            ((Relationship) first).getName() + " and " + ((Relationship) second).getName() + " are incorrect!\n\n");
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    private boolean compare(Cardinality first, Cardinality second) {
        if (first.getSourceMinimum() > second.getSourceMaximum()
                || second.getSourceMinimum() > first.getSourceMaximum()
                || first.getDestinationMinimum() > second.getDestinationMaximum()
                || second.getDestinationMinimum() > first.getDestinationMaximum()) {
            return false;
        }

        return true;
    }
}
