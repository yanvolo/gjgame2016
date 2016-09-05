package com.gj.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;

public class SkillFireball implements Skill{
	
	int ManaCost = 1;
	
	@Override
	public boolean OnUse(Room r, Entity user,Dungeon.DIRECTION dir) {
		PositionComponent usr_pos = user.getComponent(PositionComponent.class);
		CombatComponent usr_com = user.getComponent(CombatComponent.class);
		
		
		if(user instanceof Player){
			if(usr_com.Mana > ManaCost){usr_com.Mana-=ManaCost;UI.LogString("You cast a fire spell...");}
			else{UI.LogString("You were too low on mana to cast a fire spell.");return false;}
		}
		

		Random rand = new Random();
		if(usr_pos != null && usr_com != null){
			for(Entity e:r.Entities){
				if(e == user)continue;
				PositionComponent tar_pos = e.getComponent(PositionComponent.class);
				CombatComponent tar_com = e.getComponent(CombatComponent.class);
				if(tar_pos != null && tar_com != null){
					if(dir == Dungeon.DIRECTION.UP){
						if(usr_pos.x == tar_pos.x && usr_pos.y > tar_pos.y){
							for(int y=usr_pos.y;y!=tar_pos.y;y--){
								if(r.Level[y*r.Width +usr_pos.x].type == Tile.TILE_TYPE.WALL){UI.LogString("The spell hit a wall!");return true;}
							}
							if(user instanceof Player && Questionnaire.instance.isSelfDistrustful()){
								if(rand.nextInt(2)==0){
									UI.LogString("You lack faith in yourself. MISS");
									return true;}
							}
							tar_com.DoDamage(e,r,8,true);
							return true;
						}
					}
					if(dir == Dungeon.DIRECTION.DOWN){
						if(usr_pos.x == tar_pos.x && usr_pos.y < tar_pos.y){
							for(int y=usr_pos.y;y!=tar_pos.y;y++){
								if(r.Level[y*r.Width +usr_pos.x].type == Tile.TILE_TYPE.WALL){UI.LogString("The spell hit a wall!");return true;}
							}
							if(user instanceof Player && Questionnaire.instance.isSelfDistrustful()){
								if(rand.nextInt(2)==0){
									UI.LogString("You lack faith in yourself. MISS");
									return true;}
							}
							tar_com.DoDamage(e,r,8,true);
							return true;
						}
					}
					if(dir == Dungeon.DIRECTION.RIGHT){
						if(usr_pos.y == tar_pos.y && usr_pos.x < tar_pos.x){
							for(int x=usr_pos.x;x!=tar_pos.x;x++){
								if(r.Level[usr_pos.y*r.Width +x].type == Tile.TILE_TYPE.WALL){UI.LogString("The spell hit a wall!");return true;}
							}
							if(user instanceof Player && Questionnaire.instance.isSelfDistrustful()){
								if(rand.nextInt(2)==0){
									UI.LogString("You lack faith in yourself. MISS");
									return true;}
							}
							tar_com.DoDamage(e,r,8,true);
							return true;
						}
					}
					if(dir == Dungeon.DIRECTION.LEFT){
						if(usr_pos.y == tar_pos.y && usr_pos.x > tar_pos.x){
							for(int x=usr_pos.x;x!=tar_pos.x;x--){
								if(r.Level[usr_pos.y*r.Width +x].type == Tile.TILE_TYPE.WALL){UI.LogString("The spell hit a wall!");return true;}
							}
							if(user instanceof Player && Questionnaire.instance.isSelfDistrustful()){
								if(rand.nextInt(2)==0){
									UI.LogString("You lack faith in yourself. MISS");
									return true;}
							}
							tar_com.DoDamage(e,r,8,true);
							return true;
						}
					}
				}	
			}
			UI.LogString("The spell hit a wall!");
		}
		return false;
	}
}
