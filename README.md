# Fixer Mock API

### Project Description

I am trying to test and modify the ECommerce App from Daimox.
Certain parts of the project are dependent on the Fixer.io API which has some rather heavy rate-limiting.
For testing purposes I will not require an accurate or sensible reading of the exchange-rates.
So Mock-API that uses the same format as the Fixer-API will be plenty enough.


### Features

This Mock-API currently only mocks the ```/latest``` Endpoint.
It checks for an ```API-Key``` and only responds with the according answer if the key is correct.
The Key can be set in the ```application.properties``` file as the value of ```mock.api.key```.


### API-answer

The API can respond with a variety of Statuscodes:

- 200: In case the keys do match the correct answer (taken from the ```standard-answer.json``` file) will be supplied in the body.
It is the value returned by calling the latest-endpoint of the Fixer API without any arguments than the ```API-Key```
on the 13th of March 2026 at 13:55 CET.

- 401: The body will contain an error (taken from the ```error-answer.json``` file) if the ```API-Key``` doesn't match or is empty.

- 404: If a endpoint other than ```/latest``` is called the API will respond with Not-Found.

- 500: There is a chance that the Mock returns an internal server error if one of the files could not be opened.

