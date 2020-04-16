package object.gui.widgets;

import object.gui.Component;
import object.gui.Container;
import object.gui.TimerListener;
import object.gui.Toolkit;
import object.gui.window.Color;
import object.gui.window.Graphics;

public class Blinker extends Component {
	public static final int GREEN = 0;
	public static final int ORANGE = 1;
	public static final int RED = 2;

	int m_state;
	boolean clignotte = false;
	Color circle_color;
	public int blink_time;
	private TimerListener tl;

	public Blinker(Container parent) {
		super(parent);
		m_state = GREEN;
		circle_color = Color.green;
		m_bgColor = Color.darkGray;
		tl = new TimerBlink();
	}

	public void setState(int state) {
		if (state == m_state)
			return;
		switch (state) {
		case GREEN:
			circle_color = Color.green;
			break;
		case ORANGE:
			circle_color = Color.orange;
			Toolkit.getToolkit().setTimer(250, tl);
			break;
		case RED:
			circle_color = Color.red;
			Toolkit.getToolkit().setTimer(500, tl);
			break;
		default:
			throw new IllegalArgumentException();
		}
		m_state = state;
	}

	public int getState() {
		return m_state;
	}

	public void clignotte() {
		clignotte = !(clignotte);
		if (clignotte == true) {
			circle_color = circle_color.brighter();
		} else {
			circle_color = circle_color.darker();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(circle_color);
		g.fillOval(m_width / 4, m_height / 4, m_width / 2, m_height / 2);
	}

	class TimerBlink implements TimerListener {
		@Override
		public void expired() {
			clignotte();
			repaint();
			switch (m_state) {
			case ORANGE:
				Toolkit.getToolkit().setTimer(250, this);
				break;
			case RED:
				Toolkit.getToolkit().setTimer(500, this);
				break;
			default:
			}
		}
	}
}
