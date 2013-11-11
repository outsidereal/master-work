package com.diploma.parser;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.Field;
import com.diploma.classdiagram.Method;
import com.diploma.classdiagram.MethodParameter;
import com.diploma.classdiagram.enumerates.RelationshipsType;
import com.diploma.classdiagram.enumerates.Visibility;
import com.diploma.classdiagram.relationships.CardinalityRelationship;
import com.diploma.classdiagram.relationships.IRelationship;
import com.diploma.classdiagram.relationships.Relationship;
import com.diploma.global.Constants;
import com.diploma.global.IElement;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 25.08.12
 * Time: 22:45
 */
public class ClassParser implements IParser {

    private Map<String,Class> classes;
    private List<IRelationship> relationships;

    public ClassParser() {
        classes = new HashMap<String, Class>();
        relationships = new ArrayList<IRelationship>();
    }

    public Map<String, Class> getParsedClasses() {
        return classes;
    }

    public List<IRelationship> getParsedRelationships() {
        return relationships;
    }

    public List<IElement> parse(String sourceFile) {

        Document document = getDocument(sourceFile);
        if (document == null) return null;

        // Getting the root element of xml file
        Element root = document.getRootElement();
        if (root == null) return null;

        // iterate through child elements of root with
        // element name "packagedElement"
        for (Iterator iterator = root.elementIterator(
                Constants.PACKAGED_ELEMENT); iterator.hasNext(); ) {
            // getting current packaged element
            Element packagedElement = (Element) iterator.next();

            //getting attribute "type"
            // (it could be also "xmi:type" or something like that)
            Attribute type = packagedElement.attribute(Constants.TYPE);
            if (type == null) return null;

            //getting value of type
            String typeValue = type.getValue();

            //deciding what kind of element it is.
            //there are two kinds :
            //single relationship or class (interface is also class)
            if (typeValue.equals(Constants.CLASS) ||
                    typeValue.equals(Constants.INTERFACE)) {
                parseClassElement(packagedElement, classes);
            } else if (typeValue.equals(Constants.REALIZATION) ||
                    typeValue.equals(Constants.DEPENDENCY) ||
                    typeValue.equals(Constants.ASSOCIATION)) {
                parseRelationshipElement(packagedElement, relationships);
            } else {
                //We are simply skipping this elements
            }
        }
        return null;
    }

    /**
     * This method should parse class elements (also interfaces elements)
     * and return the map of current UML diagram.
     * Parsed element is adding to current class map.
     *
     * @param classElement - Current XML class element.
     * @param classes      - Map of classes where result storing.
     * @return The same list of classes with new parsed class element.
     */
    private Map<String,Class> parseClassElement(Element classElement, Map<String,Class> classes) {
        //create new instance of class
        Class newClass = new Class();

        //set main attributes
        newClass.setName(getAttributeValue(classElement, Constants.NAME));
        newClass.setId(getAttributeValue(classElement, Constants.ID));

        //set visibility type
        newClass.setVisibility(getVisibility(classElement));

        //check if final, static or abstract
        newClass.setFinal(getBooleanAttributeValue(classElement, Constants.IS_LEAF));
        newClass.setStatic(getBooleanAttributeValue(classElement, Constants.IS_STATIC));
        newClass.setAbstract(getBooleanAttributeValue(classElement, Constants.IS_ABSTRACT));

        //check if it class or interface
        newClass.setInterface(classElement.attribute(Constants.TYPE).getValue().equals(Constants.INTERFACE));

        List<Field> fields = new ArrayList<Field>();
        List<Method> methods = new ArrayList<Method>();

        parseClassFields(classElement, fields);
        parseClassMethods(classElement, methods);

        // try to find generalizations elements
        if (classElement.element(Constants.GENERALIZATION) != null) {
            parseRealizations(classElement);
        }

        newClass.setFields(fields);
        newClass.setMethods(methods);

        classes.put(newClass.getId(), newClass);

        return classes;
    }

    /**
     * This method should parse class field element
     * and return the list of fields for current class.
     * Parsed element is adding to current fields list.
     *
     * @param element - Current XML class element.
     * @param fields  - List of fields of current class where result storing.
     * @return The same list of fields with new parsed field.
     */
    private List<Field> parseClassFields(Element element, List<Field> fields) {

        // iterate through class attributes
        for (Iterator iterator = element.elementIterator(Constants.ATTRIBUTE); iterator.hasNext(); ) {
            // getting current attribute element
            Element attributeElement = (Element) iterator.next();
            Field field = new Field();

            field.setId(getAttributeValue(attributeElement, Constants.ID));
            field.setName(getAttributeValue(attributeElement, Constants.NAME));
            field.setVisibility(getVisibility(attributeElement));
            field.setFinal(getBooleanAttributeValue(attributeElement, Constants.IS_LEAF));
            field.setStatic(getBooleanAttributeValue(attributeElement, Constants.IS_STATIC));

            field.setType(getAttributeValue(attributeElement, Constants.TYPE));

            // check for default value
            Element defaultValueElement = attributeElement.element(Constants.DEFAULT_VALUE);
            if (defaultValueElement != null) {
                field.setValue(getAttributeValue(defaultValueElement, Constants.VALUE));
            }

            // if not user defined data type
            if (field.getType().equals(Constants.EMPTY_STRING)) {
                Element typeElement = attributeElement.element(Constants.TYPE);

                if (typeElement != null) {
                    field.setType(getAttributeValue(typeElement, Constants.TYPE_HREF));
                }
            }

            // adding parsed attribute
            fields.add(field);
        }

        return fields;
    }

