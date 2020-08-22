# API REST - TFM-SPRINGBOOT

### [Go back](../)

## GET ALL CUSTOMERS

_Obtener un listado de todos los clientes incuyendo compañia y presupuestos_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/customers

* #### RESPONSE:
	```
	[
		{
			"id": 982,
			"name": "Pedro",
			"lastname": "López",
			"email": "pelo@gmail.com",
			"company": {
				"id": 983,
				"vatregnumber": "B00111022",
				"name": "GESASA S.A.",
				"country": "Germany",
				"industry": "Services"
			},
			"budgets": [
				{
					"id": 984,
					"date": "2020-08-22T12:10:59.096+0000",
					"total": 160.0
				}
			]
		},
		{
			"id": 985,
			"name": "Juan ",
			"lastname": "Pérez",
			"email": "jupe@gmail.com",
			"company": {
				"id": 986,
				"vatregnumber": "B05907564",
				"name": "ARFOGA S.L.",
				"country": "Spain",
				"industry": "Telecommunications"
			},
			"budgets": [
				{
					"id": 987,
					"date": "2020-08-22T12:12:12.444+0000",
					"total": 130.0
				},
				{
					"id": 988,
					"date": "2020-08-22T12:14:46.521+0000",
					"total": 170.0
				}
			]
    	}
	]
	```

## GET CUSTOMER BY ID

_Obtener toda la información de un cliente_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/customer/{id}

* #### RESPONSE:
	```
	{
		"id": 985,
		"name": "Juan ",
		"lastname": "Pérez",
		"email": "jupe@gmail.com",
		"company": {
			"id": 986,
			"vatregnumber": "B05907564",
			"name": "ARFOGA S.L.",
			"country": "Spain",
			"industry": "Telecommunications"
		},
		"budgets": [
			{
				"id": 987,
				"date": "2020-08-22T12:12:12.444+0000",
				"total": 130.0
			},
			{
				"id": 988,
				"date": "2020-08-22T12:14:46.521+0000",
				"total": 170.0
			}
		]
	}
	```

## CREATE CUSTOMER

_Crear un cliente_

* #### METHOD: POST

* #### URL:  http://{url}:{port}/api/customer

* #### BODY:
	```
	{
		"name": "Jorge",
		"lastname": "Jimenez",
		"email": "joji@gmail.com",
		"company": null
	}
	```

* #### RESPONSE:

	```
	{
		"id": 989,
		"name": "Jorge",
		"lastname": "Jimenez",
		"email": "joji@gmail.com",
		"company": null,
		"budgets": []
	}
	```

## GET ALL COMPANIES

_Obtener un listado de todas las compañias incuyendo clientes_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/companies

* #### RESPONSE:

	```
	[
		{
			"id": 983,
			"vatregnumber": "B00111022",
			"name": "GESASA S.A.",
			"country": "Germany",
			"industry": "Services",
			"customers": [
				{
					"id": 982,
					"name": "Pedro",
					"lastname": "López",
					"email": "pelo@gmail.com"
				}
			]
		},
		{
			"id": 986,
			"vatregnumber": "B05907564",
			"name": "ARFOGA S.L.",
			"country": "Spain",
			"industry": "Telecommunications",
			"customers": [
				{
					"id": 985,
					"name": "Juan ",
					"lastname": "Pérez",
					"email": "jupe@gmail.com"
				},
				{
					"id": 990,
					"name": "Julian",
					"lastname": "Mendez",
					"email": "jume@hotmail.com"
				}
			]
		}
	]
	```

## GET COMPANY BY ID

_Obtener toda la información de una compañía_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/company/{id}

* #### RESPONSE:

	```
	{
		"id": 986,
		"vatregnumber": "B05907564",
		"name": "ARFOGA S.L.",
		"country": "Spain",
		"industry": "Telecommunications",
		"customers": [
			{
				"id": 985,
				"name": "Juan ",
				"lastname": "Pérez",
				"email": "jupe@gmail.com"
			},
			{
				"id": 990,
				"name": "Julian",
				"lastname": "Mendez",
				"email": "jume@hotmail.com"
			}
		]
	}
	```

## SET CUSTOMER COMPANY

_Setea la compañía de un cliente_

* #### METHOD: POST

* #### URL:  http://{url}:{port}/api/customer/{id}/company

* #### BODY:
	```
	{
		"vatregnumber": "B15248651",
		"name": "IBM",
		"country": "Spain",
		"industry": "Logistic"
	}
	```

