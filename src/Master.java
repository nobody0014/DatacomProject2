import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import org.apache.http.client.methods.HttpGet;

public class Master {
	private int[] categories;
	private int maximumConcurrentRequests;
	private long requestNumber;
	private String host;
	private String domain;
	private String path;
	private int port = 80;
	private HttpGet gt;

	public Master(String serverName, int maxCon, long reqNum){
		host = serverName;
		setMaximumConcurrentRequest(maxCon);
		setRequestNumber(reqNum);
		setHostInformation(serverName);
	}
	
	public void begin() {
		long start = System.currentTimeMillis();
		ArrayList<Worker> jobs = createJobs(requestNumber,maximumConcurrentRequests);
        ExtendedPoolExecutor exe;
        if(maximumConcurrentRequests/5 == 0){
            exe = new ExtendedPoolExecutor(1,1,100,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(jobs.size()));
        }else{
            exe = new ExtendedPoolExecutor(5,5,100,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(jobs.size()));
        }
		exe.createClients();
		HttpGet g = new HttpGet(host);
        for(Worker r: jobs){
            r.setConnectionInformation(g,exe.removeClient());
            exe.execute(r);
			while (!exe.isAvailable()){
				try {
					Thread.sleep(90);
				}catch(Exception e){}
			}
        }
        exe.shutdown();
        while (!exe.isTerminated()){
			try {
				Thread.sleep(90);
			}catch(Exception e){}
		}
		exe.closeConnections();
		long end = System.currentTimeMillis();
        benchmarking(jobs, end-start);
	}


	public ArrayList<Worker> createJobs(long reqNum, int maxCon){
		long jobNum = reqNum/1000 + 1;
		int mc = maxCon/5;
		if(mc == 0){
			mc = maxCon;
		}
		ArrayList<Worker> jobs = new ArrayList();
		for(int i = 1; i < jobNum; i++){
			jobs.add(new Worker(1000,mc,i));
		}
		jobs.add(new Worker((int)reqNum%1000,mc, (int)jobNum));
		return jobs;
	}


	public void benchmarking(ArrayList<Worker> jobs, long totalTime){
		ArrayList<Long> timings = new ArrayList<>();
		long[] peopleCat = new long[7];
		long[] timing;
		for(Worker w:  jobs){
			timing = w.getTiming();
			for(int i = 0; i < timing.length; i++){
				if(timing[i] != -1){
					timings.add(timing[i]);
				}
				else{
					peopleCat[0]++;
				}
			}
		}
		Collections.sort(timings);

		long maxTime = timings.get(timings.size()-1);
		int totalReqPassed = timings.size();
		int cat = 0;
		for(int i = 0; i < totalReqPassed; i++){
			if(cat == 0 && i >= totalReqPassed/2.0){
				peopleCat[1] = timings.get(i);
				cat++;
			}
			else if(cat == 1 && i >= totalReqPassed * 60.0 / 100.0){
				peopleCat[2] = timings.get(i);
				cat++;
			}
			else if(cat == 2 && i >= totalReqPassed * 70.0 / 100.0){
				peopleCat[3] = timings.get(i);
				cat++;
			}
			else if(cat == 3 && i >= totalReqPassed * 80.0 / 100.0){
				peopleCat[4] = timings.get(i);
				cat++;
			}
			else if(cat == 4 && i >= totalReqPassed * 90.0 / 100.0){
				peopleCat[5] = timings.get(i);
				cat++;
			}
		}
		peopleCat[6] = timings.get(timings.size()-1);
		double totalTimeInS = totalTime/1000.0;
		double avg = totalReqPassed/(totalTimeInS);
		System.out.println("\nTime taken for tests: " + totalTimeInS +" s\n" + "Completed requests: " + totalReqPassed
		+ "\n" + "Failed requests: " + peopleCat[0] + "\nAvg requests per second: "
 		+ avg + " s\n\nPercentage of the requests served within a certain time (ms)" +
				"\n50%  " + peopleCat[1]+
				"\n60%  " + peopleCat[2]+
				"\n70%  " + peopleCat[3]+
				"\n80%  " + peopleCat[4]+
				"\n90%  " + peopleCat[5]+
				"\n100%  " + peopleCat[6]);
	}

	public void setHostInformation(String host){
		try{
			URL u = RequestMaker.makeURL(host);
			domain = u.getHost();
			if(u.getPath() == null || u.getPath().equals("")){path = "/";}
			else{path = u.getPath();}
			if(u.getPort() != -1){port = u.getPort();}
		}catch (Exception e){
			System.out.println("Unaccetable host, Quiting program");
			System.exit(0);
		}
	}

	public void setRequestNumber(long reqNum){
		requestNumber = reqNum;
	}
	public void setMaximumConcurrentRequest(int maxCon){
		maximumConcurrentRequests = maxCon;
	}
	public long getRequestNumber(){
		return requestNumber;
	}
	public String getDomain(){return domain;}
	public String getPath(){return path;}
	public int getPort(){return port;}
	public int getMaximumConcurrentRequest(){
		return maximumConcurrentRequests;
	}
}
