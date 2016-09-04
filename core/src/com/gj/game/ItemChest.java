package com.gj.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.ashley.core.Entity;

public class ItemChest extends Entity{
	public static Texture chestSprite;
	public ItemChest(int px,int py){
		//TODO:Loot
		this.add(new DrawComponent(chestSprite,"$"));
		this.add(new PositionComponent(px,py));
	}
}