    /**
     * This method should parse class method element
     * and return the list of methods for current class.
     * Parsed element is adding to current methods list.
     *
     * @param element - Current XML class element.
     * @param methods - List of methods of current class where result storing.
     * @return The same list of methods with new parsed method.
     */
    private List<Method> parseClassMethods(Element element, List<Method> methods) {

        // iterate through class methods
        for (Iterator iterator = element.elementIterator(Constants.METHOD); iterator.hasNext(); ) {
            // getting current method element
            Element methodElement = (Element) iterator.next();
            Method method = new Method();

            method.setId(getAttributeValue(methodElement, Constants.ID));
            method.setName(getAttributeValue(methodElement, Constants.NAME));

            method.setVisibility(getVisibility(methodElement));
            method.setFinal(getBooleanAttributeValue(methodElement, Constants.IS_LEAF));
            method.setStatic(getBooleanAttributeValue(methodElement, Constants.IS_STATIC));
            method.setAbstract(getBooleanAttributeValue(methodElement, Constants.IS_ABSTRACT));

            List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();

            for (Iterator it = methodElement.elementIterator(Constants.METHOD_PARAMETER); it.hasNext(); ) {

                Element parameterElement = (Element) it.next();
                MethodParameter methodParameter = new MethodParameter();

                methodParameter.setId(getAttributeValue(parameterElement, Constants.ID));
                methodParameter.setName(getAttributeValue(parameterElement, Constants.NAME));
                methodParameter.setType(getAttributeValue(parameterElement, Constants.TYPE));

                // check if it's return parameter
                String direction = getAttributeValue(parameterElement, Constants.DIRECTION);
                boolean isRetVal = !direction.equals(Constants.EMPTY_STRING) && direction.equals(Constants.RETURN);

                Element typeElement = parameterElement.element(Constants.TYPE);
                if (typeElement != null) {
                    methodParameter.setType(getAttributeValue(typeElement, Constants.TYPE_HREF));
                }

                if (isRetVal) {
                    method.setReturnType(methodParameter.getType());
                } else {
                    methodParameters.add(methodParameter);
                }

                method.setParameters(methodParameters);
            }

            methods.add(method);
        }

        return methods;
    }

    /**
     * This method parse current "packagedElement" that is relationship.
     * It is parse all kinds of relationships except Generalization.
     *
     * @param relationshipElement - Current "packagedElement" in the tree.
     * @param relationships       - List of relationships where would be
     *                            stored current parsed relationship.
     * @return List with parsed relationship.
     */
    private List<IRelationship> parseRelationshipElement(Element relationshipElement, List<IRelationship> relationships) {
        //getting name of element
        String typeName = getAttributeValue(relationshipElement, Constants.TYPE);

        //deciding what kind of association it is.
        IRelationship relationship = new Relationship();
        relationship.setSingleElement(true);
        //simple alone (dependency and realization)
        if (typeName.equals(Constants.DEPENDENCY) || typeName.equals(Constants.REALIZATION)) {
            relationship.setName(getAttributeValue(relationshipElement, Constants.NAME));
            relationship.setId(getAttributeValue(relationshipElement, Constants.ID));
            relationship.setSource(getAttributeValue(relationshipElement, Constants.SUPPLIER));
            relationship.setDestination(getAttributeValue(relationshipElement, Constants.CLIENT));
            relationship.setRelationshipType(typeName.equals(Constants.DEPENDENCY) ? RelationshipsType.DEPENDENCY : RelationshipsType.IMPLEMENTATION);
        }
        //structured alone relationship (composition, aggregation and association)
        else {
            relationship = parseSingleRelationship(relationshipElement);
        }

        // add parsed relationship to list
        relationships.add(relationship);

        return relationships;
    }

