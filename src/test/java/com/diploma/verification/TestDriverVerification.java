package com.diploma.verification;

import com.diploma.parser.ClassParser;
import com.diploma.parser.UMLParserException;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * User: d.ulanovych
 * Date: 11/19/13
 */
public class TestDriverVerification extends TestCase {
    private static final String FAILED_GENERALIZATION = "./src/test/resources/diagrams/class/test_failed-2.uml";

    @Test
    public void testIncorrectGeneralization() throws UMLParserException {
        ClassParser parser = new ClassParser();
        parser.parse(FAILED_GENERALIZATION);
        Verification verification = new DriverVerification(parser.getParsedClasses(), parser.getParsedRelationships());
    }


}
