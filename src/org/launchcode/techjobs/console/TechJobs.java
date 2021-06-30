package org.launchcode.techjobs.console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * Created by LaunchCode
 */
public class TechJobs {
    private static Scanner in = new Scanner(System.in);
    public static void main (String[] args) {
        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        //Corresponds with Job_Data.CSV
        //These are the options for searching the data or searching all columns at once.
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options (First Menu)
        HashMap<String, String> actionChoices = new HashMap<>();
        //Choices user has to view the data. Search or List
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");
        //Prompt for user to select which way to view the data.
        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) { //this allows creates an infinite loop.
            //Present the user with first menu.
            String actionChoice = getUserSelection("View jobs by:", actionChoices);
            if (actionChoice.equals("list")) {
                String columnChoice = getUserSelection("List", columnChoices);
                if (columnChoice.equals("all")) {
                    //Carry out the request to the JobData class via one of its public methods. Display(print) the results.
                    printJobs(JobData.findAll());
                }
                else {
                    ArrayList<String> results = JobData.findAll(columnChoice);
                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");
                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }
            }
            else { // choice is "search"
                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);
                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();
                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchField));
                }
                else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {
        Integer choiceIdx;
        Boolean validChoice = false;
        //used to easily enumerate the choices HashMap.
        // In other words, it gives us a simple way to provide an ordering to choices, which doesn’t have an ordering of its own.
        String[] choiceKeys = new String[choices.size()];
        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }
        //this do-while loop is used so that the conditional is at the end and guarantees the loop will always run at least once.
        do {
            System.out.println("\n" + menuHeader);
            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }
            //collects user input
            choiceIdx = in.nextInt();
            in.nextLine();
            // Validate user's input. If invalid re-prompt the user.
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            }
            //else a valid input returns the choice as a String to the caller
            else {
                validChoice = true;
            }
        } while(!validChoice);
        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if (someJobs.size() >= 1) {
            //iterate over the ArrayList someJobs
            for (int i = 0; i < someJobs.size(); i++) {
                //each job is itself a HashMap. For each HashMap get and set
                for (Map.Entry<String, String> job : someJobs.get(i).entrySet()) {
                    //prints key and value from job
                    System.out.println(job.getKey() + ": " + job.getValue());
                }
                System.out.println("************");
            }
        }
        else {
            System.out.println("Sorry, no results for your selection. Please try again with a different selection.");
        }
    }
}
//adding notes for new commit
