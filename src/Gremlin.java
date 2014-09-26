import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.ImageIcon;


public class Gremlin
{
	private final int GESTATION_CYCLES = 2500;
	private boolean isEvil;
	private Image imgEvil;
	private Image imgGood;
	private Image imgFurBall;
	private int numWingsEaten;
	private int speed;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private long creationTime;
	private static int numberOfGremlins;
	
	public Gremlin()
	{
		
	}
	public Gremlin(int x, int y)
	{
		this.x = x;
		this.y = y;
		int rGood = new Random().nextInt(3)+1; //Makes number between 1 and 3
		int rEvil = new Random().nextInt(3)+1; 
		numberOfGremlins++;
		imgGood = new ImageIcon("images//nice" + rGood + ".png").getImage();
		imgEvil = new ImageIcon("images//naughty" + rEvil + ".png").getImage();
		imgFurBall = new ImageIcon("images//furball.png").getImage();
		creationTime = System.currentTimeMillis(); //Time stamps the Gremlin
		speed = new Random().nextInt(10)+5;
	}
	
	public boolean isEvil()
	{
		return isEvil;
	}
	public void setEvil(boolean isEvil)
	{
		this.isEvil = isEvil;
	}
	public Image getDisplayImage()
	{
		if(System.currentTimeMillis() - creationTime <= GESTATION_CYCLES)
		{
			return imgFurBall;
		}
		if(isEvil)
		{
			return imgEvil;
		}
		else
		{
			return imgGood;
		}
	}
	public int getNumWingsEaten()
	{
		return numWingsEaten;
	}
	public void setNumWingsEaten(int numWingsEaten)
	{
		this.numWingsEaten = numWingsEaten;
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	
	public boolean contains(Point p)
	{
		return new Rectangle2D.Double(this.x, this.y,
				getDisplayImage().getWidth(null),getDisplayImage().getHeight(null)).contains(p);
		
	}
	
	public boolean isTribble()
	{
		if(System.currentTimeMillis() - creationTime <= GESTATION_CYCLES)
		{
			return true;
		}
		return false;
	}
	
	public void move(Point toward)
	{
		if(!isTribble())
		{
			if(x < toward.x && 
					(y >= 800 - getDisplayImage().getHeight(null) - 50 || x < 1200 - getDisplayImage().getWidth(null) - 110))
			{
				x+=speed;
			}
			else
			{
				x-=speed;
			}
			if(y < toward.y)
			{
				y+=speed;
			}
			else
			{
				y-=speed;
			
			}
		}
	}
	public static int getNumberOfGremlins()
	{
		return numberOfGremlins;
	}

}
