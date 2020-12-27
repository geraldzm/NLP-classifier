import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ModelFile extends Thread{

    private final File file; // txt to read
    private final MyHashTable model; // histogram / frequency table
    private final int nGram;
    private double norm;

    public ModelFile(File file, MyHashTable model, int nGram){
        super();
        this.nGram = nGram;
        this.model = model;
        this.file = file;
        norm = -1;
    }

    public ModelFile(File file, int nGram){
        super();
        this.nGram = nGram;
        this.model = new MyHashTable();
        this.file = file;
        norm = -1;
    }

    @Override
    public void run() {

        try (Scanner scanner = new Scanner(file)){

            //read the whole file
            scanner.useDelimiter("\\Z");
            if(!scanner.hasNext()) return; // if txt is empty

            String fileText = scanner.next();


            //tokenize into individual words
            Matcher matcher = Pattern.compile("[A-Za-zÀ-ÖØ-öø-ÿ]{"+nGram+",}").matcher(fileText); // extract each word

            // for each word get the n-gram
            matcher.results().forEach(m -> nGramOfWord(m.group(0))); // java 9 >=



            /*

            */


           /* StringBuilder sb = new StringBuilder();

            fileText.chars().forEach(
                    i -> {

                        Character c = (char) i;
                        if(!c.toString().matches("[A-Za-zÀ-ÖØ-öø-ÿ]")){
                            sb.setLength(0); // clean
                        }else{
                            sb.append(c);
                            if(sb.length() >= nGram){
                                model.addNGram(sb.toString().toLowerCase());
                                sb.deleteCharAt(0);
                            }
                        }

                    }
            );*/



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * Extracts the n-gram & adds the occurrences to the model
     * @param word a word with length >= nGram
     * */
    private void nGramOfWord(String word) {

        IntStream.range(0, word.length() - nGram + 1)
                .forEach(
                        i -> model.addNGram(word.substring(i, i + nGram).toLowerCase())
                );

    }

    private void calculateNorm(){

        Integer reduce = model.values().stream()
                .reduce(0, (sum, toSum) -> sum + (toSum * toSum));

       // int sum = model.values().stream().mapToInt(i -> i * i).sum();

        norm = Math.sqrt(reduce);
    }

    synchronized public double getNorm(){
        if(norm == -1)
            calculateNorm();
        return norm;
    }

    public MyHashTable getVector(){
        return model;
    }

}