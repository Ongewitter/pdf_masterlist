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

    String[] paragraphs = {}, orphans = {};
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

  // tifying a substance requires time, and in some cases, 
  // you may need specialized equipment. 
  // Time: Instantaneous (primary action) to detect poi-
  // sons; Present Tense for most other uses (at least 10 
  // minutes spent examining the substance) 
  // Roll: Alchemy + Perception
  // Capability: Depending on the substance examined, 
  // you might need a relevant Lore, Science, or Tech 
  // Lore.
  // Resistance: Depends on the rarity of the substance. 
  // Examples include a common ruby or a well-known 
  // selchakah in a drink (Hard); or a rare Vril-Ya ant-
  // lered-wasp venom or an Eridolian pearl (Severe).
  // Impact: You correctly identify the substance (or lack 
  // thereof if the drink isn’t spiked).

  // REFINE 
  // Action
  // You may refine a substance, polishing it so that it better 
  // reflects the invisible light of the Empyrean. This sub-
  // stance can be material… or your own soul. Refining 
  // the soul is only transitory (the dross of the world soon 
  // begrimes it again), but as a meditative form of prayer, it 
  // can abolish negative mental and social states. 
  // Time: Narrated. It takes at least eight hours of lab 
  // work before a roll can be made. Only one attempt 
  // per day can be made on the same substance.
  // Roll: Alchemy + Faith
  // Capability: Depending on the substance to be re-
  // fined, you might need a relevant Lore, Science, or 
  // Tech Lore. If this attempt involves your soul, you 
  // need Religion Lore. 
  // Resistance: Depends on the raw state of the sub-
  // stance. Already worked materials — alloys, woven 
  // textiles — are harder to refine than raw material. 
  // If the substance is your soul, the Resistance is your 
  // Will. 
  // Impact: The substance becomes more spiritually 
  // pure than before. It is ideal for use as a theurgic 
  // implement — a phylactery or an object of a theur-
  // gic Sanctification or Consecration ritual (making 
  // it into a theurgic vestment). When using a refined 
  // item as the object of such an imbuement rite, your 
  // roll is favorable. 
  // If your opus was aimed at your own soul, you can 
  // relieve (cancel) one mental or social state that you 
  // are currently suffering. 
  // Animalia
  // Base rating: 3
  // Animalia represents your understanding of and 
  // empathy with animals, including non-sentient alien 
  // creatures. With this skill, you can train animals, and 
  // IDLE
  // Action 
  // See the Restorative Practices sidebar. Spending time just 
  // reading can help ease anxiety and stir the imagination. 

  // RESEARCH
  // Action 
  // Knowledge is a dangerous weapon. To get that weap-
  // on, you need to know how to arm yourself. Whether 
  // digging through a Church library or searching the 
  // Charioteer guildhall, you can use this maneuver to 
  // find the knowledge that you seek. 
  // Time: Narrated. A disorganized archive might re-
  // quire a full day to comb through, while a well-for-
  // matted data record might need only half an hour. 
  // Roll: Academia + Wits 
  // Capability: Read (the language your topic is written 
  // in), Think Machine (if it’s in a data archive)
  // Resistance: Depends on obscurity: Hard for com-
  // mon knowledge, Demanding for obscure topics, 
  // and Tough or higher for secret information.
  // Impact: You gain the knowledge you seek. Halve the 
  // time required per 2 VP spent. 
  // Alchemy 
  // RESTRICTED: Priest, Engineers guild, Banker, Enthu-
  // siast, Occultist, Spy, Tech Redeemer, Theurgist
  // Base rating: 0
  // Alchemy is the art and science of matter trans-
  // mutation. Those who transmute matter through al-
  // chemic means also transmute the soul into more re-
  // fined forms… or so alchemists believe. 
  // Alchemy integrates aspects of chemistry, philoso-
  // phy, and physics. It allows you to concoct various po-
  // tions and substances and to refine implements for use 
  // with theurgic rituals. As odd as it may seem, alchemy 
  // actually integrates certain sciences from the Second 
  // Republic era, although they’re not always fully under-
  // stood by 51st-century alchemists.
  // Most uses of this skill require access to an alchemical 
  // lab: a room stocked with supplies where you can per-
  // form alchemical works uninterrupted. This could be a 
  // small backroom in a hovel, a well-lit high-tech guild lab, 
  // or even a small stateroom or cargo hold on a starship.
  // Typical capabilities: Applied Science, Religion 
  // Lore, any relevant Tech Lore for the item or sub-
  // stance being worked

  // IDENTIFY SUBSTANCE
  // Action 
  // You can identify just about any substance and gauge 
  // its purity. You can also tell if a glass of wine is drugged 
  // or poisoned or if a gemstone is real or synthetic. Iden-



  private static void matchText(final String text, final String[] paragraphs) {
    String[] hitOrder = { "FULLCAPSTITLE", "Time:", "Roll:", "Capability:", "Resistance:", "Impact:" };
    String[] hits = text.split("(\\w*\\s?\\w+?)\\nAction");
    boolean hasOrphans = false;
    for(String hit : hits) {
     if (hit.indexOf("Action:") == -1) { hasOrphans = true; }
    }
    if (hasOrphans) {
      // Check for hitOrder, match those that fail
      
    }

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
