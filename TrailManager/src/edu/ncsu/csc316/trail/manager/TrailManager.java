package edu.ncsu.csc316.trail.manager;

import java.io.FileNotFoundException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.trail.data.Landmark;
import edu.ncsu.csc316.trail.data.Trail;
import edu.ncsu.csc316.trail.dsa.DSAFactory;
import edu.ncsu.csc316.trail.io.TrailInputReader;

/**
 * Class for managing trails. Finds distances to different destinations, as well as finding 
 * potential first aid locations
 * @author Jeremiah Knizley
 *
 */
public class TrailManager {
	/** a list of trails in the system */
    private List<Trail> trails;
    /** A list of landmarks in the system */
    private List<Landmark> landmarks;
    
    /**
     * Creates a TrailManager. trails are initialized to an ArrayBasedList
     * landmarks are initialized to an ArrayBasedList
     * @param pathToLandmarkFile the path to the file containing landmarks
     * @param pathToTrailFile the path to the file containing trails
     * @throws FileNotFoundException if one of the files cannot be found
     */
    public TrailManager(String pathToLandmarkFile, String pathToTrailFile) throws FileNotFoundException {
        landmarks = TrailInputReader.readLandmarks(pathToLandmarkFile);
        trails = TrailInputReader.readTrails(pathToTrailFile); 
        
    }
    
    /**
     * Checks if the given item is contained in the given list.
     * @param item The item to search for
     * @param list The list to search in.
     * @return true if the item is found, false if not.
     */
    private boolean contains(String item, List<String> list) {
    	for (int i = 0; i < list.size(); i++) {
    		if (list.get(i).equals(item)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Helper recursive method for getDistancesToDestinations
     * @param origin the current starting point of a trail.
     * @param destination The desired location to traverse to.
     * @param visited The list of locations that have been traversed already
     * @return int 0 when the location has been found, -1 if the location was not found (on the current path) or the current trail's distance.
     */
    private int getDistancesHelper(String origin, String destination, List<String> visited) {
    	if (origin.equals(destination)) {
    		return 0;
    	}
    	for (int i = 0; i < trails.size(); i++) {
    		if (trails.get(i).getLandmarkOne().equals(origin) && !contains(trails.get(i).getLandmarkTwo(), visited)) {
    			visited.addFirst(origin);
    			int result = getDistancesHelper(trails.get(i).getLandmarkTwo(), destination, visited);
    			if (result == 0) {
    				return trails.get(i).getLength();
    			}
    			else if (result != -1) {
    				return result + trails.get(i).getLength();
    			}
    		}
    		if (trails.get(i).getLandmarkTwo().equals(origin) && !contains(trails.get(i).getLandmarkOne(), visited)) {
    			visited.addFirst(origin);
    			int result = getDistancesHelper(trails.get(i).getLandmarkOne(), destination, visited);
    			if (result == 0) {
    				return trails.get(i).getLength();
    			}
    			else if (result != -1) {
    				return result + trails.get(i).getLength();
    			}
    		}
    	}
    	return -1;
    }
    
    /** 
     * Returns a map to different landmarks from an originLandmark.
     * Only trails that connect to a landmark are included in the map that is returned.
     * @param originLandmark the landmark to calculate distances from
     * @return A map containing a list of landmarks that connect to originlandmark, and their distances.
     */
    public Map<Landmark, Integer> getDistancesToDestinations(String originLandmark) {
    	Map<Landmark, Integer> distances = DSAFactory.getMap(null);
    	
    	for (int i = 0; i < landmarks.size(); i++) {
    		List<String> visited = DSAFactory.getIndexedList();
    		
    		if (!landmarks.get(i).getId().equals(originLandmark)) {
    			int result = getDistancesHelper(originLandmark, landmarks.get(i).getId(), visited);
    			if (result != -1) {
    				distances.put(landmarks.get(i), result);
    			}
    			
    		}
    	}
    	return distances;
    }
    
    /**
     * Finds a landmark in the landmarks list and returns it.
     * @param landmarkID the ID of the landmark to search for.
     * @return the landmark in from the list, or null if empty.
     */
    public Landmark getLandmarkByID(String landmarkID) {
    	Landmark desired = null;
    	//O(n) runtime.
    	for (int i = 0; i < landmarks.size(); i++) {
    		if (landmarks.get(i).getId().equals(landmarkID)) {
    			desired = landmarks.get(i);
    		}
    	}
    	return desired;
    }
    
//    private int firstAidHelper(int numFound, int currentIndex, Landmark current, int minNumTrails, List<Trail> currentTrails) {
//  
//    	int totalFound = numFound;
//    	if (currentIndex >= trails.size()) {
//    		return totalFound;
//    	}
//    	if (currentIndex != 0 && currentIndex % 900 == 0) {
//    		return -1 * numFound - 1;
//    	}
//    	else if (trails.get(currentIndex).getLandmarkOne().equals(current.getId()) || trails.get(currentIndex).getLandmarkTwo().equals(current.getId())) {
//    		totalFound = firstAidHelper(numFound + 1, currentIndex + 1, current, minNumTrails, currentTrails);
//    		if (totalFound >= minNumTrails) {
//    			currentTrails.addFirst(trails.get(currentIndex));
//    		}
//    	}
//    	else {
//    		totalFound = firstAidHelper(numFound, currentIndex + 1, current, minNumTrails, currentTrails);
//    	}
//    	return totalFound;
//    }
    
    /**
     * Finds Potential locations for first aid stations based on the minimum number of intersecting trails.
     * @param numberOfIntersectingTrails the minimum number of intersecting trails required for a first aid station.
     * @return a map containing Landmarks and List of trails as keys and values respectively. For a Landmark, the list of intersecting trails are listed.
     */
    public Map<Landmark, List<Trail>> getProposedFirstAidLocations(int numberOfIntersectingTrails) {
    	Map<Landmark, List<Trail>> firstAidLocations = DSAFactory.getMap(null);
    	
    	for (int i = 0; i < landmarks.size(); i++) {
    		List<Trail> currentTrails = DSAFactory.getIndexedList();
    		int numFound = 0;
    		for (int j = 0; j < trails.size(); j++) {
    			if (trails.get(j).getLandmarkOne().equals(landmarks.get(i).getId()) || trails.get(j).getLandmarkTwo().equals(landmarks.get(i).getId())) {
    				numFound++;
    				currentTrails.addLast(trails.get(j));
    			}
    		}
    		if (numFound >= numberOfIntersectingTrails) {
    			firstAidLocations.put(landmarks.get(i), currentTrails);
    		}
    	}
    	return firstAidLocations;
    }
}