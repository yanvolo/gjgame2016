package com.gj.game;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

public class CombatComponent implements Component{

	public String Name;
	public int LV,XP;
	public int HP,MaxHP;
	public int Mana,MaxMana;
	public int AttackPower;
	public int Armor;
	
	public ArrayList<Skill> AttackSkills;
	
	public CombatComponent(String nm,int hp,int mhp,int mn,int mmn,int atk,int amr,int lv, int xp){
		Name = nm;
		HP = hp;
		MaxHP = mhp;
		Mana = mn;
		MaxMana= mmn;
		AttackPower = atk;
		Armor = amr;
		LV = lv;
		XP = xp;
		AttackSkills = new ArrayList<Skill>();
	}
	
	public void Die(Entity e,Room r){
		if(e instanceof Player){
			UI.LogString("You have died. Press R to restart.");
		}
		else{
			r.Entities.remove(e);
			UI.LogString(Name + "has died! You gain "+this.XP+" experience.");
			CombatComponent c = r.Player.getComponent(CombatComponent.class);
			c.XP += this.XP;
			
		}	
	}

}
