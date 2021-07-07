package com.ongewitter.pdf.masterlist;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

public final class App {
  /**
   * Reads a PDF and spits out a master list.
   * @param args
   * @throws IOException
   */
  public static void main(final String[] args) throws IOException {
    if (args.length != 1) {
      usage();
    }

    PDDocument document = PDDocument.load(new File(args[0]));
    AccessPermission ap = document.getCurrentAccessPermission();
    if (!ap.canExtractContent()) {
        throw new IOException("You do not have permission to extract text");
    }

    PDFTextStripper stripper = new PDFTextStripper();

    // This example uses sorting, but in some cases it is more useful to switch it off,
    // e.g. in some files with columns where the PDF content stream respects the
    // column order.
    // stripper.setSortByPosition(true);

    // for (int page = 1; page <= document.getNumberOfPages(); ++page) {
    for (int page = 1; page <= 10; ++page) {
        // Set the page interval to extract. If you don't, then all pages would be extracted.
        stripper.setStartPage(page);
        stripper.setEndPage(page);

        // let the magic happen
        String text = stripper.getText(document);

        // do some nice output with a header
        String pageStr = String.format("page %d:", page);
        System.out.println(pageStr);
        for (int i = 0; i < pageStr.length(); ++i) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println(text.trim());
        System.out.println();

        // If the extracted text is empty or gibberish, please try extracting text
        // with Adobe Reader first before asking for help. Also read the FAQ
        // on the website:
        // https://pdfbox.apache.org/2.0/faq.html#text-extraction
    }
    document.close();
  }

  /**
   * This will print the usage for this document.
   */
  private static void usage() {
    System.err.println("Usage: java " + App.class.getName() + " <input-pdf>");
    System.exit(-1);
  }

    // /**
    //  * Read some CLI input and print some string.
    //  * @param args The arguments of the program.
    //  */
    // public static void main(String[] args) {
    //   Scanner scanner = new Scanner(System.in);  // Create a Scanner object
    //   System.out.println("Enter username");
    //
    //   String userName = scanner.nextLine();  // Read user input
    //   scanner.close();
    //   System.out.println("Username is: " + userName);  // Output user input
    // }
}
