# Networking Midpoint Write Up
Bailey Anderson
Mitchell Artin
Parker Evora

We created a multithreaded program "Driver" which creates three peerProcess threads.  Each thread connects to the peers that have been
created prior and sends a hello message.  We are currently using strings as messages and need to implement the handshake and general 
message classes.  Our peer IDs are passed in on instantiation and variables are read for each peer from the PeerInfo.cfg file.  

We implemented threads by having the peerProcess class implement Runnable.  We realize that inside these threads we will probably 
implement a thread pooled server design to accomadate multiple file transfers.

Our Config class reads PeerInfo.cfg and parses the space deliminated values inside of it.  We are ingnoring the flags in this iteration 
of the project without issue.  We included a sample config file with our submission for three peers.