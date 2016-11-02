//import java.util.*;
//import java.io.*;
//import java.net.*;
//import java.util.concurrent.*;
//import org.apache.commons.*;
//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//
//public class Master111 {
//    private int[] categories;
//    private int maximumConcurrentRequests;
//    private long requestNumber;
//    private String domain;
//    private String path;
//    private int port = 80;
//
//
//    public Master111(String serverName, int maxCon, long reqNum){
//        setMaximumConcurrentRequest(maxCon);
//        setRequestNumber(reqNum);
//        setHostInformation(serverName);
//    }
//
//    public void begin(){
//        ArrayList<Worker> jobs = createJobs(requestNumber,maximumConcurrentRequests);
//        ExtendedPoolExecutor exe;
//        if(maximumConcurrentRequests/5 == 0){
//            exe = new ExtendedPoolExecutor(maximumConcurrentRequests,maximumConcurrentRequests,100,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(jobs.size()));
//        }else{
//            exe = new ExtendedPoolExecutor(5,5,100,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(jobs.size()));
//        }
//        exe.setUp((int)requestNumber/1000+1);
//        long start = System.currentTimeMillis();
//        String place = domain + path;
//        for(Worker r: jobs){
//            r.setConnectionInforamtion(new HttpHost(domain,port), new HttpGet(place));
//            exe.execute(r);
//            while(exe.getActiveCount() == exe.getMaximumPoolSize()){}
//        }
//        exe.shutdown();
//        while (!exe.isTerminated()){}
//        long end = System.currentTimeMillis();
//        benchmarking(jobs, end-start);
//    }
//
//    public ArrayList<Worker> createJobs(long reqNum, int maxCon){
//        long jobNum = reqNum/1000 + 1;
//        int mc = maxCon/5;
//        if(mc == 0){
//            mc = 1;
//        }
//        ArrayList<Worker> jobs = new ArrayList();
//        for(int i = 1; i < jobs.size(); i++){
//            jobs.add(new Worker(1000,mc,i));
//        }
//        jobs.add(new Worker((int)reqNum%1000,mc,jobs.size()));
//        return jobs;
//    }
//    public void benchmarking(ArrayList<Worker> jobs, long totalTime){
//        ArrayList<Long> timings = new ArrayList<>();
//        long[] peopleCat = new long[7];
//        long[] timing;
//        for(Worker w:  jobs){
//            timing = w.getTiming();
//            for(int i = 0; i < timing.length; i++){
//                if(timing[i] != -1){
//                    timings.add(timing[i]);
//                }
//                else{
//                    peopleCat[0]++;
//                }
//            }
//        }
//        Collections.sort(timings);
//        long maxTime = timings.get(timings.size()-1);
//        double[] category = new double[6];
//        category[0] = maxTime * 0.5;
//        category[1] = maxTime * 0.6;
//        category[2] = maxTime * 0.7;
//        category[3] = maxTime * 0.8;
//        category[4] = maxTime * 0.9;
//        category[5] = maxTime;
//        int c = 0;
//        int ci = 1;
//        int totalReqPassed = 0;
//        for(long i : timings){
//            if(i > category[c]){
//                ci++;
//                c++;
//                peopleCat[ci] += peopleCat[ci-1];
//            }
//            totalReqPassed++;
//            peopleCat[ci]++;
//        }
//        int avg = 0;
//        System.out.println("Time taken for tests: " + totalTime +"\n" + "Completed requests: " + totalReqPassed
//                + "\n" + "Failed requests: " + peopleCat[0] + "\nAvg requests per second: " + avg + "\n\n Percentage of the requests served within a certain time (ms)" +
//                "\n50%  " + peopleCat[0]+
//                "\n60%  " + peopleCat[1]+
//                "\n70%  " + peopleCat[2]+
//                "\n80%  " + peopleCat[3]+
//                "\n90%  " + peopleCat[4]+
//                "\n100%  " + peopleCat[5]);
//    }
//
//
//    public void setHostInformation(String host){
//        try{
//            URL u = RequestMaker.makeURL(host);
//            domain = u.getHost();
//            if(u.getPath() == null || u.getPath().equals("")){path = "/";}
//            else{path = u.getPath();}
//            if(u.getPort() != -1){port = u.getPort();}
//        }catch (Exception e){
//            System.out.println("Unaccetable host, Quiting program");
//            System.exit(0);
//        }
//    }
//
//    public void setRequestNumber(long reqNum){
//        requestNumber = reqNum;
//    }
//    public void setMaximumConcurrentRequest(int maxCon){
//        maximumConcurrentRequests = maxCon;
//    }
//    public long getRequestNumber(){
//        return requestNumber;
//    }
//    public String getDomain(){return domain;}
//    public String getPath(){return path;}
//    public int getPort(){return port;}
//    public int getMaximumConcurrentRequest(){
//        return maximumConcurrentRequests;
//    }
//}
