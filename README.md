# TaskManagement


## Swagger

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs

## Example:

Create Task:

```json
{
  "header": "A",
  "description": "B",
  "status": "PENDING",
  "priority": "HIGH",
  "assigned": "assigned@example.com"
}
```

Get Tasks by 
params:

* size
* page
* creator
* assigned

```http request
http://localhost:8080/api/tasks?size=10&page=0&assigned=assigned@example.com&creator=user@example.com
```


