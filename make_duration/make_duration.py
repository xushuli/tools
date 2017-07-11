from path import Path
from hsaudiotag import mp4
from pandas import DataFrame
from os.path import exists


def make_name(name, diff_time=0):
    name_t = name + '%s.xlsx'
    if diff_time == 0:
        diff = ""
    else:
        diff = "_" + str(diff_time)

    while True:
        if exists(name_t % diff):
            return make_name(name, diff_time + 1)
        else:
            return name_t % diff


#############################################

cwd = Path('.')
name = make_name('video_duration')

print("cwd is %s" % cwd.abspath())
print(DataFrame(cwd.files("*.mp4")))
print(name + " 将会被创建!")



if __name__ == '__main__':
    a = input("直接按回车以开始，输入任何字符按回车以结束。")
    if a == "":
        mp4files =[ mp4.File(file) for file in cwd.files("*.mp4")]

        # make filename and duration (in minute) tuple to csv
        df = DataFrame(
            list(map(lambda x: (x._fp.name.__str__(), x.duration / 60), mp4files)))

        df.to_excel(name,encoding="gb2312")

        print(df,"all done",sep="\n")
