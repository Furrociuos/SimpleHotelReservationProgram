import java.io.*;
import java.nio.file.Path; // A platform agnostic way to obtain file paths
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
      System.out.println("Enter the name of your csv file: ");
        String file = in.nextLine();
          csvCheckandCreate(file); // Checks if the csv file exists

      System.out.print("What would you like to do? (Enter number for selection)\n  1. Add a Reservation\n  2.  Remove a reservation");

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
