package coen;

import java.util.ArrayList;
import java.util.Map.Entry;

public class MPCC {
	
	// The array of button configurations
	private ArrayList<ButtonC> buttons;
	private ArrayList<Entry<short[], String>> sampleList;
	
	public MPCC ()
	{
		this.buttons = new ArrayList<ButtonC>();
		for (int i = 0; i < 16; i++)
		{
			this.buttons.add(new ButtonC(String.valueOf(i)));
		}
		
		this.sampleList = new ArrayList<Entry<short[], String>>();
	}

}
