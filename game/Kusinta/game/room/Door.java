package room;
import automaton.Automaton;
import environnement.Decor;
import game.Coord;

public class Door extends Decor {
	
	public Door(Coord coord, DoorImageManager DImageManager, Room room, Automaton automaton) throws Exception {
		super(false, true, true, coord, room, automaton);
		String path = DImageManager.get("");
		if (path != null) {
			loadImage(path);
		}
	}
	
	@Override
	public boolean activate() {
		m_room.isChanged = true;
		return m_room.isChanged;
	}

	@Override
	public void tick(long elapsed) {
		// TODO Auto-generated method stub
		
	}

}

