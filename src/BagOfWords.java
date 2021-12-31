import java.util.HashMap;


public class BagOfWords {

    private final HashMap< String, WordProperty> wordsMap;

    private int positiveWord;
    private int negativeWord;
    private static final int DEFAULT_VALUE = 1;

    public BagOfWords() {
        wordsMap = new HashMap<>();
        negativeWord = 0;
        positiveWord = 0;
    }

    public void setPositive( String word ) {
        positiveWord++;
        addWord( word, true );
    }

    public void setNegative( String word ) {
        negativeWord++;
        addWord( word, false );
    }

    public double probabilityAsNegative( String word ) {
        if( wordsMap.containsKey( word ) ) {
            WordProperty wordInformation = wordsMap.get( word );
            return ( double ) ( 1 + wordInformation.getNegativeAmount() ) /
                ( ( double ) ( 2 * negativeWord + positiveWord) );
        } 
        return DEFAULT_VALUE;
    }

    public double probabilityAsPositive( String word ) {
        if( wordsMap.containsKey( word ) ) {
            WordProperty wordInformation = wordsMap.get( word );
            return ( double ) ( 1 + wordInformation.getPositiveAmount() ) /
                ( ( double ) ( 2 * positiveWord + negativeWord) );
        }
        return DEFAULT_VALUE;
    }

    private void addWord(String word, boolean positive ) {
        if( wordsMap.containsKey( word ) ) {
            WordProperty wordInformation = wordsMap.get( word );
            if( positive ) {
                wordInformation.addPositive();
            } else {
                wordInformation.addNegative();
            }
        } else {
            WordProperty wordInformation = new WordProperty();
            if( positive ) {
                wordInformation.addPositive();
            } else {
                wordInformation.addNegative();
            }
            wordsMap.put( word, wordInformation );
        }
    }
}
