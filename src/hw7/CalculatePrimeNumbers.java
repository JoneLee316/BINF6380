package hw7; //Lab 7 BINF6380 Jon Lee

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class CalculatePrimeNumbers extends JFrame
{
	private static final long serialVersionUID = 3794059922116115530L;
	
	private AtomicBoolean cancel = new AtomicBoolean(false);
	private AtomicBoolean processComplete = new AtomicBoolean(false);
	
	private JLabel textLabel = new JLabel();
	
	private JTextArea inputTextArea = new JTextArea();
	private JScrollPane inputScrollArea = new JScrollPane(inputTextArea);
	
	private JButton doButton = new JButton("Calculate");
	private JButton newButton = new JButton("New Number");
	private JButton cancelButton = new JButton("Cancel");
	
	private String text = "<html><div style='text-align: center;'>Welcome!<br>Click 'Calculate' to Determine all Prime Numbers</div></html>";
	private String inputText = "";
	
	private void updateTextLabel()
	{
		textLabel.setText(text);
		validate();
	}
	
	private void updateInputTextArea()
	{
		inputTextArea.setText(inputText);
		validate();
	}
	
	private class Task implements Runnable
	{
		public void run()
		{
			Integer input = Integer.valueOf(inputTextArea.getText());
			
			try
			{
				while(cancel.get() == false && processComplete.get() == false)
				{
					primeNumbers(input);
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in Calculation");
			}
			
			try
			{
				//will run on AWT thread to end quiz when time gets to 0
				SwingUtilities.invokeAndWait(new Runnable()
				{
					public void run()
					{
						endTask();
					}
				});
				
			}
			catch(Exception ex)
			{
				System.out.println("Error in Ending Task");
			}
		}
	}
	
	private void primeNumbers(int n) 
    {	
		long start = System.currentTimeMillis();
		
		textLabel.setText("Calculating...");
		
		List<Integer> primes = new ArrayList<Integer>();
		
		String num = "";
		
		for(Integer t=0; t<n; t++)
		{
			int m=t/2;  
			int flag=0;
			
			for(int i=2; i<=m; i++)
			{      
				if(t%i==0)
				{           
					flag=1;      
					break;      
				}
			}
			if(flag==0)
			{
				primes.add(t);
				num = t.toString() + "\n" + num;
				inputTextArea.setText(num);
			}  
			
		}
		System.out.println(primes);
		System.out.println("Total number of prime numbers = " + primes.size());
		
		long end = System.currentTimeMillis();

		float time = (end-start)/1000f;
		System.out.println("Process took: " + time + " seconds");
		
		textLabel.setText("<html><div style='text-align: center;'>Number of Primes = <html>" + primes.size() + "<html><br>Process took: <html>" + time + "<html> seconds</div></html>");
		
		processComplete.set(true);
    } 
	
	private void doTask()
	{	
		cancel.set(false);
		processComplete.set(false);
		
		new Thread(new Task()).start();
		
		doButton.setEnabled(false);
		cancelButton.setEnabled(true);
	}
	
	private void reset()
	{
		cancel.set(true);
		processComplete.set(true);
		
		doButton.setEnabled(true);
		newButton.setEnabled(false);
		cancelButton.setEnabled(false);

		inputTextArea.requestFocusInWindow();
		
		updateTextLabel();
		updateInputTextArea();
	}
	
	private void endTask()
	{
		cancel.set(true);
		processComplete.set(true);
		
		doButton.setEnabled(false);
		newButton.setEnabled(true);
		cancelButton.setEnabled(false);
		
		inputTextArea.requestFocusInWindow();
	}
	
	private JPanel getTopPanel()
	{
		JPanel topPanel = new JPanel();
		
		textLabel.setForeground(new Color(225,225,225));
		topPanel.setBackground(new Color(50,50,50));
		topPanel.add(textLabel);
		updateTextLabel();
		
		return topPanel;
	}
	
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
				doTask();
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
	
	public CalculatePrimeNumbers()
	{
		super("Determine Prime Factors");
		
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
	
	public static void main(String[] args)
	{
		new CalculatePrimeNumbers();
	}
}
