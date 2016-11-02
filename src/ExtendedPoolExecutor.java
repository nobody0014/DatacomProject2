import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.util.*;
import java.util.concurrent.*;


public class ExtendedPoolExecutor extends ThreadPoolExecutor {
	ArrayList<CloseableHttpAsyncClient> clientManager = new ArrayList<>();

	public ExtendedPoolExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		Worker w = (Worker) r;
		addClient(w.getClient());
	}
	public boolean isAvailable(){
		return clientManager.size()>0;
	}
	public void createClients(){
		for(int i = 0; i < getCorePoolSize();i++){
			clientManager.add(HttpAsyncClients.createDefault());
		}
	}
	synchronized public CloseableHttpAsyncClient removeClient(){
		return clientManager.remove(0);
	}
	synchronized public void addClient(CloseableHttpAsyncClient c){
		clientManager.add(c);
	}
	public void closeConnections(){
		for(CloseableHttpAsyncClient c : clientManager){
			try{
				c.close();
			}catch(Exception e){}
		}
	}

}
