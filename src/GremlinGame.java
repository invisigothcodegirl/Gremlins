import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GremlinGame
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
              JFrame frame = new JFrame();
              frame.setTitle("Gremlins");               
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
              GremlinPanel gPan= new GremlinPanel();
              frame.add(gPan);
              frame.pack();
             }
        });
	}
}

class GremlinPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener
{

	private Thread animator;
	private List<Gremlin> gremlins;
	private List<ChickenLeg> chickenLegs;
	private List<WaterDrop> waterDrops;
	private ChickenBucket chickenBucket;
	private SprayBottle sprayBottle;
	private ExitDoor exitDoor;
	private ScoreTimer scoreTimer;
	private final int DELAY = 50;
	private boolean gameOn = true;
	private Image afterMidnightBackground;
	private Image normalBackground;
	private boolean alreadyPlayedLaughing;
	
	public GremlinPanel()
	{
		gremlins = Collections.synchronizedList(new ArrayList<Gremlin>());
		chickenLegs = Collections.synchronizedList(new ArrayList<ChickenLeg>());
		waterDrops = Collections.synchronizedList(new ArrayList<WaterDrop>());
		afterMidnightBackground = new ImageIcon("images\\aftermidnightbackground.png").getImage();
		normalBackground = new ImageIcon("images\\daytimeBackground.png").getImage();
		chickenBucket = new ChickenBucket(1090,10);
		sprayBottle = new SprayBottle(1090,136);
		exitDoor = new ExitDoor(1090,632);
		scoreTimer = new ScoreTimer();
		addMouseListener(this);
		addMouseMotionListener(this);
		int rand = new Random().nextInt(11) + 10; 
        setDoubleBuffered(true);
        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.LOW;
        SoundEffect.RULES.play();
        for(int i = 0; i < rand; i++)
        {
        	gremlins.add(new Gremlin(new Random().nextInt(975),new Random().nextInt(775)));
        }
        
	}
	
	@Override
	public void addNotify()
	{
		//The add notify method happens when the 
		//JPanel is created
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		
		
		if(scoreTimer.isAfterMidnight())
		{
			g2d.drawImage(afterMidnightBackground, 
					0,0,1200,800,null);
			if(!alreadyPlayedLaughing)
			{
				alreadyPlayedLaughing = true;
				SoundEffect.LAUGH.play();
			}
			
		}
		else
		{
			g2d.drawImage(normalBackground, 
					0,0,1200,800,null);
		}
		
		if(scoreTimer.isTimeUp() || gremlins.size()==0)
		{
			
			g2d.setColor(Color.red);
			g2d.setFont(getGremlinFont(72f));
			int w = g2d.getFontMetrics().stringWidth("GAME OVER!");
			g2d.drawString("GAME OVER!",(getWidth()-w)/2,(getHeight() - g2d.getFontMetrics().getHeight())/2);
			chickenBucket.setMouseOver(false);
			sprayBottle.setMouseOver(false);
			gameOn = false;
		}
		
		
		g2d.drawImage(exitDoor.getDoorImage(),exitDoor.getX(),exitDoor.getY(), 
	    		exitDoor.getDoorImage().getWidth(null), exitDoor.getDoorImage().getHeight(null),null);
	    
		synchronized(gremlins)
		{
			for(Gremlin grem : gremlins)
		    {
		    	g2d.drawImage(grem.getDisplayImage(), grem.getX(), grem.getY(),
		    			grem.getDisplayImage().getWidth(null),grem.getDisplayImage().getHeight(null),null);
		    	
		    }
		}
		
	    synchronized(chickenLegs)
	    {
		    for(ChickenLeg cL : chickenLegs)
		    {
		    	g2d.drawImage(cL.displayImage(), cL.getX(), cL.getY(), cL.displayImage().getWidth(null), 
		    			cL.displayImage().getHeight(null), null);
		    }
	    }
	    
	    
	    synchronized(waterDrops)
	    {
	    	for(WaterDrop wD : waterDrops)
	    	{
		    	g2d.drawImage(wD.displayImage(),wD.getX(),wD.getY(),
		    			wD.displayImage().getWidth(null),wD.displayImage().getHeight(null),null);
		    }
	    }
	    
	    
	    g2d.drawImage(chickenBucket.displayImage(), chickenBucket.getX(), chickenBucket.getY(), 
	    		chickenBucket.displayImage().getWidth(null),
	    		chickenBucket.displayImage().getHeight(null),null);
	    g2d.drawImage(sprayBottle.displayImage(), sprayBottle.getX(), sprayBottle.getY(), 
	    		sprayBottle.displayImage().getWidth(null), sprayBottle.displayImage().getHeight(null),null);
	    
	    
	    
	    g2d.setColor(Color.red);
	    g2d.setFont(getGremlinFont(44f));
	    g2d.drawString("Time  " + scoreTimer.getCurrentSecond(), 15,getHeight()-10);
	    g2d.drawString("Score  " + scoreTimer.getScore(), 225, getHeight()-10);
	    
	    if(scoreTimer.isAfterMidnight())
	    {
	    	g2d.setColor(Color.red);
			g2d.setFont(getGremlinFont(64f));
			int w = g2d.getFontMetrics().stringWidth("After Midnight!!");
			g2d.drawString("After Midnight!!", (getWidth()-w)/2, g2d.getFontMetrics().getHeight()+10);
	    }
	    
	    Toolkit.getDefaultToolkit().sync();
	    g.dispose();
	}
	
