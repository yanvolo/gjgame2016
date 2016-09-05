package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class EnemyArcher extends Entity{

	public static Texture archerSprite;
	
	public EnemyArcher(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(archerSprite,"A"));
		CombatComponent combat = new CombatComponent("archer",5,0,1,0,1,10);
		combat.AttackSkills.add(new SkillArrow());
		this.add(combat);
	}
	
	public void OnTurn(Room r){
		PositionComponent p = this.getComponent(PositionComponent.class);
		CombatComponent c = this.getComponent(CombatComponent.class);
		PositionComponent player_pos = r.Player.getComponent(PositionComponent.class);
		if(player_pos.x == p.x){
			if(p.y > player_pos.y){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.UP);return;}
			if(p.y < player_pos.y){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.DOWN);return;}
		}
		else if(player_pos.y == p.y){
			if(p.x > player_pos.x){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.LEFT);return;}
			if(p.x < player_pos.x){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.RIGHT);return;}
		}
		
	}
	
}
