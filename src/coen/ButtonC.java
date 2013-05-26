package coen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;

import javax.sound.*;
import javax.sound.sampled.*;
import javax.swing.SwingWorker;

import sun.audio.*;

public class ButtonC {
	
	
	private String ID;
	private String fileName;
	private char latching;
	private char loopInterval;
	private short[] sample;
	private Waveform wf;
	private byte[] aStream;
	private int samplesPerPx;
	
	
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
		this.sample = newSample;
		this.wf = new Waveform(newSample);
		this.wf.addMouseListener(Listeners.WaveformClicked);
		this.aStream = AudioP.getStream(newSample);
		this.samplesPerPx = newSample.length/270;
	}
	
	public void SetLoopInterval(char li)
	{
		this.loopInterval = li;
	}
	
	public void SetLatching(char latch)
	{
		this.latching = latch;
	}
	
	public Waveform getWaveform()
	{
		return this.wf;
	}
	
	public void setPlayhead (int ph)
	{
		this.wf.setPlayhead(ph);
		//this.wf.getGraphics().clearRect(0, 0, 270, 60);
		this.wf.repaint();
	}
	
	public void sliceLeft()
	{
		int lengthOfNewSample = this.sample.length-(this.samplesPerPx*this.wf.getPlayhead());
		if (lengthOfNewSample == 0)
		{
			short[] dummyShort = new short[1];
			dummyShort[0] = 0;
			this.SetSample(dummyShort);
		}
		else
		{
			short[] newSample = new short[lengthOfNewSample];
			for (int i = 0; i < lengthOfNewSample; i++)
			{
				newSample[i] = this.sample[i+this.samplesPerPx*this.wf.getPlayhead()];
			}
			this.SetSample(newSample);
		}
	}
	
	public void sliceRight()
	{
		int lengthOfNewSample = this.sample.length-(this.samplesPerPx*(270-this.wf.getPlayhead()));
		if (lengthOfNewSample == 0)
		{
			short[] dummyShort = new short[1];
			dummyShort[0] = 0;
			this.SetSample(dummyShort);
		}
		else
		{
			short[] newSample = new short[lengthOfNewSample];
			for (int i = 0; i < lengthOfNewSample; i++)
			{
				newSample[i] = this.sample[i];
			}
			this.SetSample(newSample);
		}
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
		this.latching = 0x00;
		this.loopInterval = 0x00;
		this.sample = null;	
		short[] dummyShort = new short[1];
		dummyShort[0] = 0;
		this.wf = new Waveform(dummyShort);
		this.wf.addMouseListener(Listeners.WaveformClicked);
		this.samplesPerPx = 0;
	}

}
