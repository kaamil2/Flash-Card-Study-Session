package cs3500.pa01.controller;

/**
 * Represents the possible inputs for the math game.
 */
public enum InputVal {

  //Enum values

  /**
   * The exit input.
   */
  EXIT("4"),
  /**
   * The hard input.
   */
  HARD("1"),
  /**
   * The easy input.
   */
  EASY("2"),
  /**
   * The show answer input.
   */
  SHOW_ANSWER("3");


  String input;

  /**
   * Constructs an input value.
   *
   * @param number the input value
   */

  InputVal(String number) {
    this.input = number;
  }

  /**
   * Returns the input value as a string.
   *
   * @return the input value as a string
   */
  public String toString() {
    return this.input;
  }
}
