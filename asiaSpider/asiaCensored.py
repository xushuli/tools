import requests as rr
from bs4 import BeautifulSoup
from SpiderOrm import AsiaCensored
from SpiderOrm import DB

baseLink = "http://68.168.16.151/forum/"
asias = []


class thread():

    def __init__(self, title, catagory, size, responser_amount, view_amount, date):
        pass


def getLink(x):
    return "http://68.168.16.151/forum/forum-58-%d.html" % x


def parse(html):
    soup = BeautifulSoup(html)
    items = soup.findAll('table', id="forum_58")[-1].findAll('tbody')
    return [parseOneItem(item) for item in items]


def parseOneItem(item):
    a = item.findAll('a')
    nums = item.findAll('td', class_="nums")
    link = baseLink + a[2]['href']
    # print(link)
    raw_info = [e.get_text() for e in a + nums if e.get_text()]
    # print(raw_info)
    catagory = raw_info[0]
    title = raw_info[1]
    pub_date = raw_info[3]
    try:
        responser_amount, view_amount = [num.strip()
                                         for num in raw_info[5].split('/')]
    except ValueError as e:
        print(raw_info[5])
        responser_amount, view_amount = (0, 0)
    try:
        size, file_type = [num.strip().upper()
                           for num in raw_info[6].split('/')]
    except ValueError as e:
        print(raw_info[6])
        size, file_type = ("0", "")

    try:
        if "M" in size or "MB" in size:
            size = float(size.split('M')[0]) / 1000
        elif "GB" in size or "G" in size:
            size = float(size.split('G')[0])
    except ValueError as e:
        size = 0

    return AsiaCensored(title=title,
                        link=link,
                        size=size,
                        file_type=file_type,
                        responser_amount=responser_amount,
                        view_amount=view_amount,
                        pub_date=pub_date,
                        catagory=catagory)


if __name__ == '__main__':
    import sys
    import threading

    db = DB()
    db.drop_db()
    db.__init__()

    day0 = int(sys.argv[1])
    day1 = int(sys.argv[2])+1

    def run(i, mutex):
        global asias
        html = rr.get(getLink(i)).content.decode('gbk')
        with mutex:
            asias += parse(html)

    mutex = threading.Lock()

    threads = [threading.Thread(target=run, args=(i, mutex))
               for i in range(day0, day1)]

    for thread in threads:
        thread.start()

    for thread in threads:
        thread.join()

    db.addAll(asias)
