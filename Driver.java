public class Driver
{
	public static void main(String args[]){
		Peer peer1 = new Peer(1001);
		Peer peer2 = new Peer(1002);
		Peer peer3 = new Peer(1003);
		Thread t1 = new Thread(peer1);
		Thread t2 = new Thread(peer2);
		Thread t3 = new Thread(peer3);
		t1.start();
		t2.start();
		t3.start();
	}
}