package org.aralmighty.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TableObjectView {
	public TableObjectView(Object data, String[][] properties) {
		this.data = data;
		this.properties = properties;
		this.tableTagAttr = "";
		this.tableHeaderTrAttr = "";
	}
	
	private Object data;
	private String[][] properties;
	private String tableTagAttr;
	private String tableHeaderTrAttr;
	
	public String getTableTagAttr() {
		return tableTagAttr;
	}

	public void setTableTagAttr(String tableTagAttr) {
		this.tableTagAttr = tableTagAttr;
	}

	public TableObjectView addTableTagAttr(String attr) {
		setTableTagAttr(getTableTagAttr() + " " + attr);
		return this;
	}

	public String getTableHeaderTrAttr() {
		return tableHeaderTrAttr;
	}

	public void setTableHeaderTrAttr(String tableHeaderTrAttr) {
		this.tableHeaderTrAttr = tableHeaderTrAttr;
	}

	public TableObjectView addTableHeaderTrAttr(String attr) {
		setTableHeaderTrAttr(getTableHeaderTrAttr() + " " + attr);
		return this;
	}
	
	public String post() {
		Object obj = this.data;
		if (obj == null) {
			return "NO SUCH DATA EXISTING!";
		}
		// Construct table rows.
		String tableRows = "";		
		for (String[] property : this.properties) {
			tableRows += "<tr>";
			tableRows += "<td>" + property[0] + "</td>";
			
			tableRows += "<td>";
			String propertyValue = property[1];
			String methodName = "get" + propertyValue.substring(0, 1).toUpperCase() + propertyValue.substring(1);
			Method method = null;

			try {
				method = obj.getClass().getMethod(methodName);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			try {
				// We assume that the get method of the object attribute
				// returns a string
				tableRows += method.invoke(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			tableRows += "</td>";			
			tableRows += "</tr>";
		}
		
		return "<table " + this.getTableTagAttr() + " >" +
					"<thead>" +
				   		"<tr " + this.getTableHeaderTrAttr() + " >"	+ 
				   			"<th>Property Names</th>" +
				   			"<th>Property Values</th>" + 
				   		"</tr>" +
			   		"</thead>" +
			   		"<tbody>" +	tableRows + "</tbody>" +
			   "</table>";
	}
}
