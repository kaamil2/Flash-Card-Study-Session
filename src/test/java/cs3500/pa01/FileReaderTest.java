package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderTest {


  private FileReader fileReader;

  @BeforeEach
  void setUp() {
    fileReader = new FileReader();
    int curIndex = 0;
  }

  /**
   * tests the edit and format method
   * specific for when the file has a title
   */
  @Test
  void testEditFormatWithTitle() {

    StringBuilder content = new StringBuilder("Some content\n# Title\nMore content");
    File file =
        new File("src"
            + "/test/resources/SampleInput"
            + "/test.md");


    //StringBuilder result = fileReader.editFormat(content);

    assertEquals("#tester\n",
        fileReader.readFromFile(file));

  }

  /**
   * tests the edit and format method
   * specific for when the file has a subtitle
   */
  @Test
  void testEditFormatWithSubTitle() {
    File file =
        new File("src/test/resources/test/tester.md");


    //StringBuilder result = fileReader.editFormat(content);

    assertEquals("## testing sub\n-  this is a test "
            + "of the content split into two lines\n\n",
        fileReader.readFromFile(file));
  }

  /**
   * tests the edit and format method
   * specific for when the file has a subtitle
   */
  @Test
  void testEditFormatWithSubTitle2() {
    File file =
        new File("src/test/resources/WithBRackets/fileBrackets.md");


    //StringBuilder result = fileReader.editFormat(content);

    assertEquals("# testFor\n## Brackets\n",
        fileReader.readFromFile(file));
  }

  /**
   * tests the edit and format method
   * specific for when the file has a content that goes into two
   * lines
   */
  @Test
  void testEditFormatWithOpenContent() {
    File file =
        new File("src"
            + "/test/resources/test/imp.md");


    assertEquals("# testing Double important\n",
        fileReader.readFromFile(file));


  }

  /**
   * tests read from file method
   */
  @Test
  void testEditFormatWithOpenContent2() {
    File file =
        new File("src"
            + "/test/resources/WithBRackets/twoBracks.md");


    assertEquals("## two brackets\n"
            + "- one bracket\n"
            + "- one bracket and this one ins two lines\n\n",
        fileReader.readFromFile(file));


  }

  @Test
  void testEditFormatWithOpenContent3() {
    File file =
        new File("src"
            + "/test/resources/WithBRackets/toPushThroughAll.md");


    assertEquals("## Create file\n"
            + "- content in here  and pushed here \n"
            + "and even furter here\n\n",
        fileReader.readFromFile(file));


  }

  /**
   * tests read from file method
   */
  @Test
  void readFromFile() {

    File testFile = createTestFile("test-file.txt", "This is a test file content.");

    // Create an instance of the FileReader class
    FileReader fileReader = new FileReader();

    // Read the file contents
    String content = fileReader.readFromFile(testFile);

    // Perform assertions on the content
    assertEquals("\n", content);

    // Delete the test file
    deleteTestFile(testFile);


  }

  /**
   * tests the write to file method
   */
  @Test
  void writeToFile() {
    File testFile = createTestFile("test-file.txt", "");

    // Create an instance of the FileReader class
    FileReader fileReader = new FileReader();

    // Write contents to the file
    String contents = "This is a test file content.";
    fileReader.writeToFile(testFile, contents);

    // Read the file contents
    String content = fileReader.readFromFile(testFile);

    // Perform assertions on the content
    assertEquals("", content);

    // Delete the test file
    deleteTestFile(testFile);

    File testFile2 = createTestFile("test-file.txt",
        "##Testing\n[[This is a test file content.]]");

    String contents2 = "##Testing\nThis is a test file content.";
    fileReader.writeToFile(testFile2, contents2);


    String content2 = fileReader.readFromFile(testFile2);

    assertEquals("##Testing\n- This is a test file content.\n\n", content2);


    deleteTestFile(testFile2);
    FileReader fileReader2 = new FileReader();
    boolean title = false;
    File testFile3 = createTestFile("test-file.txt",
        "##Testing");
    String contents3 = "##Testing";
    fileReader.writeToFile(testFile3, contents3);
    contents3 = fileReader2.readFromFile(testFile3);
    if (contents3.contains("##")) {
      title = true;
    }
    assertEquals("##Testing\n", contents3);
    deleteTestFile(testFile3);

    contents3 = "##Testing\n[[This is a test file content.]]";

    assertThrows(RuntimeException.class, () -> fileReader.readFromFile(testFile3));

    assertThrows(RuntimeException.class, () ->
        fileReader.writeToFile(new File("path/title.md"), "Hello"));


  }

  @Test
  void readWholeFile() {
    File testFile = new File("src/test/resources/GivenExamples/javaWithQuestion.md");

    FileReader fileReader = new FileReader();

    // Read the file contents
    String content = fileReader.readFromFile(testFile);

    // Perform assertions on the content
    assertEquals("# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
        + "\n" + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "- no array has actually been created yet\n"
        + "\n" + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not  here\n"
        + "modifiable once instantiated.\n\n", content);

  }

  /*
  @Test
  void contentCleanerTest() {
    String content = "- [[US Capital ::: WashingtonDc]]\n"
        + "- [[Texas Capital ::: Austin]]\n"
        + "- [[What is Texas NickName\n"
        + ":::\n" + "Lone Star State]]\n"
        + "- [[Who lives in a pineapple under the sea :::\n"
        + "SpongeBob SquarePants]]\n"
        + "- [[What is 4 + 4\n"
        + "::: 8]]- [[US Capital ::: WashingtonDc]]\n"
        + "- [[Texas Capital ::: Austin]]\n"
        + "- [[What is Texas NickName\n"
        + ":::\n" + "Lone Star State]]\n"
        + "- [[Who lives in a pineapple under the sea :::\n"
        + "SpongeBob SquarePants]]\n"
        + "- [[What is 4 + 4\n" + "::: 8]]";
    FileReader fileReader = new FileReader();


  }
*/

  /**
   * Creates a test file with the given name and content.
   *
   * @param fileName the name of the test file
   * @param content  the content of the test file
   * @return the test file
   */
  private File createTestFile(String fileName, String content) {
    try {
      File testFile = File.createTempFile(fileName, null);
      FileWriter fileWriter = new FileWriter(testFile);
      fileWriter.write(content);
      fileWriter.close();
      return testFile;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the test file.
   *
   * @param testFile the test file to be deleted
   */
  private void deleteTestFile(File testFile) {
    testFile.delete();
  }


}