package com.gj.game;

import java.util.Random;

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
	public static Texture	menubar;
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
		menubar = new Texture("rsc/bar.png");
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
	
	public void Draw(SpriteBatch batch, Room r,int w, int h){
		CombatComponent c = r.Player.getComponent(CombatComponent.class);
		PositionComponent p = r.Player.getComponent(PositionComponent.class);
		Random rand = new Random();
		if(c != null){
			if(r.DRAW_MODE == 0){
				//If externalizing....
				int mhp = c.MaxHP;
				int chp = c.HP;
				int mmn = c.MaxMana;
				int cmn = c.Mana;
				if(Questionnaire.instance.isExternalizing()){
					chp = rand.nextInt(20);
					mhp = chp+rand.nextInt(20);
					cmn = rand.nextInt(20);
					mmn = cmn+rand.nextInt(20);
				}
				
				int hp =0;
				for(hp=0;hp<chp;hp++){batch.draw(hpFull,90+4*hp,90);}
				while(hp<mhp){batch.draw(hpEmpty,90+4*hp,90);hp++;}
				int mana =0;
				for(mana=0;mana<cmn;mana++){batch.draw(manaFull,90+4*mana,72);}
				while(mana<mmn){batch.draw(manaEmpty,90+4*mana,72);mana++;}
			}
			else if(r.DRAW_MODE == 1){

				if(Questionnaire.instance.isExternalizing()){
					GameFont.draw(batch,"HP:"+rand.nextInt(20)+"/"+rand.nextInt(20),90,102);
					GameFont.draw(batch,"Mana:"+rand.nextInt(20)+"/"+rand.nextInt(20),90,86);
				}
				else{
					GameFont.draw(batch,"HP:"+c.HP+"/"+c.MaxHP,90,102);
					GameFont.draw(batch,"Mana:"+c.Mana+"/"+c.MaxMana,90,86);
					
				}
			}
		}

		Questionnaire.instance.isHoarder();
		Questionnaire.instance.isFatalist();
		
		Questionnaire.instance.isGuarded();
		Questionnaire.instance.isIntense();
		
		if(r.DRAW_MODE == 0){
			batch.draw(menubar,256,72);
		}
		else if(r.DRAW_MODE == 1){
			GameFont.draw(batch,"1:Attack,2:Guard,3:Fire spell",256,102);
			GameFont.draw(batch,"4:Teleport,5:Health potion,6:Mana potion",256,86);
		}
		
		if(r.DRAW_MODE == 0){batch.draw(textBox, 90,8);}
		else if(r.DRAW_MODE == 1){
			String top =  "*----------------------------------------------------------*";
			String side = "*                                                          *";
			GameFont.draw(batch,top,82,74);
			GameFont.draw(batch,side,82,58);
			GameFont.draw(batch,side,82,42);
			GameFont.draw(batch,side,82,26);
			GameFont.draw(batch,top,82,10);
			
		}
		for(int i=0;i<LogLength;i++){
			if(QuestLog[i]!= null)
			GameFont.draw(batch, QuestLog[i],98,24+14*i);
		}
	}
	
	
}
