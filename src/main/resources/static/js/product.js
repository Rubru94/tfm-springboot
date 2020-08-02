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

	console.log(products);
	document.getElementById("prodForm").action = "/customer/" + customerId
	+ "/budget?prods=" + products.join(";");
}

