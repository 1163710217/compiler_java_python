//package token_analyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HaveToken {

	public static void main(String[] args) {
		try {
			Process pr=Runtime.getRuntime().exec("D:\\Python34\\python.exe token.py test.t");
			BufferedReader in =new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			String []wrong=null;
			pr.waitFor();
			while((line=in.readLine())!=null){
				wrong=line.split(":");
				//equals()������ʾ�Ƚ϶������ݣ�==��Ƚ϶���ĵ�ַ
				if(wrong[0].equals("word")){
					System.out.println(line);
				}
				System.out.println(line);
			}
			in.close();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		

	}

}
