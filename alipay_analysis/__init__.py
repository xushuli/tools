import os

if not __file__:
	baseDir = os.getcwd()
else:
	baseDir = os.path.split(__file__)[0]