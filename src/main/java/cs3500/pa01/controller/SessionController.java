package cs3500.pa01.controller;

import cs3500.pa01.model.SessionModel;
import cs3500.pa01.reader.Reader;
import cs3500.pa01.reader.ReaderImpl;
import cs3500.pa01.reader.SrFileReader;
import cs3500.pa01.viewer.SessionViewer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * class for the session controller
 */
public class SessionController {


  //hashmap of questions and answers
  HashMap<String, String> questionsAndAnswersEasy = new HashMap<>();
  //hashmap of questions and answers
  HashMap<String, String> questionsAndAnswersHard = new HashMap<>();

  //Session viewer for the session controller
  SessionViewer viewer;

  //Session model for the session controller
  SessionModel model;


  //number of cards in the file
  private int cardCount = 0;

  // size of the hard cards
  private int sizeHard = 0;
  // size of the easy cards
  private int sizeEasy = 0;

  //file for the session controller
  File file;

  //reader for the session controller
  Reader input;

  //random for the session controller
  Random rand;

  /**
   * constructor for the session controller
   */
  public SessionController(Readable reader, Appendable appendable, Random rand) {
    viewer = new SessionViewer(appendable);
    model = new SessionModel();
    input = new ReaderImpl(reader);
    this.rand = rand;

  }


  /**
   * starts the session controller gets the hashmaps and gets amounte of cards wanted
   */
  public void sessionStart() {
    viewer.start();
    String inputReader = input.read();
    Path path = Path.of(inputReader);

    if (!path.toString().endsWith(".sr")) {
      throw new IllegalArgumentException("input not sr file");
    }


    try {
      file = new File(path.toString());
    } catch (Exception e) {
      throw new IllegalArgumentException("input File Not Valid");
    }
    createQuestionHash(file);
    sessionCardCount();
  }

  /**
   * gets the amount of cards wanted from the user
   */
  private void sessionCardCount() {
    viewer.getCardCount();
    String numInputReader = input.read();
    try {
      Integer.valueOf(numInputReader);
      cardCount = Integer.valueOf(numInputReader);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("input not a number");
    }

    try {
      pickRandomizeQuestion();
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }


  }

  /**
   * reads a line from file and gives it to add to the hashmap
   */
  private void createQuestionHash(File input) {

    if (input.exists()) {
      SrFileReader srFileReader = new SrFileReader(input);
      addToHashMap(srFileReader.read());
    } else {
      throw new IllegalArgumentException("invalid path is not a file");
    }

  }


  /**
   * adds the questions and answers to the hashmap
   *
   * @param qnA the line given from the helper method
   */
  private void addToHashMap(String qnA) {
    String[] qnAarray = qnA.split("\n");
    for (String s : qnAarray) {
      String[] qnAarray2 = s.split(":::");
      if (qnAarray2[1].contains("***Hard")) {
        questionsAndAnswersHard.put(qnAarray2[(0)], qnAarray2[1]);
      } else if (qnAarray2[1].contains("***Easy")) {
        questionsAndAnswersEasy.put(qnAarray2[0], qnAarray2[1]);
      } else {
        System.out.println("invalid format");
      }
    }
  }


  //TODO randomly starting from the hard hash map choose a question until the card count is
  //  reached or the hard hashmap is empty then move to the easy hashmap and choose a
  //  question until the card count is reached or the easy hashmap is empty give the user
  //  the option to call the card easy or hard and then update the sr file to reflect the
  //  if it was easy or hard then repeat the process until the card count is reached
  //  at any point the user can press quit and the program will end and the sr file will
  //  be updated and the program will print out stats of the session and the user can,
  // choose to start a new session or quit the program

