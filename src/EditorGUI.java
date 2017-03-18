import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;


 //By White Druid
 @SuppressWarnings("serial")
public class EditorGUI extends JFrame implements ActionListener {

	private static final JMenuBar MenuBar = null;
	// Menu 
	 private JMenu fileMenu;
	 private JMenu editMenu;
	 private JMenuItem newFile , openFile , saveFile , savaAsFile , pageSetup , printFile , exit;
	 private JMenuItem undoEdit, redoEdit , selectEdit,selectAll , copy ,paste , cut ; 
	 
	 //  Window
	 private JFrame editorWindow;
	 	 
	 // Text Area 
	 private Border textBorder;
	 private JScrollPane scroll;
	 private JTextArea textArea;
	 private Font textFont;
	 
	 // Window
	 private JFrame window;
	 
	 // printing 
	 private PrinterJob job;
	 public PageFormat format;
	 
	 // file save / opened 
	 private boolean opened = false;
	 private boolean saved  = false;
	 
	 //record open file for quick saving
	 private File openedFile;
	 
     // Undo manager for managing the storage of the undos
	 private UndoManager undo;
	 
	 public EditorGUI() {
		 	super("JavaEdit");
		 	
		 	// create file menu 
		 	fileMenu();
		 	editMenu();
	
		 	// create Text area
		 	createtextArea();
	        
		 	// Create Undo Manager for managing undo/redo commands
		 	undoMan();
		 	
		 	// Create Window
		 	creteEditorWindow();
	 }
	 
	 private JFrame creteEditorWindow() {
		 editorWindow = new JFrame("JavaEdit");
		 editorWindow.setVisible(true);
		 editorWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		 editorWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
	     // Create Menu Bar
		 editorWindow.setJMenuBar(createMenuBar());
		 editorWindow.add(scroll , BorderLayout.CENTER);
		 editorWindow.pack();
	     // Centers application on screen
		 editorWindow.setLocationRelativeTo(null);
		 
		 return editorWindow;
	 }
	 
