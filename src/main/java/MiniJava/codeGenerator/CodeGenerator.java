package MiniJava.codeGenerator;

import MiniJava.Log.Log;
import MiniJava.errorHandler.ErrorHandler;
import MiniJava.scanner.token.Token;
import MiniJava.semantic.symbol.Symbol;
import MiniJava.semantic.symbol.SymbolTable;
import MiniJava.semantic.symbol.SymbolType;

import java.util.Stack;

/**
 * Created by Alireza on 6/27/2015.
 */
public class CodeGenerator {
    private Memory memory = new Memory();
    private Stack<Address> ss = new Stack<Address>();
    private Stack<String> symbolStack = new Stack<>();
    private Stack<String> callStack = new Stack<>();
    private SymbolTable symbolTable;
    private ExpressionGenerator expressionGenerator;
    private SymbolDefinitionHandler symbolDefinitionHandler;
    private ControlFlowCodeGenerator controlFlowGenerator;

    // Refactor: Replace Magic Number with Symbolic Constant
    private static final int SEMANTIC_FUNC_RETURN = 0;
    private static final int SEMANTIC_FUNC_CHECK_ID = 1;
    private static final int SEMANTIC_FUNC_PID = 2;
    private static final int SEMANTIC_FUNC_FPID = 3;
    private static final int SEMANTIC_FUNC_KPID = 4;
    private static final int SEMANTIC_FUNC_INTPID = 5;
    private static final int SEMANTIC_FUNC_START_CALL = 6;
    private static final int SEMANTIC_FUNC_CALL = 7;
    private static final int SEMANTIC_FUNC_ARG = 8;
    private static final int SEMANTIC_FUNC_ASSIGN = 9;
    private static final int SEMANTIC_FUNC_ADD = 10;
    private static final int SEMANTIC_FUNC_SUB = 11;
    private static final int SEMANTIC_FUNC_MULT = 12;
    private static final int SEMANTIC_FUNC_LABEL = 13;
    private static final int SEMANTIC_FUNC_SAVE = 14;
    private static final int SEMANTIC_FUNC_WHILE = 15;
    private static final int SEMANTIC_FUNC_JPF_SAVE = 16;
    private static final int SEMANTIC_FUNC_JP_HERE = 17;
    private static final int SEMANTIC_FUNC_PRINT = 18;
    private static final int SEMANTIC_FUNC_EQUAL = 19;
    private static final int SEMANTIC_FUNC_LESS_THAN = 20;
    private static final int SEMANTIC_FUNC_AND = 21;
    private static final int SEMANTIC_FUNC_NOT = 22;
    private static final int SEMANTIC_FUNC_DEF_CLASS = 23;
    private static final int SEMANTIC_FUNC_DEF_METHOD = 24;
    private static final int SEMANTIC_FUNC_POP_CLASS = 25;
    private static final int SEMANTIC_FUNC_EXTEND = 26;
    private static final int SEMANTIC_FUNC_DEF_FIELD = 27;
    private static final int SEMANTIC_FUNC_DEF_VAR = 28;
    private static final int SEMANTIC_FUNC_METHOD_RETURN = 29;
    private static final int SEMANTIC_FUNC_DEF_PARAM = 30;
    private static final int SEMANTIC_FUNC_LAST_TYPE_BOOL = 31;
    private static final int SEMANTIC_FUNC_LAST_TYPE_INT = 32;
    private static final int SEMANTIC_FUNC_DEF_MAIN = 33;

    public CodeGenerator() {
        symbolTable = new SymbolTable(memory);
        expressionGenerator = new ExpressionGenerator(memory, ss);
        symbolDefinitionHandler = new SymbolDefinitionHandler(symbolTable, memory, ss, symbolStack);
        controlFlowGenerator = new ControlFlowCodeGenerator(memory, ss);
        // TODO
    }

    public void printMemory() {
        memory.pintCodeBlock();
    }

