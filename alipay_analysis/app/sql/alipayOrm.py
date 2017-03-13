import os
import sqlalchemy
from sqlalchemy import Column
from sqlalchemy.types import TEXT, FLOAT, INTEGER
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from .. import baseDir

DBNAME = "Alipay"

DB_PATH = os.path.join(baseDir, '%s.db' % DBNAME)
BASEMODEL = declarative_base()
DB_CONNECT_STRING = 'sqlite:///%s' % DB_PATH


class Alipay(BASEMODEL):
    __tablename__ = 'Alipay'
    tradeId = Column(TEXT, primary_key=True)
    orderyId = Column(TEXT, nullable=True)
    tradeBeginingDate = Column(TEXT, nullable=True)
    paidDate = Column(TEXT, nullable=True)
    lastModifingDate = Column(TEXT, nullable=True)
    tradeSrouce = Column(TEXT, nullable=True)
    catagory = Column(TEXT, nullable=True)
    tradeCounterPart = Column(TEXT, nullable=True)
    prodName = Column(TEXT, nullable=True)
    price = Column(TEXT, nullable=True)
    io = Column(TEXT, nullable=True)
    tradeState = Column(TEXT, nullable=True)
    servicePrice = Column(TEXT, nullable=True)
    refund = Column(TEXT, nullable=True)
    comment = Column(TEXT, nullable=True)
    fundState = Column(TEXT, nullable=True)


class DB():

    def __init__(self):
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
        query = self.session.query(Alipay)

        for item in items:
            if not query.get(item.tradeId):
                self.session.add(item)

        self.session.commit()

    def init_db(self):
        BASEMODEL.metadata.create_all(self.engine)

    def drop_db(self):
        BASEMODEL.metadata.drop_all(self.engine)

db = DB()
