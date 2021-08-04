# 📋 BasicList : 가장 단순한 To-do List  
불필요한 기능은 없애고 가장 기본적인 기능만을 갖춘 베이직리스트  
## 기능
1. [리스트 추가](#리스트-추가)  
2. [리스트 삭제](#리스트-삭제)
3. [리스트 우선순위 설정](#리스트-우선순위-설정)
4. [리스트 체크](#리스트-체크)
## 기능 화면
### 리스트 추가
<img src="https://user-images.githubusercontent.com/52291662/128208933-76b2106e-b1a8-4de8-90d9-ad6800356db6.png" width="400" alt="리스트 추가" title="리스트 추가">  
  
### 리스트 삭제  
<img src="https://user-images.githubusercontent.com/52291662/128209041-447394cb-d151-4048-b468-162f5e4f70f9.jpg" width="400" alt="리스트 삭제 전" title="리스트 삭제 전">    <img src="https://user-images.githubusercontent.com/52291662/128210143-f47433c1-95e8-4fed-a468-831b1b332817.png" width="400" alt="리스트 삭제 후" title="리스트 삭제 후">  
- 삭제를 원하는 리스트를 왼쪽으로 스와이프하면 해당 리스트가 삭제된다.  
  
### 리스트 우선순위 설정  
<img src="https://user-images.githubusercontent.com/52291662/128209071-1f0ff29b-5384-40ff-8907-d3d8be2bd8ff.jpg" width="400" alt="우선순위 변경" title="우선순위 변경">  
  
- 리스트를 클릭하면 우선순위가 변경된다.  
- 우선순위는 `GRAY` -> `GREEN` -> `BLUE` -> `RED`순으로 설정할 수 있다.  
  
### 리스트 체크  
<img src="https://user-images.githubusercontent.com/52291662/128209102-d4698f3d-00c4-427e-938d-f3fc0be3fd13.png" width="400" alt="리스트 체크" title="리스트 체크">  
  
## 라이브러리
- LiveData  
- Room Database  
## 느낀점  
1. SQLite가 아닌 Room으로 로컬 데이터베이스를 사용하였고, Room은 쓰레드를 메인에서 사용할 수 없다는 것을 알게되었다.
2. 리싸이클러뷰, 어댑터가 조금 더 친숙해졌다!
3. 혼자의 힘으로 처음부터 끝까지 완성한 첫 어플이기때문에 매우 뿌듯하다!😁
