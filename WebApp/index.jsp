<%@page import="org.aralmighty.dto.User"%>
<%@page import="org.aralmighty.services.TableView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>table</title>
  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">  
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-xs-12">
    <% User[] users = TableView.generateData();	%>
	<%= new TableView(users, new String[][] {
		   {"First Name", "firstName", "filterable", "sortable"},
	       {"Last Name", "lastName"},
	       {"Home", "addressLable", "filterable"},
	       {"Gender", "genderLable", "filterable(male,female)"},
	       {"Age", "age",  "sortable", "filterable"}
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
    </div>
  </div>
</div>


<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/tableFilterSort.js"></script>
</body>
</html>