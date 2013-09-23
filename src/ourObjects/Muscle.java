package ourObjects;

public class Muscle extends Spring {
	private double myAmplitude, myAverageRestLength;
	private int mySteps;

	/**
	 * This class represents "muscles", which are springs with oscillating rest lengths.
	 * The frequency of these oscillations is a preset constant. By default amplitude is 0 and
	 * @param animId
	 * @param m1
	 * @param m2
	 */
	public Muscle(String animId, Mass m1, Mass m2) {
		super(animId, m1, m2);
		mySteps = 0;
		myAmplitude = 0;
		myAverageRestLength = getRestLength();
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
		myAverageRestLength = restLength;
	}

	private void doHarmonicMotion() {
		mySteps++;
		setRestLength(myAverageRestLength + Constants.AMPLITUDE_MULTIPLIER
				* myAmplitude
				* Math.sin(2 * Math.PI * Constants.MUSCLE_FREQUENCY * mySteps));
	}
}
