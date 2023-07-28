package whowantstomillionaire;

public class Question {
	private String Category;	
	private String Text;
	private String Answer1;
	private String Answer2;
	private String Answer3;
	private String Answer4;
	private String CorrectAnswer;
	private int Difficultydegree;	
	private boolean Is_tried;
	
	public Question(String category, String text, String choice1, String choice2, String choice3, String choice4,
			String correctAnswer, String difficultydegree, boolean istried) {
		this.Category = category;
		this.Text = text;
		this.Answer1 = choice1;
		this.Answer2 = choice2;
		this.Answer3 = choice3;
		this.Answer4 = choice4;
		this.CorrectAnswer = correctAnswer;
		this.Difficultydegree = Integer.parseInt(difficultydegree);
		this.Is_tried = istried;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}

	public String getAnswer1() {
		return Answer1;
	}
	public void setAnswer1(String answer1) {
		Answer1 = answer1;
	}
	public String getAnswer2() {
		return Answer2;
	}
	public void setAnswer2(String answer2) {
		Answer2 = answer2;
	}
	public String getAnswer3() {
		return Answer3;
	}
	public void setAnswer3(String answer3) {
		Answer3 = answer3;
	}
	public String getAnswer4() {
		return Answer4;
	}
	public void setAnswer4(String answer4) {
		Answer4 = answer4;
	}
	public String getCorrectAnswer() {
		return CorrectAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		CorrectAnswer = correctAnswer;
	}
	public int getDifficultydegree() {
		return Difficultydegree;
	}
	public void setDifficulty(int difficultydegree) {
		Difficultydegree = difficultydegree;
	}
	public boolean getIstried() {
		return Is_tried;
	}
	public void setIstried(boolean is_tried) {
		Is_tried = is_tried;
	}
	
	
	public String displayQuestion() {
		return "Category:" + Category + "\n" + Text + "\n" + "A:" + Answer1 + "  B:"+ Answer2 + "  C:"+ Answer3 + "  D:"+Answer4+"\n"+"Correct Answer : "+CorrectAnswer + "\n"+ "Degree of Difficulty: "+Difficultydegree;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}