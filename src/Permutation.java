import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        int outputStringCount;
        outputStringCount = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            rq.enqueue(StdIn.readString());
        }

        int j = 0;
        Iterator<String> it = rq.iterator();
        while(it.hasNext() && (j < outputStringCount)){
            System.out.println(it.next());
            j++;
        }
    }
}