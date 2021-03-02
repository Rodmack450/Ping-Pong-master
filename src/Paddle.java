import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Paddle 
{

	public int paddleNumber;
	int x;
	int y;
	int width = 20;
	int height = 100;
	private Color col;
    public ImageIcon imgPaddle;

	
	//Getters & Setters.
	public int getPaddleNumber() {
		return paddleNumber;
	}

	public void setPaddleNumber(int paddleNumber) {
		this.paddleNumber = paddleNumber;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public Color getCol() {
		return col;
	}

	public void setCol(Color col) {
		this.col = col;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	int score;


	//Generating the paddles.
	public Paddle(Pong pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1)
		{
			this.x = 0;
		}

		if (paddleNumber == 2)
		{
			this.x = pong.width - width;
		}

		this.y = pong.height / 2 - this.height / 2;
	}

	//Drawing The Paddles.
	public void render(Graphics g)
	{
		g.setColor(Color.GRAY);
		
		g.fillRect(x, y, width, height);
	}

	//Moving the Paddle.
	public void move(boolean up)
	{
		int speed = 15;

		if (up)
		{
			if (y - speed > 0)
			{
				y -= speed;
			}
			else
			{
				y = 0;
			}
		}
		else
		{
			if (y + height + speed < Pong.pong.height)
			{
				y += speed;
			}
			else
			{
				y = Pong.pong.height - height;
			}
		}
	}

}
