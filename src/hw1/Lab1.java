package hw1; //Lab 1 Jon Lee

import java.util.Random;

public class Lab1
{
	public static void main(String[] args) throws Exception
	{
		String seq = ""; //sequence string
		int aaa = 0; //to track AAA 3mer
		for(int x=0; x<1000; x++) //repeat 1000 times
		{	
			String trimer = ""; //3mer string to add to sequence
			for(int y=0; y<3; y++) //generate a 3mer
			{
				Random random = new Random(); //random number generator
				int z=random.nextInt(4);
				if(z==0)
				{
					trimer = trimer + "T";
				}
				if(z==1)
				{
					trimer = trimer + "C";
				}
				if(z==2)
				{
					trimer = trimer + "G";
				}
				if(z==3)
				{
					trimer = trimer + "A";
				}
			}
			seq = seq + trimer;
			if(trimer.equals("AAA"))
			{
				aaa++;	
			}
		}
		System.out.println("Sequence: "+seq);
		float len = seq.length();
		double expAAA = (0.25*0.25*0.25)*(len/3); //Expected number of AAA based on 1/4 probability of A 3 times
		System.out.println("Expected number for 3mer 'AAA': "+expAAA);
		System.out.println("Total number of times 3mer 'AAA' occured: "+aaa); //This number is roughly the expected value
		float cntT = 0;
		float cntC = 0;
		float cntG = 0;
		float cntA = 0;
		for(int i=0; i<seq.length(); i++)
		{
			if(seq.charAt(i)=='T')
			{
				cntT++;
			}
			if(seq.charAt(i)=='C')
			{
				cntC++;
			}
			if(seq.charAt(i)=='G')
			{
				cntG++;
			}
			if(seq.charAt(i)=='A')
			{
				cntA++;
			}
		}
		System.out.println("Total Sequence length: "+len);
		System.out.println("Total number of Ts: "+cntT);
		System.out.println("Total number of Cs: "+cntC);
		System.out.println("Total number of Gs: "+cntG);
		System.out.println("Total number of As: "+cntA);
		float probT = cntT/len;
		float probC = cntC/len;
		float probG = cntG/len;
		float probA = cntA/len;
		System.out.println("The probability of T is: "+probT); //roughly 0.25
		System.out.println("The probability of C is: "+probC); //roughly 0.25
		System.out.println("The probability of G is: "+probG); //roughly 0.25
		System.out.println("The probability of A is: "+probA); //roughly 0.25
	}
}