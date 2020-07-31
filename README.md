# MasterCard API: connected-cities
* Two Cities are connected if one is reachable from other through a series of routes taken
* Provided Origin & Destination this api returns `yes` if they are connected otherwise `no`
* Any invalid input should result in `no` response

### Exposed Endpoint: 
```
http://localhost:8080/connected?origin=city1&destination=city2
```
### Available Routes: 
```
{(Boston, New York),(Philadelphia, Newark),(Newark, Boston),(Trenton, Albany)}
```
### Sample Requests:
* http://localhost:8080/connected?origin=Boston&destination=Newark
```Should return yes```
* http://localhost:8080/connected?origin=Boston&destination=Philadelphia 
```Should return yes``` 
* http://localhost:8080/connected?origin=Philadelphia&destination=Albany 
```Should return no```

### Swagger Api Docs
Swagger Api Docs are available at location [swagger-ui](http://localhost:8080/swagger-ui.html)
