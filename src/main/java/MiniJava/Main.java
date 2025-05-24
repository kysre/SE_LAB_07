package MiniJava;

import MiniJava.compiler.CompilerFacade;

public class Main {
    public static void main(String[] args) {
        // Refactor: Use Facade
        CompilerFacade compiler = new CompilerFacade();
        boolean success = compiler.compile("src/main/resources/code");
        if (success) {
            System.out.println("Compilation completed successfully!");
        } else {
            System.out.println("Compilation failed with errors.");
        }
    }
}
