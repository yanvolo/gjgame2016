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
	public static Texture	menubar;
	public static Texture	hpEmpty;
	public static Texture	hpFull;
	public static Texture	manaEmpty;
	public static Texture	manaFull;
	public static Texture 	directions;
	
	
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
		directions= new Texture("rsc/directions.png");
		
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
		if(c != null){
			if(r.DRAW_MODE == 0){
				//If externalizing....
				int hp =0;
				for(hp=0;hp<c.HP;hp++){batch.draw(hpFull,90+4*hp,90);}
				while(hp<c.MaxHP){batch.draw(hpEmpty,90+4*hp,90);hp++;}
				int mana =0;
				for(mana=0;mana<c.Mana;mana++){batch.draw(manaFull,90+4*mana,72);}
				while(mana<c.MaxMana){batch.draw(manaEmpty,90+4*mana,72);mana++;}
			}
			else if(r.DRAW_MODE == 1){
				GameFont.draw(batch,"HP:"+c.HP+"/"+c.MaxHP,90,102);
				GameFont.draw(batch,"Mana:"+c.Mana+"/"+c.MaxMana,90,86);
			}
		}
		if(((Player)r.Player).selected_skill != 0){
			if(r.DRAW_MODE == 0){
				if(p != null){
					batch.draw(directions,(w/2)-r.TILE_SIZE,(h/2)-r.TILE_SIZE);
				}	
			}
			else if(r.DRAW_MODE == 1){
				GameFont.draw(batch," ^",(w/2)-r.TILE_SIZE/2,(h/2)+r.TILE_SIZE);
				GameFont.draw(batch,"< >",(w/2)-r.TILE_SIZE/2,(h/2));
				GameFont.draw(batch," v",(w/2)-r.TILE_SIZE/2,(h/2)-r.TILE_SIZE);
			}
		}

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
