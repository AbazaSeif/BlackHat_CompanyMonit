                     The TCP-Switch Application beta 5
------------------------------------------------------------------------------

Requirements:
-------------

JDK 1.4 or newer

About:
------

The TCP-Switch Application allows tunneling HTTP, HTTPS, HTTPS-Proxy, passive FTP 
and SSH request on one port to different hosts and ports.

This can be usefull if there are limited port numbers because of a firewall.

Tunnels can be configured depending on the client IP and port, the
current date and time.


The project has just started and is at the very beginning.
So there is not much documentation and not everything is perfect.

How To Install:
---------------

Copy the file tcpswitch.jar and tcpswitch.config to any location. 
Edit tcpswitch.config to match your needs.
The application is started with 
  java -jar tcpswitch.jar

Syntax:
-------

  java -jar tcpswitch [-config=<configfile>] [-daemon] [-loghostname] [-debug]
    -config=<configfile>: use <configfile> instead of "./tcpswitch.config"  
    -daemon: do not enter interactive mode
    -loghostname: log hostname in addition to ip address
    -debug:  verbose logging


Configfile:
-----------

The configfile is named tcpswitch.config and has to be in the current working 
directory. An other config file my be used with the -config=... option

An simple example config:
[--- tcpswitch.config ---]
listen (8555) {
	forward sf.net:80
}
[------------------------]
With this configuration all incoming traffic on port 8555 will be
redirected to sf.net port 80.

More than one listener can be defined:
[--- tcpswitch.config ---]
listen (8555) {
	forward sf.net:80
}
listen (8556) {
	forward localhost:8555
}
[------------------------]
Be carefull not to define circles, there is no detection    ;)

Client IP and port can be used as conditional:
[--- tcpswitch.config ---]
listen (8555) {
	check ($ip = 127.0.0.1) {
		forward sf.net:80
	}
	check ($ip >= 10.10.10.0) {
		check ($ip <= 10.10.10.255) {
			forward fsf.net:80
		}
	}
	check ($port < 1024) {
		reject
	}
	forward gnu.org:80
}
[------------------------]
requests from localhost will be redirected to sf.net,
request from the net 10.10.10.0/24 will be redirected to fsf.net
any other (external) requests will be forwarded to gnu.org
as long as the client port is above 1024

For each incoming connection the listen config is evaluated top down 
and is finished as soon as a forward or reject statement is reached.
If no forward or reject is reached the connection is rejected.

time and date can also be checked:
[--- tcpswitch.config ---]
listen (8555) {
	check ($date = 2004-02-29) {
		reject
	}
	check ($time < 08:00:00) {
		reject
	}
	check ($time < 18:00:00) {
		forward sf.net:80
	}
	/* time >= 18:00:00 */
	reject
}
[------------------------]
The forward is only active between 8:00 and 18:00,
On february 29th 2004 no forwarding is done.

Client protocol can also be checked:
[--- tcpswitch.config ---]
listen (443) {
	check ($protocol = HTTP) {
		check ($time < 12:00:00) {
			forward www.gnu.org:80,HTTPrewritehost
		}
		/* $time >= 12:00:00 */
		forward www.fsf.org:80,HTTPrewritehost
	}
	check ($protocol = HTTPS) {
		forward sourceforge.net:443
	}
	check ($protocol = HTTPSPROXY) {
		forward $proxyhost:$proxyport,HTTPSPROXYestablish
	}
	check ($protocol = FTP) {
		forward ftp.debian.org:21
	}
	check ($protocol = SSH) {
		forward localhost:22
	}
	reject
}
[------------------------]
HTTP requests antemeridian will be forwarded to gnu.org and
post meridiem to fsf.org.
The Parameter HTTPrewritehost causes the host parameter in 
the HTTP header to be rewritten accordingly. This is necessary
for virtual hosts.
HTTPS requests will be redirected to sourceforge.net
HTTPS proxy requests will be proxied.
FTP requests will be forwarded after a short while (3 seconds)
to debian.org.
SSH requests will be forwarded after a medium while (8 seconds)
to localhost.

