# 빵끗 프로젝트

빵긋 어플 프로젝트 입니다.

## Install

#### Step1. 레포지토리를 다운로드 하거나 복사합니다.

```
https://github.com/bsideproject/breadgood.git
```

#### Step2. breadgood_app 위치에서 코드를 열고 필요한 패키지를 다운로드 받습니다.
```flutter
flutter pub get
```

#### Step3. 필요에 따라 안드로이드, ios 시뮬레이터 및 실제 기기를 연결하고 빌드합니다.

## Folder Structure


    ├── android                                             # 안드로이드 코드
    ├── assets                                              # 이미지, 사용자 정의 글꼴 파일을 저장하는 데 사용할 프로젝트의 assets 폴더
    ├── build                 
    ├── ios                                                 # ios 코드
    ├── lib                   
    │    ├── config            
    │    │    ├── routes.dart                               # 애플리케이션 화면 탐색 코드를 기반으로하는 모든 파일
    │    │    └── theme
    │    ├── constant               
    │    │    ├── api_path.dart                             # REST API 서비스를 사용할 때 모든 API 엔드 포인트 path 저장 파일
    │    │    └── app_constants.dart                        # 애플리케이션 내에서 사용하는 상수 
    │    ├── core                                           # 로그인 / 인증, 둘러보기 화면 (설치 후에만 표시되는 화면), 애플리케이션 설정 기능과 같은 핵심 기능 ex) 로그인, 비밀번호 분실 기능
    │    │    └── auth                                      
    │    │          └── login
    │    ├── modules                                        # 사용자에게 표시되는 모든 화면 UI 위젯
    │    │    ├── login 
    │    │    │     ├── controller                          # GetxController로 컨트롤러 클래스를 확장하여 모든 로직 처리
    │    │    │     ├── model                               # 화면에 표시해야하는 데이터 모델 ex) API response로 받은 데이터 직렬화 처리
    │    │    │     └── screens                             # 사용자에게 표시되는 모든 화면 UI 위젯
    │    │    ├── main  
    │    │    ├── mypage
    │    │    ├── ...etc
    │    ├── utils  
    │    │    ├── service
    │    │    │    ├── local_storage_service.dart           # 로컬 저장소에서 데이터 를 저장 하고 가져 오는 데 필요한 모든 코드를 작성
    │    │    │    ├── native_api_service.dart              # 여러 패키지를 사용하여 카메라, 포토 갤러리, 위치 등과 같은 기본 서비스에 액세스
    │    │    │    ├── rest_api_service.dart                # API 호출을 만들기 위한 패키지
    │    │    │    ├── naver_map_service.dart               # 네이버 지도를 사용하기 위한 패키지
    │    │    │    └── secure_storage_service.dart          # 사용자 자격 증명, API 토큰, 비밀 API 키를 플랫폼 별 암호화 기술로 Android Keystore 및 Apple 키 체인에 데이터를 저장
    │    │    ├── ui                                        # 일반적인 UI 관련 항목 ex) 애니메이션, custom app dialogs UI, input text box with search icon ..
    │    ├── widget                                         # 응용 프로그램 전체에서 사용할 수있는 자체 사용자 지정 위젯
    │    └── main.dart
    ├── test                 # 테스트     
    ├── pubspec.yaml         # flutter package
    └── README.MD            # readme


## Using package

- getX
- naver_map_plugin
- image_picker
- camera
- shared_preferences
- flutter_secure_storage
- http
