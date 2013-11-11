package com.diploma.verification.classdiagram;

import com.diploma.classdiagram.*;
import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.enumerates.RelationshipsType;
import com.diploma.classdiagram.relationships.CardinalityRelationship;
import com.diploma.classdiagram.relationships.ICardinality;
import com.diploma.classdiagram.relationships.IRelationship;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: d.ulanovych
 * Date: 04.03.13
 * Time: 10:06
 */
public class SetVerification implements Verification {
    private final Map<String, Class> classes;
    private final List<IRelationship> relationships;

    public SetVerification(Map<String, Class> classes, List<IRelationship> relationships) {
        this.classes = classes;
        this.relationships = relationships;
    }

    @Override
    public boolean verify() {
        List<ICardinality> associations = getAssociations();
        for (ICardinality first : associations) {
            List<ICardinality> associationsBetweenSameClasses = new ArrayList<ICardinality>();
            for (ICardinality second : associations) {
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

    private List<ICardinality> getAssociations() {
        List<ICardinality> associations = new ArrayList<ICardinality>();
        for (IRelationship relationship : relationships) {
            if (relationship.getRelationshipType().equals(RelationshipsType.ASSOCIATION)) {
                associations.add((CardinalityRelationship) relationship);
            }
        }
        return associations;
    }

    private boolean compareCardinalityAssociation(List<ICardinality> associations) {
        if (associations == null || associations.isEmpty())
            throw new InvalidParameterException("Association list can't be empty or null!");

        // if we have only one association between two classes
        // then there are any conflicts
        if (associations.size() == 1)
            return true;

        for (ICardinality first : associations) {
            for (ICardinality second : associations) {
                if (!first.equals(second)) {
                    boolean isOk = compare(first, second);
                    if (!isOk)
                        return false;
                }
            }
        }
        return true;
    }

    private boolean compare(ICardinality first, ICardinality second) {
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
