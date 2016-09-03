package com.gj.game;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class DrawComponent implements Component{

	Texture draw;
	char ascii_draw;

	public DrawComponent(Texture t,char mychar){
		draw = t;
		ascii_draw = mychar;
	}
	
	public void Draw(SpriteBatch b,int drawmode,int x, int y){
		if(drawmode ==0){
			b.draw(draw, x, y);
		}
		if(drawmode ==1){
			//draw ascii art
		}
		
	}
	
}