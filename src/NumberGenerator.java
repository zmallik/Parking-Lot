public class NumberGenerator {
    private int sequence;
    private static NumberGenerator numberGenerator;
    private NumberGenerator(){
        this.sequence = 1;
    }

    public static NumberGenerator getNumberGenerator(){
        if(numberGenerator == null){
            numberGenerator = new NumberGenerator();
        }

        return numberGenerator;
    }

    public int getSequence(){
        return sequence++;
    }
}
