package MiniJava.parser;

import MiniJava.Log.Log;
import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.scanner.lexicalAnalyzer;
import MiniJava.scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;


public abstract class ActionHandler {
    public abstract ParseResult execute(
        Stack<Integer> parsStack,
        ParseTable parseTable,
        ArrayList<Rule> rules,
        CodeGenerator cg,
        lexicalAnalyzer lexicalAnalyzer,
        Action currentAction,
        Token lookAhead
    );
    
    public static class ParseResult {
        public final Token lookAhead;
        public final boolean finish;
        
        public ParseResult(Token lookAhead, boolean finish) {
            this.lookAhead = lookAhead;
            this.finish = finish;
        }
    }
} 