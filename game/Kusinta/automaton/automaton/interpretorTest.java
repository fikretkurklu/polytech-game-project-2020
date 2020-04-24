package automaton;

@SuppressWarnings("unused")
public class interpretorTest {
	public static void main(String[] args) {
		AutomatonLibrary AL = new AutomatonLibrary();
		try {
			Automaton a = AL.getAutomaton("Player_donjon");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
