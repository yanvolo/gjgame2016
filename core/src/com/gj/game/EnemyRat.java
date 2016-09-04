package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class EnemyRat extends Entity{

	public static Texture ratSprite;
	
	public EnemyRat(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(ratSprite,"R"));
		CombatComponent combat = new CombatComponent("rat",5,0,1,0,1,2);
		combat.AttackSkills.add(new SkillAttack());
		this.add(combat);
	}
}
