package matrix.listener;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import matrix.Matrix;
import matrix.r.R;
import matrix.window.WindowIn;

public class ActionPolice implements ActionListener {
	private JPanel panel1, panel2;
	private JTextField text1[][], text2[][];
	private JTextField text;// ����ʵ���ı���
	private WindowIn win;
	private JComboBox<String> combobox;

	@SuppressWarnings("unchecked")
	public ActionPolice() {
		R r = R.getInstance();
		try {
			win = (WindowIn) r.getObject("window_in");

			panel1 = (JPanel) r.getObject("panel_1");
			panel2 = (JPanel) r.getObject("panel_2");

			text1 = (JTextField[][]) r.getObject("txt_group_1");
			text2 = (JTextField[][]) r.getObject("txt_group_2");
			text = (JTextField) r.getObject("txt_hint_mult");

			combobox = (JComboBox<String>) r.getObject("combobox");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int item = combobox.getSelectedIndex();

		if (item == 0) {
			JOptionPane.showMessageDialog(null, "��ѡ��������ͣ�", "����",
					JOptionPane.WARNING_MESSAGE);
		} else if (item > 7 && item < 13) { // ��������
			if (!isSelected(panel1)) {
				JOptionPane.showMessageDialog(null, "��ѡ���һ���������������", "����",
						JOptionPane.WARNING_MESSAGE);
			} else if (!isSelected(panel2)) {
				JOptionPane.showMessageDialog(null, "��ѡ��ڶ����������������", "����",
						JOptionPane.WARNING_MESSAGE);
			} else if (!hasAssigned(panel1, text1)) {
				JOptionPane.showMessageDialog(win, "��û��Ϊ��һ�������ÿ��Ԫ�ظ�ֵ", "����",
						JOptionPane.WARNING_MESSAGE);
			} else if (!hasAssigned(panel2, text2)) {
				JOptionPane.showMessageDialog(win, "��û��Ϊ�ڶ��������ÿ��Ԫ�ظ�ֵ", "����",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Matrix matrix1 = new Matrix(
						getValueFromTextField(panel1, text1));
				Matrix matrix2 = new Matrix(
						getValueFromTextField(panel2, text2));

				switch (item) {
					case 8 :// ���
						matrix1.matrixmuti(matrix2);
						popupDialog("���", matrix1.getOutput());
						break;
					case 9 :// ���
						matrix1.add(matrix2);
						popupDialog("���", matrix1.getOutput());
						break;
					case 10 :// ���
						matrix1.sub(matrix2);
						popupDialog("���", matrix1.getOutput());
						break;
					case 11 :// ������ȼ�
						if (matrix1.isEqual(matrix2))
							JOptionPane.showMessageDialog(win, "������������ȼۣ�",
									"������", JOptionPane.PLAIN_MESSAGE);
						else
							JOptionPane.showMessageDialog(win, "�����������鲻�ȼۣ�",
									"������", JOptionPane.PLAIN_MESSAGE);
						break;
					case 12 :// �ܷ����Ա�ʾ
						if (matrix1.canRepresent(matrix2))
							JOptionPane.showMessageDialog(win,
									"B(�ұ�)�������ܱ�A(���)���������Ա�ʾ��", "������",
									JOptionPane.PLAIN_MESSAGE);
						else
							JOptionPane.showMessageDialog(win,
									"B(�ұ�)�����鲻�ܱ�A(���)���������Ա�ʾ��", "������",
									JOptionPane.PLAIN_MESSAGE);
						break;
				}
			}
		} else { // һ������
			if (!isSelected(panel1)) {
				JOptionPane.showMessageDialog(null, "��ѡ��������������", "����",
						JOptionPane.WARNING_MESSAGE);
			} else if (!hasAssigned(panel1, text1)) {
				JOptionPane.showMessageDialog(win, "��û��Ϊ�����ÿ��Ԫ�ظ�ֵ", "����",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Matrix matrix = new Matrix(getValueFromTextField(panel1, text1));

				if (item == 1) {
					double result = matrix.getResult();
					JOptionPane.showMessageDialog(win, "����ʽ��ֵΪ��" + result,
							"������", JOptionPane.PLAIN_MESSAGE);
				} else if (item == 2) {
					if (text.getText().equals(""))
						JOptionPane.showMessageDialog(null, "��û��������˵�ʵ����",
								"����", JOptionPane.WARNING_MESSAGE);
					else {
						matrix.scalarmuti(Double.parseDouble(text.getText()));
						popupDialog("����", matrix.getOutput());
					}
				} else if (item == 3) {
					if (matrix.getResult() == 0)
						JOptionPane.showMessageDialog(win,
								"������������ʽΪ0���޷���������", "������",
								JOptionPane.WARNING_MESSAGE);
					else {
						matrix.inverse();
						popupDialog("����", matrix.getOutput());
					}
				} else if (item == 4) {
					int rank = matrix.getRank();
					JOptionPane.showMessageDialog(win, "�������Ϊ��" + rank, "������",
							JOptionPane.PLAIN_MESSAGE);
				} else if (item == 5) {
					double given[] = matrix.given();
					String out = null;
					if (given[0] == given[1] && given[1] == given[2])
						out = "��1=��2=��3=" + given[0];
					else if (given[1] == given[2])
						out = "��1=" + given[0] + "  " + "��2=��3=" + given[1];
					else if (given[0] == given[2])
						out = "��1=��2=" + given[0] + "  " + "��3=" + given[1];
					else
						out = "��1=" + given[0] + "  " + "��2=" + given[1] + "  "
								+ "��3=" + given[2];
					JOptionPane.showMessageDialog(win, "���������ֵΪ��\n" + out,
							"������", JOptionPane.PLAIN_MESSAGE);
				} else if (item == 6) {
					matrix.simplest();
					popupDialog("���", matrix.getOutput());
				} else if (item == 7) {
					matrix.maxIrrelevant();
					popupDialog("�޹�", matrix.getOutput());
				} else if (item == 13) {
					if (matrix.isRelevant())
						JOptionPane.showMessageDialog(win, "��������������أ�", "������",
								JOptionPane.PLAIN_MESSAGE);
					else
						JOptionPane.showMessageDialog(win, "�������������޹أ�", "������",
								JOptionPane.PLAIN_MESSAGE);
				} else if (item == 14) {
					if (matrix.getInput()[0].length == matrix.getRank())
						JOptionPane.showMessageDialog(win,
								"������Է�����Ľ�Ψһ����ͨ�⣬�Լ����_����", "������",
								JOptionPane.PLAIN_MESSAGE);
					else {
						matrix.homogen();
						popupDialog("��ͨ", matrix.getOutput());
					}
				} else if (item == 15) {
					if (matrix.getInput()[0].length == matrix.getRank())
						JOptionPane.showMessageDialog(win,
								"������Է�����Ľ�Ψһ���޻�����ϵ���Լ����_����", "������",
								JOptionPane.PLAIN_MESSAGE);
					else {
						matrix.homogenBasic();
						popupDialog("��ϵ", matrix.getOutput());
					}
				} else if (item == 16) {
					boolean operation = true;
					matrix.simplest();
					double[][] f = matrix.getOutput();
					for (int i = 0; i < f[0].length; i++) {
						if (f[f.length - 1][i] == 1 && i == f[0].length - 1) {
							operation = false;
							JOptionPane.showMessageDialog(win, "��������Է������޽⣡",
									"������", JOptionPane.PLAIN_MESSAGE);
						}
					}
					if (f[0].length == matrix.getRank() + 1) {
						operation = false;
						JOptionPane.showMessageDialog(win,
								"������Է�����Ľ�Ψһ����ͨ�⣬�Լ����_����", "������",
								JOptionPane.PLAIN_MESSAGE);
					}
					if (operation) {
						matrix.nonhomogen();
						popupDialog("��ͨ", matrix.getOutput());
					}
				} else if (item == 17) {
					boolean operation = true;
					matrix.simplest();
					double[][] f = matrix.getOutput();
					matrix.simplest();
					for (int i = 0; i < f[0].length; i++) {
						if (f[f.length - 1][i] == 1 && i == f[0].length - 1) {
							operation = false;
							JOptionPane.showMessageDialog(win, "��������Է������޽⣡",
									"������", JOptionPane.PLAIN_MESSAGE);
						}
					}
					if (f[0].length == matrix.getRank() + 1) {
						operation = false;
						JOptionPane.showMessageDialog(win,
								"������Է�����Ľ�Ψһ���޻�����ϵ���Լ����_����", "������",
								JOptionPane.PLAIN_MESSAGE);
					}
					if (operation) {
						matrix.nonhomogenBasic();
						popupDialog("��ϵ", matrix.getOutput());
					}
				}
			}
		}
	}

	private void popupDialog(String a, double[][] f) { // ���������
		JLabel label = new JLabel("");
		if (a == "��ͨ" || a == "��ͨ" || a == "��ϵ" || a == "��ϵ") {
			if (a == "��ͨ")
				label.setText("������Է������ͨ��Ϊ��");
			if (a == "��ͨ")
				label.setText("��������Է������ͨ��Ϊ��");
			if (a == "��ϵ")
				label.setText("������Է�����Ļ�������ϵΪ");
			if (a == "��ϵ")
				label.setText("��������Է�����Ļ�������ϵΪ��");
			JPanel pane = new JPanel();
			JTextField text[][][] = new JTextField[3][f.length][f[0].length];
			pane.setLayout(new GridLayout(f.length, f[0].length * 3));
			for (int k = 0; k < 3; k++) {
				for (int i = 0; i < f.length; i++) {
					for (int j = 0; j < f[0].length; j++) {
						text[k][i][j] = new JTextField(2);
						if (k == 0 && i == (f.length - 1) / 2) {
							if (j == 0)
								text[0][i][j].setText("=");
							else
								text[0][i][j].setText("+");
						} else if (k == 1 && i == (f.length - 1) / 2) {
							if (a == "��ϵ" || a == "��ϵ")
								text[1][i][j].setText("��" + (j + 1));
							else if (a == "��ͨ" && j != f[0].length - 1)
								text[1][i][j].setText("c" + (j + 1));
						} else if (k == 2) {
							text[2][i][j].setText(String.valueOf(f[i][j]));
						}
						text[k][i][j].setEditable(false);
					}
				}
			}
			for (int i = 0; i < f.length; i++) {
				for (int j = 0; j < f[0].length; j++) {
					for (int k = 0; k < 3; k++) {
						pane.add(text[k][i][j]);
					}
				}
			}
			JPanel pane_out = new JPanel();
			pane_out.setLayout(new BorderLayout());
			pane_out.add(label, BorderLayout.NORTH);
			pane_out.add(pane, BorderLayout.SOUTH);
			JOptionPane.showMessageDialog(win, pane_out, "������",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			if (a == "����")
				label.setText("����������Ϊ��");
			if (a == "���")
				label.setText("����������ˣ�");
			if (a == "���")
				label.setText("ͬ�;�����ӣ�");
			if (a == "���")
				label.setText("ͬ�;��������");
			if (a == "����")
				label.setText("���;�����ˣ�");
			if (a == "���")
				label.setText("������������Ϊ��");
			if (a == "�޹�")
				label.setText("���������������޹���Ϊ��");
			JPanel pane = new JPanel();
			JTextField text_out[][] = new JTextField[f.length][f[0].length];
			pane.setLayout(new GridLayout(f.length, f[0].length));
			for (int i = 0; i < f.length; i++) {
				for (int j = 0; j < f[0].length; j++) {
					text_out[i][j] = new JTextField(2);
					text_out[i][j].setText(String.format("%.2f", f[i][j]));//����λ������
					text_out[i][j].setEditable(false);
					pane.add(text_out[i][j]);
				}
			}
			JPanel pane_out = new JPanel();
			pane_out.setLayout(new BorderLayout());
			pane_out.add(label, BorderLayout.NORTH);
			pane_out.add(pane, BorderLayout.SOUTH);
			JOptionPane.showMessageDialog(win, pane_out, "������",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	// ���ı��������ת�浽������
	private double[][] getValueFromTextField(JPanel panel, JTextField[][] text) {
		GridLayout gridLayout = (GridLayout) panel.getLayout();
		int row = gridLayout.getRows();
		int column = gridLayout.getColumns();
		double[][] ret = new double[row][column];
		for (int i = 0; i < row; ++i)
			for (int j = 0; j < column; ++j)
				ret[i][j] = Double.parseDouble(text[i][j].getText());
		return ret;
	}

	// �жϸ�����е�TextField�Ƿ��и�ֵ
	private boolean hasAssigned(JPanel panel, JTextField[][] text) {
		GridLayout gridLayout = (GridLayout) panel.getLayout();
		int row = gridLayout.getRows();
		int column = gridLayout.getColumns();
		for (int i = 0; i < row; ++i)
			for (int j = 0; j < column; ++j)
				if ("".equals(text[i][j].getText()))
					return false;
		return true;
	}

	// �жϸ�����Ƿ�ѡ����������
	private boolean isSelected(JPanel panel) {
		return panel.getComponents()[0] instanceof JTextField;
	}
}