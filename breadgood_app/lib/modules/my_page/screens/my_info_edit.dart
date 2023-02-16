import 'package:flutter/material.dart';
import 'package:breadgood_app/modules/my_page/model/userinfo.dart';
import 'dart:async';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/my_page/controller/mypage_controller.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:breadgood_app/utils/services/rest_api_service.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:breadgood_app/constant/api_path.dart' as api_path;

Tokens token = new Tokens();

class MyInfoEditPage extends StatefulWidget {
  const MyInfoEditPage({Key key}) : super(key: key);

  @override
  _MyInfoEditPageState createState() => _MyInfoEditPageState();
}

class _MyInfoEditPageState extends State<MyInfoEditPage> {
  Future<User> user;
  // Stream<User> suser = fetchUser();
  Stream<User> suser = (() async* {
    await Future<void>.delayed(const Duration(milliseconds: 2));
    fetchUser();
  })();
  String nickname;
  String favorite_bread_style;
  int favorite_bread_style_id;
  final controller = Get.put(DashboardController());
  @override
  void initState() {
    super.initState();
    user = fetchUser();
    Future.delayed(const Duration(milliseconds: 1000), () {
      setState(() {
        print('setstate called');
        user = fetchUser();
      });
    });
    print('init state');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: DefaultAppBar(),
        body:myInfoList(),
    );
  }

  StreamBuilder<User> buildStreamUserBuilder() {
    return StreamBuilder<User>(
        stream: suser,
        builder: (context, snapshot) {
          print('stream builder');
          if (snapshot.hasData) {
            print('has data');
            nickname = snapshot.data.nickName;
            favorite_bread_style = snapshot.data.breadStyleName;
            favorite_bread_style_id = snapshot.data.breadStyleId;
            return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  TextButton(
                    child: SizedBox(
                      width: double.infinity,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Padding(
                            padding: EdgeInsets.only(left: 20.0),
                            child: const Text(
                              '별명 수정하기',
                              style: TextStyle(
                                  color: Colors.black, fontSize: 18.0),
                            ),
                          ),
                          Padding(
                            padding: EdgeInsets.only(right: 20.0),
                            child: Text(
                              '${nickname}',
                              style:
                                  TextStyle(color: Colors.blue, fontSize: 18.0),
                            ),
                          ),
                        ],
                      ),
                    ),
                    onPressed: () {
                      Get.toNamed('/my_page/my_nickname_edit',
                          arguments: nickname);
                    },
                  ),
                  TextButton(
                    child: SizedBox(
                      width: double.infinity,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Padding(
                            padding: EdgeInsets.only(left: 20.0),
                            child: const Text('최애빵 스타일 수정하기',
                                style: TextStyle(
                                    color: Colors.black, fontSize: 18.0)),
                          ),
                          Padding(
                            padding: EdgeInsets.only(right: 20.0),
                            child: Text('${favorite_bread_style}빵',
                                style: TextStyle(
                                    color: Colors.blue, fontSize: 18.0)),
                          ),
                        ],
                      ),
                    ),
                    onPressed: () {
                      Get.toNamed('/my_page/my_breadstyle_edit_page',
                          arguments: favorite_bread_style_id);
                    },
                  ),
                ]);
          } else if (snapshot.hasError) {
            print('error');
            return Text("this: ${snapshot.error}");
          } else {
            print('no data');
            return SizedBox(
              width: 60,
              height: 60,
              child: CircularProgressIndicator(),
            );
          }
        });
  }

  FutureBuilder<User> buildFutureUserBuilder() {
    return FutureBuilder<User>(
        future: user = fetchUser(),
        builder: (context, snapshot) {
          // print('builder');
          if (snapshot.hasData) {
            // print('has data');
            nickname = snapshot.data.nickName;
            favorite_bread_style = snapshot.data.breadStyleName;
            return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  TextButton(
                    child: SizedBox(
                      width: double.infinity,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Padding(
                            padding: EdgeInsets.only(left: 20.0),
                            child: const Text(
                              '별명 수정하기',
                              style: TextStyle(
                                  color: Colors.black, fontSize: 18.0),
                            ),
                          ),
                          Padding(
                            padding: EdgeInsets.only(right: 20.0),
                            child: Text(
                              '${nickname}',
                              style:
                                  TextStyle(color: Color(0xFF4579FF), fontSize: 18.0),
                            ),
                          ),
                        ],
                      ),
                    ),
                    onPressed: () {
                      Get.toNamed('/my_page/my_nickname_edit',
                          arguments: nickname);
                    },
                  ),
                  TextButton(
                    child: SizedBox(
                      width: double.infinity,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          Padding(
                            padding: EdgeInsets.only(left: 20.0),
                            child: const Text('최애빵 스타일 수정하기',
                                style: TextStyle(
                                    color: Colors.black, fontSize: 18.0)),
                          ),
                          Padding(
                            padding: EdgeInsets.only(right: 20.0),
                            child: Text('${favorite_bread_style}빵',
                                style: TextStyle(
                                    color: Color(0xFF4579FF), fontSize: 18.0)),
                          ),
                        ],
                      ),
                    ),
                    onPressed: () {
                      Get.toNamed('/my_page/my_breadstyle_edit_page');
                    },
                  ),
                ]);
          } else if (snapshot.hasError) {
            print('error');
            return Text("this: ${snapshot.error}");
          } else {
            print('no data');
            return Text('');
            //   SizedBox(
            // width: 60,
            // height: 60,
            // child: CircularProgressIndicator(),
            // );
          }
        });
  }

  myInfoList() {
    bool button_enable = false;

    return
        // SingleChildScrollView(
        //   child:
        Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
          padding: EdgeInsets.only(left: 30.0, top: 26.0),
          child: Text(
            '내 정보 설정',
            style: TextStyle(
                fontFamily: 'NanumSquareRoundEB',
                fontSize: 26.0),
          ),
        ),
        SizedBox(
          width: 132.0,
          height: 48.0 - 11.0,
        ),
        buildFutureUserBuilder(),
        // buildStreamUserBuilder(),

        // TextButton(
        //   child: SizedBox(
        //     width: double.infinity,
        //     child: Row(
        //       mainAxisAlignment: MainAxisAlignment.spaceBetween,
        //       children: <Widget>[
        //         Padding(
        //           padding: EdgeInsets.only(left: 20.0),
        //           child: const Text(
        //             '별명 수정하기',
        //             style: TextStyle(color: Colors.black, fontSize: 18.0),
        //           ),
        //         ),
        //         Padding(
        //           padding: EdgeInsets.only(right: 20.0),
        //           child: Text(
        //             '${nickname}',
        //             style: TextStyle(color: Colors.blue, fontSize: 18.0),
        //           ),
        //         ),
        //       ],
        //     ),
        //   ),
        //   onPressed: () {
        //     Get.toNamed('/my_page/my_nickname_edit', arguments: nickname);
        //     },
        // ),
        // TextButton(
        //   child: SizedBox(
        //     width: double.infinity,
        //     child: Row(
        //       mainAxisAlignment: MainAxisAlignment.spaceBetween,
        //       children: <Widget>[
        //         Padding(
        //           padding: EdgeInsets.only(left: 20.0),
        //           child: const Text('최애빵 스타일 수정하기',
        //               style: TextStyle(color: Colors.black, fontSize: 18.0)),
        //         ),
        //         Padding(
        //           padding: EdgeInsets.only(right: 20.0),
        //           child: Text('${favorite_bread_style}빵',
        //               style: TextStyle(color: Colors.blue, fontSize: 18.0)),
        //         ),
        //       ],
        //     ),
        //   ),
        //   onPressed: () {
        //     Get.toNamed('/my_page/my_breadstyle_edit_page');
        //     },
        // ),
        Divider(
          height: 82.0,
          thickness: 1.0,
          color: Color(0xFFEAEAEA),
          indent: 30.0,
          endIndent: 30.0,
        ),
        TextButton(
          child: SizedBox(
            width: double.infinity,
            child: Padding(
              padding: EdgeInsets.only(left: 20.0),
              child: const Text('로그아웃',
                  style: TextStyle(color: Color(0xFF2D2D41), fontSize: 18.0)),
            ),
          ),
          onPressed: () async {
            await token.deleteUserInfo();
            controller.changePageIndex(0);
            Get.toNamed('/');
          },
        ),
        Spacer(),
        Padding(
          padding: EdgeInsets.only(bottom: 36.0),
          child: TextButton(
            child: SizedBox(
              width: double.infinity,
              child: Padding(
                padding: EdgeInsets.only(left: 20.0),
                child: const Text('빵긋 탈퇴하기',
                    style: TextStyle(color: Color(0xFFAFAFAF), fontSize: 18.0)),
              ),
            ),
            onPressed: () {
              showAlertDialog(context);
            },
          ),
        ),
      ],
      // )
    );
  }
}

