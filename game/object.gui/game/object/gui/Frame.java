package object.gui;

import object.gui.window.Graphics;

/**
 * This is a frame, the top-level container at the root of the tree of
 * containers and components. It manages the selected component under the
 * current mouse position.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */
public class Frame extends Container {
	@Override
	  public void layout() {
	    m_doLayout = true;
	    repaint();
	  }
	  public void doPaint(Graphics g) {
	    if (m_doLayout)
	      doLayout();
	    super.doPaint(g);
	  }

	@Override
	public void remove() {
		throw new IllegalStateException("Cannot remove the root");
	}

}
