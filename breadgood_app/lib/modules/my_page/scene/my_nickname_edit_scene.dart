import 'dart:ui';
import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:breadgood_app/modules/my_page/controller/edit_nickname_controller.dart';
import 'package:breadgood_app/modules/my_page/controller/mypage_controller.dart';

class MyNickNameEditScene extends StatefulWidget {
  const MyNickNameEditScene({Key key}) : super(key: key);

  @override
  _MyNickNameEditSceneState createState() => _MyNickNameEditSceneState();
}

class _MyNickNameEditSceneState extends State<MyNickNameEditScene> {
  String nickname = Get.arguments.toString();
  String newNickname = Get.arguments.toString();

  final controller = Get.put(MyPageController());

  final _editNameController = Get.put(EditNickNameController());

  @override
  void initState() {
    super.initState();
  }

  _onNickNameChanged(String text) async {
    setState(() {
      newNickname = text;
      _editNameController.validateNickName(newNickname);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
          children: <Widget>[
            Padding(
              padding: EdgeInsets.all(30.0),
              child: Text(
                '별명 수정하기',
                style: TextStyle(
                  fontFamily: 'NanumSquareRoundEB',
                  fontSize: 26.0,
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 30.0, top: 20.0),
              child: Text('최대 7글자, 숫자/특수문자/띄어쓰기 불가',
                  style: TextStyle(
                    fontSize: 12.0,
                    color: Colors.grey,
                  )),
            ),
            Padding(
              padding: EdgeInsets.only(
                left: 30.0,
                right: 30.0,
              ),
              child: TextFormField(
                  onChanged: _onNickNameChanged,
                  style: TextStyle(fontSize: 16.0),
                  initialValue: nickname,
                  decoration: InputDecoration(
                    border: UnderlineInputBorder(),
                    contentPadding: EdgeInsets.only(top: 10.0, bottom: 0.0),
                    // labelText: 'Enter your username',
                  ),
                  inputFormatters: [
                    FilteringTextInputFormatter.deny(
                        RegExp(r'^[_\-=@,\.;^]+$')),
                    FilteringTextInputFormatter.allow(
                        RegExp('[A-z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]')),
                  ]),
            ),
            Padding(
                padding: EdgeInsets.only(
                  left: 30.0,
                  right: 30.0,
                  top: 5.0,
                ),
                child: (_editNameController.errorMessage.isEmpty)
                    ? Text('사용할 수 있는 별명이에요',
                        style: TextStyle(
                          color: Color(0xFF4579FF),
                          fontSize: 12.0,
                        ))
                    : Text(_editNameController.errorMessage,
                        style: TextStyle(
                          color: Colors.red,
                          fontSize: 12.0,
                        ))),
            Expanded(
              child: Align(
                alignment: Alignment.bottomCenter,
                child: Padding(
                  padding: EdgeInsets.only(bottom: 50.0),
                  child: ButtonTheme(
                    minWidth: 314.0,
                    height: 56.0,
                    child: RaisedButton(
                      child: Text("수정 완료",
                          style: TextStyle(
                              color: Colors.white,
                              fontWeight: FontWeight.w600)),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30.0),
                      ),
                      elevation: 0,
                      onPressed: () {
                        if (_editNameController.errorMessage.isEmpty &&
                            nickname != newNickname) {
                          final _ =
                              _editNameController.updateNickname(newNickname);
                          Get.offAndToNamed('/dashboard');
                        }
                      },
                      color: (_editNameController.errorMessage.isEmpty &&
                              nickname != newNickname)
                          ? Color(0xFF4579FF)
                          : Color(0xFFC7C7C7),
                    ),
                  ),
                ),
              ),
            ),
          ],
        ));
  }
}

class RichWidgetNickname extends StatefulWidget {
  RichWidgetNickname({Key key}) : super(key: key);

  @override
  _RichWidgetNicknameState createState() => _RichWidgetNicknameState();
}

class _RichWidgetNicknameState extends State<RichWidgetNickname> {
  @override
  void initState() {
    super.initState();
  }

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
