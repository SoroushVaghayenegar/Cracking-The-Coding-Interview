package moderate;

public class PlankOfWood {
	public enum PlankType{
		Shorter, Longer;
	}
	
	public PlankType planktype;
	
	public PlankOfWood(PlankType pt){
		this.planktype = pt;
	}
	
	public String toString(){
		return planktype.toString();
	}
}
