The persistence layer is based on a [**Data Transfer Object** pattern](../src/main/java/tfm/springboot/dtos), which allows access to data from components in other layers, reduces requests to the persistence layer, and hides the implementation details of data persistence.

Two versions of each main entity have been included:

- **Basic**: Collecting only the unrelated attributes of the entity.

- **Full**: Shows all the attributes of the entity.


In addition, there are two DTOs specially oriented to simplify and avoid cycles of the REST response of product requests and budgets respectively:

- [**ForBudgetBudgetProductDTO**](../src/main/java/tfm/springboot/dtos/ForBudgetBudgetProductDTO.java): Provides the information of products in each budget as BasicProductDTO.

- [**ForProductBudgetProductDTO**](../src/main/java/tfm/springboot/dtos/ForProductBudgetProductDTO.java): Provides the budget information for each product as BasicBudgetDTO.