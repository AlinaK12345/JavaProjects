package wspp;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

public class WordStatCountMiddleL {

	static void getWord(String line, LinkedHashMap<String, Integer> map) {
		for (int i = 0; i < line.length(); i++){
			char a = line.charAt(i);
			int left = i;
			while (i < line.length()){
				a = line.charAt(i);
				if(!(Character.isLetter(a) ||  a == '\'' ||
						Character.getType(a) == Character.DASH_PUNCTUATION)) {
					break;
				}
				i++;
			}
			if (i > left && i - left >= 5) {
				String word = line.substring(left + 2, i - 2);
				int nomber = 0;
				map.put(word, map.getOrDefault(word, 0) + 1);
			}
		}
	}
	
	public static void main(String[] args) {
		String filenameIn = args[0];
		String filenameOut = args[1];
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(filenameIn), "UTF-8"));
			try {
				String line = in.readLine();
				while (line != null){
					getWord(line.toLowerCase(), map); 
					line = in.readLine();
				}
			} 
			finally {
				in.close();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Input file not found" + e.getMessage());
		} 
		catch (IOException e) {
			System.out.println("Cannot read input file" + e.getMessage());
		}


		String[][] arr = new String[map.size()][2];
		int j = 0;
		for (Map.Entry<String, Integer> i: map.entrySet()) {
			arr[j] = new String[]{i.getKey(), i.getValue().toString()};
			j++;
		}
		Arrays.sort(arr, new Comparator<String[]>() {
			@Override
			public int compare(String[] a, String[] b) {
				return Integer.valueOf(a[1]) - Integer.valueOf(b[1]);
			}
		});

		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filenameOut), "UTF-8"));
			try {
				for (int i = 0; i < map.size(); i++) {
					out.write(arr[i][0] + " " + arr[i][1]);
					out.newLine();
				}
			}finally {
				out.close();
			}

		}
		catch (FileNotFoundException e) {
			System.out.println("File not found" + e.getMessage());
		} 
		catch (IOException e) {
			System.out.println("Cannot read output file" + e.getMessage());
		}
	}
}