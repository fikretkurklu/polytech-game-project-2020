package automaton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import automata.ast.AST;
import automata.parser.AutomataParser;

public class AutomatonLibrary {
	
	public static String PATH = "resources/gal/";
	
	String files[] = {"automata.gal"}; // Nom des fichiers
	LinkedList<String> automatonNames;
	
	
	Interpretor interpretor;
	HashMap<String, Automaton> mapAutomaton;
	
	@SuppressWarnings("unchecked")
	public AutomatonLibrary() { // Charge toute la liste d'automates dans la HashMap
		interpretor = new Interpretor();
		mapAutomaton = new HashMap<String, Automaton>(); 
		automatonNames = new LinkedList<String>();
		AST ast;
		LinkedList<automaton.Automaton> bots;
		ListIterator<Automaton> botIterator;
		Automaton automaton;
		for (int i = 0 ; i < files.length ; i++) {
			try {
				ast = (AST) AutomataParser.from_file(PATH + files[i]);
				bots = (LinkedList<Automaton>) ast.accept(interpretor);
				botIterator = bots.listIterator();
				while (botIterator.hasNext()) {
					automaton = (Automaton) botIterator.next();
					if (! automatonNames.contains(automaton.name)) {
						mapAutomaton.put(automaton.name, automaton);
						automatonNames.add(automaton.name);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public Automaton getAutomaton(String name) throws Exception{
		Automaton automaton = mapAutomaton.get(name);
		if (automaton != null) {
			return automaton;
		}else {
			throw new Exception("Error : automaton not found !");
		}
			
	}

}
