package cs1002.checker;

import java.util.List;
import java.util.stream.Collectors;

public class OutputComparison {

  public final String output;
  public final int firstDifference;
  public final String expectedLine;
  public final String actualLine;

  public OutputComparison(String output, int lineDiff, String expected, String actual) {
    this.output = output;
    this.firstDifference = lineDiff;
    this.expectedLine = expected;
    this.actualLine = actual;
  }

  /**
   * Compares and prints the two sets of output side by side
   * @return the first line where the outputs differ, or zero if they are identical.
   */
  public static OutputComparison from(String expected, String actual) {
    StringBuilder lines = new StringBuilder();

    int maxWidth = expected.lines()
        .map(String::length)
        .max(Integer::compare)
        .orElse(0);

    maxWidth = Integer.max(maxWidth, "Expected output".length());

    List<String> c = actual.lines().collect(Collectors.toList());
    List<String> ex = expected.lines().collect(Collectors.toList());

    if (ex.size() > 0 && ex.get(ex.size() -1).equals("")) {
      ex.remove(ex.size() - 1);
    }

    int maxHeight = c.size() > ex.size() ? c.size() : ex.size();

    String formattedOutput = String.format("%-" + (maxWidth + 2) + "s","Expected output");
    String paddedLines = "-".repeat(maxWidth+2);
    lines.append("  |     | " + formattedOutput + " | " + "Actual output\n");
    lines.append("--|-----|-" + paddedLines  + "-|-" + paddedLines + "\n");

    int lineDiff = 0;
    String expectedL = "";
    String actualL = "";

    for (int i = 0; i < maxHeight; i++) {
      String exp = "===End of Output===";
      String ac = "===End of Output===";
      if (i < ex.size()) {
        exp = ex.get(i);
      }
      if (i < c.size()) {
        ac = c.get(i);
      }
      String symbol = " ";
      if (!exp.equals(ac)) {
        symbol = "*";
        if (lineDiff == 0) {
          lineDiff = i + 1;
          expectedL = exp;
          actualL = ac;
        }
      }

      String formattedLine = String.format("%-" + (maxWidth + 2) + "s",exp);
      String formattedNum = String.format("%3d", (i+1));
      String line = symbol + " | " + formattedNum + " | " + formattedLine + " | " + ac;
      lines.append(line + "\n");
    }

    return new OutputComparison(lines.toString(), lineDiff, expectedL, actualL);
  }
  
}
