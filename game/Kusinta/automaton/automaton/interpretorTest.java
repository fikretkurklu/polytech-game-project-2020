package automaton;

import automata.ast.AST;
import automata.ast.AstPrinter;
import automata.parser.AutomataParser;

public class interpretorTest {

	public static void main(String[] args) {
		AST ast;
		try {
			ast = (AST) AutomataParser.from_file("/home/yael/Documents/groupe1/game/Kusinta/automaton/examples/Block.gal");
			AstPrinter printer = new AstPrinter();
			ast.accept(printer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}
}
