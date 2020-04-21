package automaton;

import java.util.List;


import automata.ast.AST;
import automata.parser.AutomataParser;
//import game.Block;

public class interpretorTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AST ast;
		@SuppressWarnings("unused")
		List<automaton.Automaton> bots;
		try {
			ast = (AST) AutomataParser.from_file("/home/wbstrrr/projet_poo.git/groupe1/game/Kusinta/automaton/examples/autoGame.gal");
			Interpretor interpret = new Interpretor();
			bots = (List<Automaton>) ast.accept(interpret);
			//Block entity = new Block(test);
			//test.step(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}
}
