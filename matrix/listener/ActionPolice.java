package matrix.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import matrix.Matrix;
import matrix.r.R;
import matrix.window.WindowIn;

public class ActionPolice implements ActionListener{
	private JButton button1,button2;
	private JTextField text1[][],text2[][];
	private JTextField text;//����ʵ���ı���
	private WindowIn win;
	private Matrix matrix1;
	private Matrix matrix2;
	private JComboBox<String> combobox;
	
	@SuppressWarnings("unchecked")
	public ActionPolice(){
		R r=R.getInstance();
		matrix1=new Matrix();
		matrix2=new Matrix();
		try{
			win=(WindowIn) r.getObject("window_in");
			
			JButton[] btn=(JButton[]) r.getObject("btn_back_and_clear");
			button1=btn[0];
			button2=btn[2];
			
			text1=(JTextField[][]) r.getObject("txt_group_1");
			text2=(JTextField[][]) r.getObject("txt_group_2");
			text=(JTextField) r.getObject("txt_hint_mult");
			
			matrix1.setWindow(win);
			matrix2.setWindow(win);
			
			combobox=(JComboBox<String>) r.getObject("combobox");
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		int item=combobox.getSelectedIndex();
		if(item>7&&item<13){                                          //��������
			if(!(button1.isVisible()&&button2.isVisible())){
				if(!button1.isVisible()&&!button2.isVisible())JOptionPane.showMessageDialog(null,"��ѡ�������������������","����",JOptionPane.WARNING_MESSAGE);
				else if(!button1.isVisible())JOptionPane.showMessageDialog(null,"��ѡ���һ���������������","����",JOptionPane.WARNING_MESSAGE);
				else JOptionPane.showMessageDialog(null,"��ѡ��ڶ����������������","����",JOptionPane.WARNING_MESSAGE);
			}else{
				matrix1.input(text1);
				matrix2.input(text2);
				if(item==8&&matrix1.turn&&matrix2.turn){
					matrix1.matrixmuti(matrix2);
					matrix1.output("���");
				}else if(item==9&&matrix1.turn&&matrix2.turn){
					matrix1.add(matrix2);
					matrix1.output("���");
				}else if(item==10&&matrix1.turn&&matrix2.turn){
					matrix1.sub(matrix2);
					matrix1.output("���");
				}else if(item==11&&matrix1.turn&&matrix2.turn){
					if(matrix1.isEqual(matrix2))JOptionPane.showMessageDialog(win,"������������ȼۣ�","������",JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(win,"�����������鲻�ȼۣ�","������",JOptionPane.PLAIN_MESSAGE);
				}else if(item==12&&matrix1.turn&&matrix2.turn){
					if(matrix1.canRepresent(matrix2))JOptionPane.showMessageDialog(win,"B(�ұ�)�������ܱ�A(���)���������Ա�ʾ��","������",JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(win,"B(�ұ�)�����鲻�ܱ�A(���)���������Ա�ʾ��","������",JOptionPane.PLAIN_MESSAGE);
				}
			}
		}else if(item==0){JOptionPane.showMessageDialog(null,"��ѡ��������ͣ�","����",JOptionPane.WARNING_MESSAGE);
		}else{                                                                  //һ������
			if(!button1.isVisible()){
				JOptionPane.showMessageDialog(null,"��ѡ��������������","����",JOptionPane.WARNING_MESSAGE);
			}else{
				matrix1.input(text1);
				if(item==1&&matrix1.turn){
					double result=matrix1.getResult();
					JOptionPane.showMessageDialog(win,"����ʽ��ֵΪ��"+result,"������",JOptionPane.PLAIN_MESSAGE);
				}else if(item==2){
					if(text.getText().equals(""))JOptionPane.showMessageDialog(null,"��û��������˵�ʵ����","����",JOptionPane.WARNING_MESSAGE);
					else if(matrix1.turn){
						matrix1.scalarmuti(Double.parseDouble(text.getText()));
						matrix1.output("����");
					}
				}else if(item==3&&matrix1.turn){
					if(matrix1.getResult()==0)JOptionPane.showMessageDialog(win,"������������ʽΪ0���޷���������","������",JOptionPane.WARNING_MESSAGE);
					else{
						matrix1.inverse();
						matrix1.output("����");
					}
				}else if(item==4&&matrix1.turn){
					int rank=matrix1.getRank();
					JOptionPane.showMessageDialog(win,"�������Ϊ��"+rank,"������",JOptionPane.PLAIN_MESSAGE);
				}else if(item==5){
					double given[]=matrix1.given();
					String out=null;
					if(given[0]==given[1]&&given[1]==given[2])out="��1=��2=��3="+given[0];
	            else if(given[1]==given[2])out="��1="+given[0]+"  "+"��2=��3="+given[1];
	            else if(given[0]==given[2])out="��1=��2="+given[0]+"  "+"��3="+given[1];
	            else out="��1="+given[0]+"  "+"��2="+given[1]+"  "+"��3="+given[2];
					if(matrix1.turn)JOptionPane.showMessageDialog(win,"���������ֵΪ��\n"+out,"������",JOptionPane.PLAIN_MESSAGE);
				}else if(item==6){
					matrix1.simplest();
					matrix1.output("���");
				}else if(item==7){
					matrix1.maxIrrelevant();
					matrix1.output("�޹�");
				}else if(item==13){
					if(matrix1.isRelevant())JOptionPane.showMessageDialog(win,"��������������أ�","������",JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(win,"�������������޹أ�","������",JOptionPane.PLAIN_MESSAGE);
				}else if(item==14){
					if(matrix1.b[0].length==matrix1.getRank())JOptionPane.showMessageDialog(win,"������Է�����Ľ�Ψһ����ͨ�⣬�Լ����_����","������",JOptionPane.PLAIN_MESSAGE);
					else {
						matrix1.homogen();
						matrix1.output("��ͨ");
					}
				}else if(item==15){
					if(matrix1.b[0].length==matrix1.getRank())JOptionPane.showMessageDialog(win,"������Է�����Ľ�Ψһ���޻�����ϵ���Լ����_����","������",JOptionPane.PLAIN_MESSAGE);
					else {
						matrix1.homogenBasic();
						matrix1.output("��ϵ");
					}
				}else if(item==16){
					boolean operation=true;
					matrix1.simplest();
					for(int i=0;i<matrix1.f[0].length;i++){
						if(matrix1.f[matrix1.f.length-1][i]==1&&i==matrix1.f[0].length-1){
							operation=false;
							JOptionPane.showMessageDialog(win,"��������Է������޽⣡","������",JOptionPane.PLAIN_MESSAGE);
						}
					}
					if(matrix1.f[0].length==matrix1.getRank()+1){
						operation=false;
						JOptionPane.showMessageDialog(win,"������Է�����Ľ�Ψһ����ͨ�⣬�Լ����_����","������",JOptionPane.PLAIN_MESSAGE);
					}
					if(operation){
						matrix1.nonhomogen();
						matrix1.output("��ͨ");
					}
				}else if(item==17){
					boolean operation=true;
					matrix1.simplest();
					for(int i=0;i<matrix1.f[0].length;i++){
						if(matrix1.f[matrix1.f.length-1][i]==1&&i==matrix1.f[0].length-1){
							operation=false;
							JOptionPane.showMessageDialog(win,"��������Է������޽⣡","������",JOptionPane.PLAIN_MESSAGE);
						}
					}
					if(matrix1.f[0].length==matrix1.getRank()+1){
						operation=false;
						JOptionPane.showMessageDialog(win,"������Է�����Ľ�Ψһ���޻�����ϵ���Լ����_����","������",JOptionPane.PLAIN_MESSAGE);
					}
					if(operation){
						matrix1.nonhomogenBasic();
						matrix1.output("��ϵ");
					}
				}
			}
		}
	}
}