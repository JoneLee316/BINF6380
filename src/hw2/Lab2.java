package hw2; //Lab 2 BINF6380 Jon Lee

//needs to be run in terminal command line, NOT eclipse

import java.util.Random;

public class Lab2
{
	public static String[] SHORT_NAMES = 
		{
		"A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V"
		};
	public static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"
		};
	public static void main(String[] args) throws Exception
	{
		Random random = new Random(); //random number generator
		int score = 0; //initiate score counter
		
		//have length of test in seconds be a user input
		System.out.println("How many seconds do you want the quiz to run for?");
		String input = System.console().readLine();
		long timeinput = Long.parseLong(input);
		long time = timeinput*1000;
		
		//define start and end time
		long startTime = System.currentTimeMillis();
		long endTime = startTime + time;
		
		//loop while the time is less than end time
		do
		{
			//create random number for loop use
			int x = random.nextInt(20);
			
			//ask for user input
			System.out.println("What is the one letter code for "+FULL_NAMES[x]);
			String ans = System.console().readLine().toUpperCase();
			
			if(ans.equals(SHORT_NAMES[x])) //if input is correct
			{
				score++; //increase score count
				System.out.println("Correct!\nTime remaining: "+((endTime-System.currentTimeMillis())/1000)+"sec");
			}
			
			else //if input is incorrect
			{
				System.out.println("Incorrect!\nCorrect answer is: "+SHORT_NAMES[x]);
				break; //break out of loop
			}
		} while(System.currentTimeMillis() <= endTime);
		
		//print final score at end when time is up or loop breaks 
		System.out.println("Times up!\nFinal score: "+score);
	}
}
