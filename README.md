# Filter-Sort-Table

    <% User[] users = TableView.generateData();	%>
  	<%= new TableView(users, new String[][] {
  		{"First Name", "firstName", "filterable", "sortable"},
	    {"Last Name", "lastName"},
	    {"Home", "addressLable", "filterable"},
	    {"Gender", "genderLable", "filterable(male,female)"},
	    {"Age", "age", "sortable", "filterable"}
	  }).addTableTagAttr("id=\"sortable\" class=\"table table-bordered table-striped table-hover table-condensed\"").
		   addTableHeaderTrAttr("class=\"info\"").
		   setNumeration(true).
		   post()
	  %>
	  <%= new TableView(users, new String[][] {
		  {"First Name", "firstName"},
	    {"Last Name", "lastName"},
	    {"Home", "addressLable"},
	    {"Gender", "genderLable"},
	    {"Age", "age"}
	  }).addTableTagAttr("class=\"table table-bordered table-striped table-hover table-condensed\"").
		   addTableHeaderTrAttr("class=\"info\"").
		   setNumeration(false).
		   post()
	  %>

![filtersort](https://cloud.githubusercontent.com/assets/10475447/19354566/82e776c0-9167-11e6-9d8e-c0e634ec1d5a.png)