* #### RESPONSE:

	```
	{
		"id": 992,
		"vatregnumber": "B15248651",
		"name": "IBM",
		"country": "Spain",
		"industry": "Logistic"
	}
	```

## GET ALL PRODUCTS

_Obtener un listado de todos los productos incluyendo presupuestos a los que pertenecen_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/products

* #### RESPONSE:

	```
	[
		{
			"id": 969,
			"code": "AF",
			"name": "Advanced Finance",
			"description": "Oriented towards companies that require recurring billing to their clients",
			"expenseHours": 30,
			"budgets": [
				{
					"budget": {
						"id": 976,
						"date": "2020-08-16T12:33:15.384+0000",
						"total": 130.0
					},
					"regDate": "2020-08-16T12:33:15.396+0000"
				},
				{
					"budget": {
						"id": 984,
						"date": "2020-08-22T12:10:59.096+0000",
						"total": 160.0
					},
					"regDate": "2020-08-22T12:10:59.107+0000"
				},
				{
					"budget": {
						"id": 987,
						"date": "2020-08-22T12:12:12.444+0000",
						"total": 130.0
					},
					"regDate": "2020-08-22T12:12:12.490+0000"
				},
				{
					"budget": {
						"id": 988,
						"date": "2020-08-22T12:14:46.521+0000",
						"total": 170.0
					},
					"regDate": "2020-08-22T12:14:46.588+0000"
				}
			]
		},
		{
			"id": 970,
			"code": "AP",
			"name": "Advanced Purchases",
			"description": "The Advanced Purchases module will help you keep track of your purchases by relating the authorized price lists to the contracts you have assets",
			"expenseHours": 40,
			"budgets": [
				{
					"budget": {
						"id": 981,
						"date": "2020-08-22T10:54:52.025+0000",
						"total": 195.0
					},
					"regDate": "2020-08-22T10:54:52.035+0000"
				},
				{
					"budget": {
						"id": 988,
						"date": "2020-08-22T12:14:46.521+0000",
						"total": 170.0
					},
					"regDate": "2020-08-22T12:14:46.529+0000"
				},
				{
					"budget": {
						"id": 991,
						"date": "2020-08-22T14:51:44.249+0000",
						"total": 140.0
					},
					"regDate": "2020-08-22T14:51:44.285+0000"
				}
			]
		}, 
		...
	]
	```

## GET PRODUCT BY ID

_Obtener toda la información de un producto_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/product/{id}

* #### RESPONSE:

	```
	{
		"id": 970,
		"code": "AP",
		"name": "Advanced Purchases",
		"description": "The Advanced Purchases module will help you keep track of your purchases by relating the authorized price lists to the contracts you have assets",
		"expenseHours": 40,
		"budgets": [
			{
				"budget": {
					"id": 981,
					"date": "2020-08-22T10:54:52.025+0000",
					"total": 195.0
				},
				"regDate": "2020-08-22T10:54:52.035+0000"
			},
			{
				"budget": {
					"id": 988,
					"date": "2020-08-22T12:14:46.521+0000",
					"total": 170.0
				},
				"regDate": "2020-08-22T12:14:46.529+0000"
			},
			{
				"budget": {
					"id": 991,
					"date": "2020-08-22T14:51:44.249+0000",
					"total": 140.0
				},
				"regDate": "2020-08-22T14:51:44.285+0000"
			}
		]
	}
	```


## GET ALL BUDGETS

_Obtener un listado de todos los presupuestos incluyendo productos que contienen y cliente a quien pertenecen_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/budgets

