package hw8; //Lab 8 BINF6380 Jon Lee

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class CalcPrimeNumsMultiThread extends JFrame
{
	//define all variables for use
	//serial number for GUI
	private static final long serialVersionUID = 3794059922116115530L;
	
	//atomic booleans for method switching (to ensure visibility, atomicity and thread safety)
	private AtomicBoolean cancel = new AtomicBoolean(false);
	
	//text label for GUI information
	private JLabel textLabel = new JLabel();
	
	//text area for user input and results (made it a scrolling pane)
	private JTextArea inputTextArea = new JTextArea();
	private JScrollPane inputScrollArea = new JScrollPane(inputTextArea);
	
	//three buttons for user to do, reset, and cancel the GUI
	private JButton doButton = new JButton("Calculate");
	private JButton newButton = new JButton("New Number");
	private JButton cancelButton = new JButton("Cancel");
	
	//standard GUI text
	private String text = "<html><div style='text-align: center;'>Welcome!<br>Enter a Number between 0 and 2,147,483,647 and<br>Click 'Calculate' to Determine all Prime Numbers</div></html>";
	private String inputText = "";
	
	//method for updating text label
	private void updateTextLabel()
	{
		textLabel.setText(text);
		validate();
	}
	
	//method for updating text area
	private void updateInputTextArea()
	{
		inputTextArea.setText(inputText);
		validate();
	}
	
	//Runnable class for semaphore to operate on and determine if a number is prime
	private class Prime implements Runnable
	{
		private final Integer input;
		private final Semaphore semaphore;
		
		public Prime(Integer input, Semaphore semaphore)
		{
			this.input = input;
			this.semaphore = semaphore;
		}
		
		public void run()
		{
			try
			{	
				if(cancel.get()==false)
				{
					int m= input/2;  
					int flag=0;
					
					for(int i=2; i<=m; i++)
					{      
						if(input%i==0)
						{           
							flag=1;      
							break;      
						}
					}
					
					if(flag==0)
					{
						inputTextArea.append("\n" + input.toString());
						System.out.println(input);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Exit");
				System.exit(1);
			}
			finally
			{
				semaphore.release();
			}
		}
	}
	
	private class PrimeManager implements Runnable
	{
		private final Integer input;
		
		public PrimeManager(Integer input)
		{
			this.input = input;
		}
		
		public void run() throws RuntimeException
		{
			try
			{
				cancelButton.setEnabled(true);
				newButton.setEnabled(false);
				
				long start = System.currentTimeMillis();
				
				Integer num = input;
				Integer numThreads = 4;
				
				textLabel.setText("Calculating...");
				inputTextArea.setText("The following numbers are prime:");
				
				Semaphore s = new Semaphore(numThreads);
				
				for(Integer x=2; x<num; x++)
				{
					s.acquire();
					Prime p = new Prime(x, s);
					new Thread(p).start();
				}
				
				for(int x=0; x<numThreads; x++)
				{
					s.acquire();
				}
				
				long end = System.currentTimeMillis();
				
				float time = (end - start)/1000f;
				
				if(cancel.get() == false)
				{
					textLabel.setText("<html><div style='text-align: center;'>Process Complete!<br>Took: <html>" + time + "<html> seconds</div></html>");
					System.out.println("Process took " + time + " seconds");
				}
				if(cancel.get() == true)
				{
					endTask();
				}
				
				cancel.set(true);
				
				doButton.setEnabled(false);
				cancelButton.setEnabled(false);
				newButton.setEnabled(true);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//method to calculate button action listener
	private void doTask()
	{	
		try
		{	
			cancel.set(false);
			
			doButton.setEnabled(false);
			
			Integer input = Integer.valueOf(inputTextArea.getText());
			
			PrimeManager pm = new PrimeManager(input);
			new Thread(pm).start();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Please insert a valid number");
			inputTextArea.setText("");
			
			reset();
		}
	}
	
	//method for reset action
	private void reset()
	{
		cancel.set(true);
		
		doButton.setEnabled(true);
		newButton.setEnabled(false);
		cancelButton.setEnabled(false);

		inputTextArea.requestFocusInWindow();
		
		updateTextLabel();
		updateInputTextArea();
	}
	
	//method for ending task and canceling it
	private void endTask()
	{
		cancel.set(true);
		
		textLabel.setText("<html><div style='text-align: center;'>Process Canceled<br>Click 'New Number' to Try Again</div></html>");
		inputTextArea.setText("");
		
		doButton.setEnabled(false);
		newButton.setEnabled(true);
		cancelButton.setEnabled(false);
		
		inputTextArea.requestFocusInWindow();
	}
	
	//top panel layout method
	private JPanel getTopPanel()
	{
		JPanel topPanel = new JPanel();
		
		textLabel.setForeground(new Color(225,225,225));
		topPanel.setBackground(new Color(50,50,50));
		topPanel.add(textLabel);
		updateTextLabel();
		
		return topPanel;
	}
	
	//center panel layout method
	private JPanel getCenterPanel()
	{
		JPanel centerPanel = new JPanel();
		
		inputTextArea.setBackground(new Color(225,225,225));
		centerPanel.setLayout(new GridLayout(0,1));
		centerPanel.setBackground(new Color(50,50,50));
		centerPanel.add(inputScrollArea);
		updateInputTextArea();
		
		return centerPanel;
	}
	
	//bottom panel layout method
	private JPanel getBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		
		bottomPanel.setLayout(new GridLayout(0,3));
		bottomPanel.setBackground(new Color(50,50,50));
		
		doButton.setEnabled(true);
		doButton.setBackground(new Color(225,225,225));
		doButton.setMnemonic('D');
		doButton.setToolTipText("Click Here to Find Prime Numbers");
		doButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				try
				{
					doTask();
				} 
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		newButton.setEnabled(false);
		newButton.setBackground(new Color(225,225,225));
		newButton.setMnemonic('R');
		newButton.setToolTipText("Click Here to Calculate Primes for a New Number");
		newButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				reset();
			}
		});
		
		cancelButton.setEnabled(false);
		cancelButton.setBackground(new Color(225,225,225));
		cancelButton.setMnemonic('C');
		cancelButton.setToolTipText("Click Here to Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				endTask();
			}
		});
		
		bottomPanel.add(doButton);
		bottomPanel.add(newButton);
		bottomPanel.add(cancelButton);
		
		return bottomPanel;
	}
	
	//GUI method
	public CalcPrimeNumsMultiThread()
	{
		super("How Many Primes are There?");
		
		setLocationRelativeTo(null);
		setSize(400,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		updateTextLabel();
		setVisible(true);
	}
	
	//main method to run the GUI
	public static void main(String[] args)
	{
		new CalcPrimeNumsMultiThread();
	}
}

