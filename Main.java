import java.io.*;
import java.util.InputMismatchException; // A platform agnostic way to obtain file paths
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    File csvDirectory = new File("CSVs");

      if (csvDirectory.mkdir()) {
        System.out.println("CSV directory does not exist. Creating...");
      }

      System.out.print("Enter the name of your csv file: ");
        String file = in.nextLine();
          csvCheckandCreate(file); // Checks if the csv file exists

    // Main Menu
    boolean selection = false;

      System.out.println("What would you like to do?\n  1. Add a reservation\n  2. Remove a reservation\n  3. Modify a reservation");

    while (selection == false) {
      try {
        System.out.print("Please enter a number: ");
          int userChoice = in.nextInt();
          switch (userChoice) {
            case 1:
      //        addReservation();
              selection = true;
              break;
            case 2:
      //        rmReservation();
              selection = true;
              break;
            case 3:
      //        modReservation();
              selection = true;
              break;
            default:
              System.out.println("Invalid! Please enter a valid number");
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid! Please enter a valid number");
        in.nextLine();
      }
    }
  }

  private static void csvCheckandCreate(String filename) {
    try {
      File csv = new File("CSVs", filename + ".csv");

      // Informs the user that the file exists
      if (csv.exists()) {
        System.out.print("File found.\n");
      }

      // Creates the file if not found
      else {
        System.out.print("File not found. Creating...\n");
        csv.createNewFile();
      }
    } catch (IOException e) {
      System.out.println("File was not created. Do you have permission to create a file?");
    }
  }
}
