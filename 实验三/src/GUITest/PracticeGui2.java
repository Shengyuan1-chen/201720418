package GUITest;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ExerciseTest.BinaryOperation;
import ExerciseTest.Exercise;
import ExerciseTest.ExerciseIOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;

public class PracticeGui2<Answers> extends JFrame {

	private static final long serialVersionUID = -8069047189470765958L;
	private JPanel contentPane;
	private boolean submitted;//ȷ����ǰλ�õ���Ŀ�ǲ����Ѿ�д��
	
	static final int WINDOW_WIDTH = 600;
	static final int WINDOW_HEIGHT = 500;
	static final int OP_AMOUNT = 20;
	static final int OP_COLUMN = 5;
	static final int OP_WIDTH = 65;
	static final int ANSWER_WIDTH = 35;
	static final int COMPONET_HEIGHT = 25;
	static final int STAT_WIDTH = 200;
	static final int STAT_HEIGHT = 100;
	//���ô��ڴ�С����ر�����λ��
	
	private JTextField [] tfOp;//����ʽ���ı���
	private JTextField [] tfAns;//�Ŵ𰸵Ŀ�
	
	private Exercise exercise;
	private int totalAmount;
	private int correctAmount;
	private int wrongAmount;
	private double accuracy;
	private double errorrate;
	private int currentPage;
	private int totalPages;
	
	JToolBar toolBar = new JToolBar();   //����
	JToolBar toolBar_1 = new JToolBar(); //��Ŀ
	JToolBar toolBar_2 = new JToolBar(); // ��ҳ
	//��������������Ӧ�����������
	
	JButton button_7 = new JButton("\u4E0A\u4E00\u9875"); //��һҳ
	JButton button_8 = new JButton("\u4E0B\u4E00\u9875"); //��һҳ
	JMenuItem menuItem_5 = new JMenuItem("\u4E0A\u4E00\u9875"); //�˵���һҳ
	JMenuItem menuItem_6 = new JMenuItem("\u4E0B\u4E00\u9875"); //�˵���һҳ
	
	JLabel label = new JLabel("");
	
	JLabel lblNewLabel = new JLabel("New label");	//��ǰҳ��
	JLabel lblNewLabel_1 = new JLabel("New label"); //��ҳ��
	
	JLabel lblNewLabel_3 = new JLabel("New label"); //״̬��
	JLabel lblNewLabel_4 = new JLabel("New label"); //��״̬��
	
	private void reachBegin() {
		this.button_7.setEnabled(false);
		this.menuItem_5 .setEnabled(false);
		//һ��ʼû����һҳ����ֹ�����һҳ
	}
	private void reachEnd() {
		this.button_8.setEnabled(false);
		this.menuItem_6 .setEnabled(false);
		//�������һҳ��û����һҳ����ֹ�����һҳ
	}
	private void leaveBegin() {
		this.button_7.setEnabled(true);
		this.menuItem_5 .setEnabled(true);
	}
	private void leaveEnd() {
		this.button_8.setEnabled(true);
		this.menuItem_6 .setEnabled(true);
	}
	//���������������뿪���һҳ�����뿪��һҳ��ʱ�򣬿��Ե����һҳ�������һҳ��Ҫ�⿪��ֹ
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PracticeGui2 frame = new PracticeGui2();//���崰�ڶ���
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initComponets() {
		//����һ����Ŀ����ʽ�ʹ�
		this.submitted = false;
		exercise = new Exercise();
		exercise.generateAdditionExercise(OP_AMOUNT);
		
		this.currentPage = 1;
		this.totalPages = 1;
		this.reachBegin();
		this.reachEnd();
		
		tfOp = new JTextField[OP_AMOUNT];
		tfAns = new JTextField[OP_AMOUNT];
		//���ɶ�Ӧ��Ŀ����ʽλ�úʹ�λ��
		
		for (int i = 0; i < OP_AMOUNT; i++) {
			//ѭ��������ʽ�ʹ𰸣���Լ���
			tfOp[i] = new JTextField();
			tfOp[i].setBounds(20 + (i % OP_COLUMN) * (OP_WIDTH + ANSWER_WIDTH + 5),
					50 + (i / OP_COLUMN) * (COMPONET_HEIGHT + 10),
					OP_WIDTH,COMPONET_HEIGHT);
			//������ʽ���λ�úʹ�С��λ����ѭһ���Ĺ���
			tfOp[i].setHorizontalAlignment(JTextField.RIGHT);
			//������ʽ���������ʽΪ�Ҷ���
			tfOp[i].setEditable(false);//��ʽλ���ǲ��ɸ��ĵ�
			contentPane.add(tfOp[i]);
			
			tfAns[i] = new JTextField();
			tfAns[i].setBounds(20 + (i % OP_COLUMN) * (OP_WIDTH + ANSWER_WIDTH + 5) + OP_WIDTH,
					50 + (i / OP_COLUMN) * (COMPONET_HEIGHT + 10),
					ANSWER_WIDTH,
					COMPONET_HEIGHT);
			tfAns[i].setEditable(true);
			contentPane.add(tfAns[i]);
			//�𰸿�ļ������ʽ����ͬ
		}
	}
	
