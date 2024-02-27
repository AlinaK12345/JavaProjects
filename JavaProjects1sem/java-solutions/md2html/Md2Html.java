package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Md2Html {
    private final static Map<String, String> TAG_HTML = Map.of(
            "*", "em",
            "_", "em",
            "**", "strong",
            "__", "strong",
            "--", "s",
            "`", "code",
            "```", "pre");

    private final static Map<Character, String> code = Map.of(
            '<', "&lt;",
            '>', "&gt;",
            '&', "&amp;");

    private static Set<String> findSingle(String p) {
        Map<Character, Integer> count = new HashMap<>(Map.of(
                '*', 0,
                '_', 0));
        for (int i = 0; i < p.length(); i++) {
            char a = p.charAt(i);
//            count.computeIfPresent(a, (__, v) -> v + 1);
            if (count.containsKey(a)) {
                count.put(a, count.get(a) + 1);
            }
            if (a == '\\'){
                i++;
            }
        }
        Set<String> ans = new HashSet<>();

        for (Map.Entry<Character, Integer> el : count.entrySet()) {
            if (el.getValue() == 1) {
                ans.add(Character.toString(el.getKey()));
            }
        }
        return ans;
    }

    private static String findTag(int i, String p) {
        if (i + 2 < p.length() && TAG_HTML.containsKey(p.substring(i, i + 3))) {
            return p.substring(i, i + 3);
        }
        if (i + 1 < p.length() && TAG_HTML.containsKey(p.substring(i, i + 2))) {
            return p.substring(i, i + 2);
        }
        if (TAG_HTML.containsKey(Character.toString(p.charAt(i)))) {
            return Character.toString(p.charAt(i));
        }
        return "";
    }

    private static String parse(String p) {
        StringBuilder answer = new StringBuilder();
        int level = intLevel(p);
        boolean isEscaped = false;

        String levelTag = levelTag(level);

        answer.append(openTag(levelTag));

        Set<String> single = findSingle(p);

        Deque<String> deque = new ArrayDeque<>();

        boolean isParse = false;

        int i = level + 1;
        while (i < p.length()) {
            if (isEscaped) {
                answer.append(p.charAt(i));
                i++;
                isEscaped = false;
                continue;
            }
            if (p.charAt(i) == '\\') {
                isEscaped = true;
                i++;
                continue;
            }
            String currentTag = findTag(i, p);
            if (currentTag.isEmpty() || single.contains(currentTag) || (isParse && !currentTag.equals("```"))){
                currentTag = "";
                if (code.containsKey(p.charAt(i))) {
                    answer.append(code.get(p.charAt(i)));
                } else {
                    answer.append(p.charAt(i));
                }
                i++;
            } else {
                if (currentTag.equals("```")){
                    isParse = !isParse;
                }
                if (!deque.isEmpty() && deque.getFirst().equals(currentTag)) {
                    answer.append(closeTag(TAG_HTML.get(currentTag)));
                    deque.removeFirst();
                } else {
                    deque.addFirst(currentTag);
                    answer.append(openTag(TAG_HTML.get(currentTag)));
                }
            }
            i += currentTag.length();
        }

        answer.append(closeTag(levelTag));
        answer.append(System.lineSeparator());
        return answer.toString();
    }

    private static String openTag(String tag) {
        return "<" + tag + ">";
    }

    private static String closeTag(String tag) {
        return "</" + tag + ">";
    }

    private static int intLevel(String line) {
        int j = 0;
        while (j < line.length() && line.charAt(j) == '#') {
            j++;
        }
        if (j >= line.length() || line.charAt(j) != ' ') {
            return -1;
        }
        if (j == 0) {
            return -1;
        }
        return j;
    }

    private static String levelTag(int level) {
        String tag;
        if (level == -1) {
            tag = "p";
        } else {
            tag = "h" + level;
        }

        return tag;
    }

    public static void main(String[] args) {
        String filenameIn = args[0]; // NOTE: no-args
        String filenameOut = args[1];

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filenameIn), StandardCharsets.UTF_8));
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(filenameOut), StandardCharsets.UTF_8));
                try {
                    String line = in.readLine();
                    StringBuilder p = new StringBuilder();
                    while (line != null) {
                        while (line != null && line.isEmpty()) {
                            line = in.readLine();
                        }
                        while (line != null && !line.isEmpty()) {
                            if (!p.isEmpty()) {
                                p.append(System.lineSeparator());
                            }
                            p.append(line);
                            line = in.readLine();
                        }
                        if (!p.isEmpty()) {
                            String a = parse(p.toString());

                            out.write(a);
                            line = in.readLine();
                            p = new StringBuilder();
                        }
                    }
                } finally {
                    out.close();
                }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not founded: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }

    }
}
