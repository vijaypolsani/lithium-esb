import zmq
import sys

# ZeroMQ Context
context = zmq.Context()

# Define the socket using the "Context"
sock = context.socket(zmq.REQ)
sock.connect("tcp://10.240.180.18:5556")

# Send a "message" using the socket
sock.send(" ".join(sys.argv[1:]))
print sock.recv()
