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
	public static void main(String[] args)
	{
		Random random = new Random();
		int score = 0;
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + 30000;
		
		do
		{
			int x = random.nextInt(20);
			System.out.println("What is the one letter code for "+FULL_NAMES[x]);
			String ans = System.console().readLine().toUpperCase();
			if(ans.equals(SHORT_NAMES[x]))
			{
				score++;
				System.out.println("Correct!\nTime remaining: "+((endTime-System.currentTimeMillis())/1000)+"sec");
			}
			
			else
			{
				System.out.println("Incorrect!\nCorrect answer is: "+SHORT_NAMES[x]);
				break;
			}
		} while(System.currentTimeMillis() <= endTime);
		System.out.println("Times up!\nFinal score: "+score);
	}
}
