function getFormatDate(string){
	
	var date = new Date(string);
	
	return date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
}

document.addEventListener("DOMContentLoaded", function() {
	  
	document.getElementById("budget_date").innerText = getFormatDate(document.getElementById("budget_date").innerText);
});