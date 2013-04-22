package coen;

import javax.swing.*;
import java.awt.*;

public class GUI {
	
	public static void CreateAndShowGUI() {
		JFrame frame = new JFrame("Team 23 MPC Config");
		frame.setLayout(new FlowLayout());
		frame.setPreferredSize(new Dimension(640, 480));
		//frame.setBounds(400, 400, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = new JPanel();
		//pane.setBackground(Color.BLUE);
		
		JLabel label = new JLabel("Hello World");
		frame.getContentPane().add(pane);
		
		pane.add(label);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel createContentPane() {
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);
		
		return totalGUI;
	}
	
	private JPanel createButtonPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		
		for (int i = 0; i < 16; i++) {
			JButton button = new JButton(Model.)
		}
		
		
		return pane;
	}

}
