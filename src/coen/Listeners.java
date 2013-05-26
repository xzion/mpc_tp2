package coen;


import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Listeners {
	
	private static String getExt(String fn)
	{
		// Simple method to return file extension
		String ext = null;
        String s = fn;
        int i = s.lastIndexOf('.');
 
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
	}
	
	public static ActionListener ImportFileFromMenu = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
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
					if (getExt(fc.getSelectedFile().getName()).equals("mp3"))
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
		}
	};
	
	public static ActionListener OpenMPCConfig = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Open a filechooser to find the directory
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog(GUI.masterFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				// Attempt an import from the select directory
				Model.getModel().ImportConfig(fc.getSelectedFile().getAbsolutePath());
			}
			
			// Rebuild the GUI
			GUI.RebuildGUI();
		}
	};
	
	public static ActionListener ExportMPCConfig = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Open a filechooser save dialogue
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(GUI.masterFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				// Export the model into the directory
				Model.getModel().ExportConfig(fc.getSelectedFile().getAbsolutePath());
			}
		}
	};
	
	public static ActionListener ExitApplication = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// EXIT
			System.exit(0);
		}
	};
	
	public static ActionListener ButtonSelection = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Change the currently selected button
			Model.player.cancel(true);
			GUI.currentButton = MPCC.getMatchingButtonNumber(((JButton)e.getSource()).getText());
			System.out.println("Pressed " + ((JButton)e.getSource()).getText() + " ID = "+ String.valueOf(GUI.currentButton));
			
			GUI.RebuildGUI();
		}
	};
	
	public static ActionListener LatchHoldChanged = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Change the value on the button
			Model.getModel().getButtons().get(GUI.currentButton).SetLatching((char)((JComboBox<String>)e.getSource()).getSelectedIndex());
			//System.out.println("changed LH to " + String.valueOf((int)Model.getModel().getButtons().get(GUI.currentButton).GetLatching()));
		}
	};
	
	public static ActionListener LoopIntervalChanged = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Change the value on the button
			Model.getModel().getButtons().get(GUI.currentButton).SetLoopInterval((char)((JComboBox<String>)e.getSource()).getSelectedIndex());
			System.out.println("changed loop to " + String.valueOf((int)Model.getModel().getButtons().get(GUI.currentButton).GetLoopInterval()));
		}
	};
	
	public static ActionListener TempoChanged = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Change the value MPC Model
			Model.getModel().tempo = (char)(((JComboBox<String>)e.getSource()).getSelectedIndex()+10);
			System.out.println("changed tempo to " + String.valueOf((int)Model.getModel().tempo));
		}
	};
	
	public static ActionListener FX1Changed = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Change the value on MPC Model
			Model.getModel().FX1mode = (char)((JComboBox<String>)e.getSource()).getSelectedIndex();
			System.out.println("changed FX1 to " + String.valueOf((int)Model.getModel().FX1mode));
		}
	};
	
	public static ActionListener FX2Changed = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Change the value on MPC Model
			Model.getModel().FX2mode = (char)((JComboBox<String>)e.getSource()).getSelectedIndex();
			System.out.println("changed FX2 to " + String.valueOf((int)Model.getModel().FX2mode));
		}
	};
	
	public static ActionListener SampleChanged = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Keep track of selections
			GUI.currentSample = ((JComboBox<String>)e.getSource()).getSelectedIndex();
			System.out.println("changed sample to " + String.valueOf(GUI.currentSample));
		}
	};
	
	public static ActionListener LoadSampleToButton = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Push the sample into the button model
			short[] sample = Model.getModel().getSample(GUI.currentSample);
			Model.getModel().getButtons().get(GUI.currentButton).SetSample(sample);
			
			GUI.RebuildGUI();
		}
	};
	
	public static ActionListener WaveformSelectorChanged = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Keep track of selections
			GUI.currentWaveform = ((JComboBox<String>)e.getSource()).getSelectedIndex();
			System.out.println("changed waveform dropdown to " + String.valueOf(GUI.currentButton));
		}
	};
	
	public static ActionListener LoadWaveform = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Load the waveform on the secondary panel
			short[] sample = Model.getModel().getSample(GUI.currentSample);
			GUI.viewerWaveforms[GUI.currentWaveform] = new Waveform(sample);
			GUI.loadWaveformTabFirst = 1;
			
			GUI.RebuildGUI();
		}
	};
	
	public static MouseListener WaveformClicked = new MouseListener()
	{
		public void mousePressed(MouseEvent e) {
	    }
	     
	    public void mouseReleased(MouseEvent e) {
	    }
	     
	    public void mouseEntered(MouseEvent e) {
	    }
	     
	    public void mouseExited(MouseEvent e) {
	    }
	     
	    public void mouseClicked(MouseEvent e) {
	    	// Move the pointer
	    	System.out.println("CLICKED " + e.getX() + " " + e.getY());
	    	Model.getModel().getButtons().get(GUI.currentButton).getWaveform().setPlayhead(e.getX());
	    	
	    	GUI.RebuildGUI();
	    }
	};
	
	public static ActionListener PlayButtonSample = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Play the sample in the player
			if (Model.playing == 0)
			{
				Model.playbackArray = Model.getModel().getButtons().get(GUI.currentButton).GetSample();
				Model.player = new Model.PlaybackWorker();
				Model.player.execute();
				Model.playing = 1;
			}
		}
	};
	
	public static ActionListener PauseButtonSample = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Pause sample
			Model.player.cancel(true);
		}
	};
	
	public static ActionListener ResetButtonSample = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Reset the playhead
			Model.player.cancel(true);
			while(Model.playing == 1);
			try {
				Thread.sleep(300);
			}
			catch (Exception exc){}
			
			Model.getModel().getButtons().get(GUI.currentButton).setPlayhead(0);
		}
	};
	
	public static ActionListener SliceSampleLeft = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Slice left
			Model.getModel().getButtons().get(GUI.currentButton).sliceLeft();
			
			GUI.RebuildGUI();
		}
	};
	
	public static ActionListener SliceSampleRight = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Cancel player and slice right
			Model.player.cancel(true);
			while(Model.playing == 1);
			try {
				Thread.sleep(300);
			}
			catch (Exception exc){}
			Model.getModel().getButtons().get(GUI.currentButton).sliceRight();
			
			GUI.RebuildGUI();
		}
	};
	
	public static ActionListener ClearSample = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Cancel track and clear sample
			Model.player.cancel(true);
			while(Model.playing == 1);
			try {
				Thread.sleep(300);
			}
			catch (Exception exc){}
			Model.getModel().getButtons().get(GUI.currentButton).setPlayhead(0);
			Model.getModel().getButtons().get(GUI.currentButton).sliceRight();
			
			GUI.RebuildGUI();
		}
	};
	
	public static ChangeListener FXParamChanged = new ChangeListener()
	{
		public void stateChanged(ChangeEvent e)
		{			
			// Monitor variable
			GUI.FXParam = ((JSlider)e.getSource()).getValue();
		}
	};
	
	public static ActionListener PreviewSampleFX = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			System.out.println("preview " + GUI.FXMode);
			// Play the sample in the player
			if (Model.playing == 0)
			{
				switch (GUI.FXMode)
				{
				case 0:
					Model.playbackArray = FXP.applyDelay(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
					break;
				case 1:
					Model.playbackArray = FXP.applyEcho(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
					break;
				case 2:
					Model.playbackArray = FXP.applyPlaybackSpeed(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
					break;
				case 3:
					Model.playbackArray = FXP.applyDecimator(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
					break;					
				case 4:
					Model.playbackArray = FXP.applyBitcrusher(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
					break;					
				default:
					break;
				}
				Model.player = new Model.PlaybackWorker();
				Model.player.execute();
				Model.playing = 1;
			}
		}
	};
	
	public static ActionListener FXModeChanged = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Keep track of selections
			GUI.FXMode = ((JComboBox<String>)e.getSource()).getSelectedIndex();
			System.out.println("changed FX dropdown to " + String.valueOf(GUI.FXMode));
		}
	};
	
	public static ActionListener ApplyFXToButton = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{			
			// Push the sample into the button model
			short[] sample;
			switch (GUI.FXMode)
			{
			case 0:
				sample = FXP.applyDelay(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
				break;
			case 1:
				sample = FXP.applyEcho(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
				break;
			case 2:
				sample = FXP.applyPlaybackSpeed(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
				break;
			case 3:
				sample = FXP.applyDecimator(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
				break;					
			case 4:
				sample = FXP.applyBitcrusher(Model.getModel().getButtons().get(GUI.currentButton).GetSample());
				break;					
			default:
				sample = new short[1];
				sample[0] = 0;
				break;
			}
			Model.getModel().getButtons().get(GUI.currentButton).SetSample(sample);
			
			GUI.RebuildGUI();
		}
	};
	
	
	
	
	

}
