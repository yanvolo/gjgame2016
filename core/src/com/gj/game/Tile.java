package com.gj.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

	public static Texture tex_Floor0;
	public static Texture tex_Wall0;
	public static Texture tex_DoorUp;
	public static Texture tex_DoorDown;
	public static Texture tex_DoorLeft;
	public static Texture tex_DoorRight;
	public static Texture tex_FinalDoor;
	
	public enum TILE_TYPE{WALL,FLOOR,DOOR_UP,DOOR_DOWN,DOOR_LEFT,DOOR_RIGHT,DOOR_LAST};
	
	TILE_TYPE	type;
	Texture		draw_tex;
	
	void Draw(SpriteBatch b,int DrawMode,int x, int y){
		if(DrawMode == 0){
			if(draw_tex != null)b.draw(draw_tex, x, y);
		}
		else if(DrawMode ==1){
			switch(type){
				case WALL:{UI.GameFont.draw(b,"#", x, y);break;}
				case DOOR_UP:{UI.GameFont.draw(b,"^", x, y);break;}
				case DOOR_DOWN:{UI.GameFont.draw(b,"V", x, y);break;}
				case DOOR_LEFT:{UI.GameFont.draw(b,"<", x, y);break;}
				case DOOR_RIGHT:{UI.GameFont.draw(b,">", x, y);break;}
				case DOOR_LAST:{UI.GameFont.draw(b,"O", x, y);break;}
				default:break;
			}
		}
	}
}
