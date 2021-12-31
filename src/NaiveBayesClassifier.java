import java.util.List;

public class NaiveBayesClassifier {

    private static final int TRAINING_ITERATION = 1;
    private static final boolean DO_STEM = false;
    public static final int N_GRAM = 3;
    public static final boolean REMOVE_STOP_WORDS = true;

    public static void main(String[] args) {
        Classifier naiveBayes = new Classifier();
        FileReader fr = new FileReader();
        
        // Build and train the classifier
        long trainingStartTime = System.currentTimeMillis();
        List<Data> trainingData = fr.readDataFromFile(args[0], DO_STEM);
        trainClassifier(trainingData, naiveBayes, TRAINING_ITERATION);
        long trainingEndTime = System.currentTimeMillis();

        double trainingAccuracy = testClassifier(trainingData, naiveBayes);

        // test the classifier
        long labelingStartTime = System.currentTimeMillis();
        List<Data> testingData = fr.readDataFromFile(args[1], DO_STEM);
        double testingAccuracy= testClassifier(testingData, naiveBayes);
        long labelingEndTime = System.currentTimeMillis();

        System.out.println(String.format("%.3f seconds (training)",  ((double)(trainingEndTime - trainingStartTime) / 1000)));
        System.out.println(String.format("%.3f seconds (labeling)",  ((double)(labelingEndTime - labelingStartTime) / 1000)));
        System.out.println(String.format("%.3f (training)",trainingAccuracy));
        System.out.println(String.format("%.3f (testing)",testingAccuracy));

    }

    public static void trainClassifier(List<Data> trainingData, Classifier naiveBayes, int trainingIteration) {
        for (int i = 0; i < trainingIteration; i++) {
            trainingData.forEach(
                data -> {
                    if(data.getCategory() == Category.CATEGORY_POSITIVE) {
                        naiveBayes.setPositiveExample(data.getComment());
                    } else {
                        naiveBayes.setNegativeExample(data.getComment());
                    }
                });
        }
    }

    public static double testClassifier(List<Data> testingData, Classifier naiveBayes) {
        long resultCount = testingData.stream()
            .filter(data -> {
                Category classification = naiveBayes.classify(data.getComment());
                System.out.println(classification.getValue());
                return classification.getValue().equals(data.getCategory() == null ? null : data.getCategory().getValue());
            })
            .count();

        return (double) resultCount/testingData.size();
    }


}