    /**
     * This method parse current DOM element that is
     * "packagedElement" for relationship and have 3-level structure.
     *
     * @param relationshipElement - DOM element to parse.
     * @return Parsed IRelationship element.
     *         It could be Aggregation, Composition or Association.
     */
    private IRelationship parseSingleRelationship(Element relationshipElement) {
        IRelationship relationship = new CardinalityRelationship();

        // set the name and identifier of element
        relationship.setName(getAttributeValue(relationshipElement, Constants.NAME));
        relationship.setId(getAttributeValue(relationshipElement, Constants.ID));
        relationship.setSingleElement(true);

        // get the source and destination describe elements
        for (Iterator iterator = relationshipElement.elementIterator(Constants.OWNED_END); iterator.hasNext(); ) {
            // getting current owned element
            Element element = (Element) iterator.next();
            if (getAttributeValue(element, Constants.NAME).equals(Constants.SRC)) {
                //set the identifier of source class
                relationship.setSource(getAttributeValue(element, Constants.TYPE));

                Element upperValue = element.element(Constants.UPPER_VALUE);
                Element lowerValue = element.element(Constants.LOWER_VALUE);

                ((CardinalityRelationship) relationship).setSourceMinimum(getAttributeValue(lowerValue, Constants.VALUE));
                ((CardinalityRelationship) relationship).setSourceMaximum(getAttributeValue(upperValue, Constants.VALUE));
            } else {
                //set the identifier of destination class
                relationship.setDestination(getAttributeValue(element, Constants.TYPE));

                Element upperValue = element.element(Constants.UPPER_VALUE);
                Element lowerValue = element.element(Constants.LOWER_VALUE);

                ((CardinalityRelationship) relationship).setDestinationMinimum(getAttributeValue(lowerValue, Constants.VALUE));
                ((CardinalityRelationship) relationship).setDestinationMaximum(getAttributeValue(upperValue, Constants.VALUE));

                //this attribute describes relationship type.
                Attribute attribute = element.attribute(Constants.AGGREGATION_TYPE);

                if (attribute == null) {
                    relationship.setRelationshipType(RelationshipsType.ASSOCIATION);
                } else if (attribute.getValue().equals(Constants.AGGREGATION)) {
                    relationship.setRelationshipType(RelationshipsType.AGGREGATION);
                } else {
                    relationship.setRelationshipType(RelationshipsType.COMPOSITION);
                }
            }
        }

        return relationship;
    }

    /**
     * This method parse class element and find all realizations
     *
     * @param classElement - Current class element.
     * @return List of realizations.
     */
    private List<IRelationship> parseRealizations(Element classElement) {
        List<IRelationship> generalizations = new ArrayList<IRelationship>();

        for (Iterator iterator = classElement.elementIterator(Constants.GENERALIZATION); iterator.hasNext(); ) {
            Element generalization = (Element) iterator.next();

            IRelationship relationship = new Relationship();

            relationship.setId(getAttributeValue(generalization, Constants.ID));
            relationship.setName(getAttributeValue(generalization, Constants.NAME));
            relationship.setSingleElement(false);
            relationship.setRelationshipType(RelationshipsType.GENERALIZATION);
            relationship.setSource(getAttributeValue(generalization, Constants.GENERAL));
            relationship.setDestination(getAttributeValue(generalization.getParent(), Constants.ID));

            generalizations.add(relationship);
        }

        relationships.addAll(generalizations);

        return generalizations;
    }

    /**
     * This method return the value of chosen attribute of element.
     * If element have no such attribute the result would be empty string.
     *
     * @param element       - Current element to parse.
     * @param attributeName - Name of attribute
     * @return Value of current element
     */
    private String getAttributeValue(Element element, String attributeName) {
        if (element == null) throw new NullPointerException();

        Attribute attribute = element.attribute(attributeName);

        if (attribute == null) {
            return Constants.EMPTY_STRING;
        }

        String value = attribute.getValue();

        if (value == null) {
            return Constants.EMPTY_STRING;
        }

        return value;
    }

    /**
     * This method returns true or false value for attribute of element
     *
     * @param element       - Current element
     * @param attributeName - Name of attribute
     * @return true or false
     */
    private boolean getBooleanAttributeValue(Element element, String attributeName) {
        if (element == null) throw new NullPointerException();

        Attribute attribute = element.attribute(attributeName);

        if (attribute == null) {
            return false;
        }

        String value = attribute.getValue();

        if (value == null || value.equals(Constants.FALSE)) {
            return false;
        }

        return true;
    }

    /**
     * This method should return the visibility type for current element
     *
     * @param element - Current element
     * @return Visibility type
     * @see Visibility
     */
    private Visibility getVisibility(Element element) {
        Visibility visibility = Visibility.PUBLIC;

        String visibilityValue = getAttributeValue(element, Constants.VISIBILITY);

        if (visibilityValue.equals(Constants.EMPTY_STRING) || visibilityValue.equals(Constants.PUBLIC)) {
            visibility = Visibility.PUBLIC;
        } else if (visibilityValue.equals(Constants.PRIVATE)) {
            visibility = Visibility.PRIVATE;
        } else if (visibilityValue.equals(Constants.PACKAGED)) {
            visibility = Visibility.PACKAGE;
        } else if (visibilityValue.equals(Constants.PROTECTED)) {
            visibility = Visibility.PROTECTED;
        }
        return visibility;
    }

    /**
     * This method parsing the XML file
     * and return DOM object.
     *
     * @param filePath - Path to XML file that would be parsed.
     * @return - The Document object.
     */
    private Document getDocument(String filePath) {
        SAXReader reader = new SAXReader();
        Document document = null;

        try {
            document = reader.read(filePath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
}
