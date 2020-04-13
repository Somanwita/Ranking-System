
public class matchDetails {
    private team team;
    private team opponents;
    private int totalNoMatchesPlayed;
    private int noMatchesLost;
    private int noDrawMatches;
    
    public matchDetails(team team, team opponents, int totalNoMatchesPlayed, int noMatchesLost, int noDrawMatches) {
		super();
		this.team = team;
		this.opponents = opponents;
		this.totalNoMatchesPlayed = totalNoMatchesPlayed;
		this.noMatchesLost = noMatchesLost;
		this.noDrawMatches = noDrawMatches;
	}
    
    public team getTeam() {
        return team;
    }
    public void setTeam(team team) {
        this.team = team;
    }
    public team getOpponents() {
        return opponents;
    }
    public void setOpponents(team opponents) {
        this.opponents = opponents;
    }

	public int getTotalNoMatchesPlayed() {
        return totalNoMatchesPlayed;
    }
    public void setTotalNoMatchesPlayed(int totalNoMatchesPlayed) {
        this.totalNoMatchesPlayed = totalNoMatchesPlayed;
    }
    public int getNoMatchesLost() {
        return noMatchesLost;
    }
    public void setNoMatchesLost(int noMatchesLost) {
        this.noMatchesLost = noMatchesLost;
    }
    public int getNoDrawMatches() {
        return noDrawMatches;
    }
    public void setNoDrawMatches(int noDrawMatches) {
        this.noDrawMatches = noDrawMatches;
    }
    

 

}