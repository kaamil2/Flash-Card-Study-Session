package cs3500.pa01.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * class for the session model
 */
public class SessionModel {

  //number of times a card was changed from hard to easy
  int numChangedHardToEasy;
  //number of times a card was changed from easy to hard
  int numChangedEasyToHard;
  //total number of cards answered
  int totalAnswered;

  /**
   * constructor for the session model
   */
  public SessionModel() {
    numChangedHardToEasy = 0;
    numChangedEasyToHard = 0;
    totalAnswered = 0;
  }

  /**
   * updates the sr file to the proper state after a card has been answered
   *
   * @param filePath the file path of the sr file
   * @param question the question of the card
   * @param answer   the answer of the card
   * @param isHard   if the card was hard or not
   * @throws IOException if there is an error reading the file
   */
  public void updateFile(File filePath, String question, String answer, boolean isHard)
      throws IOException {
    totalAnswered++;
    Scanner sc = new Scanner(filePath);
    StringBuilder buffer = new StringBuilder();

    while (sc.hasNextLine()) {
      buffer.append(sc.nextLine()).append("\n");
    }

    String fileContents = buffer.toString();

    String oldLine = question + ":::" + answer;
    String newAnswer;

    if (answer.contains("***Hard") && !isHard) {
      numChangedHardToEasy++;
      newAnswer = answer.substring(0, answer.indexOf("***")) + "***Easy";
    } else if (answer.contains("***Easy") && isHard) {
      numChangedEasyToHard++;
      newAnswer = answer.substring(0, answer.indexOf("***")) + "***Hard";
    } else {
      newAnswer = answer;
    }
    String newLine = question + ":::" + newAnswer;

    fileContents = fileContents.replace(oldLine, newLine);

    FileWriter writer = new FileWriter(filePath);

    writer.append(fileContents);
    writer.flush();

  }

  /**
   * gets the total number of cards answered
   *
   * @return the total number of cards answered
   */
  public int getTotalAnswered() {
    return totalAnswered;
  }

  /**
   * gets the number of cards changed from hard to easy
   *
   * @return the number of cards changed from hard to easy
   */
  public int getNumChangedHardToEasy() {
    return numChangedHardToEasy;
  }

  /**
   * gets the number of cards changed from easy to hard
   *
   * @return the number of cards changed from easy to hard
   */
  public int getNumChangedEasyToHard() {
    return numChangedEasyToHard;
  }

}
