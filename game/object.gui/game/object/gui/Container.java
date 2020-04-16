package object.gui;

import object.gui.layout.LayoutManager;
import object.gui.window.Graphics;

/**
 * This is a container within a tree of containers and components. A container
 * is a component that has children components. The children are managed as an
 * ordered set. Children components are painted on top of their parent
 * container.
 * 
 * Each child component is at a location within its parent container. Each child
 * is painted above its parent, in terms of the z-order. In other words, a child
 * is considered to be in front of its parent. Children may overlap within their
 * parent. Children may not fit entirely within their parent. Any surface of a
 * child that is not within its parent's surface is not visible and thus not
 * selectable.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Container extends Component {

	protected Component[] m_children;
	protected int m_childrenCount;
	protected LayoutManager m_layoutMgr;
	boolean m_doLayout;

	Container() {
		m_children = new Component[4];
	}

	public Container(Container parent) {
		super(parent);
		m_children = new Component[4];
	}

	public void layoutManager(LayoutManager mgr) {
		m_layoutMgr = mgr;
		layout();
	}

	public LayoutManager layoutManager() {
		return m_layoutMgr;
	}

	/**
	 * Causes this container to lay out its components.
	 */
	public void layout() {
		if (!m_doLayout) {
			m_doLayout = true;
			m_parent.layout();
		}
	}

	protected void doLayout() {
		if (m_doLayout) {
			m_doLayout = false;
			if (m_layoutMgr != null)
				m_layoutMgr.layout(this);
			for (int i = 0; i < m_childrenCount; i++) {
				Component c = m_children[i];
				if (c instanceof Container)
					((Container) c).doLayout();
			}
		}
	}

	/**
	 * Ask this component for its preferred size when layed up in its parent
	 * container.
	 */
	@Override
	public Dimension getPreferredSize() {
		if (m_prefSize != null)
			return new Dimension(m_prefSize);
		if (m_layoutMgr != null)
			return m_layoutMgr.preferredSize(this);
		return new Dimension(0, 0);
	}

	/**
	 * @return the number of components that are children to this container
	 */
	public int childrenCount() {
		return m_childrenCount;
	}

	/**
	 * @return the component indexed by the given index.
	 */
	public Component childrenAt(int i) {
		return m_children[i];
	}

	/**
	 * Tells a parent that the given child has been removed.
	 * 
	 * @param child
	 */
	protected void removed(Component child) {
		for (int i = 0; i < m_childrenCount; i++) {
			if (m_children[i] == child) {
				for (i++; i < m_childrenCount; i++)
					m_children[i - 1] = m_children[i];
				m_children[i - 1] = null;
				m_childrenCount--;
				break;
			}
		}
		layout();
	}

	/**
	 * Tells a parent that the given child has been added.
	 * 
	 * @param child
	 */
	protected void added(Component child) {
		if (m_children.length == m_childrenCount) {
			Component tmp[] = new Component[2 * m_childrenCount];
			System.arraycopy(m_children, 0, tmp, 0, m_children.length);
			m_children = tmp;
		}
		m_children[m_childrenCount++] = child;
		layout();
	}

	/**
	 * Select the component, on top, at the given location. The location is given in
	 * local coordinates. Reminder: children are above their parent. Nota Bene:
	 * parents clip their children, so only the surface within the parent's surface
	 * is selectable.
	 * 
	 * @param x
	 * @param y
	 * @return this selected component
	 */
	public Component select(int x, int y) {
		Location loc = new Location(x, y);
		toLocal(loc);
		for (int i = 0; i < childrenCount(); i++) {

			if (childrenAt(i).select(loc.x(), loc.y()) != null) {
				return childrenAt(i).select(loc.x(), loc.y());
			}
		}

		return super.select(x, y);
	}

	/**
	 * Paint this container and recurse on children.
	 */
	@Override
	void doPaint(Graphics g) {
		super.doPaint(g);
		for (int i = 0; i < m_childrenCount; i++) {
			m_children[i].doPaint(g);
		}

	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		m_doLayout |= (w != m_width || h != m_height);
		super.setBounds(x, y, w, h);
	}

}
