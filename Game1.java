import java.util.Random;
import java.util.Scanner;
public class Game1
{
	static Random r=new Random();
	static Scanner s=new Scanner(System.in);
	public static void turnPlayer(Entity playing, Entity other)
	{//Initiates a player's turn
		if(playing.getCurrentCharge()>playing.getCharge())
			playing.setCurrentCharge(playing.getCharge());
		System.out.println(playing.getName()+"'s turn!");
		System.out.println("Current ult charge: "+playing.getCurrentCharge()+"/"+playing.getCharge());
		System.out.println("Possible actions:");
		System.out.println("1. Attack");
		System.out.println("2. Defend");
		System.out.println("3. Charge ult");
		System.out.println("4. Release ult");
		System.out.println("5. Heal");
		System.out.println("6. Explain an action (doesn't cost a turn)");
		System.out.println("7. Check self");
		System.out.println("Enter the number of the action you'd like to perform");
		char action=s.next().charAt(0);
		boolean validInput=false;
		while(!validInput)
		{
			if(action=='1')
			{
				attack(playing, other);
				validInput=true;
			}
			else if(action=='2')
			{
				defend(playing);
				validInput=true;
			}
			else if(action=='3')
			{
				charge(playing);
				validInput=true;
			}
			else if(action=='4')
			{
				if(playing.getCurrentCharge()==playing.getCharge())
				{
					ult(playing, other);
					validInput=true;
				}
				else
				{
					System.out.println("Need more charge. Enter a different action");
					action=s.next().charAt(0);
				}
			}
			else if(action=='5')
			{
				if(playing.getCurrentCharge()>0)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
					validInput=true;
				}
				else
				{
					System.out.println("Need more charge. Enter a different action");
					action=s.next().charAt(0);
				}
			}
			else if(action=='6')
			{
				System.out.println("Enter the number of the action you want details on");
				System.out.println("Enter 6 to learn about speed");
				System.out.println("Enter 7 to learn about hit rate");
				System.out.println("Enter 8 to learn about crit rate");
				System.out.println("Enter 9 to check enemy ");
				System.out.println("Enter 0 to return to action selection");
				char actionCheck=s.next().charAt(0);
				while(actionCheck!='0')
				{
					if(actionCheck=='1')
					{
						System.out.println("Deal damage to deplete enemy's health!");
						System.out.println("damage depends on your attack and the enemy's defense");
						System.out.println("Charges 1 part of your ult");
						System.out.println("Your current attack: "+playing.getAtk());
					}
					else if(actionCheck=='2')
					{
						System.out.println("Temporarily increase your defense! only applies until your next turn");
						System.out.println("Charges 2 parts of your ult");
						System.out.println("Your current defense: "+playing.getDef());
					}
					else if(actionCheck=='3')
						System.out.println("Charge 3 parts of your ult");
					else if(actionCheck=='4')
					{
						System.out.println("Release your ult, dealing significantly higher damage than a regular attack!");
						System.out.println("Your ult damage: "+playing.getUlt()+" times your base attack");
						System.out.println("Charges required for ult: "+playing.getCharge()+" parts");
					}
					else if(actionCheck=='5')
					{
						System.out.println("Restore health based on your attack!");
						System.out.println("Uses 1 charge");
						System.out.println("Curret attack: "+playing.getAtk());
					}
					else if(actionCheck=='6')
					{
						System.out.println("Speed determines who will have their turn first!");
						System.out.println("It also determines how frequently you can act.");
						System.out.println("Your current speed: "+playing.getSpd());
					}
					else if(actionCheck=='7')
					{
						System.out.println("Your hit rate is how likely you are to manage to land attacks");
						System.out.println("(100 hit rate = guarenteed success)");
						System.out.println("Your current hit rate: "+playing.getHit());
					}
					else if(actionCheck=='8')
					{
						System.out.println("Your crit rate is how likely you are to land strong and critical hits.");
						System.out.println("Your current crit rate: "+playing.getCritRate());
						System.out.println("Your chance to land a strong hit when attacking: "+playing.getCritRate()*9+"%");
						System.out.println("Your chance to land a critical hit when attacking: "+playing.getCritRate()+"%");
					}
					else if(actionCheck=='9')
						other.desc();
					else
						System.out.println("Please enter a valid input");
					actionCheck=s.next().charAt(0);
				}
				System.out.println("Enter the number of the action you'd like to perform");
				action=s.next().charAt(0);
			}
			else if(action=='7')
			{
				playing.desc();
				System.out.println("Enter the number of the action you'd like to perform");
				action=s.next().charAt(0);
			}
			else
			{
				System.out.println("Please enter a valid input");
				action=s.next().charAt(0);
			}
		}
	}
	public static void turnEnemy(Entity playing, Entity other)
	{/*Initiates an Enemy's turn. The enemy will choose a random action 
	   based on its current health and charge*/
		System.out.println(playing.getName()+" turn!");
		if(playing.getCurrentCharge()>playing.getCharge())
			playing.setCurrentCharge(playing.getCharge());
		System.out.println("Enemy's charge: "+playing.getCurrentCharge()+"/"+playing.getCharge());
		int action;
		if(playing.getHp()==playing.getMaxHp())
		{
			if(playing.getCurrentCharge()!=playing.getCharge())
			{
				action=r.nextInt(3)+1;
				if(action==1)
				{
					attack(playing, other);
				}
				else if(action==2)
				{
					defend(playing);
				}
				else if(action==3&&playing.getCurrentCharge()<playing.getCharge()-2)
				{
					charge(playing);
				}
				else
					attack(playing, other);
			}
			else
				ult(playing, other);
		}
		if(playing.getHp()<playing.getMaxHp()&&playing.getHp()>=playing.getMaxHp()*0.7)
		{
			if(playing.getCurrentCharge()!=playing.getCharge())
			{
				action=r.nextInt(4)+1;
				if(action==1)
				{
					attack(playing, other);
					if(playing.getCurrentCharge()<playing.getCharge())
					{
						int currentCharge=playing.getCurrentCharge()+1;
						playing.setCurrentCharge(currentCharge);
					}
				}
				else if(action==2)
				{
					defend(playing);
					if(playing.getCurrentCharge()<playing.getCharge())
					{
						int currentCharge=playing.getCurrentCharge()+2;
						playing.setCurrentCharge(currentCharge);
					}
				}
				else if(action==3&&playing.getCurrentCharge()<playing.getCharge()-2)
				{
					if(playing.getCurrentCharge()<playing.getCharge())
					{
						playing.setDef(playing.gettDef());
						int currentCharge=playing.getCurrentCharge()+3;
						playing.setCurrentCharge(currentCharge);
					}
					System.out.println(playing.getName()+" Charged ult!");
				}
				else if(action==3)
					attack(playing, other);
				else if(action==4&&playing.getCurrentCharge()>1)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
				}
				else if(action==4&&other.getHp()<other.getMaxHp()*0.5)
					attack(playing, other);
				else
					defend(playing);
			}
			else
			{
				action=r.nextInt(4)+1;
				if(action==1)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
				}
				else
					ult(playing, other);
			}
		}
		if(playing.getHp()<playing.getMaxHp()*0.7&&playing.getHp()>=playing.getMaxHp()*0.4)
		{
			if(playing.getCurrentCharge()!=playing.getCharge())
			{
				action=r.nextInt(5)+1;
				if(action==1)
					attack(playing, other);
				else if(action==2)
					defend(playing);
				else if(action==3&&playing.getCurrentCharge()<playing.getCharge()-2)
					charge(playing);
				else if(action==3)
					attack(playing, other);
				else if(action>3&&action<6&&playing.getCurrentCharge()>1)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
				}
				else if(action>3&&action<6&&other.getHp()<other.getMaxHp()*0.5)
					attack(playing, other);
				else
					defend(playing);
			}
			else
			{
				action=r.nextInt(3)+1;
				if(action==1)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
				}
				else
					ult(playing, other);
			}
		}
		if(playing.getHp()<playing.getMaxHp()*0.4)
		{
			if(playing.getCurrentCharge()!=playing.getCharge())
			{
				action=r.nextInt(6)+1;
				if(action==1)
					attack(playing, other);
				else if(action==2)
					defend(playing);
				else if(action==3&&playing.getCurrentCharge()<playing.getCharge()-2)
					charge(playing);
				else if(action==3)
					attack(playing, other);
				else if(action>4&&action<7&&playing.getCurrentCharge()>1)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
				}
				else if(action>3&&action<7&&other.getHp()<other.getMaxHp()*0.5)
					attack(playing, other);
				else
					defend(playing);
			}
			else
			{
				action=r.nextInt(2)+1;
				if(action==1)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
				}
				else
					ult(playing, other);
			}
		}
	}
	public static void turnAdmin(Entity playing, Entity other)
	{//Initiates a player's turn, if cheats are on
		if(playing.getCurrentCharge()>playing.getCharge())
			playing.setCurrentCharge(playing.getCharge());
		System.out.println(playing.getName()+"'s turn!");
		System.out.println("Current ult charge: "+playing.getCurrentCharge()+"/"+playing.getCharge());
		System.out.println("Possible actions:");
		System.out.println("1. Attack");
		System.out.println("2. Defend");
		System.out.println("3. Charge ult");
		System.out.println("4. Release ult");
		System.out.println("5. Heal");
		System.out.println("6. Check enemy");
		System.out.println("7. Check self");
		System.out.println("8. Edit a stat");
		System.out.println("9. Insta-kill enemy");
		System.out.println("Enter the number of the action you'd like to perform");
		char action=s.next().charAt(0);
		boolean validInput=false;
		while(!validInput)
		{
			if(action=='1')
			{
				attack(playing, other);
				validInput=true;
			}
			else if(action=='2')
			{
				defend(playing);
				validInput=true;
			}
			else if(action=='3')
			{
				charge(playing);
				validInput=true;
			}
			else if(action=='4')
			{
				if(playing.getCurrentCharge()==playing.getCharge())
				{
					ult(playing, other);
					validInput=true;
				}
				else
				{
					System.out.println("Need more charge. Enter a different action");
					action=s.next().charAt(0);
				}
			}
			else if(action=='5')
			{
				if(playing.getCurrentCharge()>0)
				{
					heal(playing);
					playing.setCurrentCharge(playing.getCurrentCharge()-1);
					validInput=true;
				}
				else
				{
					System.out.println("Need more charge. Enter a different action");
					action=s.next().charAt(0);
				}
			}
			else if(action=='6')
			{
				other.desc();
				System.out.println("Enter the number of the action you'd like to perform");
				action=s.next().charAt(0);
			}
			else if(action=='7')
			{
				playing.desc();
				System.out.println("Enter the number of the action you'd like to perform");
				action=s.next().charAt(0);
			}
			else if(action=='8')
			{
				System.out.println("Edit a stat for:");
				System.out.println("1. Self");
				System.out.println("2. Enemy");
				char statChange=s.next().charAt(0);
				while(statChange!='1'&&statChange!='2')
				{
					System.out.println("Enter a valid input");
					statChange=s.next().charAt(0);
				}
				if(statChange=='1')
					editStat(playing);
				else
					editStat(other);
				if(other.getHp()==0)
					validInput=true;
				else
				{
					System.out.println("Enter the number of the action you'd like to perform");
					action=s.next().charAt(0);
				}
			}
			else if(action=='9')
			{
				instaKill(other);
				validInput=true;
			}
			else
			{
				System.out.println("Please enter a valid input");
				action=s.next().charAt(0);
			}
		}
	}
	public static void editStat(Entity changed)
	{//Allows to edit a stat of yourself or an enemy
		changed.desc();
		System.out.println("If you have been given the authority to change a stat,");
		System.out.println("then you are expected to be trust-worthy. Don't mess it up.");
		System.out.println("Enter the number of the stat you'd like to edit");
		System.out.println("0. Stop editing");
		System.out.println("1. Name");
		System.out.println("2. Type");
		System.out.println("3. Difficulty");
		System.out.println("4. Attack");
		System.out.println("5. Defense");
		System.out.println("6. Health");
		System.out.println("7. Max health");
		System.out.println("8. Speed");
		System.out.println("9. Hit rate");
		System.out.println("10. Crit rate");
		System.out.println("11. Crit damage");
		System.out.println("12. Ult damage");
		System.out.println("13. Ult charge");
		System.out.println("14. Current charge");
		System.out.println("15. Health steal");
		String editStat=s.nextLine();
		editStat=s.nextLine();
		while(!editStat.equals("0"))
		{
			if(editStat.equals("1"))
			{
				System.out.println("Enter a new name");
				String name=s.nextLine();
				changed.setName(name);
				System.out.println("Name is now "+changed.getName());
			}
			else if(editStat.equals("2"))
			{
				System.out.println("Enter a new type");
				String type=s.nextLine();
				changed.setType(type);
				System.out.println("Type is now "+changed.getType());
			}
			else if(editStat.equals("3"))
			{
				System.out.println("Enter a new difficulty");
				String diff=s.nextLine();
				changed.setDifficulty(diff);
				System.out.println("Difficulty is now "+changed.getDifficulty());
			}
			else if(editStat.equals("4"))
			{
				System.out.println("Enter a new attack");
				double attack=s.nextDouble();
				while(attack<0)
				{
					System.out.println("Attack must be >=0");
					attack=s.nextDouble();
				}
				changed.setAtk((int)attack);
				System.out.println("Attack is now "+changed.getAtk());
			}
			else if(editStat.equals("5"))
			{
				System.out.println("Enter a new defense");
				double defense=s.nextDouble();
				while(defense<0)
				{
					System.out.println("Defense must be >=0");
					defense=s.nextDouble();
				}
				changed.settDef((int)defense);
				System.out.println("Defense is now "+changed.getDef());
			}
			else if(editStat.equals("6"))
			{
				System.out.println("Enter a new health");
				double health=s.nextDouble();
				while(health<0)
				{
					System.out.println("Health must be >=0");
					health=s.nextDouble();
				}
				changed.setHp((int)health);
				System.out.println("Health is now "+changed.getHp());
			}
			else if(editStat.equals("7"))
			{
				System.out.println("Enter a new max health");
				double maxHealth=s.nextDouble();
				while(maxHealth<0)
				{
					System.out.println("Max health must be >=0");
					maxHealth=s.nextDouble();
				}
				changed.setMaxHp((int)maxHealth);
				System.out.println("Max health is now "+changed.getMaxHp());
			}
			else if(editStat.equals("8"))
			{
				System.out.println("Enter a Speed");
				double speed=s.nextDouble();
				while(speed<1)
				{
					System.out.println("Speed must be >0");
					speed=s.nextDouble();
				}
				changed.setSpd((int)speed);
				System.out.println("Speed is now "+changed.getSpd());
			}
			else if(editStat.equals("9"))
			{
				System.out.println("Enter a new hit rate");
				double hit=s.nextDouble();
				while(hit<0)
				{
					System.out.println("Hit rate must be >=0");
					hit=s.nextDouble();
				}
				changed.setMaxHp((int)hit);
				System.out.println("Hit rate is now "+changed.getMaxHp());
			}
			else if(editStat.equals("10"))
			{
				System.out.println("Enter a new crit rate");
				double critRate=s.nextDouble();
				while(critRate<1)
				{
					System.out.println("Crit rate must be >0");
					critRate=s.nextDouble();
				}
				changed.setCritRate((int)critRate);
				System.out.println("Crit rate is now "+changed.getCritRate());
			}
			else if(editStat.equals("11"))
			{
				System.out.println("Enter a new crit damage");
				double critDmg=s.nextDouble();
				while(critDmg<1)
				{
					System.out.println("Crit damage must be >0");
					critDmg=s.nextDouble();
				}
				changed.setCritDmg(critDmg);
				System.out.println("Crit damage is now "+changed.getCritDmg());
			}
			else if(editStat.equals("12"))
			{
				System.out.println("Enter a new ult damage");
				double ult=s.nextDouble();
				while(ult<1)
				{
					System.out.println("Ult damage must be >0");
					ult=s.nextDouble();
				}
				changed.setUlt((int)ult);
				System.out.println("Ult damage is now "+changed.getUlt());
			}
			else if(editStat.equals("13"))
			{
				System.out.println("Enter a new ult charge");
				double charge=s.nextDouble();
				while(charge<1)
				{
					System.out.println("Ult charge must be >0");
					charge=s.nextDouble();
				}
				changed.setCharge((int)charge);
				System.out.println("Ult charge is now "+changed.getCharge());
			}
			else if(editStat.equals("14"))
			{
				System.out.println("Enter a new current charge");
				double currentCharge=s.nextDouble();
				while(currentCharge<1)
				{
					System.out.println("Current charge must be >0");
					currentCharge=s.nextDouble();
				}
				changed.setCurrentCharge((int)currentCharge);
				System.out.println("Current charge is now "+changed.getCurrentCharge());
			}
			else if(editStat.equals("15"))
			{
				System.out.println("Enter a new health steal");
				double hpSteal=s.nextDouble();
				while(hpSteal<0)
				{
					System.out.println("Health steal must be >=0");
					hpSteal=s.nextDouble();
				}
				changed.setHpSteal((int)hpSteal);
				System.out.println("Health steal is now "+changed.getHpSteal());
			}
			else if(!editStat.equals("0"))
				System.out.println("Enter a valid input");
			System.out.println("Enter the number of a new stat, or 0 to stop editing.");
			editStat=s.nextLine();
			editStat=s.nextLine();
		}
	}
	public static void instaKill(Entity instaKilled)
	{//Instantly kill an enemy
		instaKilled.setHp(0);
		System.out.println(instaKilled.getName()+" was vaporized!");
	}
	public static void attack(Entity attacking, Entity attacked)
	{//The basic attack, deal damage based on your attack stat, with a degree of randomness
		attacking.setDef(attacking.gettDef());
		System.out.println(attacking.getName()+" attacks!");
		int hit=r.nextInt(100)+1;
		if(hit<=attacking.getHit())
		{
			double dmg=damage(attacking, attacked);
			if((int)dmg==0)
				dmg=1;
			attacked.setHp(attacked.getHp()-(int)dmg);
			System.out.println(attacking.getName()+" dealt "+(int)dmg+" damage!");
			if(attacked.getHp()>0)
				System.out.println(attacked.getName()+ " has "+attacked.getHp()+" hp left!");
			else
				System.out.println(attacked.getName()+ " has 0 hp left!");
			if(attacking.getHpSteal()!=0)
			{
				double heal=dmg*0.01*attacking.getHpSteal();
				if((int)heal==0&&(int)dmg!=0)
					heal=1;
				if(heal>Integer.MAX_VALUE*0.5)
					heal=Integer.MAX_VALUE*0.5;
				else
					attacking.setHp(attacking.getHp()+(int)heal);
				attacking.setHp(attacking.getHp()+(int)heal);
				System.out.println(attacking.getName()+" healed "+(int)heal+" hp!");
				System.out.println(attacking.getName()+" has "+attacking.getHp()+" hp!");
			}
		}
		else
			System.out.println("Attack failed!");
		if(attacking.getCurrentCharge()<attacking.getCharge())
		{
			int currentCharge=attacking.getCurrentCharge()+1;
			attacking.setCurrentCharge(currentCharge);
		}
	}
	public static void defend(Entity defending)
	{//Doubles your defense until the next turn
		defending.setDef(defending.getDef()*2);
		if(defending.getCurrentCharge()<defending.getCharge())
		{
			int currentCharge=defending.getCurrentCharge()+2;
			defending.setCurrentCharge(currentCharge);
		}
		System.out.println(defending.getName()+" increased defense!");
	}
	public static void charge(Entity charging)
	{//increase current charge by 3
		if(charging.getCurrentCharge()<charging.getCharge())
		{
			charging.setDef(charging.gettDef());
			int currentCharge=charging.getCurrentCharge()+3;
			charging.setCurrentCharge(currentCharge);
		}
		System.out.println(charging.getName()+" Charged ult!");
	}
	public static void ult(Entity attacking, Entity attacked)
	{//release ult, dealing damage of attack*ult, with a degree of randomness
		attacking.setDef(attacking.gettDef());
		attacking.setCurrentCharge(0);
		System.out.println(attacking.getName()+" uses ult!");
		int hit=r.nextInt(100)+1;
		if(hit<=attacking.getHit()*1.1)
		{
			double dmg=damage(attacking, attacked)*attacking.getUlt();
			if((int)dmg==0)
				dmg=1;
			attacked.setHp(attacked.getHp()-(int)dmg);
			System.out.println(attacking.getName()+" dealt "+(int)dmg+" damage!");
			if(attacked.getHp()>0)
				System.out.println(attacked.getName()+ " has "+attacked.getHp()+" hp left!");
			else
				System.out.println(attacked.getName()+ " has 0 hp left!");
			if(attacking.getHpSteal()!=0)
			{
				double heal=dmg*0.01*attacking.getHpSteal();
				if((int)heal==0&&(int)dmg!=0)
					heal=1;
				if(heal>Integer.MAX_VALUE*0.5)
					heal=Integer.MAX_VALUE*0.5;
				else
					attacking.setHp(attacking.getHp()+(int)heal);
				System.out.println(attacking.getName()+" healed "+(int)heal+" hp!");
				System.out.println(attacking.getName()+" has "+attacking.getHp()+" hp!");
			}
		}
		else
			System.out.println("Attack failed!");
	}
	public static void heal(Entity healing)
	{//Heal based on your attack, requires one charge
		healing.setDef(healing.gettDef());
		System.out.println(healing.getName()+" tries to heal!");
		int hit=r.nextInt(100)+1;
		if(hit<=healing.getHit())
		{
			int mult=r.nextInt(10)+11;
			double heal=healing.getAtk()*((double)mult)/10;
			healing.setHp(healing.getHp()+(int)heal);
			if(healing.getHp()>healing.getMaxHp())
				healing.setHp(healing.getMaxHp());
			System.out.println(healing.getName()+" heals "+(int)heal+" hp!");
			System.out.println(healing.getName()+" has "+healing.getHp()+" hp!");
		}
		else
			System.out.println("Heal failed!");
	}
	public static double damage(Entity attacking, Entity attacked)
	{//Determines how much damage will be dealt based on your attack and enemy's defense 
		double dmg=attacking.getAtk();
		if(attacked.getDef()==0||dmg*0.1>=attacked.getDef())
			dmg=dmg*2;
		else if(dmg*10<attacked.getDef())
			dmg=dmg*0.01;
		else if(dmg*10>=attacked.getDef()&&dmg*5<attacked.getDef())
			dmg=dmg*0.2;
		else if(dmg*5>=attacked.getDef()&&dmg*2<attacked.getDef())
			dmg=dmg*0.33;
		else if(dmg*2>=attacked.getDef()&&dmg*1.5<attacked.getDef())
			dmg=dmg*0.5;
		else if(dmg*1.5>=attacked.getDef()&&dmg*1.2<attacked.getDef())
			dmg=dmg*0.7;
		else if(dmg*1.2>=attacked.getDef()&&dmg<attacked.getDef())
			dmg=dmg*0.8;
		else if(dmg>=attacked.getDef()&&dmg*0.7<attacked.getDef())
			dmg=dmg*0.9;
		else if(dmg*0.5>=attacked.getDef()&&dmg*0.4<attacked.getDef())
			dmg=dmg*1.1;
		else if(dmg*0.4>=attacked.getDef()&&dmg*0.3<attacked.getDef())
			dmg=dmg*1.2;
		else if(dmg*0.3>=attacked.getDef()&&dmg*0.2<attacked.getDef())
			dmg=dmg*1.3;
		else if(dmg*0.2>=attacked.getDef()&&dmg*0.1<attacked.getDef())
			dmg=dmg*1.5;
		dmg*=randMult(attacking);
		return dmg;
	}
	public static double randMult(Entity a)
	{//Randomly determines how strong an attack will be
		if(a.getCritRate()>10)
			a.setCritRate(10);
		Random r=new Random();
		int mult1=r.nextInt(1000)+1;
		double mult2=1;
		if(a.getCritRate()==10)
		{
			if(mult1>=(1000-100*a.getCritRate())&&mult1<(1000-10*a.getCritRate()))
			{
				System.out.println("Strong hit!");
				mult2=3*(1+(a.getCritDmg()-1)*0.5);
			}
			else if(mult1>=(1000-10*a.getCritRate())&&mult1<=1000-a.getCritRate())
			{
				System.out.println("Critical hit!");
				mult2=5*a.getCritDmg();
			}
			else if(mult1>1000-a.getCritRate())
			{
				System.out.println("Ultra critical hit!");
				mult2=100*a.getCritDmg();
			}
		}
		else if(mult1==1)
		{
			System.out.println("Ultra critical failure!");
			mult2=0.01;
		}
		else if(mult1>1&&mult1<=10)
		{
			System.out.println("Critical failure!");
			mult2=0.1;
		}
		else if(mult1>=(1000-100*a.getCritRate())&&mult1<(1000-10*a.getCritRate()))
		{
			System.out.println("Strong hit!");
			mult2=3*(1+(a.getCritDmg()-1)*0.5);;
		}
		else if(mult1>=(1000-10*a.getCritRate())&&mult1<1000-a.getCritRate()+1)
		{
			System.out.println("Critical hit!");
			mult2=5*a.getCritDmg();
		}
		else if(mult1>=1000-a.getCritRate()+1)
		{
			System.out.println("Ultra critical hit!");
			mult2=100*a.getCritDmg();
		}
		else
		{
			mult1=r.nextInt(8)+8;
			mult2=(double)mult1/10;
		}
		return mult2;
	}
	public static void applyUpgrade(Entity upgraded, Upgrade up)
	{//Apply an upgrade to the Entity
		if(up.isMult())
		{
			if(up.getStat()=="Attack")
			{
				double atk=upgraded.getAtk()*up.getNum();
				upgraded.setAtk((int)atk);
			}
			else if(up.getStat()=="Defense")
			{
				double def=upgraded.getDef()*up.getNum();
				upgraded.setDefUpgrade((int)def);
			}
			else if(up.getStat()=="Health")
			{
				double maxHp=upgraded.getMaxHp()*up.getNum();
				double hp=upgraded.getHp()*up.getNum();
				upgraded.setMaxHp((int)maxHp);
				upgraded.setHp((int)hp);
			}
			else if(up.getStat()=="Ult")
			{
				double ult=upgraded.getUlt()*up.getNum();
				upgraded.setUlt((int)ult);
			}
			else if(up.getStat()=="Speed")
			{
				double spd=upgraded.getSpd()*up.getNum();
				upgraded.setSpd((int)spd);
			}
			else if(up.getStat()=="Crit Damage")
			{
				double critDmg=upgraded.getCritDmg()*up.getNum();
				upgraded.setCritDmg(critDmg);
			}
			else if(up.getStat()=="Full Stats")
				upgraded.fullStats1(up.getNum());
			if(up.getStat()!="Full Stats")
				System.out.println(up.getStat()+" multiplied by "+up.getNum()+"!");
			else
			{
				System.out.println("Attack, defense and health multiplied by "+up.getNum()+"!");
				System.out.println("Speed multiplied by "+(1+(up.getNum()-1)*0.5)+"!");
			}
		}
		else if(up.getStat()=="Heal")
		{
			upgraded.setHp(upgraded.getMaxHp());
			System.out.println("Healed to full health!");
		}
		else
		{
			if(up.getStat()=="ult")
			{
				double ult=upgraded.getUlt()+up.getNum();
				upgraded.setUlt((int)ult);
			}
			if(up.getStat()=="speed")
			{
				double spd=upgraded.getSpd()+up.getNum();
				upgraded.setSpd((int)spd);
			}
			if(up.getStat()=="Hit")
			{
				double hit=upgraded.getHit()+up.getNum();
				upgraded.setHit((int)hit);
			}
			else if(up.getStat()=="Charge")
			{
				double charge=upgraded.getCharge()-up.getNum();
				upgraded.setCharge((int)charge);
			}
			else if(up.getStat()=="Crit Rate")
			{
				double critRate=upgraded.getCritRate()+up.getNum();
				upgraded.setCritRate((int)critRate);
			}
			else if(up.getStat()=="Crit damage")
			{
				double critDmg=upgraded.getCritDmg()+up.getNum();
				upgraded.setCritDmg(critDmg);
			}
			else if(up.getStat()=="Health Steal")
			{
				double hpSteal=upgraded.getHpSteal()+up.getNum();
				upgraded.setHpSteal((int)hpSteal);
			}
			if(up.getStat()!="Charge")
				System.out.println(up.getStat()+" increased by "+up.getNum());
			else
				System.out.println("Charge decreased by "+up.getNum());
		}
		System.out.println();
	}
	public static Upgrade randUpgrade(Entity upgraded, String rarity)
	{//Chooses a random upgrade out of 41. If an upgrade is obsolete, it will be changed to another one
		int upgradeCount=0;
		
		String effect1="Attack*1.25";
		Upgrade u1=new Upgrade("Common", effect1, "Attack", true, 1.25, 1);
		upgradeCount++;
		
		String effect2="Attack*1.5";
		Upgrade u2=new Upgrade("Rare", effect2, "Attack", true, 1.5, 2);
		upgradeCount++;
		
		String effect3="Attack*2";
		Upgrade u3=new Upgrade("Epic", effect3, "Attack", true, 2, 3);
		upgradeCount++;
		
		String effect4="Attack*5";
		Upgrade u4=new Upgrade("Legendary", effect4, "Attack", true, 5, 4);
		upgradeCount++;
		
		String effect5="Defense*1.5";
		Upgrade u5=new Upgrade("Common", effect5, "Defense", true, 1.5, 1);
		upgradeCount++;
		
		String effect6="Defense*2";
		Upgrade u6=new Upgrade("Rare", effect6, "Defense", true, 2, 2);
		upgradeCount++;
		
		String effect7="Defense*3";
		Upgrade u7=new Upgrade("Epic", effect7, "Defense", true, 3, 3);
		upgradeCount++;
		
		String effect8="Defense*7";
		Upgrade u8=new Upgrade("Legendary", effect8, "Defense", true, 7, 4);
		upgradeCount++;
		
		String effect9="Required charge for ult -2";
		Upgrade u9=new Upgrade("Rare", effect9, "Charge", false, 2, 1);
		upgradeCount++;
		
		String effect10="Required charge for ult -3";
		Upgrade u10=new Upgrade("Epic", effect10, "Charge", false, 3, 2);
		upgradeCount++;
		
		String effect11="Crit rate +1";
		Upgrade u11=new Upgrade("Common", effect11, "Crit Rate", false, 1, 1);
		upgradeCount++;
		
		String effect12="Crit rate +2";
		Upgrade u12=new Upgrade("Rare", effect12, "Crit Rate", false, 2, 2);
		upgradeCount++;
		
		String effect13="Crit rate +5";
		Upgrade u13=new Upgrade("Epic", effect13, "Crit Rate", false, 5, 3);
		upgradeCount++;
		
		String effect14="Increase crit Rate to max";
		Upgrade u14=new Upgrade("Legendary", effect14, "Crit Rate", false, 10, 3);
		upgradeCount++;

		String effect15="Crit damage +1.5";
		Upgrade u15=new Upgrade("Common", effect15, "Crit damage", false, 1.5, 1);
		upgradeCount++;

		String effect16="Crit damage +3";
		Upgrade u16=new Upgrade("Rare", effect16, "Crit damage", false, 3, 2);
		upgradeCount++;

		String effect17="Crit damage +10";
		Upgrade u17=new Upgrade("Epic", effect17, "Crit damage", false, 10, 3);
		upgradeCount++;

		String effect18="Crit damage*2";
		Upgrade u18=new Upgrade("Rare", effect18, "Crit Damage", true, 2, 1);
		upgradeCount++;

		String effect19="Crit damage*5";
		Upgrade u19=new Upgrade("Epic", effect19, "Crit Damage", true, 5, 2);
		upgradeCount++;

		String effect20="Crit damage*10";
		Upgrade u20=new Upgrade("Legendary", effect20, "Crit Damage", true, 10, 3);
		upgradeCount++;

		String effect21="Ult damage +2";
		Upgrade u21=new Upgrade("Common", effect21, "ult", false, 2, 1);
		upgradeCount++;

		String effect22="Ult damage*2";
		Upgrade u22=new Upgrade("Rare", effect22, "Ult", true, 2, 1);
		upgradeCount++;

		String effect23="Ult damage*3";
		Upgrade u23=new Upgrade("Epic", effect23, "Ult", true, 3, 2);
		upgradeCount++;

		String effect24="Ult damage*10";
		Upgrade u24=new Upgrade("Legendary", effect24, "Ult", true, 10, 3);
		upgradeCount++;

		String effect25="Hit rate +10";
		Upgrade u25=new Upgrade("Common", effect25, "Hit", false, 10, 1);
		upgradeCount++;

		String effect26="Hit rate +20";
		Upgrade u26=new Upgrade("Rare", effect26, "Hit", false, 20, 2);
		upgradeCount++;

		String effect27="Speed +20";
		Upgrade u27=new Upgrade("Rare", effect27, "speed", false, 20, 1);
		upgradeCount++;

		String effect28="Speed +40";
		Upgrade u28=new Upgrade("Epic", effect28, "speed", false, 40, 2);
		upgradeCount++;

		String effect29="Speed*1.5";
		Upgrade u29=new Upgrade("Epic", effect29, "Speed", true, 1.5, 1);
		upgradeCount++;

		String effect30="Speed*3";
		Upgrade u30=new Upgrade("Legendary", effect30, "Speed", true, 3, 2);
		upgradeCount++;

		String effect31="Health Steal +10";
		Upgrade u31=new Upgrade("Common", effect31, "Health Steal", false, 10, 1);
		upgradeCount++;

		String effect32="Health Steal +20";
		Upgrade u32=new Upgrade("Rare", effect32, "Health Steal", false, 20, 2);
		upgradeCount++;

		String effect33="Health Steal +50";
		Upgrade u33=new Upgrade("Epic", effect33, "Health Steal", false, 50, 3);
		upgradeCount++;

		String effect34="Increase health Steal to max";
		Upgrade u34=new Upgrade("Legendary", effect34, "Health Steal", false, 100, 4);
		upgradeCount++;

		String effect35="Attack, defense and health*1.5, speed*1.25";
		Upgrade u35=new Upgrade("Epic", effect35, "Full Stats", true, 1.5, 1);
		upgradeCount++;

		String effect36="Attack, defense and health*3, speed*2";
		Upgrade u36=new Upgrade("Legendary", effect36, "Full Stats", true, 3, 2);
		upgradeCount++;

		String effect37="Max health*2";
		Upgrade u37=new Upgrade("Common", effect37, "Health", true, 2, 1);
		upgradeCount++;

		String effect38="Max Health*3";
		Upgrade u38=new Upgrade("Rare", effect38, "Health", true, 3, 2);
		upgradeCount++;

		String effect39="Max health*5";
		Upgrade u39=new Upgrade("Epic", effect39, "Health", true, 5, 3);
		upgradeCount++;

		String effect40="Max Health*10";
		Upgrade u40=new Upgrade("Legendary", effect40, "Health", true, 10, 4);
		upgradeCount++;

		String effect41="Heal 100% health";
		Upgrade u41=new Upgrade("Common", effect41, "Heal", false, 1, 1);
		upgradeCount++;
		Upgrade[] upArray= {
			    u1,
			    u2,
			    u3,
			    u4,
			    u5,
			    u6,
			    u7,
			    u8,
			    u9,
			    u10,
			    u11,
			    u12,
			    u13,
			    u14,
			    u15,
			    u16,
			    u17,
			    u18,
			    u19,
			    u20,
			    u21,
			    u22,
			    u23,
			    u24,
			    u25,
			    u26,
			    u27,
			    u28,
			    u29,
			    u30,
			    u31,
			    u32,
			    u33,
			    u34,
			    u35,
			    u36,
			    u37,
			    u38,
			    u39,
			    u40,
			    u41
			};
		int randUpgrade=r.nextInt(upgradeCount);
		while(!upArray[randUpgrade].getRarity().equals(rarity))
			randUpgrade=r.nextInt(upgradeCount);
		boolean emg=false;
		String stat = upArray[randUpgrade].getStat();
		int level = upArray[randUpgrade].getLevel();
		if (stat.equals("Charge")) 
		{
		    if (upgraded.getCharge() == 4)
		        emg = true;
		    else if (upgraded.getCharge() == 5 && level > 1)
		        emg = true;
		}

		else if (stat.equals("Hit"))
		{
		    if (upgraded.getHit() == 100)
		        emg = true;
		    else if (upgraded.getHit() >= 90 && level > 1)
		        emg = true;
		}

		else if (stat.equals("Heal"))
		{
		    if (upgraded.getHp() >= upgraded.getMaxHp() * 0.7)
		        emg = true;
		}

		else if (stat.equals("Crit Rate"))
		{
		    if (upgraded.getCritRate() == 10)
		        emg = true;
		    else if (upgraded.getCritRate() >= 9 && level > 1)
		        emg = true;
		    else if (upgraded.getCritRate() >= 8 && level > 2)
		        emg = true;
		    else if (upgraded.getCritRate() >= 6 && level > 3)
		        emg = true;
		}

		else if (stat.equals("Crit damage"))
		{
		    if (upgraded.getCritDmg() >= 15)
		        emg = true;
		    else if (upgraded.getCritDmg() >= 8 && level > 1)
		        emg = true;
		    else if (upgraded.getCritDmg() >= 4 && level > 2)
		        emg = true;
		}

		else if (stat.equals("speed"))
		{
		    if (upgraded.getSpd() >= 200)
		        emg = true;
		    else if (upgraded.getSpd() >= 100 && level > 1)
		        emg = true;
		}

		else if (stat.equals("ult"))
		{
		    if (upgraded.getUlt() >= 20)
		        emg = true;
		}

		else if (stat.equals("Health Steal"))
		{
		    if (upgraded.getHpSteal() == 100)
		        emg = true;
		    else if (upgraded.getHpSteal() >= 90 && level > 1)
		        emg = true;
		    else if (upgraded.getHpSteal() >= 70 && level > 2)
		        emg = true;
		    else if (upgraded.getHpSteal() >= 50 && level > 3)
		        emg = true;
		}
		if(emg)
		{
			String emgRarity=randUpgradeRarity(false);
			if(emgRarity=="Common")
			{
				int emgUpgrade=r.nextInt(3)+1;
				if(emgUpgrade==1)
					randUpgrade=0;
				if(emgUpgrade==2)
					randUpgrade=4;
				if(emgUpgrade==3)
					randUpgrade=35;
			}
			if(emgRarity=="Rare")
			{
				int emgUpgrade=r.nextInt(3)+1;
				if(emgUpgrade==1)
					randUpgrade=1;
				if(emgUpgrade==2)
					randUpgrade=5;
				if(emgUpgrade==3)
					randUpgrade=36;
			}
			if(emgRarity=="Epic")
			{
				int emgUpgrade=r.nextInt(4)+1;
				if(emgUpgrade==1)
					randUpgrade=2;
				if(emgUpgrade==2)
					randUpgrade=6;
				if(emgUpgrade==3)
					randUpgrade=33;
				if(emgUpgrade==4)
					randUpgrade=37;
			}
			if(emgRarity=="Legendary")
			{
				int emgUpgrade=r.nextInt(4)+1;
				if(emgUpgrade==1)
					randUpgrade=3;
				if(emgUpgrade==2)
					randUpgrade=7;
				if(emgUpgrade==3)
					randUpgrade=34;
				if(emgUpgrade==4)
					randUpgrade=38;
			}
		}
		Upgrade tUpgrade=new Upgrade(upArray[randUpgrade]);
		return tUpgrade;
	}
	public static String randUpgradeRarity(boolean bossUpgrade)
	{//Chooses a rarity for the random upgrade, the higher the rarity, the stronger it will be
		String rarity;
		int num=r.nextInt(100)+1;
		if(bossUpgrade)
		{
			if(num<=15)
				rarity="Common";
			else if(num>15&&num<=65)
				rarity="Rare";
			else if(num>65&&num<=95)
				rarity="Epic";
			else
				rarity="Legendary";
		}
		else
		{
			if(num<=70)
				rarity="Common";
			else if(num>70&&num<=95)
				rarity="Rare";
			else if(num>95&&num<=99)
				rarity="Epic";
			else
				rarity="Legendary";
		}
		return rarity;
	}
	public static Upgrade chooseUpgrade(Entity upgraded, boolean bossBattle)
	{//Allows the player to choose one upgrade out of 3 random ones
		upgraded.desc();
		Upgrade u1=new Upgrade(randUpgrade(upgraded, randUpgradeRarity(bossBattle)));
		Upgrade u2=new Upgrade(randUpgrade(upgraded, randUpgradeRarity(bossBattle)));
		while(u2.getStat().equals(u1.getStat()))
			u2=new Upgrade(randUpgrade(upgraded, randUpgradeRarity(bossBattle)));
		Upgrade u3=new Upgrade(randUpgrade(upgraded, randUpgradeRarity(bossBattle)));
		while(u3.getStat().equals(u1.getStat())||u3.getStat().equals(u2.getStat()))
			u3=new Upgrade(randUpgrade(upgraded, randUpgradeRarity(bossBattle)));
		System.out.println("Choose an upgrade of the following 3:");
		System.out.println();
		System.out.print("1.");
		u1.desc();
		System.out.print("2.");
		u2.desc();
		System.out.print("3.");
		u3.desc();
		char chooseUpgrade=s.next().charAt(0);
		while(chooseUpgrade!='1'&&chooseUpgrade!='2'&&chooseUpgrade!='3')
		{
			System.out.println("Enter a valid input");
			chooseUpgrade=s.next().charAt(0);
		}
		if(chooseUpgrade=='1')
			return u1;
		if(chooseUpgrade=='2')
			return u2;
		return u3;
	}
	public static Entity chooseE(Entity e, Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8, char gameMode)
	{//Allows the player to choose an Entity
		System.out.println("1. The all rounder. Has decent numbers in all stats.");
		System.out.println("2. The tank. Low attack but high defense and health. low speed but high hit rate");
		System.out.println("3. The assassin. High attack and speed but low defense and health.");
		System.out.println("4. Fast. Very high speed and hit rate but attack defense and health are low. also has a strong ult.");
		System.out.println("5. The heavy hitter. High attack, defense and health, but low speed and hit rate.");
		System.out.println("6. The critical hitter. Has a higher chance to land strong and critical hits but has very low defense.");
		System.out.println("7. The health stealer. Can heal a proportionate amount to the damage dealt.");
		if(gameMode=='4'||gameMode=='5')
			System.out.println("8. The boss. Has very high numbers in all stats.");
		char chooseE=s.next().charAt(0);
		boolean validInput=false;
		while(!validInput)
		{
			if(chooseE=='1')
			{
				e=new Entity(e1);
				validInput=true;
			}
			else if(chooseE=='2')
			{
				e=new Entity(e2);
				validInput=true;
			}
			else if(chooseE=='3')
			{
				e=new Entity(e3);
				validInput=true;
			}
			else if(chooseE=='4')
			{
				e=new Entity(e4);
				validInput=true;
			}
			else if(chooseE=='5')
			{
				e=new Entity(e5);
				validInput=true;
			}
			else if(chooseE=='6')
			{
				e=new Entity(e6);
				validInput=true;
			}
			else if(chooseE=='7')
			{
				e=new Entity(e7);
				validInput=true;
			}
			else if(chooseE=='8'&&(gameMode=='4'||gameMode=='5'))
			{
				e=new Entity(e8);
				validInput=true;
			}
			else
			{
				System.out.println("Enter a valid input");
				chooseE=s.next().charAt(0);
			}
		}
		return e;
	}
	public static Entity chooseERand(Entity e, Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8, char gameMode)
	{//Chooses a random Entity for the enemy
		if(gameMode=='4'||gameMode=='5')
		{
			int chooseEnemy=r.nextInt(8)+1;
			if(chooseEnemy==1)
				e=new Entity(e1);
			else if(chooseEnemy==2)
				e=new Entity(e2);
			else if(chooseEnemy==3)
				e=new Entity(e3);
			else if(chooseEnemy==4)
				e=new Entity(e4);
			else if(chooseEnemy==5)
				e=new Entity(e5);
			else if(chooseEnemy==6)
				e=new Entity(e6);
			else if(chooseEnemy==7)
				e=new Entity(e7);
			else if(chooseEnemy==8)
				e=new Entity(e8);
		}
		else
		{
			int chooseEnemy=r.nextInt(7)+1;
			if(chooseEnemy==1)
				e=new Entity(e1);
			else if(chooseEnemy==2)
				e=new Entity(e2);
			else if(chooseEnemy==3)
				e=new Entity(e3);
			else if(chooseEnemy==4)
				e=new Entity(e4);
			else if(chooseEnemy==5)
				e=new Entity(e5);
			else if(chooseEnemy==6)
				e=new Entity(e6);
			else if(chooseEnemy==7)
				e=new Entity(e7);
		}
		return e;
	}
	public static void gameMode1(Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8)
	{//Initiates game mode 1 (player vs player)
		Entity p1=new Entity();
		Entity p2=new Entity();
		System.out.println("Player 1, choose the number of the character you want to play");
		p1=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'1'));
		p1.setName("Player1");
		System.out.println("Player 2, choose the number of the character you want to play");
		p2=new Entity(chooseE(p2,e1,e2,e3,e4,e5,e6,e7,e8,'1'));
		p2.setName("Player2");
		boolean win1=false;
		boolean win2=false;
		int spdCount1=0;
		int spdCount2=0;
		while(!win1&&!win2)
		{
			spdCount1+=p1.getSpd();
			spdCount2+=p2.getSpd();
			while((spdCount1>=100||spdCount2>=100)&&p1.getHp()>0&&p2.getHp()>0)
			{
				if(spdCount2>spdCount1&&p2.getHp()>0)
				{
					turnPlayer(p2, p1);
					spdCount2-=100;
					System.out.println("Enter any key to continue");
					char hold=s.next().charAt(0);
				}
				else if(p1.getHp()>0)
				{
					turnPlayer(p1, p2);
					spdCount1-=100;
					System.out.println("Enter any key to continue");
					char hold=s.next().charAt(0);
				}
			}
			if(p2.getHp()<1)
				win1=true;
			if(p1.getHp()<1)
				win2=true;
		}
		if(win1)
			System.out.println("Player 1 wins!");
		else
			System.out.println("Player 2 wins!");
	}
	public static void gameMode2(Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8)
	{//Initiates game mode 2 (player vs computer)
		Entity p1=new Entity();
		Entity p2=new Entity();
		System.out.println("Choose difficulty");
		System.out.println("1. Normal");
		System.out.println("2. Easy");
		System.out.println("3. Immortal");
		System.out.println("4. Turn on cheats");
		char chooseDiff=s.next().charAt(0);
		while(chooseDiff!='1'&&chooseDiff!='2'&&chooseDiff!='3'&&chooseDiff!='4')
		{
			System.out.println("Enter a valid input");
			chooseDiff=s.next().charAt(0);
		}
		if(chooseDiff!='2')
		{
			System.out.println("Player 1, choose the number of the character you want to play");
			p1=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'2'));
			p1.setName("Player 1");
		}
		else
			p1=new Entity("Player 1", "God Mode", "n/a", 100, 100, 1000, 100, 100, 5, 3, 10, 50, 5);
		if(chooseDiff=='3')
		{
			p1.setMaxHp(Integer.MAX_VALUE/2);
			p1.setHp(p1.getMaxHp());
		}
		p2=new Entity(chooseERand(p2,e1,e2,e3,e4,e5,e6,e7,e8,'2'));
		p2.setName("Enemy");
		boolean win1=false;
		boolean win2=false;
		int spdCount1=0;
		int spdCount2=0;
		p2.desc();
		while(!win1&&!win2)
		{
			spdCount1+=p1.getSpd();
			spdCount2+=p2.getSpd();
			while((spdCount1>=100||spdCount2>=100)&&p1.getHp()>0&&p2.getHp()>0)
			{
				if(spdCount2>spdCount1&&p2.getHp()>0)
				{
					turnEnemy(p2, p1);
					spdCount2-=100;
					System.out.println("Enter any key to continue");
					char hold=s.next().charAt(0);
				}
				else if(p1.getHp()>0)
				{
					if(chooseDiff!='4')
						turnPlayer(p1, p2);
					else
						turnAdmin(p1, p2);
					spdCount1-=100;
					System.out.println("Enter any key to continue");
					char hold=s.next().charAt(0);
				}
			}
			if(p2.getHp()<1)
				win1=true;
			if(p1.getHp()<1)
				win2=true;
		}
		if(win1)
			System.out.println("Player 1 wins!");
		else
			System.out.println("Enemy wins!");
	}
	public static void gameMode3(Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8)
	{//Initiates game mode 3 (player vs computer - full game)
		Entity p1=new Entity();
		Entity p2=new Entity();
		String difficulty="weak";
		System.out.println("Choose difficulty");
		System.out.println("1. Normal");
		System.out.println("2. Easy");
		System.out.println("3. Immortal");
		System.out.println("4. Turn on cheats");
		char chooseDiff=s.next().charAt(0);
		while(chooseDiff!='1'&&chooseDiff!='2'&&chooseDiff!='3'&&chooseDiff!='4')
		{
			System.out.println("Enter a valid input");
			chooseDiff=s.next().charAt(0);
		}
		if(chooseDiff!='2')
		{
			System.out.println("Player 1, choose the number of the character you want to play");
			p1=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'3'));
			p1.setName("Player 1");
		}
		else
			p1=new Entity("Player 1", "God Mode", "n/a", 100, 100, 1000, 100, 100, 5, 3, 10, 50, 5);
		if(chooseDiff=='3')
		{
			p1.setMaxHp(Integer.MAX_VALUE/2);
			p1.setHp(p1.getMaxHp());
		}
		boolean defeat=false;
		for(int diffLv=1;diffLv<=5&&!defeat;diffLv++)
		{
			if(diffLv==2)
			{
				System.out.println("Difficulty increased!");
				difficulty="Medium";
			}
			if(diffLv==3)
			{
				System.out.println("Difficulty increased!");
				difficulty="Strong";
			}
			if(diffLv==4)
			{
				System.out.println("Difficulty increased!");
				difficulty="Extreme";
			}
			if(diffLv==5)
			{
				System.out.println("Difficulty increased!");
				difficulty="Deadly";
			}
			System.out.println("Current difficulty: "+difficulty);
			boolean bossBattle=false;
			for(int battleCount=0;battleCount<5&&!defeat;battleCount++)
			{
				System.out.println("Battle "+(battleCount+1)+"/5");
				p2=new Entity(chooseERand(p2,e1,e2,e3,e4,e5,e6,e7,e8,'3'));
				p2.setName("Enemy");
				p2.setDifficulty(difficulty);
				int randDiff=r.nextInt(100)+1;
				if(diffLv==1)
				{
					if(battleCount!=0)
					{
						bossBattle=true;
						if(randDiff>95&&randDiff<=99)
						{
							p2.fullStats2(Math.pow(3, diffLv));
							p2.setDifficulty("Medium");
							bossBattle=true;
						}
						else if(randDiff>99)
						{
							p2.fullStats2(Math.pow(3, diffLv+1));
							p2.setDifficulty("Strong");
							bossBattle=true;
						}
						else
						{
							p2.fullStats2(Math.pow(3, diffLv-1));
						}
					}
					else
						p2.fullStats2(Math.pow(3, diffLv-1));
				}
				if(diffLv==2)
				{
					if(battleCount!=0)
					{
						if(randDiff>95&&randDiff<=99)
						{
							p2.fullStats2(Math.pow(3, diffLv));
							p2.setDifficulty("Strong");
							bossBattle=true;
						}
						else if(randDiff>99)
						{
							p2.fullStats2(Math.pow(3, diffLv+1));
							p2.setDifficulty("Extreme");
							bossBattle=true;
						}
						else if(randDiff<=5)
						{
							p2.fullStats2(Math.pow(3, diffLv-2));
							p2.setDifficulty("Weak");
						}
						else
							p2.fullStats2(Math.pow(3, diffLv-1));
					}
					else
						p2.fullStats2(Math.pow(3, diffLv-1));
				}
				if(diffLv==3)
				{
					if(battleCount!=0)
					{
						if(randDiff>95&&randDiff<=99)
						{
							p2.fullStats2(Math.pow(3, diffLv));
							p2.setDifficulty("Extreme");
							bossBattle=true;
						}
						else if(randDiff>99)
						{
							p2.fullStats2(Math.pow(3, diffLv+1));
							p2.setDifficulty("Deadly");
							bossBattle=true;
						}
						else if(randDiff<=5&&randDiff>1)
						{
							p2.fullStats2(Math.pow(3, diffLv-2));
							p2.setDifficulty("Medium");
						}
						else if(randDiff==1)
						{
							p2.fullStats2(Math.pow(3, diffLv-3));
							p2.setDifficulty("Weak");
						}
						else
							p2.fullStats2(Math.pow(3, diffLv-1));
					}
					else
						p2.fullStats2(Math.pow(3, diffLv-1));
				}
				if(diffLv==4)
				{
					if(battleCount!=0)
					{
						if(randDiff>95)
						{
							p2.fullStats2(Math.pow(3, diffLv));
							p2.setDifficulty("Deadly");
							bossBattle=true;
						}
						else if(randDiff<=5&&randDiff>1)
						{
							p2.fullStats2(Math.pow(3, diffLv-2));
							p2.setDifficulty("Strong");
						}
						else if(randDiff==1)
						{
							p2.fullStats2(Math.pow(3, diffLv-3));
							p2.setDifficulty("Medium");
						}
						else
							p2.fullStats2(Math.pow(3, diffLv-1));
					}
					else
						p2.fullStats2(Math.pow(3, diffLv-1));
				}
				if(diffLv==5)
				{
					if(battleCount!=0)
					{
						if(randDiff<=5&&randDiff>1)
						{
							p2.fullStats2(Math.pow(3, diffLv-2));
							p2.setDifficulty("Extreme");
						}
						else if(randDiff==1)
						{
							p2.fullStats2(Math.pow(3, diffLv-3));
							p2.setDifficulty("Strong");
						}
						else
							p2.fullStats2(Math.pow(3, diffLv-1));
					}
					else
						p2.fullStats2(Math.pow(3, diffLv-1));
				}
				p2.fullStats1(Math.pow(1.1, battleCount-1));
				boolean win1=false;
				boolean win2=false;
				int spdCount1=0;
				int spdCount2=0;
				p2.desc();
				while(!win1&&!win2)
				{
					spdCount1+=p1.getSpd();
					spdCount2+=p2.getSpd();
					while((spdCount1>=100||spdCount2>=100)&&p1.getHp()>0&&p2.getHp()>0)
					{
						if(spdCount2>spdCount1&&p2.getHp()>0)
						{
							turnEnemy(p2, p1);
							spdCount2-=100;
							System.out.println("Enter any key to continue");
							char hold=s.next().charAt(0);
						}
						else if(p1.getHp()>0)
						{
							if(chooseDiff!='4')
								turnPlayer(p1, p2);
							else
								turnAdmin(p1, p2);
							spdCount1-=100;
							System.out.println("Enter any key to continue");
							char hold=s.next().charAt(0);
						}
					}
					if(p2.getHp()<1)
						win1=true;
					if(p1.getHp()<1)
						win2=true;
				}
				if(win1)
				{
					System.out.println(p1.getName()+" wins!");
					System.out.println();
					applyUpgrade(p1, chooseUpgrade(p1, bossBattle));
				}
				else
				{
					System.out.println(p2.getName()+" wins!");
					defeat=true;
				}
			}
			if(!defeat)
			{
				p2=new Entity(e8);
				p2.setName("Enemy");
				p2.setDifficulty(difficulty);
				if(diffLv==2)
					p2.fullStats2(3);
				else if(diffLv==3)
					p2.fullStats2(3*4);
				else if(diffLv==4)
					p2.fullStats2(3*4*5);
				else if(diffLv==5)
				{
					p2.setType("Final boss");
					p2.fullStats2(3*4*5*6);
				}
				if(p2.getType().equals("Final boss"))
					p2.setSpd(1500);
				boolean win1=false;
				boolean win2=false;
				int spdCount1=0;
				int spdCount2=0;
				p2.desc();
				while(!win1&&!win2)
				{
					spdCount1+=p1.getSpd();
					spdCount2+=p2.getSpd();
					while((spdCount1>=100||spdCount2>=100)&&p1.getHp()>0&&p2.getHp()>0)
					{
						if(spdCount2>spdCount1&&p2.getHp()>0)
						{
							turnEnemy(p2, p1);
							spdCount2-=100;
							System.out.println("Enter any key to continue");
							char hold=s.next().charAt(0);
						}
						else if(p1.getHp()>0)
						{
							if(chooseDiff!='4')
								turnPlayer(p1, p2);
							else
								turnAdmin(p1, p2);
							spdCount1-=100;
							System.out.println("Enter any key to continue");
							char hold=s.next().charAt(0);
						}
					}
					if(p2.getHp()<1)
						win1=true;
					if(p1.getHp()<1)
						win2=true;
				}
				if(win1)
				{
					System.out.println(p1.getName()+" wins!");
					if(!p2.getName().equals("Final boss"))
					{
						bossBattle=true;
						applyUpgrade(p1, chooseUpgrade(p1, bossBattle));
					}
				}
				else
				{
					System.out.println(p2.getName()+" wins!");
					defeat=true;
				}
			}
			if(defeat)
			{
				System.out.println("Defeat.");
				System.out.println("Better luck next time!");
			}
		}
		if(!defeat)
		{
			System.out.println("Congratulations! You've beaten the game!");
			System.out.println("I hope you've had fun!");
		}
	}
	public static void gameMode4(Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8)
	{//Initiates game mode 4 (enemy vs enemy)
		System.out.println("A random match-up or a specific match-up?");
		System.out.println("1. A random match-up");
		System.out.println("2. A specific match-up");
		char matchUp=s.next().charAt(0);
		while(matchUp!='1'&&matchUp!='2')
		{
			System.out.println("Enter a valid input");
			matchUp=s.next().charAt(0);
		}
		Entity p1=new Entity();
		Entity p2=new Entity();
		if(matchUp=='1')
		{
			p1=new Entity(chooseERand(p1,e1,e2,e3,e4,e5,e6,e7,e8,'4'));
			p2=new Entity(chooseERand(p2,e1,e2,e3,e4,e5,e6,e7,e8,'4'));
		}
		else
		{
			p1=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'4'));
			p2=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'4'));
		}
		p1.setName("Enemy1");
		p2.setName("Enemy2");
		boolean win1=false;
		boolean win2=false;
		int spdCount1=0;
		int spdCount2=0;
		p1.desc();
		p2.desc();
		System.out.println("Enter any key to continue");
		char hold=s.next().charAt(0);
		while(!win1&&!win2)
		{
			spdCount1+=p1.getSpd();
			spdCount2+=p2.getSpd();
			while((spdCount1>=100||spdCount2>=100)&&p1.getHp()>0&&p2.getHp()>0)
			{
				if(spdCount2>spdCount1&&p2.getHp()>0)
				{
					turnEnemy(p2, p1);
					spdCount2-=100;
					System.out.println("Enter any key to continue");
					hold=s.next().charAt(0);
				}
				else if(p1.getHp()>0)
				{
					turnEnemy(p1, p2);
					spdCount1-=100;
					System.out.println("Enter any key to continue");
					hold=s.next().charAt(0);
				}
			}
			if(p2.getHp()<1)
				win1=true;
			if(p1.getHp()<1)
				win2=true;
		}
		if(win1)
			System.out.println(p1.getName()+" wins!");
		else
			System.out.println(p2.getName()+" wins!");
	}
	public static void gameMode5(Entity e1, Entity e2, Entity e3, Entity e4, Entity e5, Entity e6, Entity e7, Entity e8)
	{//Initiates game mode 5 (1000 battles of enemy vs enemy)
		System.out.println("Random match-ups or a specific match-up?");
		System.out.println("1. Random match-ups");
		System.out.println("2. A specific match-up");
		char matchUp=s.next().charAt(0);
		while(matchUp!='1'&&matchUp!='2')
		{
			System.out.println("Enter a valid input");
			matchUp=s.next().charAt(0);
		}
		Entity p1=new Entity();
		Entity p2=new Entity();
		Entity s1=new Entity();
		Entity s2=new Entity();
		if(matchUp=='2')
		{
			p1=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'5'));
			p2=new Entity(chooseE(p1,e1,e2,e3,e4,e5,e6,e7,e8,'5'));
			s1=new Entity(p1);
			s2=new Entity(p2);
		}
		int count1=0;
		int count2=0;
		int count3=0;
		int count4=0;
		int count5=0;
		int count6=0;
		int count7=0;
		int count8=0;
		int countWin1=0;
		int countWin2=0;
		int countWin3=0;
		int countWin4=0;
		int countWin5=0;
		int countWin6=0;
		int countWin7=0;
		int countWin8=0;
		for(int i=1;i<=1000;i++)
		{
			if(matchUp=='1')
			{
				p1=new Entity(chooseERand(p1,e1,e2,e3,e4,e5,e6,e7,e8,'5'));
				p2=new Entity(chooseERand(p2,e1,e2,e3,e4,e5,e6,e7,e8,'5'));
			}
			else
			{
				p1=new Entity(s1);
				p2=new Entity(s2);
			}
			p1.setName("Enemy1");
			p2.setName("Enemy2");
			boolean win1=false;
			boolean win2=false;
			int spdCount1=0;
			int spdCount2=0;
			p1.desc();
			p2.desc();
			if(p1.getType()=="All Rounder"&&p2.getType()!="All Rounder")
				count1++;
			if(p1.getType()=="Tank"&&p2.getType()!="Tank")
				count2++;
			if(p1.getType()=="Assassin"&&p2.getType()!="Assassin")
				count3++;
			if(p1.getType()=="Speedster"&&p2.getType()!="Speedster")
				count4++;
			if(p1.getType()=="Heavy"&&p2.getType()!="Heavy")
				count5++;
			if(p1.getType()=="Crit Hitter"&&p2.getType()!="Crit Hitter")
				count6++;
			if(p1.getType()=="Health Stealer"&&p2.getType()!="Health Stealer")
				count7++;
			if(p1.getType()=="Boss"&&p2.getType()!="Boss")
				count8++;
			if(p2.getType()=="All Rounder"&&p1.getType()!="All Rounder")
				count1++;
			if(p2.getType()=="Tank"&&p1.getType()!="Tank")
				count2++;
			if(p2.getType()=="Assassin"&&p1.getType()!="Assassin")
				count3++;
			if(p2.getType()=="Speedster"&&p1.getType()!="Speedster")
				count4++;
			if(p2.getType()=="Heavy"&&p1.getType()!="Heavy")
				count5++;
			if(p2.getType()=="Crit Hitter"&&p1.getType()!="Crit Hitter")
				count6++;
			if(p1.getType()=="Health Stealer"&&p2.getType()!="Health Stealer")
				count7++;
			if(p2.getType()=="Boss"&&p1.getType()!="Boss")
				count8++;
			while(!win1&&!win2)
			{
				spdCount1+=p1.getSpd();
				spdCount2+=p2.getSpd();
				while((spdCount1>=100||spdCount2>=100)&&p1.getHp()>0&&p2.getHp()>0)
				{
					if(spdCount2>spdCount1&&p2.getHp()>0)
					{
						turnEnemy(p2, p1);
						spdCount2-=100;
					}
					else if(p1.getHp()>0)
					{
						turnEnemy(p1, p2);
						spdCount1-=100;
					}
				}
				if(p2.getHp()<1)
				{
					win1=true;
					if(p1.getType()=="All Rounder"&&p2.getType()!="All Rounder")
						countWin1++;
					if(p1.getType()=="Tank"&&p2.getType()!="Tank")
						countWin2++;
					if(p1.getType()=="Assassin"&&p2.getType()!="Assassin")
						countWin3++;
					if(p1.getType()=="Speedster"&&p2.getType()!="Speedster")
						countWin4++;
					if(p1.getType()=="Heavy"&&p2.getType()!="Heavy")
						countWin5++;
					if(p1.getType()=="Crit Hitter"&&p2.getType()!="Crit Hitter")
						countWin6++;
					if(p1.getType()=="Health Stealer"&&p2.getType()!="Health Stealer")
						countWin7++;
					if(p1.getType()=="Boss"&&p2.getType()!="Boss")
						countWin8++;
				}
				if(p1.getHp()<1)
				{
					win2=true;
					if(p2.getType()=="All Rounder"&&p1.getType()!="All Rounder")
						countWin1++;
					if(p2.getType()=="Tank"&&p1.getType()!="Tank")
						countWin2++;
					if(p2.getType()=="Assassin"&&p1.getType()!="Assassin")
						countWin3++;
					if(p2.getType()=="Speedster"&&p1.getType()!="Speedster")
						countWin4++;
					if(p2.getType()=="Heavy"&&p1.getType()!="Heavy")
						countWin5++;
					if(p2.getType()=="Crit Hitter"&&p1.getType()!="Crit Hitter")
						countWin6++;
					if(p2.getType()=="Health Stealer"&&p1.getType()!="Health Stealer")
						countWin7++;
					if(p2.getType()=="Boss"&&p1.getType()!="Boss")
						countWin8++;
				}
			}
			if(win1)
				System.out.println(p1.getName()+" wins!");
			else
				System.out.println(p2.getName()+" wins!");
		}
		double winRate1=(((double)countWin1)/((double)count1)*100);
		double winRate2=(((double)countWin2)/((double)count2)*100);
		double winRate3=(((double)countWin3)/((double)count3)*100);
		double winRate4=(((double)countWin4)/((double)count4)*100);
		double winRate5=(((double)countWin5)/((double)count5)*100);
		double winRate6=(((double)countWin6)/((double)count6)*100);
		double winRate7=(((double)countWin7)/((double)count7)*100);
		double winRate8=(((double)countWin8)/((double)count8)*100);
		System.out.println("All Rounder");
		System.out.println("Count: "+count1);
		System.out.println("Win count: "+countWin1);
		System.out.println("Win rate: "+winRate1+"%");
		System.out.println();
		System.out.println("Tank");
		System.out.println("Count: "+count2);
		System.out.println("Win count: "+countWin2);
		System.out.println("Win rate: "+winRate2+"%");
		System.out.println();
		System.out.println("Assassin");
		System.out.println("Count: "+count3);
		System.out.println("Win count: "+countWin3);
		System.out.println("Win rate: "+winRate3+"%");
		System.out.println();
		System.out.println("Speedster");
		System.out.println("Count: "+count4);
		System.out.println("Win count: "+countWin4);
		System.out.println("Win rate: "+winRate4+"%");
		System.out.println();
		System.out.println("Heavy");
		System.out.println("Count: "+count5);
		System.out.println("Win count: "+countWin5);
		System.out.println("Win rate: "+winRate5+"%");
		System.out.println();
		System.out.println("Crit hitter");
		System.out.println("Count: "+count6);
		System.out.println("Win count: "+countWin6);
		System.out.println("Win rate: "+winRate6+"%");
		System.out.println();
		System.out.println("Health Stealer");
		System.out.println("Count: "+count7);
		System.out.println("Win count: "+countWin7);
		System.out.println("Win rate: "+winRate7+"%");
		System.out.println();
		System.out.println("Boss");
		System.out.println("Count: "+count8);
		System.out.println("Win count: "+countWin8);
		System.out.println("Win rate: "+winRate8+"%");
		System.out.println();
	}
	public static void main(String[]args)
	{
		Entity e1=new Entity("E1", "All Rounder", "n/a", 10, 10, 150, 75, 75, 1, 1, 0, 7, 9);
		Entity e2=new Entity("E2", "Tank", "n/a", 7, 20, 200, 75, 85, 1, 1, 0, 6, 7);
		Entity e3=new Entity("E3", "Assassin", "n/a", 15, 7, 100, 80, 70, 1, 1, 0, 7, 10);
		Entity e4=new Entity("E4", "Speedster", "n/a", 7, 8, 130, 100, 90, 1, 1, 0, 10, 7);
		Entity e5=new Entity("E5", "Heavy", "n/a", 15, 15, 180, 50, 60, 1, 1, 0, 7, 12);
		Entity e6=new Entity("E6", "Crit Hitter", "n/a", 12, 5, 150, 70, 80, 3, 1, 0, 6, 10);
		Entity e7=new Entity("E7", "Health Stealer", "n/a", 12, 8, 170, 75, 70, 1, 1, 20, 5, 10);
		Entity e8=new Entity("E8", "Boss", "n/a", 20, 20, 250, 100, 90, 2, 2, 0, 10, 12);
		System.out.println("Enter the number of the game mode you'd like to play");
		System.out.println("1. Player vs Player");
		System.out.println("2. Player vs computer (short game)");
		System.out.println("3. Player vs computer (full game)");
		System.out.println("4. Enemy vs Enemy (simulation)");
		System.out.println("5. Enemy vs Enemy (long simulation)");
		char gameMode=s.next().charAt(0);
		while(gameMode!='1'&&gameMode!='2'&&gameMode!='3'&&gameMode!='4'&&gameMode!='5')
		{
			System.out.println("Enter a valid input");
			gameMode=s.next().charAt(0);
		}
		if(gameMode=='1')
			gameMode1(e1,e2,e3,e4,e5,e6,e7,e8);
		if(gameMode=='2')
			gameMode2(e1,e2,e3,e4,e5,e6,e7,e8);
		if(gameMode=='3')
			gameMode3(e1,e2,e3,e4,e5,e6,e7,e8);
		if(gameMode=='4')
			gameMode4(e1,e2,e3,e4,e5,e6,e7,e8);
		if(gameMode=='5')
			gameMode5(e1,e2,e3,e4,e5,e6,e7,e8);
	}
}
