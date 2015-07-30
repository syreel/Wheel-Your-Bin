import socket
import sys
import US
import httplib
import urllib
from time import sleep

while True:
	try:
		Usrname = '10askinsw'
		message = str(US.reading(0))
		print message + 'cm'
		connection = httplib.HTTPConnection("yawk.net")
		connection.request("GET", "/sendData.php?username=%s&value=%s" %(Usrname,message))
					

	finally:
		print >>sys.stderr, 'closing socket'
