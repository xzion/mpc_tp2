package coen;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Model {

	/** Main function
	 * @param args
	 */
	private static final AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, true);
	private static MPCC MPCModel;
	private static ArrayList<ImportedSample> importedSamples;
	public static PlaybackWorker player;
	public static int playing = 0;
	public static short[] playbackArray;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		MPCModel = new MPCC();
		player = new PlaybackWorker();
		
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
	
	public static class PlaybackWorker extends SwingWorker<Integer, Integer>
	{
	    protected Integer doInBackground() throws Exception
	    {
	        // Do a time-consuming task.
	    	ButtonC currentBtn = Model.getModel().getButtons().get(GUI.currentButton);
	    	//byte[] sampleForPlayback = AudioP.getStream(currentBtn.GetSample());
	    	byte[] sampleForPlayback = AudioP.getStream(Model.playbackArray);
	    	try {
	    		SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
	    		int samplesPerPx = sampleForPlayback.length/270 - (sampleForPlayback.length/270)%2;
	            line.open(audioFormat);
	            line.start();
	            for (int i = currentBtn.getWaveform().getPlayhead(); i < 270; i++)
	            {
	            	if (this.isCancelled())
	            	{
	            		line.drain();
	    	            line.close();
	            		return 0;
	            	}
	            	line.write(sampleForPlayback, i*samplesPerPx, samplesPerPx);
	            	Model.getModel().getButtons().get(GUI.currentButton).setPlayhead(i);
	            }
	            line.drain();
	            line.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	return 0;
	    }

	    protected void done()
	    {
	    	Model.playing = 0;
	    	System.out.println("sample done");
	    }
	}
	
	public static class ImportWorker extends SwingWorker<Integer, Integer>
	{
	    protected Integer doInBackground() throws Exception
	    {
	    	// Open a filechooser, show audio files only
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files", "mp3", "wav");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(GUI.masterFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					// Check type and import
					short[] sampleArr;
					if (Listeners.getExt(fc.getSelectedFile().getName()).equals("mp3"))
					{
						System.out.println("MP3 recieved");
						sampleArr = AudioP.ImportMP3Sample(fc.getSelectedFile().getAbsolutePath());
					}
					else
					{
						System.out.println("WAV recieved");
						sampleArr = AudioP.ImportWavSample(fc.getSelectedFile().getAbsolutePath());
					}
					Model.getModel().AddSample(sampleArr, fc.getSelectedFile().getName());
					
					// Rebuild the GUI
					GUI.RebuildGUI();					
				}
				catch (Exception exc)
				{
					JOptionPane.showMessageDialog(null, "Unable to import selected file", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
	    	return 0;
	    }

	    protected void done()
	    {
	    	System.out.println("Load done");
	    }
	}

}
