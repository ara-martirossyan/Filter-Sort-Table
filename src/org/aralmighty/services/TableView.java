package org.aralmighty.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.aralmighty.dto.User;

public class TableView {
	public TableView(Object[] data, String[][] columnNames) {
		this.data = data;
		this.columns = columnNames;
		this.numeration = true;
		this.tableTagAttr = "";
		this.tableHeaderTrAttr = "";
	}

	private Object[] data;
	private String[][] columns;
	private boolean numeration;
	private String tableTagAttr;
	private String tableHeaderTrAttr;	
	
	
	public boolean isNumeration() {
		return numeration;
	}

	public TableView setNumeration(boolean numeration) {
		this.numeration = numeration;
		return this;
	}

	public String getTableTagAttr() {
		return tableTagAttr;
	}

	public void setTableTagAttr(String tableTagAttr) {
		this.tableTagAttr = tableTagAttr;
	}

	public TableView addTableTagAttr(String attr) {
		setTableTagAttr(getTableTagAttr() + " " + attr);
		return this;
	}

	public String getTableHeaderTrAttr() {
		return tableHeaderTrAttr;
	}

	public void setTableHeaderTrAttr(String tableHeaderTrAttr) {
		this.tableHeaderTrAttr = tableHeaderTrAttr;
	}

	public TableView addTableHeaderTrAttr(String attr) {
		setTableHeaderTrAttr(getTableHeaderTrAttr() + " " + attr);
		return this;
	}

	/**
	 * @return
	 */
	public String post() {
		String headerColumnsHtml = (this.isNumeration() ? "<th>#</th>": "");
		String filterColumnsHtml = (this.isNumeration() ? "<th></th>": "");
		boolean hasFilter = false;
		for (String[] column : this.columns) {      
			// Check sorting options.
			boolean hasSort = false;
			for (int i = 2; i < column.length; i++) {
				if (column[i].startsWith("sortable")) {
					hasSort = true;
					break;
				}
			}
			String sort = (hasSort ? "<div class=\"sortable glyphicon glyphicon-sort pull-right\"></div>" : "");
			headerColumnsHtml += "<th>" + column[0] + sort + "</th>";
			
		    // Check filtering options.
		    String filter = "";
		    for (int i = 2; i < column.length; i++) {
		       if(column[i].startsWith("filterable")){
		           hasFilter = true;
		           filter = column[i];
		           break;
		       }
		    }
			if (!filter.equals("")) {
				// Checking if filter has a select input
				if (filter.length() > "filterable".length() && 
					filter.substring("filterable".length(), "filterable".length()+1).equals("(") && 
					filter.substring(filter.length()-1).equals(")")
				) {
					String[] options = filter.substring("filterable".length()+1, filter.length()-1).split(",");
					filterColumnsHtml += "<th><select class=\"form-control\"><option disabled selected>Select</option><option>(All)</option>";
					for (int i = 0; i < options.length; i++) {
						filterColumnsHtml += "<option>" + options[i] + "</option>";
					}
					filterColumnsHtml += "</select></th>";
				}
				// Checking if filter has a text input
				else {
					filterColumnsHtml += "<th>" + "<input type=\"text\" class=\"form-control\">" + "</th>";
				}				
			}
			else {
				filterColumnsHtml += "<th></th>";
			}
			
		}		
		
		// Construct table rows.
		String tableRows = "";		
		for (int i = 0; i < data.length; i++) {
			Object obj = data[i];
			tableRows += (this.isNumeration() ? "<tr><td>" + (i + 1) + "</td>": "");
			for (String[] column : this.columns) {
				tableRows += "<td>";
				String columnAction = column[1];
				String[] args = null;
				Class<?>[] cArgs = null;
				String methodName = null;
				if (columnAction.indexOf("(") > 0 && columnAction.substring(columnAction.length()-1).equals(")")) {
					args = columnAction.substring(columnAction.indexOf("(")+1, columnAction.indexOf(")")).split(",");
					cArgs = new Class[args.length];
					for (int k = 0; k < cArgs.length; k++) {
						cArgs[k] = String.class;
					}
					methodName = columnAction.substring(0, columnAction.indexOf("("));
					methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
				}
				else {
					methodName = "get" + columnAction.substring(0, 1).toUpperCase() + columnAction.substring(1);
				}
				Method method = null;
				try {
					method = obj.getClass().getMethod(methodName, cArgs);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				try {
					// We assume that the get method of the object attribute
					// returns a string
					tableRows += method.invoke(obj, (Object[])args);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				tableRows += "</td>";
			}
			tableRows += "</tr>";
		}
		
		return "<table " + this.getTableTagAttr() + " >" +
					"<thead>" +
			   		"<tr " + this.getTableHeaderTrAttr() + " >"	+ headerColumnsHtml + "</tr>" +
			   		(hasFilter ? "<tr>" + filterColumnsHtml + "</tr>" : "") +
			   		"</thead>" +
			   		"<tbody>" +	tableRows + "</tbody>" +
			   "</table>";
	}

	public static User[] generateData() {
               ArrayList<User> users = new ArrayList<User>();
               users.add(new User("Ara", "Martirosyan", "rue 18", 31, true));
               users.add(new User("Raymond", "Perrée", "rue 43", 30, true));
               users.add(new User("Bernadette", "Clavette", "rue 15", 24, false));
               users.add(new User("Adrienne", "Verllow", "rue 8", 19, false));
               users.add(new User("Louis", "Deselle", "rue 19", 68, true));
               users.add(new User("Nicole", "Rossiter", "rue 33", 46, false));
               users.add(new User("Thomas", "Ferrande", "rue 52", 26, true));
               users.add(new User("David", "Nodreau", "rue 46", 52, true));
               users.add(new User("Alice", "Gaspard", "rue 22", 34, false));
               users.add(new User("Roger", "Francis", "rue 11", 47, true));

               return users.toArray(new User[users.size()]);
        }

}
