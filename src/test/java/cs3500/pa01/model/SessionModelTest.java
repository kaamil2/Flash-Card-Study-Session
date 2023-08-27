package cs3500.pa01.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * class for the session model tests
 */
class SessionModelTest {


  /**
   * tests the update file method
   *
   * @throws IOException exception if file not found
   */
  @Test
  void updateFile() throws IOException {
    SessionModel model = new SessionModel();
    File file = new File("src/test/resources/SessionModelTests/testFile.sr");
    model.updateFile(file, "Where is OOD ", "West Village F ***Hard",
        true);
    Scanner sc = new Scanner(file);
    StringBuilder buffer = new StringBuilder();

    while (sc.hasNextLine()) {
      buffer.append(sc.nextLine()).append("\n");
    }

    String fileContents = buffer.toString();

    assertEquals("Where is OOD ::: West Village F ***Hard\n"
        + "What is the name of our President ::: Joe Biden ***Hard\n"
        + "What is the name of our Vice President ::: Kamala Harris ***Hard\n"
        + "What is the name of our Governor ::: Andrew Cuomo ***Easy\n", fileContents);


    model.updateFile(file, "What is the name of our President ",
        " Joe Biden ***Hard", false);

    sc = new Scanner(file);
    buffer = new StringBuilder();

    while (sc.hasNextLine()) {
      buffer.append(sc.nextLine()).append("\n");
    }

    fileContents = buffer.toString();

    assertEquals("Where is OOD ::: West Village F ***Hard\n"
        + "What is the name of our President ::: Joe Biden ***Easy\n"
        + "What is the name of our Vice President ::: Kamala Harris ***Hard\n"
        + "What is the name of our Governor ::: Andrew Cuomo ***Easy\n", fileContents);

    model.updateFile(file, "What is the name of our President ",
        " Joe Biden ***Easy", true);

    assertThrows(FileNotFoundException.class, () ->
        model.updateFile(new File("src/test/resources/SessionModelTests/m.sr"),
            "What is the name of our President ", " Joe Biden ***Easy",
            true));


  }

  /**
   * test the get total answered methdos in model class
   *
   * @throws IOException exception if file not found
   */

  @Test
  void getTotalAnswered() throws IOException {

    SessionModel model = new SessionModel();
    File file = new File("src/test/resources/SessionModelTests/math.sr");
    model.updateFile(file, "1 + 1", " 2", true);
    model.updateFile(file, "1 + 1", " 2", true);
    model.updateFile(file, "1 + 1", " 2", true);
    model.updateFile(file, "1 + 1", " 2", true);

    assertEquals(4, model.totalAnswered);

  }

  /**
   * test the get total num changed hard to easy method in model class
   *
   * @throws IOException exception if file not found
   */

  @Test
  void getNumChangedHardToEasy() throws IOException {

    SessionModel model = new SessionModel();
    File file = new File("src/test/resources/SessionModelTests/math.sr");
    model.updateFile(file, "1 + 1", " 2***Hard", false);


    assertEquals(1, model.numChangedHardToEasy);
    model.updateFile(file, "1 + 1", " 2***Easy", true);


  }

  /**
   * test the get total to easy to hard method in model class
   *
   * @throws IOException exception if file not found
   */

  @Test
  void getNumChangedEasyToHard() throws IOException {

    SessionModel model = new SessionModel();
    File file = new File("src/test/resources/SessionModelTests/math.sr");
    model.updateFile(file, "1 + 1", " 2***Hard", false);
    model.updateFile(file, "1 + 1", " 2***Easy", true);


    assertEquals(1, model.numChangedEasyToHard);
  }
}