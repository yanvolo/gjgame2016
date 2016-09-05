package com.gj.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class EnemyRat extends Entity{

	public static Texture ratSprite;
	
	public EnemyRat(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(ratSprite,"R"));
		CombatComponent combat = new CombatComponent("rat",15,0,4,0,1,8);
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
	
		Random rand =new Random();
		if(rand.nextInt(2)==0){
			if(rand.nextInt(2)==0){p.Move(this,DIRECTION.LEFT,r);return;}
			else{p.Move(this,DIRECTION.RIGHT,r);return;}
		}
		else{
			if(rand.nextInt(2)==0){p.Move(this,DIRECTION.UP,r);return;}
			else{p.Move(this,DIRECTION.DOWN,r);return;}
		}
		
	}
}
