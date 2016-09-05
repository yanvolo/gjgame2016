package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class Player extends Entity{
	public static Texture playerSprite;
	
	public int selected_skill;
	public int num_hp_pots;
	public int num_mana_pots;
	public int num_loot;

	public Player(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(playerSprite,"P"));
		CombatComponent combat = new CombatComponent("you",10,10,5,0,1,0);
		
		combat.AttackSkills.add(new SkillAttack());//Attack-	1
		combat.AttackSkills.add(new SkillGuard());//Guard-		2
		combat.AttackSkills.add(new SkillFireball());//Fireball-	3
		combat.AttackSkills.add(new SkillTeleport());//Teleport-	4
		combat.AttackSkills.add(new SkillHealthPotion());//Health pot	6
		combat.AttackSkills.add(new SkillManaPotion());//Mana pot	6
		combat.AttackSkills.add(new SkillAttack());//Pickaxe	7
		
		this.add(combat);
		num_hp_pots =1;
		num_mana_pots =1;
		num_loot=0;
	}
	
	
	public void CheckChangeRoom(Room r,Dungeon d){
		PositionComponent p = getComponent(PositionComponent.class);
		if(p != null){
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_DOWN){
				d.Move_Room(DIRECTION.DOWN);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_UP){
				d.Move_Room(DIRECTION.UP);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_LEFT){
				d.Move_Room(DIRECTION.LEFT);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_RIGHT){
				d.Move_Room(DIRECTION.RIGHT);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_LAST){
				//if(not boss)
				d.Set_Room(4, 3);
				//else d.Set_Room(4, 4);
			}
		}
	}
}
