docker run -it  --name mongodb -d mongo --p 27017:27017 
 
docker exec -it mongodb bash

db.createCollection("itemCapped", { capped : true, size : 5242880, max : 50 } )

#mongo admin --username admin --password password

#db.runCommand( { convertToCapped: 'itemCapped',  size : 5242880, max : 50 } )