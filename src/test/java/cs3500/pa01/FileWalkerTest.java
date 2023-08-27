package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWalkerTest {


  /**
   * Tests the sortByName method
   * Being accesed by the walker class
   */

  @Test
  void sortByName() {

    ArrayList<Path> fileToSort = new ArrayList<>();
    fileToSort.add(Path.of("file3.md"));
    fileToSort.add(Path.of("file1.md"));
    fileToSort.add(Path.of("file2.md"));

    Collections.shuffle(fileToSort); // Randomize the order

    FileWalker.fileToSort = fileToSort;
    FileWalker.sortByName();


    ArrayList<Path> expected = new ArrayList<>();
    expected.add(Path.of("file1.md"));
    expected.add(Path.of("file2.md"));
    expected.add(Path.of("file3.md"));

    assertEquals(expected, FileWalker.fileToSort);
  }


  /**
   * Tests the sortByDateModified method
   * Being accesed by the walker class
   * showing it is invalid and will not work
   */
  @Test
  void invalidSortByDateModified() {
    ArrayList<Path> fileToSort = new ArrayList<>();
    fileToSort.add(Path.of("/Users/kaamilthobani/Desktop/CS350"));
    fileToSort.add(Path.of("/Users/kaamilthobani/De/CS3500/pa01-kaamil2/test/te"));

    Collections.shuffle(fileToSort); // Randomize the order

    FileWalker.fileToSort = fileToSort;

    assertThrows(IllegalArgumentException.class, () -> {
      // Perform the sorting operation
      FileWalker.sortByDateModified();
    });
  }


  /**
   * Tests the sortByDateModified method
   * Being accesed by the walker class
   * will show it is invalid and will not work
   */
  @Test
  void invalidSortByDateCreated() {

    ArrayList<Path> fileToSort = new ArrayList<>();
    fileToSort.add(Path.of("/Users/kaamilthobani/Desktop/CS350"));
    fileToSort.add(Path.of("/Users/kaamilthobani/De/CS3500/pa01-kaamil2/test/te"));

    //Collections.shuffle(fileToSort); // Randomize the order


    assertThrows(IllegalArgumentException.class, () -> {
      // Perform the sorting operation
      FileWalker.fileToSort = fileToSort;
      FileWalker.sortByDateCreated();
    });
  }

  @Test
  void testPreVisitDirectory() throws IOException {
    FileWalker fw = new FileWalker();
    assertEquals(FileVisitResult.CONTINUE, fw.preVisitDirectory(Path.of("src/test/resources/test"),
        null));
  }

  @Test
  void testPostVisitDirectory() throws IOException {
    FileWalker fw = new FileWalker();
    assertEquals(FileVisitResult.CONTINUE, fw.postVisitDirectory(Path.of("src/test/resources/test"),
        null));
  }

  /*  @Test
  void sortByDateCreated() throws IOException {
    FileWalker.fileToSort.clear();
    ArrayList<Path> fileToSort = new ArrayList<>();
    fileToSort.add(Path.of("src/test/"
        + "resources/test/imp.md"));
    fileToSort.add(Path.of("src/test/"
        + "resources/test/tester.md"));

    Files.setAttribute(fileToSort.get(0),
        "creationTime", FileTime.fromMillis(1000L));

    Files.setAttribute(fileToSort.get(1),
        "creationTime", FileTime.fromMillis(2000L));

    FileWalker.fileToSort = fileToSort;
    FileWalker.sortByDateCreated();

    assertEquals(FileWalker.fileToSort.get(1), Path.of("src/test/resources/test/imp.md"));


  }*/

  /**
   * Tests the sortByDateModified method
   * Being accesed by the walker class
   *
   */
  @Test
  void sortByDateModified() {

    ArrayList<Path> fileToSort = new ArrayList<>();
    fileToSort.add(Path.of("src/"
        + "test/resources/test/doubleImp.md"));
    fileToSort.add(Path.of("src/test/resources/test/tester.md"));

    Collections.shuffle(fileToSort); // Randomize the order

    FileWalker.fileToSort = fileToSort;
    FileWalker.sortByDateModified();

  }


  /**
   * tests the visitFile method
   *
    * @throws IOException if the file is not found
   */
  @Test
  void testVisitFileFailed() throws IOException {
    if (FileWalker.fileToSort == null) {
      throw new IllegalArgumentException("File to sort is null");
    }
    FileWalker fileWalker = new FileWalker();

    fileWalker.visitFileFailed(Path.of("file1.md"), new IOException("Failed to visit file"));

    /*assertEquals(fileWalker.visitFileFailed(Path.of("file1.md"),
        new IOException("Failed to visit file")), FileVisitResult.CONTINUE);
*/
  }

  @Test
  void voidVisitFileOnMd() throws IOException {
    FileWalker fileWalker = new FileWalker();
    fileWalker.visitFile(Path.of("src/test/resources/SampleInput/cards.txt"), null);
    assertEquals(fileWalker.visitFile(Path.of("src/test/resources/SampleInput/cards.txt"),
        null), FileVisitResult.CONTINUE);
  }


}