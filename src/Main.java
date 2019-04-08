import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Main {
	public static void main(String [] args){
		Scanner in = new Scanner(System.in);
		String temp=in.next();
		//判断参数合法性。整数
		if(isNumeric(temp)){
			int num = Integer.valueOf(temp);
			char[] operator=new char[]{'+','-','*','/'};
			Random random=new Random();
			ArrayList<String> expression=new ArrayList<String>();
			for(int i=0;i<num;i++){
				int n=2; //2个运算符
				int[] number=new int[n+1]; 
				String ex=new String();
				for(int j=0;j<=n;j++){
					number[j]=random.nextInt(100)+1; //3个数字
				}
				
				for(int j=0;j<n;j++){
					int s=random.nextInt(4);//随机选择某个运算符
					ex+=String.valueOf(number[j])+String.valueOf(operator[s]);///5+4+6+9
					if(s==3){number[j+1]=decide(number[j],number[j+1]);}
				}
				ex+=String.valueOf(number[n]);
				
				ScriptEngine js = new ScriptEngineManager().getEngineByName("JavaScript");// 判断结果是否为0-100整数
				{
					double ans;
					try {
						ans = Double.valueOf(js.eval(ex).toString());
						if (ans > 100 || ans < 0 || ans % 1 != 0) {
							i--;
							continue;
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ScriptException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				expression.add(ex);
			}
			WriteToFile("ArithmeticExpression.txt",calculate(expression));
		}
		else{
			System.out.println("非法输入！");
		}
	}
	
	/**
	 * 随即取x,y为1-100之间，x可以整除y的y值
	 * @param x
	 * @param y
	 * @return
	 */
	private static int decide(int x,int y){
		Random random=new Random();
		if(x%y!=0){
			y=random.nextInt(100)+1;
			return decide(x,y);
		}
		else{
			return y; 
		}
	}
	
	/**
	 * 计算每个等式的结果，并返回运算式
	 * @param arrayList 
	 * @return arrayList
	 */
	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");  
	private static ArrayList<String> calculate(ArrayList<String> arrayList){
		ArrayList<String> ArithExpress=new ArrayList<String>();
		for(String ax:arrayList){
			try {
				ax=ax+"="+jse.eval(ax);
				System.out.println(ax);
				ArithExpress.add(ax);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ArithExpress;
	}
	
	/**
	 * 判断输入是否为整数
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
        int chr=str.charAt(i);
        if(chr<48 || chr>57)
            return false;
        }
        if(Integer.valueOf(str)==0)
        	return false;
       return true;
    }  
	
	/**
	 * 在路径path下保存文件
	 * @param path
	 * @param content
	 * @return
	 */
	private static void WriteToFile(String path,ArrayList<String> content)
	{
		try {
	        BufferedWriter bw = new BufferedWriter(new FileWriter(path));  
	        for(String con:content){
	            bw.write(con);  
	            bw.newLine();
	        }
	        bw.close();
	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	}
}
