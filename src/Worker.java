import org.apache.http.client.methods.HttpGet;

import org.apache.http.*;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import java.util.*;


public class Worker implements Runnable {


	private long[] timing;

	private int maximumConcurrentRequests;
	private int requestNumber;
    private int id;

	private CloseableHttpAsyncClient client;
	private HttpGet req;

	public Worker(int reqNum, int maxCon, int id){
        this.id = id;
		setRequestNumber(reqNum);
		setMaximumConcurrentRequests(maxCon);
	}
	public void setConnectionInformation(HttpGet req, CloseableHttpAsyncClient asc){
		this.req = req;
        client = asc;
	}
	public void run() {
        client.start();
        timing = new long[requestNumber];
        int c =  0;
        Counter counter = new Counter(maximumConcurrentRequests);
        ArrayList<FCB> fall = new ArrayList<>();
//        ArrayList<Future<HttpResponse>> fra = new ArrayList<>();
        while (c < requestNumber){
            FCB f = new FCB(counter);
            fall.add(f);
            client.execute(req, f);
//            fra.add(client.execute(req, f));
            c++;
            counter.increment();
            while (counter.isFull()){
                try {
                    Thread.sleep(5);
                }catch(Exception e){}
            }
        }
        while (!counter.isEmpty()){
            try {
                Thread.sleep(5);
            }catch(Exception e){}
        }
        int i = 0;
        for( FCB f  : fall){
            timing[i] = f.getTiming();
            i++;
        }
//        for(Future<HttpResponse> f : fra){
//            try{
//                System.out.println(f.get());
//            }catch(Exception e){e.printStackTrace();}
//        }
//        System.out.println("Hello");
	}

	public void setRequestNumber(int reqNum){
		this.requestNumber = reqNum;
	}


	public void setMaximumConcurrentRequests(int maxCon){
		this.maximumConcurrentRequests = maxCon;
	}

	public long[] getTiming(){return timing;}
    public CloseableHttpAsyncClient getClient(){
        return client;
    }
    public int getID(){
        return id;
    }
}
