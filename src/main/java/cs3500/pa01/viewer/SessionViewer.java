package cs3500.pa01.viewer;

//src/test/resources/GivenExamples/javaWithQuestion.md FILENAME
// src/test/resources/SampleOutput/javaWithQuestions

import java.io.IOException;

/**
 * represents the session viewer for the flash card session
 */
public class SessionViewer {
  private Appendable appendable;
  /**
   * constructor for session viewer
   */

  public SessionViewer(Appendable a) {
    this.appendable = a;
  }

  /**
   * view for starting the session
   */
  public void start() {

    try {
      appendable.append("Welcome to the Spacial Recognition\n");
      appendable.append("-----------------------------------\n");
      appendable.append("please enter a valid sr path\n");
    } catch (Exception e) {
      // do something
    }
  }

  /**
   * view for getting the card count
   */
  public void getCardCount() {
    try {
      appendable.append("How many cards would you like to see?\n");
    } catch (Exception e) {
      // do something
    }
  }

  /**
   * view for showing the card question
   *
   * @param question the question to be shown
   */
  public void flashCard(String question) throws IOException {
    appendable.append("HERE IS THE QUESTION: \n");
    appendable.append(question);
    appendable.append("\n1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n");
  }

  /**
   * view for showing the cards answer
   *
   * @param answer the answer to be shown
   */
  public void flashCardAnswer(String answer) throws IOException {
    appendable.append("--------------------------\n");
    appendable.append("HERE IS THE ANSWER: \n");
    appendable.append(answer.substring(0, answer.indexOf("***")) + "\n");
  }

  /**
   * view for showing the card 3 choices after answer was pressed
   */
  public void flashCardQueue() throws IOException {
    appendable.append("--------------------------\n");
    appendable.append("Was It Easy or Hard\n");
    appendable.append("1.HARD  2. EASY  4.EXIT\n");
  }


  /**
   * view of the exit screen
   *
   * @param totalAnswered        total answered questions
   * @param numChangedHardToEasy number of questions changed from hard to easy
   * @param numChangedEasyToHard number of questions changed from easy to hard
   * @param totalHard            total hard questions in the sr file
   * @param totalEasy            total easy questions in the sr file
   */
  public void exit(int totalAnswered, int numChangedHardToEasy, int numChangedEasyToHard,
                   int totalHard, int totalEasy) throws IOException {

    appendable.append("--------------------------\n");
    appendable.append("Total Answered: " + totalAnswered + "\n");
    appendable.append("Total change hard to easy: " + numChangedHardToEasy + "\n");
    appendable.append("Total change easy to hard: " + numChangedEasyToHard + " \n");
    appendable.append("Total Hard: " + totalHard + "\n");
    appendable.append("Total Easy: " + totalEasy + "\n");


  }
}
