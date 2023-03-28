/* Brenden Blake and Elizabeth Lowe
* 3/28/2023
* CS 465 Assignment 2
* Implementing ACM by row using the CAPABILITIES approach.
*/

import java.util.*;
import java.io.*;

class Main {

  // Create scanner
  public static Scanner console = new Scanner(System.in);

  public static void main(String[] args) {

    // create the empty acm
    AccessControl acm = new AccessControl();

    // Display initial menu
    showMenu();
    String userChoice;
    userChoice = console.next();

    // Call appropriate methods for the options
    while (!userChoice.equals("5")) {
      switch (userChoice) {
        case "1":
          loadEntries(acm);
          break;
        case "2":
          acm.printACM();
          break;
        case "3":
          updateACM(acm);
          break;
        case "4":
          evaluateAccess(acm);
          break;
        case "5":
          // exit
          break;
        default:
          System.out.println("Error: Invalid choice!");
      }
      showMenu();
      userChoice = console.next();
    }
  }

  // Method to load the acm entries
  public static void loadEntries(AccessControl acm) {
    // Prompt user for file path of the acm entries
    System.out.println("Enter the path for the file: ");
    String loadPath = console.next();
    try {
      Scanner inputACM = new Scanner(new File(loadPath));
      while (inputACM.hasNext()) {
        // Get data from file
        String entry = inputACM.nextLine();
        String[] entryData = entry.split(",");
        // Store the users, file, and permission from the entry
        String user = entryData[0];
        String fileName = entryData[1];
        String permission = entryData[2];
        // Add entry
        // add user to acm
        acm.loadEntries(user, fileName, permission);;
      }
      inputACM.close();
    } catch (IOException ex) {
      System.out.println(ex);
    }

  }

  // Method to update the acm
  public static void updateACM(AccessControl acm) {
    // Prompt user for file path of the acm entries
    System.out.println("Enter the path for the file: ");
    String updatePath = console.next();
    try {
      Scanner updateACM = new Scanner(new File(updatePath));
      while (updateACM.hasNext()) {
        // Get data from file
        String updateEntry = updateACM.nextLine();
        System.out.println("\n"+updateEntry);

        String[] updateEntryData = updateEntry.split(",");
        // Store the action, user, file, and permission from the update
        String action = updateEntryData[0];
        String user = updateEntryData[1];
        String fileName = updateEntryData[2];
        String permission = updateEntryData[3];

        // Update Entry based on action
        if (action.toLowerCase().equals("add")) {
          acm.addEntry(user, fileName, permission);
        } else if (action.toLowerCase().equals("remove")) {
          acm.removeEntry(user, fileName, permission);
        } else {
          System.out.println("Update Action is not recognizable");
        }
      }
      updateACM.close();
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

  // Method to evaluate the access requests
  public static void evaluateAccess(AccessControl acm) {
    // Prompt user for file path of the acm entries
    System.out.println("Enter the path for the file: ");
    String requestPath = console.next();
    try {
      Scanner accessRequest = new Scanner(new File(requestPath));
      while (accessRequest.hasNext()) {
        // Get data from file
        String request = accessRequest.nextLine();
        System.out.println("\n"+request);
        String[] requestData = request.split(",");
        // Store the users, file, and permission from the request
        String user = requestData[0];
        String fileName = requestData[1];
        String permission = requestData[2];
        // Check Access
        if (acm.evaluateEntry(user, fileName, permission)) {
          System.out.println("Permit");
        } else {
          System.out.println("Deny");
        }
      }
      accessRequest.close();
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

  // Method to show the menu
  public static void showMenu() {
    // Display Menu
    System.out.println("\nChoose an option:");
    System.out.println("1. Load input entries");
    System.out.println("2. Print ACM");
    System.out.println("3. Update ACM entries from a file");
    System.out.println("4. Evaluate access request from a file");
    System.out.println("5. Exit.");
  }
}