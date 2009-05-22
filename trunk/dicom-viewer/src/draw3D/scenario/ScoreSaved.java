package draw3D.scenario;

public class ScoreSaved {

	private float score=0;
	private String name="NN";
	
	public ScoreSaved() {
	}
	
	

	public ScoreSaved(float score, String name) {
		super();
		this.score = score;
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
}
