package com.gj.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Questionnaire {
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
		public Question(Trait myFirstTrait, Trait mySecondTrait){
			firstTrait=myFirstTrait;
			secondTrait=mySecondTrait;
			outcome = 0;
		}
	}
	
	public Questionnaire(){}
	public void Render(SpriteBatch batch,int x,int y){}
	public void Update(){


		/*Update code goes here.*/
		if(true/*finished with questionaire*/){
			Main.game_mode = Main.GameMode.DUNGEON_START;
		}
	}
	public void dispose(){}

	/*
		boolean[] mutators = new boolean[10];
		int[] dimensions = new int[5];

		ArrayList<Trait> listOfTraits = generateListOfTraits();
		Questions[] listOfQuestions = generateQuestions(listOfTraits);

		public ArrayList<Trait> generateListOfTraits(){
			ret = new ArrayList<Trait>();
			File questionFile = new File("rsc/questions.txt");
			if(questionFile.exists(){
				BufferedReader reader = new BufferedReader(new FileReader(questionFile));
				try{

				}
				catch (Exception e) {e.printStackTrace();}
			}
			else{
				//Cry
			}
		}
		//@Param: Please let input be of even length ;-;
		public Questions[] generateQuestions(ArrayList<Trait> myListOfTraits){
			Questions[] ret = new Questions[myListOfTraits.length()/2];
			ArrayList<Trait> temp = Arrays.copyOf(myListOfTraits);
			int index=0;
			while(temp.length()>0){
				Questions currentQuestion = new Question();
				currentQuestion.firstTrait = temp.remove((int)(Math.random()*temp.length()));
				currentQuestion.secondTrait = temp.remove((int)(Math.random()*temp.length()));
				ret[index] = currentQuestion;
			}
			return ret;
		}
		//@TODO Check for correctness
		public boolean[] evaluateMutators(Questions[] answeredQuestions){
			int[] absoluteDimensions = new int[10];
			for(int i=0;i<answeredQuestions.length;i++){
				Questions currentQuestion = answeredQuestions[i];
				switch(currentQuestion.outcome){
					//case 0: Do Nothing
					case 3:
						currentDimensions[Questions.firstTrait.index]++;
					case 2:
						currentDimensions[Questions.secondTrait.index]++;
						break;
					case 1:
						currentDimensions[Questions.firstTrait.index]++;
						break;
				}
			}
			int[] relativeDimensions = new int[absoluteDimensions/2];
			for(int i=0;i<relativeDimensions;i++){
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


	 */
	
}
