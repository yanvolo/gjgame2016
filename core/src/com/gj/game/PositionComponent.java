package com.gj.game;
import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component{

	int x;
	int y;
	public PositionComponent(int px,int py){
		x=px;
		y=py;
	}
}
