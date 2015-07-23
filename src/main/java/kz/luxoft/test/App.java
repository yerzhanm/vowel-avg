package kz.luxoft.test;

import java.io.*;
import java.util.*;

public class App
{
    public static void main( String[] args )
    {
        System.out.println("Path to input file : ");
        String s = "";
        Scanner scanIn = new Scanner(System.in);
        s = scanIn.nextLine();
        scanIn.close();

        if(s.equals("")) {
            System.out.println("Empty file path");
            return;
        }

        List<String> words = readWords(s);
        List<WordSet> wordsAvg = calculateAvg(words);
        writeToFile(wordsAvg);
    }

    public static void writeToFile(List<WordSet> wordsAvg){
        try(PrintWriter writer = new PrintWriter("output.txt", "UTF-8")) {
            for(WordSet wa : wordsAvg){
                writer.println(wa);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static List<WordSet> calculateAvg(List<String> words){
        List<WordSet> wordsAvg = new ArrayList<>();

        for(String w : words){
            Set<Character> charsSet = new HashSet<Character>();
            int size = w.length();
            int vowelsCount = 0;
            for(char c : w.toCharArray()){
                if(c=='a' || c=='e' || c=='i' || c=='o' || c=='u') {
                    charsSet.add(c);
                    vowelsCount++;
                }
            }

            WordSet wordSet = new WordSet();
            wordSet.charSet=charsSet;
            wordSet.wordLength=size;
            wordSet.totalVowel=vowelsCount;

            if(wordsAvg.contains(wordSet)){
                for (WordSet wa : wordsAvg)
                    if(wa.equals(wordSet)) {
                        wa.count++;
                        wa.totalVowel+=vowelsCount;
                        break;
                    }
            }else{
                wordsAvg.add(wordSet);
            }
        }
        Collections.sort(wordsAvg);
        return wordsAvg;
    }

    public static List<String> readWords(String filePath) {
        List<String> wordList = new ArrayList<String>();

        try (
                FileReader fr = new FileReader(filePath);
                BufferedReader br = new BufferedReader(fr)
        ){

            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split("[^a-zA-Z0-9\\-\']");
                for (String w : parts) {
                    if(w.matches("^[a-zA-Z0-9\\-']{1,}$")) {
                        wordList.add(w.toLowerCase());
                    }
                }
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordList;
    }
}
