package wspp;

import wspp.Pair;

import java.io.*;
import java.util.TreeMap;
import java.util.Map;


public class WsppSortedFirst {

    static void toMap(String word, TreeMap<String, Pair> map, int pos, int firstInLine) {
        if (map.get(word) != null) {
            Pair count = map.get(word);
            if (count.lastInd() < firstInLine || pos == firstInLine) {
                count.add(pos);
            }
            count.increase();
            // :NOTE: get + get + put
            map.put(word, count);
        } else {
            Pair count = new Pair();
            count.add(pos);
            count.increase();
            map.put(word, count);
        }
    }

    public static void main(String[] args) {
        String filenameIn = args[0];
        String filenameOut = args[1];
        TreeMap<String, Pair> map = new TreeMap<>();
        try {
            Scanner in = new Scanner(filenameIn);

            try {
                int lineNo = 0;
                int pos = 0;
                int firstInLine = 1;
                while (in.hasNextStr()) {
                    if (lineNo == in.lineNumber()) {
                        pos++;
                        String word = in.nextStr().toLowerCase();
                        toMap(word, map, pos, firstInLine);
                    } else {
                        lineNo++;
                        firstInLine = pos + 1;
                    }
                }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not founded: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Cannot read input file: " + e.getMessage());
        }

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filenameOut), "UTF-8"));
            try {
                for (Map.Entry<String, Pair> i : map.entrySet()) {
                    out.write(i.getKey() + " " + i.getValue().toString());

                    out.newLine();
                }
            } finally {
                out.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Output file not founded: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Cannot read output file: " + e.getMessage());
        }
    }
}