* #### RESPONSE:

	```
	[
		...
		{
			"id": 984,
			"date": "2020-08-22T12:10:59.096+0000",
			"total": 160.0,
			"customer": {
				"id": 982,
				"name": "Pedro",
				"lastname": "López",
				"email": "pelo@gmail.com"
			},
			"products": [
				{
					"product": {
						"id": 969,
						"code": "AF",
						"name": "Advanced Finance",
						"description": "Oriented towards companies that require recurring billing to their clients",
						"expenseHours": 30
					},
					"regDate": "2020-08-22T12:10:59.107+0000"
				},
				{
					"product": {
						"id": 971,
						"code": "GL",
						"name": "General Ledger",
						"description": "With the Multiple Accounting module you will have the movements in the corresponding Books automatically and immediately without creating duplications that must be eliminated later",
						"expenseHours": 55
					},
					"regDate": "2020-08-22T12:10:59.170+0000"
				},
				{
					"product": {
						"id": 972,
						"code": "SM",
						"name": "Software Management",
						"description": "Deploy the enhancements you've created and share electronic documents and information with the Software Management module",
						"expenseHours": 75
					},
					"regDate": "2020-08-22T12:10:59.152+0000"
				}
			]
		},
		{
			"id": 987,
			"date": "2020-08-22T12:12:12.444+0000",
			"total": 130.0,
			"customer": {
				"id": 985,
				"name": "Juan ",
				"lastname": "Pérez",
				"email": "jupe@gmail.com"
			},
			"products": [
				{
					"product": {
						"id": 969,
						"code": "AF",
						"name": "Advanced Finance",
						"description": "Oriented towards companies that require recurring billing to their clients",
						"expenseHours": 30
					},
					"regDate": "2020-08-22T12:12:12.490+0000"
				},
				{
					"product": {
						"id": 972,
						"code": "SM",
						"name": "Software Management",
						"description": "Deploy the enhancements you've created and share electronic documents and information with the Software Management module",
						"expenseHours": 75
					},
					"regDate": "2020-08-22T12:12:12.475+0000"
				},
				{
					"product": {
						"id": 973,
						"code": "HR",
						"name": "Human Resources",
						"description": "The Resource Allocation module allows you to measure the occupation of your resources and the activities in which they are committed with start and end dates",
						"expenseHours": 25
					},
					"regDate": "2020-08-22T12:12:12.450+0000"
				}
			]
		},
		...
	]
	```

## GET BUDGET BY ID

_Obtener toda la información de un presupuesto

* #### METHOD: GET

* #### URL:  http://{url}:{port}/api/budget/{id}

* #### RESPONSE:

	```
	{
		"id": 987,
		"date": "2020-08-22T12:12:12.444+0000",
		"total": 130.0,
		"customer": {
			"id": 985,
			"name": "Juan ",
			"lastname": "Pérez",
			"email": "jupe@gmail.com"
		},
		"products": [
			{
				"product": {
					"id": 969,
					"code": "AF",
					"name": "Advanced Finance",
					"description": "Oriented towards companies that require recurring billing to their clients",
					"expenseHours": 30
				},
				"regDate": "2020-08-22T12:12:12.490+0000"
			},
			{
				"product": {
					"id": 972,
					"code": "SM",
					"name": "Software Management",
					"description": "Deploy the enhancements you've created and share electronic documents and information with the Software Management module",
					"expenseHours": 75
				},
				"regDate": "2020-08-22T12:12:12.475+0000"
			},
			{
				"product": {
					"id": 973,
					"code": "HR",
					"name": "Human Resources",
					"description": "The Resource Allocation module allows you to measure the occupation of your resources and the activities in which they are committed with start and end dates",
					"expenseHours": 25
				},
				"regDate": "2020-08-22T12:12:12.450+0000"
			}
		]
	}
	```

## ADD CUSTOMER BUDGET

_Añade un nuevo presupuesto a un cliente_

* #### METHOD: POST

* #### URL:  http://{url}:{port}/api/customer/{id}/budget

* #### BODY:
	```
	{
		"products": [
			{
				"product": {
					"code": "AF",
					"name": "Advanced Finance",
					"description": "Oriented towards companies that require recurring billing to their clients",
					"expenseHours": 30
				}
			},
			{
				"product": {
					"code": "AP",
					"name": "Advanced Purchases",
					"description": "The Advanced Purchases module will help you keep track of your purchases by relating the authorized price lists to the contracts you have assets",
					"expenseHours": 40
				}
			}
		]
	}
	```

* #### RESPONSE:

	```
	{
		"id": 1158,
		"date": "2020-08-22T16:10:39.601+0000",
		"total": 70.0,
		"customer": {
			"id": 1125,
			"name": "Tony",
			"lastname": "Stark",
			"email": "tost@iron.com"
		},
		"products": [
			{
				"product": {
					"id": 1083,
					"code": "AF",
					"name": "Advanced Finance",
					"description": "Oriented towards companies that require recurring billing to their clients",
					"expenseHours": 30
				},
				"regDate": "2020-08-22T16:10:39.608+0000"
			},
			{
				"product": {
					"id": 1084,
					"code": "AP",
					"name": "Advanced Purchases",
					"description": "The Advanced Purchases module will help you keep track of your purchases by relating the authorized price lists to the contracts you have assets",
					"expenseHours": 40
				},
				"regDate": "2020-08-22T16:10:39.637+0000"
			}
		]
	}
	```


### [Go back](../)
