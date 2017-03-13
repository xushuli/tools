import os
import json
from .alipayOrm import db
from .. import baseDir


class ExcutedResult(object):

    """Wrap the result of SQLStatement's execute
       @executedResult:raw result from db.session.execute
       @header:the column name correspond to every column of result"""

    def __init__(self, executedResult, header=None):
        self.executedResult = executedResult
        self.header = header

    def fetchone(self,withHeader=True):
        if self.header is not None and withHeader:
            return dict(zip(self.header, self.executedResult.fetchone()))
        else:
            return self.executedResult.fetchone()

    def fetchall(self,withHeader=True):
        if self.header is not None and withHeader:
            return [dict(zip(self.header, one))
                    for one in self.executedResult.fetchall()]
        else:
            return self.executedResult.fetchall()


class SQLStatement(object):

    """@templateName: the sql template filename under the path of .sql/templates/
       @context: is the dict that used to fomat the template string"""

    templatesFolder = os.path.join(baseDir, 'app/sql/templates/')
    headersFolder = os.path.join(baseDir, 'app/sql/headers/')
    templateExt = '.tpl'
    headerExt = '.hdr'

    def __init__(self, templateName, context):
        self.templateName = templateName
        self.headerName = templateName
        self.context = context
        self.formatedSQL = None
        self.header = None

    def _getTemplatePath(self):
        return os.path.join(self.templatesFolder, self.templateName + self.templateExt)

    def _getHeaderPath(self):
        return os.path.join(self.headersFolder, self.headerName + self.headerExt)

    def getFormatedSQL(self):
        if self.formatedSQL is None:
            self.formatedSQL = open(self._getTemplatePath(), 'r',
                                    encoding='utf-8').read().format(**self.context)
        return self.formatedSQL

    def getHeader(self):
        if self.header is None:
            self.header = json.load(
                open(self._getHeaderPath(), 'r', encoding='utf-8'))
        return self.header

    def execute(self):
        return ExcutedResult(db.session.execute(self.getFormatedSQL()),
                             self.getHeader())


if __name__ == '__main__':
    data = SQLStatement('itemsInWeek', {'daysFromNow': 0}).execute()
