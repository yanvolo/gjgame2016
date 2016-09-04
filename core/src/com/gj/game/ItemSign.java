package com.gj.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.ashley.core.Entity;

public class ItemSign extends Entity{
	public static String[] room_texts;
	public static Texture signSprite;
	public int signNo;
	public ItemSign(int px,int py,int num){
		signNo = num;
		this.add(new DrawComponent(signSprite,"4"));
		this.add(new PositionComponent(px,py));
	}
}
