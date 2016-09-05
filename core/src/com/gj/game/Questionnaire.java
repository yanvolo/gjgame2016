package com.gj.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;

public class Questionnaire {

	//GDX Tools
	public static Questionnaire instance;
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
	public String[][] dialogueSections;
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
		mutators  = new boolean[10];
		instance = this;
		questionFont = new BitmapFont();
		questionFont.setColor(Color.WHITE);
		batch = new SpriteBatch();
		//Prepare for Questioning
		listOfTraits= generateListOfTraits();
		listOfQuestions = generateQuestions(listOfTraits);
		questionIndex = 0;
		//Prepare for Chatting
		dialogueSections = new String[9][];
		dialogueSections[0] = new String[]{
			"[Press enter to continue to final level]",
			"Good, you can understand simple instructions",
			"...",
			"Who am I? Well I am the final boss, of course.",
			"Your job is to beat me, to win the day, and to be the hero.",
			"How do you do that?",
			"¯\\_(ツ)_/¯",
			"I don't see how you could do so",
			"Probably wouldn’t even tell you even if I knew"
		};
		dialogueSections[1] = new String[]{
			"...",
			"But I guess I should do something interesting",
			"Just so we have something to do",
			"So tell you what",
			"Complete a little game of mine, and I will let you through",
			"You can chicken out and just close the window",
			"but what would be the fun in that?",
			"So ready? No take backsies."
		};
		dialogueSections[2] = new String[]{
			"Good",
			"So, here’s the challenge:" ,
			"In a second, I will present you with a series of choices",
			"Simply select the choice you more agree with",
			"or choose you agree with both",
			"or neither. That’s fine too",
			"Also, please be honest.",
			"I can’t stop you from lying",
			"But the only person you would be lying to is yourself.",
			"...",
			"And besides",
			"This will be more fun if you tell the truth..."
		};
		dialogueSections[4] = new String[]{
			"I’m sure you see what I'm doing here",
			"But I actually didn’t do anything",
			"I didn’t have to",
			"All I had to do was tell you what to see",
			"What I wanted you to see",
			"And then let yourself do all the work for me.",
			"Because as you can see now",
			"All I have to do",
			"Is let you tear yourself down"
		};
		dialogueSections[5] = new String[]{
			"And then you died.",
			"Tragic, really.",
			"But, you see, it doesn’t have to end like this",
			"Because everything I told you earlier…",
			"All of that you let mull in your head",
			"Yea, I made that up on the spot",
			"Really, everything I said applies to everyone, and no one",
			"Like a terrible horoscope",
			"But that doesn’t make those ideas less destructive."
		};
		dialogueSections[7] = new String[]{
			"I can see this can feel a bit hoke.",
			"That’s one way of looking at it.",
			"But what if you looked again?",
			"What do you see?"
		};
		dialogueSections[8] = new String[]{
			"You win",
			"...",
			"...",
			"A bit anticlimactic, don’t you think?",
			"So how about we make our own ending?",
			"One not made by a group of guys in a 48 hour game jam.",
			"Instad, take a look around you",
			"What do you see?"
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
			questionFont.draw(batch, dialogueSections[dialogueSectionNumber][i],X_BUFFER,(LINE_HEIGHT*(dialogueIndex-i))+BOTTOM_HEIGHT);
		}
	}
	private void stuffArrays(int index7, int index4, String[] arr7, String[] arr4,
							   String firstString7, String secondString7, String String4){
		arr7[index7] = firstString7;
		arr7[index7+1] = secondString7;
		arr4[index4] = String4;
	}
	public void Update(){
		if(currentSection == Section.CALCULATING){
			mutators=evaluateMutators(listOfQuestions);
			boolean hasAnswer = false;
			int answerCount = 0;
			for(int i=0;i<mutators.length;i++){
				if(mutators[i]){
					answerCount++;
				}
			}
			if(answerCount==0){
				questionIndex = 0;
				currentSection = Section.QUESTIONING;
				dialogueSections[2] = new String[]{
					"Come on, I need something to work with here",
					"Look at these questions again, and think real hard about an answer this time"
				};

			}
			else{
				String[] part4 = new String[answerCount+4];
				int index4 = 0;
				part4[index4++] = "Well, well.";
				part4[index4++] = "This is interesting";
				part4[index4++] = "Here’s what I see";
				String[] part7 = new String[((answerCount*2)+2)];
				int index7 = 0;
				part7[index7++] = "Perhaps you agreed with some of the things I said";
				part7[index7++] = "If that’s the case, then think about this";
				if(mutators[0]){
					stuffArrays(index7++,index4++,part7,part4,
							"Maybe you are a cynic.",
							"Or maybe you just want a better world.",
							"A bit of a cynic, are we? See people for their basest desires?");
					index7++;
				}
				if(mutators[1]){
					stuffArrays(index7++,index4++,part7,part4,
							"Maybe you are just out there for the glory",
							"Or maybe you just want to be the best you can be",
							"A bit of a status seeker, I see? Stuck in the rat race?");
					index7++;
				}
				if(mutators[2]){
					stuffArrays(index7++,index4++,part7,part4,
							"Maybe you feel that if you try, you will fail, but who doesn’t?",
							"And surely, a little extra faith in yourself could change everything",
							"Someone who lacks faith in themselves");
					index7++;
				}
				if(mutators[3]){
					stuffArrays(index7++,index4++,part7,part4,
							"Maybe you think the world singles you out, but who doesn’t?",
							"And surely you have the power to change that?",
							"Someone who blames the unfair world");
					index7++;
				}
				if(mutators[4]){
					stuffArrays(index7++,index4++,part7,part4,
							"Sure, you might feel isolated from the world",
							"Or maybe you feel more lonely than you really are",
							"A very guarded individual");
					index7++;
				}
				if(mutators[5]){
					stuffArrays(index7++,index4++,part7,part4,
							"Sure, it might feel the world will leave you behind",
							"Or maybe you feel more abandoned than you really are",
							"A very clingy individual");
					index7++;
				}
				if(mutators[6]){
					stuffArrays(index7++,index4++,part7,part4,
							"Sometimes you reminisce too much.",
							"But every great story is built on what came before.",
							"Stuck in the past");
					index7++;
				}
				if(mutators[7]){
					stuffArrays(index7++,index4++,part7,part4,
							"Sometimes you daydream too much",
							"But every great story starts with a dream",
							"Stuck daydreaming");
					index7++;
				}
				if(mutators[8]){
					stuffArrays(index7++,index4++,part7,part4,
							"And finally, maybe you feel stuck",
							"But that just means there is opportunity you have failed to see",
							"And worst of all, there’s just not a lot of options out there");
					index7++;
				}
				if(mutators[9]){
					stuffArrays(index7++,index4++,part7,part4,
							"And finally, maybe you feel overwhelmed",
							"But that just means you have been thoughtful about your choices.",
							"And worst of all, there’s just too many options out there");
					index7++;
				}
				part4[index4++] = "What do you see?";
				dialogueSections[3] = part4;
				dialogueSections[6] = part7;
				currentSection = Section.CHATTING;
				dialogueSectionNumber = 3;
				System.out.println(Arrays.toString(mutators));
				System.out.println(Arrays.toString(dialogueSections[3]));
			}

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
			System.out.println("Dialogue Index: "+ dialogueIndex);
			if(dialogueIndex> dialogueSections[dialogueSectionNumber].length){
				dialogueIndex = 1;
				dialogueSectionNumber++;
				System.out.println("Section Number: "+ dialogueSectionNumber);
				switch (dialogueSectionNumber){
					case 4:
					case 5:
					case 9:
						currentSection= Section.DONE;
						break;
					case 3:
						currentSection = Section.QUESTIONING;
						break;
					case 8:
						System.exit(0);
					case 1:
					case 2:
					case 6:
					case 7:
					default:
						break;
				}
			}
		}
		else if(currentSection == Section.DONE){
			Main.game_mode = Main.GameMode.DUNGEON_START;
			currentSection = Section.CHATTING;
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
		public boolean isCynical(){
			return mutators[0];
		}
		public boolean isHoarder(){
			return mutators[1];
		}
		public boolean isSelfDistrustful(){
			return mutators[2];
		}
		public boolean isExternalizing(){
			return mutators[3];
		}
		public boolean isGuarded(){
			return mutators[4];
		}
		public boolean isIntense(){
			return mutators[5];
		}
		public boolean isPast(){
			return mutators[6];
		}
		public boolean isDaydreamer(){
			return mutators[7];
		}
		public boolean isFatalist(){
			return mutators[8];
		}
		public boolean isIndecisive(){
			return mutators[9];
		}

}
