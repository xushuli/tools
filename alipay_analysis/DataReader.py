import os
import re
from __init__ import baseDir


class DataReader:
    f = None
    lines = []
    itemSep = ','
    contentPattern = '^([A-Z]*\d+.*?\t,.*?\n)'

    @classmethod
    def main(cls):
        cls.readFile()
        cls.readLines()

    @classmethod
    def readFile(cls, filepath=os.path.join(baseDir, "alipay_record_20170304_1514_1.csv")):
        cls.f = open(filepath, 'r')

    @classmethod
    def readLines(cls):
        for line in cls.f:
            matchedSnippets = re.findall(cls.contentPattern, line)
            if matchedSnippets:
                splitedItems = [item.strip() for item in re.split(cls.itemSep, matchedSnippets[0]) if item!='\n']    #split item from raw line
                cls.lines.append(splitedItems)


DataReader.main()