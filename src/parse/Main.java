package parse;

public class Main {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		Parse_Token pt =new Parse_Token();
		if(pt.exp_fun!=null){
			System.out.println("�����﷨���������");
		}
		Parse_Semantics ps=new Parse_Semantics(pt.exp_fun);
	}

}
