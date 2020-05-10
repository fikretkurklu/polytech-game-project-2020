package village;

import village.Village.ID_ENV;

public class MenuButton extends Button {
	Village.ID_ENV ID_CIBLE;

	public MenuButton(int x, int y, int w, int h, ID_ENV id) {
		super(x, y, w, h);
		ID_CIBLE = id;
	}

	@Override
	public void action() throws Exception {
		Village.setEnv(ID_CIBLE);
		if (ID_CIBLE == ID_ENV.ADVENTURE) {
			this.reduceImg();
			Village.leaveVillage();
		}

	}

}
