package coen;

import java.io.FileWriter;

public class IOP {
	
	public static void WriteShortArrayToFile(short[] sample, String fn) throws Exception
	{
		FileWriter outputFile = new FileWriter(fn);
		for (int i = 0; i < sample.length; i++)
		{
			outputFile.write(sample[i] + " ");
		}
		outputFile.close();
	}

}
