package coen;

public class Model {

	/**
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
		AudioP.ExportSample(mp3outputFilename, sample1);
		
	}

}
