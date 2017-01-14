import requests as rr
import json
import threading


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
        print(self.profile['email'], r_checkin.json())

if __name__ == '__main__':
    profiles = None

    with open('./profiles.json', 'r') as f:
        profiles = json.load(f)

    for profile in profiles:
        cccat = CCCAT_Checkin(profile=profile)
        threading.Thread(target=cccat).start()
