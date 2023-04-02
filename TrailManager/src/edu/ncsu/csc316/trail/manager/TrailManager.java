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
     * Search table for landmarks. Should make olog(n) searches for landmarks possible.
     */
    private Map<String, Landmark> landmarksMap;
    
    
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
        //Putting these in a Search table will guarantee O(logn) traversal later, for the the cost of nlog(n) traversal here.
        landmarksMap = DSAFactory.getMap(null);
        for (int i = 0; i < landmarks.size(); i++) {
        	landmarksMap.put(landmarks.get(i).getId(), landmarks.get(i));
        }
        
    }
    
//    /**
//     * Helper recursive method for getDistancesToDestinations
//     * @param origin the current starting point of a trail.
//     * @param destination The desired location to traverse to.
//     * @param visited The list of locations that have been traversed already
//     * @return int 0 when the location has been found, -1 if the location was not found (on the current path) or the current trail's distance.
//     */
//    private int getDistancesHelper(String origin, String destination, String visited) {
//    	if (origin.equals(destination)) {
//    		return 0;
//    	}
//    	for (int i = 0; i < trails.size(); i++) {
//    		if (trails.get(i).getLandmarkOne().equals(origin) && !trails.get(i).getLandmarkTwo().equals(visited)) {
//    			int result = getDistancesHelper(trails.get(i).getLandmarkTwo(), destination, origin);
//    			if (result == 0) {
//    				return trails.get(i).getLength();
//    			}
//    			else if (result != -1) {
//    				return result + trails.get(i).getLength();
//    			}
//    		}
//    		if (trails.get(i).getLandmarkTwo().equals(origin) && !trails.get(i).getLandmarkOne().equals(visited)) {
//    			int result = getDistancesHelper(trails.get(i).getLandmarkOne(), destination, origin);
//    			if (result == 0) {
//    				return trails.get(i).getLength();
//    			}
//    			else if (result != -1) {
//    				return result + trails.get(i).getLength();
//    			}
//    		}
//    	}
//    	return -1;
//    }
//    
//    /** 
//     * Returns a map to different landmarks from an originLandmark.
//     * Only trails that connect to a landmark are included in the map that is returned.
//     * @param originLandmark the landmark to calculate distances from
//     * @return A map containing a list of landmarks that connect to originlandmark, and their distances.
//     */
//    public Map<Landmark, Integer> getDistancesToDestinations(String originLandmark) {
//    	Map<Landmark, Integer> distances = DSAFactory.getMap(null);
//    	
//    	for (int i = 0; i < landmarks.size(); i++) {
//    		
//    		if (!landmarks.get(i).getId().equals(originLandmark)) {
//    			int result = getDistancesHelper(originLandmark, landmarks.get(i).getId(), originLandmark);
//    			if (result != -1) {
//    				distances.put(landmarks.get(i), result);
//    			}
//    			
//    		}
//    	}
//    	return distances;
//    }
    
    
    /**
   * Helper recursive method for getDistancesToDestinations
   * @param origin the current starting point of a trail.
   * @param totalDistance The desired location to traverse to.
   * @param distances The list of locations that have been traversed already
   * @param trail the current trail we are one
   * @param tempTrails the temporary list of trails we are using to traverse
   */
  private void getDistancesHelper(Map<Landmark, Integer> distances, Trail trail, String origin, int totalDistance, List<Trail> tempTrails) {
	  for (int i = 0; i < tempTrails.size(); i++) {
		  if (tempTrails.get(i).getLandmarkOne().equals(origin) || tempTrails.get(i).getLandmarkTwo().equals(origin)) {
				Trail current = tempTrails.get(i);
				tempTrails.remove(i);
				if (current.getLandmarkOne().equals(origin)) {
					distances.put(getLandmarkByID(current.getLandmarkTwo()), totalDistance + current.getLength());
					getDistancesHelper(distances, current, current.getLandmarkTwo(), totalDistance + current.getLength(), tempTrails);
				}
				
				else if (current.getLandmarkTwo().equals(origin)) {
					distances.put(getLandmarkByID(current.getLandmarkOne()), totalDistance + current.getLength());
					getDistancesHelper(distances, current, current.getLandmarkOne(), totalDistance + current.getLength(), tempTrails);
				}
				i = -1;
			}
		}
	  
  }
    
    /** 
   * Returns a map to different landmarks from an originLandmark.
   * Only trails that connect to a landmark are included in the map that is returned.
   * @param originLandmark the landmark to calculate distances from
   * @return A map containing a list of landmarks that connect to originlandmark, and their distances.
   */
  public Map<Landmark, Integer> getDistancesToDestinations(String originLandmark) {
  	Map<Landmark, Integer> distances = DSAFactory.getMap(null);
  	
  	//Copy trails to disposable array
  	List<Trail> tempTrails = DSAFactory.getIndexedList();
  	for (int i = trails.size() - 1; i >= 0; i--) {
  		tempTrails.addLast(trails.get(i));
  	}
  	//O(n) traversal of trails.
  	for (int i = 0; i < tempTrails.size(); i++) {
		if (tempTrails.get(i).getLandmarkOne().equals(originLandmark) || tempTrails.get(i).getLandmarkTwo().equals(originLandmark)) {
			Trail current = tempTrails.get(i);
			tempTrails.remove(i);
			if (current.getLandmarkOne().equals(originLandmark)) {
				distances.put(getLandmarkByID(current.getLandmarkTwo()), current.getLength());
				getDistancesHelper(distances, current, current.getLandmarkTwo(), current.getLength(), tempTrails);
			}
			
			else if (current.getLandmarkTwo().equals(originLandmark)) {
				distances.put(getLandmarkByID(current.getLandmarkOne()), current.getLength());
				getDistancesHelper(distances, current, current.getLandmarkOne(), current.getLength(), tempTrails);
			}
			i = -1;
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
    	return landmarksMap.get(landmarkID);
    }
    
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