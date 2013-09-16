package ourObjects;

public class Muscle extends Spring {
	private double myAmplitude, myInitialRestLength;
	private int mySteps;
	
	public Muscle(String animId, Mass m1, Mass m2) {
		super(animId, m1, m2);
		mySteps = 0;
	}
	public void setAmplitude(double amp) {
		myAmplitude = amp;
	}
	
	@Override
	public void move() {
		doHarmonicMotion();
	}
	
	private void doHarmonicMotion() {
		mySteps++;
		double freq = 0.005;
		setRestLength(myInitialRestLength + myAmplitude * 
				Math.sin(2 * Math.PI * freq * mySteps));
	}
}
