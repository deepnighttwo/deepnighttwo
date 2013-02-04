

if __name__ == "__main__":
    js = file("C:\\mymise\\myprojects\\myutilities\\scriptinjava\\src\\com\\deepnighttwo\\scriptinjava\\test.js")
    all = ""
    for line in js:
        all = all + line.strip()
    print all