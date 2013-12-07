package com.diploma.parser;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.enumerates.RelationshipType;
import com.diploma.classdiagram.enumerates.Visibility;
import com.diploma.classdiagram.relationships.Cardinality;
import com.diploma.classdiagram.relationships.CardinalityRelationship;
import com.diploma.classdiagram.relationships.Relationship;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * User: d.ulanovych
 * Date: 11/16/13
 */
public class TestClassParser extends TestCase {
    private static final String filePathRelationship = "./src/test/resources/diagrams/class/test_dep.uml";
    private static final String filePathClass = "./src/test/resources/diagrams/class/class_tester.uml";

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    @Test
    public void testClassParsing() throws UMLParserException {
        ClassParser parser = new ClassParser();
        parser.parse(filePathClass);

        Map<String, Class> classes = parser.getParsedClasses();
        Class class1 = classes.get("_xSo30P_ZEeGbE8gz2iVRag");
        Class class2 = classes.get("_0MUd4P_dEeGbE8gz2iVRag");
        Class class3 = classes.get("_sPWK0P_eEeGbE8gz2iVRag");

        assertEquals(4, classes.size());

        assertEquals("IElement", class1.getName());
        assertTrue(class1.isInterface());

        assertEquals("XMLElement", class2.getName());
        assertEquals(Visibility.PUBLIC, class2.getVisibility());
        assertFalse(class2.isInterface());
        assertFalse(class2.isFinal());
        assertFalse(class2.isStatic());
        assertTrue(class2.isAbstract());

        assertEquals("MethodParameter", class3.getName());
        assertEquals(Visibility.PROTECTED, class3.getVisibility());
        assertFalse(class3.isInterface());
        assertFalse(class3.isFinal());
        assertFalse(class3.isStatic());
        assertFalse(class3.isAbstract());
    }

    @Test
    public void testRelationshipsParsing() throws UMLParserException {
        ClassParser parser = new ClassParser();
        parser.parse(filePathRelationship);

        Map<String, Relationship> relationships = parser.getParsedRelationships();
        Relationship relationship1 = relationships.get("_PH6B8OniEeGnKNahlmSO_Q");
        Relationship relationship2 = relationships.get("_obTjYOnjEeGnKNahlmSO_Q");
        CardinalityRelationship relationship3 = (CardinalityRelationship) relationships.get("_TL3xMOnjEeGnKNahlmSO_Q");
        CardinalityRelationship relationship4 = (CardinalityRelationship) relationships.get("_U7BaoOnjEeGnKNahlmSO_Q");
        CardinalityRelationship relationship5 = (CardinalityRelationship) relationships.get("_hGVg8OnjEeGnKNahlmSO_Q");

        assertEquals(7, relationships.size());

        assertEquals("realization", relationship1.getName());
        assertEquals("_3kHSYOnfEeGnKNahlmSO_Q", relationship1.getSource());
        assertEquals("_2H82YOnfEeGnKNahlmSO_Q", relationship1.getDestination());
        assertEquals(RelationshipType.IMPLEMENTATION, relationship1.getRelationshipType());

        assertEquals("dependency", relationship2.getName());
        assertEquals("_3kHSYOnfEeGnKNahlmSO_Q", relationship2.getSource());
        assertEquals("_2H82YOnfEeGnKNahlmSO_Q", relationship2.getDestination());
        assertEquals(RelationshipType.DEPENDENCY, relationship2.getRelationshipType());

        assertEquals("comp", relationship3.getName());
        assertEquals("_20WCUOnfEeGnKNahlmSO_Q", relationship3.getSource());
        assertEquals("_5TlkkOniEeGnKNahlmSO_Q", relationship3.getDestination());
        assertEquals(RelationshipType.COMPOSITION, relationship3.getRelationshipType());
        assertTrue(1 == relationship3.getSourceMaximum());
        assertTrue(1 == relationship3.getSourceMinimum());
        assertTrue(1 == relationship3.getDestinationMaximum());
        assertTrue(1 == relationship3.getDestinationMinimum());

        assertEquals("agregacia", relationship4.getName());
        assertEquals("_5TlkkOniEeGnKNahlmSO_Q", relationship4.getSource());
        assertEquals("_2H82YOnfEeGnKNahlmSO_Q", relationship4.getDestination());
        assertEquals(RelationshipType.AGGREGATION, relationship4.getRelationshipType());
        assertTrue(1 == relationship4.getSourceMaximum());
        assertTrue(1 == relationship4.getSourceMinimum());
        assertTrue(1 == relationship4.getDestinationMaximum());
        assertTrue(1 == relationship4.getDestinationMinimum());

        assertEquals("assosiation", relationship5.getName());
        assertEquals("_20WCUOnfEeGnKNahlmSO_Q", relationship5.getSource());
        assertEquals("_2H82YOnfEeGnKNahlmSO_Q", relationship5.getDestination());
        assertEquals(RelationshipType.ASSOCIATION, relationship5.getRelationshipType());
        assertTrue(6 == relationship5.getSourceMaximum());
        assertTrue(3 == relationship5.getSourceMinimum());
        assertTrue(Cardinality.INFINITY == relationship5.getDestinationMaximum());
        assertTrue(-1  == relationship5.getDestinationMinimum());
    }

}
