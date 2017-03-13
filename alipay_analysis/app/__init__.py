import os
from path import Path

if not __file__:
	baseDir = os.getcwd()
else:
	baseDir = Path(__file__).dirname().parent.__str__()