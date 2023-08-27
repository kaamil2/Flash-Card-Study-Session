package cs3500.pa01.viewer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * class for the session viewer tests
 */
class SessionViewerTest {

  /**
   * view for the start of the session
   */
  @Test
  void start() {
    Appendable a = new StringBuilder();
    SessionViewer view = new SessionViewer(a);
    view.start();
    assertEquals("Welcome to the Spacial Recognition\n"
        + "-----------------------------------\n"
        + "please enter a valid sr path\n", a.toString());


  }

  /**
   * view for getting the card count
   */
  @Test
  void getCardCount() {
    Appendable a = new StringBuilder();
    SessionViewer view = new SessionViewer(a);
    view.getCardCount();
    assertEquals("How many cards would you like to see?\n", a.toString());
  }

  /**
   * view for the flash card question
   *
   * @throws IOException if the appendable fails
   */
  @Test
  void flashCard() throws IOException {
    Appendable a = new StringBuilder();
    SessionViewer view = new SessionViewer(a);
    view.flashCard("What is the capital of the United States?");
    assertEquals("HERE IS THE QUESTION: \n"
        + "What is the capital of the United States?\n"
        + "1.HARD  2. EASY  3.SHOW ANSWER  4.EXIT\n", a.toString());
  }

  /**
   * view for the flash card answer
   *
   * @throws IOException if the appendable fails
   */
  @Test
  void flashCardAnswer() throws IOException {
    Appendable a = new StringBuilder();
    SessionViewer view = new SessionViewer(a);
    view.flashCardAnswer("Washington D.C.***Hard");
    assertEquals("--------------------------\n"
        + "HERE IS THE ANSWER: \n"
        + "Washington D.C.\n", a.toString());
  }

  /**
   * view for the flash card queue
   *
   * @throws IOException if the appendable fails
   */
  @Test
  void flashCardQueue() throws IOException {
    Appendable a = new StringBuilder();
    SessionViewer view = new SessionViewer(a);
    view.flashCardQueue();
    assertEquals("--------------------------\n"
        + "Was It Easy or Hard\n"
        + "1.HARD  2. EASY  4.EXIT\n", a.toString());
  }

  /**
   * view for exiting the session
   *
   * @throws IOException if the appendable fails
   */
  @Test
  void exit() throws IOException {
    Appendable a = new StringBuilder();
    SessionViewer view = new SessionViewer(a);
    view.exit(0, 0, 0, 0, 0);
    assertEquals("--------------------------\n"
        + "Total Answered: 0\n"
        + "Total change hard to easy: 0\n"
        + "Total change easy to hard: 0 \n"
        + "Total Hard: 0\n"
        + "Total Easy: 0\n", a.toString());
  }

}