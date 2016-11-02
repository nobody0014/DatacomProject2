/**
 * Created by Wit on 11/1/2016 AD.
 */
public class Counter {
    int c = 0;
    int max;
    Counter(int max){
        this.max  = max;
    }
    synchronized void increment(){
        c++;
    }
    synchronized void decrement(){
        c--;
    }
    synchronized int getC(){
        return c;
    }
    boolean isFull(){
        return c>=max;
    }
    boolean isEmpty(){
        return c<=0;
    }
}
