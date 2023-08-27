package cs3500.pa01.reader;

import java.util.Objects;
import java.util.Scanner;

/**
 * Reads contents from an input to a string.
 */
public class ReaderImpl implements Reader {

  // Fields
  private final Readable readable;

  private final Scanner scanner;

  //Scanner scanner = new Scanner(readable);

  /**
   * Constructs a ReaderImpl.
   *
   * @param readable the readable to read from
   */
  public ReaderImpl(Readable readable) {
    this.readable = Objects.requireNonNull(readable);
    this.scanner = new Scanner(readable);
  }

  /**
   * Reads the contents of a message to a string.
   *
   * @return the message contents
   */
  @Override
  public String read() {


    StringBuilder output = new StringBuilder();
    if (scanner.hasNextLine()) {
      output.append(scanner.nextLine());
    }
    return output.toString();
  }
}
