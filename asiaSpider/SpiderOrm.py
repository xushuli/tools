import sqlalchemy
from sqlalchemy import Column
from sqlalchemy.types import TEXT, BLOB, FLOAT, INTEGER
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm.exc import NoResultFound
from datetime import datetime
import datetime
import os
import sys
import platform
from path import Path

DBNAME = "asiaCensored"

DB_CONNECT_STRING = 'sqlite:///%s.db' % DBNAME
DB_PATH = './%s.db' % DBNAME
BASEMODEL = declarative_base()


class AsiaCensored(BASEMODEL):
    __tablename__ = 'asiaCensored'
    id = Column(INTEGER,primary_key=True)
    title = Column(TEXT,nullable=True)
    link = Column(TEXT,nullable=True)
    catagory = Column(TEXT, nullable=True)
    size = Column(FLOAT, nullable=True)
    file_type = Column(TEXT, nullable=True)
    responser_amount = Column(INTEGER, nullable=True)
    view_amount = Column(INTEGER, nullable=True)
    pub_date = Column(TEXT, nullable=True)

    def parseDatetime(self, datetime_str):
        return datetime.strptime(date_str, '%Y-%m-%d %H:%M')

    def parseDate(self, date_str):
        return datetime.strptime(date_str, '%Y-%m-%d')


class DB():

    def __init__(self):
        self.initFlag = Path(DB_PATH).exists()

        self.engine = self.__createEngine()
        self.session = self.__createSession(self.engine)

        self.init_db()

    def __createEngine(self):
        engine = sqlalchemy.create_engine(DB_CONNECT_STRING, echo=False)
        return engine

    def __createSession(self, engine):
        session = sessionmaker(bind=engine)()
        return session

    def add(self, orm_obj):
        self.session.add(orm_obj)
        self.session.commit()

    def addAll(self, items):
        for item in items:
            self.session.add(item)
        self.session.commit()

    def init_db(self):
        BASEMODEL.metadata.create_all(self.engine)

    def drop_db(self):
        BASEMODEL.metadata.drop_all(self.engine)


if __name__ == '__main__':
    db = DB()
