from DataReader import DataReader
from DataSaver import DataSaver
from alipayOrm import db, Alipay

if __name__ == '__main__':
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
    # print(DataReader.lines[1])
    # DataSaver.saveTableIntoNewWorkBook(DataReader.lines,tableHeader=tableHeader)
    
    objs = [Alipay(**dict(zip(tableHeader, tableRow))) for tableRow in DataReader.lines]

    db.addAll(objs)

    for obj in objs:
    	print(obj.price,obj.prodName)
