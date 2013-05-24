package coen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
	
	public static void CreateAndShowGUI() {
		JFrame frame = new JFrame("Team 23 MPC Config");
		frame.setLayout(new FlowLayout());
		frame.setPreferredSize(new Dimension(640, 480));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = createContentPane();
		
		frame.setJMenuBar(createMenuBar());
		frame.getContentPane().add(pane);
		frame.pack();
		frame.setVisible(true);
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
			button.addActionListener(Listeners.ImportFileFromMenu);
			pane.add(button, c);
		}

		return pane;
	}
	
	private static JPanel createConfigPanel() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4, 4, 4, 4);
		
		JLabel label;
		JButton button;
		JComboBox cb;
		JSlider slide;
		
		label = new JLabel("Button #");
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
		label = new JLabel("Sample:");
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] sampleStrings = {"sample1", "sample2"};
		cb = new JComboBox(sampleStrings);
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		
		label = new JLabel("WAVEFORM GOES HERE");
		label.setPreferredSize(new Dimension(150, 50));
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
		button = new JButton("Reset");
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		button = new JButton("Play");
		c.gridx = 1;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		button = new JButton("Pause");
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		button = new JButton("Set Start");
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		button = new JButton("Set End");
		c.gridx = 1;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		button = new JButton("Slice");
		c.gridx = 2;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		label = new JLabel("FX:");
		c.gridx = 0;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] fxStrings = {"fx1", "fx2"};
		cb = new JComboBox(fxStrings);
		c.gridx = 1;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		label = new JLabel("Param1:");
		c.gridx = 0;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		slide = new JSlider(JSlider.HORIZONTAL, 0, 127, 63);
		c.gridx = 1;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 2;
		pane.add(slide, c);
		
		label = new JLabel("Param2:");
		c.gridx = 0;
		c.gridy = 7;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		slide = new JSlider(JSlider.HORIZONTAL, 0, 127, 63);
		c.gridx = 1;
		c.gridy = 7;
		c.gridheight = 1;
		c.gridwidth = 2;
		pane.add(slide, c);
		
		button = new JButton("Apply FX");
		c.gridx = 0;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 3;
		button.setPreferredSize(new Dimension(270, 25));
		pane.add(button, c);
		
		label = new JLabel("Playback Style:");
		c.gridx = 0;
		c.gridy = 9;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] latchHoldStrings = {"Latch", "Hold"};
		cb = new JComboBox(latchHoldStrings);
		c.gridx = 1;
		c.gridy = 9;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		label = new JLabel("Loop interval:");
		c.gridx = 0;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] loopStrings = {"1/1", "1/2", "1/4", "1/8", "1/16", "1/32"};
		cb = new JComboBox(loopStrings);
		c.gridx = 1;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
				
		return pane;
	}
	
	private static JPanel createWaveformPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4, 4, 4, 4);
		
		JLabel label;
		JButton button;
		JComboBox cb;
		JSlider slide;
		
		// Sample 1
		label = new JLabel("Sample:");
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		String[] sampleStrings = {"sample1", "sample2"};
		cb = new JComboBox(sampleStrings);
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		label = new JLabel("WAVEFORM GOES HERE");
		label.setPreferredSize(new Dimension(150, 25));
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
		// Sample 2
		label = new JLabel("Sample:");
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		cb = new JComboBox(sampleStrings);
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		label = new JLabel("WAVEFORM GOES HERE");
		label.setPreferredSize(new Dimension(150, 25));
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
		// Sample 3
		label = new JLabel("Sample:");
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		cb = new JComboBox(sampleStrings);
		c.gridx = 1;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		label = new JLabel("WAVEFORM GOES HERE");
		label.setPreferredSize(new Dimension(150, 25));
		c.gridx = 0;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
		// Sample 4
		label = new JLabel("Sample:");
		c.gridx = 0;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		cb = new JComboBox(sampleStrings);
		c.gridx = 1;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 6;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		label = new JLabel("WAVEFORM GOES HERE");
		label.setPreferredSize(new Dimension(150, 25));
		c.gridx = 0;
		c.gridy = 7;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
		// Sample 5
		label = new JLabel("Sample:");
		c.gridx = 0;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		pane.add(label, c);
		
		cb = new JComboBox(sampleStrings);
		c.gridx = 1;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		cb.setPreferredSize(new Dimension(90, 25));
		pane.add(cb, c);
		
		button = new JButton("Load");
		c.gridx = 2;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		button.setPreferredSize(new Dimension(90, 25));
		pane.add(button, c);
		
		label = new JLabel("WAVEFORM GOES HERE");
		label.setPreferredSize(new Dimension(150, 25));
		c.gridx = 0;
		c.gridy = 9;
		c.gridheight = 1;
		c.gridwidth = 5;
		pane.add(label, c);
		
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
		
		String[] tempoStrings = new String[230];
		int tempoNum = 10;
		for (int i = 0; i < 230; i++) {
			tempoStrings[i] = String.valueOf(tempoNum + i);
		}
		JComboBox cb = new JComboBox(tempoStrings);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		cb.setSelectedIndex(118);
		c.gridx = 1;
		c.gridy = 0;
		pane.add(cb, c);
		
		// THESE CAN'T BE DUPES
		String[] fxStrings = {"None", "Low Pass", "High Pass", "Band Pass", "Notch", "Echo", "Bitcrusher", "Bitwise KO"};
		cb = new JComboBox(fxStrings);
		cb.setSelectedIndex(0);
		c.gridx = 1;
		c.gridy = 1;
		pane.add(cb, c);
		
		cb = new JComboBox(fxStrings);
		cb.setSelectedIndex(1);
		c.gridx = 1;
		c.gridy = 2;
		pane.add(cb, c);
		
		JButton button = new JButton("EXPORT");
		button.setPreferredSize(new Dimension(100, 100));
		c.fill = GridBagConstraints.NONE;
		c.gridx = 3;
		c.gridy = 0;
		c.gridheight = 3;
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
		
		
		return tabbedPane;
	}
	
	private static JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenuItem item;
		
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		
		item = new JMenuItem("Open Config");
		menu.add(item);
		
		item = new JMenuItem("Save Config");
		menu.add(item);
		
		menu.addSeparator();
		
		item = new JMenuItem("Import Sample");
		menu.add(item);
		
		menu.addSeparator();
		
		item = new JMenuItem("Exit");
		item.addActionListener(Listeners.ExitApplication);
		menu.add(item);
		
		return menuBar;
	}

}
