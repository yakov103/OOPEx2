package Tests;

import api.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testLocation {
static Location location = new Location("10,20,0");
static Location location2 = new Location("10,10,0");

    @Test
    void x() {
        assertEquals(10,location.x());
    }

    @Test
    void y() {
        assertEquals(20, location.y());
    }

    @Test
    void z() {
        assertEquals(0, location.z());
    }

    @Test
    void distance() {
        assertEquals(10, location.distance(location2));
    }

}