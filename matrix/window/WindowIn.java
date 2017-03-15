package matrix.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import matrix.listener.ActionPolice;
import matrix.listener.ButtonGroupListener;
import matrix.listener.CancelListener;
import matrix.listener.ClearListener;
import matrix.listener.ExitListener;
import matrix.listener.ItemPolice;
import matrix.listener.KeyPolice;
import matrix.listener.MousePolice;
import matrix.r.R;

@SuppressWarnings("serial")
public class WindowIn extends JFrame {
	JButton button1[][], button2[][], button_cal, button_exit;

	JLabel label1;// ��һ������������ʾ����
	JLabel label2;// �ڶ�������������ʾ����
	JLabel label3;// ������壬������ʾ����

	JPanel pane1;// ��һ��������� 8*8
	JPanel pane2;// �ڶ���������� 8*8
	JPanel pane3;// ������壬�������㡢�˳���ѡ��

	JComboBox<String> combobox;

	JTextField text1[][], text2[][], text;// textΪ�����������ʱ����ʵ��

	JButton button[];// 4����������ɾ��

	public WindowIn() {
		init();//��ʼ������
		registObject();//���ؼ�ע�ᵽ�ռ����
		addListener();//Ϊ�ؼ�ע�������
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void addListener() {
		ItemPolice item = new ItemPolice();
		ActionPolice action = new ActionPolice();
		MousePolice mouse = new MousePolice();
		KeyPolice key = new KeyPolice();
		CancelListener cancelListener=new CancelListener();
		ClearListener clearListener=new ClearListener();
		ExitListener exitListener=new ExitListener();
		ButtonGroupListener buttonGroupListener=new ButtonGroupListener();
		
		for(int i=0;i<button.length;++i){//"����"��"ɾ��"��ť
			if(i%2==0)
				button[i].addActionListener(cancelListener);
			else
				button[i].addActionListener(clearListener);
			button[i].addKeyListener(key);
		}
		
		for(int i=0;i<button1.length;++i){//8*8��ť���ı���
			for(int j=0;j<button1[0].length;++j){
				button1[i][j].addMouseListener(mouse);
				button2[i][j].addMouseListener(mouse);
				button1[i][j].addKeyListener(key);
				button2[i][j].addKeyListener(key);
				button1[i][j].addActionListener(buttonGroupListener);
				button2[i][j].addActionListener(buttonGroupListener);
				
				text1[i][j].addKeyListener(key);
				text2[i][j].addKeyListener(key);
			}
		}

		combobox.addKeyListener(key);
		combobox.addItemListener(item);
		button_exit.addKeyListener(key);
		button_exit.addActionListener(exitListener);
		button_cal.addActionListener(action);
		button_cal.addKeyListener(key);
		text.addKeyListener(key);
	}

	private void registObject() {
		R r=R.getInstance();
		
		try {
			r.registObject("window_in", this);
			
			r.registObject("btn_group_1", button1);
			r.registObject("btn_group_2", button2);
			r.registObject("btn_cal", button_cal);
			r.registObject("btn_exit", button_exit);
			
			r.registObject("lbl_1",label1);
			r.registObject("lbl_2",label2);
			r.registObject("lbl_3",label3);
			
			r.registObject("panel_1", pane1);
			r.registObject("panel_2", pane2);
			r.registObject("panel_cal", pane3);
			
			r.registObject("combobox", combobox);
			
			r.registObject("txt_group_1", text1);
			r.registObject("txt_group_2", text2);
			r.registObject("txt_hint_mult", text);
			
			r.registObject("btn_back_and_clear", button);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		Font font = new Font("����", 1, 20);
		UIManager.put("Label.font", font);
		UIManager.put("Button.font", font);
		UIManager.put("TextField.font", font);

		button1 = new JButton[8][8];
		button2 = new JButton[8][8];

		button = new JButton[4];// ���������

		pane1 = new JPanel();
		pane2 = new JPanel();
		pane3 = new JPanel();

		pane1.setLayout(new GridLayout(8, 8));
		pane2.setLayout(new GridLayout(8, 8));

		label1 = new JLabel("��ѡ����������������.");
		label2 = new JLabel("��ѡ����������������.");
		label3 = new JLabel("������һ��ʵ����");
		
		text = new JTextField(5);
		text1=new JTextField[8][8];
		text2=new JTextField[8][8];
		
		label3.setVisible(false);
		text.setVisible(false);
		
		button_cal = new JButton("����");
		button_exit = new JButton("�˳�");

		String[] combo_str = {"��ѡ���������", "��������ʽ", "���������", "��������", "��������",
				"������ֵ", "���������", "������޹���", "��������", "��ͬ�;���ĺ�", "��ͬ�;���Ĳ�",
				"�ж��������Ƿ�ȼ�", "�ж��������Ƿ������Ա�ʾ", "�ж��������Ƿ��������", "��������Է�����ͨ��",
				"��������Է����������ϵ", "���������Է�����ͨ��", "���������Է����������ϵ"};
		combobox = new JComboBox<String>(combo_str);
		combobox.setFont(new Font("����", 1, 17));

		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0)
				button[i] = new JButton("����");
			else
				button[i] = new JButton("���");
			add(button[i]);
			button[i].setVisible(false);
		}

		for (int i = 0; i < 8; i++) { // Ϊ8��8��ť��8*8�ı������δ�������
			for (int j = 0; j < 8; j++) {
				button1[i][j] = new JButton("");
				button2[i][j] = new JButton("");
				button1[i][j].setBackground(new Color(234, 234, 234));
				button2[i][j].setBackground(new Color(234, 234, 234));
				pane1.add(button1[i][j]);
				pane2.add(button2[i][j]);
				
				text1[i][j] = new JTextField(2);
				text2[i][j] = new JTextField(2);
			}
		}

		pane3.add(combobox);
		pane3.add(button_cal);
		pane3.add(button_exit);
		pane3.add(label3);
		pane3.add(text);

		add(label2);
		add(label1);
		add(pane1);
		add(pane2);
		add(pane3);
		setLayout(null);

		pane1.setBounds(30, 70, 200, 200);
		pane2.setBounds(290, 70, 200, 200);
		pane3.setBounds(508, 74, 280, 150);
		label2.setBounds(280, 40, 300, 20);
		label1.setBounds(20, 40, 300, 20);
		button[0].setBounds(30, 280, 76, 34);
		button[1].setBounds(130, 280, 76, 34);
		button[2].setBounds(290, 280, 76, 34);
		button[3].setBounds(390, 280, 76, 34);
	}
}