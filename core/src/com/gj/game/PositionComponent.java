package com.gj.game;
import java.util.Random;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.gj.game.Dungeon.DIRECTION;
import com.gj.game.Main.GameMode;

public class PositionComponent implements Component{

	
	int x;
	int y;
	
	int past_x;
	int past_y;
	
	int future_x;
	int future_y;
	
	public PositionComponent(int px,int py){
		x=px;
		y=py;
		past_x = px;
		past_y = py;
		future_x = px;
		future_y = py;
		MakeFuture();
	}

	public void MakeFuture(){
		future_x = x;
		future_y = y;
		Random r = new Random();
		int f = r.nextInt(4);
		switch(f){
		case 0:future_x++;break;
		case 1:future_y++;break;
		case 2:future_x--;break;
		case 3:future_y--;break;
		default:break;
		}
		
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
				if(e instanceof ItemSign && ent instanceof Player){
					UI.LogString(ItemSign.room_texts[((ItemSign)e).signNo]);
				}
				if(e instanceof ItemGameWin && ent instanceof Player){
					UI.LogString("Congratulations, you win!");
					UI.LogString("Press R to continue");
					Main.game_mode = GameMode.WIN;
				}
				if(e instanceof ItemChest && ent instanceof Player){
					((ItemChest)e).GivePlayerLoot((Player) ent);
				}
			}
		}
		past_x = x;
		past_y = y;
		x=newx;y=newy;
		MakeFuture();
		return true;
	}
	public boolean Move(Entity ent,int dx,int dy,Room r){
		int newx=x+dx;
		int newy=y+dy;
		
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
		
		past_x = x;
		past_y = y;
		x=newx;y=newy;
		MakeFuture();
		return true;
	}
	
	
}
