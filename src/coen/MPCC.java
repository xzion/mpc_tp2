package coen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MPCC {
	
	// The array of button configurations
	private ArrayList<ButtonC> buttons;
	private ArrayList<String> sampleNames;
	private ArrayList<short[]> sampleArrays;
	private String configFn = "config";
	private char tempo;
	private char FX1mode;
	private char FX2mode;
	
	public MPCC ()
	{
		String[] audioFileNames = {"00.dat", "01.dat", "02.dat", "03.dat", "04.dat", "05.dat", "06.dat", "07.dat", "08.dat", "09.dat", "10.dat", "11.dat", "12.dat", "13.dat", "14.dat", "15.dat"};
		// Initialize the buttons
		this.buttons = new ArrayList<ButtonC>();
		for (int i = 0; i < 16; i++)
		{
			this.buttons.add(new ButtonC(String.valueOf(i), audioFileNames[i]));
		}
		
		// Initialize the list for imported samples
		this.sampleArrays = new ArrayList<short[]>();
		this.sampleNames = new ArrayList<String>();
		
		// Initialize the MPC variables
		tempo = 0x80;
		FX1mode = 0;
		FX2mode = 0;
	}
	
	public ArrayList<ButtonC> getButtons() {
		return buttons;
	}
	
	public void AddSample(short[] arr, String fn)
	{
		if (!sampleNames.contains(fn))
		{
			sampleNames.add(fn);
			sampleArrays.add(arr);
		}
	}
	
	public void ExportConfig(String path) throws Exception
	{
		// Export each of the files, as well as the config file
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
			configBytes[configCount] = (byte) this.buttons.get(i).GetLatching();
			configCount++;
			configBytes[configCount] = (byte) this.buttons.get(i).GetLoopInterval();
			configCount++;
			this.buttons.get(i).OutputToFile(path);
		}
		
		FileChannel out = new FileOutputStream(outputFile).getChannel();
		out.write(ByteBuffer.wrap(configBytes));
		out.close();
		
	}
	
	
	
	

}