  /**
   * picks a random question from the hashmap
   *
   * @throws IOException if the file is invalid
   */
  private void pickRandomizeQuestion() throws IOException {
    sizeHard = questionsAndAnswersHard.keySet().size();
    sizeEasy = questionsAndAnswersEasy.keySet().size();

    if (sizeHard > 0 && cardCount > 0) {
      pickRandomQuestion(sizeHard, questionsAndAnswersHard);


    } else if (sizeEasy > 0 && cardCount > 0) {
      pickRandomQuestion(sizeEasy, questionsAndAnswersEasy);

    } else if (cardCount == 0 || ((sizeEasy == 0 && sizeHard == 0))) {
      flashCardExit();
    }


  }

  /**
   * helper method for pickRandomizeQuestion
   * gives a question and answer to the flashcard method
   *
   * @param sizeCard            size of the hard hashmap
   * @param questionsAndAnswers hashmap of questions and answers
   * @throws IOException if the file is invalid
   */
  private void pickRandomQuestion(int sizeCard, HashMap<String, String> questionsAndAnswers)
      throws IOException {
    int random = rand.nextInt(sizeCard);
    cardCount--;
    String question = questionsAndAnswers.keySet().toArray()[random].toString();

    String answer = questionsAndAnswers.get(question);
    questionsAndAnswers.keySet().remove(question);
    flashCard(question, answer);
  }

  /**
   * flash card method that gives the user the option to answer the question
   *
   * @param question the question to be asked
   * @param answer   the answer to be given if prompted
   * @throws IOException if the file is invalid
   */
  private void flashCard(String question, String answer) throws IOException {
    viewer.flashCard(question);
    String inputReader = input.read();
    if (inputReader.equals(InputVal.HARD.toString())) {
      model.updateFile(file, question, answer, true);
      pickRandomizeQuestion();
    } else if (inputReader.equals(InputVal.EASY.toString())) {
      model.updateFile(file, question, answer, false);
      pickRandomizeQuestion();
    } else if (inputReader.equals(InputVal.SHOW_ANSWER.toString())) {
      viewer.flashCardAnswer(answer);
      flashCardQueue(question, answer);
    } else if (inputReader.equals(InputVal.EXIT.toString())) {
      flashCardExit();

    } else {
      throw new IllegalArgumentException("invalid input");
    }
  }

  /**
   * waits for the user again to move on with program
   *
   * @param question the question to be asked
   * @param answer   the answer to be given if prompted
   * @throws IOException if the file is invalid
   */
  private void flashCardQueue(String question, String answer) throws IOException {
    viewer.flashCardQueue();
    String inputReader = input.read();
    if (inputReader.equals(InputVal.HARD.toString())) {
      model.updateFile(file, question, answer, true);
      pickRandomizeQuestion();
    } else if (inputReader.equals(InputVal.EASY.toString())) {
      model.updateFile(file, question, answer, false);
      pickRandomizeQuestion();
    } else if (inputReader.equals(InputVal.EXIT.toString())) {
      flashCardExit();
    } else {
      throw new IllegalArgumentException("invalid input");
    }
  }

  /**
   * gets the size of how many times a string is in the file
   *
   * @param find the string to be found
   * @return the number of times the string is in the file
   * @throws FileNotFoundException if the file is invalid
   */
  private int getSize(String find) throws FileNotFoundException {
    Scanner sc = new Scanner(file);
    StringBuilder buffer = new StringBuilder();
    int lastIndex = 0;
    int count = 0;
    while (sc.hasNextLine()) {
      buffer.append(sc.nextLine()).append("\n");
    }
    String fileContents = buffer.toString();
    while (lastIndex != -1) {

      lastIndex = fileContents.indexOf(find, lastIndex);

      if (lastIndex != -1) {
        count++;
        lastIndex += find.length();
      }
    }
    return count;
  }

  /**
   * exits the program and prints out the stats of the session
   *
   * @throws FileNotFoundException if the file is invalid
   */
  private void flashCardExit() throws IOException {
    int totalHard = getSize("***Hard");
    int totalEasy = getSize("***Easy");

    viewer.exit(model.getTotalAnswered(), model.getNumChangedHardToEasy(),
        model.getNumChangedEasyToHard(), totalHard, totalEasy);
  }


}
