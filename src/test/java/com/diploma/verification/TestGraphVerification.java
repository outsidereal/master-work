package com.diploma.verification;

import com.diploma.parser.ClassParser;
import com.diploma.parser.Parser;
import com.diploma.parser.UMLParserException;
import junit.framework.TestCase;

/**
 * User: d.ulanovych
 * Date: 11/18/13
 */
public class TestGraphVerification extends TestCase{
    private static final String FAILED_GRAPH_DIAGRAM = "./src/test/resources/diagrams/class/test_failed-1.uml";
    private static final String WORKING_GRAPH_DIAGRAM = "./src/test/resources/diagrams/class/test_working-1.uml";

    public void testGraphVerificationWhenFailed() throws UMLParserException {
        Parser parser = new ClassParser();
        parser.parse(FAILED_GRAPH_DIAGRAM);
        Verification verification = new GraphVerification(((ClassParser)parser).getParsedRelationships());
        assertFalse(verification.verify());
    }

    public void testGraphVerificationWhenTrue() throws UMLParserException {
        Parser parser = new ClassParser();
        parser.parse(WORKING_GRAPH_DIAGRAM);
        Verification verification = new GraphVerification(((ClassParser)parser).getParsedRelationships());
        assertTrue(verification.verify());
    }
}
