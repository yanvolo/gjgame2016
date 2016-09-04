package com.gj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class UI {
	public static UI instance;
	
	public static BitmapFont GameFont;
	public String[] QuestLog;
	public int LogLength;
	
	public static void LogString(String s){
		for(int i=instance.LogLength-2;i>=0;i-=1){
			instance.QuestLog[i+1] = instance.QuestLog[i];
		}
		instance.QuestLog[0] = s;
	}
	
	
	public UI(){
		instance = this;
		LogLength = 6;
		QuestLog = new String[LogLength];
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("rsc/Consolas.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		GameFont = generator.generateFont(parameter); 
		GameFont.setColor(Color.WHITE);
		generator.dispose();
	}
	
	public void Draw(SpriteBatch batch){
		for(int i=0;i<LogLength;i++){
			if(QuestLog[i]!= null)
			GameFont.draw(batch, QuestLog[i],128,16+14*i);
		}
	}
	
	
}
