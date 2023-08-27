package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;


/**
 * This is the enum of the different types of sorting algorithms that can be used.
 * - file name sort
 * - file created date sort
 * - file modified date sort
 */

public enum SortType {
  /**
   * Sorts the files by name
   */
  FILENAME {
    @Override
    public String toString() {
      return "filename";
    }

  },
  /**
   * Sorts the files by date created
   */
  CREATED {
    @Override
    public String toString() {
      return "created";
    }
  },
  /**
   * Sorts the files by date modified
   */
  MODIFIED {
    @Override
    public String toString() {
      return "modified";
    }

  };

  /**
   * Sorts the files by date modified
   *
   * @param fileToSort the list of files to sort
   *                   * the list is sorted in place
   *                   * the list is sorted by date modifie the list is sorted in ascending order
   */
  public static void sortByDateModified(ArrayList<Path> fileToSort) {
    Driver.sortType = SortType.MODIFIED;
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
   * Sorts the files by date created
   *
   * @param fileToSort the list of files to sort
   *
   *                   the list is sorted in place
   *                   the list is sorted by date created
   */
  public static void sortByDateCreated(ArrayList<Path> fileToSort) {
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
   * Sorts the files by name
   *
   * @param fileToSort the list of files to sort
   *
   */
  public static void sortByName(ArrayList<Path> fileToSort) {
    Collections.sort(fileToSort);
  }

  /* void sort(ArrayList<Path> fileToSort) {
    if (this.toString().equals("filename")) {
      sortByName(fileToSort);
    } else if (this.toString().equals("created")) {
      sortByDateCreated(fileToSort);
    } else if (this.toString().equals("modified")) {
      sortByDateModified(fileToSort);
    } else {
      throw new IllegalArgumentException("Invalid sort type");
    }
  }
*/



}
