import org.apache.commons.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

import java.util.*;
import java.io.*;
//import java.net.*;
//
//
//public class Worker111 implements Runnable {
//
//    private long[] startTime;
//    private long[] endTime;
//    private long[] timing;
//    private long totalTime;
//    private long[] category;
//    private String domain;
//    private String path;
//
//    private int port;
//
//    private int maximumConcurrentRequests;
//    private int requestNumber;
//    private int currentStartRequest;
//    private int currentEndResponse;
//    private int currentRunningNumber;
//    private int ID;
//
//
//    public Worker111(String domain, String path, int port, int reqNum, int maxCon, int ID){
//        this.ID = ID;
//        setHostInformation(domain, path, port);
//        setRequestNumber(reqNum);
//        setMaximumConcurrentRequests(maxCon);
//    }
//
//    public void run() {
//
//
////		Socket client  = connect(getDomain(),getPort());
////		if(client != null){
////			beginDownload(client);
////		}
////		close(client);
//    }
//    public void beginDownload(Socket client){
//
//
//
////		int reqCounter = 0;
////		DataOutputStream dOut = makeDataOutputStream(client);
////		DataInputStream dIn = makeDataInputStream(client);
////		byte[] req = RequestMaker.makeGETReq(getPath(), getDomain());
////		while(reqCounter < getRequestNumber()){
////			sendRequest(dOut,req);
////			download(dIn);
////			reqCounter++;
////		}
//    }
//
//    public void sendRequest(DataOutputStream dOut, byte[] request){
//        while(getCurrentRunningNumber() <= getMaximumConcurrentRequests()){
//            try{
//                dOut.write(request);
//                getStartTime()[getCurrentStartRequest()] = System.currentTimeMillis();
//                incrementCurrentStartRequest();
//                incrementCurrentRunningNumber();
//            }catch(Exception e){}
//        }
//    }
//
//    public void download(DataInputStream dIn){
//        try{
//            byte[] inpt = new byte[8192];
//            StringBuilder str = new StringBuilder();
//            while(true){
//                int a = dIn.read(inpt);
//
//            }
//        }
//        catch(Exception e){}
//    }
//
//    public long evaluateTiming(long start, long end){
//        long timing = end - start;
//        return timing;
//    }
//    public void evaluateRequestPerformance(long[] start, long[] end){
//        long[] timing = new long[start.length];
//        for(int i = 0; i < start.length; i++){
//            timing[i] = end[i] - start[i];
//        }
//    }
//
//    //
//    public void incrementCurrentStartRequest(){
//        currentStartRequest++;
//    }
//    public void decrementCurrentStartRequest(){
//        currentStartRequest--;
//    }
//    public void incrementCurrentEndResponse(){
//        currentEndResponse++;
//    }
//    public void decrementCurrentEndresponse(){
//        currentEndResponse--;
//    }
//    public void incrementCurrentRunningNumber(){
//        currentRunningNumber++;
//    }
//    public void decrementCurrentRunningNumber(){
//        currentRunningNumber--;
//    }
//
//
//
//    //
//    public void setRequestNumber(int reqNum){
//        this.requestNumber = reqNum;
//    }
//    public void setTotalTime(long time){
//        this.totalTime = time;
//    }
//    public void setHostInformation(String domain, String path, int port){
//        this.domain = domain;
//        this.path = path;
//        this.port = port;
//    }
//    public void setMaximumConcurrentRequests(int maxCon){
//        this.maximumConcurrentRequests = maxCon;
//    }
//    public void setStartTimeSlot(int slot, long time){
//        startTime[slot] = time;
//    }
//    public void setEndTimeSlot(int slot, long time){
//        endTime[slot] = time;
//    }
//    public long startTimeSlot(int slot){
//        return startTime[slot];
//    }
//    public long startEndSlot(int slot){
//        return endTime[slot];
//    }
//
//
//
//    //
//    public long[] getTiming(){return timing;}
//    public long[] getStartTime(){
//        return startTime;
//    }
//    public long[] getEndTime(){
//        return endTime;
//    }
//    public long[] getCategory(){
//        return category;
//    }
//    public long getTotalTime(){
//        return totalTime;
//    }
//    public String getDomain(){
//        return domain;
//    }
//    public String getPath(){
//        return path;
//    }
//    public int getPort(){
//        return port;
//    }
//    public int getCurrentStartRequest(){
//        return currentStartRequest;
//    }
//    public int getCurrentEndResponse(){
//        return currentEndResponse;
//    }
//    public int getRequestNumber(){
//        return requestNumber;
//    }
//    public int getMaximumConcurrentRequests(){
//        return maximumConcurrentRequests;
//    }
//    public int getCurrentRunningNumber(){
//        return currentRunningNumber;
//    }
//
//
//
//
//    //
//    public Socket connect(String domain, int port){
//        Socket client;
//        try{
//            client = new Socket();
//            client.connect(new InetSocketAddress(domain, port), 3000);
//            client.setSoTimeout(5000);
//            return client;
//        }catch(Exception e){
//            System.out.println("Unable to connect.");
//        }
//        return null;
//    }
//    public DataOutputStream makeDataOutputStream(Socket client){
//        try{
//            return new DataOutputStream(client.getOutputStream());
//        }catch(Exception e){}
//        return null;
//    }
//    public DataInputStream makeDataInputStream(Socket client){
//        try{
//            return new DataInputStream(client.getInputStream());
//        }catch(Exception e){}
//        return null;
//    }
//
//    public void close(Socket client){
//        try{
//            client.close();
//        }catch(Exception e){
//            //Dont care
//
//        }
//    }
//}
