package cs3500.pa01;


import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A class that implements FileVisitor to walk through a directory and find all the files
 * that end in .md
 */
public class FileWalker implements FileVisitor<Path> {

  /**
   * A list of all the files that end in .md
   */
  public static ArrayList<Path> fileToSort = new ArrayList<>();


  //create sorting types

  /**
   * Sorts the files by name
   */

  public static void sortByName() {
    Collections.sort(fileToSort);
  }

  /**
   * Sorts the files by date created
   */
  public static void sortByDateCreated() {
    Collections.sort(fileToSort, (o1, o2) -> {
      try {
        return Files.readAttributes(o1, BasicFileAttributes.class).creationTime()
            .compareTo(Files.readAttributes(o2, BasicFileAttributes.class).creationTime());
      } catch (IOException e) {
        throw new IllegalArgumentException("Error reading file attributes");
      }
    });

  }

  /**
   * Sorts the files by date modified
   */

  public static void sortByDateModified() {
    Collections.sort(fileToSort, (o1, o2) -> {
      try {
        return Files.readAttributes(o1, BasicFileAttributes.class).lastModifiedTime()
            .compareTo(Files.readAttributes(o2, BasicFileAttributes.class).lastModifiedTime());
      } catch (IOException e) {
        throw new IllegalArgumentException("Error reading file attributes");
      }
    });

  }


  /**
   * Invoked for a directory before entries in the directory are visited.
   *
   * @param dir   a reference to the directory
   * @param attrs the directory's basic attributes
   * @return CONTINUE
   * @throws IOException if an I/O error occurs
   */

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    return CONTINUE;
  }

  /**
   * Invoked for a file in a directory.
   *
   * @param file  a reference to the file
   * @param attrs the file's basic attributes
   * @return CONTINUE
   * @throws IOException if an I/O error occurs
   */

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    String stringFile = file.toString();

    if (stringFile.endsWith(".md")) {
      if (!fileToSort.contains(file)) {
        fileToSort.add(file);
        Collections.sort(fileToSort);
      }

    }
    return CONTINUE;
  }

  /**
   * @param file a reference to the file
   * @param exc  the I/O exception that prevented the file from being visited
   * @return CONTINUE
   * @throws IOException if an I/O error occurs
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    System.err.println(exc);
    return CONTINUE;
  }

  /**
   * @param dir a reference to the directory
   * @param exc {@code null} if the iteration of the directory completes without
   *            an error; otherwise the I/O exception that caused the iteration
   *            of the directory to complete prematurely
   * @return CONTINUE
   * @throws IOException if an I/O error occurs
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    return CONTINUE;
  }


}


