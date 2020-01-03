package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Unique {
	private Integer[] unique = {1,2,3,4,5,6,7,8,9};
	
	public int[] getUnique() {
		return shuffle().stream().mapToInt(i->i).toArray();
	}
	
	private List<Integer> shuffle() {
		List<Integer> uniqueList = Arrays.asList(unique);
		Collections.shuffle( uniqueList );
		return uniqueList;
	}
}
