package net.hogerheijde.nodeping.domain;

public enum ServerState {
	FAIL("fail"),
	PASS("pass");
	
	public final String label;
	
	ServerState(String label) {
		this.label = label;
	}
	
}
