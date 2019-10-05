package hw4; //Lab 4 BINF6380 Jon Lee

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastaSequence
{
	public static List<FastaSequence> readFastaFile(String file) throws Exception
	{
		//instantiate list newFastaSequenceList to hold FastaSequence objects
		List<FastaSequence> newFastaSequenceList = new ArrayList<FastaSequence>();
		
		//open reader
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
		
		String line = reader.readLine();
		
		//read lines while they contain text
		while(line != null)
		{
			//append header line to seq_id
			String seq_id = "";
			if(line.charAt(0) == '>')
			{
				seq_id = line;
				line = reader.readLine();
			}
			
			//append sequence line(s) to full_seq
			String full_seq = "";
			while(line.charAt(0) != '>')
			{
				if(line.charAt(0) != '>')
				{
					full_seq = full_seq + line;
					line = reader.readLine();
				}
				
				if(line == null)
				{
					break;
				}
			}
			//instantiate a FastaSequence Object using seq_id and full_seq from reader loop
			FastaSequence newFastaSequenceObject = new FastaSequence(seq_id, full_seq);
			
			//add that new FastaSequence Object to the list
			newFastaSequenceList.add(newFastaSequenceObject);
		}
		//close reader
		reader.close();
		
		//return list of FastaSequence objects newFastaSequenceList
		return newFastaSequenceList;
	}
	
	//instantiate private strings for header and sequence for construction of class
	private final String header;
	private final String sequence;
	
	//constructor for FastaSequence
	public FastaSequence(String header, String sequence)
	{
		this.header = header;
		this.sequence = sequence;
	}
	
	//returns the header of this sequence without the “>”
	public String getHeader()
	{
		return header.substring(1);
	}
	
	//returns the DNA sequence of this FastaSequence
	public String getSequence()
	{
		return sequence;
	}
	
	//returns the number of G’s and C’s divided by the length of this sequence
	public float getGCRatio()
	{
		float seq_length = sequence.length();
		float num_GC = 0;
		
		for(int i=0; i<sequence.length(); i++)
		{
			if(sequence.charAt(i) == 'G' || sequence.charAt(i) == 'C')
			{
				num_GC++;
			}
		}
		return num_GC/seq_length;
	}
	
	//override toStroing() to print out header, sequence, and GCratio
	@Override
	public String toString()
	{
		return getHeader() + "\n" + getSequence() + "\n" + getGCRatio();
	}
	
	//override .equals() using sequence string as obj
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof FastaSequence))
		{
			return false;
		}
		FastaSequence other = (FastaSequence) obj;
		
		return other.sequence.equals(this.sequence);
	}
	
	//override hashCode() using String(string).hashCode()
	@Override
	public int hashCode()
	{
		return new String(sequence).hashCode();
	}
	
	public static void writeUniuqe(String inFile, String outFile) throws Exception
	{
		//instantiate HashMap mapFS for collection of FastaSequence objects
		Map<FastaSequence, Integer> mapFS = new HashMap<FastaSequence, Integer>();
		
		//instantiate listFS of FastaSequence objects
		List<FastaSequence> listFS = FastaSequence.readFastaFile(inFile);
		
		//put listFS elements in mapFS and count duplicates
		for(int i=0; i<listFS.size(); i++)
		{
			Integer count = mapFS.get(listFS.get(i));
			
			if(count == null)
			{
				count = 0;
			}
			
			count++;
			
			mapFS.put(listFS.get(i), count);
		}
		
		//open writer
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
		
		//write elements of mapFS in order by values
		for(FastaSequence key : mapFS.keySet())
        {
        	 for(int i=0; i<=Collections.max(mapFS.values()); i++)
        	 {
        		 if(i == mapFS.get(key))
        		 {
        			 writer.write(">" + mapFS.get(key) + "\n" + key.sequence + "\n");
        		 }
        	 }
        }
        //flush and close writer
		writer.flush(); writer.close();
	}
	
	//main method
	public static void main(String[] args) throws Exception
	{
		//instantiate fastaList of FastaSequences
		List<FastaSequence> fastaList = FastaSequence.readFastaFile("/Users/jonlee/Documents/BINF6380/src/hw4/test.fasta");
	
		//print .toString() for all FastaSequence objects in fastaList
		for(FastaSequence fs : fastaList)
		{
		System.out.println(fs.toString());
		}	
		
		//run .writeUnique() method of FastaSequence
		FastaSequence.writeUniuqe("/Users/jonlee/Documents/BINF6380/src/hw4/test.fasta", "/Users/jonlee/Documents/BINF6380/src/hw4/output.txt");
	}	
}