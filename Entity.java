
public class Entity
{
	private String name;
	private String type;
	private String difficulty;
	private int atk;
	private int def;
	private int tDef;
	private int hp;
	private int maxHp;
	private int spd;
	private int hit;
	private int critRate;
	private double critDmg;
	private int hpSteal;
	private int ult;
	private int charge;
	private int currentCharge;
	public Entity()
	{
	}
	public Entity(String name, String type, String difficulty, int atk, int def, int hp, int spd, int hit, int critRate, double critDmg, int hpSteal, int ult, int charge)
	{
		this.name=name;
		this.type=type;
		this.difficulty=difficulty;
		this.atk=atk;
		this.def=def;
		this.tDef=this.def;
		this.maxHp=hp;
		this.hp=this.maxHp;
		this.spd=spd;
		this.hit=hit;
		if(this.hit>100)
			setHit(100);
		this.critRate=critRate;
		this.critDmg=critDmg;
		this.hpSteal=hpSteal;
		this.ult=ult;
		this.charge=charge;
		if(this.charge<1)
			setCharge(1);
		this.currentCharge=0;
	}
	public Entity(Entity other)
	{
		this.name=other.name;
		this.type=other.type;
		this.difficulty=other.difficulty;
		this.atk=other.atk;
		this.def=other.def;
		this.tDef=this.def;
		this.maxHp=other.hp;
		this.hp=this.maxHp;
		this.spd=other.spd;
		this.hit=other.hit;
		this.critRate=other.critRate;
		this.critDmg=other.critDmg;
		this.hpSteal=other.hpSteal;
		this.ult=other.ult;
		this.charge=other.charge;
		this.currentCharge=other.currentCharge;
	}
	public void fullStats1(double mult)
	{
		this.atk*=mult;
		this.def*=mult;
		this.tDef=this.def;
		this.maxHp*=mult;
		this.hp=this.maxHp;
		this.spd*=(1+(mult-1)*0.5);
	}
	public void fullStats2(double mult)
	{
		this.atk*=mult;
		this.def*=mult;
		this.tDef=this.def;
		this.maxHp*=mult;
		this.hp=this.maxHp;
		this.spd*=(1+(mult-1)*0.1);
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getDifficulty()
	{
		return difficulty;
	}
	public void setDifficulty(String difficulty) 
	{
		this.difficulty = difficulty;
	}
	public int getAtk() 
	{
		return atk;
	}
	public void setAtk(int atk) 
	{
		this.atk = atk;
	}
	public int getDef()
	{
		return def;
	}
	public void setDef(int def) 
	{
		this.def = def;
	}
	public void setDefUpgrade(int def)
	{
		this.def = def;
		this.tDef=this.def;
	}
	public int gettDef() 
	{
		return tDef;
	}
	public void settDef(int tDef) 
	{
		this.tDef = tDef;
	}
	public int getMaxHp() 
	{
		return maxHp;
	}
	public void setMaxHp(int maxHp) 
	{
		if(maxHp>Integer.MAX_VALUE*0.5)
			this.maxHp=(int)(Integer.MAX_VALUE*0.5);
		else
			this.maxHp = maxHp;
	}
	public int getHp()
	{
		return hp;
	}
	public void setHp(int hp) 
	{
		this.hp = hp;
		if(this.hp>this.maxHp)
			this.hp=this.maxHp;
	}
	public int getSpd()
	{
		return spd;
	}
	public void setSpd(int spd) 
	{
		this.spd = spd;
	}
	public int getHit()
	{
		return hit;
	}
	public void setHit(int hit)
	{
		this.hit=hit;
		if(this.hit>100)
			this.hit=100;
	}
	public int getCritRate() 
	{
		return critRate;
	}
	public void setCritRate(int critRate)
	{
		this.critRate = critRate;
		if(this.critRate>10)
			this.critRate=10;
	}
	public double getCritDmg() 
	{
		return critDmg;
	}
	public void setCritDmg(double critDmg)
	{
		this.critDmg = critDmg;
	}
	public int getHpSteal()
	{
		return hpSteal;
	}
	public void setHpSteal(int hpSteal) 
	{
		this.hpSteal = hpSteal;
		if(this.hpSteal>100)
			this.hpSteal=100;
	}
	public int getUlt() 
	{
		return ult;
	}
	public void setUlt(int ult) 
	{
		this.ult = ult;
	}
	public int getCharge() 
	{
		return charge;
	}
	public void setCharge(int charge) 
	{
		this.charge = charge;
	}
	public int getCurrentCharge()
	{
		return currentCharge;
	}
	public void setCurrentCharge(int currentCharge) 
	{
		this.currentCharge = currentCharge;
	}
	public void desc()
	{
		System.out.println(this.name);
		System.out.println(this.type);
		System.out.println("Difficulty: " +this.difficulty);
		System.out.println("Attack: "+this.atk);
		System.out.println("Defense: "+this.def);
		System.out.println("Ult: "+this.ult+" times base attack");
		System.out.println("Charges needed for ult: "+this.charge);
		System.out.println("Current charges: "+this.currentCharge);
		System.out.println("Total health: "+this.maxHp);
		System.out.println("Current health: "+this.hp);
		System.out.println("Speed: "+this.spd);
		System.out.println("Hit rate: "+this.hit);
		System.out.println("Crit rate: "+this.critRate);
		System.out.println("Crit damage: "+this.critDmg);
		System.out.println("Health steal: "+this.hpSteal+"%");
		System.out.println();
	}
}
