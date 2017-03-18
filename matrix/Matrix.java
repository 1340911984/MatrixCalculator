package matrix;

public class Matrix {
	public double b[][]; // �������������
	public double f[][]; // ����������

	public Matrix() {
		b = null;
		f = null;
	}

	public Matrix(double[][] input) {
		setInput(input);
	}

	public void setInput(double[][] input) {
		b = copyArray(input);
	}
	
	public double[][] getInput(){
		return b;
	}

	public double[][] getOutput() {
		Double zero = new Double(-0.0); // ������ת��Ϊ���㣬ȥ����ĸ���
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				if (zero.equals(f[i][j]))
					f[i][j] *= -1;
				if (Math.ceil(f[i][j]) - f[i][j] <= 10e-5)
					f[i][j] = Math.ceil(f[i][j]);
				if (f[i][j] - Math.floor(f[i][j]) <= 10e-5)
					f[i][j] = Math.floor(f[i][j]);
			}
		}
		return f;
	}

	/*
	 * ������Ϊ�������Σ��ٽ��Խ��ߵ�Ԫ����ˡ��������μ����Խ��ߣ�x��x�У��ϵ�Ԫ�أ�����0���������
	 * Ԫ�ؼ�������һ��Ԫ��ʹ�䲻��0.����Ȼ��0�����������ڶ���Ԫ�أ��Դ����ơ�ֱ���Խ�����Ԫ�ز�Ϊ0����
	 * �ӵ����һ�С�Ȼ�����һ��������Ԫ�ؼ�ȥ����Ԫ�أ�ʹ��x��Ԫ�ؾ�Ϊ�㡣
	 */
	public double getResult() { // ��n������ʽ��ֵ
		int x = 0;
		double item, result = 1;
		double jz[][] = copyArray(b);
		for (int i = 0; i < b.length - 1; i++) {
			x = i + 1;
			while (Math.abs(b[i][i]) <= 1e-5) {
				for (int q = i; q < b[0].length; q++) {
					b[i][q] += b[x][q];
				}
				x += 1;
				if (x == b.length)
					break;
			}
			for (int j = i + 1; j < b.length; j++) {
				if (Math.abs(b[i][i]) <= 1e-5)
					break;
				item = b[j][i] / b[i][i];
				for (int k = i; k < b[0].length; k++)
					b[j][k] -= item * b[i][k];
			}
		}
		for (int i = 0; i < b.length; i++) { // ������ļ�С����ת��Ϊ0
			for (int j = i; j < b[0].length; j++) {
				if (Math.abs(b[i][j]) <= 1e-5)
					b[i][j] = 0;
			}
		}
		for (int i = 0; i < b.length; i++)
			result *= b[i][i];
		b = jz;

		/*
		 * Double zero = new Double(-0.0); // ������ת��Ϊ���㣬ȥ����ĸ��� for (int i = 0; i
		 * < b.length; i++) { for (int j = 0; j < b[0].length; j++) { if
		 * (zero.equals(b[i][j])) b[i][j] *= -1; } }
		 */

		// ��ʱresult=3��ȴ������2.99999999������Ҫת��һ��
		if (Math.ceil(result) - result <= 1e-5)
			result = Math.ceil(result);
		if (result - Math.floor(result) <= 1e-5)
			result = Math.floor(result);
		return result;
	}

	public void add(Matrix r) { // �������������
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				f[i][j] = b[i][j] + r.b[i][j];
			}
		}
	}

	public void sub(Matrix r) { // �������������
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				f[i][j] = b[i][j] - r.b[i][j];
			}
		}
	}

	public void scalarmuti(double n) { // ����������
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++)
				f[i][j] = b[i][j] * n;
		}
	}

	public void trans() { // ������ת��
		f = new double[b[0].length][b.length];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++)
				f[j][i] = b[i][j];
		}
	}

	/*
	 * ˼·���������Ԫ�صĴ�������ʽ���ٽ���������ʽ�ľ���ת�ü��ɡ��ص���������Ԫ�ش�������ʽ���
	 * �����顣��ȷ��ĳһԪ�أ����㣨-1��^(i+j)ȷ����������ʽ���ţ���ȡ�����Ԫ�ز�ͬ���Ҳ�ͬ�е�����Ԫ
	 * �أ�������һ��һλ�����У���ȡ��ɺ���Щ��һλ�����е�Ԫ�طŽ�һ����ά�����У��γɾ��󣩣�����
	 * ���������ʽ��ֵ�����Դ�������ʽ���ţ�����һ����������ʽ����˱���һ��ѭ����ѭ��i��j�дβ������
	 * ��������ת����i��j�У�����ѭ�����õ���Ԫ�ش�������ʽ��ɵľ����ٽ���ת�ü���
	 */
	public void adjoint() { // ��n�׾���İ�����
		double d[][] = new double[b.length][b[0].length];
		f = new double[b.length][b[0].length];
		int n = (b.length - 1) * (b.length - 1);
		double e[] = new double[n];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				int w = 0;
				double k = Math.pow(-1, j + i);
				for (int p = 0; p < b.length; p++) {
					if (p == i)
						continue;
					for (int q = 0; q < b[0].length; q++) {
						if (q == j)
							continue;
						e[w++] = b[p][q];
					}
				}
				w = 0;
				Matrix r = new Matrix();
				r.b = new double[b.length - 1][b.length - 1];
				for (int x = 0; x < r.b.length; x++) {
					for (int y = 0; y < r.b[0].length; y++)
						r.b[x][y] = e[w++];
				}
				d[i][j] = k * r.getResult();
			}
		}
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++)
				f[i][j] = d[j][i];
		}
	}
	/*
	 * ��������ķ����������İ��棬������������ʽ��ֵ�ķ���������������ʽ��ֵ������ֵk��Ϊ0ʱ������
	 * �������ٵ����������˵ķ�����k���԰����󣬵ó�����󡣣��м��½���һ��Matrix���������������� ת��Ϊ����������������������㣩
	 */
	public void inverse() { // ��n�׷������
		adjoint();
		double t = 1 / getResult();
		Matrix r = new Matrix();
		r.b = f;
		r.scalarmuti(t);
		f = r.f;
	}

	/*
	 * �½�f�ĳ���Ϊ b.length �� r.b[0].length������һ��������������Եڶ����������������������ѭ��
	 * һ�����Ƶ�һ��������������ڶ���ѭ�����Ƶڶ��������������������ѭ�����μ�����һ��������������
	 * һ�У��У��ĸ���Ԫ�أ�������˲����������ڶ���ѭ��ѭ��һ�Σ���������ѭ������һ�Σ��õ��¾���� һ��Ԫ��
	 */
	public void matrixmuti(Matrix r) { // �������������
		f = new double[b.length][r.b[0].length];
		for (int i = 0; i < b.length; i++) {
			for (int k = 0; k < r.b[0].length; k++) {
				for (int j = 0; j < b[0].length; j++) {
					f[i][k] += b[i][j] * r.b[j][k];
				}
			}
		}
	}

	public void simplest() { // �������������
		f = new double[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) { // ��b����copy��f����
			for (int j = 0; j < b[0].length; j++)
				f[i][j] = b[i][j]; // ��f����������������
		}
		for (int i = 0; i < f.length; i++) {
			int column = i;
			if (column == f[0].length)
				break;
			int x = i;
			if (Math.abs(f[i][column]) <= 10e-7)
				f[i][column] = 0;
			while (f[i][column] == 0) { // ������Ϊ0����while�ɽ���
				if (f[x][column] != 0) { // ���������У�ʹ�䲻Ϊ0
					for (int q = column; q < f[0].length; q++)
						f[i][q] += f[x][q];
				}
				x++;
				if (x == f.length && f[i][column] == 0) {
					column++;
					x = i;
				}
				if (column == f[0].length) {
					column--;
					break;
				}
				if (Math.abs(f[i][column]) <= 10e-7)
					f[i][column] = 0;
			}
			if (Math.abs(f[i][column]) <= 10e-7)
				f[i][column] = 0;
			for (int j = 0; j < f.length; j++) { // ����0��ͬ�е�����Ϊ0
				if (f[i][column] == 0)
					break;
				if (j == i)
					continue;
				double item = f[j][column] / f[i][column];
				for (int k = column; k < f[0].length; k++) {
					f[j][k] -= item * f[i][k];
				}
			}
		}
		for (int i = 0; i < f.length; i++) {
			for (int j = i; j < f[0].length; j++) {
				if (f[i][j] == 0)
					continue;
				for (int k = f[0].length - 1; k >= j; k--)
					f[i][k] /= f[i][j];
				if (f[i][j] != 0)
					break;
			}
		}
	}

	/*
	 * ������ķ����������Σ����������f�����У�����������ѭ����һ����������ε��У�һ�������У�
	 * һ��һ�д����Ҽ�����������0����rank��1��������ѭ������һ��û�м�������0������rank�����1
	 */
	public int getRank() { // ��������
		int rank = 0;
		simplest();
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				if (f[i][j] != 0) {
					rank += 1;
					break;
				}
			}
		}
		return rank;
	}
	public double[] given() { // ֻ�������׾��󣬶���ֻ�������� //����������ֵ
		double a[] = {21, 21, 21};
		int n = 0;
		f = new double[b.length][b[0].length];
		Matrix r = new Matrix();
		for (int i = -20; i <= 20; i++) {
			for (int k = 0; k < b.length; k++) {
				for (int j = 0; j < b[0].length; j++)
					f[k][j] = b[k][j];
			}
			for (int j = 0; j < f.length; j++)
				f[j][j] -= i;
			r.b = f;
			if (r.getResult() == 0)
				a[n++] = i;
		}
		if (a[1] == 21) {
			a[1] = a[0];
			a[2] = a[0];
		} else if (a[2] == 21) {
			if (a[0] * a[1] * a[1] == getResult())
				a[2] = a[1];
			else
				a[2] = a[0];
		}
		return a;
	}
	public void homogen() { // ��������Է������ͨ��
		int rank = getRank(); // ���ѳ����ӣ������Һö���ϸ��������
		if (f[0].length - rank != 0) { // �㷨Ҳ��֪���Բ��ԣ�����Ҳ������򵥵ģ�����
			int c[] = new int[f[0].length];
			int r[] = new int[f[0].length];
			for (int i = 0; i < c.length; i++)
				c[i] = 1;
			for (int i = 0; i < r.length; i++)
				r[i] = 1;
			double t[][] = new double[f[0].length][f[0].length];
			for (int i = 0; i < f.length; i++) { // ȷ����Щ�в�Ҫ
				for (int j = 0; j < f[0].length; j++) {
					if (f[i][j] != 0) {
						c[j] = 0;
						r[i] = 0;
						break;
					}
				}
			}
			for (int i = 0; i < f[0].length; i++) { // ��f����copy��t���󣬲�����ȡ����ȡ����
				for (int j = 0; j < f[0].length; j++) {
					if (i < f.length)
						t[i][j] = -f[i][j];
				}
			}
			int index = 0;
			int location = 0;
			int row[] = new int[rank];
			int column[] = new int[rank];
			for (int i = 0; i < r.length; i++) { // ��ÿһ�е�һ��1��������������¼����
				if (r[i] == 1)
					continue; // ���ں�����н��������ų�
				for (int j = location; j < c.length; j++) {
					if (c[j] == 0) {
						row[index] = i;
						column[index] = j;
						index++;
						location = j + 1;
						break;
					}
				}
			}
			index = rank - 1;
			for (int j = f[0].length; j >= 0; j--) { // �н���
				if (j == column[index]) {
					if (column[index] != row[index]) {
						for (int q = 0; q < f[0].length; q++) {
							t[column[index]][q] = t[row[index]][q];
							t[row[index]][q] = 0;
						}
						index--;
					}
				}
			}
			for (int i = 0; i < f[0].length; i++) { // ����Ӧ��Ԫ�ظĳ�1
				for (int j = 0; j < f[0].length; j++) {
					if (i == j)
						t[i][j] = 1;
				}
			}
			f = new double[t[0].length - rank][t[0].length];
			int count;
			for (int i = 0; i < f[0].length; i++) { // ��ȡ��Ҫ���д����f����
				count = 0;
				for (int j = 0; j < f[0].length; j++) {
					if (c[j] == 0)
						continue;
					f[count][i] = t[i][j];
					count++;
				}
			}
			Matrix trans = new Matrix();
			trans.b = f;
			trans.trans();
			f = trans.f;
		} else
			; // ��Ψһ�������������
	}
	public void nonhomogen() { // ���������Է������ͨ��
		int rank = getRank();
		int c[] = new int[f[0].length];
		int r[] = new int[f[0].length];
		for (int i = 0; i < c.length; i++)
			c[i] = 1;
		for (int i = 0; i < r.length; i++)
			r[i] = 1;
		for (int i = 0; i < f.length; i++) { // ȷ����Щ�в�Ҫ
			for (int j = 0; j < f[0].length; j++) {
				if (f[i][j] != 0) {
					c[j] = 0;
					r[i] = 0;
					break;
				}
			}
		}
		if ((f[0].length - rank - 1) != 0) {
			double t[][] = new double[f[0].length - 1][f[0].length];
			for (int i = 0; i < t.length; i++) { // ��f����copy��t���󣬲�����ȡ����ȡ����
				for (int j = 0; j < t[0].length; j++) {
					if (i < f.length) {
						if (j != f[0].length - 1)
							t[i][j] = -f[i][j];
						if (j == f[0].length - 1)
							t[i][j] = f[i][j];
					}
				}
			}
			int index = 0;
			int location = 0;
			int row[] = new int[rank];
			int column[] = new int[rank];
			for (int i = 0; i < r.length; i++) { // ��ÿһ�е�һ��1��������������¼����
				if (r[i] == 1)
					continue; // ���ں�����н��������ų�
				for (int j = location; j < c.length; j++) {
					if (c[j] == 0) {
						row[index] = i;
						column[index] = j;
						index++;
						location = j + 1;
						break;
					}
				}
			}
			index = rank - 1;
			for (int j = f[0].length; j >= 0; j--) { // �н���
				if (j == column[index]) {
					if (column[index] != row[index]) {
						for (int q = 0; q < f[0].length; q++) {
							t[column[index]][q] = t[row[index]][q];
							t[row[index]][q] = 0;
						}
						index--;
					}
				}
			}
			for (int i = 0; i < t.length; i++) { // ����Ӧ��Ԫ�ظĳ�1
				for (int j = 0; j < t[0].length; j++) {
					if (i == j)
						t[i][j] = 1;
				}
			}
			f = new double[t[0].length - rank][t.length];
			int count;
			for (int i = 0; i < t.length; i++) { // ��ȡ��Ҫ���д����f����
				count = 0;
				for (int j = 0; j < t[0].length; j++) {
					if (c[j] == 0)
						continue;
					f[count][i] = t[i][j];
					count++;
				}
			}
			Matrix trans = new Matrix();
			trans.b = f;
			trans.trans();
			f = trans.f;
		} else
			; // Ψһ�������������
	}
	public void homogenBasic() { // ��������Է�����Ļ�����ϵ
		homogen();
	}
	/* ����ͨ���࣬������ϵû�����һ�������ģ�ȥ��������һ�оͺ��� */
	public void nonhomogenBasic() { // ���������Է�����Ļ�����ϵ
		nonhomogen();
		double t[][] = new double[f.length][f[0].length - 1];
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[0].length; j++)
				t[i][j] = f[i][j];
		}
		f = t;
	}
	public boolean isOrthorhombic() { // δ���� //�жϷ����Ƿ���������
		double item = 0; // ��Ҫ֧����������͸��ţ������
		for (int j = 0; j < f[0].length; j++) {
			for (int i = 0; i < f.length; i++) {
				item += f[i][j] * f[i][j];
			}
			if (item != 1)
				return false;
		}
		item = 0;
		for (int j = 0; j < f[0].length; j++) {
			for (int j2 = j + 1; j2 < f[0].length; j2++) {
				for (int i = 0; i < f.length; i++)
					item += f[i][j] * f[i][j2];
				if (item != 0)
					return false;
			}
		}
		return true;
	}
	/*
	 * B�ܱ�A���Ա�ʾ�ĳ�Ҫ������R(A)=R(A,B)����������A���ȣ����½�f����ΪA��B����һ��ľ�������
	 * f������ȣ��ж��Ƿ���ȼ��ɡ����м�ע�����ת���ǲ���ֱ���ø�ֵ�ţ�����ѭ�����һ��һ����ֵ
	 */
	public boolean canRepresent(Matrix r) { // �ж�������B�ܷ���������A���Ա�ʾ
		int rank_A = getRank(); // ��ı�f��ֵ�����Բ��ܷ��ں���
		f = new double[b.length][b[0].length + r.b[0].length];
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				if (j < b[0].length)
					f[i][j] = b[i][j];
				else
					f[i][j] = r.b[i][j - b[0].length];
			}
		}
		Matrix c = new Matrix();
		double jz[][] = new double[f.length][f[0].length];
		for (int i = 0; i < jz.length; i++) {
			for (int j = 0; j < jz[0].length; j++)
				jz[i][j] = f[i][j];
		}
		c.b = jz;
		int rank_B = c.getRank();
		if (rank_A != rank_B)
			return false;
		else
			return true;
	}
	public boolean isEqual(Matrix r) { // �ж�������A��������B�Ƿ�ȼ�
		if (canRepresent(r) && r.canRepresent(this))
			return true;
		else
			return false;
	}
	/* �Ƿ�������صĳ�Ҫ���������������С������ */
	public boolean isRelevant() { // �ж��������Ƿ��������
		int rank = getRank();
		if (rank < b[0].length)
			return true;
		else
			return false;
	}
	public void maxIrrelevant() { // �������������޹���
		int rank = getRank();
		int c[] = new int[f[0].length];
		for (int i = 0; i < c.length; i++)
			c[i] = 0;
		for (int i = 0; i < f.length; i++) { // ȷ����Щ��Ҫ
			for (int j = 0; j < f[0].length; j++) {
				if (f[i][j] != 0) {
					c[j] = 1;
					break;
				}
			}
		}
		f = new double[b.length][rank];
		int count = 0;
		for (int j = 0; j < b[0].length; j++) { // ��ȡ��Ҫ���д����f����
			if (c[j] == 0)
				continue;
			for (int i = 0; i < b.length; i++) {
				f[i][count] = b[i][j];
			}
			count++;
		}
	}

	private double[][] copyArray(double[][] arr) {
		double[][] ret;
		ret = new double[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; ++i)
			for (int j = 0; j < arr[0].length; ++j)
				ret[i][j] = arr[i][j];
		return ret;
	}
}