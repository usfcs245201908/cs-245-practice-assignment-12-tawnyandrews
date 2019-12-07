import java.util.ArrayList;

public class Hashtable {

    int size;
    HashNode[] bucket;
    double thrashld = (float)0.5;
    int counter = 0;

    //this is a constructor
    public Hashtable(){
        size = 10000;
        bucket = new HashNode[size];
        counter = 0;
    }

    //this method checks to see if the said method contains the key 
    public boolean containsKey(String key) {
        return bucket[getHash(key)] != null;
    }

    //this method will retrieve the key of the designated value
    public String get(String key){
        HashNode head = bucket[getHash(key)];

        while(head != null){
            if (head.key == key){
                return head.value;
            } else {
                head = head.next;
            }
        }
        return null;
    }

    //this method will add in values based on key 
    public void put (String key, String value){
        HashNode head = bucket[getHash(key)];
        if (head == null){
            bucket[getHash(key)] = new HashNode(key, value);
        } else {
            while (head != null) {
                if (head.key.equals(key)) {
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket[getHash(key)];
            bucket[getHash(key)] = node;
        }
        counter++;
        if ((counter * 1.0) / size >= thrashld){
            increaseBucketSize();
        }
    }

    //This method will remove the said key-value pair
    public String remove(String key) throws Exception {
        HashNode head = bucket[getHash(key)];

        if (head != null) {
            if (head.key.equals(key)) {
                bucket[getHash(key)] = head.next;
                counter--;
                return head.value;
            } else {
                HashNode prev = head;
                HashNode curr;

                while (head.next != null) {
                    curr = head.next;
                    if (curr != null && curr.key == key) {
                        head.next = curr.next;
                        counter--;
                        return curr.value;
                    }
                }
                return head.next.value;
            }
        } else {
            throw new Exception();
        }
    }

    //this will retrieve the hashCode
    public int getHash(String key){
        return Math.abs(key.hashCode() % size);
    }
    private void increaseBucketSize(){
        HashNode[] temp = bucket;
        size = size *2;
        bucket = new HashNode[size];
        counter = 0;
        for(HashNode head : temp) {
            while(head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }
}