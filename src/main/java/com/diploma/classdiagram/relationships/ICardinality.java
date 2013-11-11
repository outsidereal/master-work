package com.diploma.classdiagram.relationships;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 23.09.12
 * Time: 12:53
 */
public interface ICardinality {
    /**
     * Value to describe the infinity value
     */
    public static final Integer INFINITY = Integer.MAX_VALUE;

    /**
     * This method should return minimum value of source cardinality.
     *
     * @return minimum value of source cardinality
     */
    public Integer getSourceMinimum();

    /**
     * This method should return maximum value of source cardinality.
     *
     * @return maximum value of source cardinality
     */
    public Integer getSourceMaximum();

    /**
     * This method should return minimum value of destination cardinality.
     *
     * @return minimum value of destination cardinality
     */
    public Integer getDestinationMinimum();

    /**
     * This method should return minimum value of source cardinality.
     *
     * @return minimum value of source cardinality
     */
    public Integer getDestinationMaximum();

    /**
     * @param value - Minimum value of source cardinality.
     */
    public void setSourceMinimum(Integer value);

    /**
     * @param value - Maximum value of source cardinality.
     */
    public void setSourceMaximum(Integer value);

    /**
     * @param value - Minimum value of destination cardinality.
     */
    public void setDestinationMinimum(Integer value);

    /**
     * @param value - Maximum value of destination cardinality.
     */
    public void setDestinationMaximum(Integer value);
}
