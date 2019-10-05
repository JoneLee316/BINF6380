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
		List<FastaSequence> newFastaSequenceList = new ArrayList<FastaSequence>();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));

		String line = reader.readLine();
		
		while(line != null)
		{
			String seq_id = "";
			
			if(line.charAt(0) == '>')
			{
				seq_id = seq_id + line;
				line = reader.readLine();
			}
			
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
			//instantiate a FastaSequence Object
			FastaSequence newFastaSequenceObject = new FastaSequence(seq_id, full_seq);
			
			//add that new FastaSequence Object to the list
			newFastaSequenceList.add(newFastaSequenceObject);
		}
		reader.close();
		return newFastaSequenceList;
	}
	
	private final String header;
	private final String sequence;
	
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
		Map<FastaSequence, Integer> mapFS = new HashMap<FastaSequence, Integer>();
		
		List<FastaSequence> listFS = FastaSequence.readFastaFile(inFile);
		
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
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
		
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
        
		writer.flush(); writer.close();
	}
	
	//main method
	public static void main(String[] args) throws Exception
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile("/Users/jonlee/Documents/BINF6380/src/hw4/test.fasta");
	
		for(FastaSequence fs : fastaList)
		{
		System.out.println(fs.toString());
		}	
		
		FastaSequence.writeUniuqe("/Users/jonlee/Documents/BINF6380/src/hw4/test.fasta", "/Users/jonlee/Documents/BINF6380/src/hw4/output.txt");
	}	
}
