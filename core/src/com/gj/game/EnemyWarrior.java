package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class EnemyWarrior extends Entity{

	public static Texture warriorSprite;
	
	public EnemyWarrior(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(warriorSprite,"W"));
		CombatComponent combat = new CombatComponent("warrior",5,0,1,0,1,2);
		combat.AttackSkills.add(new SkillAttack());
		this.add(combat);
	}
	public void OnTurn(Room r){
		
		
	}

}