It is important to check for FTP before SSH, because the check 
for the SSH-Protocol breaks the FTP-client .


Quick Tutorial:
---------------

Use the last config file above.
On the command line (unix or dos) enter the following
  java -jar tcpswitch.jar -debug
If the port 443 is not available or you are not root you can 
choose any other port, but the HTTPS example with sf.net will 
then redirect to sf.net:443

The interactive mode of TCP-Switch is started. 

Start a browser and enter the following 
  URL: http://localhost:443/
the homepage of GNU will appear.
Now open the following URL in your Browser
  URL: https://localhost/
There will be a warning about URL mismatch of the certificate,
because "localhost" does not match "sf.net".
After accepting the certificate You will see the Homepage of sourceforge.

Now you can start a ftp client from another commandline 
  ftp -p localhost 443
the prompt is delayed for 3 seconds.
Afterwards you have 5 seconds time to enter your name,
otherwise TCP-Switch assumes a SSH client.
Currently only passive FTP (-p) is supported

Now start from another commandline a ssh client:
  ssh -p 443 localhost
If you have an ssh daemon listening you will see the login prompt as if
you entered "ssh localhost". Otherwise you get a connection closed message.
The Login prompt will be delayed for 8 seconds. This is neccessary to surely
distinct HTTP[S] and FTP requests from SSH requests.

You can set localhost:443 as HTTP-proxy and browse to any https website 
(note HTTP proxying is not yet supported).

In interactive Mode you may enter 'status' to see a list of all connections.
HTTP[S] connections are closed immediately after receiving the data, while
SSH connections stay open until you log out.

If you change the config file you can reload the configuration with the
command 'reconfig' (there seems to be a bug, so try it twice).

To stop tunneling just enter 'quit'. There will be an error message about
canceled sockets which can be ignored.


Advanced features:
------------------

With the HTTPSPROXY protocol some useful things can be done.
Assume the following scenario:

                           +-- intrahost1
                           |
localhost === public.net --+-- intrahost2
                           |
                           +-- intrahostX
                           |
                           +-- wwwhost

[--- localhost-config ---]
listen (9122) {
    forward public.net:443,HTTPSPROXYconnect(intrahost1:22)
}
listen (9222) {
    forward public.net:443,HTTPSPROXYconnect(intrahost2:22)
}
[-------------------------]

[--- public.net-config ---]
listen (443) {
    check ($protocol = HTTPSPROXY) {
        forward $proxyhost:$proxyport,HTTPSPROXYestablish
    }
    forward wwwhost:443
}
[------------------------]

Using theese configurations on localhost and public.net 
You can also reach wwwhost via your browser https://public.net 
and the two intrahosts via "ssh -p 9x22 localhost".

Some SSH clients like putty directly support HTTP-Proxy and
could also access intrahostX . This can be restricted in 
the public.net config:

[--- public.net-config ---]
listen (443) {
    check ($protocol = HTTPSPROXY) {
        check ($proxyhost = intrahost1) {
            forward intrahost1:22
        }
        check ($proxyhost = intrahost2) {
            forward intrahost1:22
        }
        reject
    }
    forward wwwhost:443
}
[------------------------]
Only intrahost1 and intrahost2 will be proxied, 
the port is always set to 22.


TODOes:
-------

 * HTTPSPROXY should support also HTTP Proxying
 * loadbalancing, e.g. forward host1:21+host2:21,LBsticky
 * bandwith limitation, e.g. forward host1:21,maxspeed=64k
 * other protocols

Limitations and known problems:
-------------------------------

Only HTTP, HTTPS, HTTPS-Proxy, passive FTP and SSH are supported

Bugs, Feedback:
----------------------------

In case you run into problems, or if you have any suggestions, please send me
an email at ferenc_hechler@users.sourceforge.net. 
Updates can be found on http://sourceforge.net/projects/tcpswitch

Ferenc Hechler          
ferenc_hechler@users.sourceforge.net

