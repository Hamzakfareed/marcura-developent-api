<h1>Marcura Developer Test</h1>

<h3>com.wlogsolutions.marcura.controllers:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	Classes under this package are used as rest controller in the project

<h3>com.wlogsolutions.marcura.services:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	Services interface are creating to decouple the code.

<h3>com.wlogsolutions.marcura.impl:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	implemenation classes are implementing the services interfaces and communicating directly with jpa respositories
	
<h3>com.wlogsolutions.marcura.repositories:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	communicating with database directly
	
<h3>com.wlogsolutions.marcura.models:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	models are pojo classes which are goign to be store in database.

<h3>com.wlogsolutions.marcura.models.dto:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	dto classes are used for passing database between controller and services implemation classes to remove dependencies and also avoid passing data which are not going to be update.

<h3>com.wlogsolutions.marcura.request:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	request classes has only attributes that a user can is allowed to passing to the api. its is used to avoid update data which a user is not authorized for.

<h3>com.wlogsolutions.marcura.response:</h3>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;response classes are used only to show data which a user is only allowed to read , its used for removing mistake to response data which are critical , such as passwords

<h3>com.wlogsolutions.marcura.scheduler:</h3>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	scheduler has a component which accept cron value for running fixerio and retreive data from it every 12:05 am

<h3>com.wlogsolutions.marcura.utils:</h3>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DateUtil class is used to format the date into ISO8601 date.
	
<h3>com.wlogsolutions.marcura.exceptions:</h3>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exceptions package is used for handling global exceptions and also creating some custom exception class
	
<br>
<--------------------------------------------------------------------------------------------------------------------->
<br>
<h2>API</h2>

<h3>H2 console database is used as in memory database.</h3>

<h2>Swagger Api Documentation</h2>

http://localhost:8080/swagger-ui/

 <h4> GET : http://localhost:8080/exchange?from={from}&{to}=USD&date={date}</h4>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; http://localhost:8080/exchange?from=EUR&to=USD&date=2021-11-18<br>

    {
		"from": "EUR",
		"to": "USD",
		"exchange": 2.334
	}
<h4>GET : http://localhost:8080/exchange?from={from}&{to}=USD</h4>

http://localhost:8080/exchange?from=EUR&to=USD<br>

	{
		"from": "EUR",
		"to": "USD",
		"exchange": 2.334
	}

<h4>PUT : http://localhost:8080/exchange</h4>
	Request:
	
	{
		"name":"USD",
		"value":"2.4"
	}

Response:
	
	{
    	"name": "USD",
    	"value": "2.4",
    	"date": "2021-11-18"
	}
	
verify by : http://localhost:8080/exchange?from={from}&{to}=USD&date={date}
	
<h4>GET : http://localhost:8080/fixer</h4>
	<h2>NOTE: 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;First you need to fetch data from fixerio either http://localhost:8080/fixer or using scheduler.
	FixerIO is implemented using RestApi and also scheduler.
	
	
	
<h3>NEED TO BE DONE:</h3>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	User input validation<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	User Authentication
		
	