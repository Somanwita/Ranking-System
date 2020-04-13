
public class RankingClass {
	team homeTeam;
	team awayTeam;
	String ftr;
	
	
	public RankingClass(team homeTeam, team awayTeam, String ftr) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.ftr = ftr;
	}
	
	public team getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(team homeTeam) {
		this.homeTeam = homeTeam;
	}
	public team getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(team awayTeam) {
		this.awayTeam = awayTeam;
	}
	public String getFtr() {
		return ftr;
	}
	public void setFtr(String ftr) {
		this.ftr = ftr;
	}

	@Override
	public String toString() {
		return "RankingClass [homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", ftr=" + ftr + "]";
	}
	
}
