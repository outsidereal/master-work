package com.diploma.classdiagram.verification;

import org.apache.log4j.Logger;

/**
 * User: ZIM
 * Date: 26.01.13
 * Time: 15:26
 */
public interface Verification {
    public static final Logger LOGGER = Logger.getLogger(Verification.class);

    public boolean verify();
}
