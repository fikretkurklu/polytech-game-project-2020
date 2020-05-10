/*
 * Copyright (C) 2020  Pr. Olivier Gruber
 * Educational software for a basic game development
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created on: March, 2020
 *      Author: Pr. Olivier Gruber
 */
package game;

import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.graphics.GameCanvasListener;

public class Controller implements GameCanvasListener {
	public static final int K_Z = 122;
	public static final int K_Q = 113;
	public static final int K_S = 115;
	public static final int K_D = 100;
	public static final int K_A = 97;
	public static final int K_E = 101;
	public static final int K_SPACE = 32;
	public static final int K_V = 118;
	public static final int K_C = 99;
	public static final int K_X = 120;

	Game m_game;

	Controller(Game game) {
		m_game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (m_game.m_model.actualMode == Model.mode.VILLAGE) {
			m_game.m_model.m_village.Clicked();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(m_game.m_model.actualMode) {
		case ROOM:
			m_game.m_model.setPressed((int) ' ', true);
			break;
		case UNDERWORLD:
			m_game.m_model.setPressed((int) 'v', true);
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(m_game.m_model.actualMode) {
		case ROOM:
			m_game.m_model.setPressed((int) ' ', false);
			break;
		case UNDERWORLD:
			m_game.m_model.setPressed((int) 'v', false);
			break;
		default:
			break;
		}
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		m_game.m_model.setMouseCoord(new Coord(e.getX(), e.getY()));
		if (m_game.m_model.actualMode == Model.mode.VILLAGE) {
			m_game.m_model.m_village.mouseMoved(e.getX(), e.getY());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		m_game.m_model.setPressed((int) e.getKeyChar(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {

		m_game.m_model.setPressed((int) e.getKeyChar(), false);

	}

	@Override
	public void tick(long elapsed) {
		m_game.tick(elapsed);
	}

	@Override
	public void paint(Graphics g) {
		m_game.paint(g);
	}

	@Override
	public void windowOpened() {
		//m_game.loadMusic("Village");
		//m_game.m_view.setTimer(6000);
	}

	@Override
	public void exit() {
	}

	boolean m_expired;

	@Override
	public void endOfPlay(String name) {
		if (!m_expired) { // only reload if it was a forced reload by timer
			switch(m_game.m_model.actualMode) {
			case VILLAGE:
				m_game.loadMusic("Village");
				break;
			case ROOM:
				m_game.loadMusic("Donjon");
				break;
			case UNDERWORLD:
				m_game.loadMusic("Underworld");
				break;
			case GAMEOVER:
				m_game.loadMusic("GameOver");
				break;
			default:
				break;
			}
		}
		m_expired = false;
		
	}

	@Override
	public void expired() {
		// will force a change of music, after 6s of play
		m_expired = true;
		/*switch(m_game.m_model.actualMode) {
		case VILLAGE:
			m_game.loadMusic("Village");
			break;
		case ROOM:
			m_game.loadMusic("Donjon");
			break;
		case UNDERWORLD:
			m_game.loadMusic("Underworld");
			break;
		case GAMEOVER:
			m_game.loadMusic("GameOver");
			break;
		default:
			break;
		}*/
	}

}
