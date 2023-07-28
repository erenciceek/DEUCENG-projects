package whowantstomillionaire;

import java.util.*;

public class CategoryStatistic {
	private String catogery;
	private int CategoryCounter;
	
	public CategoryStatistic(String c, int a) {
		catogery = c;
		CategoryCounter = a;
			
	}

	public String getCatogery() {
		return catogery;
	}

	public void setCatogery(String catogery) {
		this.catogery = catogery;
	}

	public int getCategoryCounter() {
		return CategoryCounter;
	}

	public void setCategoryCounter(int CategoryCounter) {
		this.CategoryCounter = CategoryCounter;
	}
	

}