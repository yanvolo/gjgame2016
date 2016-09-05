package com.gj.game;

import com.badlogic.gdx.graphics.Texture;
import com.gj.game.Main.GameMode;

import java.util.Random;

import com.badlogic.ashley.core.Entity;

public class ItemChest extends Entity{
	public static Texture chestSprite;
	public boolean empty;
	public ItemChest(int px,int py){
		//TODO:Loot
		empty = false;
		this.add(new DrawComponent(chestSprite,"$"));
		this.add(new PositionComponent(px,py));
	}
	
	public void GivePlayerLoot(Player p){
		if(empty == false){
			empty = true;
			if(Questionnaire.instance.isHoarder()== true){
				p.num_loot++;
				UI.LogString("Loot get!");
				if(p.num_loot >= 5){
					UI.LogString("Congratulations, you win!");
					UI.LogString("Press R to continue");
					Main.game_mode = GameMode.WIN;
					}
				return;
			}
			Random rand = new Random();
			int r = rand.nextInt(8);
			
			CombatComponent c = p.getComponent(CombatComponent.class);
			
			switch(r){
				case 0:{
					p.num_hp_pots++;
					UI.LogString("You find a health potion within the chest.");
					UI.LogString("You now have "+p.num_hp_pots);	
					break;
				}
				case 1:{
					p.num_mana_pots++;
					UI.LogString("You find a mana potion within the chest.");
					UI.LogString("You now have "+p.num_mana_pots);
					break;
					}
				case 2:{
					p.num_loot++;
					UI.LogString("You find some gold within the chest.");
					UI.LogString("You now have "+p.num_loot + " gold");	
					break;
					}
				case 3:{
					c.Mana = c.MaxMana;
					UI.LogString("A spell cast on the chest replenishes your mana.");
					break;
					}
				case 4:{
					if(rand.nextInt(21)==20){
						UI.LogString("No way! You've found [Tinfoil Chainmail]!");
						UI.LogString("Nothing can stop you now!");
						c.Armor = 999;
					}
					else{
						c.Armor+=1;
						p.num_hp_pots++;UI.LogString("You find some sturdy armor in the chest!");
					}
					break;
					}
				case 5:{
					if(rand.nextInt(21)==20){
						UI.LogString("Incredible! You've found ");
						UI.LogString("[IceBreaker, Bringer of Minty Freshness]! Very cool!");
						c.AttackPower = 999;
					}
					else{
						c.AttackPower+=1;
						p.num_hp_pots++;UI.LogString("You find a well-crafted sword in the chest!");
					}
					break;
				}
				case 6:{
					UI.LogString("You find some supplies and recover 3 HP");
					c.HP+= 3;
					if(c.HP > c.MaxHP){c.HP=c.MaxHP;}
					break;
				}
				default:{
					c.HP = c.MaxHP;
					UI.LogString("A spell cast on the chest replenishes your health.");
					break;}
			}			
		}
	}
}
