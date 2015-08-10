package edu.uta.codeGenerator.ui.layout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import edu.uta.codeGenerator.component.DCDComponent;

public class AutoLayout extends Layout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2939330499538997298L;

	@Override
	public void layoutContainer(DCDComponent parent) {
	}

	@Override
	public void autoLayoutInit(DCDComponent parent) {
		// generate the dot file
		String gvs = parent.toDOT();

		/**
		 * call the graphviz
		 * */
		GraphViz gv = new GraphViz();
		gv.add(gvs);
		String type = "dot";
//		String path = PropertySetter.getProperties().getProperty(
//				PropertySetter.OUTPUT_FOLDER_DOT_PATH);
//		File out = new File(path + "/dotTemp." + type);
//		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
		File out = null;
		try {
			out = gv.writeDotSourceToFile(new String(gv.getGraph(gv.getDotSource(), type)));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(out));
			StringBuffer sb = new StringBuffer();
			String stmp = null;
			while ((stmp = br.readLine()) != null) {
				sb.append(stmp);
			}
			// parse the layout file
			parent.parseDOT(sb.toString());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
