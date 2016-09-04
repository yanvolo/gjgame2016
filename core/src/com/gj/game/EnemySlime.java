package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class EnemySlime extends Entity{

	public static Texture slimeSprite;
	
	public EnemySlime(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(slimeSprite,"s"));
		CombatComponent combat = new CombatComponent("slime",12,0,2,0,1,4);
		combat.AttackSkills.add(new SkillAttack());
		this.add(combat);
	}
	
	
}
