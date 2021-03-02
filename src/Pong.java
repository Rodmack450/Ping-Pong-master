import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Pong implements ActionListener, KeyListener, MouseMotionListener
{
	public static Pong pong;
	public int width = 700, height = 700;
	public Render renderer;
	public Paddle player1;
	public Paddle player2;
	public Ball ball;
	public boolean w, s, up, down;
	public int gameStatus = 0, scoreLimit = 5, playerWon;
	public Random random;
	public JFrame jframe;
	
	Thread thread = new Thread();

	//Start the Window.
	public Pong()
	{
		Timer timer = new Timer(20, this);
		random = new Random();
        jframe = new JFrame("Pong");

		renderer = new Render();

		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);
		timer.start();
	}
	
	    //Some Dialogue boxes.
		Object[] options = {"Keyboard", "Mouse"};
		String p1Name = JOptionPane.showInputDialog("Player 1 Please enter your name:");
		String p2Name = JOptionPane.showInputDialog("Player 2 Please enter your name:");
		
		int controls1 = JOptionPane.showOptionDialog(null, "Thank you " +
		    p1Name + "!\nWould you like to use the keyboard or mouse?",
		    "Select controls", JOptionPane.DEFAULT_OPTION,
		    JOptionPane.PLAIN_MESSAGE, new
		    ImageIcon("images\\controls.png"), options, options[0]);
		
		
		//Start the Game.
	public void start(){

		gameStatus = 2;
		player1 = new Paddle(this, 1);
		player2 = new Paddle(this, 2);
		ball = new Ball(this);
	
	}

	//Update through the game (movements, score etc...) AKA keep the game alive :D 
	public void update(){
		
		if (player1.score >= scoreLimit){
		
			playerWon = 1;
			gameStatus = 3;
		}

		if (player2.score >= scoreLimit){
		
			gameStatus = 3;
			playerWon = 2;
		}
		
		if (w){
		
			player1.move(true);
		}
		if (s){
		
			player1.move(false);
		}
	
		if(up) {
			
			
			player2.move(true);
			
		}
		
		if(down) {
			
			player2.move(true);
		}
		
		ball.update(player1, player2);
	}

	//Some Drawing, and graphics.
	public void render(Graphics2D g) throws InterruptedException{
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (gameStatus == 0){
		
			g.setColor(Color.GRAY);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			
				g.setFont(new Font("Arial", 1, 30));

				g.drawString("Created By Georgio Haddad", width / 2 - 150, height / 2 - 25);
				g.drawString("            Press Space to Play", width / 2 - 200, height / 2 + 25);
				g.drawString("          Score Limit: " + scoreLimit , width / 2 - 150, height / 2 + 75);
				g.drawString("          To Pause The Game Press Space. " , width / 2 - 250, height / 2 + 120);
		
			
		}
	
	
		if (gameStatus == 1){
	
			g.setColor(Color.BLUE);
			
			g.setFont(new Font("Bold", 1, 50));
			g.drawString("PAUSE", width / 2 - 80, height / 2 - 60);
		}
		
		if (gameStatus == 1 || gameStatus == 2){	

			
			
			g.setColor(Color.GRAY);
     
			g.setStroke(new BasicStroke(5f));

			g.drawLine(width / 2, 0, width / 2, height);
			
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG" , 280, 350);
			
			g.setStroke(new BasicStroke(2f));

			g.setFont(new Font("Arial", 1, 30));

			g.drawString(String.valueOf(player1.score), width / 2 - 90, 50);
			
			g.drawString(String.valueOf(player2.score), width / 2 + 65, 50);
		
			g.setColor(Color.RED);
			
			g.drawString(p1Name ,width / 4 + 280, 670);
			
			g.drawString(p2Name ,width / 4 - 100, 670);
			
			player1.render(g);
			player2.render(g);
			ball.render(g);
		
		}

		if (gameStatus == 3){

			g.setColor(Color.RED);
			g.setFont(new Font("Arial", 1, 50));
			
			g.drawString("PONG", width / 2 - 75, 50);
            g.setFont(new Font("Arial", 1, 30));
            
            if(player1.score > player2.score) {
            	
            	g.drawString(p1Name + " WIN!!!!", width / 2 - 120, height / 2 - 75);
            	
            }
            
            else {
            	
            	g.drawString(p2Name + " WIN!!!!", width / 2 - 120, height / 2 - 75);
            }

            g.setColor(Color.blue);
			g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
			g.drawString("Press ESC for Menu", width / 2 - 140, height / 2 + 25);
		
			
			}
	
	}

	@Override
	public void actionPerformed(ActionEvent e){
		
		if (gameStatus == 2){
			
			update();
		}

		renderer.repaint();
	}

	//Main
	public static void main(String[] args){
	
		pong = new Pong();
	}

	//Keyboard Functions.
	@Override
	public void keyPressed(KeyEvent e){
		
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W){
			
			w = true;
		}
		
		else if (id == KeyEvent.VK_S){
			
			s = true;
		}
	
		else if (id == KeyEvent.VK_RIGHT){
		
			 if (gameStatus == 0){
				
				 scoreLimit++;
			}
		}
		else if (id == KeyEvent.VK_LEFT){
	
			if (gameStatus == 0 && scoreLimit > 1){
				scoreLimit--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3)){
		
			gameStatus = 0;
		}
	
		else if (id == KeyEvent.VK_SPACE)
		{
			if (gameStatus == 0 || gameStatus == 3){
			
				start();
			}
			else if (gameStatus == 1){
		
				gameStatus = 2;
			}
			else if (gameStatus == 2){
			
				gameStatus = 1;
			}
		}
		
		
	}
	
	//Keyboard Functions.
	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W){
			
			w = false;
		}
		
		else if (id == KeyEvent.VK_S){
			s = false;
		}
			
	}

	
	@Override
	public void keyTyped(KeyEvent e){

	}

	@Override
	 public void mouseDragged(MouseEvent e){
	
	 }
	 
	@Override
	  public void mouseMoved(MouseEvent e) {
		
		up = true;
		down = true;
		
		
}

}