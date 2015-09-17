package parse;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

//enum type_id {NUM,STRING,BOOL,NORMAL_NUM,NORMAL_STRING};
enum type_com{BIG,LITTLE,EQUAL,BIG_EQUAL,LITTLE_EQUAL};
enum type_cal{BIG,LITTLE,EQUAL,BIG_EQUAL,LITTLE_EQUAL,
	GOT,ADD,SUB,MUL,DIV,ID,NUM,STRING,BOOL,NORMAL_NUM,
	NORMAL_STRING,TRANS_NUM,TRANS_STRING,TRANS_BOOL,FUNC,WHI,IFE};
enum type_ret{NUM,STRING,BOOL};

class Exp{
	   type_cal type;
	   String line;
	   String row;
}

//��ֵ���߱���
class Exp_mark extends Exp{
   String  value;
   List<Exp> list_value=null;
   public void set_listvalue(Exp s){
	   if(list_value==null){
	   list_value=new ArrayList<Exp>();
	   }
	   list_value.add(s);
   }
   Exp got=null;  //�жϱ��������Ƿ���һ���Ⱥ�
}
class Exp_type extends Exp{
	Exp Id=null;   //����ı���
}
class Exp_calcul extends Exp{
       Exp left;
	   Exp right;
}
//����
class Exp_cond extends Exp{
	Exp left;
	Exp_comp exp_comp;
	Exp right;
}
//�ȽϷ���
class Exp_comp extends Exp{

}
//��������
class Exp_func extends Exp{
	Exp_funcname name;
	
	
//	String return_type;
//	Exp_defvar exp;  //�����������
//	List<Exp> list_var=null;
//	exp���ʽ�ĸ���Ҳ����
	List<Exp> list_exp=null;
//	whileѭ���ĸ���Ҳ����
//	List<Exp> list_whi=null;
//	if�������ĸ���Ҳ����
//	List<Exp> list_ife=null;

	public void setExp(Exp exp){
		if(list_exp==null){
		list_exp=new ArrayList<Exp>();
		}
		list_exp.add(exp);
	}

}
class Exp_funcname extends Exp{
	String name=null;
}
////��������
//class Exp_defvar extends Exp{
//
//	String id_name;
//
//}

class Exp_whi extends Exp{
//	Object exp;
	
	Exp_cond condition;
//	Exp_ife if_el;
	Exp next=null;
//	List<Exp> list_whi=null;
//	List<Exp> list_if=null;
//	List<Exp> list_exp=null;
//	public void setList_whi(Exp whi){
//		if(list_whi==null){
//		list_whi=new ArrayList<Exp>();
//		}
//		list_whi.add(whi);
//	}
//	public void setList_if(Exp ife){
//		if(list_if==null){
//		list_if=new ArrayList<Exp>();
//		}
//		list_if.add(ife);
//	}
//	public void setList_exp(Exp exp){
//		if(list_exp==null){
//		list_exp=new ArrayList<Exp>();
//		}
//		list_exp.add(exp);
//	}
}

class Exp_ife extends Exp{
	Exp_cond condition;
	
	Exp left=null;  //��������ʱ
	Exp right=null;  //����������ʱ
//	List<Exp> list_whi=null;
//	List<Exp> list_if=null;
//	List<Exp> list_exp=null;
//	public void setList_whi(Exp whi){
//		if(list_whi==null){
//		list_whi=new ArrayList<Exp>();
//		}
//		list_whi.add(whi);
//	}
//	public void setList_if(Exp ife){
//		if(list_if==null){
//		list_if=new ArrayList<Exp>();
//		}
//		list_if.add(ife);
//	}
//	public void setList_exp(Exp exp){
//		if(list_exp==null){
//		list_exp=new ArrayList<Exp>();
//		}
//		list_exp.add(exp);
//	} 
//	
////	else�����µĿ��ܵ����
//	Exp_cond condition1;
//	List<Exp> list_whi1=null;
//	List<Exp> list_if1=null;
//	List<Exp> list_exp1=null;
//	public void setList_whi1(Exp whi){
//		if(list_whi1==null){
//		list_whi1=new ArrayList<Exp>();
//		}
//		list_whi1.add(whi);
//	}
//	public void setList_if1(Exp ife){
//		if(list_if1==null){
//		list_if1=new ArrayList<Exp>();
//		}
//		list_if1.add(ife);
//	}
//	public void setList_exp1(Exp exp){
//		if(list_exp1==null){
//		list_exp1=new ArrayList<Exp>();
//		}
//		list_exp1.add(exp);
//	} 
}
class Exp_B extends Exp{
//	Exp list_whi=null;
//	Exp exp_if=null;
	List<Exp> list_exp=null;
//	public void setList_whi(Exp whi){
//		list_whi=whi;
//	}
//	public void setList_if(Exp ife){
//		exp_if=ife;
//	}
	public void setList_exp(Exp exp){
		if(list_exp==null){
		list_exp=new ArrayList<Exp>();
		}
		list_exp.add(exp);
	} 
}