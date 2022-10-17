
# e-commerce

This is a backend application for the below APIs:

1.	GET API to return the list of all product categories.
2.	GET API to return the list of all the sub-categories for a given category.
3.	GET API to return all the products for a given sub-category.
4.	GET API to return all the product details for a given product.
5.	POST API to add an item to the cart.
6.	PUT/DELETE API to add/delete items to the cart.
7.	GET API to view the list of products in the cart.
8.	Mock API for checkout.
9. Dockerized the backend application


### Current UserCase Design
Application is built as single service for above APIs due to time contraint with the able to add new APIs by making changes in respective controller,service classes.

#### Benefits:
 . Fast to develop and test

 . Easy to deploy as single service

### Drawbacks :
 . changing business in one of service we need to repackage and redeploy
 . Can not individually scale or make changes on specefic services without affecting other service

 ### Microservice Design (Recommended for real scenario)
 This usecase can be built using microservice design approach by creating individual springboot application for services like 
 orderservice,userserivce,productservice etc
 #### Benefits :
 . Improved Scalalibity
 . Exception propegation is prorper.
 . Adopting new technologies very easy
 
 #### Drawbacks :
 . We need to use extra services like euraka server and feign for service discovery and communication 
 . Takes more time to develop and test
 . Deployment takes more time as we need to deploy multiple services
