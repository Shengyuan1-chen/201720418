package GUITest;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

import ExerciseTest.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PracticeGui extends JFrame {

	private static final long serialVersionUID = 6478286329988994564L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	static final int WINDOW_WIDTH = 600;
	static final int WINDOW_HEIGHT = 500;
	static final int OP_AMOUNT = 20;
	static final int OP_COLUMN = 5;
	static final int OP_WIDTH = 65;
	static final int ANSWER_WIDTH = 35;
	static final int COMPONET_HEIGHT = 25;
	static final int STAT_WIDTH = 200;
	static final int STAT_HEIGHT = 100;
	
	private JTextField [] tfOp;
	private JTextField [] tfAns;
	private JTextArea taStat;
	
	private Exercise exercises;
	private int correctAmount;
	private int wrongAmount;
	private double accuracy;
	private double errorrate;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	
	private void judge() {
		BinaryOperation op;
		correctAmount = wrongAmount = 0;
		accuracy = errorrate = 0;
		for (int i = 0; i < OP_AMOUNT; i++) {
			op = exercises.getOperation(i);
			String result = String.valueOf(op.getvalue());
			String answer = tfAns[i].getText().trim();
			if (result.contentEquals(answer)) {
				tfAns[i].setBackground(Color.GREEN);
				correctAmount++;
			}
			else {
				tfAns[i].setBackground(Color.RED);
				wrongAmount++;
			}
		}
		accuracy = (correctAmount * 1.0 / OP_AMOUNT * 1.0) * 100.0;
		errorrate = (wrongAmount * 1.0 / OP_AMOUNT * 1.0) * 100.0;
		
		String ac = String.format("%.2f", accuracy);
		ac += '%';
		String er = String.format("%.2f", errorrate);
		er += '%';
		taStat.setText("统计信息：\n\n" + "总题数： " + OP_AMOUNT 
				+ "     正确题数 ：" + correctAmount + "     错误题数： " + wrongAmount + '\n' 
				+ "                            正确率： " + ac + "        错误率： " + er);
	}
	
	private void initExerciseComponets() {
		exercises = new Exercise();
		exercises.generateAdditionExercise(OP_AMOUNT);
		
		tfOp = new JTextField[OP_AMOUNT];
		tfAns = new JTextField[OP_AMOUNT];
		for (int i = 0; i < OP_AMOUNT; i++) {
			tfOp[i] = new JTextField();
			tfOp[i].setBounds(20 + (i % OP_COLUMN) * (OP_WIDTH + ANSWER_WIDTH + 5),
					20 + (i / OP_COLUMN) * (COMPONET_HEIGHT + 10),
					OP_WIDTH,
					COMPONET_HEIGHT);
			tfOp[i].setHorizontalAlignment(JTextField.RIGHT);
			tfOp[i].setEditable(false);
			contentPane.add(tfOp[i]);
			
			tfAns[i] = new JTextField();
			tfAns[i].setBounds(20 + (i % OP_COLUMN) * (OP_WIDTH + ANSWER_WIDTH + 5) + OP_WIDTH,
					20 + (i / OP_COLUMN) * (COMPONET_HEIGHT + 10),
					ANSWER_WIDTH,
					COMPONET_HEIGHT);
			tfAns[i].setEditable(true);
			contentPane.add(tfAns[i]);
		}
		
		
		
		//按钮与统计信息文本域
		JButton button = new JButton("\u91CD\u65B0\u751F\u6210\u9898\u76EE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exercises.generateAdditionExercise(OP_AMOUNT);
				updateExerciseComponets();
			}
		});
		
		JButton button_1 = new JButton("\u63D0\u4EA4\u7B54\u6848");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				judge();
			}
		});
		
		taStat = new JTextArea();
		
		btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton, BorderLayout.WEST);
		
		btnNewButton_1 = new JButton("New button");
		contentPane.add(btnNewButton_1, BorderLayout.EAST);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(button_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(47)
					.addComponent(taStat, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(246, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(290, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(taStat, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button)
							.addGap(36)
							.addComponent(button_1)))
					.addGap(64))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	private void updateExerciseComponets() {
		BinaryOperation op;
		for (int i = 0; i < OP_AMOUNT; i++) {
			op = exercises.getOperation(i);
			tfOp[i].setText(op.toString());
			tfAns[i].setBackground(Color.WHITE);
			tfAns[i].setText(" ");
		}
		String ac = String.format("%.2f", accuracy);
		ac += '%';
		String er = String.format("%.2f", errorrate);
		er += '%';
		taStat.setText("统计信息：\n\n" + "总题数： " + OP_AMOUNT 
				+ "     正确题数 ：" + correctAmount + "     错误题数： " + wrongAmount + '\n' 
				+ "                            正确率： " + ac + "        错误率： " + er);
		taStat.setEditable(false);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PracticeGui frame = new PracticeGui();
					frame.setTitle("100以内的口算练习程序");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PracticeGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initExerciseComponets();
		updateExerciseComponets();
	}
}