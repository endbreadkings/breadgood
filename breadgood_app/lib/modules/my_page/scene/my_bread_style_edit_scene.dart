import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/dashboard/scenes/dashboard_scene.dart';
import 'package:breadgood_app/modules/my_page/ui/bread_style_gridview.dart';
import 'package:breadgood_app/modules/my_page/repository/my_page_repository.dart';

class MyBreadStyleEditScene extends StatefulWidget {
  const MyBreadStyleEditScene({Key key}) : super(key: key);

  @override
  _MyBreadStyleEditSceneState createState() => _MyBreadStyleEditSceneState();
}

class _MyBreadStyleEditSceneState extends State<MyBreadStyleEditScene> {
  String nickName = Get.arguments.toString();
  String checkDuplicate;
  var nickNameMaxLength = 7;
  bool isEntered = true;
  String newNickName = Get.arguments.toString();
  var breadStyleId = Get.arguments;
  var newBreadStyleId;

  @override
  void initState() {
    super.initState();
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
            onPressed: () => Get.to(DashboardScene()),
          ),
          backgroundColor: Colors.transparent,
          elevation: 0.0,
        ),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisSize: MainAxisSize.max,
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 30.0, top: 26.0),
              child: Text(
                '최애빵 스타일 변경하기',
                style: TextStyle(
                  fontFamily: 'NanumSquareRoundEB',
                  fontSize: 26.0,
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 31.0, top: 48.0),
              child: Text('중복선택 불가',
                  style: TextStyle(
                    fontSize: 12.0,
                    color: Color(0xFFA4A4A4),
                  )),
            ),
            Padding(
              padding: EdgeInsets.only(
                left: 30.0,
                top: 17.0,
                right: 30.0,
              ),
              child: SizedBox(
                width: 315.0,
                height: 205.0,
                child: BreadStyleGridView(breadStyleId),
              ),
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
                        newBreadStyleId =
                            BreadStyleGridView(breadStyleId).breadStyleId;
                        updateBreadStyle(newBreadStyleId);
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
}

class NickNameRichWidget extends StatefulWidget {
  NickNameRichWidget({Key key}) : super(key: key);
  @override
  _NickNameRichWidgetState createState() => _NickNameRichWidgetState();
}

class _NickNameRichWidgetState extends State<NickNameRichWidget> {
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
