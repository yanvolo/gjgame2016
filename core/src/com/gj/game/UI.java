package com.gj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class UI {

	public static int Player_HP;
	public static int Player_Mana;
	
	public static BitmapFont GameFont;
	
	public static int LogLength;
	public static String[] QuestLog;
	public static void LogString(String s){
		for(int i=LogLength-2;i>=0;i-=1){
			QuestLog[i+1] = QuestLog[i];
		}
		QuestLog[0] = s;
	}
	
	
	public static void Load(){
		LogLength = 6;
		QuestLog = new String[LogLength];
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("rsc/Consolas.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		GameFont = generator.generateFont(parameter); 
		GameFont.setColor(Color.WHITE);
		generator.dispose();
	}
	
	public static void Draw(SpriteBatch batch){
		
		for(int i=0;i<LogLength;i++){
			if(QuestLog[i]!= null)
			GameFont.draw(batch, QuestLog[i],128,16+14*i);
		}
	}
	
	
}
