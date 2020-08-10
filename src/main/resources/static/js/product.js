var products = [];

function selectProduct(id, customerId, button) {

	if(!products.find(elem => elem == id))	{
		
		products.push(id);
		button.innerText = '+ Remove';
		button.style.backgroundColor = "#af5454";
	}
	else{
		
		products.splice(products.indexOf(id),1);	
		button.innerText = '+ Add';
		button.style.backgroundColor = "#616161";
	}

	if(products.length > 0){
		document.getElementById("next-button").disabled = false;
		document.getElementById("next-button").style.backgroundColor = "#00E098";
	}else{
		document.getElementById("next-button").disabled = true;
		document.getElementById("next-button").style.backgroundColor = "#b7b7b7";
	}
	
	console.log(products);
	document.getElementById("prodForm").action = "/customer/" + customerId
	+ "/budget?prods=" + products.join(";");
}

