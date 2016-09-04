package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class EnemyBat extends Entity{

	public static Texture batSprite;
	
	public EnemyBat(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(batSprite,"b"));
		CombatComponent combat = new CombatComponent("bat",5,0,1,0,1,2);
		combat.AttackSkills.add(new SkillAttack());
		this.add(combat);
	}

	public void OnTurn(Room r){
		
		
	}
}
