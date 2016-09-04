package com.gj.game;

import com.badlogic.ashley.core.Entity;

public interface Skill {
	int Mana_Cost=0;
	int Cooldown_Turns=0;
	
	public boolean OnUse(Room r,Entity user,Dungeon.DIRECTION dir);
}
