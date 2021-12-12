package Tests;

import api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class testEdge {
    static EdgeData edgeData=new Edge(5,2,3);
    static EdgeData edgeData2=new Edge(15,12,32);


    @Test
    void testgetSrc() {
        Assertions.assertEquals(5,edgeData.getSrc());
        Assertions.assertEquals(15,edgeData2.getSrc());

    }

    @Test
    void testgetDest() {
        Assertions.assertEquals(2,edgeData.getDest());
        Assertions.assertEquals(12,edgeData2.getDest());
    }

    @Test
    void testgetWeight() {
        Assertions.assertEquals(3,edgeData.getWeight());
        Assertions.assertEquals(32,edgeData2.getWeight());
    }

}