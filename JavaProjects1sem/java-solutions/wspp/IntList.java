package wspp;

import java.util.Arrays;


public class IntList {
	// :NOTE: access modifiers
	int[] array;
	int lastInd;


	public IntList(int x) {
		array = new int[1];
		array[0] = x;
		lastInd = 0;
	}
	

	public IntList() {
		array = new int[1];
		lastInd = -1;
	}

	private void makeBigger() {
		array = Arrays.copyOf(array, array.length * 2);
	
	}

	public void add(int x) {
		if (lastInd == array.length - 1) {
			makeBigger();
		}
		lastInd++;
		array[lastInd] = x;

	}

	public int last(){
		return array[lastInd];
	}

	public int get(int i) {
		return array[i];
	}
	// :NOTE: Meaningless

	public void count(){
		array[0]++;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < lastInd; i++){
			str.append(array[i]);
			str.append(" ");
		}
		str.append(array[lastInd]);
		return str.toString();
	}
}
