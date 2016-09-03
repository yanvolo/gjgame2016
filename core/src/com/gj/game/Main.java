package com.gj.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Room test_room;
	
	public static int LogLength;
	public static String[] QuestLog;
	public static void LogString(String s){
		for(int i=LogLength-2;i>=0;i-=1){
			QuestLog[i+1] = QuestLog[i];
		}
		QuestLog[0] = s;
	}
	
	BitmapFont gameFont;
	
	
	int SCREEN_WIDTH = 600;
	int SCREEN_HEIGHT = 400;
	
	int cam_x,cam_y;
	
	 private OrthographicCamera camera;
	
	@Override
	public void create () {
		LogLength = 6;
		QuestLog = new String[LogLength];
		gameFont = new BitmapFont();
		gameFont.setColor(Color.WHITE);
		
		
		
		
		camera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
		//camera.setToOrtho(false,);
		
		batch = new SpriteBatch();
		EnemySlime.slimeSprites = new Texture("rsc/slime.png");
		Tile.tex_DoorDown = new Texture("rsc/doorDown.png");
		Tile.tex_DoorLeft = new Texture("rsc/doorLeft.png");
		Tile.tex_DoorRight = new Texture("rsc/doorRight.png");
		Tile.tex_DoorUp = new Texture("rsc/doorUp.png");
		Tile.tex_FinalDoor = new Texture("rsc/doorLast.png");
		Tile.tex_Floor0 = new Texture("rsc/floor0.png");
		Tile.tex_Wall0 = new Texture("rsc/wall0.png");
		
		test_room = new Room();
		test_room.DRAW_MODE =0;
		test_room.TILE_SIZE = 16;
		test_room.Load("rsc/genericLevel.txt");
		

		cam_x = -(SCREEN_WIDTH/2)+(test_room.TILE_SIZE*test_room.Width/2);
		cam_y = -(SCREEN_HEIGHT/2)-(test_room.TILE_SIZE*test_room.Height/2)-64;
		
		
		LogString("You do 8 damage");
		LogString("You do 9 damage");
		LogString("You do 10 damage");
		LogString("You do 11 damage");
		LogString("You do 12 damage");
		LogString("You do 13 damage");
		LogString("You do 14 damage");
		LogString("You do 15 damage");
		LogString("You do 16 damage");
		LogString("You do 17 damage");
		LogString("You do 18 damage");
	}

	@Override
	public void render () {
		
		//float delta = Gdx.graphics.getDeltaTime();
		camera.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
			test_room.Draw(batch,cam_x,cam_y);
			gameFont.setColor(Color.WHITE);
			for(int i=0;i<LogLength;i++){
				gameFont.draw(batch, QuestLog[i],128,16+14*i);
			}
			
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		EnemySlime.slimeSprites.dispose();
		Tile.tex_DoorDown.dispose();
		Tile.tex_DoorLeft.dispose();
		Tile.tex_DoorRight.dispose();
		Tile.tex_DoorUp.dispose();
		Tile.tex_FinalDoor.dispose();
		Tile.tex_Floor0.dispose();
		Tile.tex_Wall0.dispose();
		Tile.tex_Floor0.dispose();
	}
}
