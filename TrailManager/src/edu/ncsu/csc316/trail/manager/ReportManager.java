package edu.ncsu.csc316.trail.manager;

import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.util.Comparator;
import java.util.Iterator;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.trail.data.Landmark;
import edu.ncsu.csc316.trail.data.Trail;
import edu.ncsu.csc316.trail.dsa.Algorithm;
import edu.ncsu.csc316.trail.dsa.DSAFactory;
import edu.ncsu.csc316.trail.dsa.DataStructure;

/**
 * A class for reporting results of various operations like 
 * getting distances to various landmarks, getting possible first aid locations.
 * @author Jeremiah Knizley
 *
 */
public class ReportManager {

	/**
	 * Manager responsible for trail-landmark ordering and retrieval
	 */
	TrailManager manager;
	
	/**
	 * Constructor for ReportManager. Constructs a TrailManager object using ArrayBasedList, then sets
	 * list type to SinglyLinkedList and map type to UnorderedLinkedMap
	 * @param pathToLandmarkFile the file path to the file containing the landmarks
	 * @param pathToTrailFile the path to the file containing the trails.
	 * @throws FileNotFoundException if the file could not be found
	 */
    public ReportManager(String pathToLandmarkFile, String pathToTrailFile) throws FileNotFoundException {
        // For example, for Maps: DSAFactory.setMapType(DataStructure.<MAPTYPE>)
        // You'll need to set any Maps, Lists, and/or sorting algorithms that are used.
        // See the project writeup for more information about using DSAFactory.
        // This is the ONLY place you will need to call these DSAFactory setter methods!
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST); //Array based list will work best with TrailInputReader
        manager = new TrailManager(pathToLandmarkFile, pathToTrailFile);
        DSAFactory.setListType(DataStructure.SINGLYLINKEDLIST); //Singly Linked List has better runtime for finding the first item.
        DSAFactory.setMapType(DataStructure.SEARCHTABLE); 
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
    }

    /**
     * Finds the distances to all landmarks (if possible)from the origin landmark.
     * @param originLandmark the landmark to calculate distances from.
     * @return a string that displays the distances to all landmarks (if possible) from the origin
     */
    public String getDistancesReport(String originLandmark) {
        Map<Landmark, Integer> distances = manager.getDistancesToDestinations(originLandmark);
        Map<Integer, Landmark> reverseDistances = DSAFactory.getMap(null);
        Iterable<Entry<Landmark, Integer>> iterable = distances.entrySet();
        Iterator<Entry<Landmark, Integer>> it = iterable.iterator();
        
        while (it.hasNext()) {
        	Entry<Landmark, Integer> current = it.next();
        	reverseDistances.put(current.getValue(), current.getKey());
        }
        //Build header
        Landmark origin = manager.getLandmarkByID(originLandmark);
        StringBuilder sb = new StringBuilder();
        sb.append("Landmarks Reachable from ");
        sb.append(origin.getDescription());
        sb.append(" ");
        sb.append("(");
        sb.append(origin.getId());
        sb.append(") {\n");
        //Build inner text
        Iterable<Entry<Integer, Landmark>> stringIterable = reverseDistances.entrySet();
        Iterator<Entry<Integer, Landmark>> stringIt = stringIterable.iterator();
        while (stringIt.hasNext()) {
        	Entry<Integer, Landmark> current = stringIt.next();
        	sb.append("   ");
        	sb.append(current.getKey());
        	sb.append(" feet ");
        	if (current.getKey() > 5280) {
        		double miles = (double)current.getKey() / 5280.0;
        		miles = miles * 100;
        		miles = Math.round(miles);
        		miles = miles / 100;
        		sb.append("(");
        		sb.append(miles);
        		sb.append(" miles) to ");
        	}
        	else {
        		sb.append("to ");
        	}
        	sb.append(current.getValue().getDescription());
        	sb.append(" (");
        	sb.append(current.getValue().getId());
        	sb.append(")\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
    
    /**
     * Return a string containing information about possible first aid locations.
     * @param numberOfIntersectingTrails the minimum number of intersecting trails for a first aid station to be possible
     * @return a string containing information about possible first aid locations
     */
    public String getProposedFirstAidLocations(int numberOfIntersectingTrails) {
        Map<Landmark, List<Trail>> locations = manager.getProposedFirstAidLocations(numberOfIntersectingTrails);
        Iterable<Entry<Landmark, List<Trail>>> iterable = locations.entrySet();
        Iterator<Entry<Landmark, List<Trail>>> it = iterable.iterator();
        StringBuilder sb = new StringBuilder();
        if (locations.size() == 0) {
        	sb.append("No landmarks have at least ");
        	sb.append(numberOfIntersectingTrails);
        	sb.append(" intersecting trails.\n");
        	return sb.toString();
        }
        sb.append("Proposed Locations for First Aid Stations {\n");
        
        Sorter<Entry<Landmark, List<Trail>>> sorter = DSAFactory.getComparisonSorter(new FirstAidOrder());
        @SuppressWarnings("unchecked")
		Entry<Landmark, List<Trail>>[] elements = new Entry[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
        	elements[i] = it.next(); 
        }
        
        sorter.sort(elements);
        for (int i = 0; i < locations.size(); i++) {
        	sb.append("   ");
        	sb.append(elements[i].getKey().getDescription());
        	sb.append(" (");
        	sb.append(elements[i].getKey().getId());
        	sb.append(") - ");
        	sb.append(elements[i].getValue().size());
        	sb.append(" intersecting trails\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
    
    /**
     * Comparator for comparing FirstAid Entries
     * @author Jeremiah Knizley
     *
     */
    private class FirstAidOrder implements Comparator<Entry<Landmark, List<Trail>>> {
    	/**
    	 * compares first with second by using compareTo (the natural ordering)
    	 * @param o1 the first object to be compared
    	 * @param o2 the second object that is compared with the first.
    	 * @return 0 if the two objects are equal, -1 if first comes before second, and 1 if first comes after second
    	 */
		@Override
		public int compare(Entry<Landmark, List<Trail>> o1, Entry<Landmark, List<Trail>> o2) {
			if (o1.getValue().size() > o2.getValue().size()) {
				return -1;
			}
			else if (o1.getValue().size() < o2.getValue().size()) {
				return 1;
			}
			else {
				if (((o1.getKey().getDescription()).compareTo(o2.getKey().getDescription()) < 0)) {
					return -1;
				}
				else if (((o1.getKey().getDescription()).compareTo(o2.getKey().getDescription()) > 0)) {
					return 1;
				}
			}
			return 0;
		}
    }
    
}