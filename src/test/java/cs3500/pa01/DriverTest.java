package cs3500.pa01;

import static cs3500.pa01.SortType.MODIFIED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class DriverTest {

  /**
   * Tests the Driver class
   */
  @Test
  void testDriver() {
    Driver d = new Driver();

    assertEquals(d.getClass(), Driver.class);
  }

  /**
   * Tests the main method when there is not valid arg
   *
   * @throws IOException if the file is not found
   */
  @Test
  void mainNoSort() throws IOException {

    String[] args = {"src/test/resources/SampleInput/",
        "FILE_MODIFIED_DATE_SOR",
        "src/test/resources/SampleOutput/output"};
    assertThrows(IllegalArgumentException.class, () -> Driver.main(args));
  }

  /* @Test
  void sortModifiedDate() throws IOException {
    FileWalker sf = new FileWalker();
    sf.fileToSort.clear();

    String[] args4 = {"src/test/resources/test/",
        "MODIFIED",
        "src/test/resources/SampleOutput/output/"};
    FileReader write = new FileReader();

    String content = "";

    Path startingDirectory = Path.of(args4[0]);

    String[] args = {startingDirectory.toString(), args4[1], args4[2]};


    // Run the main method
    Driver.main(args);


    Files.walkFileTree(startingDirectory, sf);

    if ((args[1].equals("FILENAME"))) {
      SortType.FILENAME.sortByName(sf.fileToSort);
    } else if ((args[1].equals("CREATED"))) {
      SortType.CREATED.sortByDateCreated(sf.fileToSort);
    } else if ((args[1].equals("MODIFIED"))) {
      SortType.MODIFIED.sortByDateModified(sf.fileToSort);
    } else {
      throw new IllegalArgumentException("Invalid sort type");
    }


    for (Path p : sf.fileToSort) {

      File file = new File(p.toString());
      FileReader read = new FileReader();
      content += read.readFromFile(file);
    }


    assertEquals("# testing Double"
        + " important\n- this is firsrt \n- this is second\n\n"
        + "## testing sub"
        + "\n-  this is a test of the content split into two lines\n\n", content);

    sf.fileToSort.clear();

  }

  /*
  @Test
  void sortCreateDate() throws IOException {
    FileWalker sf = new FileWalker();
    sf.fileToSort.clear();

    String[] args4 = {"src/test/"
        + "resources/test/tester.md",
        "CREATED",
        "src/test/resources/SampleOutput/output"};
    FileReader write = new FileReader();

    String content = "";

    Path startingDirectory = Path.of(args4[0]);

    String[] args = {startingDirectory.toString(), args4[1], args4[2]};


    // Run the main method
    Driver.main(args);


    Files.walkFileTree(startingDirectory, sf);

    if ((args[1].equals("FILENAME"))) {
      SortType.FILENAME.sortByName(sf.fileToSort);
    } else if ((args[1].equals("CREATED"))) {
      SortType.CREATED.sortByDateCreated(sf.fileToSort);
    } else if ((args[1].equals("MODIFIED"))) {
      SortType.MODIFIED.sortByDateModified(sf.fileToSort);
    } else {
      throw new IllegalArgumentException("Invalid sort type");
    }

    for (Path p : sf.fileToSort) {

      File file = new File(p.toString());
      FileReader read = new FileReader();
      content += read.readFromFile(file);
    }


    assertEquals("## testing sub"
        + "\n-  this is a test of the content split into two lines\n\n", content);

  }

  */

  /**
   * Tests the main method when there is not valid arg
   *
   * @throws IOException if the file is not found
   */

  @Test
  void sortTestingToString() throws IOException {

    String[] args4 = {"src/test/resources/test/tester.md",
        "FILENAME",
        "src/test/resources/SampleOutput/output"};



    Driver.main(args4);

    assertEquals(SortType.FILENAME, Driver.sortType);
  }

  @Test
  void sortTestingCreated() throws IOException {

    String[] args4 = {"src/test/resources/test/tester.md",
        "CREATED",
        "src/test/resources/SampleOutput/output"};


    Driver.main(args4);

    assertEquals(SortType.CREATED, Driver.sortType);
  }

  @Test
  void sortTestingModified() throws IOException {

    String[] args4 = {"src/test/resources/test/tester.md",
        "MODIFIED",
        "src/test/resources/SampleOutput/output"};


    Driver.main(args4);

    assertEquals(SortType.MODIFIED, Driver.sortType);
  }

  /* FileWalker sf = new FileWalker();
    sf.fileToSort.removeAll(sf.fileToSort);

    String[] args4 = {"src/test/resources/test/tester.md",
        "FILENAME",
        "src/test/resources/SampleOutput/output"};

    String content = "";


    Path startingDirectory = Path.of(args4[0]);



    String[] args = {startingDirectory.toString(), args4[1], args4[2]};


    Files.walkFileTree(startingDirectory, sf);

    if ((args[1].equals("FILENAME"))) {
      SortType.FILENAME.sortByName(sf.fileToSort);
    } else if ((args[1].equals("CREATED"))) {
      SortType.CREATED.sortByDateCreated(sf.fileToSort);
    } else if ((args[1].equals("MODIFIED"))) {
      SortType.MODIFIED.sortByDateModified(sf.fileToSort);
    } else {
      throw new IllegalArgumentException("Invalid sort type");
    }

    for (Path p : sf.fileToSort) {

      File file = new File(p.toString());
      FileReader read = new FileReader();
      content += read.readFromFile(file);
    }


    assertEquals("## testing sub"
        + "\n-  this is a test of the content split into two lines\n\n", content);
*/


  /**
   * Tests the main method when there is invalid arg
   * sorting is invalid
   *
   * @throws IOException if the file is not found
   */
  @Test
  void testMainWithInvalidSortType() throws IOException {

    // Prepare test data
    Path inputDir = Path.of("src/test/resources/test/tester.md");
    Path outputFile =
        Path.of("src/test/resources/SampleOutput");


    // Set up command line arguments with an invalid sort type
    String[] args = {inputDir.toString(), "INVALID_SORT_TYPE", outputFile.toString()};

    // Run the main method and expect an exception
    assertThrows(IllegalArgumentException.class, () -> Driver.main(args));
  }


}