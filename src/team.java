public class team {
	private String teamName;
	//private int noOfMatchesWon;
	//private int noOfGoals;
	//private boolean homeGround;
	private int totalNoOfMatches;
	private int totalmerginOfVictory;
	private int curmerginOfVictory;
	private int avgmarginOfVictory;
	private int maxmarginOfVictory;
	private int totalmerginOfLoss;
	private int curmarginOfLoss;
	private int avgmarginOfLoss;
	private int maxmarginOfLoss;

	public team() {
		super();
	}
	

	public team(String teamName) {
		super();
		this.teamName = teamName;
	}


	public team(String teamName, int curmerginOfVictory, int curmarginOfLoss) {
		super();
		this.teamName = teamName;
		this.curmerginOfVictory = curmerginOfVictory;
		this.curmarginOfLoss = curmarginOfLoss;
	}
	
	public void addValue(int curmerginOfVictory, int curmarginOfLoss) {
		totalNoOfMatches += 1;
		
		totalmerginOfVictory += curmerginOfVictory;
		avgmarginOfVictory = totalmerginOfVictory/totalNoOfMatches;
		maxmarginOfVictory = Math.max(maxmarginOfVictory, curmerginOfVictory);
		
		totalmerginOfLoss += curmarginOfLoss;
		avgmarginOfLoss = curmarginOfLoss/totalNoOfMatches;
		maxmarginOfLoss = Math.max(maxmarginOfLoss, curmarginOfLoss);
	}


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public int getTotalNoOfMatches() {
		return totalNoOfMatches;
	}


	public void setTotalNoOfMatches(int totalNoOfMatches) {
		this.totalNoOfMatches = totalNoOfMatches;
	}


	public int getTotalmerginOfVictory() {
		return totalmerginOfVictory;
	}


	public void setTotalmerginOfVictory(int totalmerginOfVictory) {
		this.totalmerginOfVictory = totalmerginOfVictory;
	}


	public int getCurmerginOfVictory() {
		return curmerginOfVictory;
	}


	public void setCurmerginOfVictory(int curmerginOfVictory) {
		this.curmerginOfVictory = curmerginOfVictory;
	}


	public int getAvgmarginOfVictory() {
		return avgmarginOfVictory;
	}


	public void setAvgmarginOfVictory(int avgmarginOfVictory) {
		this.avgmarginOfVictory = avgmarginOfVictory;
	}


	public int getMaxmarginOfVictory() {
		return maxmarginOfVictory;
	}


	public void setMaxmarginOfVictory(int maxmarginOfVictory) {
		this.maxmarginOfVictory = maxmarginOfVictory;
	}


	public int getTotalmerginOfLoss() {
		return totalmerginOfLoss;
	}


	public void setTotalmerginOfLoss(int totalmerginOfLoss) {
		this.totalmerginOfLoss = totalmerginOfLoss;
	}


	public int getCurmarginOfLoss() {
		return curmarginOfLoss;
	}


	public void setCurmarginOfLoss(int curmarginOfLoss) {
		this.curmarginOfLoss = curmarginOfLoss;
	}


	public int getAvgmarginOfLoss() {
		return avgmarginOfLoss;
	}


	public void setAvgmarginOfLoss(int avgmarginOfLoss) {
		this.avgmarginOfLoss = avgmarginOfLoss;
	}


	public int getMaxmarginOfLoss() {
		return maxmarginOfLoss;
	}


	public void setMaxmarginOfLoss(int maxmarginOfLoss) {
		this.maxmarginOfLoss = maxmarginOfLoss;
	}




}