	 private UndoManager undoMan() {
	    // Listener for undo and redo functions to document
		undo = new UndoManager();
		textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
		 
		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
		}
	 });
	return undo;
 }
	 private JMenuBar createMenuBar() {
		 JMenuBar menuBar = new JMenuBar();
		 setJMenuBar(menuBar);
		 menuBar.add(fileMenu);
		 menuBar.add(editMenu);
		 
		return MenuBar;
	 }
	 
	 private JTextArea createtextArea() {
		 textBorder = BorderFactory.createBevelBorder(0,Color.RED,Color.RED);
		 textArea = new JTextArea(30, 50);
		 textArea.setEditable(true);
		 textArea.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(2 , 5 , 0 ,0)));
		 
		 textFont = new Font("Verdana" , 0 , 14);
		 textArea.setFont(textFont);
		
		 scroll = new JScrollPane(textArea , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 
		 return textArea;
	 }
	 
	 private void editMenu() {
		 // Create File Menu
		 editMenu = new JMenu("edit");
		 editMenu.setPreferredSize(new Dimension(40 , 20));
		 
		 // add menu item
		 undoEdit = new JMenuItem("undo");
		 undoEdit.addActionListener(this);
		 undoEdit.setPreferredSize(new Dimension(100 , 20));
		 undoEdit.setEnabled(true);
		 
		 redoEdit = new JMenuItem("redoEdit");
		 redoEdit.addActionListener(this);
		 redoEdit.setPreferredSize(new Dimension(100 ,20));
		 redoEdit.setEnabled(true);
		 
		 selectEdit = new JMenuItem("selectEdit");
		 selectEdit.addActionListener(this);
		 selectEdit.setPreferredSize(new Dimension(100 ,20));
		 selectEdit.setEnabled(true);
		 
		 selectAll = new JMenuItem("selectAll");
		 selectAll.addActionListener(this);
		 selectAll.setPreferredSize(new Dimension(100 ,20));
		 selectAll.setEnabled(true);
		 
		 copy = new JMenuItem("copy");
		 copy.addActionListener(this);
		 copy.setPreferredSize(new Dimension(100 ,20));
		 copy.setEnabled(true);
		 
		 paste = new JMenuItem("paste");
		 paste.addActionListener(this);
		 paste.setPreferredSize(new Dimension(100 ,20));
		 paste.setEnabled(true);
		 
		 cut = new JMenuItem("cut");
		 cut.addActionListener(this);
		 cut.setPreferredSize(new Dimension(100 ,20));
		 cut.setEnabled(true);
		 
		 // add items
		 editMenu.add(cut);
		 editMenu.add(redoEdit);
		 editMenu.add(paste);
		 editMenu.add(copy);
		 editMenu.add(selectAll);
		 editMenu.add(selectEdit);
		 editMenu.add(redoEdit);
		 editMenu.add(undoEdit);
		 editMenu.add(editMenu);

	 }
	 
	 private void fileMenu() {
	      // Create File Menu
		  fileMenu = new JMenu("File");
		  fileMenu.setPreferredSize(new Dimension(40 , 20));
	 
	      // Add file menu items
		  newFile = new JMenuItem("New");
		  newFile.addActionListener(this);
		  newFile.setPreferredSize(new Dimension(100 , 20 ));
		  newFile.setEnabled(true);
		  
		  openFile = new JMenuItem("OpenFile");
		  openFile.addActionListener(this);
		  openFile.setPreferredSize(new Dimension(100 , 20));
		  openFile.setEnabled(true);
		  
		  saveFile = new JMenuItem("SaveFile");
		  saveFile.addActionListener(this);
		  saveFile.setPreferredSize(new Dimension(100 , 20));
		  saveFile.setEnabled(true);
		  
		  savaAsFile = new JMenuItem("savaAsFile. . ");
		  savaAsFile.addActionListener(this);
		  savaAsFile.setPreferredSize(new Dimension(100 , 20));
		  savaAsFile.setEnabled(true);

		  pageSetup = new JMenuItem("pageSetup");
		  pageSetup.addActionListener(this);
		  pageSetup.setPreferredSize(new Dimension(100 , 20));
		  pageSetup.setEnabled(true);
		  
		  printFile  = new JMenuItem("printFile..");
		  printFile .addActionListener(this);
		  printFile .setPreferredSize(new Dimension(100 , 20));
		  printFile .setEnabled(true);

		  exit = new JMenuItem("exit ..");
		  exit.addActionListener(this);
		  exit.setPreferredSize(new Dimension(100 , 20));
		  exit.setEnabled(true);

	      // Add items to menu
		  fileMenu.add(openFile);
		  fileMenu.add(pageSetup);
		  fileMenu.add(printFile);
		  fileMenu.add(exit);
		  fileMenu.add(savaAsFile);
		  fileMenu.add(saveFile);
		  fileMenu.add(newFile);
		  
	 }
	 
	 private void saveFile(File filename) {
		 try {
			 BufferedWriter br = new BufferedWriter(new FileWriter(filename)); 
			 br.write(textArea.getText());
			 br.close();
			 saved = true;
			 window.setTitle("JavaText - " + filename.getName());
		 } catch (Exception e) {
	            e.printStackTrace();
	            }
	 }
	 
	 private void quickSave(File filename)  {
		 try {
			 BufferedWriter bf = new BufferedWriter(new FileWriter(filename));
			 bf.write(textArea.getText());
			 bf.close();
		 } catch (IOException e){
	            e.printStackTrace();
		 }
	 }
	 
	 private void openingFiles(File filename) {
		 try {
			 openedFile = filename ;
			 FileReader fr = new FileReader(filename);
			 textArea.read(fr , null);
			 opened = true;
			 window.setTitle("JavaEdit - " + filename.getName());
		 } catch ( IOException e) {
			 
		 }
	 }
	 
	public static void main(String[] args) {
			new EditorGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}



