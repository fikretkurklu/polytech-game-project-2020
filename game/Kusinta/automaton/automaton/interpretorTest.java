package automaton;

import automata.ast.AST;
import automata.ast.AstPrinter;
import automata.parser.AutomataParser;

public class interpretorTest {

	public static void main(String[] args) {
		AST ast;
		try {
			ast = (AST) AutomataParser.from_file("/home/yael/Documents/groupe1/game/Kusinta/automaton/examples/Block.gal");
			Interpretor interpret = new Interpretor();
			ast.accept(interpret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}
}
