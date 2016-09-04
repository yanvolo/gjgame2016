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

	public Room(int DrawMode,int TileSize){
		DRAW_MODE = DrawMode;
		TILE_SIZE = TileSize;
	}
	
	public void Load(String filename){
		File lvlfile = new File(filename);
		if(lvlfile.exists()){
			Entities = new ArrayList<Entity>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(lvlfile));
			
				String line = reader.readLine();
					Width = Integer.parseInt(line);
				line = reader.readLine();
					Height = Integer.parseInt(line);
				
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
						else if(t.compareTo("E0")==0){
							tile.type = TILE_TYPE.FLOOR;
							tile.draw_tex = Tile.tex_Floor0;
							Entities.add(new EnemySlime(i,j));
						}
					}
				}
				reader.close();	
			} 
			catch (Exception e) {e.printStackTrace();}
			
			
			
		}
		
		
	}
	
	public void Draw(SpriteBatch batch,int cam_x,int cam_y){
		for(int y=0;y<Height;y++){
			for(int x=0;x<Width;x++){
				Level[y*Width + x].Draw(batch, DRAW_MODE,TILE_SIZE*x -cam_x,-TILE_SIZE*y-cam_y);		
			}
		}
		for(Entity e:Entities){
			PositionComponent pos = e.getComponent(PositionComponent.class);
			DrawComponent draw = e.getComponent(DrawComponent.class);
			if(pos != null && draw != null){
				draw.Draw(batch, DRAW_MODE,TILE_SIZE*pos.x-cam_x,-TILE_SIZE*pos.y-cam_y);
			}
		}
	}	
}
	

