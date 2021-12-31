import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Classifier {

    private final BagOfWords bagOfWords;

    private int numberPositiveExample;
    private int numberNegativeExample;
    private double falseProbability;
    private double trueProbability;
    private int limitOnNegative;
    private int limitOnPositive;

    private final static double LIMIT = 0.0000001;
    private final static double MULTIPLICATION_ON_LIMIT = 10000000;
    public final static int INVALID = -1;

    public Classifier() {
        bagOfWords = new BagOfWords();
        numberNegativeExample = 0;
        numberPositiveExample = 0;
        falseProbability = INVALID;
        trueProbability = INVALID;
    }

    public void setPositiveExample( String example ) {
        numberPositiveExample++;
        setExample( example, true );
    }

    public void setNegativeExample( String example ) {
        numberNegativeExample++;
        setExample( example, false );
    }
    

    public Category classify( String stringToClassify ) {
        limitOnPositive = 0;
        limitOnNegative = 0;
        int numberExamples = numberPositiveExample + numberNegativeExample;
        double percentTrue = ( double ) ( numberPositiveExample ) /
            ( double ) ( numberExamples );
        double percentFalse = ( double ) ( numberNegativeExample ) /
            ( double ) ( numberExamples );
        falseProbability = probability( stringToClassify, false ) *
            percentFalse;
        trueProbability = probability( stringToClassify, true ) *
            percentTrue;

        int difference = limitOnPositive - limitOnNegative;
        if( difference > 0 ) {
            falseProbability *= Math.pow( MULTIPLICATION_ON_LIMIT, difference );
        } else if( difference < 0 ) {
            trueProbability *= Math.pow( MULTIPLICATION_ON_LIMIT, -difference );
        }

        if( trueProbability > falseProbability ) {
            return Category.CATEGORY_POSITIVE;
        } else {
            return Category.CATEGORY_NEGATIVE;
        }
    }

    public double chanceOfPositive() {
        return chance( true );
    }

    public double chanceOfNegative() {
        return chance( false );
    }

    private double chance( boolean positive ) {
        double total = trueProbability + falseProbability;
        if( total < 0 ) {
            return INVALID;
        } else {
            if( positive ) {
                return trueProbability / total;
            } else {
                return falseProbability / total;
            }
        }
    }

    private double probability( String stringToClassify, boolean positive ) {
        double returnProbability = 1;
        String lowerCase = stringToClassify.toLowerCase();
        List<String> words = new ArrayList<>();
        for(int i = 1; i <= NaiveBayesClassifier.N_GRAM; i++){
            words.addAll(NGramTokenizer.ngrams(i, lowerCase));
        }
        if( positive ) {
            for( String word : words ) {
                returnProbability *= bagOfWords.probabilityAsPositive( word );
                if( returnProbability < LIMIT ) {
                    returnProbability *= MULTIPLICATION_ON_LIMIT;
                    limitOnPositive++;
                }
            }
        } else {
            for( String word : words ) {
                returnProbability *= bagOfWords.probabilityAsNegative( word );
                if( returnProbability < LIMIT ) {
                    returnProbability *= MULTIPLICATION_ON_LIMIT;
                    limitOnNegative++;
                }
            }
        }
        return returnProbability;
    }
    

    private void setExample( String example, boolean positive ) {
        String lowerCase = example.toLowerCase();
        List<String> words = new ArrayList<>();
        for(int i = 1; i <= NaiveBayesClassifier.N_GRAM; i++){
            words.addAll(NGramTokenizer.ngrams(i, lowerCase));
        }
        for( String word : words ) {
            if( positive ) {
                bagOfWords.setPositive( word );
            } else {
                bagOfWords.setNegative( word );
            }
        }
    }
}
