# JettyWithJerseyHomework

This repo is an example of a RESTful API using Jetty & Jersey.

# Note
  - id
  - content
  - created_by
  - created_date
  - lastupdated_by
  - lastupdated_date

# API Instruction
- Create a new Note:

  POST method to /note
  
  Header 
  
    Content-Type: application/type
    
  Body
  
      {
        "content" : "Note content",
        "created_by" : "username"
      }

- Get Note by Id

  GET method to /note/:id

- Get all Note

  GET method to /note

- Search notes

  GET method to /note/search
  
  Query params:
  
    + created_by
    + from (long value only)
    + content

- Edit Note by Id

  PUT method to /note/:id
  
      {
        "content" : "Note content",
        "lastupdated_by" : "username"
      }

- Delete Note By Id

  DELETE method to /note/:id
