import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;


public class ExitDoor
{
	private Image doorImage;
	private int x;
	private int y;

	public ExitDoor(int x, int y)
	{
		doorImage = new ImageIcon("images//exitdoor.png").getImage();
		this.x = x;
		this.y = y;
	}
	
	public boolean intersects(Gremlin grem)
	{
		//Draws a rectangle in the entrance of the door
		//image and returns a true or a false to indicate
		//if a gremlin intersects with this area.
		return new Rectangle2D.Double(x+75, y+25, 30, 125).intersects(
				new Rectangle2D.Double(grem.getX(),grem.getY(),
						grem.getDisplayImage().getWidth(null),
						grem.getDisplayImage().getHeight(null)));
	}
	
	public Image getDoorImage()
	{
		return doorImage;
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

}
