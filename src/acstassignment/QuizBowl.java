package acstassignment;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;

/**
 * ACST Assignment Quiz Bowl 
 * @author Rahul_Singh
 *
 **/


/*  Class to store player details */ 
class Player{					
	public String f_name;
	public String l_name;
	public static int score;
	public Player() {
		f_name = "";
		l_name = "";
		score = 0;
	}
	public void get_details(String f_name, String l_name) {
		this.f_name = f_name;
		this.l_name = l_name;
	}
}



/* Base Class for questions */
abstract class Question{
	
	protected String q_txt;
	protected String q_ans;
	protected String q_type;			// TF or SA or MCQ
	public Question() {
		q_txt = "";
		q_ans = "";
		q_type = "";
	}
	
	public String get_qtype(BufferedReader fin) throws Exception{
		return fin.readLine();
	}
	
	public void print_ques() {
		System.out.println(q_txt);
	}
	
	public abstract void get_ques(BufferedReader fin) throws Exception;
	
	public int check_ans(String ans, String q_ans) {
		if(ans.equalsIgnoreCase(q_ans))			// Correct answer
			return 1;
		else if("Skip".equalsIgnoreCase(ans)) {		// Skip Question
			return 2;
		}
		else									// Incorrect answer
			return 3;
	}
}


/* Short answer type Questions */

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

/* True False Type Questions */
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

/* MCQ type questions */
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

/* main class*/
public class QuizBowl {
	public static void main(String args[]) throws Exception{
		Scanner inp = new Scanner(System.in);
		String fpath = System.getProperty("user.dir") + "\\src\\acstassignment\\questions.txt";
		String rpath = System.getProperty("user.dir") + "\\src\\acstassignment\\result.txt";
		String fchc;
		System.out.print("Enter 1 or User defined file input or any other key for default : ");
		fchc = inp.next();
		if("1".equals(fchc)) {
			System.out.print("Enter questions file path : ");
			fpath = inp.next();
			System.out.print("Enter results file path : ");
			rpath = inp.next();
		}
		
		BufferedReader fin = new BufferedReader(new FileReader(fpath));
		FileOutputStream fout = new FileOutputStream(rpath,true);
		
		Random rand = new Random();
		
		Player p = new Player();
		
		System.out.print("Enter First name : ");		
		String f_name = inp.next();
		
		
		if("".equals(f_name)) {
			while("".equals(f_name)) {
				System.out.print("Enter First name again : ");		
				f_name = inp.next();
			}
		}
		System.out.print("Enter Last name : ");		
		String l_name = inp.next();
		
		p.get_details(f_name, l_name);
		System.out.println("Hello "+ p.f_name + " " + p.l_name + "!!!");
		
		QuestionSA[] sa = new QuestionSA[100];			// array of class short answer type for storing data from file
		QuestionMC[] mc = new QuestionMC[100];			// array of class MCQ type for storing data from file
		QuestionTF[] tf = new QuestionTF[100];			// array of class true false answer type for storing data from file
		
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
		
		
		
		/* Logic to randomly choose any of the question from file */
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
		String chc;
		System.out.println("You want specific questions(SA, TF, MCQ, Random):");
		chc = inp.next();
		int t_no, q_no,r_sa=0,r_tf=0,r_mc=0;
		if("random".equalsIgnoreCase(chc)) {
			System.out.print("Enter no of questions you want to attempt out of "+cnt_file+" (in numbers):");
			int cnt = inp.nextInt();
			if(cnt>cnt_file || cnt<=0) {
				while(cnt>cnt_file && cnt<=0) {
					System.out.print("Invalid number of questions in file.... ");
					System.out.print("Enter again : ");
					cnt = inp.nextInt();
				}
			}
			
		
				
				
				for(int i=0; i<cnt; i++) {
					
					System.out.println();
					t_no = rand.nextInt(3) + 1;  	// Logic to randomly choose question type
					if(t_no==1) {					// Short answer type question chosen
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
					
					else if(t_no==2) {				// MCQ type question chosen
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
					
					else if(t_no==3) {			// True false type question chosen
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
		}
		else if("SA".equalsIgnoreCase(chc)) {
			System.out.print("Enter no of questions you want to attempt out of "+cnt_sa+" (in numbers):");
			int cnt = inp.nextInt();
			if(cnt>cnt_sa || cnt<=0) {
				while(cnt>cnt_sa|| cnt<=0) {
					System.out.print("Invalid number of questions in file.... ");
					System.out.print("Enter again : ");
					cnt = inp.nextInt();
				}
			}
			for(int i=0; i<cnt; i++) {
				q_no = arr_sa.get(r_sa);
				r_sa++;
				System.out.print("Q"+(i+1)+".)");
				sa[q_no].print_ques();
				System.out.print("Enter answer(single word/Skip - 20 points) : ");
				String answer = inp.next();
				sa[q_no].pt_inc(answer, sa[q_no].q_ans);
			}
		}
		else if("MCQ".equalsIgnoreCase(chc)) {
			System.out.print("Enter no of questions you want to attempt out of "+cnt_mc+" (in numbers):");
			int cnt = inp.nextInt();
			if(cnt>cnt_mc || cnt<=0) {
				while(cnt>cnt_mc || cnt<=0) {
					System.out.print("Invalid number of questions in file.... ");
					System.out.print("Enter again : ");
					cnt = inp.nextInt();
				}
			}
			for(int i=0; i<cnt; i++) {
				q_no = arr_mc.get(r_mc);
				r_mc++;
				System.out.print("Q"+(i+1)+".)");
				mc[q_no].print_ques();
				System.out.print("Enter answer(single word/Skip - 20 points) : ");
				String answer = inp.next();
				mc[q_no].pt_inc(answer, mc[q_no].q_ans);
			}
		}
		else if("TF".equalsIgnoreCase(chc)) {
			System.out.print("Enter no of questions you want to attempt out of "+cnt_tf+" (in numbers):");
			int cnt = inp.nextInt();
			if(cnt>cnt_tf || cnt<=0) {
				while(cnt>cnt_tf || cnt<=0) {
					System.out.print("Invalid number of questions in file.... ");
					System.out.print("Enter again : ");
					cnt = inp.nextInt();
				}
			}
			for(int i=0; i<cnt; i++) {
				q_no = arr_tf.get(r_tf);
				r_tf++;
				System.out.print("Q"+(i+1)+".)");
				tf[q_no].print_ques();
				System.out.print("Enter answer(single word/Skip - 20 points) : ");
				String answer = inp.next();
				tf[q_no].pt_inc(answer, tf[q_no].q_ans);
			}
		}
			System.out.println("\n"+p.f_name + " " +p.l_name + " gets "+ Player.score +" points.");
			fout.write((p.f_name+" "+p.l_name+"\n").getBytes());		// Write name to file
			fout.write((String.valueOf(Player.score)+"\n").getBytes());	//Write score to file
			
			inp.close();
			fout.close();
			fin.close();
	}
}