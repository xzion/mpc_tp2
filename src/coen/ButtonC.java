package coen;

import java.io.FileWriter;

public class ButtonC {
	
	public String ID;
	private boolean latching;
	private int loopInterval;
	private short[] sample;
	private String sampleLocation;	
	
	public String GetID()
	{
		return this.ID;
	}
	
	public short[] GetSample()
	{
		return this.sample.clone();
	}
	
	public boolean GetLatching()
	{
		return this.latching;
	}
	
	public int GetLoopInterval()
	{
		return this.loopInterval;
	}
	
	public String GetSampleLocation()
	{
		return this.sampleLocation;
	}
	
	public void SetSample(short[] newSample, String fn)
	{
		this.sample = newSample.clone();
		this.sampleLocation = fn;
	}
	
	public void SetLoopInterval(int li)
	{
		this.loopInterval = li;
	}
	
	public void SetLatching(boolean latch)
	{
		this.latching = latch;
	}
	
	/**OutputToFile
	 * Writes the button config to a file
	 * 
	 * @throws Exception - Bad Filename/Unable to create file
	 */
	public void OutputToFile() throws Exception
	{
		FileWriter outputFile = new FileWriter(this.ID);
		
		// First character is latching: 0 = latch, 1 = hold
		if (this.latching)
		{
			outputFile.write("1 ");
		}
		else 
		{
			outputFile.write("0 ");
		}
		
		// Second character is loop interval: number 1-5 for different intervals
		// Could be changed to the actual loop divider if easier
		outputFile.write(this.loopInterval + " ");
		
		// Output the length of the array, followed by the array itself
		// Currently output as spaced, signed shorts (-65536 <-> 65536)
		if (this.sample != null)
		{
			outputFile.write(sample.length + " ");
			for (int i = 0; i < sample.length; i++)
			{
				outputFile.write(sample[i] + " ");
			}
		}
		else
		{
			// The button hasn't been assigned
			// Output for the MPC will be a single 0
			outputFile.write("1 0");
		}
		outputFile.close();
	}
	
	public ButtonC (String id)
	{
		this.ID = id;
		this.latching = true;
		this.loopInterval = 1;
		this.sample = null;
		this.sampleLocation = null;		
	}

}