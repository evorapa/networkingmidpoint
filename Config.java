import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Config {
	
	//init vars
	private final ArrayList<Integer> IDs;
	private final ArrayList<String> addresses;
	private final ArrayList<Integer> downloadPorts;
	private final int numPeers;

	//getters
	public int getNumPeers() {
		return numPeers;
	}

	public ArrayList<Integer> getDownloadPorts() {
		return downloadPorts;
	}

	public ArrayList<Integer> getIDs() {
		return IDs;
	}

	public ArrayList<String> getAddresses() {
		return addresses;
	}

	public Config(String peerInfo) throws FileNotFoundException {
		//open the file
		Scanner peerInfoScnr = new Scanner(new FileReader(peerInfo));
		
		//create array lists for each column of the config
		//ignoring flags for now
		IDs = new ArrayList<Integer>();
		addresses = new ArrayList<String>();
		downloadPorts = new ArrayList<Integer>();
		
		//loop through each line
		//x is our counter
		int x = 0;
		while (peerInfoScnr.hasNextLine()) {
			x++;

			//read line
			String s = peerInfoScnr.nextLine();
			//line is space deliminated
			String[] split = s.split(" ");
			//add each section to the array list
			this.IDs.add(Integer.parseInt(split[0]));
			this.addresses.add(split[1]);
			this.downloadPorts.add(Integer.parseInt(split[2]));
			
		}
		//each line was a peer
		this.numPeers=x;
		
	}
}