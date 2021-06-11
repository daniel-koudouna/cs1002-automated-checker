package cs1002.checker;

import java.util.Arrays;
import java.util.List;

public class Settings {
  public final boolean useColors;  
  public final boolean terseOutput;
  public final boolean showLinks;
  public final boolean failFast;

  private Settings() {
    this.useColors = false;
    this.terseOutput = false;
    this.showLinks = false;
    this.failFast = false;
  }

  private Settings(String[] args) {
    List<String> argv = Arrays.asList(args);
    this.useColors = argv.contains("--color") || argv.contains("--full");
    this.showLinks = argv.contains("--overflow") || argv.contains("--full");
    this.terseOutput = argv.contains("--terse");
    this.failFast = argv.contains("--fail-fast");
  }

  private static Settings instance;

  public static Settings init(String[] args) {
    instance = new Settings(args);
    return instance;
  }

  public static Settings get() {
    if (instance == null) {
      instance = new Settings();
    }
    return instance;
  }

  private String col(String s, String code) {
    if (this.useColors) {
      return "\u001B[" + code + "m" + s + "\u001B[0m";
    } else {
      return s;
    }
  }

  public String red(String s) {
    return col(s, "31");
  }

  public String green(String s) {
    return col(s, "32");
  }

  public String yellow(String s) {
    return col(s, "33");
  }

  public String cyan(String s) {
    return col(s, "36");
  }
}
