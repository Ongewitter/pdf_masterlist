package com.ongewitter.pdf.masterlist;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

public final class App {
  /**
   * javac /Users/cedric/Documents/Projects/Java/pdf_masterlist/src/main/java/com/ongewitter/pdf/masterlist/App.java
   * Reads a PDF and spits out a master list.
   * @param args
   * @throws IOException
   */
  public static void main(final String[] args) throws IOException {
    String file;
    if (args.length != 1) {
      usage();
      file = "/Users/cedric/Documents/Projects/Java/Fading_Suns_Character_Book.pdf";
    } else {
      file = args[0];
    }

    PDDocument document = PDDocument.load(new File(file));
    AccessPermission ap = document.getCurrentAccessPermission();
    if (!ap.canExtractContent()) {
        throw new IOException("You do not have permission to extract text");
    }

    PDFTextStripper stripper = new PDFTextStripper();
    // This example uses sorting, but in some cases it is more useful to switch it off,
    // e.g. in some files with columns where the PDF content stream respects the
    // column order.
    // stripper.setSortByPosition(true);

    String[] paragraphs, orphans;
    // for (int page = 1; page <= document.getNumberOfPages(); ++page) {
    // for (int page = 172; page <= 202; ++page) {
    for (int page = 175; page <= 175; ++page) {
        // Set the page interval to extract. If you don't, then all pages would be extracted.
        stripper.setStartPage(page);
        stripper.setEndPage(page);

        // TODO:
        // Collect text
        // order hits per key word
        // Print out all at once

        String text = stripper.getText(document);

        matchText(text, paragraphs);
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
  }

  /**
   * Group text by sticking occurrence in pos[x]
   * Read in order of hits FULLCAPSTITLE, Time, Roll, Capability, Resistance, Impact
   * If any are missing add to orphans[]? Most likely to work, emptying out as matches are made :thinking_face:
   */
  private static void matchText(final String text, final String[] paragraphs) {
    String[] hitOrder = { "FULLCAPSTITLE", "Time:", "Roll:", "Capability:", "Resistance:", "Impact:" };
    
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
