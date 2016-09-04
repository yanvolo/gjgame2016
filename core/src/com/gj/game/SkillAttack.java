package com.gj.game;

import com.badlogic.ashley.core.Entity;

public class SkillAttack implements Skill{

	@Override
	public void OnUse(Room r, Entity user,Dungeon.DIRECTION dir) {
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		if(usr_pos != null && usr_com != null){
			int tar_x = usr_pos.x,tar_y = usr_pos.y;
			if(dir == Dungeon.DIRECTION.UP){}
			if(dir == Dungeon.DIRECTION.DOWN){}
			if(dir == Dungeon.DIRECTION.LEFT){}
			if(dir == Dungeon.DIRECTION.RIGHT){}
			
			for(Entity e:r.Entities){
				PositionComponent tar_pos = e.getComponent(PositionComponent.class);
				CombatComponent tar_com = e.getComponent(CombatComponent.class);
				if(tar_pos != null && tar_com != null){
					if(tar_pos.x == tar_x && tar_pos.y == tar_y){
						
						if(usr_com.AttackPower > tar_com.Armor){
							tar_com.HP -= usr_com.AttackPower-tar_com.Armor;
							if(tar_com.HP < 0){tar_com.Die(e,r);}
						}
						
						
					}	
				}	
			}
		}
	}


}
