import glob
import os
from .. import baseDir

class FileList(list):

    """docstring for FileList"""

    def __init__(self, filelist):
        list.__init__([])
        self.extend(list(filelist))

    def first(self):
        try:
            return self[0]
        except IndexError:
            raise IndexError("Sorry, doesn't find that file!")

    def last(self):
        try:
            return self[-1]
        except IndexError:
            print("Sorry, doesn't find that file!")
            raise IndexError

    def all(self):
        return self


class FilePicker:

    @classmethod
    def listdir(cls, pattern):
        pattern = os.path.join(baseDir,pattern) #pattern under the base dir
        return FileList(glob.glob(pattern))

    @classmethod
    def listdirOrderByCtime(cls, pattern, desc=True):
        filename_timestamp = [
            (filename, os.path.getctime(filename)) for filename in cls.listdir(pattern)]
        tempForSort = sorted(
            filename_timestamp, key=lambda x: x[1], reverse=desc)  # 按时间降序排列
        return FileList([filename for filename, timestamp in tempForSort])

if __name__ == '__main__':
    print(FilePicker.listdirOrderByCtime("*").all())
