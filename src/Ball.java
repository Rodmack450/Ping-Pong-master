import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.ImageIcon;

public class Ball
{

	private int xPos;
	int yPos;
	private int width = 23;
	private int height = 23;
    private int motionX, motionY;
    public Color col;
    public ImageIcon imgBall;
    public Random random;
    private Pong pong;
    public int Hits;
    private boolean drawImage;

	//Default Constructor.
    public Ball(Pong pong, int x, int y, int w , int h , int mx , int my, Color c, int hit)
	{
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		motionX = mx;
		motionY = my;
		c = Color.white;
		Hits = hit;
		this.pong = pong;
		this.random = new Random();

		spawn();
	}

  //Default Constructor 2.
	public Ball(Pong pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}
 
    //Drawing the ball 
	public void drawBall(Graphics2D g2) {

	    if (drawImage == false) {
	      g2.setColor(col);
	      g2.fillOval(xPos, yPos, width, height);
	    }
	    else {
	      g2.drawImage(imgBall.getImage(), xPos, yPos, width,
	          height, null);
	    }
	  }
	
	
	//Make the ball alive in the game, while checking collisions and precising it's limites on the paddle hitting.
	public void update(Paddle paddle1, Paddle paddle2)
	{
		int speed = 5;

		this.xPos += motionX * speed;
		this.yPos += motionY * speed;

		if (this.yPos + height - motionY > pong.height || this.yPos + motionY < 0)
		{
			if (this.motionY < 0)
			{
				this.yPos = 0;
				this.motionY = random.nextInt(4);

				if (motionY == 0)
				{
					motionY = 1;
				}
			}
			else
			{
				this.motionY = -random.nextInt(4);
				this.yPos = pong.height - height;

				if (motionY == 0)
				{
					motionY = -1;
				}
			}
		}

		if (checkCollision(paddle1) == 1)
		{
			this.motionX = 1 + (Hits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0)
			{
				motionY = 1;
			}

			Hits++;
		}
		else if (checkCollision(paddle2) == 1)
		{
			this.motionX = -1 - (Hits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0)
			{
				motionY = 1;
			}

			Hits++;
		}

		if (checkCollision(paddle1) == 2)
		{
			paddle2.score++;
			spawn();
		}
		else if (checkCollision(paddle2) == 2)
		{
			paddle1.score++;
			spawn();
		}
	}

	//Ball Spawn using random integer, at the beginning of the game.
	public void spawn()
	{
		this.Hits = 0;
		this.xPos = pong.width / 2 - this.width / 2;
		this.yPos = pong.height / 2 - this.height / 2;

		this.motionY = -2 + random.nextInt(4);

		if (motionY == 0)
		{
			motionY = 1;
		}

		if (random.nextBoolean())
		{
			motionX = 1;
		}
		else
		{
			motionX = -1;
		}
	}

	//Getters & Setters .
	public int getX() {
		return xPos;
	}

	public void setX(int x) {
		this.xPos = x;
	}

	public int getY() {
		return yPos;
	}

	public void setY(int y) {
		this.yPos = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMotionX() {
		return motionX;
	}

	public void setMotionX(int motionX) {
		this.motionX = motionX;
	}

	public int getMotionY() {
		return motionY;
	}

	public void setMotionY(int motionY) {
		this.motionY = motionY;
	}

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}

	public ImageIcon getImgBall() {
		return imgBall;
	}

	public void setImgBall(ImageIcon imgBall) {
		this.imgBall = imgBall;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public Pong getPong() {
		return pong;
	}

	public void setPong(Pong pong) {
		this.pong = pong;
	}

	
	//Check if there is a collision, using the paddle class.
	public int checkCollision(Paddle paddle)
	{
		if (this.xPos < paddle.x + paddle.width && this.xPos + width > paddle.x && this.yPos < paddle.y + paddle.height && this.yPos + height > paddle.y)
		{
			return 1; //bounce
		}
		else if ((paddle.x > xPos && paddle.paddleNumber == 1) || (paddle.x < xPos - width && paddle.paddleNumber == 2))
		{
			return 2; //score
		}

		return 0; //nothing
	}

	//Some Graphics
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillOval(xPos, yPos, width, height);
	}

}
