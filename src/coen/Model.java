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
		
		String[] audioFileNames = {"00.dat", "01.dat", "02.dat", "03.dat", "04.dat", "05.dat", "06.dat", "07.dat", "08.dat", "09.dat", "10.dat", "11.dat", "12.dat", "13.dat", "14.dat", "15.dat"};
		
		MPCModel = new MPCC();
		
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
		
		for (int i = 0; i < 16; i++)
		{
			ButtonC btn = new ButtonC(audioFileNames[i]);
			btn.SetSample(AudioP.GenerateSineWave(100*(1+i)), "blah");
			btn.OutputToFile();
		}
		
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				GUI.CreateAndShowGUI();
//			}
//		});
		
	}
	
	public static MPCC getModel()
	{
		return MPCModel;
	}

}
