import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TrainTest {
    //UC 8 - filter bogies by capacity tests
    @Test
    void testFilter_CapacityGreaterThanThreshold() {
        List<Bogie> bogies = List.of(
                new Bogie("Sleeper", 72),
                new Bogie("Cargo", 120),
                new Bogie("AC", 56)
        );

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertEquals(2, result.size());
    }

    @Test
    void testFilter_CapacityEqualToThreshold() {
        List<Bogie> bogies = List.of(
                new Bogie("Sleeper", 70)
        );

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFilter_CapacityLessThanThreshold() {
        List<Bogie> bogies = List.of(
                new Bogie("AC", 50)
        );

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertTrue(result.isEmpty());
    }


    @Test
    void testFilter_MultipleBogiesMatching() {
        List<Bogie> bogies = List.of(
                new Bogie("Sleeper", 80),
                new Bogie("Cargo", 120),
                new Bogie("AC", 40)
        );

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertEquals(2, result.size());
    }


    @Test
    void testFilter_NoBogiesMatching() {
        List<Bogie> bogies = List.of(
                new Bogie("AC", 40),
                new Bogie("Chair", 50)
        );

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFilter_AllBogiesMatching() {
        List<Bogie> bogies = List.of(
                new Bogie("Sleeper", 80),
                new Bogie("Cargo", 120)
        );

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertEquals(bogies.size(), result.size());
    }

    @Test
    void testFilter_EmptyBogieList() {
        List<Bogie> bogies = new ArrayList<>();

        List<Bogie> result = Train.filterBogiesByCapacity(bogies, 70);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFilter_OriginalListUnchanged() {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 80));
        bogies.add(new Bogie("AC", 50));

        int originalSize = bogies.size();

        Train.filterBogiesByCapacity(bogies, 70);

        assertEquals(originalSize, bogies.size());
    }





    //UC 9 - group bogies by type tests
    private List<Bogie> getSampleBogies() {
        return Arrays.asList(
                new Bogie("Sleeper", 72),
                new Bogie("AC Chair", 56),
                new Bogie("First Class", 24),
                new Bogie("Sleeper", 80)
        );
    }

    private List<Bogie> filterWithLoop(List<Bogie> bogies, int threshold) {
        List<Bogie> filtered = new ArrayList<>();
        for (Bogie bogie : bogies) {
            if (bogie.capacity > threshold) {
                filtered.add(bogie);
            }
        }
        return filtered;
    }

    @Test
    void testGrouping_BogiesGroupedByType() {
        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(getSampleBogies());

        assertTrue(grouped.containsKey("Sleeper"));
        assertEquals(2, grouped.get("Sleeper").size());
    }

    @Test
    void testGrouping_MultipleBogiesInSameGroup() {
        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(getSampleBogies());

        assertEquals(2, grouped.get("Sleeper").size());
    }

    @Test
    void testGrouping_DifferentBogieTypes() {
        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(getSampleBogies());

        assertEquals(3, grouped.size());
    }

    @Test
    void testGrouping_EmptyBogieList() {
        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(new ArrayList<>());

        assertTrue(grouped.isEmpty());
    }

    @Test
    void testGrouping_SingleBogieCategory() {
        List<Bogie> bogies = Arrays.asList(
                new Bogie("Sleeper", 72),
                new Bogie("Sleeper", 80)
        );

        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(bogies);

        assertEquals(1, grouped.size());
        assertTrue(grouped.containsKey("Sleeper"));
    }

    @Test
    void testGrouping_MapContainsCorrectKeys() {
        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(getSampleBogies());

        assertTrue(grouped.containsKey("Sleeper"));
        assertTrue(grouped.containsKey("AC Chair"));
        assertTrue(grouped.containsKey("First Class"));
    }

    @Test
    void testGrouping_GroupSizeValidation() {
        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(getSampleBogies());

        assertEquals(2, grouped.get("Sleeper").size());
        assertEquals(1, grouped.get("AC Chair").size());
        assertEquals(1, grouped.get("First Class").size());
    }

    @Test
    void testGrouping_OriginalListUnchanged() {
        List<Bogie> bogies = getSampleBogies();
        int originalSize = bogies.size();

        Map<String, List<Bogie>> grouped =
                Train.groupBogiesByType(bogies);

        assertEquals(originalSize, bogies.size());
        assertEquals(4, bogies.size());
    }





    //UC10: total seating capacity
    @Test
    void testReduce_TotalSeatCalculation() {
        int total = Train.aggregateCapacity(getSampleBogies());

        assertEquals(232, total);
    }

    @Test
    void testReduce_MultipleBogiesAggregation() {
        int total = Train.aggregateCapacity(getSampleBogies());

        assertEquals(232, total);
    }

    @Test
    void testReduce_SingleBogieCapacity() {
        List<Bogie> bogies = Collections.singletonList(
                new Bogie("Sleeper", 72)
        );

        int total = Train.aggregateCapacity(bogies);

        assertEquals(72, total);
    }

    @Test
    void testReduce_EmptyBogieList() {
        List<Bogie> bogies = new ArrayList<>();

        int total = Train.aggregateCapacity(bogies);

        assertEquals(0, total);
    }

    @Test
    void testReduce_CorrectCapacityExtraction() {
        List<Bogie> bogies = getSampleBogies();

        int total = Train.aggregateCapacity(bogies);

        int expected = bogies.stream().mapToInt(b -> b.capacity).sum();

        assertEquals(expected, total);
    }

    @Test
    void testReduce_AllBogiesIncluded() {
        List<Bogie> bogies = getSampleBogies();

        int total = Train.aggregateCapacity(bogies);

        assertEquals(232, total);
    }

    @Test
    void testReduce_OriginalListUnchanged() {
        List<Bogie> bogies = getSampleBogies();
        int originalSize = bogies.size();

        Train.aggregateCapacity(bogies);

        assertEquals(originalSize, bogies.size());
        assertEquals(4, bogies.size());
    }





    //UC11: Validation Check
    @Test
    void testRegex_ValidTrainID() {
        assertTrue(Train.isValidTrainId("TRN-1234"));
    }

    @Test
    void testRegex_InvalidTrainIDFormat() {
        assertFalse(Train.isValidTrainId("TRAIN12"));
        assertFalse(Train.isValidTrainId("TRN12A"));
        assertFalse(Train.isValidTrainId("1234-TRN"));
    }

    @Test
    void testRegex_ValidCargoCode() {
        assertTrue(Train.isValidCargoCode("PET-AB"));
    }

    @Test
    void testRegex_InvalidCargoCodeFormat() {
        assertFalse(Train.isValidCargoCode("PET-ab"));
        assertFalse(Train.isValidCargoCode("PET123"));
        assertFalse(Train.isValidCargoCode("AB-PET"));
    }

    @Test
    void testRegex_TrainIDDigitLengthValidation() {
        assertFalse(Train.isValidTrainId("TRN-123"));
        assertFalse(Train.isValidTrainId("TRN-12345"));
    }

    @Test
    void testRegex_CargoCodeUppercaseValidation() {
        assertFalse(Train.isValidCargoCode("PET-ab"));
    }

    @Test
    void testRegex_EmptyInputHandling() {
        assertFalse(Train.isValidTrainId(""));
        assertFalse(Train.isValidCargoCode(""));
    }

    @Test
    void testRegex_ExactPatternMatch() {
        assertFalse(Train.isValidTrainId("TRN-1234XYZ"));
        assertFalse(Train.isValidCargoCode("PET-AB123"));
    }

    //UC12: safety compliance validation
    @Test
    void testSafety_AllBogiesValid() {
        List<Train.GoodsBogie> goodsBogies = List.of(
                new Train.GoodsBogie("Cylindrical", "Petroleum"),
                new Train.GoodsBogie("Open", "Coal"),
                new Train.GoodsBogie("Box", "Grain")
        );

        assertTrue(Train.allMatch(goodsBogies));
    }

    @Test
    void testSafety_CylindricalWithInvalidCargo() {
        List<Train.GoodsBogie> goodsBogies = List.of(
                new Train.GoodsBogie("Cylindrical", "Coal")
        );

        assertFalse(Train.allMatch(goodsBogies));
    }

    @Test
    void testSafety_NonCylindricalBogiesAllowed() {
        List<Train.GoodsBogie> goodsBogies = List.of(
                new Train.GoodsBogie("Open", "Coal"),
                new Train.GoodsBogie("Box", "Grain")
        );

        assertTrue(Train.allMatch(goodsBogies));
    }

    @Test
    void testSafety_MixedBogiesWithViolation() {
        List<Train.GoodsBogie> goodsBogies = List.of(
                new Train.GoodsBogie("Cylindrical", "Petroleum"),
                new Train.GoodsBogie("Open", "Coal"),
                new Train.GoodsBogie("Cylindrical", "Grain")
        );

        assertFalse(Train.allMatch(goodsBogies));
    }

    @Test
    void testSafety_EmptyBogieList() {
        assertTrue(Train.allMatch(Collections.emptyList()));
    }

    //UC13: Performance comparison tests
    @Test
    void testLoopFilteringLogic() {
        List<Bogie> bogies = List.of(
                new Bogie("A", 80),
                new Bogie("B", 60),
                new Bogie("C", 61),
                new Bogie("D", 40)
        );

        List<Bogie> loopResult = filterWithLoop(bogies, 60);

        assertEquals(2, loopResult.size());
        assertTrue(loopResult.stream().allMatch(b -> b.capacity > 60));
    }

    @Test
    void testStreamFilteringLogic() {
        List<Bogie> bogies = List.of(
                new Bogie("A", 80),
                new Bogie("B", 60),
                new Bogie("C", 61),
                new Bogie("D", 40)
        );

        List<Bogie> streamResult = Train.filterBogiesByCapacity(bogies, 60);

        assertEquals(2, streamResult.size());
        assertTrue(streamResult.stream().allMatch(b -> b.capacity > 60));
    }

    @Test
    void testLoopAndStreamResultsMatch() {
        List<Bogie> bogies = List.of(
                new Bogie("A", 80),
                new Bogie("B", 60),
                new Bogie("C", 61),
                new Bogie("D", 120),
                new Bogie("E", 59)
        );

        List<Bogie> loopResult = filterWithLoop(bogies, 60);
        List<Bogie> streamResult = Train.filterBogiesByCapacity(bogies, 60);

        assertEquals(loopResult.size(), streamResult.size());
    }

    @Test
    void testExecutionTimeMeasurement() {
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            bogies.add(new Bogie("T" + i, i % 200));
        }

        long start = System.nanoTime();
        List<Bogie> streamResult = Train.filterBogiesByCapacity(bogies, 60);
        long end = System.nanoTime();

        long elapsed = end - start;

        assertTrue(elapsed > 0);
        assertNotNull(streamResult);
    }

    @Test
    void testLargeDatasetProcessing() {
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            bogies.add(new Bogie("T" + i, i % 200));
        }

        List<Bogie> loopResult = filterWithLoop(bogies, 60);
        List<Bogie> streamResult = Train.filterBogiesByCapacity(bogies, 60);

        assertNotNull(loopResult);
        assertNotNull(streamResult);
        assertEquals(loopResult.size(), streamResult.size());
    }

    //UC14: Invalid capacity exception handling
    @Test
    void testException_ValidCapacityCreation() {
        assertDoesNotThrow(() -> new Train.PassengerBogie("Sleeper", 72));
    }

    @Test
    void testException_NegativeCapacityThrowsException() {
        assertThrows(Train.InvalidCapacityException.class,
                () -> new Train.PassengerBogie("Sleeper", -10));
    }

    @Test
    void testException_ZeroCapacityThrowsException() {
        assertThrows(Train.InvalidCapacityException.class,
                () -> new Train.PassengerBogie("Sleeper", 0));
    }

    @Test
    void testException_ExceptionMessageValidation() {
        Train.InvalidCapacityException exception = assertThrows(Train.InvalidCapacityException.class,
                () -> new Train.PassengerBogie("Sleeper", 0));

        assertEquals("Capacity must be greater than zero", exception.getMessage());
    }

    @Test
    void testException_ObjectIntegrityAfterCreation() throws Train.InvalidCapacityException {
        Train.PassengerBogie bogie = new Train.PassengerBogie("Sleeper", 72);

        assertEquals("Sleeper", bogie.type);
        assertEquals(72, bogie.capacity);
    }

    @Test
    void testException_MultipleValidBogiesCreation() {
        assertDoesNotThrow(() -> {
            Train.PassengerBogie b1 = new Train.PassengerBogie("Sleeper", 72);
            Train.PassengerBogie b2 = new Train.PassengerBogie("AC Chair", 56);
            Train.PassengerBogie b3 = new Train.PassengerBogie("First Class", 24);

            assertNotNull(b1);
            assertNotNull(b2);
            assertNotNull(b3);
        });
    }

}