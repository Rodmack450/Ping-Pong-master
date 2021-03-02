import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Render extends JPanel
{

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		try {
			Pong.pong.render((Graphics2D) g);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
