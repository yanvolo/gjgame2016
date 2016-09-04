package com.gj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class UI {
	public static UI instance;
	
	public static Texture	textBox;
	public static Texture	hpEmpty;
	public static Texture	hpFull;
	public static Texture	manaEmpty;
	public static Texture	manaFull;
	
	
	public static BitmapFont GameFont;
	public String[] QuestLog;
	public int LogLength;
	
	public static void LogString(String s){	
		String[] lines = s.split("\n");

		for(int i=lines.length-1;i>=1;i--){LogString(lines[i]);}
		for(int i=instance.LogLength-2;i>=0;i-=1){
			instance.QuestLog[i+1] = instance.QuestLog[i];
		}
		instance.QuestLog[0] = lines[0];
	}
	
	
	public UI(){
		instance = this;
		textBox = new Texture("rsc/textbox.png");
		hpEmpty = new Texture("rsc/hpEmpty.png");
		hpFull = new Texture("rsc/hpFull.png");
		manaEmpty = new Texture("rsc/manaEmpty.png");
		manaFull = new Texture("rsc/manaFull.png");
		
		LogLength = 4;
		QuestLog = new String[LogLength];
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("rsc/Consolas.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		GameFont = generator.generateFont(parameter); 
		GameFont.setColor(Color.WHITE);
		generator.dispose();
	}
	
	public void Draw(SpriteBatch batch, Room r){
		CombatComponent c = r.Player.getComponent(CombatComponent.class);
		if(c != null){
			int hp =0;
			for(hp=0;hp<c.HP;hp++){batch.draw(hpFull,90+4*hp,90);}
			while(hp<c.MaxHP){batch.draw(hpEmpty,90+4*hp,90);hp++;}
			int mana =0;
			for(mana=0;mana<c.Mana;mana++){batch.draw(manaFull,90+4*mana,72);}
			while(mana<c.MaxMana){batch.draw(manaEmpty,90+4*mana,72);mana++;}
		}

		
		batch.draw(textBox, 90,8);
		for(int i=0;i<LogLength;i++){
			if(QuestLog[i]!= null)
			GameFont.draw(batch, QuestLog[i],98,64-14*i);
		}
	}
	
	
}
