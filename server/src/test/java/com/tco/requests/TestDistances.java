package com.tco.requests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;




public class TestDistances {

    @Test
    @DisplayName("Benzo79: distances with no places")
    public void testNoDistances(){
        Distances distances = new Distances();
        assertEquals(0, distances.size());
        assertEquals(0, distances.total());
    }

    @Test
    @DisplayName("Benzo79: distances with one places ")
    public void testOneDistances(){
        Distances distances = new Distances();
        distances.add(12345L);
        assertEquals(1, distances.size());
        assertEquals(12345L, distances.total());
    }
    @Test
    @DisplayName("Benzo79: distances with five places ")
    public void testDistances(){
        Distances distances = new Distances();
        distances.add(5L);
        distances.add(40L);
        distances.add(300L);
        distances.add(2000L);
        distances.add(10000L);
        assertEquals(5, distances.size());
        assertEquals(12345L, distances.total());
    }
    @Test
    @DisplayName("Benzo79: fail test of distances with five places ")
    public void testFailDistances(){
        Distances distances = new Distances();
        distances.add(5L);
        distances.add(10L);
        distances.add(15L);
        distances.add(20L);
        distances.add(25L);
        assertFalse(distances.total() > 100L);
    }
    @Test
    @DisplayName("Benzo79: fail test of distances with five places ")
    public void testNotEqualsDistances(){
        Distances distances = new Distances();
        distances.add(1L);
        distances.add(15L);
        distances.add(20L);
        assertNotEquals(0, distances.total());
    }
    @Test
    @DisplayName("Benzo79: fail test of distances with five places ")
    public void testTrueDistance(){
        Distances distances = new Distances();
        distances.add(3L);
        distances.add(7L);
        distances.add(10L);
        distances.add(70L);
        assertTrue(distances.total() == 90L);
    }

    @Test
    @DisplayName("joelpr02: test distances with 4 places")
    public void testHalfDistance(){
        Distances distances = new Distances();
        distances.add(20L);
        distances.add(20L);
        distances.add(20L);
        distances.add(20L);
        assertTrue(distances.total() == 80L);
    }
    @Test
    @DisplayName("Bigg52: Test distances with 6 places")
    public void testSixDistances(){
        Distances distances = new Distances();
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        assertTrue(distances.total() == 60L);
        
    }
    @Test
    @DisplayName("Bigg52 Test distances with 7 places")
    public void testSevenDistances(){
        Distances distances = new Distances();
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        distances.add(10L);
        assertTrue(distances.total() == 70L);
    }

}
