package com.ongewitter.pdf.masterlist;

/**
 * Hello world!

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}

*/
// import com.ongewitter.pdf.masterlist.classes.Reader;
import java.util.Scanner;  // Import the Scanner class


public final class App {
    private App() {
    }

    /**
     * Read some CLI input and print some string.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);  // Create a Scanner object
      System.out.println("Enter username");
  
      String userName = scanner.nextLine();  // Read user input
      scanner.close();
      System.out.println("Username is: " + userName);  // Output user input
    }
}

