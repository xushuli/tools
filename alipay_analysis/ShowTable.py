from pandas import DataFrame
from DataReader import DataReader


class ShowTable():

    def __init__(self, tableArr):
        self.tableArr = tableArr
        self.df = DataFrame(self.tableArr)

    def show(self):
        print(self.df)

    def getDataFrame(self):
        return self.df
