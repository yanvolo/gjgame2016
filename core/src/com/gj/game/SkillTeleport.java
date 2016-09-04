package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.gj.game.Dungeon.DIRECTION;

public class SkillTeleport implements Skill{

	int ManaCost = 3;
	
	@Override
	public boolean OnUse(Room r, Entity user, DIRECTION dir) {
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		if(usr_pos != null && usr_com != null){
			
			if(user instanceof Player){
				if(usr_com.Mana > ManaCost){usr_com.Mana-=ManaCost;UI.LogString("You cast a teleport spell");}
				else{UI.LogString("You were too low on mana to cast a teleport spell.");return false;}
			}
			
			if(dir == Dungeon.DIRECTION.UP){return usr_pos.Move(user, 0, -2, r);}
			if(dir == Dungeon.DIRECTION.DOWN){return usr_pos.Move(user, 0, 2, r);}
			if(dir == Dungeon.DIRECTION.LEFT){return usr_pos.Move(user, -2, 0, r);}
			if(dir == Dungeon.DIRECTION.RIGHT){return usr_pos.Move(user, 2, 0, r);}

		}
		return false;
	}
}
