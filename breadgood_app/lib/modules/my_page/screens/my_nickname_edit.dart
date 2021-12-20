import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'dart:ui';
import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/utils/services/rest_api_service.dart';
import 'package:breadgood_app/modules/my_page/controller/mypage_controller.dart';
import 'package:breadgood_app/modules/my_page/model/userinfo.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';

Tokens token=new Tokens();

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
          'https://api.breadgood.com/api/v1/user/me/duplicate/nickName/${currentNickname}'),
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

Future<http.Response> updateNickname(String newNickname) async {
  print('****newnickname: ${newNickname}');
  // const String url = 'https://api.breadgood.com/api/v1/user/me/duplicate/nickName/$newNickname';
  String accessToken= await token.getAccessToken();
  final response = await http.patch(
      Uri.parse(
          'https://api.breadgood.com/api/v1/user/me/nickName/${newNickname}'),
      headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + accessToken
      },
      body: <String, String>{
        'nickName': 'nickName',
      });
  if (response.statusCode == 200) {
    print('Updated!');
  } else {
    print('error: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  // print(responseJson);
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
  return response;
}

class MyNicknameEditPage extends StatefulWidget {
  const MyNicknameEditPage({Key key}) : super(key: key);

  @override
  _MyNicknameEditPageState createState() => _MyNicknameEditPageState();
}

class _MyNicknameEditPageState extends State<MyNicknameEditPage> {
  String nickname = Get.arguments.toString();
  // Future<User> futureUser;
  String checkDuplicate;
  var nicknameLength = 7;
  bool nicknameEntered = true;
  String newNickname = Get.arguments.toString();
  final controller = Get.put(MyPageController());

  @override
  void initState() {
    super.initState();
    // futureUser = fetchUser();
    // checkNickName(nickname);
    print('init state');
    print(nickname);
  }

  _onChanged(String text) async {
    setState(() {
      print('****onChanged');
      print(text);
      newNickname = text;
      print(newNickname);
      checkDuplicate = checkNickname(text).toString();

      print('duplicate:');
      print(checkDuplicate);
      nicknameLength = text.length;
      nicknameEntered = text.isEmpty;
      print(nicknameEntered);
    });
  }

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
            onPressed: () => Navigator.of(context).pop(),
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
            // GetBuilder<MyPageController>(builder: (_) {
            //   print("my page controller called");
            //   return Text('get');
            // }
            // ),
            Padding(
              padding: EdgeInsets.all(30.0),
              //       EdgeInsets.only(top: 20.0, left: 10.0, bottom: 20.0),
              child: Text(
                '별명 수정하기',
                style: TextStyle(
                    fontFamily: 'NanumSquareExtraBold',
                    fontSize: 26.0,
                    // fontWeight: FontWeight.w800
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
              padding: EdgeInsets.only(left: 30.0, top: 20.0),
              child: Text('최대 7글자, 숫자/특수문자/띄어쓰기 불가',
                  style: TextStyle(
                    fontSize: 12.0,
                    color: Colors.grey,
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
                right: 30.0,
              ),
              child: TextFormField(
                // onChanged: _onChanged,
                onChanged: (text) {
                  print('####onChanged');
                  print(text);
                  newNickname = text;
                  checkDuplicate = checkNickname(text).toString();

                  print('duplicate:');
                  print(checkDuplicate);
                  nicknameLength = text.length;
                  nicknameEntered = text.isEmpty;
                  print(nicknameEntered);
                  },
                style: TextStyle(fontSize: 16.0),

                initialValue: nickname,
                decoration: InputDecoration(
                  border: UnderlineInputBorder(),
                  contentPadding: EdgeInsets.only(top: 10.0, bottom: 0.0),
                  // labelText: 'Enter your username',
                ),
              ),
            ),
            Padding(
                padding: EdgeInsets.only(
                  left: 30.0,
                  right: 30.0,
                  top: 5.0,
                ),
//               child: if(checkDuplicate == true
//             || (!nicknameEntered && nicknameLength < 1)
//                 || (!nicknameEntered && nicknameLength > 7))
//                 {
//                   Text(
//                             '!이미 사용중인 별명이에요.',
//                             style: TextStyle(color: Colors.red),
//                           );
//                 } else if(nicknameEntered) {
//    child: Text(
//             '사용할 수 있는 별명이에요.',
//             style: TextStyle(color: Colors.blue),
//           );
//     } else {
// child: Text('$nickname');
//     })
                child: (checkDuplicate == true)
                    ? Text('이미 사용중인 별명이에요',
                        style: TextStyle(
                          color: Colors.red,
                          fontSize: 12.0,
                        ))
                    // : ((!nicknameEntered && nicknameLength < 1) ||
                    //         (!nicknameEntered && nicknameLength > 7))
                    //     ? Text(
                    //         '!별명을 1글자 이상 7글자 이하로 입력해주세요.',
                    //         style: TextStyle(
                    //           color: Colors.red,
                    //           fontSize: 12.0,
                    //         ),
                    //       )
                        : Text('사용할 수 있는 별명이에요',
                            style: TextStyle(
                              color: Colors.blue,
                              fontSize: 12.0,
                            ))

                // if (checkDuplicate == true) {
                // child: Text(
                // '!이미 사용중인 별명이에요.',
                // style: TextStyle(color: Colors.red),
                // );
                // } else {
                // child: Text(
                // '사용할 수 있는 별명이에.',
                // style: TextStyle(color: Colors.blue),
                // );
                // }
                ),
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
                        final response = updateNickname(newNickname);
                        Get.offAndToNamed('/my_page/my_info_edit');
                        // Navigator.pop(
                        //   context,
                        // );
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
