
public class team {
 private String teamName;
 private int noOfMatchesWon;
 private int totalNoOfMatches;
 private int noOfGoals;
 
 
public team(String teamName) {
	//super();
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
public int getTotalNoOfMatches() {
	return totalNoOfMatches;
}
public void setTotalNoOfMatches(int totalNoOfMatches) {
	this.totalNoOfMatches = totalNoOfMatches;
}
public int getNoOfGoals() {
	return noOfGoals;
}
public void setNoOfGoals(int noOfGoals) {
	this.noOfGoals = noOfGoals;
}
 
}
