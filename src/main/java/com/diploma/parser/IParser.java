package com.diploma.parser;

import com.diploma.global.IElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 25.08.12
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public interface IParser {

    /**
     * This method should parse well-formed XML file
     * and return list of xml elements.
     *
     * @param sourceFile - The XML file path.
     * @return - List of XML elements.
     */
    public List<IElement> parse(String sourceFile);
}
