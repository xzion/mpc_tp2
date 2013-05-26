package coen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.*;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

import coen.Model.PlaybackWorker;

import sun.audio.AudioPlayer;

public class MPCC {
	
	// The array of button configurations
	private ArrayList<ButtonC> buttons;
	private ArrayList<String> sampleNames;
	private ArrayList<short[]> sampleArrays;
	private String configFn = "config";
	public char tempo;
	public char FX1mode;
	public char FX2mode;
	private static String[] audioFileNames = {"12.dat", "13.dat", "14.dat", "15.dat", "08.dat", "09.dat", "10.dat", "11.dat", "04.dat", "05.dat", "06.dat", "07.dat", "00.dat", "01.dat", "02.dat", "03.dat"};
	private static ArrayList<String> buttonOrder = new ArrayList<String>(Arrays.asList("13", "14", "15", "16", "9", "10", "11", "12", "5", "6", "7", "8", "1", "2", "3", "4"));
	
	public MPCC ()
	{
		// Initialize the buttons
		this.buttons = new ArrayList<ButtonC>();
		for (int i = 0; i < 16; i++)
		{
			this.buttons.add(new ButtonC(buttonOrder.get(i), audioFileNames[i]));
		}
		
		// Initialize the list for imported samples
		this.sampleArrays = new ArrayList<short[]>();
		this.sampleNames = new ArrayList<String>();
		
		// Initialize the MPC variables
		tempo = 0x80;
		FX1mode = 1;
		FX2mode = 2;
	}
	
	public ArrayList<ButtonC> getButtons() {
		return buttons;
	}
	
	public void AddSample(short[] arr, String fn)
	{
		if (!sampleNames.contains(fn))
		{
			sampleNames.add(fn);
			//sampleArrays.add(arr);
			sampleArrays.add(AudioP.scaleVolume(arr));
			System.out.println("Got Sample. len = " + String.valueOf(sampleNames.size()));
		}
	}
	
	public int getNumberSamples()
	{
		return sampleNames.size();
	}
	
	public String[] getSampleStrings()
	{
		return (String[]) sampleNames.toArray(new String[sampleNames.size()]);
	}
	
	public static int getMatchingButtonNumber(String btnString)
	{
		return buttonOrder.indexOf(btnString);
	}
	
	public short[] getSample(int sampleNumber)
	{
		return sampleArrays.get(sampleNumber);
	}
	
	public static String getMatchingButtonString(int btnNum)
	{
		return buttonOrder.get(btnNum);
	}
	
	public void ExportConfig(String path)
	{
		// Export each of the files, as well as the config file
		try
		{
			File dir = new File(path);
			dir.mkdirs();
			File outputFile = new File(dir, this.configFn);

			byte[] configBytes = new byte[35];
			configBytes[0] = (byte) FX1mode;
			configBytes[1] = (byte) FX2mode;
			configBytes[2] = (byte) tempo;
			
			int configCount = 3;		
			for (int i = 0; i < 16; i++)
			{
				// This will need changing cause buttons aren't in linear order
				configBytes[configCount] = (byte) this.buttons.get(Integer.valueOf(buttonOrder.get(i))-1).GetLatching();
				configCount++;
				configBytes[configCount] = (byte) this.buttons.get(Integer.valueOf(buttonOrder.get(i))-1).GetLoopInterval();
				configCount++;
				this.buttons.get(i).OutputToFile(path);
			}
			
			FileChannel out = new FileOutputStream(outputFile).getChannel();
			out.write(ByteBuffer.wrap(configBytes));
			out.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Alert message", "Title", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void ImportConfig(String path)
	{
		System.out.println("Got path " + path + "\\" + this.configFn);
		// Read each of the files in, overwrite any current data
		Path pt = Paths.get(path + "\\" + this.configFn);
		byte[] configData;
		try
		{
			configData = Files.readAllBytes(pt);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Could not open existing config", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return;
		}
		
		// Got the config
		this.FX1mode = (char) configData[0];
		this.FX2mode = (char) configData[1];
		this.tempo = (char)(((int)configData[2]+256)%256);
		
		System.out.println((int)this.FX1mode + " " + (int)this.FX2mode + " " + (int)this.tempo + " " + (int)configData[2]);
		
		for (int i = 0; i < 16; i++)
		{
			// Load latchHold
			ButtonC btn = buttons.get(Integer.valueOf(buttonOrder.get(i))-1);
			btn.SetLatching((char)configData[3+i*2]);
			btn.SetLoopInterval((char)configData[4+i*2]);
			
			// Load the sample, if it's there
			byte[] sampleData;
			Path samplePath = Paths.get(path + "\\" + audioFileNames[Integer.valueOf(buttonOrder.get(i))-1]);
			try
			{
				sampleData = Files.readAllBytes(samplePath);
				System.out.println(sampleData.length);
				if (sampleData.length > 1)
				{
					short[] sample = new short[sampleData.length/2];
					ByteBuffer.wrap(sampleData).order(ByteOrder.BIG_ENDIAN).asShortBuffer().get(sample);
					for (int j = 0; j < sample.length; j++)
					{
						if (sample[j] <= 0)
						{
							sample[j] = (short) (sample[j]-32768);
						} else
						{
							sample[j] = (short) (sample[j]+32768);
						}
						//sample[j] = (short)(((int)sample[j]+32768)%32768);
					}
					btn.SetSample(sample);
				} else
				{
					short[] dummyShort = new short[1];
					dummyShort[0] = 0;
					btn.SetSample(dummyShort);
				}
				
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Could not open track " + buttonOrder.get(i) + ", loaded blank", "Error", JOptionPane.WARNING_MESSAGE);
				short[] dummyShort = new short[1];
				dummyShort[0] = 0;
				btn.SetSample(dummyShort);
				e.printStackTrace();
			}
			
		}
	}
	
	

}
