package com.diploma.verification.classdiagram;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.Field;
import com.diploma.classdiagram.Method;
import com.diploma.classdiagram.enumerates.RelationshipsType;
import com.diploma.classdiagram.enumerates.Visibility;
import com.diploma.classdiagram.relationships.IRelationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 26.01.13
 * Time: 11:20
 */
public class DriverVerification implements Verification{
    private final Map<String, Class> classes;
    private final List<IRelationship> relationships;

    public DriverVerification(Map<String, Class> classes, List<IRelationship> relationships) {
        this.classes = classes;
        this.relationships = relationships;
    }

    public boolean verify() {
        boolean isValid = true;
        isValid &= checkImplementationRules();
        isValid &= checkInterfaceDefinitionRules();
        isValid &= checkAbstractClassRules();
        isValid &= checkForAbsenceOfMultipleGeneralization();
        return isValid;
    }

    /**
     * This method is checking all implementation rules.
     *
     * @return <b>true</b> if all implementation rules are observed.
     */
    public boolean checkImplementationRules() {
        for (IRelationship relationship : relationships) {
            if (relationship.getRelationshipType() == RelationshipsType.IMPLEMENTATION) {
                Class source = classes.get(relationship.getSource());
                Class destination = classes.get(relationship.getDestination());
                checkImplementation(source, destination);
            }
        }

        return true;
    }

    /**
     * This method is checking access level of each interface's attribute and method.
     * Also check for the initialization of attributes of interface.
     *
     * @return <b>true</b> if access level of each attribute and method are valid.
     */
    public boolean checkInterfaceDefinitionRules() {
        final Collection<Class> classCollection = classes.values();
        for (Class clazz : classCollection) {
            if (clazz.isInterface()) {
                final List<Method> methods = clazz.getMethods();
                final List<Field> fields = clazz.getFields();

                for (Method method : methods) {
                    if (!(method.getVisibility() == Visibility.PUBLIC || method.getVisibility() == Visibility.PACKAGE))
                        return false;
                }
                for (Field field : fields) {
                    if (!(field.getVisibility() == Visibility.PUBLIC || field.getVisibility() == Visibility.PACKAGE))
                        return false;
                    if (field.getValue() == null)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * This method is checking rules for using keyword abstract.
     * If at least one method is declared as an abstract then hole class must be declared as <b>abstract</b>.
     *
     * @return <b>true</b> if abstract rules are followed.
     */
    public boolean checkAbstractClassRules() {
        final Collection<Class> classCollection = classes.values();
        for (Class clazz : classCollection) {
            final List<Method> methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAbstract() && !clazz.isAbstract())
                    return false;
            }
        }
        return true;
    }

    /**
     * This method is checking for the absence of multiple-generalization.
     *
     * @return <b>true</b> if generalization rules are followed.
     */
    public boolean checkForAbsenceOfMultipleGeneralization() {
        List<Class> dstClasses = new ArrayList<Class>();
        for (IRelationship relationship : relationships){
            if (relationship.getRelationshipType() == RelationshipsType.GENERALIZATION) {
                dstClasses.add(classes.get(relationship.getDestination()));
            }
        }
        for (Class dstClass1 : dstClasses){
            for (Class dstClass2 : dstClasses)
                if (dstClass1.getId().equals(dstClass2.getId()))
                    return false;
        }

        return true;
    }

    /**
     * This method is checking implementation rules between two classes.
     *
     * @param source      Source class (parent), must be an interface.
     * @param destination Destination class (child) of implementation, must be a class.
     * @return Result of checking of implementation rules between two classes.
     *         Return <b>true</b> when source class is an interface and destination class
     *         is a class with all implemented methods or destination class is abstract class.
     */
    private boolean checkImplementation(Class source, Class destination) {
        // Abstract class mustn't implement interface's methods.
        if (destination.isAbstract())
            return true;
        // Implementation is using only for interfaces.
        if (!source.isInterface())
            return false;

        for (Method method : source.getMethods()) {
            if (!destination.getMethods().contains(method))
                return false;
        }
        return true;
    }

}
