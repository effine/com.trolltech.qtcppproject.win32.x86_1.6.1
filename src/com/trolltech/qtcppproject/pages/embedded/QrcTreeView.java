package com.trolltech.qtcppproject.pages.embedded;

import java.util.ArrayList;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;

public class QrcTreeView extends Composite {
	static ArrayList<QrcTreeViewListener> listeners; // 由于 35 行的需要 修改为static
	OleFrame frame;
	OleControlSite site;
	OleAutomation automation;
	ArrayList<Integer> dispIds;

	public QrcTreeView(Composite parent, int style) {
		super(parent, 0);
		dispIds = new ArrayList<Integer>();

		setLayout(new FillLayout());
		frame = new OleFrame(this, 0);

		site = new OleControlSite(frame, 0, "qtcppqrceditor.QrcTreeView");
		automation = new OleAutomation(site);
		site.doVerb(-1);

		listeners = new ArrayList<QrcTreeViewListener>();
		site.addEventListener(1, new OleListener() {
			public void handleEvent(OleEvent event) {
				for (int i = 0; i < QrcTreeView.listeners.size(); i++) {
					((QrcTreeViewListener) QrcTreeView.listeners.get(i))
							.dirtyChanged();
				}
			}
		});
		int[] tmpIds = automation.getIDsOfNames(new String[] { "load" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "save" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "isDirty" });
		dispIds.add(new Integer(tmpIds[0]));

		tmpIds = automation.getIDsOfNames(new String[] { "contents" });
		dispIds.add(new Integer(tmpIds[0]));
	}

	public boolean load(String file) {
		Variant[] par = new Variant[1];
		par[0] = new Variant(file);
		int dispId = ((Integer) dispIds.get(0)).intValue();
		Variant varResult = automation.invoke(dispId, par);
		return varResult.getBoolean();
	}

	public boolean save() {
		int dispId = ((Integer) dispIds.get(1)).intValue();
		Variant varResult = automation.invoke(dispId);
		return varResult.getBoolean();
	}

	public boolean isDirty() {
		int dispId = ((Integer) dispIds.get(2)).intValue();
		Variant varResult = automation.invoke(dispId);
		return varResult.getBoolean();
	}

	public String contents() {
		int dispId = ((Integer) dispIds.get(3)).intValue();
		Variant varResult = automation.invoke(dispId);
		return varResult.getString();
	}

	public void addQrcTreeViewListener(QrcTreeViewListener lstnr) {
		listeners.add(lstnr);
	}

	public void removeQrcTreeViewListener(QrcTreeViewListener lstnr) {
		listeners.remove(listeners.indexOf(lstnr));
	}
}