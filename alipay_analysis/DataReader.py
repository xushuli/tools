import os
import re
from __init__ import baseDir


class DataReader:
    f = None
    lines = []
    itemSep = '\t,'
    contentPattern = '^([A-Z]?\d+.*?\t,.*?\n)'

    @classmethod
    def main(cls):
        cls.readFile()
        cls.readLines()

    @classmethod
    def readFile(cls, filepath=os.path.join(baseDir, "alipay.csv")):
        cls.f = open(filepath, 'r')

    @classmethod
    def readLines(cls):
        for line in cls.f:
            matchedLines = re.findall(cls.contentPattern, line)
            if matchedLines:
                splitedItems = re.split(cls.itemSep, matchedLines[0])
                cls.lines.append(splitedItems)


DataReader.main()