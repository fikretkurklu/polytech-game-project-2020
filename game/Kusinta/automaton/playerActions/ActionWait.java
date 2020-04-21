package playerActions;
import automaton.Entity;


public class ActionWait extends Action {
		
		public ActionWait(int percentage) {
			super(percentage);
		}
		
		@Override
		public boolean apply(Entity e) {
			 try {
				e.wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return true;
		}
		
}
