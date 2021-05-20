# CS1002 Automated Checker

This repository contains the automated checker used for CS1002 and beyond. The checker is designed to be used with VS Code, but can also be ran as a standalone application.

## Compilation

`make jar`

The jar file will be built and placed in the `dist` directory along with the JUnit jar. Ensure you also include JUnit in additon to the generated file. Also ensure the `.vscode` directory is present to run it from VS Code.

## Running the example project manually through JUnit

`make test`

In general, to run a JUnit test class:

`java -cp [path to junit:path to testing.jar:path to your own files] org.junit.runner.JUnitCore [Your own tests java file]`

e.g, in the project:

`java -cp temp:lib/junit.jar:dist/testing.jar org.junit.runner.JUnitCore AppTests`