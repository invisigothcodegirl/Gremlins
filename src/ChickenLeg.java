import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;


public class ChickenLeg
{
	private Image imgLeg;
	private int x; 
	private int y;
	
	public ChickenLeg(int x, int y)
	{
		imgLeg = new ImageIcon("images//leg.png").getImage();
		this.x = x; 
		this.y = y;
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
	public Image displayImage()
	{
		return imgLeg;
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
