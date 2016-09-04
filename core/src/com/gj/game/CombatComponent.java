package com.gj.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.gj.game.Main.GameMode;

public class CombatComponent implements Component{

	public String Name;
	public int LV,XP;
	public int HP,MaxHP;
	public int Mana,MaxMana;
	public int AttackPower;
	public int Armor;
	
	public ArrayList<Skill> AttackSkills;
	
	public CombatComponent(String nm,int hp,int mn,int atk,int amr,int lv, int xp){
		Name = nm;
		HP = hp;
		MaxHP = hp;
		Mana = mn;
		MaxMana= mn;
		AttackPower = atk;
		Armor = amr;
		LV = lv;
		XP = xp;
		AttackSkills = new ArrayList<Skill>();
	}
	
	public void DoDamage(Entity me,Room r,int dmg,boolean magical){
		if(magical == false){
			if(dmg > Armor){
				HP -= (dmg-Armor);
				if(me instanceof Player){
					UI.LogString("You take " +dmg+" damage");
				}
				else{
					UI.LogString(Name+" takes " +dmg+" damage");
				}
				
				if(HP < 0){Die(me,r);}
			}
			else{
				if(me instanceof Player){
					UI.LogString("You block the hit");
				}
				else{
				UI.LogString(Name+" blocks the hit");
				}
			}
		}
		else{
			HP -= dmg;
			if(me instanceof Player){
				UI.LogString("You take " +dmg+" damage");
			}
			else{
				UI.LogString(Name+" takes " +dmg+" damage");
			}
			
			if(HP < 0){Die(me,r);}
		}
	}
	
	public void Die(Entity e,Room r){
		if(e instanceof Player){
			UI.LogString("You have died. Press R to restart.");
			Main.game_mode = GameMode.DEATH;
		}
		else{
			r.Entities.remove(e);
			UI.LogString(Name + " has died! You gain "+this.XP+" experience.");
			CombatComponent c = r.Player.getComponent(CombatComponent.class);
			c.XP += this.XP;
			if(c.XP > c.LV*5){
				c.LV++;
				Random rand= new Random();
				
				int php = Math.abs(rand.nextInt())%5+1;
				c.HP+= php;
				c.MaxHP+= php;
				c.AttackPower += Math.abs(rand.nextInt())%3+1;
				c.Armor += Math.abs(rand.nextInt())%2;
				
				String level_title = "";
				if(c.LV == 2){level_title="2nd";}
				else if(c.LV == 3){level_title="3rd";}
				else{level_title= c.LV+"th";}
				
				UI.LogString("Level up! You are now "+level_title+" level ");
				UI.LogString("with "+c.AttackPower+" Attack, "+c.Armor+" Armor, and "+c.MaxHP+" max HP.");
				
			}
		}	
	}

}
