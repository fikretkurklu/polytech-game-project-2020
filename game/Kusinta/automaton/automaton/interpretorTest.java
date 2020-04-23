package automaton;

import java.util.List;


import automata.ast.AST;
import automata.parser.AutomataParser;
//import game.Block;

public class interpretorTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		AutomatonLibrary AL = new AutomatonLibrary();
		try {
			Automaton a = AL.getAutomate("Player_donjon");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
