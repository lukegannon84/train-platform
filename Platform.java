import java.util.concurrent.locks.*;
class Platform{
    private int max=100;
    private int count=0;
    private boolean open=true;  
    private Lock lock = new ReentrantLock();
    private Condition notFull=lock.newCondition();
      
    public void entry(){
        lock.lock();
        try{
            while(count==max||!open){
                try{
              
                    notFull.await();
                                      
                }
        catch(InterruptedException e){}
        }
        count++;        
              
    }
    finally{
    lock.unlock();
    }
  
  
    }
    public void open(){open=true;}
      
    public void close(){open=false;}
      
  
      
      
    public void leave(){
        lock.lock();
        try{
            count--;
            if(count==max-1)
                notFull.signal();
                 this.close(); 
        }finally{
            lock.unlock();
        }
    }
      
}
  
class Vistor extends Thread{
    Platform person;
    int num;
    
    public Vistor(Platform p,int j){
        person=p;
        num=j;
    }
      
    public void run(){
          
        person.entry();
        
        try{
        	for(int t=0;t<150;t++)  Thread.sleep(t);
        }
        catch(InterruptedException e){
        }
        person.leave();
        
          
    }
}
  
class PlatformTest{
    public static void main(String[]args){
        Platform p=new Platform();
        for(int j=0;j<120;j++)new Vistor(p,j).start();
    }
}