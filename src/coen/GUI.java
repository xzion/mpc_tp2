package coen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
	
	public static JFrame masterFrame;
	//public static JPanel totalGUI;
	
	public static int currentButton;
	public static int currentSample;
	public static int currentWaveform;
	public static Waveform[] viewerWaveforms;
	public static int loadWaveformTabFirst;
	public static int FXParam;
	public static int FXMode;
	
	public static void CreateAndShowGUI() {
		// Initialize the globals
		currentButton = 12;
		currentSample = 0;
		currentWaveform = 0;
		loadWaveformTabFirst = 0;
		FXParam = 63;
		FXMode = 0;
		viewerWaveforms = new Waveform[5];
		for (int i = 0; i < 5; i++)
		{
			short[] dummyShort = new short[1];
			dummyShort[0] = 0;
			viewerWaveforms[i] = new Waveform(dummyShort);
		}
		
		// Build the frame
		masterFrame = new JFrame("Team 23 MPC Config");
		masterFrame.setLayout(new FlowLayout());
		masterFrame.setPreferredSize(new Dimension(640, 480));
		masterFrame.setResizable(false);
		masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = createContentPane();
		
		masterFrame.setJMenuBar(createMenuBar());
		masterFrame.getContentPane().add(pane);
		masterFrame.pack();
		masterFrame.setVisible(true);
	}
	
	public static void RebuildGUI()
	{
		masterFrame.getContentPane().removeAll();
		
		JPanel pane = createContentPane();
		masterFrame.setJMenuBar(createMenuBar());
		masterFrame.getContentPane().add(pane);
		
		masterFrame.revalidate();
		masterFrame.repaint();
	}
	
	private static JPanel createContentPane() {
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4, 4, 4, 4);
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		totalGUI.add(createLeftPane(), c);
		
		
		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 0;
		totalGUI.add(createButtonPane(), c);
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.gridx = 1;
		c.gridy = 1;
		totalGUI.add(createExportPane(), c);
		
		return totalGUI;
	}
	
	private static JPanel createButtonPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		for (int i = 0; i < 16; i++) {
			JButton button = new JButton(Model.getModel().getButtons().get(i).GetID());
			button.setPreferredSize(new Dimension(60, 60));
			c.gridx = i%4;
			c.gridy = i/4;
			c.insets = new Insets(4, 4, 4, 4);
			button.addActionListener(Listeners.ButtonSelection);
			pane.add(button, c);
		}
		return pane;
	}
	
	private static JPanel createConfigPanel() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4, 4, 4, 4);

		ButtonC currentBtn = Model.getModel().getButtons().get(GUI.currentButton);
		JLabel label;
		JButton button;
		JComboBox<String> cb;
		JSlider slide;
		
		label = new JLabel("Button " + currentBtn.GetID());
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);

		cb = new JComboBox<String>(Model.getModel().getSampleStrings());
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 2;
		cb.setPreferredSize(new Dimension(190, 25));
		if (GUI.currentSample != 0)
		{
			cb.setSelectedIndex(GUI.currentSample);
		}
		cb.addActionListener(Listeners.SampleChanged);
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.LoadSampleToButton);
		pane.add(button, c);
		
		Waveform wf = currentBtn.getWaveform();
		wf.setPreferredSize(new Dimension(270, 60));
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 5;
		
		pane.add(wf, c);
		
		button = new JButton("Play");
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.PlayButtonSample);
		pane.add(button, c);
		
		button = new JButton("Pause");
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.PauseButtonSample);
		pane.add(button, c);
		
		button = new JButton("Reset");
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.ResetButtonSample);
		pane.add(button, c);
		
		button = new JButton("Slice Left");
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.SliceSampleLeft);
		pane.add(button, c);
		
		button = new JButton("Slice Right");
		c.gridx = 1;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.SliceSampleRight);
		pane.add(button, c);
		
		button = new JButton("Clear");
		c.gridx = 2;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.ClearSample);
		pane.add(button, c);
		
		label = new JLabel("FX:");
		c.gridx = 0;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		cb = new JComboBox<String>(FXP.fxNames);
		c.gridx = 1;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 2;
		cb.setPreferredSize(new Dimension(190, 25));
		cb.setSelectedIndex(FXMode);
		cb.addActionListener(Listeners.FXModeChanged);
		pane.add(cb, c);
		
		label = new JLabel("Variation:");
		c.gridx = 0;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		slide = new JSlider(JSlider.HORIZONTAL, 0, 127, 63);
		slide.setValue(FXParam);
		c.gridx = 1;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 2;
		slide.setPreferredSize(new Dimension(190, 25));
		slide.addChangeListener(Listeners.FXParamChanged);
		pane.add(slide, c);
		
		button = new JButton("Preview");
		c.gridx = 0;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.PreviewSampleFX);
		pane.add(button, c);
		
		button = new JButton("Stop");
		c.gridx = 1;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.PauseButtonSample);
		pane.add(button, c);
		
		button = new JButton("Apply FX");
		c.gridx = 2;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(95, 25));
		button.addActionListener(Listeners.ApplyFXToButton);
		pane.add(button, c);
		
		label = new JLabel("Playback Style:");
		c.gridx = 0;
		c.gridy = 9;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] latchHoldStrings = {"Latch", "Hold"};
		cb = new JComboBox<String>(latchHoldStrings);
		c.gridx = 1;
		c.gridy = 9;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setSelectedIndex((int)currentBtn.GetLatching());
		cb.setPreferredSize(new Dimension(95, 25));
		cb.addActionListener(Listeners.LatchHoldChanged);
		pane.add(cb, c);
		
		label = new JLabel("Loop interval:");
		c.gridx = 0;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] loopStrings = {"1/1", "1/2", "1/4", "1/8", "1/16", "1/32"};
		cb = new JComboBox<String>(loopStrings);
		c.gridx = 1;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(95, 25));
		cb.setSelectedIndex((int)currentBtn.GetLoopInterval());
		cb.addActionListener(Listeners.LoopIntervalChanged);
		pane.add(cb, c);
				
		return pane;
	}
	
	private static JPanel createWaveformPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 3, 3, 3);
		
		JButton button;
		JComboBox<String> cb;
		Waveform wf;
		
		cb = new JComboBox<String>(Model.getModel().getSampleStrings());
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
		cb.setPreferredSize(new Dimension(180, 25));
		if (GUI.currentSample != 0)
		{
			cb.setSelectedIndex(GUI.currentSample);
		}
		cb.addActionListener(Listeners.SampleChanged);
		pane.add(cb, c);
		
		String[] channelStrings = {"1", "2", "3", "4", "5"};
		cb = new JComboBox<String>(channelStrings);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(40, 25));
		cb.setSelectedIndex(GUI.currentWaveform);
		cb.addActionListener(Listeners.WaveformSelectorChanged);
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(70, 25));
		button.addActionListener(Listeners.LoadWaveform);
		pane.add(button, c);
		
		wf = viewerWaveforms[0];
		wf.setPreferredSize(new Dimension(270, 60));
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 4;
		pane.add(wf, c);
		
		wf = viewerWaveforms[1];
		wf.setPreferredSize(new Dimension(270, 60));
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 4;
		pane.add(wf, c);
		
		wf = viewerWaveforms[2];
		wf.setPreferredSize(new Dimension(270, 60));
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 4;
		pane.add(wf, c);
		
		wf = viewerWaveforms[3];
		wf.setPreferredSize(new Dimension(270, 60));
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 4;
		pane.add(wf, c);
		
		wf = viewerWaveforms[4];
		wf.setPreferredSize(new Dimension(270, 60));
		c.gridx = 0;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 4;
		pane.add(wf, c);
		
		return pane;
	}
	
	private static JPanel createExportPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4, 4, 4, 4);
		
		JLabel label = new JLabel("Tempo");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		pane.add(label, c);
		
		label = new JLabel("FX 1");
		c.gridx = 0;
		c.gridy = 1;
		pane.add(label, c);
		
		label = new JLabel("FX 2");
		c.gridx = 0;
		c.gridy = 2;
		pane.add(label, c);
		
		String[] tempoStrings = new String[231];
		int tempoNum = 10;
		for (int i = 0; i < 231; i++) {
			tempoStrings[i] = String.valueOf(tempoNum + i);
		}
		JComboBox<String> cb = new JComboBox<String>(tempoStrings);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 0;
		cb.setSelectedIndex((int)Model.getModel().tempo-10);
		cb.addActionListener(Listeners.TempoChanged);
		pane.add(cb, c);
		
		// THESE CAN'T BE DUPES - fuck it sure they can
		String[] fxStrings = {"None", "Low Pass", "High Pass", "Band Pass", "Notch", "Echo", "Bitcrusher", "Bitwise KO"};
		cb = new JComboBox<String>(fxStrings);
		cb.setSelectedIndex(Model.getModel().FX1mode);
		cb.addActionListener(Listeners.FX1Changed);
		c.gridx = 1;
		c.gridy = 1;
		pane.add(cb, c);
		
		cb = new JComboBox<String>(fxStrings);
		cb.setSelectedIndex(Model.getModel().FX2mode);
		cb.addActionListener(Listeners.FX2Changed);
		c.gridx = 1;
		c.gridy = 2;
		pane.add(cb, c);
		
		JButton button = new JButton("EXPORT");
		button.setPreferredSize(new Dimension(100, 100));
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 3;
		button.addActionListener(Listeners.ExportMPCConfig);
		pane.add(button, c);

		return pane;
	}
	
	private static JTabbedPane createLeftPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(320, 400));
		
		JPanel card1 = new JPanel(new FlowLayout());
		card1.add(createConfigPanel());		
				
		
		//Create the "cards".
        JPanel card2 = new JPanel(new FlowLayout()) {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                //size.width += 50;
                return size;
            }
        };
        card2.add(createWaveformPane());
 
        tabbedPane.addTab("Button Config", card1);
        tabbedPane.addTab("Waveform Viewer", card2);
        if (GUI.loadWaveformTabFirst == 1)
        {
        	tabbedPane.setSelectedIndex(1);
        	GUI.loadWaveformTabFirst = 0;
        }
		
		return tabbedPane;
	}
	
	private static JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenuItem item;
		
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		item = new JMenuItem("Open Config");
		item.addActionListener(Listeners.OpenMPCConfig);
		menu.add(item);
		
		item = new JMenuItem("Save Config");
		item.addActionListener(Listeners.ExportMPCConfig);
		menu.add(item);
		
		menu.addSeparator();
		
		item = new JMenuItem("Import Sample");
		item.addActionListener(Listeners.ImportFileFromMenu);
		menu.add(item);
		
		menu.addSeparator();
		
		item = new JMenuItem("Exit");
		item.addActionListener(Listeners.ExitApplication);
		menu.add(item);
		
		return menuBar;
	}

}
