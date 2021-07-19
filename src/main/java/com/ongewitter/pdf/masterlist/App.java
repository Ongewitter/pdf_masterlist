package com.ongewitter.pdf.masterlist;

import java.util.*;
import java.util.regex.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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

    List<String> paragraphs = new ArrayList<String>();
    List<String> orphans = new ArrayList<String>();
    // for (int page = 1; page <= document.getNumberOfPages(); ++page) {
    // for (int page = 172; page <= 202; ++page) {
    for (int page = 174; page <= 202; ++page) {
        // Set the page interval to extract. If you don't, then all pages would be extracted.
        stripper.setStartPage(page);
        stripper.setEndPage(page);

        // TODO:
        // Collect text
        // order hits per key word
        // Print out all at once

        String text = stripper.getText(document);

        matchText(text, paragraphs, orphans);
        // do some nice output with a header
        String pageStr = String.format("page %d:", page);
        System.out.println(pageStr);
        System.out.println("Name\t|\tRoll\t|\tImpact");
        for (int i = 0; i < pageStr.length(); ++i) {
            System.out.print("-");
        }
        System.out.println();
        FileWriter writer = createWriter();
        try {
          for (String paragraph : paragraphs) {
            printParagraph(paragraph, writer);
          }
          writer.close();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
          writer.close();
        }

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

  private static void matchText(final String text, final List<String> paragraphs, List<String> orphans) {
    String[] hitOrder = { "Action", "Time:", "Roll:", "Capability:", "Resistance:", "Impact:" };
    String[] hits = text.split("\\n(?=.*\\nAction)"); // Split on Action and previous line
    // Check for orphans and isolate them
    for(String hit : hits) {
      boolean isOrphan = false;
      for (String order : hitOrder) {
        if (!hit.contains(order)) {
          isOrphan = true;
          orphans.add(hit);
          break;
        }
      }
      if (!isOrphan) {
        // Add hits
        paragraphs.add(hit);
      }
    }
    // Merge orphans through hitOrder I guess?
    List<String> foundOrphans = new ArrayList<String>();
    if (orphans.size() > 0) {
      String merged = "";
      for (String orphan : orphans) {
        // Filter out orphans that direct you to "See something else"
        System.out.println("--------------orphan--------------");
        System.out.println(orphan);
        System.out.println(orphan.matches("See the.+sidebar"));
        if (orphan.contains("See the")) {
          paragraphs.add(orphan);
          foundOrphans.add(orphan);
          continue;
        }
        for (String order : hitOrder) {
          if (merged.length() > 0 && merged.contains(order)) { continue; }
          if (orphan.contains(order)) {
            merged = merged.concat(orphan);
            foundOrphans.add(orphan);
            break;
          }
        }
        if (containsAllWords(merged, hitOrder)) {
          paragraphs.add(merged);
          merged = "";
        }
      }
    }
    // Remove the found orphans
    orphans.removeAll(foundOrphans);
    System.out.println("--------------paragraphs--------------");
    for (String p : paragraphs) {
      System.out.println(p);
    }
  }

  public static boolean containsAllWords(String word, String ...keywords) {
    for (String k : keywords) {
      if (!word.contains(k)) {
        return false;
      }
    }
    return true;
  }

  // Print out the paragraph in the format "Name | Roll | Impact"
  private static void printParagraph(final String paragraph, final FileWriter writer) throws IOException {
    Pattern titlePattern = Pattern.compile("(.+)(?=\\nAction)");
    Matcher m = titlePattern.matcher(paragraph);
    String line;
    String title = "SKILL_NAME";
    if (m.find()) {
      title = m.group(1);
    }

    if (paragraph.contains("See the")) {
      String subParagraph = paragraph.substring(paragraph.indexOf("Action") + 6);
      line = title + "\t|\t" + subParagraph;
      writer.write(line);
      System.out.println(line);
    } else {
      Pattern rollPattern = Pattern.compile("Roll:(.+\n)");
      m = rollPattern.matcher(paragraph);
      String roll = "SKILL_ROLL";
      if (m.find()) {
        roll = m.group(1);
      }
  
      Pattern impactPattern = Pattern.compile("Impact:(.+\n.+\n)");
      m = impactPattern.matcher(paragraph);
      String impact = "SKILL_IMPACT";
      if (m.find()) {
        impact = m.group(1);
      }
  
      line = title + "\t|\t" + roll + "\t|\t" + impact;
      writer.write(line);
      System.out.println(title + "\t|\t" + roll + "\t|\t" + impact);
    }
    writer.write(System.getProperty( "line.separator" ));
    System.out.println();
  }
  
  private static FileWriter createWriter() throws IOException {
    new File("Maneuvers.txt");
    FileWriter myWriter = new FileWriter("Maneuvers.txt");
    return myWriter;
  }
}
