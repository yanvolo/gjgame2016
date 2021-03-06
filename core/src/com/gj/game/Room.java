package com.gj.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gj.game.Tile.TILE_TYPE;

public class Room {


	int	Width,Height;
	public int TILE_SIZE;
	public int DRAW_MODE;
	
	public Tile[] 				Level;
	public ArrayList<Entity> 	Entities;
	public Entity				Player;

	public static Texture 	directions;
	

	public Room(int DrawMode,int TileSize){
		DRAW_MODE = DrawMode;
		TILE_SIZE = TileSize;
	}
	
	public void Load(String filename){
		int sign_count =0;
		File lvlfile = new File(filename);
		if(lvlfile.exists()){
			Entities = new ArrayList<Entity>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(lvlfile));

				String line = reader.readLine();
					Width = Integer.parseInt(line);
				line = reader.readLine();
					Height = Integer.parseInt(line);
				
					//System.out.println(filename +": "+Width+","+Height);
				Level = new Tile[Width*Height];
				for(int i=0;i<Height;i++){
					line = reader.readLine();
					String[] tokens = line.split(" ");
					for(int j=0;j<Width;j++){
						Level[i*Width+j] =new Tile();
						Tile tile = Level[i*Width+j];
						String t = tokens[j];
						if(t.compareTo("W0")==0){
							tile.type = TILE_TYPE.WALL;
							tile.draw_tex = Tile.tex_Wall0;
						}
						else if(t.compareTo("F0")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
						}
						else if(t.compareTo("DU")==0){
							tile.type = TILE_TYPE.DOOR_UP;
							tile.draw_tex = Tile.tex_DoorUp;
						}
						else if(t.compareTo("DD")==0){
							tile.type = TILE_TYPE.DOOR_DOWN;
							tile.draw_tex = Tile.tex_DoorDown;
						}
						else if(t.compareTo("DL")==0){
							tile.type = TILE_TYPE.DOOR_LEFT;
							tile.draw_tex = Tile.tex_DoorLeft;
						}
						else if(t.compareTo("DR")==0){
							tile.type = TILE_TYPE.DOOR_RIGHT;
							tile.draw_tex = Tile.tex_DoorRight;
						}
						else if(t.compareTo("DF")==0){
							tile.type = TILE_TYPE.DOOR_LAST;
							tile.draw_tex = Tile.tex_FinalDoor;
						}
						else if(t.compareTo("DS")==0){
							tile.type = TILE_TYPE.WALL;
							tile.draw_tex = Tile.tex_DoorUp;
						}
						else if(t.compareTo("ES")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemySlime(j,i,true));
						}
						else if(t.compareTo("Es")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemySlime(j,i,false));
						}
						else if(t.compareTo("EB")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemyBat(j,i));
						}
						else if(t.compareTo("ER")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemyRat(j,i));
						}
						else if(t.compareTo("EA")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemyArcher(j,i));
						}
						else if(t.compareTo("EW")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemyWarrior(j,i));
						}
						else if(t.compareTo("IC")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new ItemChest(j,i));
						}
						else if(t.compareTo("IS")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new ItemSign(j,i,sign_count));
							sign_count++;
						}
						else if(t.compareTo("IW")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new ItemGameWin(j,i));
						}
					}
				}
				ItemSign.room_texts = new String[sign_count];
				for(int i=0;i<sign_count;i++){
					ItemSign.room_texts[i] = reader.readLine().replace("%n","\n");
				}
				
				reader.close();	
			} 
			catch (Exception e) {e.printStackTrace();}
			Entities.add(Player);
		}
	}
	
	public void Draw(SpriteBatch batch,int cam_x,int cam_y){
		for(int y=0;y<Height;y++){
			for(int x=0;x<Width;x++){
				Level[y*Width + x].Draw(batch, DRAW_MODE,TILE_SIZE*x +cam_x,-TILE_SIZE*y+cam_y);		
			}
		}
		for(Entity e:Entities){
			PositionComponent pos = e.getComponent(PositionComponent.class);
			DrawComponent draw = e.getComponent(DrawComponent.class);
			if(pos != null && draw != null){
				if(Questionnaire.instance.isPast()){
					 draw.Draw(batch, DRAW_MODE,TILE_SIZE*pos.past_x+cam_x,-TILE_SIZE*pos.past_y+cam_y);
				}
				else if(Questionnaire.instance.isDaydreamer()){
					 draw.Draw(batch, DRAW_MODE,TILE_SIZE*pos.future_x+cam_x,-TILE_SIZE*pos.future_x+cam_y);
				}
				else draw.Draw(batch, DRAW_MODE,TILE_SIZE*pos.x+cam_x,-TILE_SIZE*pos.y+cam_y);
			}
		}
		PositionComponent pos = Player.getComponent(PositionComponent.class);
		if(((Player)Player).selected_skill != 0 && pos != null){
			if(DRAW_MODE == 0){
				if(Questionnaire.instance.isPast()){
					batch.draw(directions,TILE_SIZE*pos.past_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.past_y+cam_y-TILE_SIZE);
				}
				else if(Questionnaire.instance.isDaydreamer()){
					batch.draw(directions,TILE_SIZE*pos.future_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.future_y+cam_y-TILE_SIZE);
				}
				else batch.draw(directions,TILE_SIZE*pos.x+cam_x-TILE_SIZE,-TILE_SIZE*pos.y+cam_y-TILE_SIZE);
			}
			else if(DRAW_MODE == 1){
				if(Questionnaire.instance.isPast()){
					UI.GameFont.draw(batch," ^", TILE_SIZE*pos.past_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.past_y+cam_y+TILE_SIZE);
					UI.GameFont.draw(batch,"< >",TILE_SIZE*pos.past_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.past_y+cam_y);
					UI.GameFont.draw(batch," v", TILE_SIZE*pos.past_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.past_y+cam_y-TILE_SIZE);
				}
				else if(Questionnaire.instance.isDaydreamer()){
					UI.GameFont.draw(batch," ^", TILE_SIZE*pos.future_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.future_y+cam_y+TILE_SIZE);
					UI.GameFont.draw(batch,"< >",TILE_SIZE*pos.future_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.future_y+cam_y);
					UI.GameFont.draw(batch," v", TILE_SIZE*pos.future_x+cam_x-TILE_SIZE,-TILE_SIZE*pos.future_y+cam_y-TILE_SIZE);
				}
				else{
					UI.GameFont.draw(batch," ^", TILE_SIZE*pos.x+cam_x-TILE_SIZE,-TILE_SIZE*pos.y+cam_y+TILE_SIZE);
					UI.GameFont.draw(batch,"< >",TILE_SIZE*pos.x+cam_x-TILE_SIZE,-TILE_SIZE*pos.y+cam_y);
					UI.GameFont.draw(batch," v", TILE_SIZE*pos.x+cam_x-TILE_SIZE,-TILE_SIZE*pos.y+cam_y-TILE_SIZE);
				}
			}
		}
	}	

	public void DoTurn(){
		for(int i=0;i<Entities.size();i++){
			Entity e = Entities.get(i);
			if(e instanceof EnemyArcher){((EnemyArcher) e).OnTurn(this);}
			else if(e instanceof EnemyBat){((EnemyBat) e).OnTurn(this);}
			else if(e instanceof EnemyRat){((EnemyRat) e).OnTurn(this);}
			else if(e instanceof EnemySlime){((EnemySlime) e).OnTurn(this);}
			else if(e instanceof EnemyWarrior){((EnemyWarrior) e).OnTurn(this);}
		}	
	}
}
	

