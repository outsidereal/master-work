package com.diploma.verification;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.Field;
import com.diploma.classdiagram.Method;
import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.classdiagram.enumerates.Visibility;
import com.diploma.classdiagram.relationships.Relationship;

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
                        LOGGER.error(DriverVerification.class.getName() + " Invalid accessor of method " + method.getName() +
                                " in interface " + clazz.getName() + " . It must be PUBLIC only!");
                        isValid = false;
                    }
                }
                for (Field field : fields) {
                    if (!(field.getVisibility() == Visibility.PUBLIC || field.getVisibility() == Visibility.PACKAGE)) {
                        LOGGER.error(DriverVerification.class.getName() + " Invalid accessor of field " + field.getName() +
                                " in interface " + clazz.getName() + " . It must be PUBLIC only!");
                        isValid = false;
                    }
                    if (null == field.getValue()) {
                        LOGGER.error(DriverVerification.class.getName() + "Error in field " + field.getName() +
                                " of interface " + clazz.getName() + " .Fields in interfaces must has value.");
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
                    LOGGER.error(DriverVerification.class.getName() + " Invalid modifier at method " + method.getName() +
                            " in class " + clazz.getName() + " . If class has at least one abstract method - class must be marked as abstract!");
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
        List<Class> dstClasses = new ArrayList<Class>();
        for (Relationship relationship : relationships.values()) {
            if (relationship.getRelationshipType() == RelationshipType.GENERALIZATION) {
                dstClasses.add(classes.get(relationship.getDestination()));
            }
        }

        //TODO incorrect comparsion
        for (Class dstClass1 : dstClasses) {
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
        boolean isValid = true;
        // Abstract class mustn't implement interface's methods.
        if (destination.isAbstract()) {
            return isValid;
        }

        if (!source.isInterface()) {
            LOGGER.error(DriverVerification.class.getName() + " : Implementation relationship is using only for interfaces!");
            return false;
        }

        for (Method method : source.getMethods()) {
            if (!destination.getMethods().contains(method)) {
                LOGGER.error(DriverVerification.class.getName() + "Class " + destination.getName() + " doesn't implement method " +
                        method.getName() + " of interface " + source.getName());
                isValid = false;
            }
        }
        return isValid;
    }

}
