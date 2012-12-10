/*
Andrew Darwin
Fall 2012
CSC 420: Graphical User Interfaces
SUNY Oswego
*/

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Graphics;

public class TransparentPanel extends JPanel
{
  public TransparentPanel()
  {
    setOpaque(false);
  }
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    //Graphics2D g2 = (Graphics2D)g;
    g.setColor(getBackground());
    Rectangle r = g.getClipBounds();
    g.fillRect(r.x, r.y, r.width, r.height);
  }
}
