package coen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javazoom.jl.converter.*;

public class AudioP {
	
	/** SplitStereoTrack
	 * Reduce a stereo byte[] to a mono byte[]
	 * 
	 * @param stereoTrack - stereo byte[]
	 * @return mono byte[]
	 */
	public static byte[] SplitStereoTrack(byte[] stereoTrack)
	{
		System.out.println("Splitting stereo track");
		byte[] monoTrack = new byte[stereoTrack.length/2];
		
		for (int i = 0; i < stereoTrack.length/4; i++)
		{
			monoTrack[2*i] = stereoTrack[4*i];
			monoTrack[2*i+1] = stereoTrack[4*i+1];
		}
		return monoTrack.clone();
	}
	

	/** RescaleBitrate
	 * Rescales a lower bitrate sample up to 16bit
	 * 
	 * @param currentBitrate - int for current bit sampling
	 * @param currentSample - short[]to be re-sampled
	 */
	public static void RescaleBitrate(int currentBitrate, short[] currentSample)
	{
		System.out.println("Rescaling sample");
		for (int i = 0; i < currentSample.length; i++)
		{
			currentSample[i] = (short)(currentSample[i]/(2^currentBitrate)*(2^16));
		}
	}
	
	/** ExportSample
	 * Exports a sample as a mono WAV audio
	 * 
	 * @param fn - filename string
	 * @param sample - short[] of sample to be exported
	 */
	public static void ExportSample(String fn, short[] sample) throws Exception
	{
		System.out.println("Exporting Sample");
		
		// Standard output format. 16bit, 44.1kHz, mono
		AudioFormat outputFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float)44100.0, 16, 1, 2, 2, false);
		
		// Convert down to byte[] inputStream
		byte[] sampleBytes = new byte[sample.length*2];
		ByteBuffer.wrap(sampleBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(sample);
		ByteArrayInputStream outputSampleStream = new ByteArrayInputStream(sampleBytes);
		
		// Output
		File outputAudio = new File(fn);
		AudioInputStream outputAIS = new AudioInputStream(outputSampleStream, outputFormat, outputSampleStream.available()/outputFormat.getFrameSize());
		AudioSystem.write(outputAIS, AudioFileFormat.Type.WAVE, outputAudio);
		
	}
	
	/** ImportWavSample
	 * Import a WAV sample
	 * 
	 * @param fn - file location string
	 * @return short[] of audio track (16 bit, 44.1kHz, single channel)
	 * @throws Exception
	 */
	public static short[] ImportWavSample(String fn) throws Exception
	{
		System.out.println("Importing wav sample");
		
		File sourceAudio = new File(fn);
		
		// Initialize byte[]
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(sourceAudio);
		int numBytes = inputStream.available();
		byte[] tempBuffer = new byte[numBytes];
		inputStream.read(tempBuffer, 0, numBytes);
		
		// Check channels
		if (inputStream.getFormat().getChannels() > 2)
		{
			throw new Exception("Invalid file: Too many channels");
		} 
		else if (inputStream.getFormat().getChannels() == 2)
		{
			tempBuffer = SplitStereoTrack(tempBuffer);
		}
		
		// Check sample rate
		if (inputStream.getFormat().getSampleRate() != 44100.0)
		{
			throw new Exception("Invalid file: Bad sample rate (resample it yourself!)");
		}
		
		// Convert to short[]
		short[] finalBuffer = new short[tempBuffer.length/2];
		ByteBuffer.wrap(tempBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(finalBuffer);
		
		// Check bits
		if (inputStream.getFormat().getSampleSizeInBits() != 16)
		{
			if (inputStream.getFormat().getSampleSizeInBits() == AudioSystem.NOT_SPECIFIED || inputStream.getFormat().getSampleSizeInBits() > 16)
			{
				throw new Exception("Invalid file: Unable to identify bitrate");
			}
			else
			{
				RescaleBitrate(inputStream.getFormat().getSampleSizeInBits(), finalBuffer);
			}
		}
		
		return finalBuffer.clone();
	}
	
	public static short[] ImportMP3Sample(String fn) throws Exception
	{
		System.out.println("Converting MP3 Sample");
		// Use JLayer to convert to wav
		String tmpSample = "tmp/00.wav";
		Converter cvt = new Converter();
		cvt.convert(fn, tmpSample);
		
		// Import the wav
		File sourceAudio = new File(tmpSample);
		short[] newSample = ImportWavSample(tmpSample);
		
		// Remove the tempfile !!DOESNT WORK!!
		sourceAudio.delete();
		
		return newSample.clone();
	}

}
