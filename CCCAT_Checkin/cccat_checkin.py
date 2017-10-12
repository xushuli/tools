import json
import datetime
import threading
import requests as rr
import os
baseDir = os.path.split(__file__)[0]

class CCCAT_Checkin():

    """
    need json like below
    profile = {
        "email": "example@example.com",
        "passwd": "passwd",
        "remember_me": "week"
    }
    """
    _login = 'https://cccat.co/user/_login.php'
    _checkin = 'https://cccat.co/user/_checkin.php'

    def __init__(self, profile):
        self.profile = profile

    def __call__(self,useProxy = False):
        if useProxy == True:
            proxies = {
              "http": "http://127.0.0.1:10800",
              "https": "http://127.0.0.1:10800",
            }
        else:
            proxies = None
            
        # login to fetch these cookies
        r_login = rr.post(self._login, data=self.profile,timeout=10,proxies=proxies)
        # adjust these cookies represent style
        cookies = dict(r_login.cookies.items())
        # checkin with proper cookies
        r_checkin = rr.get(self._checkin, cookies=cookies,proxies=proxies,timeout=10)
        # print the user and result
        return self.profile['email'], r_checkin.json()


class MutliThreadPlusLog(threading.Thread):

    """
            A extended Threading class that offer multiple thread
            run method and the feature to log the target's return
            into a file or stdout
    """

    def __init__(self, func, stdout, mutex):
        self.func = func
        self.mutex = mutex
        self.stdout = stdout
        threading.Thread.__init__(self)

    def run(self):
        # fetch func result to log
        msg = self.func()
        with self.mutex:
            print(msg, file=self.stdout)


def main():
    profiles = None

    # open the json file that contains user profile
    with open(os.path.join(baseDir,'profiles.json'), 'r', encoding="utf-8") as f:
        profiles = json.load(f)

    # make file object and mutex
    file_stdout = open(os.path.join(baseDir,'cccat.log'), 'a')
    stdout_mutex = threading.Lock()

    print(datetime.datetime.now().__str__(), file=file_stdout)

    for profile in profiles:
        cccat = CCCAT_Checkin(profile=profile)    # make cccat checkin plan
        # do that plan in multi threads
        MutliThreadPlusLog(cccat, file_stdout, stdout_mutex).start()

if __name__ == '__main__':
    main()
