Software development strategy model chosen to define how to work collaboratively in the repository is **Trunk based development (TBD)**.

This model is very focused on CI/CD as well as development speed.

#### Features: 

- All development on *master* branch, there is no branch for each feature.

- Changes are uploaded often, at least once a day.

- *Master* is ready to version and deploy to production always.

- For more extensive developments in time than usual a few commits are made in a separate branch that will later be integrated into *master* through pull request

- For refactoring, **branch by abstraction** technique can be used, which allows large modifications without impacting the ability to continue making daily changes on *master* and allowing versions to be released.

![TBD](https://trunkbaseddevelopment.com/trunk1b.png)