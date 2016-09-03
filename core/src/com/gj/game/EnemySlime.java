package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class EnemySlime extends Entity{

	public static Texture slimeSprites;
	
	public EnemySlime(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(slimeSprites,'s'));
		hp = 12;
	}
	
	public int x,y;
	public int hp;
	
	
	
}
