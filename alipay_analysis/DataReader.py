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
    def readFile(cls, filepath=None):
        if filepath is None:
            filepath=os.path.join(baseDir,FilePicker.listdirOrderByCtime(cls.filepattern).first())
            print("Reading:%s"%filepath)
        if cls.f is not None:
            return cls.f
        else:
            cls.f = open(filepath, 'r')
            return cls.f

    @classmethod
    def readLines(cls):
        if cls.lines != []:
            return cls.lines

        for line in cls.readFile():
            matchedSnippets = re.findall(cls.contentPattern, line)
            if matchedSnippets:
                splitedItems = [item.strip() for item in re.split(cls.itemSep, matchedSnippets[0]) if item!='\n']    #split item from raw line
                cls.lines.append(splitedItems)
        return cls.lines

if __name__ == '__main__':
    DataReader.readFile()