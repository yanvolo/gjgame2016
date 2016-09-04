package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class Player extends Entity{
public static Texture playerSprite;
	
	public Player(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(playerSprite,"P"));
	}
	
	
	public void CheckChangeRoom(Room r,Dungeon d){
		PositionComponent p = getComponent(PositionComponent.class);
		if(p != null){
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_DOWN){
				d.Move_Room(DIRECTION.DOWN);
			}
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_UP){
				d.Move_Room(DIRECTION.UP);
			}
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_LEFT){
				d.Move_Room(DIRECTION.LEFT);
			}
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_RIGHT){
				d.Move_Room(DIRECTION.RIGHT);
			}
		}
	}
}
