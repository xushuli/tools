import os
import sys
from .. import baseDir
from openpyxl import Workbook


class DataSaver:

    defaultWorkBookPath = "data.xlsx"

    @classmethod
    def __createNewWorkBookForPath(cls, wbName=None, uniqueNumber=1):
        if wbName is None:
            wbName = cls.defaultWorkBookPath

        wbPath = os.path.join(baseDir, wbName)

        if os.path.exists(wbPath):
            name, ext = os.path.splitext(wbPath)

            if uniqueNumber > 1:  # has some same name already
                name = name[:-len(str(uniqueNumber-1))]  # remove the tail of uniqueNumber

            newWbName = name + str(uniqueNumber) + ext
            return cls.__createNewWorkBookForPath(newWbName, uniqueNumber + 1)
        else:
            print("create a new workbook %s" % wbPath)
            open(wbPath, 'w').close()
            return wbPath

    @classmethod
    def __fillIntoSheet(cls, tableArray, ws, rowStart=0, colStart=0):
        for rowIndex, row in enumerate(tableArray):
            for colIndex, col in enumerate(row):
                ws.cell(column=colIndex + 1 + colStart,
                        row=rowIndex + 1 + rowStart, value=col)

    @classmethod
    def saveTableIntoNewWorkBook(cls, tableArray, tableHeader=None, wbName=None):
        if tableHeader is not None:
            if len(tableArray[0]) == len(tableHeader):
                tableArray = [tableHeader] + tableArray
            else:
                raise Exception(
                    "Table header is not the same length as table body!")
        wb = Workbook()
        ws = wb.active
        cls.__fillIntoSheet(tableArray, ws)
        wbPath = cls.__createNewWorkBookForPath(wbName)
        wb.save(wbPath)


if __name__ == '__main__':
    # table = [list(range(5)) for line in range(5)]
    # DataSaver.saveTableIntoNewWorkBook(table)
    import json
    table = None

    try:
        table = json.loads(sys.stdin.read())
    except Exception as e:
        raise Exception("你的json有格式问题")

    DataSaver.saveTableIntoNewWorkBook(table)
