import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.ImageIcon;


public class WaterDrop
{
	private Image imgDrop;
	private int x; 
	private int y;
	
	public WaterDrop(int x, int y)
	{
		imgDrop = new ImageIcon("images//waterdrop.png").getImage();
		this.x = x; 
		this.y = y;
	}
	
	public Image displayImage()
	{
		return imgDrop;
	}
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	
	}
	
	public boolean intersects(Gremlin g)
	{
		Rectangle2D.Double dropRect = new
				Rectangle2D.Double(x,y,displayImage().getWidth(null),
						displayImage().getHeight(null));
		Rectangle2D.Double gremRect = new
				Rectangle2D.Double(g.getX(),g.getY(),g.getDisplayImage().getWidth(null),
						g.getDisplayImage().getHeight(null));
		return dropRect.intersects(gremRect);
	}
}
