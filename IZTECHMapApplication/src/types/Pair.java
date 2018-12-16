package types;
public class Pair {
	private int id1;
	private int id2;
	
	public Pair(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}
	
	public int getMaxId() {
		if(id1 > id2) {
			return id1;
		} else {
			return id2;
		}
	}
	
	public boolean equals(Pair pair) {
		if(((getId1() == pair.getId1()) && (getId2() == pair.getId2())) || ((getId1() == pair.getId2()) && (getId2() == pair.getId1()))) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if(other.equals(this)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String str = getId1() + " <--> " + getId2();
		return str;
	}
}