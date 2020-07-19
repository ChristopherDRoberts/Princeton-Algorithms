import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head; 
    private Node tail;
    private int queueLength;

    // construct an empty deque
    public Deque(){
        head = null;
        tail = null;
        queueLength = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return (head == null);
    }

    // return the number of items on the deque
    public int size(){
        return queueLength;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = head;
        newNode.prev = null;
        if (isEmpty()){
            head = newNode;
            tail = head;
        } else {
            head.prev = newNode;
            head = newNode;
        }
        queueLength++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = tail;
        if(isEmpty()){
            tail = newNode;
            head = tail;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        queueLength++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(!isEmpty()){
            Item item = head.item;
            head = head.next;
            if(head != null){
                head.prev = null;
            } else {
                tail = null;
            }
            queueLength--;
            return item;
        } else{
            throw new NoSuchElementException();
        }
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(!isEmpty()){
            Item item = tail.item;
            tail = tail.prev;
            if (tail != null){
                tail.next = null;
            } else {
                head = null;
            }
            queueLength--;
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class Node{

        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item>{

        private Node currentNode = head;

        public boolean hasNext(){
            return (currentNode != null);
        }

        public Item next(){
            if(hasNext()){
                Item item = currentNode.item;
                currentNode = currentNode.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> deq = new Deque<Integer>();
        System.out.println(deq.isEmpty());
        deq.addFirst(1);
        deq.addFirst(2);
        deq.addLast(3);
        deq.addLast(4);
        System.out.println(deq.size());
        for(Integer i : deq){
            System.out.println(i);
        }
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        return;
    }

}