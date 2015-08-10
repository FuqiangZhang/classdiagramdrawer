package edu.uta.codeGenerator.component;


public class DCDAttribute extends DCDComponent {
	
	private String type = "";
	
	public DCDAttribute() {
		this.height = this.nameHeight ;
	}

	public String getType() {
		return type;
	}

	// set type for not primary type
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public DCDComponent getSelectedComponent(int x_in, int y_in, int depth) {
		System.out.println(x_in+"/"+y_in + "==>" + this.getxPos() + "/" + this.getyPos());
		System.out.println(this.isInsect(x_in, y_in +15));
		if (this.isInsect(x_in, y_in +15) && depth == 0) {
			return this;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		
		try{
			DCDAttribute attr = (DCDAttribute) obj;
			if(attr.getName().equalsIgnoreCase(this.getName())){
				return true;
			}
		}catch(RuntimeException e){
			return false;
		}
		
		return false;
	}

	@Override
	public String toDOT() {
		return toString();
	}
	
	@Override
	public void parseDOT(String dot) {
	}
	
	/**
	 * <ownedAttribute xmi:id="_QEtAwM32EeSSaqYCH6D21w" name="name"
	 * visibility="private" type="_9NrQYM31EeSSaqYCH6D21w"/>
	 * 
	 * */

}
