package hw5; //Lab 5 BINF6380 Jon Lee

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MeowNow extends JFrame
{
	private static final long serialVersionUID =3794059922116115530L;
	
	//add text field and string to populate (string will be continually added to)
	private JTextField aTextField = new JTextField();
	private String meow = "Meow";
	
	//method to update text field
	private void updateTextField()
	{
		aTextField.setText(meow);
		validate();
	}
	
	//define a bottom panel to have three buttons
	private JPanel getBottomPanel()
	{
		//define a panel to set up and populate
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		
		//meow button to meow every time its clicked
		JButton meowButton = new JButton("Meow Now");
		meowButton.setMnemonic('M');
		meowButton.setToolTipText("Click Here");
		meowButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				meow = meow + " Meow";
				updateTextField();
			}
		});
		
		//purr button to purr every time its clicked
		JButton purrButton = new JButton("Purr Now");
		purrButton.setMnemonic('P');
		purrButton.setToolTipText("Click Here");
		purrButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				meow = meow + " Purr";
				updateTextField();
			}
		});
		
		//hiss button to hiss every time its clicked
		JButton hissButton = new JButton("Hiss Now");
		hissButton.setMnemonic('H');
		hissButton.setToolTipText("Click Here");
		hissButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				meow = meow + " Hiss";
				updateTextField();
			}
		});
		
		//add all 3 buttons to the panel
		panel.add(meowButton);
		panel.add(purrButton);
		panel.add(hissButton);
		
		//return panel to generate the bottom panel
		return panel;
	}
	
	
	//method to save current state of GUI to hard drive
	private void saveToFile()
	{
		//invoke file chooser to allow use to see file navigator
		JFileChooser jfc = new JFileChooser();
		
		//if user selects cancel return
		if(jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		//if user selects no file return
		if(jfc.getSelectedFile() == null)
			return;
		
		//define selected file
		File chosenFile = jfc.getSelectedFile();
		
		//if file exists prompt user with warning message
		if(chosenFile.exists())
		{
			String message = "File " + chosenFile.getName() + " exists. Overwrite?";
			
			//if user does not want to over write return
			if(JOptionPane.showConfirmDialog(this, message) != JOptionPane.YES_OPTION)
				return;
		}
		
		//try catch block to write content of GUI to chosen file
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile));
			writer.write(this.meow + "\n");
			writer.flush(); writer.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//method to load state of GUI from hard drive
	public void loadFromFile()
	{
		//invoke file chooser to allows user to see file navigator
		JFileChooser jfc = new JFileChooser();
		
		//if user clicks cancel return
		if(jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		//if nothing selected return
		if(jfc.getSelectedFile() == null)
			return;
		
		//define the selected file
		File chosenFile = jfc.getSelectedFile();
		
		//try catch block to read selected file
		try
		{
			//read lines of selected file **(not sure if reader should be closed or where to do so)**
			BufferedReader reader = new BufferedReader(new FileReader(chosenFile));
			String line = reader.readLine();
			
			if(line == null || reader.readLine() != null)
			{
				reader.close();
				throw new Exception("Unexpected file format");
			}
				
			
			//try catch block to read in contents of chosen file to GUI
			try
			{
				this.meow = line;
				reader.close();
			}
			catch(Exception ex)
			{
				reader.close();
				throw new Exception("Unexpected file format");
			}
			
			updateTextField();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not read file", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//menu bar to add to GUI for additional tools
	private JMenuBar getMeowMenuBar()
	{
		//define a menu bar to be populates
		JMenuBar jmenuBar = new JMenuBar();
		
		//file menu to add to menu bar
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		//save item to add to file menu
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setMnemonic('S');
		
		//action invoked when save item is clicked (save state of GUI to hard drive)
		saveItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveToFile();
			}
		});
		
		//open item to add to file menu
		JMenuItem openItem = new JMenuItem("Open");
		openItem.setMnemonic('O');
		
		//action invoked when open item clicked (open a saved state of the GUI from the hard drive)
		openItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadFromFile();
			}
		});
		
		//add save and open items to file menu
		fileMenu.add(saveItem);
		fileMenu.add(openItem);
		
		//add file menu to menu bar
		jmenuBar.add(fileMenu);
		
		//return menu bar to generate the menu bar
		return jmenuBar;
	}
	
	//method to invoke GUI
	public MeowNow()
	{
		//GUI tile
		super("Meow Now!");
		
		//GUI parameters
		setLocationRelativeTo(null);
		setSize(400,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(aTextField, BorderLayout.CENTER);
		setJMenuBar(getMeowMenuBar());
		updateTextField();
		setVisible(true);
	}
	
	//main method to invoke GUI
	public static void main(String[] args)
	{
		new MeowNow();
	}
}
