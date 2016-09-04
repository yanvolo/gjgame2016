package com.gj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Questionnaire {

	//GDX Tools
	public SpriteBatch batch;
	public BitmapFont questionFont;
	//Useful Screen Constants
	public static final int LINE_HEIGHT = 20;
	public static final int BOTTOM_HEIGHT = 20;
	public static final int MAX_HEIGHT = 500;
	public static final int X_BUFFER = 5;
	//Genneral Stuff
	public enum Section{
		DONE,
		CHATTING,
		QUESTIONING,
		CALCULATING
	}
	public Section currentSection;

	//Chat Stuff
	public String[][] dialogueSecctions;
	int dialogueIndex;
	public int dialogueSectionNumber;

	//Questioning Stuff
	public Trait[] listOfTraits;
	public Question[] listOfQuestions;
	public int questionIndex;
	public boolean[] mutators;

	//Useful structures
	protected class Trait{
		public int dimensionIndex;
		public String questionText;
		public Trait(){
			dimensionIndex=0;
			questionText="";
		}
		public Trait(int myIndex, String myQuestion) {
			dimensionIndex = myIndex;
			questionText = myQuestion;
		}
	}
	protected class Question {
		public Trait firstTrait;
		public Trait secondTrait;
		public int outcome;
		//0-Neither, 1-First, 2-Second, 3-Both
		public Question(){
			firstTrait = new Trait();
			secondTrait = new Trait();
			outcome = 0;
		}
	}
	public Questionnaire(){
		//Prep GDX Stuff
		questionFont = new BitmapFont();
		questionFont.setColor(Color.WHITE);
		batch = new SpriteBatch();
		//Prepare for Questioning
		listOfTraits= generateListOfTraits();
		listOfQuestions = generateQuestions(listOfTraits);
		questionIndex = 0;
		//Prepare for Chatting
		dialogueSecctions = new String[2][];
		dialogueSecctions[0] = new String[]{
			"[Welcome: Please press enter to initilize program]",
			"This is a test",
			"This is more text",
			"And this is even more"
		};
		dialogueSecctions[1] = new String[]{
			"Alright, let's see how your new game came out",
			"This is more text",
			"And Even More Text"
		};
		dialogueIndex=1;
		dialogueSectionNumber = 0;
		//Activate Current Section
		currentSection = Section.CHATTING;
	}
	public void Render(SpriteBatch batch,int x,int y){
		if(currentSection == Section.QUESTIONING){
			renderQuestion(batch);
		}
		else if(currentSection == Section.CHATTING){
			renderChat(batch);
		}


	}
	private void renderQuestion(SpriteBatch batch){
		String leftSide = "[1] - " + listOfQuestions[questionIndex].firstTrait.questionText;
		String rightSide = "[2] - " + listOfQuestions[questionIndex].secondTrait.questionText;
		questionFont.draw(batch, leftSide, 5, MAX_HEIGHT - (5*LINE_HEIGHT));
		questionFont.draw(batch, rightSide, 5, MAX_HEIGHT - (7*LINE_HEIGHT));
		questionFont.draw(batch, "[3]-I agree with both [4]-I disagree with both",5,BOTTOM_HEIGHT);
	}
	private void renderChat(SpriteBatch batch){
		for(int i=0;i<dialogueIndex;i++){
			questionFont.draw(batch, dialogueSecctions[dialogueSectionNumber][i],X_BUFFER,(LINE_HEIGHT*(dialogueIndex-i))+BOTTOM_HEIGHT);
		}
	}

	public void Update(){
		if(currentSection == Section.CALCULATING){
			mutators=evaluateMutators(listOfQuestions);
			currentSection = Section.CHATTING;
		}
		/*Update code goes here.*/
		if(currentSection == Section.QUESTIONING){
			int originalQuestionIndex = questionIndex;
			Question currentQuestion = listOfQuestions[questionIndex];
			if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
				currentQuestion.outcome = 1;
				questionIndex++;
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
				currentQuestion.outcome = 2;
				questionIndex++;
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
				currentQuestion.outcome = 3;
				questionIndex++;
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
				currentQuestion.outcome = 0;
				questionIndex++;
			}
			if(questionIndex>originalQuestionIndex && questionIndex==listOfQuestions.length){
				currentSection = Section.CALCULATING;
				dialogueSectionNumber = 1;
			}
		}
		else if(currentSection == Section.CHATTING && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			dialogueIndex++;
			if(dialogueIndex>dialogueSecctions[dialogueSectionNumber].length){
				dialogueIndex = 1;
				dialogueSectionNumber++;
				if(dialogueSectionNumber==1){
					currentSection = Section.QUESTIONING;
				}
				if(dialogueSectionNumber==2){
					currentSection = Section.DONE;
					System.out.println(Arrays.toString(mutators));
				}
			}
		}
		else if(currentSection == Section.DONE){
			Main.game_mode = Main.GameMode.DUNGEON_START;
		}
	}
	public void dispose(){
		questionFont.dispose();
		batch.dispose();
	}
	public Trait[] generateListOfTraits(){
		Trait[] ret = new Trait[28];
		ret[0] = new Trait(0,"Society is frequently wrong.");
		ret[1] = new Trait(0,"People often use flattery to ask for favors.");
		ret[2] = new Trait(0,"People are always disingenuous about their emotions.");
		ret[3] = new Trait(1,"People who keep working hard will always succeed.");
		ret[4] = new Trait(1,"Your image is the most important thing you have.");
		ret[5] = new Trait(1,"One should constantly work to keep up with those around them.");
		ret[6] = new Trait(2,"Criticism is always hard to hear.");
		ret[7] = new Trait(2,"Compliments must be immediately returned.");
		ret[8] = new Trait(2,"People should only do what they are good at.");
		ret[9] = new Trait(3,"The world is always unfair.");
		ret[10]= new Trait(3,"Authority should always be fought.");
		ret[11]= new Trait(3,"It is always someone’s fault.");
		ret[12]= new Trait(4,"One should always be prepared for the worst.");
		ret[13]= new Trait(4,"People rarely care about the emotions of others.");
		ret[14]= new Trait(4,"Most people have unrealistic standards about personal relationships");
		ret[15]= new Trait(5,"People must be held closely or else they will leave.");
		ret[16]= new Trait(5,"A couple should dedicate most of their life around each other.");
		ret[17]= new Trait(5,"An unhappy couple should remain loyal and stick together.");
		ret[18]= new Trait(6,"The past was a simpler time.");
		ret[19]= new Trait(6,"People should always reflect on failures and see what could have been done better.");
		ret[20]= new Trait(7,"Things will always be better in the future");
		ret[21]= new Trait(7,"It is easy to get lost in a daydream.");
		ret[22]= new Trait(8,"A person's outcome in life has more to do with luck than with what I do.");
		ret[23]= new Trait(8,"Pain can never be avoided.");
		ret[24]= new Trait(8, "While people can grow it a little, intelligence is largely fixed at birth.");
		ret[25]= new Trait(9,"People regret most decisions that they make.");
		ret[26]= new Trait(9,"Options should be kept open for as long as possible.");
		ret[27]= new Trait(9,"All options must always be researched before choosing.");
		return ret;
	}
		//@Param: Please let input be of even length ;-;
		public Question[] generateQuestions(Trait[] traitArray){
			ArrayList<Trait> myListOfTraits = new ArrayList<Trait>(Arrays.asList(traitArray));
			Question[] ret = new Question[traitArray.length/2];
			int index=0;
			while(myListOfTraits.size()>1){
				Question currentQuestion = new Question();
				currentQuestion.firstTrait = myListOfTraits.remove((int)(Math.random()*myListOfTraits.size()));
				currentQuestion.secondTrait = myListOfTraits.remove((int)(Math.random()*myListOfTraits.size()));
				//Outcome init'd to 0
				ret[index++] = currentQuestion;
			}
			return ret;
		}
		//@TODO Check for correctness
		public boolean[] evaluateMutators(Question[] answeredQuestions){
			int[] absoluteDimensions = new int[10];
			for(int i=0;i<answeredQuestions.length;i++){
				Question currentQuestion = answeredQuestions[i];
				switch(currentQuestion.outcome){
					//case 0: Do Nothing
					case 3:
						absoluteDimensions[currentQuestion.firstTrait.dimensionIndex]++;
					case 2:
						absoluteDimensions[currentQuestion.secondTrait.dimensionIndex]++;
						break;
					case 1:
						absoluteDimensions[currentQuestion.firstTrait.dimensionIndex]++;
						break;
				}
			}
			int[] relativeDimensions = new int[absoluteDimensions.length/2];
			for(int i=0;i<relativeDimensions.length;i++){
				relativeDimensions[i]=absoluteDimensions[(2*i)+1]-absoluteDimensions[2*i];
			}
			boolean[] ret = new boolean[10];
			for(int i=0;i<relativeDimensions.length;i++){
				if(relativeDimensions[i]>0){
					ret[i*2] = true;}
				else if(relativeDimensions[i]<0){
					ret[(i*2)+1] = true;
				}
			}
			return ret;
		}

}
