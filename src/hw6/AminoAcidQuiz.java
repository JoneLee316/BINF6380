package hw6; //Lab 6 BINF6380 Jon Lee

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class AminoAcidQuiz extends JFrame
{
	//all variables needed for GUI
	private static final long serialVersionUID = 3794059922116115530L;
	
	private AtomicBoolean cancel = new AtomicBoolean(false);
	
	private JTextField inputTextField = new JTextField();
	
	private JLabel textLabel = new JLabel();
	private JLabel scoreLabel = new JLabel();
	private JLabel timerLabel = new JLabel();
	
	private JButton startButton = new JButton("Start!");
	private JButton cancelButton = new JButton("Cancel");
	private JButton nextButton = new JButton("Next Question");
	private JButton checkButton = new JButton("Check Answer");
	
	private String inputText = "";
	private String answer = "";
	private String text = "";
	
	private int correctInt = 0;
	private int incorrectInt = 0;
	
	//reference array for AA short names for answers
	private static String[] SHORT_NAMES = 
		{
		"A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V"
		};
	
	//reference array for AA full names for question
	private static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"
		};
	
	//method to update Input Text Field
	private void updateInputText()
	{
		inputTextField.setText(inputText);
		validate();
	}
	
	//method to update Text Label
	private void updateTextLabel()
	{
		textLabel.setText(text);
		validate();
	}
	
	//method to update Score Label
	private void updateScoreLabel()
	{
		scoreLabel.setText("Current Score: Correct = " + correctInt + " Incorrect = " + incorrectInt);
		validate();
	}
	
	//method to update Timer Label
	private void updateTimerLabel(float time)
	{
		timerLabel.setText("Time Reaining (sec): " + time);
		validate();
	}
	
	//method to play quiz - tied to Start button and Next Question button
	private void playQuiz()
	{	
		cancel.set(false);
		
		startButton.setEnabled(false);
		nextButton.setEnabled(true);
		checkButton.setEnabled(true);
		
		inputTextField.requestFocusInWindow();
		
		Random random = new Random();

		int i = random.nextInt(20);
		
		inputText = "";
		updateInputText();
		
		text = "What is the one letter code for: " + FULL_NAMES[i];
		updateTextLabel();
			
		answer = SHORT_NAMES[i];
	}
	
	//method to check quiz answers - tied to Check Answer button
	private void checkAnswer()
	{
		inputText = inputTextField.getText().toUpperCase();
		
		if(inputText.equals(answer))
		{
			text = "Correct!";
			correctInt = correctInt + 1;
				
			updateTextLabel();
			updateScoreLabel();
		}
		else
		{
			text = "Incorret! The correct answer is: "+ answer;
			incorrectInt = incorrectInt + 1;
						
			updateTextLabel();
			updateScoreLabel();
		}
		
		checkButton.setEnabled(false);
	}
	
	//method to end quiz - tied to Cancel button and end of timer
	private void endQuiz()
	{
		cancel.set(true);
		
		startButton.setEnabled(true);
		nextButton.setEnabled(false);
		checkButton.setEnabled(false);
		
		text = "";
		inputText = "";
		
		updateTextLabel();
		updateInputText();
		updateTimerLabel(0);
	}
	
	//timer class to run on a separate thread and update timer on AWT thread
	private class Timer implements Runnable
	{
		public void run()
		{
			int timer = 0;
			float time = 0;
			
			try
			{
				//will only execute when timer is < 60 and cancel = false
				while(cancel.get() == false && timer < 60)
				{
					timer++;
					time = 60 - timer;
					
					timerLabel.setText("Time Reaining (sec): " + time);
					Thread.sleep(1000);
				}
			}
			catch(Exception e)
			{
				System.out.println("Error in timer");
			}
			
			try
			{
				//will run on AWT thread to end quiz when time gets to 0
				SwingUtilities.invokeAndWait(new Runnable()
				{
					public void run()
					{
						endQuiz();
					}
				});
				
			}
			catch(Exception ex)
			{
				System.out.println("Error in timer");
			}
		}
	}
	
	//method to set up Top Panel with Timer and Score labels
	private JPanel getTopPanel()
	{
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(0,2));
		topPanel.setBackground(new Color(50,50,50));
		
		scoreLabel.setForeground(new Color(255,255,255));
		timerLabel.setForeground(new Color(255,255,255));
		
		topPanel.add(scoreLabel);
		topPanel.add(timerLabel);
		
		updateScoreLabel();
		updateTimerLabel(0);
		
		return topPanel;
	}
	
	//method to set up Center Panel with Text Label and Input Text Field
	private JPanel getCenterPanel()
	{
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0,2));
		centerPanel.setBackground(new Color(50,50,50));
		
		textLabel.setForeground(new Color(255,255,255));
		
		centerPanel.add(textLabel);
		centerPanel.add(inputTextField);
		
		updateTextLabel();
		updateInputText();
		
		return centerPanel;
	}
	
	//method to set up Bottom Panel with Start, Next Question, Check Answer, and Cancel buttons
	private JPanel getBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0,4));
		bottomPanel.setBackground(new Color(50,50,50));
		
		startButton.setMnemonic('M');
		startButton.setToolTipText("Click Here to Start!");
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Thread(new Timer()).start();
				playQuiz();
			}
		});
		
		nextButton.setEnabled(false);
		nextButton.setMnemonic('N');
		nextButton.setToolTipText("Click for Next Question");
		nextButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				playQuiz();
			}
		});
		
		checkButton.setEnabled(false);
		checkButton.setMnemonic('C');
		checkButton.setToolTipText("Click here to Check Answer");
		checkButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				checkAnswer();
			}
		});
		
		cancelButton.setMnemonic('X');
		cancelButton.setToolTipText("Click Here to Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				endQuiz();
			}
		});
		
		bottomPanel.add(startButton);
		bottomPanel.add(nextButton);
		bottomPanel.add(checkButton);
		bottomPanel.add(cancelButton);
		
		return bottomPanel;
	}
	
	//method to set up Amino Acid Quiz GUI
	public AminoAcidQuiz()
	{
		super("Amino Acid Quiz!");
		
		setLocationRelativeTo(null);
		setSize(600,150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
		updateInputText();
		updateTextLabel();
		updateScoreLabel();
		updateTimerLabel(0);
		setVisible(true);
	}
	
	//main method to run GUI
	public static void main(String[] args)
	{
		new AminoAcidQuiz();
	}
}
