package acstassignment;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

class Player{
	public String name;
	public static int score;
	public Player() {
		name = "";
		score = 0;
	}
	public void get_details(String name) {
		this.name = name;
	}
}


abstract class Question{
	
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
	
	public int check_ans(String ans, String q_ans) {
		if(ans.equalsIgnoreCase(q_ans))
			return 1;
		else if("Skip".equalsIgnoreCase(ans)) {
			return 2;
		}
		else
			return 3;
	}
}


class QuestionSA extends Question{
	
	static int marks = 20;
	
	QuestionSA(){
		super();
	}
	
	public void get_ques(BufferedReader fin) throws Exception{
			q_txt = fin.readLine();
			q_ans = fin.readLine();
	}
	
	public void pt_inc(String ans, String q_ans) {
		if(check_ans(ans,q_ans)==1) {
			System.out.println("Correct answer 20 points awarded!!!");
			Player.score+=marks;
		}
		else if(check_ans(ans,q_ans)==3){
			System.out.println("Incorrect answer 20 points deducted!!!");
			System.out.println("Correct answer : "+ q_ans);
			Player.score-=marks;
		}
		else {
			System.out.println("Question skipped...");
		}
	}
}

class  QuestionTF extends Question{
	
	static int marks = 10;
	
	QuestionTF(){
		super();
	}
	
	public void get_ques(BufferedReader fin) throws Exception{
		q_txt = fin.readLine();
		q_ans = fin.readLine();	
	}
	
	public void pt_inc(String ans, String q_ans) {
		if(check_ans(ans,q_ans)==1) {
			System.out.println("Correct answer 10 points awarded!!!");
			Player.score+=marks;
		}
		else if(check_ans(ans,q_ans)==3){
			System.out.println("Incorrect answer 10 points deducted!!!");
			System.out.println("Correct answer : "+ q_ans);
			Player.score-=marks;
		}
		else {
			System.out.println("Question skipped...");
		}
	}
}

class QuestionMC extends Question{
	
	static int marks = 10;
	
	QuestionMC(){
		super();
	}
	
	public void get_ques(BufferedReader fin) throws Exception{
		q_txt = fin.readLine();
		q_txt = q_txt + "\n" + fin.readLine();
		q_ans = fin.readLine();
	}
    
	public void pt_inc(String ans, String q_ans) {
    	if(check_ans(ans,q_ans)==1) {
    		System.out.println("Correct answer 10 points awarded!!!");
			Player.score+=marks;
		}
		else if(check_ans(ans,q_ans)==3){
			System.out.println("Incorrect answer 10 points deducted!!!");
			System.out.println("Correct answer : "+ q_ans);
			Player.score-=marks;
		}
		else {
			System.out.println("Question skipped...");
		}
	}
}

public class QuizBowl {
	public static void main(String args[]) throws Exception{
		
		BufferedReader fin = new BufferedReader(new FileReader("C:\\Users\\Rahul_Singh\\Desktop\\acst assignment\\acstassignment\\src\\acstassignment\\questions.txt"));
		Scanner inp = new Scanner(System.in);
		Random rand = new Random();
		
		Player p = new Player();
		
		System.out.print("Enter name : ");
		
		String name = inp.nextLine();
		p.get_details(name);
		System.out.println("Hello "+ p.name + "!!!");
		
		QuestionSA[] sa = new QuestionSA[100];
		QuestionMC[] mc = new QuestionMC[100];
		QuestionTF[] tf = new QuestionTF[100];
		
		int cnt_mc=0, cnt_sa=0, cnt_tf=0;
		
		int cnt_file = Integer.valueOf(fin.readLine());

		for(int i=0; i<cnt_file; i++) {
			String qtp = fin.readLine();
			if("SA".equals(qtp)) {
				sa[cnt_sa] = new QuestionSA();
				sa[cnt_sa].get_ques(fin);
				cnt_sa++;
			}
			
			else if("TF".equals(qtp)) {
				tf[cnt_tf] = new QuestionTF();
				tf[cnt_tf].get_ques(fin);
				cnt_tf++;
			}
			
			else if("MCQ".equals(qtp)) {
				mc[cnt_mc] = new QuestionMC();
				mc[cnt_mc].get_ques(fin);
				cnt_mc++;
			}
		}
		
		System.out.print("Enter no of questions you want to attempt out of "+cnt_file+" (in numbers):");
		int cnt = inp.nextInt();
		
		List<Integer> arr_sa = new ArrayList<>();
		for(int m=0;m<cnt_sa;m++) {
			arr_sa.add(m);
		}
		List<Integer> arr_tf = new ArrayList<>();
		for(int m=0;m<cnt_tf;m++) {
			arr_tf.add(m);
		}
		List<Integer> arr_mc = new ArrayList<>();
		for(int m=0;m<cnt_mc;m++) {
			arr_mc.add(m);
		}
		Collections.shuffle(arr_sa);
		Collections.shuffle(arr_tf);
		Collections.shuffle(arr_mc);
		
		if(cnt>cnt_file) {
			while(cnt>cnt_file) {
				System.out.print("Number of questions exceeded questions in file.... ");
				System.out.print("Enter again : ");
				cnt = inp.nextInt();
			}
		}
		
	
			int t_no, q_no,r_sa=0,r_tf=0,r_mc=0;;
			
			for(int i=0; i<cnt; i++) {
				
				System.out.println();
				t_no = rand.nextInt(3) + 1;
				if(t_no==1) {
					if(r_sa>=cnt_sa) {
						i--;
						continue;
					}
					q_no = arr_sa.get(r_sa);
					r_sa++;
					System.out.print("Q"+(i+1)+".)");
					sa[q_no].print_ques();
					System.out.print("Enter answer(single word/Skip - 20 points) : ");
					String answer = inp.next();
					sa[q_no].pt_inc(answer, sa[q_no].q_ans);
				}
				
				else if(t_no==2) {
					if(r_mc>=cnt_mc) {
						i--;
						continue;
					}
					q_no = arr_mc.get(r_mc);
					r_mc++;
					System.out.print("Q"+(i+1)+".)");
					mc[q_no].print_ques();
					System.out.print("Enter answer(a,b,c,d)/Skip - 10 points) : ");
					String answer = inp.next();
					mc[q_no].pt_inc(answer, mc[q_no].q_ans);
				}
				
				else if(t_no==3) {
					if(r_tf>=cnt_tf) {
						i--;
						continue;
					}
					q_no = arr_tf.get(r_tf);
					r_tf++;
					System.out.print("Q"+(i+1)+".)");
					tf[q_no].print_ques();
					System.out.print("Enter answer(True/False/Skip - 10 points) : ");
					String answer = inp.next();
					tf[q_no].pt_inc(answer, tf[q_no].q_ans);
				}
			}
			
			System.out.println("\n"+p.name + " gets "+ Player.score +" points.");


		
		inp.close();
		fin.close();
	}
}