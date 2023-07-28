package whowantstomillionaire;

import java.util.*;

public class ContestantStatistic {
	private String name;
	private int level;
	private int correcttimes;
	private int age;
	private String cityadress;
	
		
	public ContestantStatistic(String name,int level,int correcttimes, int age ,String cityadress) {
		this.name = name;
		this.level = level;
		this.age = age;
		this.correcttimes = correcttimes;
		this.cityadress = cityadress;
	}


	public String getCityadress() {
		return cityadress;
	}


	public void setCityadress(String cityadress) {
		this.cityadress = cityadress;
	}


	public int getCorrecttimes() {
		return correcttimes;
	}


	public void setCorrecttimes(int correcttimes) {
		this.correcttimes = correcttimes;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}
	
}