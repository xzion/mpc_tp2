package coen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class Listeners {
	
	public static ActionListener ImportFileFromMenu = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Do stuff here
			System.out.println("EVENT FIRED!!");
		}
	};
	
	public static ActionListener OpenMPCConfig = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Do stuff here
			System.out.println("EVENT FIRED!!");
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.show
		}
	};
	
	public static ActionListener ExportMPCConfig = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Do stuff here
			System.out.println("EVENT FIRED!!");
		}
	};
	
	public static ActionListener ExitApplication = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			// Do stuff here
			System.exit(0);
		}
	};
	
	
	
	

}
