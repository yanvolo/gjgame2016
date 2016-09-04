package com.gj.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dungeon {

	public Room current_room;
	public int room_x,room_y;
	public String dungeon_rooms[][];
	
	public enum DIRECTION{UP,DOWN,LEFT,RIGHT};
	
	public void Move_Room(DIRECTION d){
		if(d==DIRECTION.RIGHT){room_x++;}
		if(d==DIRECTION.LEFT){room_x--;}
		if(d==DIRECTION.DOWN){room_y++;}
		if(d==DIRECTION.UP){room_y--;}
		
		Entity player = current_room.Player;
		current_room = new Room(0,16);//Is Cynical?
		current_room.Load("rsc/"+dungeon_rooms[room_y][room_x]);
		current_room.Player = player;
		PositionComponent p= player.getComponent(PositionComponent.class);
		if(p== null)return;
		Tile.TILE_TYPE door =Tile.TILE_TYPE.FLOOR;
		if(d==DIRECTION.RIGHT){
			p.x = 1;
			p.y=0;
			door = Tile.TILE_TYPE.DOOR_LEFT;}
		if(d==DIRECTION.LEFT){
			p.x = -1;
			p.y=0;
			door = Tile.TILE_TYPE.DOOR_RIGHT;}
		if(d==DIRECTION.DOWN){
			p.x=0;
			p.y = 1;
			door = Tile.TILE_TYPE.DOOR_UP;}
		if(d==DIRECTION.UP){
			p.x=0;
			p.y = -1;
			door = Tile.TILE_TYPE.DOOR_DOWN;}
		
		for(int i=0;i<current_room.Height;i++){
			for(int j=0;j<current_room.Width;j++){
				if(current_room.Level[i*current_room.Width+j].type==door){
					p.x += j;//*current_room.TILE_SIZE;
					p.y += i;//*current_room.TILE_SIZE;
				}
			}
		}
	}
	
	
	public Dungeon(){
		Player.playerSprite = new Texture("rsc/player.png");
		EnemySlime.slimeSprites = new Texture("rsc/slime.png");
		Tile.tex_DoorDown = new Texture("rsc/doorDown.png");
		Tile.tex_DoorLeft = new Texture("rsc/doorLeft.png");
		Tile.tex_DoorRight = new Texture("rsc/doorRight.png");
		Tile.tex_DoorUp = new Texture("rsc/doorUp.png");
		Tile.tex_FinalDoor = new Texture("rsc/doorLast.png");
		Tile.tex_Floor0 = new Texture("rsc/floor0.png");
		Tile.tex_Wall0 = new Texture("rsc/wall0.png");		
		
		room_x = 2;
		room_y = 4;
		
		dungeon_rooms = new String[][]{
			{"r11.txt"	,"r12.txt"	,""			,"r14.txt"	,""},
			{"r6.txt"	,"r7.txt"	,"r8.txt"	,"r9.txt"	,"r10.txt"},
			{""			,"r5.txt"	,"r3.txt"	,"r4.txt"	,""},
			{""			,""			,"r2.txt"	,""			,""},
			{""			,""			,"r1.txt"	,""			,""}
		};
	}
	
	public void LoadPostQuestionare(){
		current_room = new Room(0,16);
		current_room.Player = new Player(8,8);
		current_room.Load("rsc/"+dungeon_rooms[room_y][room_x]);

		/*change rules, dungeon rooms based on values.
		 * 
		 * */
		
	}
	
	public void Update(){
		PositionComponent p = current_room.Player.getComponent(PositionComponent.class);
		if(p!= null){
			if(Gdx.input.isKeyJustPressed(Keys.W)){p.Move(DIRECTION.UP,current_room);}
			if(Gdx.input.isKeyJustPressed(Keys.A)){p.Move(DIRECTION.LEFT,current_room);}
			if(Gdx.input.isKeyJustPressed(Keys.S)){p.Move(DIRECTION.DOWN,current_room);}
			if(Gdx.input.isKeyJustPressed(Keys.D)){p.Move(DIRECTION.RIGHT,current_room);}
			((Player)current_room.Player).CheckChangeRoom(current_room,this);
		}
	}
	
	
	public void Render(SpriteBatch batch,int x, int y){
		int cam_x = x;//-(SCREEN_WIDTH/2) 
		int cam_y = y;//-(SCREEN_HEIGHT/2)
		PositionComponent p = current_room.Player.getComponent(PositionComponent.class);
		if(p!= null){
		cam_x += current_room.TILE_SIZE*p.x;
		cam_y -= current_room.TILE_SIZE*p.y;
		}
		current_room.Draw(batch,cam_x,cam_y);
		
	}
	
	public void dispose(){
		EnemySlime.slimeSprites.dispose();
		Tile.tex_DoorDown.dispose();
		Tile.tex_DoorLeft.dispose();
		Tile.tex_DoorRight.dispose();
		Tile.tex_DoorUp.dispose();
		Tile.tex_FinalDoor.dispose();
		Tile.tex_Floor0.dispose();
		Tile.tex_Wall0.dispose();
		Tile.tex_Floor0.dispose();
		UI.GameFont.dispose();
	}
	
}
