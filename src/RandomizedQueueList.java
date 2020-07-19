import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueueList<Item> implements Iterable<Item> {

    private Node head;
    private int queueLength;

    // construct an empty randomized queue
    public RandomizedQueueList(){
        queueLength = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return (head == null);
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
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = head;
        head = newNode;
        queueLength++;
    }

    // remove and return a random item
    public Item dequeue(){
        if(!isEmpty()){
            Node prevNode = null;
            Node node = head;
            int idx = randomIdx();
            int i = 0;
            while(i < idx){
                prevNode = node;
                node = node.next;
                i++;
            }
            Item item;
            if(node == head){
                item = node.item;
                head = node.next;
                node.next = null;
            } else {
                item = node.item;
                prevNode.next = node.next;
                node.next = null;
            }
            queueLength--;
            return item;
        } else {
            throw new NoSuchElementException();   
        }
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(!isEmpty()){
            Node node = head;
            int idx = randomIdx();
            int i = 0;
            while(i < idx){
                node = node.next;
                i++;
            }
            return node.item;
        } else {
            throw new NoSuchElementException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private class Node {
        Item item;
        Node next;
    }

    private class RandomizedQueueIterator implements Iterator<Item>{
        
        private Item[] items;
        private int itemIdx;
        private int itemsCount;
        
        public RandomizedQueueIterator(){
            itemIdx = 0;
            itemsCount = queueLength;
            items = (Item[]) new Object[queueLength];
            Node node = head;
            int i = 0;
            while(node != null){
                items[i] = node.item;
                i++;
                node = node.next;
            }
            StdRandom.shuffle(items);
        }

        public boolean hasNext(){
            return itemIdx < itemsCount;
        }

        public Item next(){
            if(hasNext()){
                return items[itemIdx++];
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }        
    }

    private int randomIdx(){
        return StdRandom.uniform(0, queueLength);
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueueList<Integer> rq = new RandomizedQueueList<Integer>();
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        System.out.println(rq.sample());
        System.out.println(rq.dequeue());
        for (int i : rq){
            System.out.println(i);
        }
    }

}
