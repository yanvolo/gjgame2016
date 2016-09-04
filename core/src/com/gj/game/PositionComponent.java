package com.gj.game;
import com.badlogic.ashley.core.Component;
import com.gj.game.Dungeon.DIRECTION;

public class PositionComponent implements Component{

	
	int x;
	int y;
	public PositionComponent(int px,int py){
		x=px;
		y=py;
	}

	public boolean Move(DIRECTION d,Room r){
		int newx=x;
		int newy=y;
		
		if(d == DIRECTION.UP){newy--;}
		if(d == DIRECTION.DOWN){newy++;}
		if(d == DIRECTION.LEFT){newx--;}
		if(d == DIRECTION.RIGHT){newx++;}
		
		if(newy < 0 || newx < 0 || newy >= r.Height  || newx >= r.Width){return false;}//dont move out of bounds
		if(r.Level[newy*r.Width+newx].type == Tile.TILE_TYPE.WALL){return false;}
		else if(r.Level[newy*r.Width+newx].type == Tile.TILE_TYPE.FLOOR){x=newx;y=newy;return true;}
		x=newx;y=newy;
		return true;
	}
	
	
}
