
package p1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle_GUI extends JFrame  {

	private JPanel contentPane;
	private static final int rows = 6;
    private static final int cols = 6;
 
	/**
	 * Create the frame.
	 */
	public Puzzle_GUI(List<Vehicle> vehicles) {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		for(int y = 0; y < cols;  y++) {
			for(int x = 0; x < rows; x ++) {
				JPanel nextPanel = new JPanel();			
				nextPanel.setBackground(Color.GRAY);
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.insets = new Insets(0, 0, 5, 5);
				gbc_panel.fill = GridBagConstraints.BOTH;
				gbc_panel.gridx = x;
				gbc_panel.gridy = y;
				contentPane.add(nextPanel, gbc_panel);
				
			}
		}
		
		constructGrid(vehicles);
		

		
		
	}
	
	public void constructGrid(List<Vehicle> vehicles) {
		byte x,y;
		for(int i = 0; i < rows * cols; i ++) {
			contentPane.getComponent(i).setBackground(Color.GRAY);
		}
		
		for(Vehicle v: vehicles) {
			if(v.direction) {
				x = v.lockedPoint;
				y = (byte) (5 - v.axisPoints.get(0));
					
				contentPane.getComponent(x + y*6).setBackground(v.color);

				contentPane.getComponent(x + (y-1)*6).setBackground(v.color);
				
				if(v.size) {
					contentPane.getComponent(x + (y-2)*6).setBackground(v.color);
				}
			}
			else {
				y = (byte) (5 - v.lockedPoint);			
				x = v.axisPoints.get(0);
				contentPane.getComponent(x + y*6).setBackground(v.color);	
				contentPane.getComponent((x+1) + y*6).setBackground(v.color);
				if(v.size) {
					contentPane.getComponent((x +2)+ y*6).setBackground(v.color);
				}
			}
			
				
			
		}
	}
	
	private void cleanUp(int location, Color color) {
		if(location < 0 || location > 35) return;
		
		if(contentPane.getComponent(location).getBackground().equals(color)) {
			
			contentPane.getComponent(location).setBackground(Color.GRAY);
		}
	}
	
}
