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
		super.move();
	}

	public void setInitialRestLength(double restLength) {
		myInitialRestLength = restLength;
	}

	private void doHarmonicMotion() {
		mySteps++;
		setRestLength(myInitialRestLength + Constants.AMPLITUDE_MULTIPLIER
				* myAmplitude
				* Math.sin(2 * Math.PI * Constants.MUSCLE_FREQUENCY * mySteps));
	}
}