    public void semanticFunction(int func, Token next) {
        Log.print("codegenerator : " + func);
        switch (func) {
            case SEMANTIC_FUNC_RETURN:
                return;
            case SEMANTIC_FUNC_CHECK_ID:
                checkID();
                break;
            case SEMANTIC_FUNC_PID:
                pid(next);
                break;
            case SEMANTIC_FUNC_FPID:
                fpid();
                break;
            case SEMANTIC_FUNC_KPID:
                kpid(next);
                break;
            case SEMANTIC_FUNC_INTPID:
                intpid(next);
                break;
            case SEMANTIC_FUNC_START_CALL:
                startCall();
                break;
            case SEMANTIC_FUNC_CALL:
                call();
                break;
            case SEMANTIC_FUNC_ARG:
                arg();
                break;
            case SEMANTIC_FUNC_ASSIGN:
                assign();
                break;
            case SEMANTIC_FUNC_ADD:
                expressionGenerator.add();
                break;
            case SEMANTIC_FUNC_SUB:
                expressionGenerator.sub();
                break;
            case SEMANTIC_FUNC_MULT:
                expressionGenerator.mult();
                break;
            case SEMANTIC_FUNC_LABEL:
                controlFlowGenerator.label();
                break;
            case SEMANTIC_FUNC_SAVE:
                controlFlowGenerator.save();
                break;
            case SEMANTIC_FUNC_WHILE:
                controlFlowGenerator._while();
                break;
            case SEMANTIC_FUNC_JPF_SAVE:
                controlFlowGenerator.jpf_save();
                break;
            case SEMANTIC_FUNC_JP_HERE:
                controlFlowGenerator.jpHere();
                break;
            case SEMANTIC_FUNC_PRINT:
                expressionGenerator.print();
                break;
            case SEMANTIC_FUNC_EQUAL:
                expressionGenerator.equal();
                break;
            case SEMANTIC_FUNC_LESS_THAN:
                expressionGenerator.less_than();
                break;
            case SEMANTIC_FUNC_AND:
                expressionGenerator.and();
                break;
            case SEMANTIC_FUNC_NOT:
                expressionGenerator.not();
                break;
            case SEMANTIC_FUNC_DEF_CLASS:
                symbolDefinitionHandler.defClass();
                break;
            case SEMANTIC_FUNC_DEF_METHOD:
                symbolDefinitionHandler.defMethod();
                break;
            case SEMANTIC_FUNC_POP_CLASS:
                symbolDefinitionHandler.popClass();
                break;
            case SEMANTIC_FUNC_EXTEND:
                symbolDefinitionHandler.extend();
                break;
            case SEMANTIC_FUNC_DEF_FIELD:
                symbolDefinitionHandler.defField();
                break;
            case SEMANTIC_FUNC_DEF_VAR:
                symbolDefinitionHandler.defVar();
                break;
            case SEMANTIC_FUNC_METHOD_RETURN:
                methodReturn();
                break;
            case SEMANTIC_FUNC_DEF_PARAM:
                symbolDefinitionHandler.defParam();
                break;
            case SEMANTIC_FUNC_LAST_TYPE_BOOL:
                lastTypeBool();
                break;
            case SEMANTIC_FUNC_LAST_TYPE_INT:
                lastTypeInt();
                break;
            case SEMANTIC_FUNC_DEF_MAIN:
                defMain();
                break;
        }
    }

    private void defMain() {
        // ss.pop();
        memory.add3AddressCode(ss.pop().getNum(), Operation.JP,
                new Address(memory.getCurrentCodeBlockAddress(), varType.Address), null, null);
        String methodName = "main";
        String className = symbolStack.pop();

        symbolTable.addMethod(className, methodName, memory.getCurrentCodeBlockAddress());

        symbolStack.push(className);
        symbolStack.push(methodName);
    }

    // public void spid(Token next){
    // symbolStack.push(next.value);
    // }
    public void checkID() {
        symbolStack.pop();
        if (ss.peek().getVarType() == varType.Non) {
            // TODO : error
        }
    }

    public void pid(Token next) {
        if (symbolStack.size() > 1) {
            String methodName = symbolStack.pop();
            String className = symbolStack.pop();
            try {

                Symbol s = symbolTable.get(className, methodName, next.value);
                varType t = varType.Int;
                switch (s.type) {
                    case Bool:
                        t = varType.Bool;
                        break;
                    case Int:
                        t = varType.Int;
                        break;
                }
                ss.push(new Address(s.address, t));

            } catch (Exception e) {
                ss.push(new Address(0, varType.Non));
            }
            symbolStack.push(className);
            symbolStack.push(methodName);
        } else {
            ss.push(new Address(0, varType.Non));
        }
        symbolStack.push(next.value);
    }

