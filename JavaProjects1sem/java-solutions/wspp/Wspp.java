package wspp;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class Wspp {

    static void toMap(String word, LinkedHashMap<String, IntList> map, int pos) {
        IntList a = new IntList(0);
        // :NOTE: new IntList
        IntList count = map.getOrDefault(word, a);
        count.count();
        count.add(pos);
        map.put(word, count);

    }

    public static void main(String[] args) {
        String filenameIn = args[0];
        String filenameOut = args[1];
        LinkedHashMap<String, IntList> map = new LinkedHashMap<>();
        int idWord = 0;
        try {
            Scanner in = new Scanner(filenameIn);
            try {
                while (in.hasNextStr()) {
                    String word = in.nextStr().toLowerCase();
                    idWord++;
                    toMap(word, map, idWord);
                }

            } finally {
                in.close();
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filenameOut), "UTF-8"));
            try {
                for (Map.Entry<String, IntList> i : map.entrySet()) {
                    out.write(i.getKey() + " " + i.getValue().toString());
                    out.newLine();
                }
            } finally {
                out.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not founded" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Cannot read input file" + e.getMessage());
        }
    }

}