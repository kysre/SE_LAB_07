package MiniJava.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import MiniJava.codeGenerator.CodeGenerator;

public class ParserSubsystemFacade {
    private ParseTable parseTable;
    private ArrayList<Rule> rules;
    private CodeGenerator codeGenerator;

    public void initialize() {
        try {
            initializeParseTable();
            initializeGrammarRules();
            initializeCodeGenerator();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize parser subsystem: " + e.getMessage(), e);
        }
    }

    private void initializeParseTable() throws Exception {
        String tableData = Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0);
        this.parseTable = new ParseTable(tableData);
    }

    private void initializeGrammarRules() throws IOException {
        this.rules = new ArrayList<>();
        for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
            rules.add(new Rule(stringRule));
        }
    }

    private void initializeCodeGenerator() {
        this.codeGenerator = new CodeGenerator();
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public static ParserSubsystemFacade createInitialized() {
        ParserSubsystemFacade facade = new ParserSubsystemFacade();
        facade.initialize();
        return facade;
    }
}
