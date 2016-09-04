package com.gj.game;

import java.util.Random;

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
		Random rand = new Random();
		
		current_room = (rand.nextBoolean())?new Room(0,32):new Room(1,16);//Is Cynical?
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
		EnemySlime.slimeSprite = new Texture("rsc/slime.png");
		EnemyBat.batSprite = new Texture("rsc/bat.png");
		EnemyRat.ratSprite = new Texture("rsc/rat.png");
		EnemyArcher.archerSprite = new Texture("rsc/archer.png");
		EnemyWarrior.warriorSprite = new Texture("rsc/warrior.png");
		
		ItemSign.signSprite = new Texture("rsc/sign.png");
		ItemChest.chestSprite = new Texture("rsc/chest.png");
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
		current_room = new Room(0,32);
		current_room.Player = new Player(4,10);
		current_room.Load("rsc/"+dungeon_rooms[room_y][room_x]);

		/*change rules, dungeon rooms based on values.
		 * 
		 * */
		
	}
	
	public void Update(){
		Player player = (Player)current_room.Player;
		PositionComponent p = player.getComponent(PositionComponent.class);
		CombatComponent com = player.getComponent(CombatComponent.class);
		if(p!= null && com != null){
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)){current_room.DoTurn();}
			if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){player.selected_skill = 0;}
			if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){player.selected_skill = 1;}
			if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){//Guard,directionless
				com.AttackSkills.get(1).OnUse(current_room, player, DIRECTION.UP);
				player.selected_skill = 0;}
			if(Gdx.input.isKeyJustPressed(Keys.NUM_3)){player.selected_skill = 3;}
			if(Gdx.input.isKeyJustPressed(Keys.NUM_4)){player.selected_skill = 4;}
			if(Gdx.input.isKeyJustPressed(Keys.NUM_5)){//HP potion,directionless
				com.AttackSkills.get(4).OnUse(current_room, player, DIRECTION.UP);
				player.selected_skill = 0;}
			if(Gdx.input.isKeyJustPressed(Keys.NUM_6)){//Mana Potion,directionless
				com.AttackSkills.get(5).OnUse(current_room, player, DIRECTION.UP);
				player.selected_skill = 0;}
			
			if(Gdx.input.isKeyJustPressed(Keys.W)){
					if(player.selected_skill==0){
						p.Move(current_room.Player,DIRECTION.UP,current_room);
						current_room.DoTurn();
					}
					else{
						if(com.AttackSkills.get(player.selected_skill-1).OnUse(current_room, player, DIRECTION.UP)){
							current_room.DoTurn();
						}
						player.selected_skill = 0;
					}
				}
			if(Gdx.input.isKeyJustPressed(Keys.A)){
				if(player.selected_skill==0){
					p.Move(current_room.Player,DIRECTION.LEFT,current_room);
					current_room.DoTurn();
				}
				else{
					if(com.AttackSkills.get(player.selected_skill-1).OnUse(current_room, player, DIRECTION.LEFT)){
						current_room.DoTurn();
					}
					player.selected_skill = 0;
				}
				}
			if(Gdx.input.isKeyJustPressed(Keys.S)){
				if(player.selected_skill==0){
					p.Move(current_room.Player,DIRECTION.DOWN,current_room);
					current_room.DoTurn();
				}
				else{
					if(com.AttackSkills.get(player.selected_skill-1).OnUse(current_room, player, DIRECTION.DOWN)){
						current_room.DoTurn();
					}
					player.selected_skill = 0;
				}
				}
			if(Gdx.input.isKeyJustPressed(Keys.D)){
				if(player.selected_skill==0){
					p.Move(current_room.Player,DIRECTION.RIGHT,current_room);
					current_room.DoTurn();
				}
				else{
					if(com.AttackSkills.get(player.selected_skill-1).OnUse(current_room, player, DIRECTION.RIGHT)){
						current_room.DoTurn();
					}
					player.selected_skill = 0;
				}
			}
			((Player)current_room.Player).CheckChangeRoom(current_room,this);
		}
	}
	
	
	public void Render(SpriteBatch batch,int w, int h){
		int cam_x=0;
		int cam_y=0;
		PositionComponent p = current_room.Player.getComponent(PositionComponent.class);
		if(p!= null){
		cam_x =  (w/2) -current_room.TILE_SIZE*p.x;
		cam_y =  (h/2) +current_room.TILE_SIZE*p.y;
		}
		current_room.Draw(batch,cam_x,cam_y);
		
	}
	
	public void dispose(){
		EnemySlime.slimeSprite.dispose();
		EnemyBat.batSprite.dispose();
		EnemyRat.ratSprite.dispose();
		EnemyArcher.archerSprite.dispose();
		EnemyWarrior.warriorSprite.dispose();
		
		ItemSign.signSprite.dispose();
		ItemChest.chestSprite.dispose();
		
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
