package playerActions;
import automaton.Entity;


public class ActionWait extends Action {
		
		public ActionWait(int percentage) {
			super(percentage);
		}
		
		@Override
		public boolean apply(Entity e) {
			return e.waitAction();
		}
		
}
