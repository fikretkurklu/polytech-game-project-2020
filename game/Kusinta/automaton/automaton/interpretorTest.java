package automaton;

import java.util.List;


import automata.ast.AST;
import automata.parser.AutomataParser;
//import game.Block;

public class interpretorTest {

	public static void main(String[] args) {
		AST ast;
		List<automaton.Automaton> bots;
		try {
			ast = (AST) AutomataParser.from_file("/home/yael/Documents/groupe1/game/Kusinta/automaton/examples/Block.gal");
			Interpretor interpret = new Interpretor();
			bots = (List<Automaton>) ast.accept(interpret);
			Automaton test = bots.get(0);
			//Block entity = new Block(test);
			//test.step(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}
}
