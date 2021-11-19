<-------------------------------Marcura Developer Test ---------------------------->

com.wlogsolutions.marcura.controllers:
	Classes under this package are used as rest controller in the project

com.wlogsolutions.marcura.services:
	Services interface are creating to decouple the code.

com.wlogsolutions.marcura.impl:
	implemenation classes are implementing the services interfaces and communicating directly with jpa respositories
	
com.wlogsolutions.marcura.repositories:
	communicating with database directly
	
com.wlogsolutions.marcura.models:
	models are pojo classes which are goign to be store in database.

com.wlogsolutions.marcura.models.dto:
	dto classes are used for passing database between controller and services implemation classes to remove dependencies and also avoid passing data which are not going to be update.

com.wlogsolutions.marcura.request:
	request classes has only attributes that a user can is allowed to passing to the api. its is used to avoid update data which a user is not authorized for.

com.wlogsolutions.marcura.response:
	response classes are used only to show data which a user is only allowed to read , its used for removing mistake to response data which are critical , such as passwords

com.wlogsolutions.marcura.scheduler:
	scheduler has a component which accept cron value for running fixerio and retreive data from it every 12:05 am

com.wlogsolutions.marcura.utils:
	DateUtil class is used to format the date into ISO8601 date.
	
com.wlogsolutions.marcura.exceptions:
	Exceptions package is used for handling global exceptions and also creating some custom exception class
	
	
<------------------------------------------API ---------------------------------------->
H2 console database is used as in memory database.

Swagger Api Documentation

http://localhost:8080/swagger-ui/

GET : http://localhost:8080/exchange?from={from}&{to}=USD&date={date}

--> http://localhost:8080/exchange?from=EUR&to=USD&date=2021-11-18

	{
		"from": "EUR",
		"to": "USD",
		"exchange": 2.334
	}
GET : http://localhost:8080/exchange?from={from}&{to}=USD

--> http://localhost:8080/exchange?from=EUR&to=USD
	{
		"from": "EUR",
		"to": "USD",
		"exchange": 2.334
	}

PUT : http://localhost:8080/exchange
	Request:
	{
    "name":"USD",
    "value":"2.4"
	}

	Response: {
    "name": "USD",
    "value": "2.4",
    "date": "2021-11-18"
	}
	verify by : http://localhost:8080/exchange?from={from}&{to}=USD&date={date}
	
GET : http://localhost:8080/fixer
	NOTE: you can custom call to fixerio before calling to any above query. 
	
	
	
	NEED TO BE DONE:
		User input validation
		User Authentication
		
	