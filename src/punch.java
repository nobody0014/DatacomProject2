import java.util.*;
import java.io.*;
import java.net.*;

public class punch {
	public static void main(String[] args){
		checkArgs(args);
		Master m = new Master(args[args.length-1], Integer.parseInt(args[3]), Long.parseLong(args[1]));

//		Master m = new Master("http://10.27.8.20:8080/",200,50000);
		try{
			m.begin();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void checkArgs(String[] args){
		if(args[0].equals("-n")){
			try{
				int reqNum =  Integer.parseInt(args[1]);
			}
			catch(Exception e){
				System.out.println("Incorrect input");
				System.exit(0);
			}
		}
		if(args[2].equals("-c")){
			try{
				int maxCon =  Integer.parseInt(args[3]);
			}
			catch(Exception e){
				System.out.println("Incorrect input");
				System.exit(0);
			}
		}
		else{
			System.out.println("Incorrect input");
			System.exit(0);
		}
	}
}
