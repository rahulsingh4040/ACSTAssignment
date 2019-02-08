package acstassignment;

import java.util.Random;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;

class player{
	public String name;
	public int score;
	public player() {
		name = "";
		score = 0;
	}
	public void get_details(String name) {
		this.name = name;
	}
}

abstract class Question extends player{
	
	String q_txt;
	String q_ans;
	String q_type;
	int max_ques;
	public Question() {
		super();
		q_txt = "";
		q_ans = "";
		q_type = "";
		max_ques = 0;
	}
	
	public String get_qtype(BufferedReader fin) throws Exception{
		return fin.readLine();
	}
	
	public void print_ques() {
		System.out.println(q_txt);
	}
	
	public abstract void get_ques(BufferedReader fin) throws Exception;
	
	public boolean check_ans(String ans, String q_ans) {
		if(ans.equals(q_ans))
			return true;
		else
			return false;
	}
}


class shortans extends Question{
	
	static int marks = 20;
	
	shortans(){
		super();
	}
	
	public void get_ques(BufferedReader fin) throws Exception{
			q_txt = fin.readLine();
			q_ans = fin.readLine();
	}
	
	public void pt_inc(String ans, String q_ans) {
		if(check_ans(ans,q_ans)==true) {
			System.out.println("Correct answer 20 points awarded!!!");
			score+=marks;
		}
		else {
			System.out.println("Incorrect answer 20 points deducted!!!");
			score-=marks;
		}
	}
}

class  Truefalse extends Question{
	
	static int marks = 10;
	
	Truefalse(){
		super();
	}
	
	public void get_ques(BufferedReader fin) throws Exception{
		q_txt = fin.readLine();
		q_ans = fin.readLine();	
	}
	
	public void pt_inc(String ans, String q_ans) {
		if(check_ans(ans,q_ans)==true) {
			System.out.println("Correct answer 10 points awarded!!!");
			score+=marks;
		}
		else {
			System.out.println("Incorrect answer 10 points deducted!!!");
			score-=marks;
		}
	}
}

class Mcqs extends Question{
	
	static int marks = 10;
	
	Mcqs(){
		super();
	}
	
	public void get_ques(BufferedReader fin) throws Exception{
		q_txt = fin.readLine();
		q_txt = q_txt + "\n" + fin.readLine();
		q_ans = fin.readLine();
	}
    
	public void pt_inc(String ans, String q_ans) {
    	if(check_ans(ans,q_ans)==true) {
    		System.out.println("Correct answer 10 points awarded!!!");
			score+=marks;
		}
		else {
			System.out.println("Incorrect answer 10 points deducted!!!");
			score-=marks;
		}
	}
}

public class Quiz {
	public static void main(String args[]) throws Exception{
		
		BufferedReader fin = new BufferedReader(new FileReader("C:\\Users\\Rahul_Singh\\Desktop\\acst assignment\\acstassignment\\src\\acstassignment\\shortanswer.txt"));
		Scanner inp = new Scanner(System.in);
		Random rand = new Random();
		
		player p = new player();
		
		System.out.print("Enter name : ");
		
		String name = inp.nextLine();
		p.get_details(name);
		
		System.out.println("Hello "+p.name+"!!!");
		
		shortans[] sa = new shortans[100];
		Mcqs[] mc = new Mcqs[100];
		Truefalse[] tf = new Truefalse[100];
		
		int cnt_mc=0, cnt_sa=0, cnt_tf=0;
		int cnt_file = Integer.valueOf(fin.readLine());
		
		for(int i=0; i<cnt_file; i++) {
			String qtp = fin.readLine();
			if("SA".equals(qtp)) {
				sa[cnt_sa] = new shortans();
				sa[cnt_sa].get_ques(fin);
				cnt_sa++;
			}
			
			else if("TF".equals(qtp)) {
				tf[cnt_tf] = new Truefalse();
				tf[cnt_tf].get_ques(fin);
				cnt_tf++;
			}
			
			else if("MCQ".equals(qtp)) {
				mc[cnt_mc] = new Mcqs();
				mc[cnt_mc].get_ques(fin);
				cnt_mc++;
			}
		}
		
		System.out.print("Enter no of questions you want to attempt :");
		int cnt = inp.nextInt();
		if(cnt<=cnt_file) {
			int t_no, q_no;
			for(int i=0; i<cnt; i++) {
				
				t_no = rand.nextInt(3) + 1;
				
				if(t_no==1) {
					q_no = rand.nextInt(cnt_sa);
					sa[q_no].print_ques();
					System.out.print("Enter answer(single word - 20 points) : ");
					String answer = inp.nextLine();
					sa[q_no].pt_inc(answer, sa[q_no].q_ans);
				}
				
				else if(t_no==2) {
					q_no = rand.nextInt(cnt_mc);
					mc[q_no].print_ques();
					System.out.print("Enter answer(a,b,c,d) - 10 points) : ");
					String answer = inp.nextLine();
					mc[q_no].pt_inc(answer, mc[q_no].q_ans);
				}
				
				else if(t_no==3) {
					q_no = rand.nextInt(cnt_tf);
					tf[q_no].print_ques();
					System.out.print("Enter answer(True/False - 10 points) : ");
					String answer = inp.nextLine();
					tf[q_no].pt_inc(answer, tf[q_no].q_ans);
				}
			}
			
			System.out.println(p.name + " gets "+ p.score +" points.");
		}
		else {
			System.out.println("Number of questions exceeded questions in file.... ");
		}
		
		inp.close();
		fin.close();
	}
}