	private void updateComponets() {
		//��ʾ��Ŀ�ʹ𰸿��Լ����Ӧ��ʽ
		this.totalPages = exercise.length() / OP_AMOUNT;
		this.lblNewLabel.setText("��   " + String.valueOf(this.currentPage) + "   ҳ");
		this.lblNewLabel_1.setText("��  " + String.valueOf(this.totalPages) + "   ҳ");
		//����ҳ����ǩ
		
		if (this.currentPage == 1) 
			this.reachBegin();
		else 
			this.leaveBegin();
		
		if (this.currentPage == this.totalPages) 
			this.reachEnd();
		else 
			this.leaveEnd();
		//���õ�ǰҳ����һҳ�������һҳ��ʱ����Ȩ��
		
		BinaryOperation op;
		for (int i = 0; i < OP_AMOUNT; i++) {
			op = exercise.getOperation((currentPage - 1) * OP_AMOUNT + i);
			tfOp[i].setText(op.toString());
			//����Ѿ����ɵ���ʽ�е���ʽ�����뵽�ı�����
			
			if(!submitted) {
				tfAns[i].setBackground(Color.WHITE);
				//ûд������ʱ��𰸿�����Ϊ��ɫ
			}
			else {
				if(exercise.getJudgement((currentPage - 1) * OP_AMOUNT + i))
					tfAns[i].setBackground(Color.GREEN);
				else
					tfAns[i].setBackground(Color.RED);
				//�Ѿ��ύ�˵Ļ��͸��ݴ��Ƿ���ȷ�����𰸿�����Ϊ��ͬ����ɫ
			}
			tfAns[i].setText(exercise.getAnswer((currentPage - 1) * OP_AMOUNT + i));
		}
		
		String s = new String();
		
		if(!submitted ) {
			s = "������ύ����ɻ�ȡ��";
		}
		else {
			//������ȷ�ʵ�һϵ����
			totalAmount = exercise.length();
			correctAmount = exercise.Correct();
			wrongAmount = totalAmount - correctAmount;
			accuracy = (correctAmount * 1.0 / totalAmount * 1.0) * 100.0;
			errorrate = (wrongAmount * 1.0 / totalAmount * 1.0) * 100.0;
			
			String co = String.format("%d", correctAmount);
			String wr = String.format("%d", wrongAmount);
			String ac = String.format("%.2f", accuracy);
			ac += '%';
			String er = String.format("%.2f", errorrate);
			er += '%';
			s = "��ȷ���� �� " + co + "   " + "�������� �� " + wr + "   " + 
					"��ȷ��  : " + ac + "   " + "������ : " + er;
		}
		
		lblNewLabel_3.setText("״̬��  " 
				+ "��Ŀ���� - " + exercise.getExerciseType()
				+ "     "
				+ "��Ŀ���� - " + exercise.length());
		lblNewLabel_4.setText(s);
	}
	
