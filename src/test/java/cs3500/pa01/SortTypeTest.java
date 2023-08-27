package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class SortTypeTest {


  /**
   * Tests the sortByName method
   *
   */

  @Test
  void testSortByName() {
    // Prepare test data
    ArrayList<Path> fileToSort = new ArrayList<>(Arrays.asList(
        Path.of("file3.txt"),
        Path.of("file1.txt"),
        Path.of("file2.txt")
    ));

    // Call the method
    SortType.sortByName(fileToSort);

    // Verify the result
    ArrayList<Path> expected = new ArrayList<>(Arrays.asList(
        Path.of("file1.txt"),
        Path.of("file2.txt"),
        Path.of("file3.txt")
    ));
    assertEquals(expected, fileToSort);

  }

  /**
   * Tests the sortByDateCreated method
   */
  @Test
  void testSortByDateCreated() {
    // Prepare test data
    ArrayList<Path> fileToSort = new ArrayList<>(Arrays.asList(
        Path.of("src/test/resources/SampleInput/best.md"),
        Path.of("src/test/resources/SampleInput/test.md")
    ));

    // Call the method
    SortType.sortByDateCreated(fileToSort);

    assertEquals(fileToSort.get(1).toString(),
        "src/"
            + "test/resources/SampleInput/test.md");

    ArrayList<Path> badFiles = new ArrayList<>(Arrays.asList(
        Path.of("/Users/kaamilthobani/Desktop/CS3500/pa01-kaamil2/SampleIon"),
        Path.of("/Users/kaamilthobani/Desktop/CS3500/pa01-kaamil2/SampleInput/teed.md")
    ));

    assertThrows(IllegalArgumentException.class, () -> SortType.sortByDateCreated(badFiles));

  }

  /**
   * Tests the sortByDateModified method
   */
  @Test
  void testSortByDateModified() {
    // Prepare test data
    ArrayList<Path> fileToSort = new ArrayList<>(Arrays.asList(
        Path.of("src/"
            + "test/resources/SampleInput/best.md"),
        Path.of("src/test/resources/"
            + "SampleInput/test.md")
    ));

    // Call the method
    SortType.sortByDateModified(fileToSort);

    assertEquals(fileToSort.get(0).toString(),
        "src/test/resources/SampleInput/best.md");

    ArrayList<Path> badFiles = new ArrayList<>(Arrays.asList(
        Path.of("/Users/kaamilthobani/Desktop/CS3500/pa01-kaamil2/SampleIon"),
        Path.of("/Users/kaamilthobani/Desktop/CS3500/pa01-kaamil2/SampleInput/teed.md")
    ));

    assertThrows(IllegalArgumentException.class, () -> SortType.sortByDateModified(badFiles));

  }

  /**
   * Tests the toString method of each enum
   */
  @Test
  void testToString() {
    assertEquals("filename", SortType.FILENAME.toString());
    assertEquals("created", SortType.CREATED.toString());
    assertEquals("modified", SortType.MODIFIED.toString());

  }

}