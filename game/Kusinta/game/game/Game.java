/*
d * Copyright (C) 2020  Pr. Olivier Gruber
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import entityFactory.Factory;
import entityFactory.ImageLoader;
import entityFactory.Factory.Type;
import game.graphics.View;

public class Game {

	public static Game game;
	public static Factory m_factory;

	public static void main(String args[]) throws Exception {
		try {
			game = new Game();
		} catch (Throwable th) {
			th.printStackTrace(System.err);
		}
	}

	JFrame m_frame;
	JLabel m_text;
	View m_view;
	Controller m_controller;
	Model m_model;
	Sound m_music;
	Image gameOverImage;
	public boolean gameOver;

	JList<Type> typeList;
	JList<String> automatonList;
	JList<String> animationList;

	Game() throws Exception {
		m_factory = new Factory();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		m_controller = new Controller(this);
		m_view = new View(m_controller);
		// creating frame
		m_frame = m_view.createFrame(d);

		gameOver = false;
		gameOverImage = ImageLoader.loadImage("resources/GameOver.png");
		setupFrame();

		m_musicName = "";
	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {
		m_frame.setTitle("Game");
		m_frame.setLayout(new BorderLayout());
		// m_frame.add(m_view, BorderLayout.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Entity"), BorderLayout.NORTH);
		typeList = new JList<Type>(Factory.Type.values());
		panel.add(typeList, BorderLayout.CENTER);
		m_frame.add(panel, BorderLayout.WEST);

		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.add(new JLabel("Automaton"), BorderLayout.NORTH);
		automatonList = new JList<String>(Factory.Avatars);
		panel2.add(automatonList, BorderLayout.CENTER);
		m_frame.add(panel2, BorderLayout.EAST);

		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.add(new JLabel("Animation"), BorderLayout.NORTH);
		animationList = new JList<String>(Factory.Avatars);
		panel3.add(animationList, BorderLayout.CENTER);
		m_frame.add(panel3, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		JButton btn = new JButton("OK");
		btn.addActionListener(new MyActionListenerOK(m_factory, m_frame, typeList, automatonList, animationList));
		buttonPanel.add(btn, BorderLayout.WEST);

		btn = new JButton("Valider");
		btn.addActionListener(new MyActionListenerValidate(this));
		buttonPanel.add(btn, BorderLayout.EAST);

		m_frame.add(buttonPanel, BorderLayout.SOUTH);

		// center the window on the screen
		m_frame.setLocationRelativeTo(null);

		m_frame.setUndecorated(true);

		// make the vindow visible
		m_frame.doLayout();
		m_frame.setVisible(true);

	}

	public void setupGame() {
		m_frame.getContentPane().removeAll();
		m_frame.getContentPane().repaint();
		try {
			m_model = new Model(m_view, m_frame.getWidth(), m_frame.getHeight(), m_factory, this);
		} catch (Exception e) {
			System.out.println("Test");
			e.printStackTrace();
		}
		m_frame.setLayout(new BorderLayout());
		m_frame.add(m_view, BorderLayout.CENTER);

		m_text = new JLabel();
		m_text.setText("Tick: 0ms FPS=0");
		m_frame.add(m_text, BorderLayout.NORTH);

		m_frame.setVisible(true);

	}

	/*
	 * ================================================================ All the
	 * methods below are invoked from the GameCanvas listener, once the window is
	 * visible on the screen.
	 * ==============================================================
	 */

	/*
	 * Called from the GameCanvas listener when the frame
	 */
	String m_musicName;

	void loadMusic(String musicName) {
		if (m_musicName!= null && (!m_musicName.equals(musicName) || m_controller.getExpired())) {
			m_view.stop(m_musicName);
			m_musicName = musicName;
			String filename = "resources/" + m_musicName + ".ogg";
			try {
				File file = new File(filename);
				FileInputStream fis = new FileInputStream(file);
				m_view.play(m_musicName, fis, -1);
			} catch (Throwable th) {
				th.printStackTrace(System.err);
				System.exit(-1);
			}
		}
	}

	private long m_textElapsed;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) {
		if (!gameOver) {
			m_model.tick(elapsed);

			// Update every second
			// the text on top of the frame: tick and fps
			m_textElapsed += elapsed;
			if (m_textElapsed > 1000) {
				m_textElapsed = 0;
				float period = m_view.getTickPeriod();
				int fps = m_view.getFPS();

				String txt = "Tick=" + period + "ms";
				while (txt.length() < 15)
					txt += " ";
				txt = txt + fps + " fps   ";
				m_text.setText(txt);
			}
		}
	}

	/*
	 * This request is to paint the Game Canvas, using the given graphics. This is
	 * called from the GameCanvasListener, called from the GameCanvas.
	 */
	void paint(Graphics g) {
		int width = m_view.getWidth();
		int height = m_view.getHeight();
		if (!gameOver) {
			// get the size of the canvas
			// erase background
			g.setColor(Color.gray);
			g.fillRect(0, 0, width, height);
			// paint
			m_model.paint(g, width, height);
		} else {
			g.drawImage(gameOverImage, 0, 0, width, height, null);
		}
	}
}

class MyActionListenerOK implements ActionListener {

	JFrame m_frame;
	Factory m_factory;
	JList<Type> typeList;
	JList<String> automatonList;
	JList<String> animationList;

	public MyActionListenerOK(Factory factory, JFrame frame, JList<Type> typeList, JList<String> automatonList,
			JList<String> animationList) {
		m_factory = factory;
		m_frame = frame;
		this.typeList = typeList;
		this.automatonList = automatonList;
		this.animationList = animationList;

	}

	public void actionPerformed(ActionEvent e) {
		m_factory.changeAnimation(Factory.Type.valueOf(typeList.getSelectedValue().toString()),
				animationList.getSelectedValue().toString());
		try {
			m_factory.changeAutomaton(Factory.Type.valueOf(typeList.getSelectedValue().toString()),
					automatonList.getSelectedValue().toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class MyActionListenerValidate implements ActionListener {

	Game g;

	public MyActionListenerValidate(Game game) {
		g = game;
	}

	public void actionPerformed(ActionEvent e) {
		g.setupGame();
	}
}
