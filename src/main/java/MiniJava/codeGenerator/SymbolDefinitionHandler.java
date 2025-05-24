package MiniJava.codeGenerator;

import MiniJava.semantic.symbol.SymbolTable;
import java.util.Stack;

// Refactor: Extract Class
public class SymbolDefinitionHandler {
    private SymbolTable symbolTable;
    private Memory memory;
    private Stack<Address> ss;
    private Stack<String> symbolStack;

    public SymbolDefinitionHandler(SymbolTable symbolTable, Memory memory, Stack<Address> ss,
            Stack<String> symbolStack) {
        this.symbolTable = symbolTable;
        this.memory = memory;
        this.ss = ss;
        this.symbolStack = symbolStack;
    }

    public void defClass() {
        ss.pop();
        symbolTable.addClass(symbolStack.peek());
    }

    public void defMethod() {
        ss.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void popClass() {
        symbolStack.pop();
    }

    public void extend() {
        ss.pop();
        symbolTable.setSuperClass(symbolStack.pop(), symbolStack.peek());
    }

    public void defField() {
        ss.pop();
        symbolTable.addField(symbolStack.pop(), symbolStack.peek());
    }

    public void defVar() {
        ss.pop();

        String var = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodLocalVariable(className, methodName, var);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    public void defParam() {
        // TODO : call Ok
        ss.pop();
        String param = symbolStack.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();

        symbolTable.addMethodParameter(className, methodName, param);

        symbolStack.push(className);
        symbolStack.push(methodName);
    }
}
