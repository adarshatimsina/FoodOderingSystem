# FoodeOderingSystem
This is an assignment code.

FOS (Food Ordering System) for McGee restaurant using a RMI distributed technology.

cd C:\Users\User\Documents\NetBeansProjects\FOS\build\classes\

java -cp ".;C:\Users\User\Documents\NetBeansProjects\FOS\dist\lib\postgresql-42.7.4.jar" Server.Server 

.;C:\Users\User\Documents\NetBeansProjects\FOS\dist\lib\postgresql-42.7.4.jar;


Cafeteria.data
1. Feedbacks
	a. OrderID
	b. Message

2. Menu
	a. foodID
	b. food name
	c. food price
	d. category(veg,nonveg)
	e. Category ( drink , lunch , snacks)

3. Orders
	a. OrderID  (if more food on same id, duplicate it)
	b. Food name
	c. Quantity
	d. total price
	e. Table no.
	d. Student ID

4. Payments
	a. orderID
	b. total price
	c. Student ID

5. Staffs
	a. StaffID
	b. Password
	c. Name

6. Students
	a. StudentID
	b. password
	c. Student Name

7. Login-log
	a. User/ admin
	b. Student/ Staff ID
	c. date
	d. time


FoodOderingSystem
1. menu
FoodID: Unique identifier for the food item (Auto Increment)
FoodName: Name of the food item
FoodPrice: Price of the food item
SubCategory: Type of food (e.g., Drink, Lunch, Dessert, Snack)

2. Customers
CustomerID: Unique identifier for the customer
Password: Password for customer login
FirstName: First name of the customer
LastName: Last name of the customer
GovernmentID: Unique government ID of the customer

3. Staffs
StaffID: Unique identifier for the staff member
Password: Password for staff login
Name: Name of the staff member

4. Orders
OrderID: Unique identifier for the order
TotalPrice: Total price of the order
CustomerID: Identifier for the customer placing the order

5. OrderItems
OrderID: Identifier for the associated order
FoodID: Identifier for the food item
Quantity: Quantity of the food item ordered

6. Payments
PaymentID: Unique identifier for the payment
OrderID: Identifier for the associated order
TotalPrice: Total price paid for the order
CustomerID: Identifier for the customer making the payment



Steps:
1. Create Client, Database, Server and Shared Package.
2. Import postgres JDBC , (This sucks)
3. Make database in pgadmin 
4. Database connection
5. Define remote interface(FOSInterface)
6. Implement the remote interface in server. (ServerImplementation , where the method is written from interface)
7. Implement the server (Server.java)
8. Implement the client (Client.java)
9. 
