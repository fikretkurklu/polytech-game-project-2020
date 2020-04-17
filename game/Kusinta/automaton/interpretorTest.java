package automaton;

import info3.game.automata.ast.AST;
import info3.game.automata.ast.AstPrinter;
import info3.game.automata.parser.AutomataParser;

public class interpretorTest {

	public static void main(String[] args) {
		AST ast;
		try {
			ast = (AST) AutomataParser.from_file("/home/wbstrrr/projet_poo/groupe1/Automaton/Automate/src/automaton/examples/Block.gal");
			AstPrinter printer = new AstPrinter();
			ast.accept(printer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}
}
