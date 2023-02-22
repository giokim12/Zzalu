import json
from urllib import parse, request

import pymysql

############################ mariadb 연결
conn = pymysql.connect(host="127.0.0.1", user="root", password="ssafy", db="ZZALU", charset="utf8")
cur = conn.cursor()

############################ file에서 keyword 추출
# file 읽
keyword_file = open("./search_keyword.txt", "r", encoding='UTF8')
# print(keyword_file.readlines());

keyword_lists = keyword_file.readlines();

search_dict = {}
tag = []
keyword = []
for keywords in keyword_lists:
    if(keywords.strip()):
        if('#' in keywords.strip()):
            tag.append(keywords.strip().split("#")[-1].strip())
        elif('-' in keywords.strip()):
            print(tag);
            print(keyword)
            tag_string = ','.join(s for s in tag)
            search_dict[tag_string] = keyword
            tag.pop()
            keyword = []
        else:
            keyword.append(keywords.strip())

############################
print(str(type(keywords)))
print(search_dict)


############################ 추출한 keyword를 기반으로 parsing
gif_dict = {}
for search in search_dict:
    search_keywords = []
    search_keywords.append(search.split(',')[-1])
    search_keywords += search_dict[search]

    for keyword in search_keywords:
        url = "http://api.giphy.com/v1/gifs/search"
        params = parse.urlencode({
            "q": keyword,
            "api_key": "ODoOz3VrkVbbaAlIBxDDH2WbSj7GPMvW",
            "limit": "100"
        })
        
        with request.urlopen("".join((url, "?", params))) as response:
            data = json.loads(response.read())
        
        # print(json.dumps(data, sort_keys=True, indent=4))
        # print(data["data"][0]["embed_url"])
        
        for d in data["data"]:

            search_tag_list = search.split(",")
            if(keyword not in search_tag_list):
                search_tag_list.append(keyword)

            # 기존에 keyword 추가
            if(d["images"]["original"]["url"] in gif_dict) : 
                if(keyword not in gif_dict[d["images"]["original"]["url"]]["tags"]):    # keyworkd 있는지 확인
                    gif_dict[d["images"]["original"]["url"]]["tags"].append(keyword)

            # gif_dict 추가
            else :
                gif_dict[d["images"]["original"]["url"]] = {
                    "gif_path": d["images"]["original"]["url"],
                    "source" : d["source"],
                    "source_post_url" : d["source_post_url"],
                    "source_tld" : d["source_tld"],
                    "title" : d["title"],
                    "import_datetime" : d["import_datetime"] ,
                    "tags" : search_tag_list,
                }

            print(gif_dict[d["images"]["original"]["url"]])
            print("")

            exists_sql = ""
            insert_sql = ""
            update_sql = ""
            select_sql = ""
            try:
                if("'" not in d["source"]):
                    exists_sql = "SELECT EXISTS (SELECT * FROM GIFS WHERE GIF_PATH='" + d["images"]["original"]["url"]  + "') AS SUCCESS"
                    cur.execute(exists_sql)
                    exists_sql_return = cur.fetchone()[0]

                    if(exists_sql_return == 1):    # 이미 존재하는 경우
                        select_sql = "SELECT TAGS FROM GIFS WHERE GIF_PATH='" + d["images"]["original"]["url"]  + "'"
                        cur.execute(select_sql)
                        get_tags = cur.fetchone()[0]
                        get_tags_list = list(set(get_tags.split(",") + search_tag_list))
                        update_sql = "UPDATE GIFS SET TAGS='" + ",".join(s for s in get_tags_list) + "'"

                    else:
                        insert_sql = "INSERT INTO GIFS (GIF_PATH, TAGS, SORUCES, SORUCES_POST_URL, SORUCES_TLD, IMPORT_DATETIME, SOURCE_TYPE) VALUES(\"" + d["images"]["original"]["url"] + "\",\"" + ','.join(s for s in gif_dict[d["images"]["original"]["url"]]["tags"]) + "\",\"" + d["source"] + "\",\"" + d["source_post_url"] + "\",\"" +  d["source_tld"]+ "\",\"" +  d["import_datetime"] + "\"," + "2" + ")"
                        print(insert_sql)
                        cur.execute(insert_sql);
            except pymysql.err.InternalError as e:
                code, msg = e.args
                print("=== sql execute failed ===")
                print("Error code : ", code)
                print("Error message : ", msg)
                print(insert_sql)
                print()
            else:
                print("=== sql execute success ===")
                print()

conn.commit();
conn.close();
print("=== DB 반영 완료 ===")
