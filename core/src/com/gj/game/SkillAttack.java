package com.gj.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;

public class SkillAttack implements Skill{
	
	@Override
	public boolean OnUse(Room r, Entity user,Dungeon.DIRECTION dir) {
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		if(usr_pos != null && usr_com != null){
			int tar_x = usr_pos.x,tar_y = usr_pos.y;
			if(dir == Dungeon.DIRECTION.UP){tar_y--;}
			if(dir == Dungeon.DIRECTION.DOWN){tar_y++;}
			if(dir == Dungeon.DIRECTION.LEFT){tar_x--;}
			if(dir == Dungeon.DIRECTION.RIGHT){tar_x++;}

			
			
			for(Entity e:r.Entities){
				PositionComponent tar_pos = e.getComponent(PositionComponent.class);
				CombatComponent tar_com = e.getComponent(CombatComponent.class);
				if(tar_pos != null && tar_com != null){
					if(tar_pos.x == tar_x && tar_pos.y == tar_y){
						if(user instanceof Player){
							UI.LogString("You swing your sword at the "+tar_com.Name);
						}
						else{
							UI.LogString(usr_com.Name+" attacks "+tar_com.Name);
						}
						
						Random rand= new Random();
						int d20 = 1+Math.abs(rand.nextInt())%20;
						int dmg = usr_com.AttackPower;
						if(d20 == 20){
							dmg += 5;
							UI.LogString("Critical hit!");
						}
						else if(d20 > 15){
							dmg+=1;
						}
						else if(d20 < 5){
							dmg-=1;
						}
						
						tar_com.DoDamage(e,r,dmg,false);
						
						return true;}
				}	
			}
		}
		return false;
	}


}
