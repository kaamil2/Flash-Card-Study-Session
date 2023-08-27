package cs3500.pa01.reader;

/**
 * Reads contents from an input to a string.
 */
public interface Reader {
  /**
   * Reads the contents of a message to a string.
   *
   * @return the message contents
   */
  public String read();
}
