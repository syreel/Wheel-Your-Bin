import socket
import sys
import US
import httplib
import urllib
from time import sleep

#create TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

#connect the socket to the portwhere the server is listening

#server_address = ('localhost', 7779)
#print >>sys.stderr, 'connecting to %s port %s' % server_address 
#sock.connect(server_address)
#print 'just under connect'
#send data with sendall(), receive with recv()
date
while True:
	try:
		#send data
		Usrname = '10askinsw'
		message = str(US.reading(0))
		#sock.sendall(message)
		print message + 'cm'
		connection = httplib.HTTPConnection("yawk.net")
		connection.request("GET", "/sendData.php?username=%s&value=%s" %(Usrname,message))
					

	finally:
		print >>sys.stderr, 'closing socket'
		sock.close()

