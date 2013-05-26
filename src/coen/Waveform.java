package coen;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Waveform extends JPanel {
	
	private int playheadPosition;
	private ArrayList<Line> lines;
	
	public Waveform(short[] sample)
	{
		// Creates a shitty waveform, height = 60, width = 270
		
		this.playheadPosition = 0;
		lines = new ArrayList<Line>();
		
		int samplesPerPx = sample.length/270;
		System.out.println("Len " + sample.length + " perPX " + samplesPerPx);
		for (int i = 0; i < 270; i++)
		{
			lines.add(new Line(i, 30-(sample[samplesPerPx*i]*30/32768), i, 30+(sample[samplesPerPx*i]*30/32768)));
		}
		
	}
	
	public void setPlayhead(int ph)
	{
		playheadPosition = ph;
	}
	
	public int getPlayhead()
	{
		return playheadPosition;
	}
	
	protected void paintComponent(Graphics g)
	{
		g.clearRect(0, 0, 270, 60);
		for (int i = 0; i < 270; i++)
		{
			if (i != playheadPosition)
			{
				g.setColor(Color.BLACK);
				lines.get(i).paint(g);
			}
			else
			{
				//System.out.println("printing playhead");
				g.setColor(Color.RED);
				Line playHead = new Line(i, 0, i, 60);
				playHead.paint(g);
			}
		}
	}
	
	public static class Line {
		public final int x1;
	    public final int x2;
	    public final int y1;
	    public final int y2;
	    
	    public Line(int x1, int y1, int x2, int y2) {
	        this.x1 = x1;
	        this.x2 = x2;
	        this.y1 = y1;
	        this.y2 = y2;
	    }
	    
	    public void paint(Graphics g) {
	        g.drawLine(this.x1, this.y1, this.x2, this.y2);
	    }
	}

}