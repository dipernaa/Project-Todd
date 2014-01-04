package dipernaa;

import java.awt.GridLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class gameFrame extends JFrame {

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	private Panel panel;
	
	public gameFrame() {
		setTitle("Platformer");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		setLocationRelativeTo(null);
		
		
		panel = new Panel(this);
		add(panel);
		
		setVisible(true);
	}
}