	private void impExercise() {	
		//������Ŀ
		JFileChooser jfc = new JFileChooser();//�½�һ���ļ�ѡ����
		jfc.showOpenDialog(null);//���ò�����ֵ���Ըı��ļ�ѡ��������Ļ�ϵĵ���λ��
		
		File file = jfc.getSelectedFile();
		try {
			exercise = Exercise.loadObject(file.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "������Ŀ�ɹ���"
					,"��ʾ",JOptionPane.INFORMATION_MESSAGE);
			this.submitted = false;
			updateComponets();
		}catch(NullPointerException npe) {
			
		}catch(ExerciseIOException e) {
			JOptionPane.showMessageDialog(null, "������Ŀʧ�ܣ���������Ϊѡ���˴�����ļ�",
					"����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void expExercise() {	
		//������Ŀ
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(null);
		File file = jfc.getSelectedFile();
		try {
			exercise.saveObject(file.getAbsolutePath(), exercise);
			//����һ����Ŀ�������ļ�����
			JOptionPane.showMessageDialog(null, "������Ŀ�ɹ���"
					,"��ʾ",JOptionPane.INFORMATION_MESSAGE);
		}catch(NullPointerException npe) {
			
		}catch(ExerciseIOException e) {
			JOptionPane.showMessageDialog(null, "������Ŀʧ�ܣ���������Ϊѡ���˴�����ļ�",
					"����",JOptionPane.ERROR_MESSAGE);
		}
	}
	//���뵼����Ŀ�õĶ���JFileChooser������������������Ŀ��·����֮����file������е��뵼��
	
	private void prePage() {	//�Ϸ�һҳ
		getAnswers(this.currentPage);
		//���·�ҳ��ʱ���Ȱѵ�ǰҳ�����ݴ�����
		if(this.currentPage == this.totalPages) 
			this.leaveEnd();
		if(--currentPage == 1) 
			this.reachBegin();
		//����ҳ��֮���ҳ�����·�ҳ������
		this.lblNewLabel.setText(String.valueOf(currentPage));
		updateComponets();
	}
	private void nextPage() {	//�·�һҳ
		getAnswers(this.currentPage);
		if(this.currentPage == this.totalPages)
			this.leaveEnd();
		if(++currentPage == totalPages) 
			this.reachEnd();
		this.lblNewLabel.setText(String.valueOf(currentPage));
		updateComponets();
	}
	
	private void getAnswers(int pageIndex) { //��ȡ��
		for (int i = 0; i < OP_AMOUNT; i++) {
			exercise.setAnswer((pageIndex - 1) * OP_AMOUNT + i, tfAns[i].getText());
		}
	}
	
	private void generateExercise() {	//����������Ŀ
		int length = exercise.length();
		exercise.generateWithFormerType(length);
		//ͨ�����ε��÷�����������Ŀ����ʵ������������Ŀ�Ĺ���
		updateComponets();
	}
	
	private void judgeAnswer() {
		this.submitted = true;
		getAnswers(this.currentPage);
		updateComponets();//��Ŀǰ������Ŀ�������ģ����´𰸿����ɫ
	}
	
	private void clearAnswers() {
		exercise.clearAnswers();
		this.submitted = false;
		updateComponets();
		//�Ѵ���գ��ύ״̬��Ϊδ�ύ
	}
	
	public PracticeGui2() {
		//���ӻ����ڱ༭GUI���棬��Ӷ�Ӧ�Ķ�������
		setTitle("100\u4EE5\u5185\u7684\u53E3\u7B97\u7EC3\u4E60\u7A0B\u5E8F");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 376);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u6587\u4EF6");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("\u5BFC\u5165");
		menu.add(menuItem);
		
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				impExercise();
			}
		});
		
		JMenuItem menuItem_1 = new JMenuItem("\u5BFC\u51FA");
		menu.add(menuItem_1);
		
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expExercise();
			}
		});
		
		JMenu menu_1 = new JMenu("\u9898\u76EE\u8BBE\u7F6E");
		menuBar.add(menu_1);
		
		JMenu menuItem_7 = new JMenu("\u6839\u636E\u7C7B\u578B\u751F\u6210");
		menu_1.add(menuItem_7);
		
		JCheckBoxMenuItem chckbxmntmadd = new JCheckBoxMenuItem("\u52A0\u6CD5\u9898\u76EE");
		menuItem_7.add(chckbxmntmadd);
		chckbxmntmadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int length = exercise.length();
				exercise.generateAdditionExercise(length);
				updateComponets();
			}
		});
		
		JCheckBoxMenuItem chckbxmntmsub = new JCheckBoxMenuItem("\u51CF\u6CD5\u9898\u76EE");
		menuItem_7.add(chckbxmntmsub);
		
		chckbxmntmsub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int length = exercise.length();
				exercise.generateSubstractExercise(length);
				updateComponets();
			}
		});
		
		JCheckBoxMenuItem chckbxmntmas = new JCheckBoxMenuItem("\u52A0\u51CF\u6DF7\u5408");
		menuItem_7.add(chckbxmntmas);
		
		chckbxmntmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int length = exercise.length();
				exercise.generateBinaryExercise(length);
				updateComponets();
			}
		});
		
		JMenu menuItem_8 = new JMenu("\u6839\u636E\u6570\u91CF\u751F\u6210");
		menu_1.add(menuItem_8);
		
		JCheckBoxMenuItem checkBoxMenuItem_3 = new JCheckBoxMenuItem("20");
		menuItem_8.add(checkBoxMenuItem_3);
		
		checkBoxMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exercise.generateWithFormerType(20);
				updateComponets();
			}
		});
		
		JCheckBoxMenuItem checkBoxMenuItem_4 = new JCheckBoxMenuItem("40");
		menuItem_8.add(checkBoxMenuItem_4);
		
		checkBoxMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exercise.generateWithFormerType(40);
				updateComponets();
			}
		});
		
		JCheckBoxMenuItem checkBoxMenuItem_5 = new JCheckBoxMenuItem("100");
		menuItem_8.add(checkBoxMenuItem_5);
		
		checkBoxMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exercise.generateWithFormerType(100);
				updateComponets();
			}
		});
		
		JMenu menu_2 = new JMenu("\u9898\u76EE\u64CD\u4F5C");
		menuBar.add(menu_2);
		
		JMenuItem menuItem_2 = new JMenuItem("\u91CD\u65B0\u751F\u6210");
		menu_2.add(menuItem_2);
		
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateExercise();
				clearAnswers();
			}
		});
		
		JMenuItem menuItem_3 = new JMenuItem("\u6E05\u7A7A");
		menu_2.add(menuItem_3);
		
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAnswers();
			}
		});
		
		JMenuItem menuItem_4 = new JMenuItem("\u63D0\u4EA4");
		menu_2.add(menuItem_4);
		
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				judgeAnswer();
			}
		});
		
		JMenu menu_3 = new JMenu("\u67E5\u770B");
		menuBar.add(menu_3);
		
		
		menu_3.add(menuItem_5);
		
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prePage();
			}
			
		});
		
		menu_3.add(menuItem_6);
		
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextPage();
			}
			
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		
		lblNewLabel_2.setText("------------------------------------------"
				+ "------------------------------------------"
				+ "-----------------------------------------------------");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(toolBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(toolBar_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(137)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(63)
									.addComponent(lblNewLabel)
									.addGap(42)
									.addComponent(lblNewLabel_1))
								.addComponent(label)))
						.addComponent(lblNewLabel_2)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_3))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_4)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(toolBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(toolBar_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
					.addComponent(label)
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_4)
					.addGap(19))
		);
		
		
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prePage();
			}
		});
		
		toolBar_2.add(button_7);
		
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextPage();
			}
		});
		toolBar_2.add(button_8);
		
		JButton button_4 = new JButton("\u91CD\u65B0\u751F\u6210");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateExercise();
				clearAnswers();
			}
		});
		toolBar_1.add(button_4);
		
		JButton button = new JButton("\u6E05\u7A7A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAnswers();
			}
		});
		toolBar_1.add(button);
		
		JButton button_6 = new JButton("\u63D0\u4EA4");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				judgeAnswer();
			}
		});
		toolBar_1.add(button_6);
		
		JButton button_2 = new JButton("\u5BFC\u5165");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				impExercise();
			}
		});
		toolBar.add(button_2);
		
		JButton button_3 = new JButton("\u5BFC\u51FA");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expExercise();
			}
		});
		toolBar.add(button_3);
		contentPane.setLayout(gl_contentPane);
		
		initComponets();
		updateComponets();
	}
}