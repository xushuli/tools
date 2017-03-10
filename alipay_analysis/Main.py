from DataReader import DataReader
from DataSaver import DataSaver
from alipayOrm import db, Alipay
from SQLStatements import SQLStatements
from ShowTable import ShowTable

tableHeader = ['tradeId',
               'orderyId',
               'tradeBeginingDate',
               'paidDate',
               'lastModifingDate',
               'tradeSrouce',
               'catagory',
               'tradeCounterPart',
               'prodName',
               'price',
               'io',
               'tradeState',
               'servicePrice',
               'refund',
               'comment',
               'fundState']


def AddCsvToDB():
    # print(DataReader.lines[1])
    # DataSaver.saveTableIntoNewWorkBook(DataReader.lines,tableHeader=tableHeader)
    objs = [Alipay(**dict(zip(tableHeader, tableRow)))
            for tableRow in DataReader.readLines()]
    print('Running db add method')
    db.addAll(objs)
    print('All Done!')

if __name__ == '__main__':
    itemsInThisWeek = SQLStatements.get('everyDayExpenseInWeek')(0)
    print(itemsInThisWeek.getFormatedSQL())
    items =itemsInThisWeek.execute().fetchall()
    print(len(items))
    print(items)
