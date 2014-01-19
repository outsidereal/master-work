package com.diploma.classdiagram.verification;

import com.diploma.classdiagram.model.Class;
import com.diploma.classdiagram.model.Field;
import com.diploma.classdiagram.model.Method;
import com.diploma.classdiagram.model.relationships.RelationshipType;
import com.diploma.classdiagram.model.Visibility;
import com.diploma.classdiagram.model.relationships.Relationship;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 26.01.13
 * Time: 11:20
 */
public class DriverVerification implements Verification {
    private final Map<String, Class> classes;
    private final Map<String, Relationship> relationships;


    public DriverVerification(Map<String, Class> classes, Map<String, Relationship> relationships) {
        this.classes = classes;
        this.relationships = relationships;
    }

    public boolean verify() {
        boolean isValid = true;
        isValid &= checkImplementationRules();
        isValid &= checkInterfaceDefinitionRules();
        isValid &= checkAbstractClassRules();
        isValid &= checkForAbsenceOfMultipleGeneralization();
        if (isValid){
            LOGGER.info("Driver Verification: The diagram is correct!");
        }
        return isValid;
    }

    /**
     * This method is checking all implementation rules.
     *
     * @return <b>true</b> if all implementation rules are observed.
     */
    public boolean checkImplementationRules() {
        boolean isValid = true;
        for (Relationship relationship : relationships.values()) {
            if (RelationshipType.IMPLEMENTATION.equals(relationship.getRelationshipType())) {
                Class source = classes.get(relationship.getSource());
                Class destination = classes.get(relationship.getDestination());
                if (!checkImplementation(source, destination)) {
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    /**
     * This method is checking access level of each interface's attribute and method.
     * Also check for the initialization of attributes of interface.
     *
     * @return <b>true</b> if access level of each attribute and method are valid.
     */
    public boolean checkInterfaceDefinitionRules() {
        boolean isValid = true;
        final Collection<Class> classCollection = classes.values();
        for (Class clazz : classCollection) {
            if (clazz.isInterface()) {
                final List<Method> methods = clazz.getMethods();
                final List<Field> fields = clazz.getFields();

                for (Method method : methods) {
                    if (!(Visibility.PUBLIC.equals(method.getVisibility()) || Visibility.PACKAGE.equals(method.getVisibility()))) {
                        LOGGER.error(DriverVerification.class.getSimpleName() + ". Invalid accessor of method " + method.getName() +
                                " in interface " + clazz.getName() + ".\nIt must be PUBLIC only\n\n!");
                        isValid = false;
                    }
                }
                for (Field field : fields) {
                    if (!(Visibility.PUBLIC.equals(field.getVisibility()) || Visibility.PACKAGE.equals(field.getVisibility()))) {
                        LOGGER.error(DriverVerification.class.getSimpleName() + ". Invalid accessor of field " + field.getName() +
                                " in interface " + clazz.getName() + ".\nIt must be PUBLIC only!\n\n");
                        isValid = false;
                    }
                    if (null == field.getValue()) {
                        LOGGER.error(DriverVerification.class.getSimpleName() + ". Error in field " + field.getName() +
                                " of interface " + clazz.getName() + ".\nFields in interfaces must has value.\n\n");
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * This method is checking rules for using keyword abstract.
     * If at least one method is declared as an abstract then hole class must be declared as <b>abstract</b>.
     *
     * @return <b>true</b> if abstract rules are followed.
     */
    public boolean checkAbstractClassRules() {
        boolean isValid = true;
        final Collection<Class> classCollection = classes.values();
        for (Class clazz : classCollection) {
            final List<Method> methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAbstract() && !clazz.isAbstract()) {
                    LOGGER.error(DriverVerification.class.getSimpleName() + ". Invalid modifier at method "
                            + method.getName() + " in class " + clazz.getName() +
                            ".\nIf class has at least one abstract method - class must be marked as abstract!\n\n");
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    /**
     * This method is checking for the absence of multiple-generalization.
     *
     * @return <b>true</b> if generalization rules are followed.
     */
    public boolean checkForAbsenceOfMultipleGeneralization() {
        boolean isValid = true;
        Set<Class> destinationClasses = new HashSet<Class>();
        for (Relationship relationship : relationships.values()) {
            if (RelationshipType.GENERALIZATION.equals(relationship.getRelationshipType())) {
                Class currentClass = classes.get(relationship.getDestination());
                if (destinationClasses.contains(currentClass)) {
                    LOGGER.error(DriverVerification.class.getSimpleName() + ". Invalid inheritance at " + currentClass.getName() +
                            " class.\nEach class can extends only one super-class!\n\n");
                    isValid = false;
                }
                destinationClasses.add(currentClass);
            }
        }

        return isValid;
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
        boolean isValid = true;
        // Abstract class mustn't implement interface's methods.
        if (destination.isAbstract()) {
            return isValid;
        }

        if (!source.isInterface()) {
            LOGGER.error(DriverVerification.class.getSimpleName() + ". Error at class " + source.getName() +
                    ".\n Implementation relationship is using only for interfaces!\n\n");
            return false;
        }

        for (Method method : source.getMethods()) {
            if (!destination.getMethods().contains(method)) {
                LOGGER.error(DriverVerification.class.getSimpleName() + ". Class " + destination.getName()
                        + " doesn't implement method " + method.getName() + " of interface " + source.getName() + "\n\n");
                isValid = false;
            }
        }
        return isValid;
    }

}
