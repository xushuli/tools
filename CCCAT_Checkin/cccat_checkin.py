import json
import datetime
import threading
import requests as rr


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

    def __call__(self):
        # login to fetch these cookies
        r_login = rr.post(self._login, data=self.profile)
        # adjust these cookies represent style
        cookies = dict(r_login.cookies.items())
        # checkin with proper cookies
        r_checkin = rr.get(self._checkin, cookies=cookies)
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


if __name__ == '__main__':
    profiles = None

    # open the json file that contains user profile
    with open('./profiles.json', 'r') as f:
        profiles = json.load(f)

    # make file object and mutex
    file_stdout = open('cccat.log', 'a')
    stdout_mutex = threading.Lock()

    print(datetime.datetime.now().__str__(), file=file_stdout)

    for profile in profiles:
        cccat = CCCAT_Checkin(profile=profile)    # make cccat checkin plan
        # do that plan in multi threads
        MutliThreadPlusLog(cccat, file_stdout, stdout_mutex).start()