    public void fpid() {
        ss.pop();
        ss.pop();

        Symbol s = symbolTable.get(symbolStack.pop(), symbolStack.pop());
        varType t = varType.Int;
        switch (s.type) {
            case Bool:
                t = varType.Bool;
                break;
            case Int:
                t = varType.Int;
                break;
        }
        ss.push(new Address(s.address, t));

    }

    public void kpid(Token next) {
        ss.push(symbolTable.get(next.value));
    }

    public void intpid(Token next) {
        ss.push(new Address(Integer.parseInt(next.value), varType.Int, TypeAddress.Imidiate));
    }

    public void startCall() {
        // TODO: method ok
        ss.pop();
        ss.pop();
        String methodName = symbolStack.pop();
        String className = symbolStack.pop();
        symbolTable.startCall(className, methodName);
        callStack.push(className);
        callStack.push(methodName);

        // symbolStack.push(methodName);
    }

    public void call() {
        // TODO: method ok
        String methodName = callStack.pop();
        String className = callStack.pop();
        try {
            symbolTable.getNextParam(className, methodName);
            ErrorHandler.printError("The few argument pass for method");
        } catch (IndexOutOfBoundsException e) {
        }
        varType t = varType.Int;
        switch (symbolTable.getMethodReturnType(className, methodName)) {
            case Int:
                t = varType.Int;
                break;
            case Bool:
                t = varType.Bool;
                break;
        }
        Address temp = new Address(memory.getTemp(), t);
        ss.push(temp);
        memory.add3AddressCode(Operation.ASSIGN, new Address(temp.getNum(), varType.Address, TypeAddress.Imidiate),
                new Address(symbolTable.getMethodReturnAddress(className, methodName), varType.Address), null);
        memory.add3AddressCode(Operation.ASSIGN,
                new Address(memory.getCurrentCodeBlockAddress() + 2, varType.Address, TypeAddress.Imidiate),
                new Address(symbolTable.getMethodCallerAddress(className, methodName), varType.Address), null);
        memory.add3AddressCode(Operation.JP,
                new Address(symbolTable.getMethodAddress(className, methodName), varType.Address), null, null);

        // symbolStack.pop();
    }

    public void arg() {
        // TODO: method ok

        String methodName = callStack.pop();
        // String className = symbolStack.pop();
        try {
            Symbol s = symbolTable.getNextParam(callStack.peek(), methodName);
            varType t = varType.Int;
            switch (s.type) {
                case Bool:
                    t = varType.Bool;
                    break;
                case Int:
                    t = varType.Int;
                    break;
            }
            Address param = ss.pop();
            if (param.getVarType() != t) {
                ErrorHandler.printError("The argument type isn't match");
            }
            memory.add3AddressCode(Operation.ASSIGN, param, new Address(s.address, t), null);

            // symbolStack.push(className);

        } catch (IndexOutOfBoundsException e) {
            ErrorHandler.printError("Too many arguments pass for method");
        }
        callStack.push(methodName);

    }

    public void assign() {
        Address s1 = ss.pop();
        Address s2 = ss.pop();
        // try {
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in assign is different ");
        }
        // }catch (NullPointerException d)
        // {
        // d.printStackTrace();
        // }
        memory.add3AddressCode(Operation.ASSIGN, s1, s2, null);
    }

    public void methodReturn() {
        // TODO : call ok

        String methodName = symbolStack.pop();
        Address s = ss.pop();
        SymbolType t = symbolTable.getMethodReturnType(symbolStack.peek(), methodName);
        varType temp = varType.Int;
        switch (t) {
            case Int:
                break;
            case Bool:
                temp = varType.Bool;
        }
        if (s.getVarType() != temp) {
            ErrorHandler.printError("The type of method and return address was not match");
        }
        memory.add3AddressCode(Operation.ASSIGN, s,
                new Address(symbolTable.getMethodReturnAddress(symbolStack.peek(), methodName), varType.Address,
                        TypeAddress.Indirect),
                null);
        memory.add3AddressCode(Operation.JP,
                new Address(symbolTable.getMethodCallerAddress(symbolStack.peek(), methodName), varType.Address), null,
                null);

        // symbolStack.pop();
    }

    public void lastTypeBool() {
        symbolTable.setLastType(SymbolType.Bool);
    }

    public void lastTypeInt() {
        symbolTable.setLastType(SymbolType.Int);
    }

    public void main() {

    }
}
