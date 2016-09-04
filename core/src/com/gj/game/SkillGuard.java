package com.gj.game;

import com.badlogic.ashley.core.Entity;

public class SkillGuard implements Skill {

	
	@Override
	public boolean OnUse(Room r, Entity user,Dungeon.DIRECTION dir) {
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		if(usr_pos != null && usr_com != null){
			for(Entity e:r.Entities){
				PositionComponent tar_pos = e.getComponent(PositionComponent.class);
				CombatComponent tar_com = e.getComponent(CombatComponent.class);
				if(tar_pos != null && tar_com != null){
					return true;
				}	
			}
		}
		return false;
	}
	
}
