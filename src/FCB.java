import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

/**
 * Created by Wit on 11/1/2016 AD.
 */
public class FCB implements FutureCallback<HttpResponse> {
    long start = System.currentTimeMillis();
    long end;
    Counter cc;
    FCB(Counter c){
        this.cc = c;
    }
    @Override
    public void completed(HttpResponse httpResponse) {
        end = System.currentTimeMillis();
        cc.decrement();
    }
    @Override
    public void failed(Exception e) {
        start = 0;
        end = -1;
        cc.decrement();
    }
    @Override
    public void cancelled() {
        start = 0;
        end = -1;
        cc.decrement();
    }
    public long getTiming(){
        return end - start;
    }
}
