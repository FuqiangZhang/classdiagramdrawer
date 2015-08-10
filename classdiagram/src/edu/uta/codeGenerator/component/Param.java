package edu.uta.codeGenerator.component;

public class Param  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160636133114262490L;
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Param [type=" + type + ", name=" + name
				+ "]";
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Param otherParam = (Param) obj;
		if (otherParam.getType().equalsIgnoreCase(type))
			return true;
		return false;
	}

	private String type;
	private String name;
}
