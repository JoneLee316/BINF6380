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
		
		System.out.println("How many seconds do you want the quiz to run for?");
		String input = System.console().readLine();
		long timeinput = Long.parseLong(input);
		long time = timeinput*1000;
		
		long startTime = System.currentTimeMillis(); //define start time
		long endTime = startTime + time; //define end time
		
		do
		{
			int x = random.nextInt(20); //create random number for loop use
			System.out.println("What is the one letter code for "+FULL_NAMES[x]);
			String ans = System.console().readLine().toUpperCase(); //input from user
			if(ans.equals(SHORT_NAMES[x])) //if input is correct
			{
				score++; //increase score count
				System.out.println("Correct!\nTime remaining: "+((endTime-System.currentTimeMillis())/1000)+"sec");
			}
			
			else //if input is incorrect
			{
				System.out.println("Incorrect!\nCorrect answer is: "+SHORT_NAMES[x]);
				break;
			}
		} while(System.currentTimeMillis() <= endTime); //loop while the time is less than end time
		System.out.println("Times up!\nFinal score: "+score);
	}
}
