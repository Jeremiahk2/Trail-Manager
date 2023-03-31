package edu.ncsu.csc316.trail.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.trail.data.Landmark;
import edu.ncsu.csc316.trail.dsa.DSAFactory;
import edu.ncsu.csc316.trail.dsa.DataStructure;

public class TrailManagerTest {
	
	private TrailManager manager;
	/**
     * Create a new instance of an array-based list before each test case executes
	 * @throws FileNotFoundException 
     */
    @Before
    public void setUp() throws FileNotFoundException {
        manager = new TrailManager("input/landmarks_sample.csv", "input/trails_sample.csv");
    }
	@Test
	public void testGetDistancesToDestinations() {
		DSAFactory.setMapType(DataStructure.UNORDEREDLINKEDMAP);
		Map<Landmark, Integer> map = DSAFactory.getMap(null);
		
		map = manager.getDistancesToDestinations("L11");
		assertTrue(1066 == map.get(manager.getLandmarkByID("L12")));
		
		map = manager.getDistancesToDestinations("L12");
		assertTrue(1066 == map.get(manager.getLandmarkByID("L11")));
		
		map = manager.getDistancesToDestinations("L01");
		assertTrue(3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(6626 == map.get(manager.getLandmarkByID("L10")));
		assertTrue(1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(3490 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(1046 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(5250 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(6289 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(9201 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(11092 == map.get(manager.getLandmarkByID("L08")));
		assertNull(map.get(manager.getLandmarkByID("L01")));
		
		map = manager.getDistancesToDestinations("L02");
		assertTrue(3013 == map.get(manager.getLandmarkByID("L01")));
		
		assertTrue(3613 == map.get(manager.getLandmarkByID("L10")));
		assertTrue(3013 + 1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(3013 + 1179 + 2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(3013 + 1046 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(3013 + 1046 + 4204 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(3013 + 1046 + 4204 + 1039 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(3013 + 1046 + 4204 + 1039 + 2912 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(3013 + 1046 + 4204 + 1039 + 2912 + 1891 == map.get(manager.getLandmarkByID("L08")));
		
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L02")));
		
		map = manager.getDistancesToDestinations("L03");
		assertTrue(1046 == map.get(manager.getLandmarkByID("L01")));
		assertTrue(1046 + 3013 + 3613 == map.get(manager.getLandmarkByID("L10")));
		assertTrue(1046 + 1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(1046 + 1179 + 2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(1046 + 3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(4204 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(4204 + 1039 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(4204 + 1039 + 2912 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(4204 + 1039 + 2912 + 1891 == map.get(manager.getLandmarkByID("L08")));
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L03")));
		
		map = manager.getDistancesToDestinations("L04");
		assertTrue(2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(1179 == map.get(manager.getLandmarkByID("L01")));
		assertTrue(1179 + 3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(1179 + 3013 + 3613 == map.get(manager.getLandmarkByID("L10")));
		assertTrue(1179 + 1046 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(1179 + 1046 + 4204 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(1179 + 1046 + 4204 + 1039 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(1179 + 1046 + 4204 + 1039 + 2912 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(1179 + 1046 + 4204 + 1039 + 2912 + 1891 == map.get(manager.getLandmarkByID("L08")));
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L04")));
		
		map = manager.getDistancesToDestinations("L05");
		assertTrue(4204 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(4204 + 1046 == map.get(manager.getLandmarkByID("L01")));
		assertTrue(4204 + 1046 + 1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(4204 + 1046 + 1179 + 2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(4204 + 1046 + 3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(4204 + 1046 + 3013 + 3613 == map.get(manager.getLandmarkByID("L10")));
		assertTrue(1039 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(1039 + 2912 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(1039 + 2912 + 1891 == map.get(manager.getLandmarkByID("L08")));
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L05")));
		
		map = manager.getDistancesToDestinations("L06");
		assertTrue(1039 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(1039 + 4204 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(1039 + 4204 + 1046 == map.get(manager.getLandmarkByID("L01")));
		assertTrue(1039 + 4204 + 1046 + 1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(1039 + 4204 + 1046 + 1179 + 2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(1039 + 4204 + 1046 + 3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(1039 + 4204 + 1046 + 3013 + 3613 == map.get(manager.getLandmarkByID("L10")));
		assertTrue(2912 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(2912 + 1891 == map.get(manager.getLandmarkByID("L08")));
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L06")));
		
		map = manager.getDistancesToDestinations("L07");
		assertTrue(2912 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(2912 + 1039 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(2912 + 1039 + 4204 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(2912 + 1039 + 4204 + 1046 == map.get(manager.getLandmarkByID("L01")));
		assertTrue(2912 + 1039 + 4204 + 1046 + 1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(2912 + 1039 + 4204 + 1046 + 1179 + 2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(2912 + 1039 + 4204 + 1046 + 3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(2912 + 1039 + 4204 + 1046 + 3013 + 3613 == map.get(manager.getLandmarkByID("L10")));;
		assertTrue(1891 == map.get(manager.getLandmarkByID("L08")));
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L07")));
		
		map = manager.getDistancesToDestinations("L08");
		assertTrue(1891 == map.get(manager.getLandmarkByID("L07")));
		assertTrue(1891 + 2912 == map.get(manager.getLandmarkByID("L06")));
		assertTrue(1891 + 2912 + 1039 == map.get(manager.getLandmarkByID("L05")));
		assertTrue(1891 + 2912 + 1039 + 4204 == map.get(manager.getLandmarkByID("L03")));
		assertTrue(1891 + 2912 + 1039 + 4204 + 1046 == map.get(manager.getLandmarkByID("L01")));
		assertTrue(1891 + 2912 + 1039 + 4204 + 1046 + 1179 == map.get(manager.getLandmarkByID("L04")));
		assertTrue(1891 + 2912 + 1039 + 4204 + 1046 + 1179 + 2311 == map.get(manager.getLandmarkByID("L09")));
		assertTrue(1891 + 2912 + 1039 + 4204 + 1046 + 3013 == map.get(manager.getLandmarkByID("L02")));
		assertTrue(1891 + 2912 + 1039 + 4204 + 1046 + 3013 + 3613 == map.get(manager.getLandmarkByID("L10")));;
		assertNull(map.get(manager.getLandmarkByID("L11")));
		assertNull(map.get(manager.getLandmarkByID("L12")));
		assertNull(map.get(manager.getLandmarkByID("L08")));
		
		
		
		
		
		
	}

	@Test
	public void testGetLandmarkByID() {
		Landmark landmark1 = manager.getLandmarkByID("L01");
		assertEquals("Park Entrance", landmark1.getDescription());
		assertEquals("Location", landmark1.getType());
		
		Landmark landmark2 = manager.getLandmarkByID("L02");
		assertEquals("Entrance Fountain", landmark2.getDescription());
		assertEquals("Fountain", landmark2.getType());
		
		Landmark landmark3 = manager.getLandmarkByID("L03");
		assertEquals("Waste Station 1", landmark3.getDescription());
		assertEquals("Pet Waste Station", landmark3.getType());
		
		Landmark landmark4 = manager.getLandmarkByID("L04");
		assertEquals("Entrance Restrooms", landmark4.getDescription());
		assertEquals("Restroom", landmark4.getType());
		
		Landmark landmark5 = manager.getLandmarkByID("L05");
		assertEquals("Overlook 1", landmark5.getDescription());
		assertEquals("Overlook", landmark5.getType());
		
		Landmark landmark6 = manager.getLandmarkByID("L06");
		assertEquals("Rock Formation 1", landmark6.getDescription());
		assertEquals("Rock Formation", landmark6.getType());
		
		Landmark landmark7 = manager.getLandmarkByID("L07");
		assertEquals("Overlook 2", landmark7.getDescription());
		assertEquals("Overlook", landmark7.getType());
		
		Landmark landmark8 = manager.getLandmarkByID("L08");
		assertEquals("Overlook Restrooms", landmark8.getDescription());
		assertEquals("Restroom", landmark8.getType());
		
		Landmark landmark9 = manager.getLandmarkByID("L09");
		assertEquals("Waste Station 2", landmark9.getDescription());
		assertEquals("Pet Waste Station", landmark9.getType());
		
		Landmark landmark10 = manager.getLandmarkByID("L10");
		assertEquals("Hidden Gardens", landmark10.getDescription());
		assertEquals("Gardens", landmark10.getType());
		
		Landmark landmark11 = manager.getLandmarkByID("L11");
		assertEquals("Campsite 1", landmark11.getDescription());
		assertEquals("Campsite", landmark11.getType());
		
		Landmark landmark12 = manager.getLandmarkByID("L12");
		assertEquals("Campsite Restrooms", landmark12.getDescription());
		assertEquals("Restroom", landmark12.getType());
		
		assertNull(manager.getLandmarkByID("L13"));
	}

	@Test
	public void testGetProposedFirstAidLocations() {
		fail("Not yet implemented");
	}

}
