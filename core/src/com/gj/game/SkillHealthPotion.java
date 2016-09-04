package com.gj.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;

public class SkillHealthPotion implements Skill{


	int Base_Healing = 5;
	
	@Override
	public boolean OnUse(Room r, Entity user,Dungeon.DIRECTION dir) {
		
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		
		if(usr_pos != null && usr_com != null && user instanceof Player){
			Player plyr = (Player)user;
			if(plyr.num_hp_pots <= 0){
				UI.LogString("You have no healing potions left.");
				return false;}
			plyr.num_hp_pots--;
			Random rand= new Random();
			int randHeal = Math.abs(rand.nextInt())%6;
			usr_com.HP += Base_Healing+randHeal;
			if(usr_com.HP > usr_com.MaxHP){
				usr_com.HP = usr_com.MaxHP;
				UI.LogString("You restored your all your health with a healing potion.");
			}
			else{
				UI.LogString("You restored "+ (Base_Healing+randHeal) +" health with a healing potion.");
			}
			return true;
		}
		return false;
	}
	
}
