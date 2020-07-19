import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int queueLength;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue(){
        items = (Item[]) new Object[1];
        queueLength = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return queueLength == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return queueLength;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(queueLength == items.length){
            resize(2*queueLength);
        }
        items[queueLength++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if(!isEmpty()){
            int i = StdRandom.uniform(0, queueLength);
            Item item = items[i];
            items[i] = items[queueLength - 1];
            items[queueLength - 1] = null;
            queueLength--;
            if((queueLength > 0) && (queueLength == items.length/4)){
                resize(items.length/2);
            }
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(!isEmpty()){
            int i = StdRandom.uniform(0, queueLength);
            return items[i];
        } else {
            throw new NoSuchElementException();
        }   
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>{

        private Item[] iteratorItems;
        private int idx;


        public RandomizedQueueIterator(){
            iteratorItems = (Item[]) new Object[queueLength];
            for(int i = 0; i < queueLength; i++){
                iteratorItems[i] = items[i];
            }
            StdRandom.shuffle(iteratorItems);
            idx = 0;
        }

        public boolean hasNext(){
            return idx < iteratorItems.length;
        }

        public Item next(){
            if(hasNext()){
                return iteratorItems[idx++];
            } else {
                throw new NoSuchElementException();
            }
            
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for(int i = 0; i < queueLength; i++){
            newItems[i] = items[i];
        }
        items = newItems;
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        System.out.println("==============================");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());
        System.out.println("==============================");
        System.out.println(rq.sample());
        System.out.println("==============================");
        for(int i : rq){
            for (int j : rq){
                System.out.printf("%d,%d ",i,j);
            }
            System.out.println();
        }
        System.out.println("==============================");
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println("==============================");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());
    }

}