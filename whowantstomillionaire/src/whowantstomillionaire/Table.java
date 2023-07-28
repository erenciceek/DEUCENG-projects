package whowantstomillionaire;

import enigma.console.*;
import enigma.core.Enigma;

public class Table {
	
	private String table;
	private Timer time;
	
	
	public Table () {
		Timer time = new Timer();
		
		String table = "--------------------------- \n"
				+      "| Money :                 | \n"
				+      "|                         | \n"
				+      "| Remaining time :        | \n"
				+      "|                         | \n"
				+      "| %50                     | \n"
				+      "|                         | \n"
				+      "| Double Dip              | \n"
				+      "|                         | \n"
				+      "---------------------------";
	}
	
	public String getTable() {
		Enigma.getConsole().getTextWindow().setCursorPosition(50, 5);
		String table = "--------------------------- \n"
				+      "| Money :                 | \n"
				+      "|                         | \n"
				//+      "| Remaining time : " + time.timer() + "     | \n"
				+      "|                         | \n"
				+      "| %50                     | \n"
				+      "|                         | \n"
				+      "| Double Dip              | \n"
				+      "|                         | \n"
				+      "---------------------------";
		System.out.print(table);
		return table;
	}
}
