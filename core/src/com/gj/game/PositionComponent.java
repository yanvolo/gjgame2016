package com.gj.game;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.gj.game.Dungeon.DIRECTION;

public class PositionComponent implements Component{

	
	int x;
	int y;
	public PositionComponent(int px,int py){
		x=px;
		y=py;
	}

	public boolean Move(Entity ent,DIRECTION d,Room r){
		int newx=x;
		int newy=y;
		
		if(d == DIRECTION.UP){newy--;}
		if(d == DIRECTION.DOWN){newy++;}
		if(d == DIRECTION.LEFT){newx--;}
		if(d == DIRECTION.RIGHT){newx++;}
		
		if(newy < 0 || newx < 0 || newy >= r.Height  || newx >= r.Width){return false;}//dont move out of bounds
		if(r.Level[newy*r.Width+newx].type == Tile.TILE_TYPE.WALL){return false;}

		for(Entity e:r.Entities){
			PositionComponent cmp = e.getComponent(PositionComponent.class);
			if(cmp != null && newx == cmp.x && newy == cmp.y){
				if(e instanceof EnemyBat)return false;
				if(e instanceof EnemyRat)return false;
				if(e instanceof EnemyArcher)return false;
				if(e instanceof EnemyWarrior)return false;
				if(e instanceof ItemChest)return true;
				if(e instanceof ItemSign && ent instanceof Player){
					UI.LogString(ItemSign.room_texts[((ItemSign)e).signNo]);
				}
			}
		}
		
		x=newx;y=newy;
		return true;
	}
	
	
}
