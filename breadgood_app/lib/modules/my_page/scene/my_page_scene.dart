import 'package:get/get.dart';
import 'package:flutter/material.dart';
import 'package:breadgood_app/modules/my_page/model/user_info.dart';
import 'package:breadgood_app/modules/my_page/repository/my_page_repository.dart';
import 'package:breadgood_app/common/ui/bottom_navigation.dart';
import 'package:breadgood_app/common/ui/default_app_bar.dart';

var myBakeryCount = 0;
var myReviewCount = 0;
String nickName = "default";
String profileImageUrl;

var myFavoriteBreadStyle = 0;
String favoriteBreadStyle;

class MyPageScene extends StatelessWidget {
  MyPageScene({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: MyPageAppbar(),
      body: Column(children: [
        Container(
          margin: EdgeInsets.only(left: 20.0, top: 116.0 - 44.0 - 42.0),
          child: DefaultTextStyle(
              style: Theme.of(context).textTheme.headline6,
              child: NickNameRichWidget()),
        ),
      ]),
      bottomNavigationBar: BottomNavigation(),
    );
  }
}

class NickNameRichWidget extends StatefulWidget {
  NickNameRichWidget({Key key}) : super(key: key);

  @override
  _NickNameRichWidgetState createState() => _NickNameRichWidgetState();
}

class _NickNameRichWidgetState extends State<NickNameRichWidget> {
  Future<User> futureUser;

  String get getNickname {
    return nickName;
  }

  String get getBreadStyle {
    return favoriteBreadStyle;
  }

  @override
  void initState() {
    super.initState();
    futureUser = fetchUser();
    buildFutureBuilder();
  }

  @override
  Widget build(BuildContext context) {
    return buildFutureBuilder();
  }

  FutureBuilder<User> buildFutureBuilder() {
    return FutureBuilder<User>(
      future: futureUser = fetchUser(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (snapshot.data.nickName != null) {
            profileImageUrl = snapshot.data.profileImgUrl;
            nickName = snapshot.data.nickName;
            myFavoriteBreadStyle = snapshot.data.breadStyleId;
            favoriteBreadStyle = snapshot.data.breadStyleName;
          }
          return MyPageInfoScene();
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        return Text('Error');
      },
    );
  }
}

class MyPageAppbar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      automaticallyImplyLeading: false,
      title: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Container(
              margin: EdgeInsets.only(right: 10),
              child: SizedBox(
                  width: 30,
                  height: 30,
                  child: Image.asset('asset/images/bread_appbar.png'))),
          Container(
            child: SizedBox(
                width: 35,
                height: 25,
                child: Image.asset('asset/images/breadgood.png')),
          ),
        ],
      ),
      actions: <Widget>[
        IconButton(
          icon: Image.asset('asset/images/Setting.png'),
          onPressed: () {
            Get.toNamed('/my_page/my_info_edit');
          },
        ),
      ],
      elevation: 0.0,
      backgroundColor: Colors.white,
      centerTitle: false,
    );
  }
}

class MyPageInfoScene extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        RichText(
          text: TextSpan(
            style: TextStyle(
              color: Colors.black,
              fontSize: 30.0,
            ),
            children: <TextSpan>[
              TextSpan(
                text: '$nickName님,\n',
                style: TextStyle(
                  fontFamily: 'NanumSquareExtraBold',
                ),
              ),
              TextSpan(
                  text: '아름다운빵이에요',
                  style: TextStyle(fontFamily: 'NanumSquareBold')),
            ],
          ),
        ),
        Container(
          child: SizedBox(
            width: 218.0,
            height: 20.0,
          ),
        ),
        RichText(
          text: TextSpan(
            style: TextStyle(
              color: Colors.black,
              fontSize: 30.0,
            ),
            children: <TextSpan>[
              TextSpan(
                  text: '$favoriteBreadStyle빵',
                  style: TextStyle(
                    fontSize: 15.0,
                    color: Colors.blue,
                  )),
              TextSpan(
                  text: '을 좋아하시는군요\n새로운 $favoriteBreadStyle빵을 찾아 떠나볼까요?',
                  style: TextStyle(
                    fontSize: 15.0,
                  )),
            ],
          ),
        ),
      ],
    );
  }
}