showAlertDialog(BuildContext context) {
  bool button_enable = false;
  String text = "1. 회원탈퇴시 등록한 빵집 정보는 삭제되지 않으며 탈퇴한 회원의 닉네임은 랜덤값으로 표기됩니다.\n";
  List<String> split_text = text.split(RegExp(r'\s'));
  for (var item in split_text) print(item);
  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(10.0))),
    titlePadding: EdgeInsets.fromLTRB(30.0, 24.0, 24.0, 0),
    contentPadding: EdgeInsets.fromLTRB(30.0, 16.0, 30.0, 0.0),
    title: Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: EdgeInsets.fromLTRB(0, 6.0, 0, 0),
          child: Text(
            "회원탈퇴",
            style: TextStyle(
                fontSize: 26.0,
                fontFamily: 'NanumSquareRoundEB'),
          ),
        ),
        new Spacer(),
        SizedBox(
          height: 20.0,
          width: 20.0,
          child: _getCloseButton(context),
        ),
      ],
    ),
    content: Container(
      width: double.maxFinite,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          ListView(
            shrinkWrap: true,
            children: <Widget>[
              new Row(children: <Widget>[
                Text('1. ', style: TextStyle(fontSize: 12)),
                Expanded(
                    child: Text(
                        '회원탈퇴시 등록한 빵집 정보는 삭제되지 않으며 탈퇴한 회원의 닉네임은 랜덤값으로 표기됩니다.\n',
                        style: TextStyle(fontSize: 12)))
              ], crossAxisAlignment: CrossAxisAlignment.start),
              new Row(children: <Widget>[
                Text('2. ', style: TextStyle(fontSize: 12)),
                Expanded(
                    child: Text(
                        '회원 탈퇴 시 등록한 리뷰 정보는 자동 삭제되지 않으며 탈퇴한 회원의 닉네임은 랜덤 값으로 표기됩니다.',
                        style: TextStyle(fontSize: 12)))
              ], crossAxisAlignment: CrossAxisAlignment.start),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 21.0, 0, 21.0),
                child: Divider(
                  thickness: 1.0,
                  color: Color(0xFFEAEAEA),
                  // endIndent: 21.0,
                ),
              ),
              CheckAgreement(),
            ],
          ),
        ],
      ),
    ),
  );
  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}

