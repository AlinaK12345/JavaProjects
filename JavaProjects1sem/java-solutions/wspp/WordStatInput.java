package wspp;
//package wordStat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WordStatInput{

    static void toMap(String word, HashMap<String, Integer> map, Map<Integer, String> mapInd){
        
        if (map.containsKey(word)) {
            map.put(word, map.get(word) + 1);
        }
        else {
            mapInd.put(map.size(), word);
            map.put(word, 1);
        }
    }


    static void getWord(String line, HashMap<String, Integer> map, Map<Integer, String> mapInd){
        int left = -1;
        for (int i = 0; i < line.length(); i++){
            char a = line.charAt(i);
            
            left = i;
            while (i < line.length()){
                a = line.charAt(i);
                if(!(Character.isLetter(a) ||  a == '\'' ||
                        Character.getType(a) == Character.DASH_PUNCTUATION)){
                    break;
                }
                
                i++;
            }
            if (i > left){
                String word = line.substring(left, i);
                
                toMap(word, map, mapInd);
            }
        }
    }

    public static void main(String[] args){
        String filenameIn = args[0];
        String filenameOut = args[1];
        HashMap<String, Integer> map = new HashMap<>();
        Map<Integer, String> mapInd = new HashMap<>();
        //int len = 0;
        try {
            Scanner in = new Scanner(filenameIn);
            try{
                while (in.hasNextStr()){
                    
                    String word = in.nextStr().toLowerCase();
                    getWord(word, map, mapInd);
                        
                }
            }
            finally {
                in.close();
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filenameOut), "UTF-8"));
            try{
                for (int i = 0; i < map.size(); i++){
                    out.write(mapInd.get(i)+" ");
                    out.write(map.get(mapInd.get(i)).toString());
                    out.newLine();
                }
            }finally {
                out.close();
            }

        }
        catch (FileNotFoundException e){
            System.out.println("File not founded" + e.getMessage());
        }
        catch (IOException e){
            System.out.println("Cannot read input file" + e.getMessage());
        }
    }
}