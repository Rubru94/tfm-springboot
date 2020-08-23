# API WEB - TFM-SPRINGBOOT

### [Go to repository](https://github.com/Rubru94/tfm-springboot)

## NEW CUSTOMER

_Formulario inicial de registro de cliente_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/customer/new 

* #### TEMPLATE: _newCustomer.html_

![newCustomer.html](images/webTemplates/newCustomer.png)
	

## SAVE NEW CUSTOMER

_Guardar nuevo cliente_

* #### METHOD: POST

* #### URL:  http://{url}:{port}/customer 


## CUSTOMER

_Formulario de cliente donde seleccionaremos los datos de la compañía_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/customer/{id}  

* #### TEMPLATE: _customer.html_

![customer.html](images/webTemplates/customer.png)


## SAVE COMPANY INFO

_Guardar datos compañía asociada a cliente_

* #### METHOD: POST

* #### URL:  http://{url}:{port}/customer/{id}/company


## PRODUCT

_Formulario de selección de productos_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/customer/{id}/products 

* #### TEMPLATE: _product.html_

![product.html](images/webTemplates/product.png)


## SAVE NEW BUDGET

_Guardar un nuevo presupuesto de cliente_

* #### METHOD: POST

* #### URL:  http://{url}:{port}/customer/{id}/budget


## LAST BUDGET

_Sumario datos presupuesto_

* #### METHOD: GET

* #### URL:  http://{url}:{port}/customer/{id}/lastBudget

* #### TEMPLATE: _budget.html_

![budget.html](images/webTemplates/budget.png)


### [Go to repository](https://github.com/Rubru94/tfm-springboot)