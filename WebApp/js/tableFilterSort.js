// table numeration control
function numerate(table){
	if (table.find("tr:first-child th:first-child").text() === "#") {
		table.find("tbody > tr:visible td:first-child").each(function(index){
			$(this).text(function(){return ++index;});
		}); 
	}
}

var initSortableRows = $("#sortable tbody tr");

/**
 * Callback function for sorting rows
 * @param columnIndex
 * @param sortOrder take values "ASC" or "DESC"
 * @returns callback function
 */
function sortByColumnCallBack(columnIndex, sortOrder){
	if(sortOrder === "ASC") {
		return function(r1,r2){
			var r1 = r1.childNodes[columnIndex].innerHTML;
			var	r2 = r2.childNodes[columnIndex].innerHTML;
			if(r1 > r2) {return 1;}
			if(r1 < r2) {return -1;}
			return 0;
		}
	}
	else if (sortOrder === "DESC") {
		return function(r2,r1){
			var r1 = r1.childNodes[columnIndex].innerHTML;
			var	r2 = r2.childNodes[columnIndex].innerHTML;
			if(r1 > r2) {return 1;}
			if(r1 < r2) {return -1;}
			return 0;
		}
	}
	else {
		return null;
	}
}

//Filter against the visible rows keeping the init order
function resetOrderOfVisibleRows(table){
	// array of visible tr columns without numeration column
	var rowsCloneArrayWhithoutNumerationColumn = [].map.call(table.find("tbody tr:visible").clone(), function(e){
		if (table.find("tr:first-child th:first-child").text() === "#") {
			e.firstChild.remove()
		}
		return e.innerHTML
	})
	// Filter against the visible rows keeping the init order
	// initSortableRows defined outside this function when the page loads
	return initSortableRows.filter(function(index){
		var el = $(this).clone();
		if (table.find("tr:first-child th:first-child").text() === "#") {
			el[0].firstChild.remove()
		}
		return rowsCloneArrayWhithoutNumerationColumn.indexOf(el.get(0).innerHTML)>=0
	})
}

// FILTER EVENT
$("thead :input").bind("keyup change", {p1: "glyphicon-sort", p2: "glyphicon-sort-by-attributes", p3: "glyphicon-sort-by-attributes-alt"}, function(e) {
	var table = $(this).closest("table");
	var body = table.find("tbody")
	var rows = table.find( "tbody tr");
	rows.hide();
	// column indices as keys and input values as obj  prop values
	// objects will store only non-empty input values
	var inputFilter = {};
	var selectFilter = {};
	$(this).closest("tr").find(":input").filter(function() {
	    return this.value && this.value != "(All)" &&  this.value != "Select";
	}).each(function(){
		var i = $(this).parent().index();
		var key = "" + (i+1);
		var tag = $(this).prop("tagName");
		if (tag === "SELECT") {
			selectFilter[key] = $(this).val();
		}
		else if (tag === "INPUT") {
			inputFilter[key] = $(this).val();
		}		
	});

	// filter rows with respect to inputs
	rows.each(function(){
		var row = $(this);
		var isToShow = true;
		for (var col in inputFilter) {
			if (row.find("td:nth-child(" + col + ")").text().indexOf(inputFilter[col]) < 0) {
				isToShow = false;
			}				
		}
		for (var col in selectFilter) {
			if (row.find("td:nth-child(" + col + ")").text() !== selectFilter[col]) {
				isToShow = false;
			}				
		}
		if(isToShow){
			row.show();
		}
	});		
	
	// SORT AFTER EACH FILTER
	var sortableIcons = table.find("thead th div.sortable");
	if (sortableIcons.length > 0) {
		// Filter against the visible rows keeping the init order
		initOrderOfTableRows = resetOrderOfVisibleRows(table)
		sortableIcons.each(function(){
			var currentIcon = $(this);
			var index = currentIcon.closest("th").index();
			var currentIconClasses = currentIcon.attr("class");
			table.find("tbody tr:visible").remove()
			if(currentIconClasses.indexOf(e.data.p3) >= 0){
				var sorted = initOrderOfTableRows.clone().sort(sortByColumnCallBack(index, "DESC"))
				body.append(sorted)
				return false
			}
			else if(currentIconClasses.indexOf(e.data.p2) >= 0) {
				var sorted = initOrderOfTableRows.clone().sort(sortByColumnCallBack(index, "ASC"))
				body.append(sorted)	
				return false
			}
			else if(currentIconClasses.indexOf(e.data.p1) >= 0) {
				body.append(initOrderOfTableRows);	
			}
		})
	}	
	
	// numeration control
	numerate(table);
})// END FILTER

//SORT EVENT
$("thead th div.sortable").click({p1: "glyphicon-sort", p2: "glyphicon-sort-by-attributes", p3: "glyphicon-sort-by-attributes-alt"}, function(e) {
	var table = $(this.closest("table"));
	var body = table.find("tbody");
	var init = body.find("tr")
	var rows = body.find("tr:visible")
	var sortableIcons = table.find("thead th div.sortable");
	var clickedSortableIcon = $(this);
	// All the sortable icons different from clicked one should be brought to initial state
	sortableIcons.each(function(){
		var currentIcon = $(this);
		var currentIconClasses = currentIcon.attr("class");
		if(currentIcon.get(0) !== clickedSortableIcon.get(0)){
			if (currentIconClasses.indexOf(e.data.p3) >= 0) {
				currentIcon.removeClass(e.data.p3).addClass(e.data.p1);
			} else if(currentIconClasses.indexOf(e.data.p2) >= 0) {
				currentIcon.removeClass(e.data.p2).addClass(e.data.p1);
			}
		}
	})
	
	// Filter against the visible rows keeping the init order
	initOrderOfTableRows = resetOrderOfVisibleRows(table)
	
	var index = clickedSortableIcon.closest("th").index();
	
	var classes = clickedSortableIcon.attr("class");	
	
	rows.remove()	
	
	if(classes.indexOf(e.data.p3) >= 0){
		clickedSortableIcon.removeClass(e.data.p3).addClass(e.data.p1);
		body.append(initOrderOfTableRows);
	}
	else if(classes.indexOf(e.data.p2) >= 0) {
		clickedSortableIcon.removeClass(e.data.p2).addClass(e.data.p3);
		var sorted = initOrderOfTableRows.clone().sort(sortByColumnCallBack(index, "DESC"))
		body.append(sorted)	
	}
	else if(classes.indexOf(e.data.p1) >= 0) {
		clickedSortableIcon.removeClass(e.data.p1).addClass(e.data.p2);
		var sorted = initOrderOfTableRows.clone().sort(sortByColumnCallBack(index, "ASC"))
		body.append(sorted)		
	}
	
	// numeration control
	numerate(table);	
});
