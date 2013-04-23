package coen;

import javax.swing.*;
import java.awt.*;

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
		
		//Create the "cards".
        JPanel card1 = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                //size.width += 50;
                return size;
            }
        };
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));
 
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));
 
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
		menu.add(item);
		
		return menuBar;
	}

}
