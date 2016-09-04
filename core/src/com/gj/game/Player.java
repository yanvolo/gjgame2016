package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Dungeon.DIRECTION;

public class Player extends Entity{
public static Texture playerSprite;
	
	public Player(int xp,int yp){
		this.add(new PositionComponent(xp,yp));
		this.add(new DrawComponent(playerSprite,"P"));
		CombatComponent combat = new CombatComponent("you",10,10,5,0,1,0);
		this.add(combat);
	}
	
	
	public void CheckChangeRoom(Room r,Dungeon d){
		PositionComponent p = getComponent(PositionComponent.class);
		if(p != null){
			if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_DOWN){
				d.Move_Room(DIRECTION.DOWN);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_UP){
				d.Move_Room(DIRECTION.UP);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_LEFT){
				d.Move_Room(DIRECTION.LEFT);
			}
			else if(r.Level[p.y*r.Width+p.x].type == Tile.TILE_TYPE.DOOR_RIGHT){
				d.Move_Room(DIRECTION.RIGHT);
			}
		}
	}
}
