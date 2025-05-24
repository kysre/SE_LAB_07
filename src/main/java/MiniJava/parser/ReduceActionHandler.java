package MiniJava.parser;

import MiniJava.Log.Log;
import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.scanner.lexicalAnalyzer;
import MiniJava.scanner.token.Token;

import java.util.ArrayList;
import java.util.Stack;

public class ReduceActionHandler extends ActionHandler {
    @Override
    public ParseResult execute(Stack<Integer> parsStack, ParseTable parseTable, ArrayList<Rule> rules, CodeGenerator cg,
            lexicalAnalyzer lexicalAnalyzer, Action currentAction, Token lookAhead) {
        Rule rule = rules.get(currentAction.number);
        for (int i = 0; i < rule.RHS.size(); i++) {
            parsStack.pop();
        }
        Log.print(/* "state : " + */ parsStack.peek() + "\t" + rule.LHS);
        parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.LHS));
        Log.print(/* "new State : " + */parsStack.peek() + "");
        try {
            cg.semanticFunction(rule.semanticAction, lookAhead);
        } catch (Exception e) {
            Log.print("Code Generator Error");
        }
        return new ParseResult(lookAhead, false);
    }
}
