package com.gj.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Questionnaire {

	
	public Questionnaire(){}
	public void Render(SpriteBatch batch,int x,int y){}
	public void Update(){
		
		/*Update code goes here.*/
		if(true/*finished with questionaire*/){
			Main.SetGameMode(true);
		}
	}
	public void dispose(){}
	
}
