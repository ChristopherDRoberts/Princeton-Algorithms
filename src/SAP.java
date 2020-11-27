import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if(G == null){
            throw new IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if(v >= G.V() || w >= G.V()){
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        return findSap(bfsV, bfsW).length();
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if(v >= G.V() || w >= G.V()){
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        return findSap(bfsV, bfsW).ancestor();
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null){
            throw new IllegalArgumentException();
        }
        else if(!v.iterator().hasNext() || !w.iterator().hasNext()){
            return -1;
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        return findSap(bfsV, bfsW).length();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null){
            throw new IllegalArgumentException();
        }
        else if(!v.iterator().hasNext() || !w.iterator().hasNext()){
            return -1;
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        return findSap(bfsV, bfsW).ancestor();
    }

    private SapSummary findSap(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW){
        int dist = Integer.MAX_VALUE;
        int ancestor = 0;

        for(int i = 0; i < G.V(); i++){
            if(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                if((bfsV.distTo(i) + bfsW.distTo(i)) < dist){
                    ancestor = i;
                    dist = bfsV.distTo(i) + bfsW.distTo(i);
                }
            }
        }

        if(dist == Integer.MAX_VALUE){
            return new SapSummary(-1, -1);
        } else {
            return new SapSummary(dist, ancestor);
        }
    }

    private class SapSummary{
        private int length;
        private int ancestor;

        public SapSummary(int length, int ancestor){
            this.length = length;
            this.ancestor = ancestor;
        }

        public int length(){
            return length;
        }

        public int ancestor(){
            return ancestor;
        }
    }

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}