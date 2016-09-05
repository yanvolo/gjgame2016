package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;

public class ItemGameWin extends Entity{

	public static Texture gameWinSprite;
	public int signNo;
	public ItemGameWin(int px,int py){
		this.add(new DrawComponent(gameWinSprite,"!"));
		this.add(new PositionComponent(px,py));
	}
	
}
