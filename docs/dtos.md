The persistence layer is based on a [**Data Transfer Object** pattern](https://github.com/Rubru94/tfm-springboot/tree/master/src/main/java/tfm/springboot/dtos), which allows access to data from components in other layers, reduces requests to the persistence layer, and hides implementation details of data persistence.

Two versions of each main entity have been included:

- **Basic**: Collecting only the unrelated entity attributes.

- **Full**: Shows all entity attributes.


In addition, there are two DTOs specially oriented to simplify and avoid cycles of the REST response of product requests and budgets respectively:

- [**ForBudgetBudgetProductDTO**](../src/main/java/tfm/springboot/dtos/ForBudgetBudgetProductDTO.java): Provides products information in each budget as BasicProductDTO.

- [**ForProductBudgetProductDTO**](../src/main/java/tfm/springboot/dtos/ForProductBudgetProductDTO.java): Provides budget information for each product as BasicBudgetDTO.