package com.diploma.classdiagram.parser;

import com.diploma.classdiagram.model.XMLElement;

import java.util.List;

/**
 * User: ZIM
 * Date: 25.08.12
 * Time: 21:30
 */
public interface Parser {

    /**
     * This method should parse well-formed XML file
     * and return list of xml elements.
     *
     * @param sourceFile - The XML file path.
     * @return - List of XML elements.
     */
    public List<XMLElement> parse(String sourceFile) throws UMLParserException;
}
