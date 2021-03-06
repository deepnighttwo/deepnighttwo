/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.googlecode.flickr2twitter.com.aetrion.flickr;

import java.util.Comparator;

/**
 * Compare Parameter sorting on the key in alphabetical order.
 *
 * @author Anthony Eden
 */
public class ParameterAlphaComparator implements Comparator<Parameter> {

    /**
     * Compare the two objects
     *
     * @param o1 The first parameter
     * @param o2 The second parameter
     * @return The comparison results
     */
    public int compare(Parameter o1, Parameter o2) {
        Parameter p1 = (Parameter) o1;
        Parameter p2 = (Parameter) o2;
        return p1.getName().compareTo(p2.getName());
    }
}
