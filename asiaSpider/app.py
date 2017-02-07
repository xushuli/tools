from flask import Flask, render_template
from flask.ext.sqlalchemy import SQLAlchemy
from SpiderOrm import AsiaCensored as Asia
import re

app = Flask(__name__)
db = SQLAlchemy(app)

app.config['SQLALCHEMY_DATABASE_URI'] = "sqlite:///asiaCensored.db"
app.debug=True

@app.context_processor
def inject_user():
    f= lambda link:re.match("^(http://).*",link)
    return dict(islink=f,str=str)

class AsiaCensored(db.Model):
    __tablename__ = 'asiaCensored'
    id = db.Column(db.INTEGER,primary_key=True)
    title = db.Column(db.TEXT,nullable=True)
    link = db.Column(db.TEXT,nullable=True)
    catagory = db.Column(db.TEXT, nullable=True)
    size = db.Column(db.FLOAT, nullable=True)
    file_type = db.Column(db.TEXT, nullable=True)
    responser_amount = db.Column(db.INTEGER, nullable=True)
    view_amount = db.Column(db.INTEGER, nullable=True)
    pub_date = db.Column(db.TEXT, nullable=True)


@app.route("/show/<sql>")
def hello(sql):
    result = db.session.execute(sql).fetchall()
    return render_template("index.html",items=result,sql=sql)

@app.route("/get/<start>to<end>")
def get(start,end):
    return "get"+start+end

if __name__ == "__main__":
    app.run()