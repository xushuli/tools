import os
import re
from FilePicker import FilePicker
from __init__ import baseDir


class DataReader:
    f = None
    lines = []
    filepattern = '*alipay*.csv'
    itemSep = ','
    contentPattern = '^([A-Z]*\d+.*?\t,.*?\n)'

    @classmethod
    def main(cls):
        cls.readFile()
        cls.readLines()

    @classmethod
    def readFile(cls, filepath=os.path.join(baseDir,FilePicker.listdirOrderByCtime(filepattern).first())):
        cls.f = open(filepath, 'r')

    @classmethod
    def readLines(cls):
        for line in cls.f:
            matchedSnippets = re.findall(cls.contentPattern, line)
            if matchedSnippets:
                splitedItems = [item.strip() for item in re.split(cls.itemSep, matchedSnippets[0]) if item!='\n']    #split item from raw line
                cls.lines.append(splitedItems)


DataReader.main()