import java.io.*;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File csvDirectory = new File("CSVs");

        // Checks if the "CSVs" folder exists then creates it if it dosen't
        if (csvDirectory.mkdir()) {
            System.out.println("CSV folder does not exist. Creating...");
        }

        // Lists down what files are contained in the "CSVs" folder
        listCSVs();

        Scanner in = new Scanner(System.in);
        System.out.print(
            "Enter the name of your csv file (Without file extension): "
        );
        String file = in.nextLine();
        csvCheckandCreate(file); // Checks if the csv file exists

        // Main Menu
        System.out.println(
            "What would you like to do? (CTRL + C to stop the program)\n  1. Add a reservation\n  2. Remove a reservation\n  3. Modify a reservation"
        );
        mainMenu();

        in.close();
    }

    private static void listCSVs() {
        File csvDirectory = new File("CSVs");
        File[] files = csvDirectory.listFiles();

        System.out.println("CSVs/");
        for (File file : files) {
            System.out.println("  └ " + file.getName());
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
                FileWriter writer = new FileWriter(csv);

                String[][] rooms = new String[21][3];
                rooms[0][0] = "Rooms";
                rooms[0][1] = "Customer";
                rooms[0][2] = "Status";

                for (int i = 1; i < rooms.length; i++) {
                    rooms[i][0] = Integer.toString(i);
                    rooms[i][1] = "N/A";
                    rooms[i][2] = "Available";
                }

                Arrays.toString(rooms);

                for (String[] room : rooms) {
                    writer.write(String.join(" | ", room) + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(
                "File was not created. Do you have permission to create a file?"
            );
        }
    }

    private static void mainMenu() {
        Scanner menu = new Scanner(System.in);
        boolean selection = false;
        while (selection == false) {
            try {
                System.out.print("Please enter a number: ");
                int userChoice = menu.nextInt();
                switch (userChoice) {
                    case 1:
                        //                        addReservation();
                        selection = true;
                        break;
                    case 2:
                        //                        rmReservation();
                        selection = true;
                        break;
                    case 3:
                        //                        modReservation();
                        selection = true;
                        break;
                    default:
                        System.out.println(
                            "Invalid! Please enter a valid number"
                        ); // Continues loop if input is invalid
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid! Please enter a valid number"); // Continues loop if input is invalid
                menu.nextLine();
            }
        }
        menu.close();
    }
}
