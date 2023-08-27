package cs3500.pa01;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


/**
 * Examples from FileIO which will be useful for the Assignment.
 */


public class FileReader {


  /**
   * keeps the index of the whole content
   */
  public int curIndex = 0;
  /**
   * keeps track of whether or not we are in the content
   */
  public boolean openContent = false;
  /**
   * keeps track of whether or not we were just  in the content
   */
  public boolean justContent = false;

  /**
   * this is the string builder for all of the questions and answers
   */
  public StringBuilder questionsAndAnswers = new StringBuilder();

  /**
   * this is the indexes of the important elements with a ::: in them
   */
  public int indexToMove = 0;

  /**
   * helps me determine when multilined if the important statemnt has a ::: and is a quesiton
   */

  public boolean isaqna = false;


  /**
   * Reads the contents from a file to a String
   *
   * @param file A file object which corresponds to a path in the
   *             file system and some information at that path
   * @return the contents of the file
   */


  public String readFromFile(File file) {
    // Initialize a Scanner to read the file
    Scanner sc;
    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      sc = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    StringBuilder content =
        new StringBuilder(); // StringBuilder is more efficient than String concatenation
    while (sc.hasNextLine()) { // Check there is another unread line in the file

      content.append(sc.nextLine()).append("\n");
      // Read the aforementioned line

      content = editFormat(content);

      System.out.println(content.toString());

    }


    return content.toString(); // Return the contents collected in the StringBuilder
  }


  /**
   * @param content which is the same content of the file that we will be putting into the review
   * @return the content that has been edited
   */


  private StringBuilder editFormat(StringBuilder content) {
    boolean title = false;

    if (content.indexOf("#", curIndex) > -1) {
      curIndex = content.indexOf("\n", curIndex);
      title = true;

    }
    if (content.indexOf("##", curIndex) > -1) {
      title = true;
      curIndex = content.indexOf("\n", curIndex + 1);

    }

    if ((content.indexOf("[[", curIndex) > -1) || openContent) {
      editContent(content);
      justContent = true;
    }

    if (!title && !openContent && content.indexOf("[[", curIndex) == -1
        && content.indexOf("##", curIndex) == -1 && !justContent) {
      content.replace((curIndex), content.indexOf("\n", curIndex + 1), "");
      curIndex = content.indexOf("\n", curIndex);
    }


    justContent = false;
    return content;

  }


  /**
   * focuses on the insideo f the important content and ensuring the right format
   *
   * @param content which is the same content of the file that we will be putting into the review
   * @return the content that has been edited
   */


  private StringBuilder editContent(StringBuilder content) {


    if (content.indexOf("[[", curIndex) > -1
        && content.indexOf(":::", curIndex) < 0) {

      boolean lastBracket = false;
      if (!openContent) {

        content.delete(curIndex, content.indexOf("[[", curIndex));
        curIndex = content.indexOf("[[", curIndex);
        indexToMove = curIndex;
        content.replace(curIndex, curIndex + 2, "\n- ");
        openContent = true;
      }
      if (content.indexOf("]]", curIndex) > -1) {
        curIndex = content.indexOf("]]", curIndex);
        openContent = false;
        if (content.indexOf("[[", curIndex) > -1) {
          editContent(content);
        } else {
          lastBracket = true;
        }
        if (lastBracket) {
          content.delete(curIndex, content.indexOf("\n", curIndex));
          curIndex = content.indexOf("\n", curIndex);
          content.append("\n");
        }
      }
      content.delete(curIndex, content.indexOf("\n", curIndex));
    }
    if (content.indexOf("[[", curIndex) > -1
        && content.indexOf(":::", curIndex) > -1 && content.indexOf("]]", curIndex) > -1) {
      String contentToMove = content.substring(content.indexOf("[[", curIndex) + 2,
          content.indexOf("]]", curIndex));
      content.delete(content.indexOf("[[", curIndex), content.indexOf("]]", curIndex) + 2);
      moveToSrFile(contentToMove);
    }

    if (openContent) {
      whileInEditContent(content);
    }


    if (content.indexOf("]]", curIndex) > -1 && openContent) {
      content.replace((content.indexOf("\n", curIndex + 1)),
          (content.indexOf("\n", curIndex + 1) + 1), " ");
      curIndex = content.indexOf("]]", curIndex);
      content.replace(curIndex, curIndex + 2, "\n");
      if (isaqna) {
        String contentToMove;
        contentToMove = content.substring(indexToMove, curIndex);
        //contentToMoveCleaner(contentToMove);
        moveToSrFile(contentToMove);
        content.delete(indexToMove, curIndex);
        isaqna = false;
        curIndex = indexToMove;
      }
      openContent = false;


    }


    return content;
  }

  /**
   * the conent going into the sr file will be cleaned
   *
   * @param content the string going into the sr file
   * @return a cleaned string
   */
  private String contentToMoveCleaner(String content) {
    StringBuilder moving = new StringBuilder();
    moving.append(content);
    if (moving.indexOf("\n") > -1) {
      moving.delete(content.indexOf("\n"), content.indexOf("\n") + 1);
      content = moving.toString();
      content = contentToMoveCleaner(content);
    /*}else if (moving.indexOf("[[") > -1) {
      moving.delete(content.indexOf("[["), content.indexOf("[[") + 1);
      content = moving.toString();
      content = contentToMoveCleaner(content);*/
    } else if (moving.indexOf("- ") > -1) {
      moving.delete(content.indexOf("- "), content.indexOf("- ") + 2);
      content = moving.toString();
      content = contentToMoveCleaner(content);
    }

    return content;

  }

  private void moveToSrFile(String contentBeingMoved) {
    contentBeingMoved = contentToMoveCleaner(contentBeingMoved);
    contentBeingMoved += ("***Hard\n");
    questionsAndAnswers.append(contentBeingMoved);

  }

  /**
   * focuses on the insideo f the important content and ensuring the right format
   *
   * @param content which is the same content of the file that we will be putting into the review
   * @return the content that has been edited
   */

  private StringBuilder whileInEditContent(StringBuilder content) {
    if (content.indexOf(":::") > -1) {
      isaqna = true;
    }
    content.delete(curIndex, content.indexOf("\n", curIndex));

    curIndex = content.indexOf("\n", curIndex);
    return content;
  }


  /**
   * Writes the given String to the given filepath.
   *
   * @param file     where to write the contents
   * @param contents contents to write to the file
   */

  public void writeToFile(File file, String contents) {

    // Add .md to the end of the file path.
    // You may need to change this to get the desired user-experience that was asked for.
    Path path = Path.of(file.toPath() + ".md");

    // Convert String to data for writing ("raw" byte data)
    byte[] data = contents.getBytes();

    // The path may not exist, or we may not have permissions to write to it,
    // in which case we need to handle that error (hence try-catch)
    try {
      // special syntax, so you can add `.md` to the file-path to write a Markdown file.
      Files.write(path, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes the given String to the given filepath.
   *
   * @param file     where to write the contents
   * @param contents contents to write to the file
   */
  public void writeToSr(File file, String contents) {

    // Add .md to the end of the file path.
    // You may need to change this to get the desired user-experience that was asked for.
    Path path = Path.of(file.toPath() + ".sr");

    // Convert String to data for writing ("raw" byte data)
    byte[] data = contents.getBytes();

    // The path may not exist, or we may not have permissions to write to it,
    // in which case we need to handle that error (hence try-catch)
    try {
      // special syntax, so you can add `.md` to the file-path to write a Markdown file.
      Files.write(path, data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}





