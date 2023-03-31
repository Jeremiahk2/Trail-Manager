package edu.ncsu.csc316.trail.manager;

import java.io.FileNotFoundException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.trail.data.Landmark;
import edu.ncsu.csc316.trail.data.Trail;
import edu.ncsu.csc316.trail.dsa.DSAFactory;
import edu.ncsu.csc316.trail.dsa.DataStructure;
import edu.ncsu.csc316.trail.io.TrailInputReader;

public class TrailManager {
    private List<Trail> trails;
    private List<Landmark> landmarks;
    
    public TrailManager(String pathToLandmarkFile, String pathToTrailFile) throws FileNotFoundException {
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST); //Array based list has best runtime for sequential search.
        landmarks = TrailInputReader.readLandmarks(pathToLandmarkFile);
        
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST); 
        trails = TrailInputReader.readTrails(pathToTrailFile); 
        
    }
    
    private boolean contains(String item, List<String> list) {
    	for (int i = 0; i < list.size(); i++) {
    		if (list.get(i).equals(item)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private int getDistancesHelper(String origin, String destination, List<String> visited) {
    	if (origin.equals(destination)) {
    		return 0;
    	}
    	for (int i = 0; i < trails.size(); i++) {
    		if (trails.get(i).getLandmarkOne().equals(origin)) {
    			if (!contains(trails.get(i).getLandmarkTwo(), visited)) {
    				visited.addFirst(origin);
    				int result = getDistancesHelper(trails.get(i).getLandmarkTwo(), destination, visited);
        			if (result == 0) {
        				return trails.get(i).getLength();
        			}
        			else if (result != -1) {
        				return result + trails.get(i).getLength();
        			}
    			}
    		}
    		if (trails.get(i).getLandmarkTwo().equals(origin)) {
    			if (!contains(trails.get(i).getLandmarkOne(), visited)) {
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
        // TODO: Complete this method
        // Remember to use DSAFactory to get instances of data structures or sorters that you will need!
        // For example: DSAFactory.getIndexedList() or DSAFactory.getMap(null)
        // See the project writeup for more information about using DSAFactory.
    	DSAFactory.setMapType(DataStructure.UNORDEREDLINKEDMAP);
    	Map<Landmark, Integer> distances = DSAFactory.getMap(null);
    	
    	
    	DSAFactory.setListType(DataStructure.SINGLYLINKEDLIST);
    	for (int i = 0; i < landmarks.size(); i++) {
    		List<String> visited = DSAFactory.getIndexedList();
//    		visited.addFirst(landmarks.get(i).getId());
    		
    		if (!landmarks.get(i).getId().equals(originLandmark)) {
    			int result = getDistancesHelper(originLandmark, landmarks.get(i).getId(), visited);
    			if (result != -1) {
    				distances.put(landmarks.get(i), result);
    			}
    			
    		}
    	}
    	return distances;
    }
    
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
    
    public Map<Landmark, List<Trail>> getProposedFirstAidLocations(int numberOfIntersectingTrails) {
        // TODO: Complete this method
    	return null;
    }
}