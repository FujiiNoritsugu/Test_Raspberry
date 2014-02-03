package upload;

import java.io.Serializable;

public class UploadData implements Serializable {

	private static final long serialVersionUID = 6515920901661910948L;

	int pressure1;
	int pressure2;
	int pressure3;
	int pressure4;

	public int getPressure1() {
		return pressure1;
	}

	public void setPressure1(int pressure1) {
		this.pressure1 = pressure1;
	}

	public int getPressure2() {
		return pressure2;
	}

	public void setPressure2(int pressure2) {
		this.pressure2 = pressure2;
	}

	public int getPressure3() {
		return pressure3;
	}

	public void setPressure3(int pressure3) {
		this.pressure3 = pressure3;
	}

	public int getPressure4() {
		return pressure4;
	}

	public void setPressure4(int pressure4) {
		this.pressure4 = pressure4;
	}

}
