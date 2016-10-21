import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Config {
	
	//peer info
	private final ArrayList<Integer> IDs;
	private final ArrayList<String> addresses;
	private final ArrayList<Integer> downloadPorts;
	private final ArrayList<Boolean> flags;
	
	private final int numPeers;

	public int getNumPeers() {
		return numPeers;
	}

	
	public int getDownloadPort(int index) {
		return downloadPorts.get(index);
	}


	public ArrayList<Integer> getIDs() {
		return IDs;
	}


	public ArrayList<String> getAddresses() {
		return addresses;
	}


	public Config(String peerInfo) throws FileNotFoundException {
		Scanner peerInfoScnr = new Scanner(new FileReader(peerInfo));
		
		IDs = new ArrayList<Integer>();
		addresses = new ArrayList<String>();
		downloadPorts = new ArrayList<Integer>();
		flags = new ArrayList<Boolean>();
		
		int count = 0;
		while (peerInfoScnr.hasNextLine()) {
			count++;

			String s = peerInfoScnr.nextLine();
			String[] split = s.split(" ");
			this.IDs.add(Integer.parseInt(split[0].trim()));
			this.addresses.add(split[1].trim());
			this.downloadPorts.add(Integer.parseInt(split[2].trim()));
			if (split[3].trim().equals("1")) {
				this.flags.add(true);
			} else {
				this.flags.add(false);
			}
			
		}
		
		this.numPeers = count;
		
	}
}