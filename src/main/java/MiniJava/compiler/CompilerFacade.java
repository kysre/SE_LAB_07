package MiniJava.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import MiniJava.errorHandler.ErrorHandler;
import MiniJava.parser.Parser;

public class CompilerFacade {
    public boolean compile(String sourceFilePath) {
        try {
            ErrorHandler.hasError = false;
            Parser parser = new Parser();
            Scanner sourceScanner = new Scanner(new File(sourceFilePath));
            parser.startParse(sourceScanner);
            sourceScanner.close();
            return !ErrorHandler.hasError;
        } catch (FileNotFoundException e) {
            ErrorHandler.printError(e.getMessage());
            return false;
        }
    }
}
