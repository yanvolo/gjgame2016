package com.gj.game;

import java.util.Random;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class EnemySlime extends Entity{

	public static Texture slimeSprite;
	public DIRECTION go;
	
	public EnemySlime(int xp,int yp,boolean lr){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(slimeSprite,"s"));
		CombatComponent combat = new CombatComponent("slime",8,0,1,0,1,1);
		combat.AttackSkills.add(new SkillAttack());

		Random rand =new Random();
		if(lr){
			if(rand.nextInt(2)==0){go = DIRECTION.LEFT;}
			else{go = DIRECTION.RIGHT;}
		}
		else{
			if(rand.nextInt(2)==0){go = DIRECTION.UP;}
			else{go = DIRECTION.DOWN;}
		}
		
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
		if(p.Move(this, go, r)==true){return;}
		if(go == DIRECTION.UP){go=DIRECTION.DOWN;}
		else if(go == DIRECTION.DOWN){go=DIRECTION.UP;}
		else if(go == DIRECTION.LEFT){go=DIRECTION.RIGHT;}
		else if(go == DIRECTION.RIGHT){go=DIRECTION.LEFT;}
		p.Move(this, go, r);
		
	}
}
