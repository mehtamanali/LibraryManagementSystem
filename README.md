A simple Spring Boot REST API for managing a library's books.
Supports CRUD operations with Spring Security and Swagger (OpenAPI) for API documentation.

Access the Application :
API based URL 
http://localhost:8080

Swagger UI
http://localhost:8080/swagger-ui/index.html

Authentication Details
This project is secured with Basic Authentication.


Username	Password	Role
admin	admin	ADMIN
user	user	USER
Admin has full access to create, update, delete books.

User can only fetch books.

To authorize in Swagger, click on the Authorize 🔒 button and enter the credentials.

API Endpoints

Method	Endpoint	Access	   Description
POST   	/book	   ADMIN  	  Create a new book
GET	    /book	   USER/ADMIN	Get all books
GET	  /book/{id}	USER/ADMIN	Get book by ID
PUT	   /book/{id}	   ADMIN	      Update book by ID
DELETE	/book/{id}	ADMIN	   Delete book by ID
