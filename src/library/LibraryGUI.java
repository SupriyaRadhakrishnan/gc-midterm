package library;


import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.awt.*;


@SuppressWarnings("serial")
public class LibraryGUI extends JFrame implements ActionListener
{
	private JButton but1, but2, but3, but4, but5 = null;	
	private Container con = null;
	private static Path bookfile = Paths.get("Books.txt");
	private static Path movieFile = Paths.get("Movies.txt");
	
	
	public LibraryGUI()
	{
		//set some behaviors of the GUI
		setSize(600,400);
		setTitle("S&T Library");
		setLocation(500, 500);		
		
		con = getContentPane();
		
		//add buttons to a grid in the panel
		JPanel north = new JPanel( new GridLayout());
		
		but1 = new JButton("List Books.txt");
		but2 = new JButton("List Movies.txt");
		but3 = new JButton("Search");
		but4 = new JButton("Return item");
		but5 = new JButton("Exit");	
		
		north.add(but1);
		north.add(but2);
		north.add(but3);
		north.add(but4);
		north.add(but5);	
		
		//add the panels to the container		
		con.add(north);
		
		//add the listeners
		but1.addActionListener(this);
		but2.addActionListener(this);
		but3.addActionListener(this);
		but4.addActionListener(this);
		but5.addActionListener(this);		
		
		addWindowListener(new MyWindowResponder());
		setVisible(true);	
	}
	
	
	
	@SuppressWarnings("unused")
	public static void main(String [] args )
	{
		LibraryGUI libGUI = new LibraryGUI();	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent ae)
	{
		//button 1 - list books.txt
		if(ae.getSource() == but1)
		{
			String[] array = null;
			JFrame jf = new JFrame();	
			
			try {
				List<String> allLines = Files.readAllLines(bookfile);
				array = allLines.toArray(new String[0]);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			jf.add(new JList(array));
			jf.pack();
			jf.setTitle("Books.txt");
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);					
		}
		
		//button 2 list movies.txt
		if(ae.getSource() == but2)
		{
			String[] array = null;
			JFrame jf = new JFrame();	
			
			try {
				List<String> allLines = Files.readAllLines(movieFile);
				array = allLines.toArray(new String[0]);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			jf.add(new JList(array));
			jf.pack();
			jf.setTitle("Movies.txt");
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setVisible(true);
			
		}
		// button 3 - search
		if(ae.getSource() == but3)
		{
			JOptionPane.showMessageDialog(but3, "Search goes here...", "Search", JOptionPane.INFORMATION_MESSAGE);
		
		}
		// button 4 - return
		if(ae.getSource() == but4)
		{
			JOptionPane.showMessageDialog(but4, "Return goes here...", "Return", JOptionPane.INFORMATION_MESSAGE);
				
		}
		//button 5 - exit
		if(ae.getSource() == but5)
		{
			
			System.exit(0);
			
		}
	}	
}

class MyWindowResponder extends WindowAdapter
{
	/**
	* This method runs the exit method for a window that is
	* defined by the windowClosing operation.
	*/
	
	public void windowClosing( WindowEvent e )
	{
		exit(e.getWindow());
	}
	
	/**
	* Disposes of a window and ends the program upon execution
	*/
	
	public void exit(Window w)
	{
		w.dispose();
		System.exit(0);
	}	
}	