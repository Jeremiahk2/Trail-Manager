package edu.ncsu.csc316.trail.ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.trail.manager.ReportManager;

/**
 * Class for handling user input. Receives a series of commands and carries out actions in accordance with them.
 * @author Jeremiah Knizley
 *
 */
public class ManagerUI {
	
	/**
	 * Main part of the program. Handles user input and retrives necessary information.
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		ReportManager manager = null;
		boolean notValidFile = true;
		while (notValidFile) {
			System.out.println("Enter File Path To Landmarks: ");
			String landmarkPath = scnr.nextLine();
			System.out.println("Enter File Path to Trails: ");
			String trailPath = scnr.nextLine();
			try {
				notValidFile = false;
				manager = new ReportManager(landmarkPath, trailPath);
			} catch (FileNotFoundException e) {
				System.out.println("Unknown or invalid file entered. Please try again");
				notValidFile = true;
			}
		}
		
		
		boolean quit = false;
		while (!quit) {
			System.out.println("Commands:\n"
					+ "First <number of intersecting trails>\n"
					+ "Distances <landmark ID>\n"
					+ "Quit\n"
					+ "Enter Command: ");
			String command = scnr.nextLine();
			if (command.charAt(0) == 'F') {
				Scanner lineScanner = new Scanner(command);
				lineScanner.next();
				int numIntersecting = lineScanner.nextInt();
				System.out.println(manager.getProposedFirstAidLocations(numIntersecting));
				lineScanner.close();
			}
			else if (command.charAt(0) == 'D') {
				Scanner lineScanner = new Scanner(command);
				lineScanner.next();
				String id = lineScanner.next();
				System.out.println(manager.getDistancesReport(id));
				lineScanner.close(); 
			}
			else if (command.charAt(0) == 'Q') {
				quit = true;
			}
			else {
				System.out.println("Invalid Command\n");
			}
		}
		scnr.close();
	}
}
