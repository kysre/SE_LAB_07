package MiniJava.parser;

import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.scanner.lexicalAnalyzer;
import MiniJava.scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public class ShiftActionHandler extends ActionHandler {
    @Override
    public ParseResult execute(Stack<Integer> parsStack, ParseTable parseTable, ArrayList<Rule> rules, CodeGenerator cg,
            lexicalAnalyzer lexicalAnalyzer, Action currentAction, Token lookAhead) {
        parsStack.push(currentAction.number);
        Token newLookAhead = lexicalAnalyzer.getNextToken();
        return new ParseResult(newLookAhead, false);
    }
}
