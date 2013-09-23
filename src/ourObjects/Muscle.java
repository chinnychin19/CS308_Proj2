package ourObjects;

public class Muscle extends Spring {
	private double myAmplitude, myAverageRestLength;
	private int mySteps;

	/**
	 * This class represents "muscles", which are springs with oscillating rest
	 * lengths. The frequency of these oscillations is a preset constant. By
	 * default amplitude is 0 and the average rest length is equivalent to the
	 * initial length between the two anchored masses.
	 * 
	 * @param animId
	 *            Used by the underlying engine
	 * @param m1
	 *            First anchored mass
	 * @param m2
	 *            Second anchored mass
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

	public void setAverageRestLength(double restLength) {
		myAverageRestLength = restLength;
	}

	private void doHarmonicMotion() {
		mySteps++;
		setRestLength(myAverageRestLength + Constants.AMPLITUDE_MULTIPLIER
				* myAmplitude
				* Math.sin(2 * Math.PI * Constants.MUSCLE_FREQUENCY * mySteps));
	}
}
