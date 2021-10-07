import javafx.scene.control.Button;
import javafx.util.Pair;

public class GameButton extends Button {
	private Pair<Integer, Integer> index;
	
	public Pair<Integer, Integer> getIndex() {
		return index;
	}
	public void setIndex(Pair<Integer, Integer> index) {
		this.index = index;
	}
	
}
