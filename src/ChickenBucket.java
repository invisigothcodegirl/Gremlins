import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class ChickenBucket 
{
	private Image imgBucket;
	private Image imgBucketOver;
	private boolean isMouseOver;
	private int x, y;
	
	public ChickenBucket(int x, int y)
	{
		imgBucket = new ImageIcon("images//bucketofchicken.png").getImage();
		imgBucketOver = new ImageIcon("images//bucketofchickenover.png").getImage();
		this.x = x;
		this.y = y;
		
	}
	
	public boolean contains(Point p)
	{
		return new Rectangle2D.Double(x,y,imgBucket.getWidth(null),imgBucket.getHeight(null)).contains(p);
	}
	
	public Image displayImage()
	{
		if(isMouseOver)
		{
			return imgBucketOver;
		}
		else
		{
			return imgBucket;
		
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

	
