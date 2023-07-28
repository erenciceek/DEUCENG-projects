package whowantstomillionaire;

import enigma.console.*;
import enigma.core.Enigma;

public class Timer {
	
	private long startTime; 
	private long elapsedTime;
	private long elapsedSeconds;
	private long secondsDisplay;
	public static boolean flag;
	
	public Timer() {
		long startTime = System.currentTimeMillis();
		long elapsedTime = System.currentTimeMillis() - startTime;
		long elapsedSeconds = elapsedTime / 1000;
		long secondsDisplay = elapsedSeconds % 60;
	}
	public long getStartTime() {
		return System.currentTimeMillis();
	}
	public boolean getFlag() {
		return flag;
	}
	
	public void timer(long startTime) {
		int cursorx =  Enigma.getConsole().getTextWindow().getCursorX(), cursory =  Enigma.getConsole().getTextWindow().getCursorY();
		long secondsDisplay = 0;
		long temp = 0;
		long elapsedTime = System.currentTimeMillis() - startTime;
		long elapsedSeconds = elapsedTime / 1000;
		secondsDisplay = elapsedSeconds % 60;
		if (secondsDisplay >= 0 && secondsDisplay < 21 && temp != secondsDisplay) {
			System.out.println(secondsDisplay);
			temp = secondsDisplay;
			
			if (secondsDisplay >= 20) {
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println("Your time has up. Game over! \n");
				flag = false;
			}
			else {
				flag = true;
			}
		}
		Enigma.getConsole().getTextWindow().setCursorPosition(cursorx, cursory);
	}
	public long getSecond() {
		return secondsDisplay;
	}
}