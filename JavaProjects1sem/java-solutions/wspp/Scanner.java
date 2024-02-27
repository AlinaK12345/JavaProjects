package wspp;//package reverse;//package hw5;

import java.io.*;


public class Scanner {
    Reader reader;
    char[] buffer;
    private int left = 0;
    private int right = 0;
    StringBuilder word = new StringBuilder();
    int lineNumber = 0;
    private final String SEPORATOR = System.lineSeparator();
    boolean inLine;

    private void getBuffer() throws IOException {
        this.buffer = new char[1000];
        this.right = reader.read(buffer); //right = -1 -- читать нечего
        this.left = 0;
    }
/*
    public Scanner(String string) throws IOException {
        this.reader = new StringReader(string);
        getBuffer();
    }*/

    public Scanner(String file) throws IOException {
        this.reader = new InputStreamReader(
                new FileInputStream(file), "UTF-8");
        getBuffer();
    }

    public Scanner(InputStream input) throws IOException {
        this.reader = new InputStreamReader(input);
        getBuffer();
    }

    
    String getValueW() {
        String ans = this.word.toString();
        this.word = new StringBuilder();
        return ans;
    }

    public boolean hasNextBuffer() {
        return right > 0;
    }

    public void close() throws IOException {
        reader.close();
    }

    public boolean isSeparator() throws IOException {
        String str = SEPORATOR;

        for (int i = 0; i < str.length(); i++, this.left++) {
            if (left == right) {
                if (hasNextBuffer()) {
                    getBuffer();
                } else {
                    return false;
                }
            }

            char a = buffer[left];
            if (a != str.charAt(i)) {
                return false;
            }
        }
        inLine = false;
        return true;
    }

    public boolean hasEmptyLine() throws IOException {
        for (left = this.left; left < right; left++) {
            char a = buffer[left];
            if (isNumber(a)) {
                return false;
            }
            if (isSeparator()) {
                lineNumber++;
                return true;
            }

        }
        if (hasNextBuffer()) {
            getBuffer();
            return hasEmptyLine();
        } else {
            return false;
        }
    }

    public boolean hasNextInLine() throws IOException {
        boolean a = inLine;
        return a & hasNext();
    }

    public boolean hasNext() throws IOException {
        while (left < right) {
            if (isSeparator()) {
                lineNumber++;
                left--; //////////////////

            }
            if (left < right) {
                char a = buffer[left];
                if (isNumber(a)) {
                    inLine = true;
                    return true;
                }
            }
            left++;
        }
        if (hasNextBuffer()) {
            getBuffer();
            return hasNext();
        } else {
            return false;
        }
    }

    public boolean hasNextStr() throws IOException {
        while (left < right) {
            if (isSeparator()) {
                lineNumber++;
                left--;

            }
            if (left < right) {
                char a = buffer[left];
                if (isLetter(a)) {
                    inLine = true;
                    return true;
                }
            }
            left++;
        }
        if (hasNextBuffer()) {
            getBuffer();
            return hasNextStr();
        } else {
            return false;
        }
    }

    String next() throws IOException {
        while (left < right) {
            char a = buffer[left];
            if (!isNumber(a)) {
                if (isSeparator()) {
                    this.lineNumber++;
                    if (SEPORATOR.length() == 2) {
                        left--;
                    }
                    //if (!word.isEmpty()) {
                        //left--;
                    //}
                    
                }
                if (this.word.length() != 0) {
                    //left++;
                    return getValueW();
                }
                break;
            }
            this.word.append(a);
            left++;
        }
        if (hasNextBuffer()) {
            getBuffer();
            return next();
        } else {
            return getValueW();
        }
    }

    String nextStr() throws IOException {
        while (left < right) {
            char a = buffer[left];
            if (!isLetter(a)) {
                if (isSeparator()) {
                    this.lineNumber++;
                        left--;
                }
                if (this.word.length() != 0) {
                    //left++;
                    return getValueW();
                }
                break;
            }
            this.word.append(a);
            left++;
        }
        if (hasNextBuffer()) {
            getBuffer();
            return nextStr();
        } else {
            return getValueW();
        }
    }

    public int lineNumber(){
        return lineNumber;
    }


    private boolean isLetter(char a) {
        return Character.isLetter(a) || a == '\'' ||
                Character.getType(a) == Character.DASH_PUNCTUATION;
    }

    private boolean isNumber(char a) {
        return Character.isLetter(a) || Character.isDigit(a) || Character.getType(a) == Character.DASH_PUNCTUATION;
        //return Character.isLetter(a) || a == '\'' ||
          //      Character.getType(a) == Character.DASH_PUNCTUATION || Character.isDigit(a);
    }



}
	
