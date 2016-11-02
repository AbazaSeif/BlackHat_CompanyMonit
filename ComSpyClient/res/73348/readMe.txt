/////********************raxix****************************//////

This code shows a simple TCP which has checksum, error control, packet loss and simulation for a random network.
You can give the time slice, packet loss, corrupt packet, and random number range. The time slice indicates the time
it takes the packet to reach from A_side(client) to B_Side(server), the more the packet loss probability the more
packets will be lost before send to the server. The higher the packet corrupt probability the more packets will be 
corrupted before send to the server. If random seed is high, the range of random number generated will be more. It
will be easier for you to trace the program if the range is less. ex: 5. Try to use a timer value about 1000 if you
want to send the message from client to server.

I believe there is a problem with the timer. It is not a real timer but it gives a general idea of how it should be
working. When I did this assignment I couldn't make the timer work as I wanted so I use random numbers.

Don't forget to change the IP in A_side code. Write the B_side IP address there.

You can do what ever you want to do with the program.
Enjoy the code.

If any problems you can contact me at raxix@yahoo.co.uk


>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>