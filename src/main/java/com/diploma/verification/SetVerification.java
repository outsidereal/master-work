package com.diploma.verification;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.classdiagram.relationships.Cardinality;
import com.diploma.classdiagram.relationships.CardinalityRelationship;
import com.diploma.classdiagram.relationships.Relationship;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: d.ulanovych
 * Date: 04.03.13
 * Time: 10:06
 */
public class SetVerification implements Verification {
    private final Map<String, Class> classes;
    private final List<Relationship> relationships;

    public SetVerification(Map<String, Class> classes, List<Relationship> relationships) {
        this.classes = classes;
        this.relationships = relationships;
    }

    @Override
    public boolean verify() {
        List<Cardinality> associations = getAssociations();
        for (Cardinality first : associations) {
            List<Cardinality> associationsBetweenSameClasses = new ArrayList<Cardinality>();
            for (Cardinality second : associations) {
                if (first != second){
                    if (((CardinalityRelationship)second).getSource()
                            .equals(((CardinalityRelationship)first).getSource())
                        && ((CardinalityRelationship)second).getDestination()
                            .equals(((CardinalityRelationship)first).getDestination())){
                        associationsBetweenSameClasses.add(second);
                    }
                }
            }
            if (associationsBetweenSameClasses.size() > 0 ){
                boolean isOk = compareCardinalityAssociation(associationsBetweenSameClasses);
                if (!isOk)
                    return false;
            }
        }
        return true;
    }

    private List<Cardinality> getAssociations() {
        List<Cardinality> associations = new ArrayList<Cardinality>();
        for (Relationship relationship : relationships) {
            if (relationship.getRelationshipType().equals(RelationshipType.ASSOCIATION)) {
                associations.add((CardinalityRelationship) relationship);
            }
        }
        return associations;
    }

    private boolean compareCardinalityAssociation(List<Cardinality> associations) {
        if (associations == null || associations.isEmpty())
            throw new InvalidParameterException("Association list can't be empty or null!");

        // if we have only one association between two classes
        // then there are any conflicts
        if (associations.size() == 1)
            return true;

        for (Cardinality first : associations) {
            for (Cardinality second : associations) {
                if (!first.equals(second)) {
                    boolean isOk = compare(first, second);
                    if (!isOk)
                        return false;
                }
            }
        }
        return true;
    }

    private boolean compare(Cardinality first, Cardinality second) {
        if (first.getSourceMinimum() >= second.getSourceMinimum() &&
                first.getSourceMaximum() >= second.getSourceMaximum()) {
            //do nothing, everything is ok
        } else if (first.getSourceMinimum() <= second.getSourceMinimum() &&
                first.getSourceMaximum() <= second.getSourceMaximum()) {
            //do nothing, everything is ok
        } else
            return false;

        if (first.getDestinationMinimum() >= second.getDestinationMinimum() &&
                first.getDestinationMaximum() >= second.getDestinationMaximum()) {
            //do nothing, everything is ok
        } else if (first.getDestinationMinimum() <= second.getDestinationMinimum() &&
                first.getDestinationMaximum() <= second.getDestinationMaximum()) {
            //do nothing, everything is ok
        } else
            return false;

        return true;
    }
}
