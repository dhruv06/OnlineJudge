# OnlineJudge
The application is based on providing software delivery using internet platform.The
application is a Docker based sandbox to run code and return the output to your app.
Users can submit their code in any of the supported languages. The system will test the
code in an cloud based virtual environment and give results accordingly.
3.1
How does it work ?
The client-side app submits the code in supported language , , it can be scilab code or
mysql query. User can provide input simultaneously with code . The REST API transfers
the code to server. Here shared memory access is provided between docker and server.
Code is compiled using shell script and response is send back to client-side. Response
can be output or error.
3.2
Technology road-map for an application
• Technology for cloud platform setup
Different services of Amazon web services(AWS) is used for implementing IaaS
like Elastic Compute Cloud (EC2)(for creating virtual machine),Amazon Simple
Storage Service (S3) (for simple storage service),Identity and Access Management
(IAM)(for identity management),Relational Database System (RDS)(for simple
database service).
• Technology for integrating software
Docker is used to provide container service for running different language code in
container and give back result to REST API call.
• Technology for back-end server
For receiving request from client side and connection between virtual machine and
24docker we need to configure apache server.Java(Spring Boot) is used for development
of web services and REST API.
• Technology for client side development
In order to provide interface to cloud based compiler HTML,CSS,Javascript,jQuery
and Angular Js is used.
3.3
Implementation steps
1. Created a user interface for taking code given by user.
• For front-end HTML and Angular Js is used.
• For Back-end Java framework spring boot is used.
• For styling CSS is used.
• Implemented a basic sign up, login system.
• Implemented compiler for C, C++, Java, Python, Scilab, MySQL SaaS.
• Implemented contest environment where user can add contest, questions for
each contest, test cases for each question. Other users can participate and
solve the question. Leader board is also provided for each contest.
2. Create ”Docker Image” of required application
• Install docker in server
• Create ”Dockerfile” of required software if image is not available on docker
hub
• Build docker images for generated Dockerfile
• Integrate docker image with AWS
3. Connect server with ubuntu terminal
• Used process builder class
4. Provide shared memory access
• This provides shared memory access between container and input comes from
host machine.
• It provides access to container to see the code
5. Compile code and send response
• Shell script is created to compile code using docker container
• Output is created may be valid or contains error
• If given code generates image output then image is stored S3(simple storage
service in AWS).
• Then S3 generates URL for given image and provide response to client.
• Output is send to the server.