_getCloseButton(context) {
  return GestureDetector(
    onTap: () {
      Navigator.pop(context);
    },
    child: Icon(
      Icons.clear,
      color: Color(0xFF4D4D4D),
    ),
  );
}

// Map<String, String> header_ex = await headers();
void deleteUser() async {
  print('checkNickname');
  final response = await http.delete(
    Uri.parse('https://${api_path.restApiUrl}/user/me/withdrawal'),
    headers: await headers(),
  );

  if (response.statusCode == 200) {
    print('deleted!');
  } else {
    print('delete error: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);
}

class CheckAgreement extends StatefulWidget {
  const CheckAgreement({Key key}) : super(key: key);

  @override
  _CheckAgreementState createState() => _CheckAgreementState();
}

class _CheckAgreementState extends State<CheckAgreement> {
  bool button_enable = false;
  final controller = Get.put(DashboardController());

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            SizedBox(
              height: 20,
              width: 20,
              child: IconButton(
                // padding: EdgeInsets.zero,
                padding: EdgeInsets.all(0),
                constraints: BoxConstraints(),
                icon: Icon(
                  Icons.check_circle_sharp,
                  color: button_enable ? Color(0xFF4579FF) : Color(0xFFA4A4A4),
                  size: 20.0,
                ),
                onPressed: () {
                  setState(() {
                    button_enable = !button_enable;
                  });
                },
              ),
            ),
            Padding(
              padding: EdgeInsets.fromLTRB(10.0, 0.0, 0.0, 0.0),
              child: Container(
                child: Text(
                  "위 내용을 모두 확인하였으며, 이에 동의합니다.",
                  style: TextStyle(
                    fontSize: 12.0,
                  ),
                ),
              ),
            )
          ],
        ),
        Padding(
          padding: EdgeInsets.fromLTRB(0, 23.0, 0.0, 31.0),
          child: IntrinsicHeight(
            // Container(
            //   width: double.maxFinite,
            child: Row(
              // crossAxisAlignment: CrossAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.fromLTRB(0, 0, 7.0, 0),
                    child:
                        // SizedBox(
                        //   width: 124.0,
                        //   height: 48.0,
                        //   child:
                        RaisedButton(
                      child: Text("탈퇴하기",
                          style: TextStyle(
                            fontWeight: FontWeight.w600,
                            fontSize: 16.0,
                            color: Color(0xFFFFFFFF),
                          )),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      ),
                      color:
                          button_enable ? Color(0xFF4579FF) : Color(0xFFA4A4A4),
                      onPressed: (button_enable == false)
                          ? null
                          : () async {
                              deleteUser();
                              await token.deleteUserInfo();
                              controller.changePageIndex(0);
                              Get.offAndToNamed('/'); // rm all screen history
                            },
                    ),
                  ),
                ),
                // ),
                // SizedBox(
                //   width: 124.0,
                // width: 114,
                // height: 48.0,
                // child:
                Expanded(
                  child: RaisedButton(
                    child: Text("뒤로가기",
                        style: TextStyle(
                          fontWeight: FontWeight.w600,
                          fontSize: 16.0,
                          color: Color(0xFFFFFFFF),
                        )),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    ),
                    color: Color(0xFF4579FF),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                ),
                // ),
              ],
            ),
          ),
        ),
      ],
    );
  }
}
