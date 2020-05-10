package hud;

import java.awt.Color;
import java.awt.Graphics;

import game.Model;
import game.Model.mode;
import player.Player;

public class HUDBoss {
	int m_x, m_y, m_width, m_height;
	Model m_model;
	Player m_player;
	int x, y;

	long updateElpased;
	long statsElapsed;

	public HUDBoss(int x, int y, int w, int h, Player p, Model model) throws Exception {
		m_model = model;
		m_x = x;
		m_y = y;
		m_width = w;
		m_height = h;
		m_player = p;

		this.x = x + w / 6;
		this.y = y;

	}

	public void paint(Graphics g) {
		if (m_model.actualMode != mode.VILLAGE) {
			g.setColor(Color.LIGHT_GRAY);

			g.drawString("Boss Key : " + m_model.bossKeydroprate + "%", x, y);
			g.drawString("Level Difficulty : " + m_model.difficultyLevel, x, y + m_height / 8);
			g.drawString("Opponents killed : " + m_model.getEnemyCount(), x, y + m_height / 4);
			g.drawString("Opponents left : " + m_model.getOpponents().size(), x, y + (3 * m_height / 8));
		}
	}
}
