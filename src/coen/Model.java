package coen;

import java.util.ArrayList;

public class Model {

	/** Main function
	 * @param args
	 */
	
	private static MPCC MPCModel;
	private static ArrayList<ImportedSample> importedSamples;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		MPCModel = new MPCC();
		
//		String sourceFilename = "samples/test48.wav";
//		String wavoutputFilename = "wavout.wav";
//		String mp3outputFilename = "mp3out.wav";
//		
//		short[] sample1 = AudioP.ImportWavSample(sourceFilename);
//		AudioP.ExportSample(wavoutputFilename, sample1);
//		
//		System.out.println("");
//		
//		String mp3source = "samples/test48.mp3";
//		short[] sample2 = AudioP.ImportMP3Sample(mp3source);
//		//IOP.WriteShortArrayToFile(sample2, "test.txt");
//		AudioP.ExportSample(mp3outputFilename, sample1);
//		
//		ButtonC btn1 = new ButtonC("00");
//		btn1.SetSample(sample2, sourceFilename);
//		btn1.OutputToFile();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI.CreateAndShowGUI();
			}
		});
		
	}
	
	public static MPCC getModel()
	{
		return MPCModel;
	}

}
