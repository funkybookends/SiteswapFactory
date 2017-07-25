package com.ignoretheextraclub.siteswapfactory.siteswap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 Created by caspar on 26/07/17.
 */
public interface Thro extends Comparable
{
    /**
     Returns the number of beats before all objects in the throw land
     @return
     */
    @JsonProperty("throw_int")
    int getNumBeats();

    /**
     Get the number of objects needed to make this throw.

     @return int number of objects
     */
    int getNumObjectsThrown();
}
