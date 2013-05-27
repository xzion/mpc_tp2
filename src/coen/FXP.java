package coen;

public class FXP {
	
	public static String[] fxNames = {"Delay", "Echo", "Playback Speed", "Decimator", "Bitcrusher"};
	
	public static short[] applyBitcrusher(short[] originalSample)
	{
		System.out.println("THATS THE BITC SHIT");
		int param = GUI.FXParam+1;
		short[] newSample = new short[originalSample.length];
		for (int i = 0; i < originalSample.length; i++)
		{
			newSample[i] = (short)(originalSample[i] / (param*100));
		}
		return AudioP.scaleVolume(newSample);
	}
	
	public static short[] applyDecimator(short[] originalSample)
	{
		System.out.println("THATS THE DECI SHIT");
		int param = GUI.FXParam/12+1;
		short[] newSample = new short[originalSample.length];
		for (int i = 0; i < originalSample.length/param; i++)
		{
			for(int j = 0; j < param; j++)
			{
				newSample[i*param+j] = originalSample[i*param];
			}
			
		}
		return newSample;
	}
	
	public static short[] applyDelay(short[] originalSample)
	{
		System.out.println("THATS THE DELAY SHIT");
		int param = GUI.FXParam*100;
		short[] newSample = new short[originalSample.length];
		for (int i = 0; i < originalSample.length; i++)
		{
			if (i > param)
			{
				newSample[i] = (short)((originalSample[i] + originalSample[i-param])/2);
			}
			else
			{
				newSample[i] = originalSample[i];
			}
		}
		return AudioP.scaleVolume(newSample);
	}
	
	public static short[] applyEcho(short[] originalSample)
	{
		System.out.println("THATS THE ECHO SHIT");
		int param = GUI.FXParam*200;
		short[] newSample = new short[originalSample.length];
		for (int i = 0; i < originalSample.length; i++)
		{
			if (i > param)
			{
				newSample[i] = (short)((originalSample[i] + newSample[i-param])/2);
			}
			else
			{
				newSample[i] = originalSample[i];
			}
		}
		return AudioP.scaleVolume(newSample);
	}
	
	public static short[] applyPlaybackSpeed(short[] originalSample)
	{
		System.out.println("THATS THE SPEEDY SHIT");
		int param = GUI.FXParam/16;
		if (param < 4)
		{
			// Slow it
			param = 4-param;
			short[] newSample = new short[originalSample.length*param];
			for (int i = 0; i < originalSample.length; i++)
			{
				for (int j = 0; j < param; j++)
				{
					newSample[i*param+j] = originalSample[i];
				}
			}
			return newSample;
		}
		else
		{
			// Speed up
			param = param-3;
			short[] newSample = new short[originalSample.length/param];
			for (int i = 0; i < originalSample.length/param; i++)
			{
				newSample[i] = originalSample[i*param];			
			}
			return newSample;
		}
		
	}

}
