public class Driver
{
	public static void main(String args[]){
		peerProcess peer1 = new peerProcess(1001);
		peerProcess peer2 = new peerProcess(1002);
		peerProcess peer3 = new peerProcess(1003);
		Thread t1 = new Thread(peer1);
		Thread t2 = new Thread(peer2);
		Thread t3 = new Thread(peer3);
		t1.start();
		t2.start();
		t3.start();
	}
}