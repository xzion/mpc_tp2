package coen;

import sun.audio.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.*;

public class Model {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		File sourceAudio = new File("test.wav");
		File outputAudio = new File("out.wav");
		
		
		
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(sourceAudio);
		AudioFormat inputFormat = inputStream.getFormat();
		int numBytes = inputStream.available();
		byte[] buffer = new byte[numBytes];
		inputStream.read(buffer, 0, numBytes);
		
		AudioFileFormat outputFileFormat = AudioSystem.getAudioFileFormat(sourceAudio);
		AudioFileFormat.Type outputFileType = outputFileFormat.getType();
		AudioFormat outputFormat = new AudioFormat(inputFormat.getEncoding(), inputFormat.getSampleRate(), inputFormat.getSampleSizeInBits(), 1, inputFormat.getFrameSize(), inputFormat.getFrameRate(), inputFormat.isBigEndian());
		
		
		ByteArrayInputStream monoTrack = new ByteArrayInputStream(SplitStereoTrack(buffer));
		System.out.println(inputStream.getFormat());
		
		AudioInputStream outputAIS = new AudioInputStream(monoTrack, outputFormat, monoTrack.available()/outputFormat.getFrameSize());
		
		AudioSystem.write(outputAIS, outputFileType, outputAudio);
		
		BufferedWriter fileout = new BufferedWriter(new FileWriter(new File("out")));
		
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		while(bb.remaining() > 1){
			short current = bb.getShort();
			fileout.write(current);
			//fileout.newLine();
		}
		
		
		System.out.println(numBytes);
	}
	
	public static byte[] SplitStereoTrack(byte[] stereoTrack)
	{
		//System.out.println(stereoTrack.length);
		byte[] monoTrack = new byte[stereoTrack.length/2];
		
		for (int i = 0; i < stereoTrack.length/4; i++)
		{
			monoTrack[2*i] = stereoTrack[4*i];
			monoTrack[2*i+1] = stereoTrack[4*i+1];
		}
		System.out.println(monoTrack.length);
		return monoTrack.clone();
	}
	
	public static void ImportWavTrack(String fn)
	{
		File sourceAudio = new File(fn);
		
		
		
		
	}

}
