package hw3; //Lab 3 BINF6380 Jon Lee

//import all necessary packages
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;

public class Lab3
{
	public static void main(String[] args) throws Exception
	{
		//initiate reader to read through FASTA file
		BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/jonlee/Documents/BINF6380/src/hw3/test.fasta")));
		
		//declare lists that will be used during file parsing
		List<String> seqs_id = new ArrayList<String>();
		List<String> seqs = new ArrayList<String>();
		ArrayList<List<Integer>> num_bp = new ArrayList<List<Integer>>();
		
		//create string to read the lines of the files
		String line = reader.readLine();
		
		while(line != null)
		{
			if(line.charAt(0) == '>')
			{
				//add lines that start with '>' to seqs_id list
				seqs_id.add(line);
				
				//iterate to next line
				line = reader.readLine();
			}
			
			//intermediate string to add sequence lines too
			String full_seq = "";
			
			while(line.charAt(0) != '>')
			{
				if(line.charAt(0) != '>')
				{
					//add lines that make up the sequence to full_seq
					full_seq = full_seq + line;	
					
					//iterate to next line
					line = reader.readLine();
				}
				
				//if the next line is 'null' the loop breaks
				if(line == null)
				{
					break;
				}
			}
			//add full_seq to seqs list 
			seqs.add(full_seq);
			
			//declare variables for counting bps
			int num_T = 0;
			int num_C = 0;
			int num_G = 0;
			int num_A = 0;
			
			//iterate through sequence string and count bps
			for(int i=0; i<full_seq.length();)
			{
				if(full_seq.charAt(i) == 'T')
				{
					num_T++;
				}
				if(full_seq.charAt(i) == 'C')
				{
					num_C++;
				}
				if(full_seq.charAt(i) == 'G')
				{
					num_G++;
				}
				if(full_seq.charAt(i) == 'A')
				{
					num_A++;
				}
				i++;
			}
			//generate list of integers of bp counts and add them to the list
			List<Integer> num_TCGA = new ArrayList<Integer>();
			num_TCGA.add(num_T);
			num_TCGA.add(num_C);
			num_TCGA.add(num_G);
			num_TCGA.add(num_A);
			
			//add list of bp counts to the array list num_bp
			num_bp.add(num_TCGA);
		}
		//close reader
		reader.close();
		
		//print out all lists and array lists made during reader (for information only)
		System.out.println(seqs_id);
		System.out.println(seqs);
		System.out.println(num_bp);
		
		//initiate writer and generate output file in same location as input file (reader)
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/jonlee/Documents/BINF6380/src/hw3/output.txt")));
		
		//write header for the file
		writer.write("sequenceID"+"\t"+"numT"+"\t"+"numC"+"\t"+"numG"+"\t"+"numA"+"\t"+"sequence"+"\n");
		
		//for loop to write the elements of each list consecutively with a new line after each sequence
		for(int i=0; i<seqs_id.size(); i++)
		{
			String seq_id = seqs_id.get(i).toString();
			writer.write(seq_id.substring(1)+"\t");
			
			//for loop to write the num_bp array list
			for(int a=0; a<4; a++)
			{
				Object[] bp_counts = num_bp.get(i).toArray();
				writer.write(bp_counts[a]+"\t");
			}
			
			String seq = seqs.get(i).toString();
			writer.write(seq+"\n");
		}
		//flush and closer writer
		writer.flush(); writer.close();
	}
}