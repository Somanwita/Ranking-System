public class team {
	private String teamName;
	private int noOfMatchesWon;
	private int noOfGoals;
	private boolean homeGround;


	public team() {
		super();
	}
	

	public team(String teamName) {
		super();
		this.teamName = teamName;
	}


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public int getNoOfMatchesWon() {
		return noOfMatchesWon;
	}


	public void setNoOfMatchesWon(int noOfMatchesWon) {
		this.noOfMatchesWon = noOfMatchesWon;
	}


	public int getNoOfGoals() {
		return noOfGoals;
	}


	public void setNoOfGoals(int noOfGoals) {
		this.noOfGoals = noOfGoals;
	}


	public boolean isHomeGround() {
		return homeGround;
	}


	public void setHomeGround(boolean homeGround) {
		this.homeGround = homeGround;
	}

	



}
