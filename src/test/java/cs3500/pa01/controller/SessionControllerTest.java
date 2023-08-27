package cs3500.pa01.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa01.model.SessionModel;
import cs3500.pa01.viewer.SessionViewer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * class for the session controller tests
 */
class SessionControllerTest {

  /**
   * tests the constructor if there is a null file
   */
  @Test
  public void testNotValidInputFiles() {
    File input = new File("src/test/resources/testValidSR/notAfile.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }


    File input2 = new File("src/test/resources/testValidSR/notSr.txt");
    StringBuilder sb2 = new StringBuilder();
    SessionController testController2;
    Random rand2 = new Random(22);
    try {
      testController2 = new SessionController(new FileReader(input2), sb2, rand2);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    assertThrows(IllegalArgumentException.class, () -> {
      testController2.sessionStart();
    });
    assertThrows(IllegalArgumentException.class, () -> {
      testController.sessionStart();
    });
  }


  /**
   * tests the constructor if there is a valid sr file path
   */
  @Test
  public void testValidSr() {
    File input = new File("src/test/resources/testValidSR/testFile.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    testController.sessionStart();
    assertEquals("Welcome to the Spacial Recognition\n"
        + "-----------------------------------\n"
        + "please enter a valid sr path\n"
        + "How many cards would you like to see?\n"
        + "HERE IS THE QUESTION: \n"
        + "Who lives in a pineapple under the sea \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "HERE IS THE QUESTION: \n"
        + "What is Texas NickName \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "--------------------------\n"
        + "HERE IS THE ANSWER: \n"
        + "Lone Star State\n"
        + "--------------------------\n"
        + "Was It Easy or Hard\n"
        + "1.HARD  2. EASY  4.EXIT\n"
        + "--------------------------\n"
        + "Total Answered: 2\n"
        + "Total change hard to easy: 0\n"
        + "Total change easy to hard: 0 \n"
        + "Total Hard: 1\n"
        + "Total Easy: 4\n", sb.toString());

  }

  /**
   * tests the constructor if there is a valid sr file path
   */
  @Test
  public void testValidSrP2() {

    File input = new File("src/test/resources/testValidSR/testFileDiffiteration.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    testController.sessionStart();
    assertEquals("Welcome to the Spacial Recognition\n"
        + "-----------------------------------\n"
        + "please enter a valid sr path\n"
        + "How many cards would you like to see?\n"
        + "HERE IS THE QUESTION: \n"
        + "What is Texas NickName \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "HERE IS THE QUESTION: \n"
        + "Who lives in a pineapple under the sea \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "--------------------------\n"
        + "HERE IS THE ANSWER: \n"
        + "SpongeBob SquarePants\n"
        + "--------------------------\n"
        + "Was It Easy or Hard\n"
        + "1.HARD  2. EASY  4.EXIT\n"
        + "HERE IS THE QUESTION: \n"
        + "What is 4 + 4 \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "HERE IS THE QUESTION: \n"
        + "Texas Capital \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "--------------------------\n"
        + "HERE IS THE ANSWER: \n"
        + " Austin\n"
        + "--------------------------\n"
        + "Was It Easy or Hard\n"
        + "1.HARD  2. EASY  4.EXIT\n"
        + "--------------------------\n"
        + "Total Answered: 3\n"
        + "Total change hard to easy: 0\n"
        + "Total change easy to hard: 0 \n"
        + "Total Hard: 2\n"
        + "Total Easy: 3\n", sb.toString());

  }

  /**
   * tests if the user imputs not a number for the number of cards
   */
  @Test
  public void notNumber() {
    File input = new File("src/test/resources/testValidSR/notNumber.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }

    assertThrows(IllegalArgumentException.class, () -> testController.sessionStart());

  }

  /**
   * tests the changing from esy to hard
   *
   * @throws IOException if file not found
   */
  @Test
  public void changeEasyHard() throws IOException {

    File input = new File("src/test/resources/testValidSR/changingLevel.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    try {
      testController = new SessionController(new FileReader(input), sb, new Random(22));

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    testController.sessionStart();


    Scanner sc = new Scanner("src/test/resources/SampleOutput/hardandEasyQuestions.sr");
    StringBuilder buffer = new StringBuilder();

    while (sc.hasNextLine()) {
      buffer.append(sc.nextLine()).append("\n");
    }

    String fileContents = "What is your favorite color ::: Blue***Easy\n"
        + "What is the last thing you ate ::: A piece of chocolate***Hard";

    FileWriter writer =
        new FileWriter("src/test/resources/SampleOutput/hardandEasyQuestions.sr");

    writer.append(fileContents);
    writer.flush();
  }

  /**
   * tests the toQuit method
   */
  @Test
  public void toQuit() {
    File input = new File("src/test/resources/testValidSR/toExit.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    testController.sessionStart();
    assertEquals("Welcome to the Spacial Recognition\n"
        + "-----------------------------------\n"
        + "please enter a valid sr path\n"
        + "How many cards would you like to see?\n"
        + "HERE IS THE QUESTION: \n"
        + "Texas Capital \n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n"
        + "--------------------------\n"
        + "Total Answered: 0\n"
        + "Total change hard to easy: 0\n"
        + "Total change easy to hard: 0 \n"
        + "Total Hard: 1\n"
        + "Total Easy: 4\n", sb.toString());
  }

  @Test
  public void noCards() {
    File input = new File("src/test/resources/testValidSR/noCardsWanted.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    testController.sessionStart();
    assertEquals("Welcome to the Spacial Recognition\n"
        + "-----------------------------------\n"
        + "please enter a valid sr path\n"
        + "How many cards would you like to see?\n"
        + "--------------------------\n"
        + "Total Answered: 0\n"
        + "Total change hard to easy: 0\n"
        + "Total change easy to hard: 0 \n"
        + "Total Hard: 1\n"
        + "Total Easy: 4\n", sb.toString());
  }


  /**
   * tests the invalid input of file path
   */
  @Test
  public void testInvalidInput() {
    File input = new File("src/test/resources/testValidSR/invalidInput.txt");
    StringBuilder sb = new StringBuilder();
    SessionController testController;
    Random rand = new Random(22);
    try {
      testController = new SessionController(new FileReader(input), sb, rand);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }
    assertThrows(IllegalArgumentException.class, () -> testController.sessionStart());

    File input2 = new File("src/test/"
        + "resources/testValidSR/invalidInputInAnswer.txt");
    StringBuilder sb2 = new StringBuilder();
    SessionController testController2;
    Random rand2 = new Random(22);
    try {
      testController2 = new SessionController(new FileReader(input2), sb2, rand2);

    } catch (Exception e) {
      throw new IllegalArgumentException("test file not found");
    }

    assertThrows(IllegalArgumentException.class, () -> testController2.sessionStart());
  }


}

