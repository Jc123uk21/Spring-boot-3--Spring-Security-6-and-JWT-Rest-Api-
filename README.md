  **Spring boot 3, Spring Security 6 and JWT Rest Api**


I have created this repository to help build a better understanding of how to integrate spring-boot 3, spring security 6 and json web token to aid authentication and authorization within a RESTful api, the prerequisite of following the steps setout in this guide are to be familiar with spring’s framework in general but essentially how to create a new working project within a software development IDE as this will not be covered within this guide, I would also recommend becoming  familiar with how json web tokens are used when authentication and authorization is need to secured resources within a RESTful api. 


**_The Setup_** 


When creating  the project I made use of Springs initializer to create a .war build type project and selected the following dependencies
	
Spring web, Spring Data Jpa, MySql Driver (used as mySql is my chosen database), Validation (for hibernate bean validation), Lombok (to reduce boilerplate code).

**_Step 1 Creating the required file structure_**


We First create an initial file structure within the src/main/java of our project by adding the required folders to contain the following layers

Configuration, Model, Controllers, Dto (Data Transfer objects), Services, Repository


![fileStruct screen](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/60228594-db35-4db4-b98c-9b135a4a76ad)


We will first begin to implement the user functionalities of our api, we will first need to implement the model of our users, then create a (DTO) layer to transfer any important user data between the controller layer and the service layer. We will then follow this on by implementing the user service layer which will be responsible for handling the business logic needed by the model and also interacting with our data layer to request it to conduct crud operations over our user data, finally we will create our user repository to carry out these operations. Finally we will complete our setup by introducing our configuration classes and services to handle our bean resources and the json web tokens.

**_Step 2 Creating the User Model_**

Here we next create a new java class within our models folder (src/main/java/models) to represent our user model object, note we have made use of a number of annotation to first mark the class as a database entity,  then we have used lombok annotations to remove the need to implement the constructors, Setter and Getter methods etc. The class implements Springs security UserDetails Interface as this will aid us when authenticating and authorizing users.


![userModel scren](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/8ce051a7-7e31-4b6f-8808-eadd31a90c19)


**_Step 3 Request and response objects (DTO)_**

Next we create the AuthRequest data transfer object in the Dto folder (src/main/java/Dto) to carry our authentication request details from our controller layer into our service layer.



We then follow this on by creating our AuthReponse data transfer object in the same folder, this will carry any authentication responses back into the controller layer from the service layer.


![Dto screen](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/6db728ee-c1fd-434b-bd80-efc57219b9bd)

**_Step 4 User Service_**

The next step to be undertaken is to implement our user service layer interface and implementing class within the services folder, it is worth noting that authenticating users upon registration and login takes place within this service purely for simplicity, whereas this would normally take place within a separate service which purely handles authentication.

 ![userService screens](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/d135ce4d-9e9e-442a-bb10-eba42ffdb7ac)

 
![serviceUserImpl](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/36f91a91-4bd0-4dd6-9220-f73f85e07d70)


**_Step 5 User Repository_**

This step is very straight forward we simply create a new interface within our repository folder named UserRepository which extends the JpaRepository, this repository already holds a number of methods which will allows to carry out a majority of the operations needed to interact with our data layer, we do need to however add two custom queries as highlighted below

![userRepo scren](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/cfe46535-b24c-408b-bad2-1827d928beb6)


**_Step 6 JwtService_**

Next we need to implement the service class named JwtService in the services folder, this will handle creating the jwt’s for our api, this service will also provide us with the functionalities needed to access any important data within them.


![jwtService scr](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/e424e09b-1a20-4bcc-93bb-f07370043a23)



**_Step 7 Additional Application configuration_** 

This step sees us implement a configuration file which will be used to configure and initialize the new objects (beans)  which we need to be managed by the spring IoC container, this allows us use these objects (beans) across the whole application without the need to reconstruct these therm.


![appConfig screen](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/9f0e0a24-b17e-49dd-8930-32bfda5a81a0)


**_Step 8 Authentication Filter_**

We next need to add our custom JwtAuthenticationFilter filter class in the configuration folder  to be used within springs security filter chain, this custom filter will check the availability and integrity of the jwt’s passed in our api requests before moving onto the next filter in the filter chain.

![authfilterscreen](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/e03ec750-33ca-4579-bc31-f6a42fa09bd9)


**_Step 9 Security Configuration_**

Here we next create the SecurityConfig class, this is the class in which we will setup our security filter chain, determine which resources are accessible to different types of users and add our custom filter to the chain.


![securityConfig screen](https://github.com/Jc123uk21/Spring-boot-3--Spring-Security-6-and-JWT-Rest-Api-/assets/92167481/db67589f-8433-4c07-bdda-050e45428e35)


**_Step 10 The User Controller_**

