import 'package:breadgood_app/modules/dashboard/dashboard.dart';
import 'package:breadgood_app/modules/my_page/bread_style_gridview.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'dart:ui';
import 'dart:async';

import 'dart:convert';
import 'package:breadgood_app/modules/my_page/model/userInfo.dart';
import 'package:breadgood_app/modules/my_page/screens/my_page.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/utils/services/rest_api_service.dart';
import 'package:breadgood_app/constant/api_path.dart' as api_path;

// class CheckDuplication {
//   final String nickName;
//
//   CheckDuplication(this.nickName);
//
//   factory CheckDuplication.fromJson(Map<String, dynamic> json) {
//     return CheckDuplication(
//       nickName: json['nickName'],
//     );
//   }
// }

Future<http.Response> checkNickname(String currentNickname) async {
  print('checkNickname');
  final response = await http.post(
      Uri.parse(
          '${api_path.restApiUrl}/user/me/duplicate/nickName/${currentNickname}'),
      headers: await headers(),
      body: <String, String>{
        'nickName': 'nickName',
      });
  if (response.statusCode == 200) {
    print('Uploaded!');
  } else {
    print('error: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);
}

Future<http.Response> updateBreadStyle(int newBreadStyle) async {
  // const String url = '${api_path.restApiUrl}/user/me/duplicate/nickName/$newNickname';
  final response = await http.patch(
      Uri.parse(
          '${api_path.restApiUrl}/user/me/breadStyle/${newBreadStyle}'),
      headers: await headers(),
      body: <String, String>{
        'breadStyleId': 'breadStyleId',
      });
  if (response.statusCode == 200) {
    print('Updated!');
  } else {
    print('error: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);
  // await http.patch(
  //   url,
  //   body: {
  //     "nickName": newNickname,
  //   },
  //   headers: {"Authorization": "Bearer $token"},
  // ).then((value) {
  //   // print(authToken);
  //   print(value.body);
  // });
}

class MyBreadStyleEditPage extends StatefulWidget {
  const MyBreadStyleEditPage({Key key}) : super(key: key);

  @override
  _MyBreadStyleEditPageState createState() => _MyBreadStyleEditPageState();
}

class _MyBreadStyleEditPageState extends State<MyBreadStyleEditPage> {
  String nickname = Get.arguments.toString();
  // Future<User> futureUser;
  String checkDuplicate;
  var nicknameLength = 7;
  bool nicknameEntered = true;
  String newNickname = Get.arguments.toString();
  var breadStyleId = Get.arguments;
  var newBreadStyleId;
  @override
  void initState() {
    super.initState();
    // futureUser = fetchUser();
    // checkNickName(nickname);
    print('init state');
    print(nickname);
  }

  // _onChanged(String text) async {
  //   setState(() {
  //     print('onChanged');
  //     print(text);
  //     // newNickname = text;
  //     newBreadStyleId = 3;
  //     checkDuplicate = checkNickname(text).toString();
  //
  //     print('duplicate:');
  //     print(checkDuplicate);
  //     nicknameLength = text.length;
  //     nicknameEntered = text.isEmpty;
  //     print(nicknameEntered);
  //   });
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(title: Text("빵긋")),
        appBar: AppBar(
          leading: IconButton(
            icon: Icon(
              Icons.arrow_back_ios_rounded,
              color: Color(0xFF4579FF),
            ),
            onPressed: () => Get.to(Dashboard()),
          ),
          backgroundColor: Colors.transparent,
          elevation: 0.0,
        ),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.max,
          // mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            // Container(
            //   height: 0,
            //   child:
            //       (futureUser == null) ? Text('noUser') : buildFutureBuilder(),
            // ),
            Padding(
              padding: EdgeInsets.only(left: 30.0, top: 26.0),
              //       EdgeInsets.only(top: 20.0, left: 10.0, bottom: 20.0),
              child: Text(
                '최애빵 스타일 변경하기',
                style: TextStyle(
                  fontFamily: 'NanumSquareRoundEB',
                  fontSize: 26.0,
                ),
              ),
            ),
            // Container(
            //   // padding: EdgeInsets.all(16.0),
            //   margin: EdgeInsets.all(12.0),
            //   child: DefaultTextStyle(
            //       style: Theme.of(context).textTheme.title,
            //       child: RichWidgetNickname()),
            // ),
            // const SizedBox(height: 30),
            Padding(
              padding: EdgeInsets.only(left: 31.0, top: 48.0),
              child: Text('중복선택 불가',
                  style: TextStyle(
                    fontSize: 12.0,
                    color: Color(0xFFA4A4A4),
                  )),
            ),
            // Padding(
            //   padding: EdgeInsets.symmetric(horizontal: 8, vertical: 16),
            //   child: TextField(
            //     decoration: InputDecoration(
            //       border: OutlineInputBorder(),
            //       hintText: 'Enter a search term',
            //     ),
            //   ),
            // ),
            Padding(
              padding: EdgeInsets.only(
                left: 30.0,
                top: 17.0,
                right: 30.0,
              ),
            child: SizedBox(
    width: 315.0,
    height: 205.0,
    child:  BreadStylePage(breadStyleId),
    ),
            //   child: GridView.count(
            //     crossAxisCount: 2,
            //     children: <Widget>[
            //       Image.asset('asset/images/breadstyle/breadstyle_cream.png'),
            //       Image.asset('asset/images/breadstyle/breadstyle_sweet.png'),
            //       Image.asset('asset/images/breadstyle/breadstyle_savory.png'),
            //       Image.asset('asset/images/breadstyle/breadstyle_plain.png'),
            //     ],
            //
            //   ),
            ),
            // Padding(
            //     padding: EdgeInsets.only(
            //       left: 30.0,
            //       right: 30.0,
            //       top: 5.0,
            //     ),
            //   // child:
            // ),
            Expanded(
              child: Align(
                alignment: Alignment.bottomCenter,
                child: Padding(
                  padding: EdgeInsets.only(bottom: 50.0),
                  child: ButtonTheme(
                    minWidth: 314.0,
                    height: 48.0,
                    child: RaisedButton(
                      child: Text("수정 완료",
                          style: TextStyle(
                              color: Colors.white,
                              fontWeight: FontWeight.w600)),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      ),
                      onPressed: () {
                        print('OnPressed');
                        print('breadStyleId:');
                        print(breadStyleId);

                        // newBreadStyleId = BreadStylePage.breadStyleId;
                        newBreadStyleId = BreadStylePage(breadStyleId).breadStyleId;
                        print('newBreadStyleId');
                        print(newBreadStyleId);
                        updateBreadStyle(newBreadStyleId);

                        // updateBreadStyle(breadStyleId);
                        Get.offAndToNamed('/dashboard');
                      },
                      color: Colors.blue,
                    ),
                  ),
                ),
              ),
            ),
          ],
        ));
  }

