import java.text.SimpleDateFormat;
import java.util.Random;


public class ScoreTimer
{
	private long creationTime;
	private int currentSecond;
	private final int gameInterval = 60000;
	private boolean isAfterMidnight;
	private long randomMidnightStart;
	private boolean isTimeUp;
	private long score;
	
	public ScoreTimer()
	{
		creationTime = System.currentTimeMillis();
		randomMidnightStart = new Random().nextInt(4)+1 * 10000;
	}

	public boolean isAfterMidnight()
	{
		if(System.currentTimeMillis() >= creationTime + randomMidnightStart && 
				System.currentTimeMillis() <= creationTime + randomMidnightStart + 10000)
		{
			return true;
		}
		
		return false;
	}

	public boolean isTimeUp()
	{
		if(System.currentTimeMillis() > creationTime + gameInterval)
		{
			return true;
		}
		return false;
	}

	public long getScore()
	{
		return score;
	}

	public void setScore(long score)
	{
		this.score = score;
	}

	public long getCurrentSecond()
	{
		SimpleDateFormat format = new SimpleDateFormat("s");
		return 60 - Integer.parseInt(format.format(System.currentTimeMillis() - creationTime));
	}
	
}
