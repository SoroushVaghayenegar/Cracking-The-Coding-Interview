package hard;

public class DocumentPair {
	private int ID1;
	private int ID2;
	private double similarity;
	
	public DocumentPair(int ID1, int ID2, double similarity){
		this.ID1 = ID1;
		this.ID2 = ID2;
	}

	public int getID1() {
		return ID1;
	}

	public void setID1(int iD1) {
		ID1 = iD1;
	}

	public int getID2() {
		return ID2;
	}

	public void setID2(int iD2) {
		ID2 = iD2;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	
	public String toString(){
		return ID1 + ", " + ID2 + "   : " + similarity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID1;
		result = prime * result + ID2;
		long temp;
		temp = Double.doubleToLongBits(similarity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentPair other = (DocumentPair) obj;
		if (ID1 != other.ID1 && ID2 != other.ID2 || ID1 != other.ID2 && ID2 != other.ID1)
			return false;
		if (Double.doubleToLongBits(similarity) != Double.doubleToLongBits(other.similarity))
			return false;
		return true;
	}
	
	
}
