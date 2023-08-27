package cs3500.pa01;

import cs3500.pa01.controller.SessionController;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * The sort type to use
   */
  public static SortType sortType;

  /**
   * Project entry point
   *
   * @param args - no command line args required
   * @throws IOException - if there is an error reading the file
   */
  public static void main(String[] args) throws IOException {


    if (args.length == 0) {
      Readable input = new InputStreamReader(System.in);
      Appendable output = new PrintStream(System.out);
      Random rand = new Random();
      SessionController start = new SessionController(input, output, rand);
      start.sessionStart();

    } else if (args.length == 3) {

      String qnA = "";
      String content = "";

      if ((args[1].equals("FILENAME"))) {
        sortType = SortType.FILENAME;
      } else if ((args[1].equals("CREATED"))) {
        sortType = SortType.CREATED;
      } else if ((args[1].equals("MODIFIED"))) {
        sortType = SortType.MODIFIED;
      } else {
        throw new IllegalArgumentException("Invalid sort type");
      }

      Path startingDirectory = Path.of(args[0]);


      FileWalker sf = new FileWalker();

      Files.walkFileTree(startingDirectory, sf);


      for (Path p : sf.fileToSort) {

        File file = new File(p.toString());
        FileReader read = new FileReader();
        content += read.readFromFile(file);
        qnA += read.questionsAndAnswers;
      }

      FileReader write = new FileReader();

      File output = new File(args[2]);

      write.writeToFile(output, content);
      write.writeToSr(output, qnA);


    }


  }


}