package object.gui.widgets;

import object.gui.Component;
import object.gui.Container;
import object.gui.window.Color;
import object.gui.window.Graphics;

public class ProgressBar extends Component {

  public interface ProgressListener {
    void progressUpdate(ProgressBar bar);
  }

  public int m_maximum;  // maximum value for the progress
  public int m_minimum;  // minimum value for the progress
  public boolean m_horizontal = true; // true if the progress bar is horizontal
  public int m_border = 5; // the border around the progress bar in pixels
  public int m_thickness = 50; // the thickness, perpendicular to the growing/shrinking axis
  public Color m_color;

  private ProgressListener m_listener;
  private int m_progress;

  public ProgressBar(Container parent) {
    super(parent);
    m_color = Color.green;
  }

  public ProgressListener getListener() {
    return m_listener;
  }
  
  public void setListener(ProgressListener l) {
    m_listener = l;
  }

  public int getProgress() {
    return m_progress;
  }

  public void setProgress(int value) {
    if (value < this.m_minimum)
      value = this.m_minimum;
    else if (value > this.m_maximum)
      value = this.m_maximum;
    m_progress = value;
    repaint();
    if (m_listener != null)
      m_listener.progressUpdate(this);
  }
  @Override
  public void paint(Graphics g) {
	  super.paint(g);
	  Util.paintRaisedComponentShadow(g, this);
	  g.setColor(m_bgColor.darker());
	  g.fillRect(m_border, m_border, m_width - m_border * 2, m_height - m_border * 2);
	  g.setColor(m_color);
	  if (m_width - m_border * 2 != 0) {
		  int w =  (int) (m_progress / (float)(m_maximum - m_minimum) * (float) (m_width - m_border * 2));
		  g.fillRect(m_border, m_border, w , m_height - m_border * 2);
	  } else {
		  g.fillRect(m_border, m_border, 0 , m_height - m_border * 2);
	  }
  }
}
