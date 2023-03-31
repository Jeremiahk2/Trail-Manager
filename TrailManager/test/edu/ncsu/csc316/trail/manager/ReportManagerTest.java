package edu.ncsu.csc316.trail.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ReportManager
 * @author Jeremiah Knizley
 *
 */
public class ReportManagerTest {

	/**
	 * ReportManager for testing purposes
	 */
	private ReportManager manager;
	
	/**
     * Create a new instance of an array-based list before each test case executes
	 * @throws FileNotFoundException  if the file was not found
     */
    @Before
    public void setUp() throws FileNotFoundException {
        manager = new ReportManager("input/landmarks_sample.csv", "input/trails_sample.csv");
    }
    
	/**
     * Tests getDistancesReport method
     */
	@Test
	public void testGetDistancesReport() {
		String s = manager.getDistancesReport("L02");
		assertEquals(s, "Landmarks Reachable from Entrance Fountain (L02) {\n"
				+ "   3013 feet to Park Entrance (L01)\n"
				+ "   3613 feet to Hidden Gardens (L10)\n"
				+ "   4059 feet to Waste Station 1 (L03)\n"
				+ "   4192 feet to Entrance Restrooms (L04)\n"
				+ "   6503 feet (1.23 miles) to Waste Station 2 (L09)\n"
				+ "   8263 feet (1.56 miles) to Overlook 1 (L05)\n"
				+ "   9302 feet (1.76 miles) to Rock Formation 1 (L06)\n"
				+ "   12214 feet (2.31 miles) to Overlook 2 (L07)\n"
				+ "   14105 feet (2.67 miles) to Overlook Restrooms (L08)\n"
				+ "}\n");
		
		try {
			manager = new ReportManager("input/landmarks_sample.csv", "input/identical_distances.csv");
		} catch (FileNotFoundException e) {
			fail();
		}
		String s1 = manager.getDistancesReport("L01");
		assertEquals(s1, "Landmarks Reachable from Park Entrance (L01) {\n"
				+ "   3013 feet to Entrance Fountain (L02)\n"
				+ "   3013 feet to Waste Station 1 (L03)\n"
				+ "}\n");
		
		try {
			manager = new ReportManager("input/landmarks_sample.csv", "input/identical_distances2.csv");
		} catch (FileNotFoundException e) {
			fail();
		}
		String s2 = manager.getDistancesReport("L01");
		assertEquals(s2, "Landmarks Reachable from Park Entrance (L01) {\n"
//				+ "   3013 feet to Entrance Fountain (L02)\n"
				+ "   3013 feet to Waste Station 1 (L03)\n"
				+ "}\n");
	}
	
	/**
	 * Tests getProposedFirstAidLocations
	 */
	@Test
	public void testGetProposedFirstAidLocations() {
		String s = manager.getProposedFirstAidLocations(4);
		
		assertEquals(s, "No landmarks have at least 4 intersecting trails.");
		
		s = manager.getProposedFirstAidLocations(3);
		
		assertEquals(s, "Proposed Locations for First Aid Stations {\n"
				+ "   Park Entrance (L01) - 3 intersecting trails\n"
				+ "}\n");
		s = manager.getProposedFirstAidLocations(2);
		
		assertEquals(s, "Proposed Locations for First Aid Stations {\n"
				+ "   Park Entrance (L01) - 3 intersecting trails\n"
				+ "   Entrance Fountain (L02) - 2 intersecting trails\n"
				+ "   Entrance Restrooms (L04) - 2 intersecting trails\n"
				+ "   Overlook 1 (L05) - 2 intersecting trails\n"
				+ "   Overlook 2 (L07) - 2 intersecting trails\n"
				+ "   Rock Formation 1 (L06) - 2 intersecting trails\n"
				+ "   Waste Station 1 (L03) - 2 intersecting trails\n"
				+ "}\n");
		
		s = manager.getProposedFirstAidLocations(1);
		
		assertEquals(s, "Proposed Locations for First Aid Stations {\n"
				+ "   Park Entrance (L01) - 3 intersecting trails\n"
				+ "   Entrance Fountain (L02) - 2 intersecting trails\n"
				+ "   Entrance Restrooms (L04) - 2 intersecting trails\n"
				+ "   Overlook 1 (L05) - 2 intersecting trails\n"
				+ "   Overlook 2 (L07) - 2 intersecting trails\n"
				+ "   Rock Formation 1 (L06) - 2 intersecting trails\n"
				+ "   Waste Station 1 (L03) - 2 intersecting trails\n"
				+ "   Campsite 1 (L11) - 1 intersecting trails\n"
				+ "   Campsite Restrooms (L12) - 1 intersecting trails\n"
				+ "   Hidden Gardens (L10) - 1 intersecting trails\n"
				+ "   Overlook Restrooms (L08) - 1 intersecting trails\n"
				+ "   Waste Station 2 (L09) - 1 intersecting trails\n"
				+ "}\n");
		s = manager.getProposedFirstAidLocations(0);
		assertEquals(s, "Number of intersecting trails must be greater than 0.");
	}

}
