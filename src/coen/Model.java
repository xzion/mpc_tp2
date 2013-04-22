package coen;

import javax.swing.*;

public class Model {

	/** Main function
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String sourceFilename = "samples/test48.wav";
		String wavoutputFilename = "wavout.wav";
		String mp3outputFilename = "mp3out.wav";
		
		short[] sample1 = AudioP.ImportWavSample(sourceFilename);
		AudioP.ExportSample(wavoutputFilename, sample1);
		
		System.out.println("");
		
		String mp3source = "samples/test48.mp3";
		short[] sample2 = AudioP.ImportMP3Sample(mp3source);
		//IOP.WriteShortArrayToFile(sample2, "test.txt");
		AudioP.ExportSample(mp3outputFilename, sample1);
		
		ButtonC btn1 = new ButtonC("00");
		btn1.SetSample(sample2, sourceFilename);
		btn1.OutputToFile();
		
		MPCConfig MPCModel = new MPCConfig();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI.CreateAndShowGUI();
			}
		});
		
	}
	
	public MPCConfig getModel()
	{
		
	}

}
