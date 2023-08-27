package cs3500.pa01.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads contents from a file to a string.
 */
public class SrFileReader implements Reader {

  //file to read from
  File file;

  /**
   * Constructs a SrReader.
   *
   * @param file the file to read from
   */
  public SrFileReader(File file) {
    this.file = file;
  }

  /**
   * Reads the contents of a file to a string.
   *
   * @return the file contents
   */
  public String read() {
    // Initialize a Scanner to read the file
    Scanner sc;
    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      sc = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    StringBuilder content =
        new StringBuilder(); // StringBuilder is more efficient than String concatenation
    while (sc.hasNextLine()) { // Check there is another unread line in the file

      //addToHashMap(sc.nextLine());
      content.append(sc.nextLine()).append("\n");
      // Read the aforementioned line

      //content = editFormat(content);


    }
    return content.toString(); // Return the contents collected in the StringBuilder
  }
}
