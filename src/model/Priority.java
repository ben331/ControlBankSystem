package model;

import java.time.LocalTime;

public class Priority implements Comparable<Priority>{
	
	private boolean elderly;
	private boolean underFiveYears;
	private boolean pregnant;
	private boolean disabled;
	private int value;
	private LocalTime arrivelTime;
	
	public Priority(boolean elderly, boolean underFiveYears, boolean pregnant, boolean disabled) {
		this.elderly = elderly;
		this.underFiveYears = underFiveYears;
		this.pregnant = pregnant;
		this.disabled = disabled;
		calculatePriorityValue();
		this.arrivelTime = LocalTime.now();
	}
	
	private void calculatePriorityValue() {
		int priority = 0;
		if(elderly) {
			priority++;
		}if(underFiveYears) {
			priority++;
		}if(pregnant) {
			priority++;
		}if(disabled) {
			priority++;
		}
		this.value = priority;
	}
	
	@Override
	public int compareTo(Priority otherPriority) {
		int difference=otherPriority.value-value;
		if(difference==0) {
			difference = arrivelTime.compareTo(otherPriority.arrivelTime);
		}
		return difference;
	}
	
}
