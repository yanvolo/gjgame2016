package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Main.DIRECTION;

public class Player extends Entity{
public static Texture playerSprite;
	
	public Player(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(playerSprite,"P"));
	}
	
	
	public void CheckChangeRoom(Room r){
		PositionComponent p = getComponent(PositionComponent.class);
		if(p != null){
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_DOWN){
				Main.Move_Room(DIRECTION.DOWN);
			}
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_UP){
				Main.Move_Room(DIRECTION.UP);
			}
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_LEFT){
				Main.Move_Room(DIRECTION.LEFT);
			}
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_RIGHT){
				Main.Move_Room(DIRECTION.RIGHT);
			}
			
			
		}
		
		
	}
	
	
}
