package diamond.ast;

import java.util.List;

//˫Ŀ������ڵ㣬��Ҷ�ӽڵ�
public class BinaryExpr extends ASTList{
	public BinaryExpr(List<ASTree> c) {super(c);}//c��ʼ������ʹ�䴢����ʽ
	public ASTree left() {return child(0);}//���ص�ǰ�ڵ���������ֵ
	public String operator() {
		return ((ASTLeaf)child(1)).token().getText();//��ȡ��������
	}
	public ASTree right() {return child(2);}//���ص�ǰ�ڵ���������ֵ
}
