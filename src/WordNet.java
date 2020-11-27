import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Bag;
import java.util.ArrayList;

public class WordNet {

    private SET<String> nouns;
    private BST<String, Bag<Integer> > setMap;
    private ArrayList<String> sets;
    private int synsetCount;
    private Digraph synsetGraph;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){

        if(synsets == null || hypernyms == null){
            throw new IllegalArgumentException();
        }

        nouns = new SET<String>();
        setMap = new BST<String, Bag<Integer> >();
        sets = new ArrayList<String>();
        synsetCount = 0;
        
        // build synset data structures
        In synsetsIn = new In(synsets);
        String line;
        int setNumber;
        Bag<Integer> set;
        while(!synsetsIn.isEmpty()){
            line = synsetsIn.readLine();
            setNumber = Integer.parseInt(line.split(",")[0]);
            sets.add(line.split(",")[1]);
            for(String s: line.split(",")[1].split(" ")){
                nouns.add(s);
                set = setMap.get(s);
                if(set != null){
                    set.add(setNumber);
                } else {
                    set = new Bag<Integer>();
                    set.add(setNumber);
                    setMap.put(s, set);
                }
            }
            synsetCount++;
        }

        // build hypernym graph
        synsetGraph = new Digraph(synsetCount);
        In hypernymsIn = new In(hypernyms);
        int sourceVertex;
        while(!hypernymsIn.isEmpty()){
            String[] splitLine = hypernymsIn.readLine().split(",");
            sourceVertex = Integer.parseInt(splitLine[0]);
            for(int i = 1; i < splitLine.length; i++){
                    synsetGraph.addEdge(sourceVertex, Integer.parseInt(splitLine[i]));
            }
        }

        // check that graph has a single root
        int rootCount = 0;
        for(int i = 0; i < synsetGraph.V(); i++){
            if (synsetGraph.outdegree(i) == 0){
                rootCount++;
            }
        }
        if(rootCount != 1){
            throw new IllegalArgumentException();
        }

        sap = new SAP(synsetGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null){
            throw new IllegalArgumentException();
        }
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA == null || nounB == null){
            throw new IllegalArgumentException();
        }
        return sap.length(setMap.get(nounA), setMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null){
            throw new IllegalArgumentException();
        }
        return sets.get(sap.ancestor(setMap.get(nounA), setMap.get(nounB)));
    }


    private void printSynsetMap(){
        for(String noun : setMap.keys()){
            System.out.printf("%s :", noun);
            for(Integer i : setMap.get(noun)){
                System.out.printf("%d, ", i);
            }
            System.out.println();
        }
    }
    // do unit testing of this class
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        WordNet wn = new WordNet(args[0], args[1]);
        long endTime = System.currentTimeMillis();
        wn.printSynsetMap();
        System.out.println(endTime-startTime);
    }
}