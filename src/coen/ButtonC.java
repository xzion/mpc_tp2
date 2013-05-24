package coen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;

public class ButtonC {
	
	public String ID;
	private String fileName;
	private char latching;
	private char loopInterval;
	private short[] sample;	
	
	public String GetID()
	{
		return this.ID;
	}
	
	public short[] GetSample()
	{
		return this.sample.clone();
	}
	
	public char GetLatching()
	{
		return this.latching;
	}
	
	public char GetLoopInterval()
	{
		return this.loopInterval;
	}
	
	public void SetSample(short[] newSample)
	{
		this.sample = newSample.clone();
	}
	
	public void SetLoopInterval(char li)
	{
		this.loopInterval = li;
	}
	
	public void SetLatching(char latch)
	{
		this.latching = latch;
	}
	
	/**OutputToFile
	 * Writes the button config to a file
	 * 
	 * @throws Exception - Bad Filename/Unable to create file
	 */
	public void OutputToFile(String path) throws Exception
	{
		File dir = new File(path);
		dir.mkdirs();
		File outputFile = new File(dir, this.fileName);
		
		// Output the length of the array, followed by the array itself
		// Currently output as spaced, signed shorts (-65536 <-> 65536)
		if (this.sample != null)
		{
			short[] rescaledShorts = new short[sample.length];
			
			for (int i = 0; i < sample.length; i++)
			{
				if (sample[i] >= 0)
				{
					rescaledShorts[i] = (short) (sample[i]-32768);
				} else
				{
					rescaledShorts[i] = (short) (sample[i]+32768);
				}
			}
			
			ByteBuffer myByteBuffer = ByteBuffer.allocate(rescaledShorts.length*2);
			myByteBuffer.order(ByteOrder.BIG_ENDIAN);

			ShortBuffer myShortBuffer = myByteBuffer.asShortBuffer();
			myShortBuffer.put(rescaledShorts);

			FileChannel out = new FileOutputStream(outputFile).getChannel();
			out.write(myByteBuffer);
			out.close();
			
		}
		else
		{
			// The button hasn't been assigned
			// Output for the MPC will be a single 0
			//outputFile.write(0);
			FileWriter fw = new FileWriter(outputFile, false);
			fw.write(0);
			fw.close();
		}
	}
	
	public ButtonC (String id, String fn)
	{
		this.ID = id;
		this.fileName = fn;
		this.latching = 0x01;
		this.loopInterval = 0x02;
		this.sample = null;	
	}

}
