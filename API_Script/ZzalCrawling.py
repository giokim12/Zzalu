import urllib.request
import pymysql
import urllib
from bs4 import BeautifulSoup
from botocore.exceptions import ClientError
import boto3

# mysql과 연결하기 위한 정보를 입력합니다. 
conn = pymysql.connect(host="호스트", user="계정id", password="계정pwd", db="DB이름", charset="utf8" ,port=포트번호)
cur = conn.cursor()


def upload_file(file_path ,gif_name):


    # 생성한 bucket 이름 
    bucket = "zzalu-buket"

    # s3 파일 객체 이름
    object_name = 'AllZzal/'+gif_name

    # aws region 
    location = 'ap-northeast-2'
   
    #자격 증명 
    s3_client = boto3.client(
    's3',
    aws_access_key_id="aws s3 액세스키",
    aws_secret_access_key="aws s3 시크릿키"
    )

     # S3 Upload the file
    try:
        s3_client.upload_file(file_path, bucket, object_name)
    except ClientError as e:
        logging.error(e)
        return None
    #이미지 Url을 만듭니다.
    image_url = f'https://버킷이름.s3.{location}.amazonaws.com/{urllib.parse.quote(object_name)}'
    return image_url

def s3_connection():
    try:
        # s3 클라이언트 생성
        s3 = boto3.client(
            service_name="s3",
            region_name="ap-northeast-2",
            aws_access_key_id="엑세스키",
            aws_secret_access_key="시크릿키",
        )
    except Exception as e:
        print(e)
    else:
        print("s3 bucket connected!") 
        return s3
        
s3 = s3_connection()

for i in range(15001,20000):

    #크롤링할 url을 입력합니다.
    url = 'https://jjalbang.today/view/%EC%B5%9C%EC%8B%A0/'+str(i)
    req = urllib.request.Request(url)
    res = urllib.request.urlopen(url).read()


    soup = BeautifulSoup(res,'html.parser')

    # img 태그의 id가 image_url이며 src의 속성값을 가져옵니다.
    imgUrl = soup.find("img", {'id':'image_url'})['src']
   
    # 이미지 url의 확장자를 가져옵니다. (끝에서 3자리)
    extension = imgUrl[-3:]

    # 확장자가 'gif'인 경우에만 DB에  INSERT 합니다.
    print(extension+' '+str(i))
    if extension == 'gif':

        # img 태그의 id가 image_url이며 alt의 속성값을 가져옵니다.
        tags = soup.find("img", {'id':'image_url'})['alt']

        # 공백문자를 , 로 치환합니다. -> 공백이 한개이거나 두개인 경우가 입력값으로 주어지기 때문이 이 방법으로는 해결 할 수 없습니다.
        #replace_tags = tags.replace('  ', ',')
        #따라서 공백문자 기준으로 단어를 하나씩 가져오겠습니다.
        taglist = tags.split()
        
        # join 함수를 이용하여 리스트에 특정 구분자를 추가하여 문자열로 변환하겠습니다.
        replace_tags = ','.join(taglist)
        replace_tags_to_title='-'.join(taglist).replace('?','')
        #파일이름을 지정합니다.
        custom_file_name = str(i)+"-"+replace_tags_to_title+".gif"
        #파일을 저장할 경로를 입력합니다.
        savelocation = "C:/Users/multicampus/Desktop/오늘의 짤방/today/"+custom_file_name
        
        urllib.request.urlretrieve(imgUrl, savelocation)
        amazon_s3_url = upload_file(savelocation,custom_file_name)
        print(amazon_s3_url)
    
        sql =  "INSERT INTO gifs  (GIF_PATH, TAGS, SOURCE_TYPE) VALUES ('{}' ,'{}',{})".format(amazon_s3_url,replace_tags,3)
        cur.execute(sql)

conn.commit()
conn.close()
print("==========DATABASE 에 반영을 완료하였습니다.=============")
