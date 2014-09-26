import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class SprayBottle 
{
	private Image imgBottle;
	private Image imgBottleOver;
	private boolean isMouseOver;
	private int x, y;
	
	public SprayBottle(int x, int y)
	{
		imgBottle = new ImageIcon("images//spraybottle.png").getImage();
		imgBottleOver = new ImageIcon("images//spraybottleover.png").getImage();
		this.x = x;
		this.y = y;
		
	}
	
	public boolean contains(Point p)
	{
		return new Rectangle2D.Double(x,y,imgBottle.getWidth(null),imgBottle.getHeight(null)).contains(p);
	}
	
	public Image displayImage()
	{
		if(isMouseOver)
		{
			return imgBottleOver;
		}
		else
		{
			return imgBottle;
		
		}
	}

	public boolean isMouseOver()
	{
		return isMouseOver;
	}

	public void setMouseOver(boolean isMouseOver)
	{
		this.isMouseOver = isMouseOver;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	
	}
	
	
}

	
