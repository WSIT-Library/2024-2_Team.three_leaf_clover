> # **2024년도 2학기 캡스톤디자인**  
> Team 세잎클로버 (김태완, 안성모, 유동훈)
<br>
<br>

# 🌟 지체장애인을 위한 행복충전소 지도 어플리케이션 🌟 <br> <<strong>해피드림</strong>> 


<br>

## 개발 환경

| 구분             | 기술                        | 버전       |
|------------------|-----------------------------|------------|
| **웹 서버**      | JDK                         | 17         |
| **웹 서버**      | Java                        | 17.0.6     |
| **웹 서버**      | Spring Boot                 | 3.3.4      |
| **웹 서버**      | Spring Security             | 6.1.0      |
| **웹 서버**      | Spring Data JPA             | 3.3.4      |
| **웹 서버**      | Thymeleaf                  | 3.1.2      |
| **웹 서버**      | Swagger                     | 2.6.0      |
| **웹 서버**      | Gson                        | 2.10.1     |
| **웹 서버**      | MySQL                       | 8.0        |
| **웹 서버**      | Gradle                      | 7.6        |
| **웹 서버**      | JQuery                      | 3.5.1      |
| **웹 서버**      | Bootstrap                   | 5.3.0      |
| **안드로이드 앱** | JDK                         | 17         |
| **안드로이드 앱** | Android Studio              | Flamingo   |
| **안드로이드 앱** | Retrofit                    | 2.9.0      |
| **안드로이드 앱** | Naver Maps SDK              | 3.19.1     |
| **안드로이드 앱** | Play Services Location      | 21.0.1     |
| **안드로이드 앱** | Proj4j                      | 1.1.0      |
| **안드로이드 앱** | Material Components         | 1.9.0      |

<br>

## 🌏 지체장애인을 위한 행복충전소 지도 어플리케이션 : "해피드림" <br><br> 👥 TEAM 세잎 클로버 <br>
<br>

### 📖 목차
1. [프로젝트 설명](#-프로젝트-설명)
2. [프로젝트 목표](#-프로젝트-목표)
3. [기대효과](#-기대효과)
4. [프로젝트 구조](#-프로젝트-구조)
5. [기술 스택](#-기술-스택)
6. [프로젝트 기여자](#-프로젝트-기여자)

--- 
### 📜 프로젝트 설명
현재 대전시에는 지체장애인들을 위한 전동휠체어를 충전하는 행복충전소(전동휠체어 급속충전기)가 상용화 되고 있습니다. 사용자는 이러한 행복충전소를 기존에 제공되고 있던 행복충전소 위치 파악 어플리케이션들을 이용해 사용하고 있습니다. 하지만 해당 애플리케이션들은 행복충전소의 위치만 알려주기 때문에 현재 사용이 가능한지 알 수 없으며, 행복충전소  사용빈도, 고장여부 등을 알 수 없다는 점을 가지고 있습니다. 본 프로젝트는 기존 지도 기반의 솔루션에 충전소 사용 가능 여부, 충전소 분석(사용빈도, 전력량, 고장여부 등), 지체장애인을 위한 편의 기능 등을 추가적으로 개발하여 실시간으로 사용자에게 편의를 제공하고자 합니다. 
<br><br>

### 🥅 프로젝트 목표
- 설치된 충전기의 효율적인 운영 및 수요 분석을 위해 충전기 사용 현황을 실시간으로 모니터링하는 서비스를 만들고자 함.
- 기업 측 요구사항에 따라 하드웨어보다 소프트웨어 및 실제 운영 가능한 서비스에 초점을 맞춰 개발할 것임.
- 추후 기업 측에서 하드웨어 개발/운영 가능성이 있으므로, REST API 등을 통한 확장성 있는 개발 방식을 선택해 개발할 것임.
- 각 충전기의 충전 상태 및 각종 정보를 관리하는 서버와 실사용자인 지체장애인이 이용 가능한 앱 형태로 개발할 것임.
- 서버 개발을 위해 JAVA/Spring을 사용하여 서버를 개발하고, 코틀린을 사용해 안드로이드 앱을 개발할 것임.
- 충전기의 위치를 지도상에 표시하고, 충전기의 상세 정보를 확인할 수 있는 기능을 필수적으로 구현하고자 함.
<br><br>

### 🔍 기대효과
- 본 프로젝트의 결과물을 통해 설치된 충전기의 충전 현황을 실시간으로 관리할 수 있음.
- 이를 통해 충전기 운영 패턴을 파악하고, 잠재 수요지를 파악하여 충전기 추가 설치 등의 연계 사업이 가능함.
- 충전기 정보 확인 외에도 장애인 화장실, 장애인 보장구 수리점 등의 위치를 확인할 수 있도록 할 수 있고, 장애인 콜택시 등과 연계하여 택시 호출 등 장애인 편의 기능을 다양하게 탑재할 수 있을 것으로 기대됨.
- 또한 전력 측정 데이터를 수집하는 시스템이므로, 홈 IoT 사업으로 추가 개발이 가능함.
- 이를 위해서는 전문적인 하드웨어 개발이 필요할 것으로 보임 .
<br><br>
---  

## API 명세서

### URI

- URI는 반드시 복수형으로 작성
- 2개 이상의 단어 사용을 위해 구분자 필요 시 대시(-) 사용

### 권한 수준

<aside>
🚧

상위 레벨은 하위 레벨에 접근 가능

</aside>

- 일반(레벨1): 일반 사용자 앱에서 호출
- 하드웨어(레벨2): 하드웨어에서 호출
- 관리자(레벨3): 관리자 페이지에서 호출
- 테스트(레벨4): 내부 로직에 의해 호출되거나, Postman 등 테스트 도구에 의해 호출(외부 호출일 경우 작업 비밀번호 등 파라미터로 받아서 검증 필수)

### API 목록

![image](https://github.com/user-attachments/assets/c2dffd75-2c44-44b0-a2e0-43a9c460ddfe)

---

## 💻 기술 스택

- **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA, Thymeleaf, Swagger
- **Frontend**: Thymeleaf, Bootstrap, JQuery
- **Database**: MySQL, JPA
- **Android App**: Java (Android), Retrofit, Naver Maps SDK, Proj4j
- **Build Tools**: Gradle
- **Version Control**: Git, GitHub
- **Utilities**: Gson, Logging Interceptor
  
---

## 👨‍💻 프로젝트 기여자
<table>
<thead>
<tr>
<th align="center"><strong>유동훈</strong></th>
<th align="center"><strong>안성모</strong></th>
<th align="center"><strong>김태완</strong></th>
</tr>
</thead>
<tbody>
<tr>
<td align="center"><a href="https://github.com/dbehdgns1215"><img src="https://github.com/dbehdgns1215.png" height="150" width="150" style="max-width: 100%;"> <br> @dbehdgns1215</a></td>
<td align="center"><a href="https://github.com/yolol312"><img src="https://github.com/yolol312.png" height="150" width="150" style="max-width: 100%;"> <br> @yolol312</a></td>
<td align="center"><a href="https://github.com/lulluralra"><img src="https://github.com/lulluralra.png" height="150" width="150" style="max-width: 100%;"> <br> @Lullu</a></td>

</tr>
</tbody>
</table>
<br>

