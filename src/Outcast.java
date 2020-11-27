import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        if(wordnet == null){
            throw new IllegalArgumentException();
        }
        this.wordnet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        if(nouns == null || nouns.length == 0){
            throw new IllegalArgumentException();
        }
        int maxDist = 0;
        int outcast = 0;
        int i = 0;
        for(String nounA : nouns){
            int sumDist = 0;
            for(String nounB : nouns){
                sumDist += wordnet.distance(nounA, nounB);
            }
            if(sumDist > maxDist){
                outcast = i;
                maxDist = sumDist;
            }
            i++;
        }
        return nouns[outcast];
    }

    public static void main(String[] args){
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}