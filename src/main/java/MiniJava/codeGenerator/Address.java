package MiniJava.codeGenerator;

/**
 * Created by mohammad hosein on 6/28/2015.
 */

public class Address {
    private int num;
    private TypeAddress Type;
    private varType varType;

    public Address(int num, varType varType, TypeAddress Type) {
        this.num = num;
        this.Type = Type;
        this.varType = varType;
    }

    public Address(int num, varType varType) {
        this.num = num;
        this.Type = TypeAddress.Direct;
        this.varType = varType;
    }

    // Refactor:Self Encapsulate Field pattern
    public int getNum() {
        return num;
    }

    public TypeAddress getType() {
        return Type;
    }

    public varType getVarType() {
        return varType;
    }

    public String toString() {
        switch (getType()) {
            case Direct:
                return getNum() + "";
            case Indirect:
                return "@" + getNum();
            case Imidiate:
                return "#" + getNum();
        }
        return getNum() + "";
    }
}