// FutureBuilder<User> buildFutureBuilder() {
//   print('nbuildFutureBuilder');
//   return FutureBuilder<User>(
//     future: futureUser,
//     // future: fetchUser(),
//     builder: (context, snapshot) {
//       if (snapshot.hasData) {
//         if (snapshot.data.nickName != null) {
//           print('data received');
//           print(snapshot.data.nickName);
//           nickname = snapshot.data.nickName;
//           print('abc');
//           print(nickname);
//           return Text('');
//         } else {
//           return Text('no data');
//         }
//         // String nickname = snapshot.data!.nickName;
//
//       } else if (snapshot.hasError) {
//         return Text("${snapshot.error}");
//       }
//
//       // By default, show a loading spinner.
//       // return CircularProgressIndicator();
//       return Text('err');
//     },
//   );

}

class RichWidgetNickname extends StatefulWidget {
  RichWidgetNickname({Key key}) : super(key: key);

  // Future<User> futureUser = fetchUser();
  @override
  _RichWidgetNicknameState createState() => _RichWidgetNicknameState();
}

class _RichWidgetNicknameState extends State<RichWidgetNickname> {
  // Future<User> futureUser;

  // String get getNickname {
  //   return nickname;
  // }
  //
  // String get getBreadStyle {
  //   return favorite_bread_style;
  // }

  @override
  void initState() {
    super.initState();
    // futureUser = fetchUser();
    // fetchUser();
    // String nickName = futureUser.user;
  }

  // var manager = UserManager();
  // String nickname = manager.user;
  // print(manager.user);
  // @override
  // void initState() {
  //   super.initState();
  //   futureUser = fetchUser();
  // }
  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        Padding(
          padding: EdgeInsets.only(left: 10.0),
          child: RichText(
            text: TextSpan(
              style: TextStyle(
                color: Colors.black,
                fontSize: 15.0,
              ),
              children: <TextSpan>[
                TextSpan(
                  text: '수정할 ',
                ),
                TextSpan(
                    text: '별명',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.blue,
                    )),
                TextSpan(
                  text: '을 입력해주세요!',
                ),
                TextSpan(
                    text: ' *\n',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.blue,
                    )),
                TextSpan(
                    text: '최대 7글자, 숫자/특수문자/띄어쓰기 불가\n',
                    style: TextStyle(
                      fontSize: 12.0,
                      color: Colors.grey,
                    )),
              ], // buildFutureBuilder(),
            ),
          ),
        ),
      ],
    );
  }
}
