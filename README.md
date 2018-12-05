# JettyWithJerseyHomework

This repo is an example of a RESTful API using Jetty with Filter & Jersey.

This repo was deployed to Heroku at: https://tiennhajetty.herokuapp.com/ 

Limit 1 request for each user in 5 sec. If the user reach that limit, their requests will get rejected. (Response code 429)

# Using Hey:
hey -n 300 -c 10 -q 1 -m POST -d '{"content":"This is a test","created_by":"TIEN"}' -H "Content-Type: application/json" https://tiennhajetty.herokuapp.com/note

Test create note with 300 requests total. 10 requests were sent per sec. 

(only 1 request get though per 5 sec => 300 / 5 = 60 r

Summary:

  Total:	30.2598 secs
  
  Slowest:	1.3949 secs
  
  Fastest:	0.2384 secs
  
  Average:	0.2863 secs
  
  Requests/sec:	9.9141
  
  
  Total data:	112230 bytes
  
  Size/request:	374 bytes

Response time histogram:

  0.238 [1]	|
  
  0.354 [289]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  
  0.470 [0]	|
  
  0.585 [0]	|
  
  0.701 [0]	|
  
  0.817 [0]	|
  
  0.932 [0]	|
  
  1.048 [0]	|
  
  1.164 [0]	|
  
  1.279 [0]	|
  
  1.395 [10]	|■


Latency distribution:

  10% in 0.2429 secs
  
  25% in 0.2449 secs
  
  50% in 0.2478 secs
  
  75% in 0.2557 secs
  
  90% in 0.2610 secs
  
  95% in 0.2670 secs
  
  99% in 1.3520 secs
  

Details (average, fastest, slowest):

  DNS+dialup:	0.0368 secs, 0.2384 secs, 1.3949 secs
  
  DNS-lookup:	0.0122 secs, 0.0000 secs, 0.3652 secs
  
  req write:	0.0000 secs, 0.0000 secs, 0.0003 secs
  
  resp wait:	0.2493 secs, 0.2382 secs, 0.2752 secs
  
  resp read:	0.0001 secs, 0.0000 secs, 0.0009 secs
  

Status code distribution:

  [200]	59 responses ~ 60 responses (pretty close :3)
  
  [400]	241 responses


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
