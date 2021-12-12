package Tests;


import api.Location;
import api.NodeData;
import api.NodeV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class testNode {
    static NodeData nodeData=new NodeV(8,"10,5,0");
    static NodeData nodeData2=new NodeV(9,"1,2,0");
    static NodeData nodeData3=new NodeV(9,"1,2,0");



    @Test
    void testgetKey() {
        Assertions.assertEquals(8,nodeData.getKey());
        Assertions.assertEquals(9,nodeData2.getKey());

    }

    @Test
    void getLocation() {
        assertEquals(10, nodeData.getLocation().x());
        assertEquals(5, nodeData.getLocation().y());
        assertEquals(0, nodeData.getLocation().z());

        assertEquals(1, nodeData2.getLocation().x());
        assertEquals(2, nodeData2.getLocation().y());
        assertEquals(0, nodeData2.getLocation().z());
    }

    @Test
    void setLocation() {
        Location location = new Location("10,20,0");
        nodeData3.setLocation(location);
        assertEquals(10, nodeData3.getLocation().x());
        assertEquals(20, nodeData3.getLocation().y());
        assertEquals(0, nodeData3.getLocation().z());
    }


    @Test
    void getInfo() {
        assertEquals("white", nodeData.getInfo());
    }

    @Test
    void setInfo() {
        nodeData.setInfo("black");
        assertEquals("black", nodeData.getInfo());
    }


}