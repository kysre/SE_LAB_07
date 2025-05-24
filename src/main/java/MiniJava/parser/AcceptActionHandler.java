package MiniJava.parser;

import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.scanner.lexicalAnalyzer;
import MiniJava.scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public class AcceptActionHandler extends ActionHandler {
    @Override
    public ParseResult execute(Stack<Integer> parsStack, ParseTable parseTable, ArrayList<Rule> rules, CodeGenerator cg,
            lexicalAnalyzer lexicalAnalyzer, Action currentAction, Token lookAhead) {
        return new ParseResult(lookAhead, true);
    }
}