	public void cycle()
	{

		ArrayList<Gremlin> gremlinsToRemove = new ArrayList<Gremlin>();
		
		for(Gremlin grem : gremlins)
		{
			if(exitDoor.intersects(grem))
			{
				gremlinsToRemove.add(grem);
			}
			if(chickenLegs.size()>0)
			{
				double closestDistance = 10000;
				ChickenLeg closestLeg = chickenLegs.get(0);
				synchronized(chickenLegs)
				{
					for(ChickenLeg l : chickenLegs)
					{
						
						double distance = Math.sqrt(Math.pow(l.getX() - grem.getX(),2) + Math.pow(l.getY() - grem.getY(), 2));
						if(distance < closestDistance)
						{
							closestDistance = distance;
							closestLeg = l;
						}
					}
				}
				grem.move(new Point(closestLeg.getX(),closestLeg.getY()));
			}
			else
			{
				grem.move(new Point(getWidth()-30 - grem.getDisplayImage().getWidth(null), 
						getHeight()-10-grem.getDisplayImage().getHeight(null)));
			}
		}
		synchronized(gremlins)
		{
			for(Gremlin grem : gremlinsToRemove)
			{
				if(grem.isEvil())
				{
					scoreTimer.setScore(scoreTimer.getScore() - grem.getNumWingsEaten()*10);
					SoundEffect.UHOH.play();
					if(scoreTimer.getScore()<0)
						scoreTimer.setScore(0);
				}
				else
				{
					scoreTimer.setScore(scoreTimer.getScore() + grem.getNumWingsEaten());
				}
				gremlins.remove(grem);
			}
		}
		
		eatChicken();
		checkDrops();
		
	}
	
	public void eatChicken()
	{
		ArrayList<ChickenLeg> legsToRemove = new ArrayList<ChickenLeg>();
		synchronized(chickenLegs)
		{
			for(ChickenLeg l : chickenLegs)
			{
				for(Gremlin g : gremlins)
				{
					if(l.intersects(g) && !g.isTribble())
					{
						int rand = new Random().nextInt(4)+1;
						legsToRemove.add(l);
						g.setNumWingsEaten(g.getNumWingsEaten()+1);
						if(scoreTimer.isAfterMidnight())
						{
							g.setEvil(true);
						}
						break;
					}
				}
			}
		}
		
		for(ChickenLeg l : legsToRemove)
		{
			chickenLegs.remove(l);
		}
	}
	
	private Font getGremlinFont(float size)
	{
		Font gremlinFont = new Font("Arial",Font.BOLD,(int)size);
		try
		{
			File f = new File("font\\GREMLINS.TTF");
		    FileInputStream in = new FileInputStream(f);
		    Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
		    gremlinFont = dynamicFont.deriveFont(size);
		     
		 }
		 catch(Exception e)
		 {
			System.out.println("Loading Font Didn't Work");
		 }
		 return gremlinFont;
	}
	
	public void checkDrops()
	{
		ArrayList<Gremlin> gremlinsToAdd = new ArrayList<Gremlin>();
		ArrayList<WaterDrop> dropsToRemove = new ArrayList<WaterDrop>();
		synchronized(waterDrops)
		{
			for(WaterDrop d : waterDrops)
			{
				for(Gremlin g : gremlins)
				{
					if(d.intersects(g) && !g.isTribble())
					{
						int rand = new Random().nextInt(4)+1;
						dropsToRemove.add(d);
						for(int k = 0; k < rand; k++)
						{
							int randX = new Random().nextInt(1090-g.getDisplayImage().getWidth(null));
							int randY = new Random().nextInt(800 - g.getDisplayImage().getHeight(null));
							gremlinsToAdd.add(new Gremlin(randX,randY));
							
						}
						break;
					}
				}
			}
		}
		
		//Add the new gremlins
		for(Gremlin g : gremlinsToAdd)
		{
			gremlins.add(g);
		}
		
		//Remove any drops that the gremlins touched
		for(WaterDrop d : dropsToRemove)
		{
			waterDrops.remove(d);
		}
		
	}
	
	@Override
	public void run()
	{
		 long beforeTime, timeDiff, sleep;
		 beforeTime = System.currentTimeMillis();
		 while (gameOn) 
		 {
			 cycle();
			 repaint();
			 
			 timeDiff = System.currentTimeMillis() - beforeTime;
	         sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try 
            {
                Thread.sleep(sleep);
            } 
            catch(InterruptedException e) 
            {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
		 }
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(1200,800);
	}


	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(chickenBucket.contains(e.getPoint()))
		{
			int numLegs = new Random().nextInt(gremlins.size())+1;
			synchronized(chickenLegs)
			{
				for(int i = 0; i<numLegs; i++)
				{
					int randX = new Random().nextInt(1041);
					int randY = new Random().nextInt(770);
					chickenLegs.add(new ChickenLeg(randX,randY));
				}
			}
			
		}
		if(sprayBottle.contains(e.getPoint()))
		{
			int numDrops = new Random().nextInt(16)+10;
			synchronized(waterDrops)
			{
				for(int i = 0; i<numDrops; i++)
				{
					int randX = new Random().nextInt(1065);
					int randY = new Random().nextInt(765);
					waterDrops.add(new WaterDrop(randX,randY));
				
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e)	{}

	@Override
	public void mouseReleased(MouseEvent e)	{}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		//System.out.println(e.getPoint());
		if(chickenBucket.contains(e.getPoint()))
		{
			chickenBucket.setMouseOver(true);
		}
		else
		{
			chickenBucket.setMouseOver(false);
		}
		
		if(sprayBottle.contains(e.getPoint()))
		{
			sprayBottle.setMouseOver(true);
		}
		else
		{
			sprayBottle.setMouseOver(false);
		}
		
	}
	
}