/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 20/04/2014
 * 
 */
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.*;

import asgn2CarParks.CarPark;
	
/**
 * @author hogan
 * @author Ken Lewis
 * @author Thomas McCarthy
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	//Window Size
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 480;
	protected static final Dimension PREFSIZE = new Dimension(WIDTH, HEIGHT);
	
	
	public GUISimulator (){
		// Initialize the Frame and add the GamePanel
		this.setTitle("Car Park Application");
		setSize(PREFSIZE);
		this.getContentPane().add(new GUIPanel());
		repaint();
	}
	
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}
