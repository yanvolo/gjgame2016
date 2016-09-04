package com.gj.game;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class DrawComponent implements Component{

	Texture draw;
	CharSequence ascii_draw;

	public DrawComponent(Texture t,CharSequence mychar){
		draw = t;
		ascii_draw = mychar;
	}
	
	public void Draw(SpriteBatch b,int drawmode,int x, int y){
		if(drawmode ==0){
			if(draw != null)b.draw(draw, x, y);
			else System.out.println(ascii_draw);
		}
		if(drawmode ==1){
			UI.GameFont.draw(b, ascii_draw, x, y);
			//draw ascii art
		}
		
	}
	
}