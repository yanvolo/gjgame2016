package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class EnemyArcher extends Entity{

	public static Texture archerSprite;
	
	public EnemyArcher(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(archerSprite,"A"));
		CombatComponent combat = new CombatComponent("archer",5,0,1,0,1,2);
		combat.AttackSkills.add(new SkillAttack());
		this.add(combat);
	}
}
