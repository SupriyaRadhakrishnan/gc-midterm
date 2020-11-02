package library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

@SuppressWarnings("serial")
public class LibraryGUI extends JFrame implements ActionListener {
	private JButton but1, but2, but3, but4, but5, but6, but7 = null;
	private Container con = null;
	private static Path bookfile = Paths.get("Books.txt");
	private static Path movieFile = Paths.get("Movies.txt");
	JTable table = new JTable();
	JScrollPane pane = null;

	public LibraryGUI() {
		// set some behaviors of the GUI
		setSize(600, 400);
		setTitle("S&T Library");
		setLocation(500, 500);

		con = getContentPane();

		// add buttons to a grid in the panel
		JPanel north = new JPanel(new FlowLayout());

	//	but1 = new JButton("List Books.txt");
		but2 = new JButton("Search by keyword");
		but3 = new JButton("Search Books by Author");
		but4 = new JButton("Search Movies by Director");
		but5 = new JButton("Display Books");
		but6 = new JButton("Display Movies");
		but7 = new JButton("Exit");

	//	north.add(but1);
		north.add(but2);
		north.add(but3);
		north.add(but4);
		north.add(but5);
		north.add(but6);
		north.add(but7);

		// add the panels to the container
		con.add(north);

		// add the listeners
	//	but1.addActionListener(this);
		but2.addActionListener(this);
		but3.addActionListener(this);
		but4.addActionListener(this);
		but5.addActionListener(this);
		but6.addActionListener(this);
		but7.addActionListener(this);

		addWindowListener(new MyWindowResponder());
		setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		LibraryGUI libGUI = new LibraryGUI();
	}

	public void showMedia(List<Media> listOfMedia) {
		if (!listOfMedia.isEmpty()) {
			if (pane != null) {
				getContentPane().remove(pane);
				pane = null;
			}
			if (listOfMedia.get(0) instanceof Book) {

				Object obj[][] = new Object[listOfMedia.size()][2];

				for (int i = 0; i < listOfMedia.size(); i++) {

					for (int j = 0; j < 2; j++) {

						obj[i][j] = listOfMedia.get(i).getTitle();
						j++;
						obj[i][j] = (((Book) listOfMedia.get(i)).getAuthor());
					}
				}

				DefaultTableModel model = new DefaultTableModel(obj, new Object[] { "Title", "Author" });

				table = new JTable(model);
				table.setAutoCreateRowSorter(true);
				pane = new JScrollPane(table);
				getContentPane().add(pane, BorderLayout.SOUTH);
				pack();

			} else {
				Object obj[][] = new Object[listOfMedia.size()][3];

				for (int i = 0; i < listOfMedia.size(); i++) {

					for (int j = 0; j < 3; j++) {

						obj[i][j] = listOfMedia.get(i).getTitle();
						j++;
						obj[i][j] = (((Movie) listOfMedia.get(i)).getDirector());
						j++;
						obj[i][j] = (((Movie) listOfMedia.get(i)).getRuntime());

					}
				}

				DefaultTableModel model = new DefaultTableModel(obj, new Object[] { "Title", "Director", "Runtime" });

				table = new JTable(model);
				table.setAutoCreateRowSorter(true);
				pane = new JScrollPane(table);
				getContentPane().add(pane, BorderLayout.SOUTH);
				pack();

			}
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void actionPerformed(ActionEvent ae) {
		// button 1 - list books.txt
		if (ae.getSource() == but1) {
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

		// button 2 list movies.txt
		if (ae.getSource() == but2) {
			
			String keyword = JOptionPane.showInputDialog(but3, "Search by Keyword", "Enter the keyword");

			if (keyword == null) {
				JOptionPane.showMessageDialog(but3, "Nothing entered");
			}
			List<Media> listOfMedia = new ArrayList<>();

			try {
				listOfMedia = LibraryApp.searchByKeyword(keyword);
			} catch (IOException | NullPointerException e) {

			}
			
			showMedia(listOfMedia);
//			String[] array = null;
//			JFrame jf = new JFrame();
//
//			try {
//				List<String> allLines = Files.readAllLines(movieFile);
//				array = allLines.toArray(new String[0]);
//			} catch (IOException e1) {
//
//				e1.printStackTrace();
//			}
//			jf.add(new JList(array));
//			jf.pack();
//			jf.setTitle("Movies.txt");
//			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			jf.setLocationRelativeTo(null);
//			jf.setVisible(true);

		}
		// button 3 - search by author
		if (ae.getSource() == but3) {

			String authorName = JOptionPane.showInputDialog(but3, "Search by Author", "Enter author name");

			if (authorName == null) {
				JOptionPane.showMessageDialog(but3, "Nothing entered");
			}
			List<Media> listOfMedia = new ArrayList<>();

			try {
				listOfMedia = LibraryApp.searchByAuthor(authorName);
			} catch (IOException | NullPointerException e) {

			}

			showMedia(listOfMedia);

		}
		// button 4 - search movies by director
		if (ae.getSource() == but4) {
			String movieDirector = JOptionPane.showInputDialog(but4, "Search by Director", "Enter the director");

			if (movieDirector == null) {
				JOptionPane.showMessageDialog(but4, "Nothing entered");
			}
			List<Media> listOfMedia = new ArrayList<>();

			try {
				listOfMedia = LibraryApp.searchByDirector(movieDirector);
			} catch (IOException | NullPointerException e) {

			}

			showMedia(listOfMedia);

		}
		// button 5 - display books
		if (ae.getSource() == but5) {
			List<Media> listOfMedia = new ArrayList<>();
			try {
				listOfMedia = LibraryApp.displayBooks(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showMedia(listOfMedia);
		}
		// button 6 - display movies
		if (ae.getSource() == but6) {
			List<Media> listOfMedia = new ArrayList<>();
			try {
				listOfMedia = LibraryApp.displayMovies(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showMedia(listOfMedia);
		}
		// button 7 - exit
		if (ae.getSource() == but7)

		{

			System.exit(0);

		}
	}
}

class MyWindowResponder extends WindowAdapter {
	/**
	 * This method runs the exit method for a window that is defined by the
	 * windowClosing operation.
	 */

	public void windowClosing(WindowEvent e) {
		exit(e.getWindow());
	}

	/**
	 * Disposes of a window and ends the program upon execution
	 */

	public void exit(Window w) {
		w.dispose();
		System.exit(0);
	}
}