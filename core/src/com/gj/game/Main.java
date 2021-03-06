package com.gj.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	
	Dungeon d;
	Questionnaire q;
	UI	ui;
	
	int SCREEN_WIDTH = 800;
	int SCREEN_HEIGHT = 600;
	private OrthographicCamera camera;
	
	public enum GameMode{
		QUESTIONNAIRE,
		DUNGEON_START,
		DUNGEON,
		DEATH,
		WIN
	}
	
	public static GameMode game_mode;
	
	@Override
	public void create () {
		game_mode = GameMode.DUNGEON_START;
		batch = new SpriteBatch();
		q = new Questionnaire();
		d = new Dungeon();
		ui = new UI();
		camera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
	}

	@Override
	public void render () {

		/*Update Logic*/
		if(game_mode == game_mode.DUNGEON){
			d.Update();
		}
		else if(game_mode == game_mode.DUNGEON_START){//Questionaire is over, load dungeon.
			d.LoadPostQuestionnaire();
			game_mode =game_mode.DUNGEON;
		}
		else if(game_mode ==game_mode.QUESTIONNAIRE){
			q.Update();
		}
		else if(game_mode == game_mode.DEATH){
			if(Gdx.input.isKeyJustPressed(Keys.R)){game_mode = GameMode.DUNGEON_START;}
		}
		else if(game_mode == game_mode.WIN){
			if(Gdx.input.isKeyJustPressed(Keys.R)){game_mode = GameMode.QUESTIONNAIRE;}
		}
		
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
			/*Render Code*/
			if(game_mode == game_mode.DUNGEON ||game_mode == game_mode.DEATH||game_mode == game_mode.WIN){
				d.Render(batch,SCREEN_WIDTH,SCREEN_HEIGHT);
				ui.Draw(batch,d.current_room, SCREEN_WIDTH, SCREEN_HEIGHT);
			}
			else if(game_mode == game_mode.QUESTIONNAIRE){
				q.Render(batch, (SCREEN_WIDTH/2),(SCREEN_HEIGHT/2));
			}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		d.dispose();
		q.dispose();
	}
}
