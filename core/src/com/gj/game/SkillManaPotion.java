package com.gj.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;

public class SkillManaPotion implements Skill{


	int Base_Mana = 5;
	
	@Override
	public boolean OnUse(Room r, Entity user,Dungeon.DIRECTION dir) {
		
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		
		if(usr_pos != null && usr_com != null && user instanceof Player){
			Player plyr = (Player)user;
			if(plyr.num_mana_pots <= 0){
				UI.LogString("You have no mana potions left.");
				return false;}
			plyr.num_mana_pots--;
			Random rand= new Random();
			int randHeal = Math.abs(rand.nextInt())%6;
			usr_com.Mana += Base_Mana+randHeal;
			if(usr_com.Mana > usr_com.MaxMana){
				usr_com.Mana = usr_com.MaxMana;
				UI.LogString("You restored your all your mana with a magic potion.");
			}
			else{
				UI.LogString("You restored "+ (Base_Mana+randHeal) +" mana with a magic potion.");
			}
			return true;
		}
		return false;
	}
	
}