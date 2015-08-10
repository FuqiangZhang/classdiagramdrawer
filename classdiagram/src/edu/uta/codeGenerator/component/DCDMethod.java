package edu.uta.codeGenerator.component;

import java.util.ArrayList;

public class DCDMethod extends DCDComponent {

	private ArrayList<Param> prameArrayList;


	public DCDMethod() {
		this.height = this.nameHeight;
	}

	
	private String returnType = "";
	

	public ArrayList<Param> getPrameArrayList() {
		return prameArrayList;
	}

	// set the parameter to the method
	public void setPrameArrayList(ArrayList<Param> prameArrayList) {
		this.prameArrayList = prameArrayList;
	}

	public String getReturn_type() {
		return returnType;
	}

	public void setReturn_type(String return_type) {
		this.returnType = return_type;
	}

	private ArrayList<String> statementList = new ArrayList<String>();

	// set the return statement, for getXXX
	public void setReturnStatement(String returnName) {
		String returnStatement = "return " + returnName + ";";
		statementList.add(returnStatement);
	}

	// add setXXX statement
	public void setSetStatement(String name) {
		String setStatement = "this." + name +"=" + name + ";";
		statementList.add(setStatement);
	}
	
	public void addMethodInvoker(String who,String method){
		String methodInvoker = who+"."+method + "()";
		statementList.add(methodInvoker);
	}

	@Override
	public DCDComponent getSelectedComponent(int x_in, int y_in, int depth) {
		if (this.isInsect(x_in, y_in + 15) && depth == 0) {
			return this;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Param p : getPrameArrayList()) {
			sb.append(p.getName() + ":");
			sb.append(p.getType());
			sb.append(",");
		}
		// remove the last ','
		if (sb.length() > 1 && sb.charAt(sb.length() - 1) == ',')
			sb.deleteCharAt(sb.length() - 1);
		String returnString = "";
		if (getReturn_type() != null && !getReturn_type().equalsIgnoreCase("")) {
			returnString = ":" + getReturn_type();
		}
		return getAccess() + getName() + "(" + sb + ")"
				+ returnString;
	}

	@Override
	public String toDOT() {
		return toString();
	}

	@Override
	public void parseDOT(String dot) {
		// TODO DCD: implement parseDOT for DCDMethod
	}


	@Override
	public boolean equals(Object obj) {

		try {
			DCDMethod attr = (DCDMethod) obj;
			if (attr.getName().equalsIgnoreCase(this.getName())) {
				if (this.getPrameArrayList().size() != attr.getPrameArrayList()
						.size()) {
					return false;
				}
				for (int i = 0; i < this.getPrameArrayList().size(); i++) {
					if (!this.getPrameArrayList().get(i)
							.equals(attr.getPrameArrayList().get(i))) {
						return false;
					}
				}
				return true;
			}
		} catch (RuntimeException e) {
			return false;
		}

		return false;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public ArrayList<String> getStatementList() {
		return statementList;
	}

	public void setStatementList(ArrayList<String> statementList) {
		this.statementList = statementList;
	}
}
