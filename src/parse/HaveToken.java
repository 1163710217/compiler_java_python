package parse;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HaveToken {
	Token_List tk=null;
	public HaveToken(){
		getToken();
	}

	public void getToken() {
		try {
			Process pr=Runtime.getRuntime().exec("D:\\Python34\\python.exe token.py test.t");
			BufferedReader in =new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			tk=new Token_List();
			
			String []wrong=null;
			pr.waitFor();
			while((line=in.readLine())!=null){
				wrong=line.split(":");
				//equals()������ʾ�Ƚ϶������ݣ�==��Ƚ϶���ĵ�ַ
				if(wrong[0].equals("word")){
					System.out.println(line);
					tk=null;
					break;
				}
				if(!wrong[1].equals("\"")){  //ȥ���ַ��������еġ� ��
				tk.appendToken(new StringBuilder(line));
//				System.out.println(line);
				}
			}
			in.close();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
  public Token_List getTokenList(){
	  Token_List tokenlist=null;
	  if(this.tk!=null){
		  tokenlist=this.tk;
	  }
	  return tokenlist;
  }
}


