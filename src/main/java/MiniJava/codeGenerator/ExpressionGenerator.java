package MiniJava.codeGenerator;

import MiniJava.errorHandler.ErrorHandler;
import java.util.Stack;

// Refactor: Extract Class
public class ExpressionGenerator {
    private Memory memory;
    private Stack<Address> ss;

    public ExpressionGenerator(Memory memory, Stack<Address> ss) {
        this.memory = memory;
        this.ss = ss;
    }

    public void add() {
        Address temp = new Address(memory.getTemp(), varType.Int);
        Address s2 = ss.pop();
        Address s1 = ss.pop();

        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("In add two operands must be integer");
        }
        memory.add3AddressCode(Operation.ADD, s1, s2, temp);
        ss.push(temp);
    }

    public void sub() {
        Address temp = new Address(memory.getTemp(), varType.Int);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("In sub two operands must be integer");
        }
        memory.add3AddressCode(Operation.SUB, s1, s2, temp);
        ss.push(temp);
    }

    public void mult() {
        Address temp = new Address(memory.getTemp(), varType.Int);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("In mult two operands must be integer");
        }
        memory.add3AddressCode(Operation.MULT, s1, s2, temp);
        // memory.saveMemory();
        ss.push(temp);
    }

    public void print() {
        memory.add3AddressCode(Operation.PRINT, ss.pop(), null, null);
    }

    public void equal() {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != s2.getVarType()) {
            ErrorHandler.printError("The type of operands in equal operator is different");
        }
        memory.add3AddressCode(Operation.EQ, s1, s2, temp);
        ss.push(temp);
    }

    public void less_than() {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Int || s2.getVarType() != varType.Int) {
            ErrorHandler.printError("The type of operands in less than operator is different");
        }
        memory.add3AddressCode(Operation.LT, s1, s2, temp);
        ss.push(temp);
    }

    public void and() {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s1.getVarType() != varType.Bool || s2.getVarType() != varType.Bool) {
            ErrorHandler.printError("In and operator the operands must be boolean");
        }
        memory.add3AddressCode(Operation.AND, s1, s2, temp);
        ss.push(temp);
    }

    public void not() {
        Address temp = new Address(memory.getTemp(), varType.Bool);
        Address s2 = ss.pop();
        Address s1 = ss.pop();
        if (s2.getVarType() != varType.Bool) {
            ErrorHandler.printError("In not operator the operand must be boolean");
        }
        memory.add3AddressCode(Operation.NOT, s1, s2, temp);
        ss.push(temp);
    }
}
