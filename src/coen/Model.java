package coen;

public class Model {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String sourceFilename = "samples/test.wav";
		String outputFilename = "out.wav";
		
		//short[] sample1 = AudioP.ImportWavSample(sourceFilename);
		//AudioP.ExportSample(outputFilename, sample1);
		
		String mp3source = "samples/barbie.mp3";
		short[] sample1 = AudioP.ImportMP3Sample(mp3source);
		AudioP.ExportSample(outputFilename, sample1);
		
	}

}
