package net.hogerheijde.nodeping.domain;

public class Server {
	private final String name;
	private ServerState state;
	
	public Server(String name) {
		this.name = name;
		state = ServerState.PENDING;
	}
	
	public void setState(ServerState state) {
		this.state = state;
	}
	
	public ServerState getState() {
		return state;
	}
	public String getName() {
		return name;
	}
	
}
