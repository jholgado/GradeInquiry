# GradeInquiry
Project By:
Rui Fernandes and Jarod Holgado
CPE 545

This project is a remote gradebook that allows the client to request the specific grade of a student by supplying their name. It also has the option of adding a new grade entry. The server holds all name to grade pairs in a hashmap that is serialized every time it is updated by the client through a remote method invocation. This is to preserve the gradebook in between Server sessions.  

to run the server: 
- first use "start rmiregistry" in the terminal
- then run with "java Server"

to use the client:
- run java Client in a different terminal (or machine)
- follow instructions printed in the terminal