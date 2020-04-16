package object.gui.widgets;

import object.gui.Component;
import object.gui.Container;
import object.gui.Dimension;
import object.gui.MouseListener;
import object.gui.window.Font;
import object.gui.window.Graphics;
import object.gui.window.Image;
import object.gui.window.Window;

public class Button extends Component {
	public static final int DEPRESSED = 0;
	public static final int RAISED = 1;
	private int m_border;
	private String m_label;
	private ButtonListener m_bl;
	private Font m_font;
	private int m_state = RAISED;
	Image i_depressed, i_raised;

	public Button(Container parent) {
		super(parent);
		Window win = Window.getWindow();
		m_font = win.font(Window.MONOSPACED, 12F);
		m_label = "";
		Listener l = new Listener(this);
		setMouseListener(l);
	}

	private void computePreferredSize() {
		Dimension d_txt = new Dimension(m_font.charsWidth(m_label.toCharArray(), 0, m_label.length()), m_font.getHeight());
		if (i_depressed != null && i_raised != null) {
			Dimension d_img ;
			switch (m_state) {
			case DEPRESSED:
				d_img = new Dimension(i_depressed.getWidth(), i_depressed.getHeight());
				break;
			case RAISED:
				d_img = new Dimension(i_raised.getWidth(), i_raised.getHeight());
				break;
			default:
				d_img = new Dimension(i_depressed.getWidth(), i_depressed.getHeight());
				break;
			}
			d_img.max(d_txt);
			d_img.add(2 * m_border, 2 * m_border);
			setPreferredSize(d_img);
		} else {
			d_txt.add(2 * m_border, 2 * m_border);
			setPreferredSize(d_txt);
		}
		
	}

	public void setBorder(int border) {
		this.m_border = border;
		computePreferredSize();
	}
	
	public int getBorder() {
		return m_border;
	}

	public String getLabel() {
		return m_label;
	}

	public void setListener(ButtonListener l) {
		m_bl = l;
	}

	public ButtonListener getButtonListener() {
		return m_bl;
	}
	
	public void setFont(Font f) {
		this.m_font = f;
		computePreferredSize();
	}

	public void setLabel(String txt) {
		m_label = txt;
		computePreferredSize();
	}

	public void setImages(Image released, Image pressed) {
		i_raised = released;
		i_depressed = pressed;
		computePreferredSize();
	}

	@Override
	public void paint(Graphics g) {
		int x, y, h, a, d;
		super.paint(g);
		switch (m_state) {
		case RAISED:
			Util.paintRaisedComponentShadow(g, this);
			break;
		case DEPRESSED:
			Util.paintDepressedComponentShadow(g, this);
			break;
		default:
			Util.paintRaisedComponentShadow(g, this);
			break;
		}
		if (i_depressed != null && i_raised != null) {
			
			switch (m_state) {
			case RAISED:
				g.drawImage(i_raised, m_border, m_border, i_raised.getWidth(), i_raised.getHeight());
				break;
			case DEPRESSED:
				g.drawImage(i_depressed, m_border, m_border, i_raised.getWidth(), i_raised.getHeight());
				break;
			default:
				g.drawImage(i_raised, m_border, m_border, i_raised.getWidth(), i_raised.getHeight());
				break;
			}
		}
		if (m_label != "") {
			g.setFont(m_font);
			a = m_font.getAscent();
			d = m_font.getDescent();
			h = m_font.getHeight(); // leading + ascent + descent
			// align the text left
			x = m_border;
			// center the text vertically.
			// compute first the middle of the widget, accounting for the border
			// then shift it down for the font baseline
			y = m_border + (m_height - 2 * m_border) / 2;
			y = y + (h - d) / 2;
			g.setColor(m_fgColor);
			g.drawString(m_label.toCharArray(), 0, m_label.length(), x, y);
		}
	
		return;
	}

	private static class Listener implements MouseListener {
		Button m_button;

		Listener(Button b) {
			m_button = b;
		}

		@Override
		public void mouseMoved(Component c, int x, int y) {
		}
		@Override
		public void mousePressed(Component c, int x, int y, int buttons) {
			m_button.m_state = DEPRESSED;
			m_button.setBackgroundColor(m_button.getBackgroundColor().darker());
			m_button.repaint();
			if (m_button.getButtonListener() != null) {
				m_button.getButtonListener().pressed(m_button);
			}
		}

		@Override
		public void mouseReleased(Component c, int x, int y, int buttons) {
			m_button.m_state = RAISED;
			m_button.setBackgroundColor(m_button.getBackgroundColor().brighter());
			m_button.repaint();
			if (m_button.getButtonListener() != null) {
				m_button.getButtonListener().released(m_button);
				m_button.getButtonListener().clicked(m_button);
			}
		}

		@Override
		public void mouseEntered(Component c, int x, int y) {
			
		}

		@Override
		public void mouseExited(Component c) {
			m_button.m_state =  RAISED;			
		}
	}

}
