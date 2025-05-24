package MiniJava.codeGenerator;

import java.util.Stack;

// Refactor: Extract Class
public class ControlFlowCodeGenerator {
    private Memory memory;
    private Stack<Address> ss;

    public ControlFlowCodeGenerator(Memory memory, Stack<Address> ss) {
        this.memory = memory;
        this.ss = ss;
    }

    public void label() {
        ss.push(new Address(memory.getCurrentCodeBlockAddress(), varType.Address));
    }

    public void save() {
        ss.push(new Address(memory.saveMemory(), varType.Address));
    }

    public void _while() {
        memory.add3AddressCode(ss.pop().getNum(), Operation.JPF, ss.pop(),
                new Address(memory.getCurrentCodeBlockAddress() + 1, varType.Address), null);
        memory.add3AddressCode(Operation.JP, ss.pop(), null, null);
    }

    public void jpf_save() {
        Address save = new Address(memory.saveMemory(), varType.Address);
        memory.add3AddressCode(ss.pop().getNum(), Operation.JPF, ss.pop(),
                new Address(memory.getCurrentCodeBlockAddress(), varType.Address), null);
        ss.push(save);
    }

    public void jpHere() {
        memory.add3AddressCode(ss.pop().getNum(), Operation.JP,
                new Address(memory.getCurrentCodeBlockAddress(), varType.Address), null, null);
    }
} 