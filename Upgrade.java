
public class Upgrade
{
	private String rarity;
	private String effect;
	private String stat;
	private boolean mult;
	private double num;
	private int level;
	public Upgrade(String rarity, String effect, String stat, boolean mult, double num, int level)
	{//Creates an Upgrade
		this.rarity=rarity;
		this.effect=effect;
		this.stat=stat;
		this.mult=mult;
		this.num=num;
		this.level=level;
	}
	public Upgrade(Upgrade other)
	{//Create an Upgrade that is a copy of another
		this.rarity=other.rarity;
		this.effect=other.effect;
		this.stat=other.stat;
		this.mult=other.mult;
		this.num=other.num;
		this.level=other.level;
	}
	public String getRarity()
	{
		return rarity;
	}
	public void setRarity(String rarity) 
	{
		this.rarity = rarity;
	}
	public String getEffect() 
	{
		return this.effect;
	}
	public String getStat() 
	{
		return stat;
	}
	public void setStat(String stat) 
	{
		this.stat = stat;
	}
	public boolean isMult()
	{
		return mult;
	}
	public void setMult(boolean mult)
	{
		this.mult = mult;
	}
	public double getNum() 
	{
		return num;
	}
	public void setNum(double num)
	{
		this.num = num;
	}
	public int getLevel() 
	{
		return level;
	}
	public void setLevel(int level) 
	{
		this.level = level;
	}
	public void desc()
	{//Prints a description of the Upgrade
		System.out.println(this.rarity);
		System.out.println(this.effect);
		System.out.println();
	}
}
