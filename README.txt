Data Communication Project 2

Phase 0
Sending as specified number of requests as fast as possible given the number of connections

Goal:
Getting more than 5000 requests/s ~ Completed

How to use:
1)Git clone this thing or whatever, you have to 
2)mvn clean package
3)then you can just ./punch -n <request number> -c <max request concurrent> <url>
4)You may have to edit the file permission for punch through chmod 

How this was done:
Okay so i got retked by reading the questions wrongly and dont have time to change the entire structure of the project so this could
have been a lot better.

Max request concurrent is actually the number of pipe/socket usable per second but
I thought it is the number of requests I can send without reading its response
per seconds.

1) I use threads to split it into 4 pipes (i think) and have jobs to throw into these threads. (ThreadExecutorPool)
2) Then I use external library HttpAsyncClient from Apache to send the requests to my heart desire.
3) I had to synchronize a lot of stuff due to misunderstanding of the questions.






