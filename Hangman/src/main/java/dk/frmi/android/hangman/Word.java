package dk.frmi.android.hangman;

/**
 * Created by Frederik on 05-11-13.
 */
public class Word {
    String word;
    String category;
    String[] facit;
    String[] guess;


    public Word(String word, String category){
        this.word = word;
        this.category = category;
        facit = Helper.stringToArray(this.word);
        guess = convertWordToUnderscores();
    }

    public Word(String word, String category, String guess){
        this.word = word;
        this.category = category;
        facit = Helper.stringToArray(this.word);
        this.guess = Helper.stringToArray(guess);
    }

    private String[] convertWordToUnderscores(){
        int length = facit.length;
        String[] result = new String[length];
        for (int i = 0; i < length; i++){
            if (facit[i].equals(" ")) {
                result[i] = " ";
            } else if (facit[i].equals("-")) {
                result[i] = "-";
            } else {
                result[i] = "_";
            }
        }
        return result;
    }
}
