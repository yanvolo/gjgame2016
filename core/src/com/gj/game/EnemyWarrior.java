package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class EnemyWarrior extends Entity{

	public static Texture warriorSprite;
	
	public EnemyWarrior(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(warriorSprite,"W"));
		CombatComponent combat = new CombatComponent("warrior",30,0,3,3,1,12);
		combat.AttackSkills.add(new SkillAttack());
		this.add(combat);
	}
	public void OnTurn(Room r){
		PositionComponent p = this.getComponent(PositionComponent.class);
		CombatComponent c = this.getComponent(CombatComponent.class);
		PositionComponent player_pos = r.Player.getComponent(PositionComponent.class);
		if(player_pos.x == p.x){
			if(p.y+1 == player_pos.y){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.DOWN);return;}
			if(p.y-1 == player_pos.y){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.UP);return;}
		}
		else if(player_pos.y == p.y){
			if(p.x+1 == player_pos.x){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.RIGHT);return;}
			if(p.x-1 == player_pos.x){c.AttackSkills.get(0).OnUse(r, this, DIRECTION.LEFT);return;}
		}
		
		if(p.y > player_pos.y){p.Move(this, DIRECTION.UP, r);return;}
		if(p.y < player_pos.y){p.Move(this, DIRECTION.DOWN, r);return;}
		if(p.x > player_pos.x){p.Move(this, DIRECTION.LEFT, r);return;}
		if(p.x < player_pos.x){p.Move(this, DIRECTION.RIGHT, r);return;}
		
	}

}
