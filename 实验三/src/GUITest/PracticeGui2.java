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
	private boolean submitted;//确定当前位置的题目是不是已经写了
	
	static final int WINDOW_WIDTH = 600;
	static final int WINDOW_HEIGHT = 500;
	static final int OP_AMOUNT = 20;
	static final int OP_COLUMN = 5;
	static final int OP_WIDTH = 65;
	static final int ANSWER_WIDTH = 35;
	static final int COMPONET_HEIGHT = 25;
	static final int STAT_WIDTH = 200;
	static final int STAT_HEIGHT = 100;
	//设置窗口大小和相关变量的位置
	
	private JTextField [] tfOp;//放算式的文本域
	private JTextField [] tfAns;//放答案的框
	
	private Exercise exercise;
	private int totalAmount;
	private int correctAmount;
	private int wrongAmount;
	private double accuracy;
	private double errorrate;
	private int currentPage;
	private int totalPages;
	
	JToolBar toolBar = new JToolBar();   //导入
	JToolBar toolBar_1 = new JToolBar(); //题目
	JToolBar toolBar_2 = new JToolBar(); // 翻页
	//三个工具栏，对应三个功能组合
	
	JButton button_7 = new JButton("\u4E0A\u4E00\u9875"); //上一页
	JButton button_8 = new JButton("\u4E0B\u4E00\u9875"); //下一页
	JMenuItem menuItem_5 = new JMenuItem("\u4E0A\u4E00\u9875"); //菜单上一页
	JMenuItem menuItem_6 = new JMenuItem("\u4E0B\u4E00\u9875"); //菜单下一页
	
	JLabel label = new JLabel("");
	
	JLabel lblNewLabel = new JLabel("New label");	//当前页数
	JLabel lblNewLabel_1 = new JLabel("New label"); //总页数
	
	JLabel lblNewLabel_3 = new JLabel("New label"); //状态栏
	JLabel lblNewLabel_4 = new JLabel("New label"); //答案状态栏
	
	private void reachBegin() {
		this.button_7.setEnabled(false);
		this.menuItem_5 .setEnabled(false);
		//一开始没有上一页，禁止点击上一页
	}
	private void reachEnd() {
		this.button_8.setEnabled(false);
		this.menuItem_6 .setEnabled(false);
		//到达最后一页后没有下一页，禁止点击下一页
	}
	private void leaveBegin() {
		this.button_7.setEnabled(true);
		this.menuItem_5 .setEnabled(true);
	}
	private void leaveEnd() {
		this.button_8.setEnabled(true);
		this.menuItem_6 .setEnabled(true);
	}
	//这两个方法用于离开最后一页或者离开第一页的时候，可以点击第一页或者最后一页，要解开禁止
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PracticeGui2 frame = new PracticeGui2();//定义窗口对象
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initComponets() {
		//加入一定数目的算式和答案
		this.submitted = false;
		exercise = new Exercise();
		exercise.generateAdditionExercise(OP_AMOUNT);
		
		this.currentPage = 1;
		this.totalPages = 1;
		this.reachBegin();
		this.reachEnd();
		
		tfOp = new JTextField[OP_AMOUNT];
		tfAns = new JTextField[OP_AMOUNT];
		//生成对应数目的算式位置和答案位置
		
		for (int i = 0; i < OP_AMOUNT; i++) {
			//循环加入算式和答案，配对加入
			tfOp[i] = new JTextField();
			tfOp[i].setBounds(20 + (i % OP_COLUMN) * (OP_WIDTH + ANSWER_WIDTH + 5),
					50 + (i / OP_COLUMN) * (COMPONET_HEIGHT + 10),
					OP_WIDTH,COMPONET_HEIGHT);
			//设置算式框的位置和大小，位置遵循一定的规律
			tfOp[i].setHorizontalAlignment(JTextField.RIGHT);
			//设置算式框里面的算式为右对齐
			tfOp[i].setEditable(false);//算式位置是不可更改的
			contentPane.add(tfOp[i]);
			
			tfAns[i] = new JTextField();
			tfAns[i].setBounds(20 + (i % OP_COLUMN) * (OP_WIDTH + ANSWER_WIDTH + 5) + OP_WIDTH,
					50 + (i / OP_COLUMN) * (COMPONET_HEIGHT + 10),
					ANSWER_WIDTH,
					COMPONET_HEIGHT);
			tfAns[i].setEditable(true);
			contentPane.add(tfAns[i]);
			//答案框的加入和算式框相同
		}
	}
	
	private void updateComponets() {
		//显示题目和答案框以及其对应格式
		this.totalPages = exercise.length() / OP_AMOUNT;
		this.lblNewLabel.setText("第   " + String.valueOf(this.currentPage) + "   页");
		this.lblNewLabel_1.setText("共  " + String.valueOf(this.totalPages) + "   页");
		//设置页数标签
		
		if (this.currentPage == 1) 
			this.reachBegin();
		else 
			this.leaveBegin();
		
		if (this.currentPage == this.totalPages) 
			this.reachEnd();
		else 
			this.leaveEnd();
		//设置当前页到第一页或者最后一页的时候点击权限
		
		BinaryOperation op;
		for (int i = 0; i < OP_AMOUNT; i++) {
			op = exercise.getOperation((currentPage - 1) * OP_AMOUNT + i);
			tfOp[i].setText(op.toString());
			//获得已经生成的算式中的算式并加入到文本域中
			
			if(!submitted) {
				tfAns[i].setBackground(Color.WHITE);
				//没写东西的时候答案框设置为白色
			}
			else {
				if(exercise.getJudgement((currentPage - 1) * OP_AMOUNT + i))
					tfAns[i].setBackground(Color.GREEN);
				else
					tfAns[i].setBackground(Color.RED);
				//已经提交了的话就根据答案是否正确，将答案框设置为不同的颜色
			}
			tfAns[i].setText(exercise.getAnswer((currentPage - 1) * OP_AMOUNT + i));
		}
		
		String s = new String();
		
		if(!submitted ) {
			s = "点击【提交】后可获取答案";
		}
		else {
			//计算正确率等一系列量
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
			s = "正确题数 ： " + co + "   " + "错误题数 ： " + wr + "   " + 
					"正确率  : " + ac + "   " + "错误率 : " + er;
		}
		
		lblNewLabel_3.setText("状态：  " 
				+ "题目类型 - " + exercise.getExerciseType()
				+ "     "
				+ "题目数量 - " + exercise.length());
		lblNewLabel_4.setText(s);
	}
	
	private void impExercise() {	
		//导入题目
		JFileChooser jfc = new JFileChooser();//新建一个文件选择器
		jfc.showOpenDialog(null);//设置参数的值可以改变文件选择器在屏幕上的弹出位置
		
		File file = jfc.getSelectedFile();
		try {
			exercise = Exercise.loadObject(file.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "导入题目成功！"
					,"提示",JOptionPane.INFORMATION_MESSAGE);
			this.submitted = false;
			updateComponets();
		}catch(NullPointerException npe) {
			
		}catch(ExerciseIOException e) {
			JOptionPane.showMessageDialog(null, "导入题目失败，可能是因为选择了错误的文件",
					"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void expExercise() {	
		//导出题目
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(null);
		File file = jfc.getSelectedFile();
		try {
			exercise.saveObject(file.getAbsolutePath(), exercise);
			//把这一道题目导出到文件里面
			JOptionPane.showMessageDialog(null, "导出题目成功！"
					,"提示",JOptionPane.INFORMATION_MESSAGE);
		}catch(NullPointerException npe) {
			
		}catch(ExerciseIOException e) {
			JOptionPane.showMessageDialog(null, "导出题目失败，可能是因为选择了错误的文件",
					"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	//导入导出题目用的都是JFileChooser对象，用这个对象来获得目标路径，之后用file对象进行导入导出
	
	private void prePage() {	//上翻一页
		getAnswers(this.currentPage);
		//上下翻页的时候，先把当前页的内容存下来
		if(this.currentPage == this.totalPages) 
			this.leaveEnd();
		if(--currentPage == 1) 
			this.reachBegin();
		//调整页数之后对页数上下翻页的限制
		this.lblNewLabel.setText(String.valueOf(currentPage));
		updateComponets();
	}
	private void nextPage() {	//下翻一页
		getAnswers(this.currentPage);
		if(this.currentPage == this.totalPages)
			this.leaveEnd();
		if(++currentPage == totalPages) 
			this.reachEnd();
		this.lblNewLabel.setText(String.valueOf(currentPage));
		updateComponets();
	}
	
	private void getAnswers(int pageIndex) { //获取答案
		for (int i = 0; i < OP_AMOUNT; i++) {
			exercise.setAnswer((pageIndex - 1) * OP_AMOUNT + i, tfAns[i].getText());
		}
	}
	
	private void generateExercise() {	//重新生成题目
		int length = exercise.length();
		exercise.generateWithFormerType(length);
		//通过两次调用方法，更新题目对象，实现重新生成题目的功能
		updateComponets();
	}
	
	private void judgeAnswer() {
		this.submitted = true;
		getAnswers(this.currentPage);
		updateComponets();//把目前做的题目进行批改，更新答案框的颜色
	}
	
	private void clearAnswers() {
		exercise.clearAnswers();
		this.submitted = false;
		updateComponets();
		//把答案清空，提交状态改为未提交
	}
	
	public PracticeGui2() {
		//可视化窗口编辑GUI界面，添加对应的动作监听
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