
public class Entity
{
	private String name; //name
	private String type; //type, some kind of a second name
	private String difficulty; //difficulty, only relevant for game mode 3
	private int atk; //attack, affects how much damage will be dealt
	private int def; //defense, reduces how much damage will be dealt to you
	private int tDef; /*true defense, your defense will be set back to it
	 					after using the action "Defend"*/
	private int hp; //current health. when reaches 0 you die
	private int maxHp; //max health, current health cannot get past it
	private int spd; //speed, affects how often you can have your turn
	private int hit; //hit rate, affects how likely you are to land an attack
	private int critRate; /*crit rate, affects how likely you are to land
							a stronger type of attack*/
	private double critDmg; /*crit damage, when landing a strong attack
	 						  affects how much stronger it will be*/
	private int hpSteal; /*health steal, affects the percent of health
	 					   you will heal out of damage dealt*/
	private int ult; /*ult, affects how much stronger your ult
	 				   is than a normal attack*/
	private int charge; //charge, how many charges you need to be able to use ult
	private int currentCharge; //How many charges you currently have
	public Entity()
	{//Creates an empty Entity
	}
	public Entity(String name, String type, String difficulty, int atk, int def, int hp, int spd, int hit, int critRate, double critDmg, int hpSteal, int ult, int charge)
	{//Create an Entity
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
	{//Create an Entity that is a copy of another
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
	{//Buff a number of stats for an Entity
		this.atk*=mult;
		this.def*=mult;
		this.tDef=this.def;
		this.maxHp*=mult;
		this.hp=this.maxHp;
		this.spd*=(1+(mult-1)*0.5);
	}
	public void fullStats2(double mult)
	{//Buff a number of stats for an Entity, without making the speed to high
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
	{//Changes the defense temporarily, it will later be set back to tDef
		this.def = def;
	}
	public void setDefUpgrade(int def)
	{// Changes the defense permanently
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
	{/*This action also makes sure the health doesn't get above the integer limit,
	   which could crush the game*/
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
	{//This action also makes sure the health doesn't pass its current limit
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
	{/*This action also makes sure the hit rate doesn't get past 100,
	   since 100% is already the maximum*/
		this.hit=hit;
		if(this.hit>100)
			this.hit=100;
	}
	public int getCritRate() 
	{
		return critRate;
	}
	public void setCritRate(int critRate)
	{/*This action also makes sure the crit rate doesn't get past 10,
	   since 10 is already the maximum*/
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
	{/*This action also makes sure the health steal doesn't get past 100,
	   since 100% is already the maximum*/
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
	{//Prints a description of the entity, including its name and stats
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
