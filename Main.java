import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static List<String[]> reservationData = new ArrayList<>(); // Makes ArrayList accessible to all functions

    public static void main(String[] args) {
        boolean loop = true;
        File csvDirectory = new File("CSVs");

        clearScreen();

        // Checks if the "CSVs" folder exists then creates it if it dosen't
        if (csvDirectory.mkdir()) {
            System.out.println("CSV folder does not exist. Creating...");
        }

        do {
            String file = selCSV(); // Lists and selects csv file

            // Main Menu
            reservationData = parseCSV(file + ".csv"); // Parse selected csv file
            mainMenu(file);
        } while (loop);
    }

    private static void listCSVs() {
        File csvDirectory = new File("CSVs");
        File[] files = csvDirectory.listFiles();

        System.out.println("CSVs/");
        for (File file : files) {
            String filename = file.getName();
            String nameonly = filename.replace(".csv", ""); // Removes the file extension
            System.out.println("  └ " + nameonly);
        }
    }

    private static String selCSV() {
        listCSVs();

        System.out.print("Enter the name of your csv file: ");

        String file = in.nextLine();

        clearScreen(); // Clears the console
        csvCheckandCreate(file + ".csv"); // Checks if the csv file exists

        return file;
    }

    private static void csvCheckandCreate(String filename) {
        try {
            File csv = new File("CSVs", filename);

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

                for (String[] room : rooms) {
                    writer.write(String.join(",", room) + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("File was not created: " + e.getMessage());
        }
    }

    // Used for parsing CSV files
    private static List<String[]> parseCSV(String filename) {
        List<String[]> data = new ArrayList<>();
        try {
            File csv = new File("CSVs", filename);
            Scanner fileRead = new Scanner(csv);

            while (fileRead.hasNextLine()) {
                String line = fileRead.nextLine();
                String[] values = line.split(",");
                data.add(values);
            }
            fileRead.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return data;
    }

    private static void displayData(List<String[]> data) {
        // Informs the user if no data is found
        if (data.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        // Determine the maximum number of columns
        int maxCols = 0;
        for (String[] row : data) {
            maxCols = Math.max(maxCols, row.length);
        }

        // Determine the width for each column
        int[] colWidths = new int[maxCols];
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                colWidths[i] = Math.max(colWidths[i], row[i].length());
            }
        }

        // Print the data with even spacing
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                String format = "%-" + (colWidths[i] + 2) + "s";
                System.out.printf(format, row[i]);
            }
            System.out.println();
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void mainMenu(String filename) {
        boolean loop = true;

        while (loop) {
            try {
                displayData(reservationData); // Display parsed data

                System.out.println(
                    "What would you like to do? (CTRL + C to quit)\n  1. Add a reservation\n  2. Remove a reservation\n  3. Modify a reservation\n  4. Open a different csv file"
                );

                System.out.print("Please enter a number: ");
                int userChoice = in.nextInt();

                switch (userChoice) {
                    case 1:
                        clearScreen();
                        addReservation(filename);
                        break;
                    case 2:
                        clearScreen();
                        rmReservation(filename);
                        break;
                    case 3:
                        clearScreen();
                        modReservation(filename);
                        break;
                    case 4:
                        clearScreen();
                        loop = false;
                        in.nextLine();
                        break;
                    default:
                        clearScreen();
                        System.out.println(
                            "Invalid! Please enter a valid number"
                        ); // Continues loop if input is invalid
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid! Please enter a valid number"); // Continues loop if input is invalid
                in.nextLine();
            }
        }
    }

    private static void addReservation(String filename) {
        boolean loop = true;

        do {
            try {
                displayData(reservationData);
                System.out.print("Enter the room number to reserve: ");
                int i = in.nextInt();
                in.nextLine();

                if (i > 20) {
                    System.out.println("Room does not exist.");
                    continue;
                }

                String[] roomUpdate = reservationData.get(i);

                if (!roomUpdate[2].equalsIgnoreCase("Available")) {
                    clearScreen();
                    System.out.println("Room is already reserved.");
                    continue;
                }

                System.out.print("Enter the customer's name: ");
                String name = in.nextLine();

                roomUpdate[1] = name;
                roomUpdate[2] = "Reserved";

                System.out.print(
                    "Would you like to add another reservation? (y/N): "
                );
                String choice = in.nextLine();

                if (choice.equalsIgnoreCase("y")) {
                    clearScreen();
                    System.out.println("Add another reservation...");
                    continue;
                } else {
                    clearScreen();
                    saveCSV(filename + ".csv", reservationData);
                    System.out.println("Changes saved.");
                    loop = false;
                    break;
                }
            } catch (InputMismatchException e) {}
            clearScreen();
            System.out.println("Invalid! Please enter a valid number."); // Continues loop if input is invalid
            in.nextLine();
        } while (loop);
    }

    private static void rmReservation(String filename) {
        boolean loop = true;

        do {
            try {
                displayData(reservationData);
                System.out.print(
                    "Enter the room number of the reservation to remove: "
                );
                int i = in.nextInt();
                in.nextLine();

                if (i > 20) {
                    System.out.println("Room does not exist.");
                    continue;
                }

                String[] roomUpdate = reservationData.get(i);

                if (!roomUpdate[2].equalsIgnoreCase("Available")) {
                    clearScreen();
                    System.out.println("Room is already reserved.");
                    continue;
                }

                if (!roomUpdate[2].equalsIgnoreCase("Reserved")) {
                    clearScreen();
                    System.out.println("Room is already not reserved.");
                    continue;
                }

                roomUpdate[1] = "N/A";
                roomUpdate[2] = "Available";

                System.out.print(
                    "Would you like to remove another reservation? (y/N): "
                );
                String choice = in.nextLine();

                if (choice.equalsIgnoreCase("y")) {
                    clearScreen();
                    System.out.println("Remove another reservation...");
                    continue;
                } else {
                    clearScreen();
                    saveCSV(filename + ".csv", reservationData);
                    System.out.println("Changes saved.");
                    loop = false;
                    break;
                }
            } catch (InputMismatchException e) {}
            clearScreen();
            System.out.println("Invalid! Please enter a valid number"); // Continues loop if input is invalid
            in.nextLine();
        } while (loop);
    }

    private static void modReservation(String filename) {
        boolean loop = true;

        do {
            try {
                displayData(reservationData);
                System.out.print("Enter the room number to modify: ");
                int i = in.nextInt();
                in.nextLine();

                if (i > 20) {
                    System.out.println("Room does not exist.");
                    continue;
                }

                String[] roomUpdate = reservationData.get(i);

                if (!roomUpdate[2].equalsIgnoreCase("Reserved")) {
                    clearScreen();
                    System.out.println("Reservation does not exist.");
                    continue;
                }

                System.out.print("Enter a new name: ");
                String name = in.nextLine();

                roomUpdate[1] = name;

                System.out.print(
                    "Would you like to modify another reservation? (y/N): "
                );
                String choice = in.nextLine();

                if (choice.equalsIgnoreCase("y")) {
                    clearScreen();
                    System.out.println("Modify another reservation...");
                    continue;
                } else {
                    clearScreen();
                    saveCSV(filename + ".csv", reservationData);
                    System.out.println("Changes saved.");
                    loop = false;
                    break;
                }
            } catch (InputMismatchException e) {}
            clearScreen();
            System.out.println("Invalid! Please enter a valid number"); // Continues loop if input is invalid
            in.nextLine();
        } while (loop);
    }

    private static void saveCSV(String filename, List<String[]> data) {
        try {
            File csv = new File("CSVs", filename);
            FileWriter save = new FileWriter(csv);
            for (String[] row : data) {
                save.write(String.join(",", row) + "\n");
            }
            save.close();
        } catch (IOException e) {
            System.out.println("Error saving CSV file");
        }
    }
}
