# NEWS PORTAL
A REST API for querying and retrieving scoped news and information

## Description
The Java application is a REST API that provides access to endpoints that allow employees to create and retrieve news/articles/posts available to all employees and those available by navigating to a department. Aside from that there are endpoints to create and retrieve departments and users. There are also endpoints that allow an admin to delete users let go by the company and allow a user/admin that created a post to delete the corresponding post.

**Database to be moved from Heroku**

#### By **[Lynn Nyangon](https://github.com/AnnaL001)**

## Setup/Installation Requirements

- Using a mobile device/laptop ensure you have access to stable internet connection
- To access the Java application's code from your GitHub repository, you can fork the repository main's branch via the 'Fork' button.
- To access the Java application's code locally, you can clone the main branch or download the ZIP folder via the 'Code' button
- Once locally, you can view/run the Java application's code via a text editor(VS Code or Sublime Text) or an IDE(IntelliJ).
- In the case of IntelliJ, to navigate you can reference their documentation https://www.jetbrains.com/help/idea/getting-started.html
- Otherwise to access the API endpoints online navigate to the link below <br>
 [Live Link](https://anna-news-portal-api.herokuapp.com/)

## Endpoints
### GET & DELETE
| **URL**                              | **HTTP VERB**       | **DESCRIPTION**                      |
|:--------------------------------------|:--------------------|:-------------------------------------|
| /departments                         | GET                | Retrieve departments                  |    
| /users                               | GET                | Retrieve users                        |
| /departments/:id                     | GET                | Retrieve a department                 |
| /users/:id                           | GET                | Retrieve a user                       |
| /departments/:id/users              | GET                | Retrieve users/employees in a department |
| /departments/:id/news               | GET               | Retrieve news associated with a department |
| /news                               | GET               | Retrieve general news                   |
| /topics                             | GET               | Retrieve news topics                    |
| /users/:userId/news/:newsId/delete  | DELETE            | Delete general news post by owner (user) |
| /departments/:departmentId/users/:userId/news/:newsId/delete | DELETE     | Delete department news post by owner(user)|
| /admins/:adminId/users/:userId/delete | DELETE  | Delete user by admin |
| /departments/:departmentId/admins/:adminId/news/:newsId/delete | DELETE | Delete department news post by owner(admin) |
| /admins/:adminId/news/:newsId/delete | DELETE | Delete general news post by owner (admin) |
### POST
* URL: /departments <br>
  HTTP verb: POST <br>
  Description: Add a department <br>
  Sample input
  ``` 
  {
    "name": "Finance",
    "description": "Handles all things money"
  } 
  ```

* URL: /departments/:id/users <br>
  HTTP verb: POST <br>
  Description: Add a user in department <br>
  Sample input
  ```
  {
    "name": "Lucian Doe",
    "position": "Project manager"
  } 
  ```
  
* URL: /users/:id/news <br>
  HTTP verb: POST <br>
  Description: Add a general news post by a user <br>
  Sample input
  ``` 
  {
    "title": "Email 2FA Authentication",
    "content": "It is recommended to implement 2FA authentication in order to add an extra layer"
  } 
  ```
  
* URL: /departments/:departmentId/user/:userId/news <br>
  HTTP verb: POST <br>
  Description: Add a department news post by a user <br>
  Sample input
  ```
  {
    "title": "A message from the incoming department head",
    "content": "As we usher in the next chapter in the company's operation, the incoming department head has a few words of encouragement" 
  } 
  ```
 
 * URL: /departments/:id/admins/ <br>
   HTTP verb: POST <br>
   Description: Add an admin to a department <br>
   Sample input 
   ``` 
   {
    "name": "Bobby Doe",
    "position": "Systems Admin"
   }
   ```
   
 * URL: /admins/:id/news <br>
   HTTP verb: POST <br>
   Description: Add a general news post by an admin<br>
   Sample input 
   ``` 
   {
    "title": "Email two factor Authentication",
    "content": "It is recommended to implement two factor authentication in order to add an extra layer"
   }
   ```
   
 * URL: /departments/:departmentId/admins/:adminId/news <br>
   HTTP verb: POST <br>
   Description: Add a department news post by admin <br>
   Sample input
   ``` 
    {
      "title": "A message from the incoming department head",
      "content": "As we usher in the next chapter in the company's operation, the incoming department head has a few words of encouragement"
    }
    ```
  
  * URL: /users/:userId/news/:newsId/topics <br>
    HTTP verb: POST <br>
    Description: Add topics to general news post by user <br>
    Sample input
    ```
    [
     {"name": "Systems security"},
     {"name": "Penetration testing"},
     {"name": "Cyber security"}
    ]
    ```
    
  * URL: /admins/:adminId/news/:newsId/topics <br>
    HTTP verb: POST <br>
    Description: Add topics to general news post by admin <br>
    Sample input
    ```
    [
     {"name": "Systems security"},
     {"name": "Penetration testing"},
     {"name": "Cyber security"},
     {"name": "Hacking"}
    ]
    ```
    

## Dependencies

- JUnit 5 
- Spark Framework
- Gson
- Gradle
- Maven
- Joda Time

## Technologies Used
- Java 
- Postgresql
- Postman : For testing API endpoints

## Support and contact details

In case of any queries you can reach out via email; lynn.nyangon@gmail.com

### License

MIT License
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.<br>
Copyright (c) 2022 **Lynn Nyangon**
