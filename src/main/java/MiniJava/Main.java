package MiniJava;

import MiniJava.compiler.CompilerFacade;

public class Main {
    public static void main(String[] args) {
        // Refactor: Use Facade
        CompilerFacade compiler = new CompilerFacade();
        compiler.compile("src/main/resources/code");
    }
}
