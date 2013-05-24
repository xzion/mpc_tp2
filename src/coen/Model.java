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
		
		String samplePath = "C:/temp/";
		MPCModel.ExportConfig(samplePath);
		
		
		
		
		
		

		
		
//		String sourceFilename = "samples/Juicyh1.wav";
//		String wavoutputFilename = "wavout.wav";
//		String mp3outputFilename = "mp3out.wav";
//		
//		short[] sample1 = AudioP.ImportWavSample(sourceFilename);
//		AudioP.ExportSample(wavoutputFilename, sample1);
//		
//		System.out.println("");
//		
//		String mp3source = "samples/flume1.mp3";
//		short[] sample2 = AudioP.ImportMP3Sample(mp3source);
//		//IOP.WriteShortArrayToFile(sample2, "test.txt");
//		AudioP.ExportSample(mp3outputFilename, sample2);
			
//		for (int i = 0; i < 16; i++)
//		{
//			ButtonC btn = new ButtonC(audioFileNames[i]);
//			btn.SetSample(AudioP.GenerateSineWave(100*(1+i)), "flume");
//			btn.OutputToFile();
//		}
		
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
