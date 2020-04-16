package object.gui;

import java.io.PrintStream;

import object.gui.window.Window;

public class EventQueue extends object.events.EventQueue {

  Window m_win; // provided of the global timer

  public EventQueue(Window win, PrintStream log) {
    super(log);
    m_win = win;
  }

  public void expired() {
    super.timerExpired();
  }

  @Override
  protected void setTimer(long delay) {
    m_win.cancelTimer();
    if (delay >= 0)
    	m_win.setTimer((int)delay);
  }

}
