package dk.frmi.android.hangman;

import android.graphics.Point;

/**
 * Created by Frederik on 04-11-13.
 */
public class PointCalculator {

    private final static String VOCALS = "aeiouyæøå";
    private final static String CONSONANTS = "bcdfghjklmnpqrstvxzwy";
    private final static int POINTS_LENGTH = 5;
    private final static int POINTS_PER_VOCAL = 5;
    private final static int POINTS_PER_CONSONANT = 10;
    private final static int POINTS_FOR_WIN = 20;
    private final static int POINTS_PER_ATTEMP = -2;
    private final static float EXP_PER_STREAK = 0.2F;

    public static int calculatePoints(String[] word, int attemps, int streak){
        int points = 0;
        int wordLength = word.length;
        int amountOfConsonants = 0;
        int amountOfVocals = 0;

        for (int i = 0; i < wordLength; i++){
            String ch = word[i].toLowerCase();
            if (VOCALS.contains(ch)){
                amountOfVocals++;
            } else if (CONSONANTS.contains(ch)){
                amountOfConsonants++;
            }
        }

        points += attemps * POINTS_PER_ATTEMP;
        points += wordLength * POINTS_LENGTH;
        points += amountOfVocals * POINTS_PER_VOCAL;
        points += amountOfConsonants * POINTS_PER_CONSONANT;
        points += POINTS_FOR_WIN;
        points += points + (points * (streak * EXP_PER_STREAK));

        return points;
    }
}
