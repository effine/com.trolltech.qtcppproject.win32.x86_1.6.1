package com.trolltech.qtcppproject.pages.embedded;

import java.util.ArrayList;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;

public class ScopeList extends Composite {
	private OleFrame frame;
	private OleControlSite site;
	private OleAutomation automation;
	private ArrayList<Integer> dispIds;

	public ScopeList(Composite parent, int style) {
		super(parent, 0);
		dispIds = new ArrayList<Integer>();

		setLayout(new FillLayout());
		frame = new OleFrame(this, 0);

		site = new OleControlSite(frame, 0, "qtcppproparser.ScopeList");
		automation = new OleAutomation(site);
		site.doVerb(-1);

		int[] tmpIds = automation.getIDsOfNames(new String[] { "showModel" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation
				.getIDsOfNames(new String[] { "selectFirstVariable" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "search" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "isChanged" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "removeFiles" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "addFiles" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "addFile" });
		dispIds.add(new Integer(tmpIds[0]));
	}

	public void showModel(String proFileName, String contents, boolean enabled) {
		Variant[] par = new Variant[3];
		par[0] = new Variant(proFileName);
		par[1] = new Variant(contents);
		par[2] = new Variant(enabled);
		int dispId = dispIds.get(0).intValue();
		automation.invoke(dispId, par);
	}

	public void selectFirstVariable() {
		int dispId = dispIds.get(1).intValue();
		automation.invoke(dispId);
	}

	public boolean search(String proFileName, String contents) {
		Variant[] par = new Variant[2];
		par[0] = new Variant(proFileName);
		par[1] = new Variant(contents);
		int dispId = dispIds.get(2).intValue();
		Variant varResult = automation.invoke(dispId, par);
		return varResult.getBoolean();
	}

	public boolean isChanged(String proFileName) {
		Variant[] par = new Variant[1];
		par[0] = new Variant(proFileName);
		int dispId = dispIds.get(3).intValue();
		Variant varResult = automation.invoke(dispId, par);
		return varResult.getBoolean();
	}

	public String removeFiles(String proFileName) {
		Variant[] par = new Variant[1];
		par[0] = new Variant(proFileName);
		int dispId = dispIds.get(4).intValue();
		Variant varResult = automation.invoke(dispId, par);
		return varResult.getString();
	}

	public String addFiles(String proFileName) {
		Variant[] par = new Variant[1];
		par[0] = new Variant(proFileName);
		int dispId = dispIds.get(5).intValue();
		Variant varResult = automation.invoke(dispId, par);
		return varResult.getString();
	}

	public void addFile(String file, String var) {
		Variant[] par = new Variant[2];
		par[0] = new Variant(file);
		par[1] = new Variant(var);
		int dispId = dispIds.get(6).intValue();
		automation.invoke(dispId, par);
	}
}