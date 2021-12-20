// import 'package:breadgood_app/modules/my_page/screens/my_bakery_page.dart';
// import 'package:breadgood_app/modules/my_page/screens/my_review_page.dart';
// import 'package:breadgood_app/modules/register_bakery/naver_serach.dart';
// import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:breadgood_app/modules/my_page/model/userinfo.dart';
// import 'package:breadgood_app/modules/register_review/register_review.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';

var my_bakery_cnt = 0;
// var my_bakery_cnt = 0;
var my_review_cnt = 0;
// var my_review_cnt = 0;
String nickname = "default";
String profile_image;

var my_favorite_bread_style = 0;
String favorite_bread_style;

class MyPage extends StatelessWidget {
  MyPage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: MyPageAppbar(),
      body: Column(
          // crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              // margin: EdgeInsets.only(left: 20.0,),
              // padding: EdgeInsets.all(16.0),
              margin: EdgeInsets.only(left: 20.0, top: 116.0 - 44.0 - 42.0),
              child: DefaultTextStyle(
                  style: Theme.of(context).textTheme.headline6,
                  child: RichWidgetNickname()),
            ),

/*
        // 내가 등록한 빵집
        // Sprint3
        Container(
            margin: EdgeInsets.all(12.0),
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              RichText(
                text: TextSpan(
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 15.0,
                    fontWeight: FontWeight.bold,
                  ),
                  children: <TextSpan>[
                    TextSpan(
                      text: '내가 등록한 빵집',
                    ),
                    TextSpan(
                        text: ' ${my_bakery_cnt}개',
                        style: TextStyle(
                          color: Colors.blue,
                        )),
                  ],
                ),
              ),
              my_bakery_cnt > 0
                  ? ButtonTheme(
                      minWidth: 400.0,
                      height: 35.0,
                      child: RaisedButton(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        ),
                        child: Text("전체 빵집 보러 가기 >"),
                        onPressed: () {
                          // Get.to(MyBakeryPage());
                        },
                        color: Colors.blueAccent,
                      ))
                  : Container(
                      width: 400,
                      child: Card(
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(20.0),
                          ),
                          color: Colors.white70,
                          child: Column(
                              mainAxisSize: MainAxisSize.min,
                              children: <Widget>[
                                ListTile(
                                    leading:
                                        Image.asset('assets/images/cat.png'),
                                    title: Text('등록한 빵집이 아직 없으시네요',
                                        style:
                                            TextStyle(color: Colors.black54)),
                                    subtitle: Text('지금 최애 빵집 등록하러 가시겠어요?')),
                                ButtonTheme(
                                  minWidth: 350.0,
                                  height: 35.0,
                                  child: RaisedButton(
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(30.0),
                                    ),
                                    child: Text("최애 빵집 등록하러 가기 >"),
                                    onPressed: () {
                                      // Get.to(MyBakeryPage());
                                      // Get.to(SearchDataInfo());
                                      // Get.to(SearchBakeryPage());
                                    },
                                    color: Colors.white,
                                  ),
                                ),
                              ]))),
            ])),
        // 내가 등록한 빵집
        // Sprint3

        // 내가 등록한 리뷰
        // Sprint3
        Container(
          margin: EdgeInsets.all(12.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              RichText(
                text: TextSpan(
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 15.0,
                    fontWeight: FontWeight.bold,
                  ),
                  children: <TextSpan>[
                    TextSpan(
                      text: '내가 등록한 리뷰',
                    ),
                    TextSpan(
                        text: ' ${my_review_cnt}개',
                        style: TextStyle(
                          color: Colors.blue,
                        )),
                  ],
                ),
              ),
              my_review_cnt > 0
                  ? ButtonTheme(
                      minWidth: 400.0,
                      height: 35.0,
                      child: RaisedButton(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        ),
                        child: Text("전체 리뷰 보러 가기 >"),
                        onPressed: () {
                          // Get.to(MyBakeryPage());
                        },
                        color: Colors.blueAccent,
                      ))
                  : Container(
                      width: 400,
                      child: Card(
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(20.0),
                          ),
                          color: Colors.white70,
                          // elevation: 10,
                          child: Column(
                              mainAxisSize: MainAxisSize.min,
                              children: <Widget>[
                                ListTile(
                                    // leading: Icon(Icons.bread, size: 70),
                                    leading:
                                        Image.asset('assets/images/bread2.png'),
                                    title: Text('등록한 리뷰가 아직 없으시네요',
                                        style:
                                            TextStyle(color: Colors.black54)),
                                    subtitle: Text('지금 첫 리뷰 남기러 가요!')),
                                ButtonTheme(
                                  minWidth: 350.0,
                                  height: 35.0,
                                  child: RaisedButton(
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(30.0),
                                    ),
                                    child: Text("리뷰 남기러 가기 >"),
                                    onPressed: () {
                                      // Get.to(RegisterReviewPage());
                                    },
                                    color: Colors.white,
                                  ),
                                )
                              ]))),
            ],
          ),
        ),
        // 내가 등록한 리뷰
        // Sprint3
 */
          ]),
      bottomNavigationBar: BottomNavigation(),
    );
  }
}

class RichWidgetNickname extends StatefulWidget {
  RichWidgetNickname({Key key}) : super(key: key);

  @override
  _RichWidgetNicknameState createState() => _RichWidgetNicknameState();
}

class _RichWidgetNicknameState extends State<RichWidgetNickname> {
  Future<User> futureUser;

  String get getNickname {
    return nickname;
  }

  String get getBreadStyle {
    return favorite_bread_style;
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
      // myInfoList();
    //   Column(
    //   crossAxisAlignment: CrossAxisAlignment.start,
    //   children: <Widget>[
    //     Container(
    //       height: 0,
    //       child: (futureUser == null) ? Text('noUser') : buildFutureBuilder(),
    //     ),
    //     RichText(
    //       text: TextSpan(
    //
    //         style: TextStyle(
    //           color: Colors.black,
    //           fontSize: 30.0,
    //
    //         ),
    //         children: <TextSpan>[
    //           TextSpan(
    //               text: '${nickname}님,\n',
    //               style: TextStyle(
    //                   fontFamily: 'NanumSquareExtraBold',
    //               ),
    //
    //         ),
    //           // TextSpan(
    //           //   text: '\n',
    //           //     style: TextStyle(fontSize:157.0 - 116.0 - 34.0)),
    //
    //           TextSpan(
    //               text: '아름다운빵이에요',
    //               style: TextStyle(fontFamily: 'NanumSquareBold')),
    //         ],
    //       ),
    //     ),
    //     Container (
    //     child: SizedBox(
    //         width: 218.0,
    //         height: 20.0,
    //     ),
    //     ),
    //     // 211.0 - 157.0 - 34.0
    //     RichText(
    //       text: TextSpan(
    //         style: TextStyle(
    //           color: Colors.black,
    //           fontSize: 30.0,
    //         ),
    //         children: <TextSpan>[
    //           TextSpan(
    //               text: '${favorite_bread_style}빵',
    //               style: TextStyle(
    //                 fontSize: 15.0,
    //                 color: Colors.blue,
    //               )),
    //           TextSpan(
    //               text: '을 좋아하시는군요\n새로운 ${favorite_bread_style}빵을 찾아 떠나볼까요?',
    //               style: TextStyle(
    //                 fontSize: 15.0,
    //               )),
    //         ],
    //       ),
    //     ),
    //   ],
    // );
  }

  FutureBuilder<User> buildFutureBuilder() {
    return FutureBuilder<User>(
      future: futureUser = fetchUser(),
      //   future: fetchUser(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (snapshot.data.nickName != null) {
            profile_image = snapshot.data.profileImgUrl;
            print(profile_image);
            nickname = snapshot.data.nickName;
            my_favorite_bread_style = snapshot.data.breadStyleId;
            favorite_bread_style = snapshot.data.breadStyleName;
            // switch (my_favorite_bread_style) {
            //   case 1:
            //     {
            //       favorite_bread_style = '달콤한';
            //     }
            //     break;
            //   case 2:
            //     {
            //       favorite_bread_style = '담백한';
            //     }
            //     break;
            //   case 3:
            //     {
            //       favorite_bread_style = '짭짤한';
            //     }
            //     break;
            //   case 4:
            //     {
            //       favorite_bread_style = '짭짤한';
            //     }
            //     break;
            // }
            // return Text('');
          } else {
            // return Text('');
          }
          return myPageInfo();
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }

        // By default, show a loading spinner.
        // return CircularProgressIndicator();
        return Text('err');
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
          // Icon(

            // Icons.settings,
            // color: Colors.grey,
          // ),
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

class myPageInfo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        // Container(
        //   height: 0,
        //   child: (futureUser == null) ? Text('noUser') : buildFutureBuilder(),
        // ),
        RichText(
          text: TextSpan(

            style: TextStyle(
              color: Colors.black,
              fontSize: 30.0,

            ),
            children: <TextSpan>[
              TextSpan(
                text: '${nickname}님,\n',
                style: TextStyle(
                  fontFamily: 'NanumSquareExtraBold',
                ),

              ),
              // TextSpan(
              //   text: '\n',
              //     style: TextStyle(fontSize:157.0 - 116.0 - 34.0)),

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
        // 211.0 - 157.0 - 34.0
        RichText(
          text: TextSpan(
            style: TextStyle(
              color: Colors.black,
              fontSize: 30.0,
            ),
            children: <TextSpan>[
              TextSpan(
                  text: '${favorite_bread_style}빵',
                  style: TextStyle(
                    fontSize: 15.0,
                    color: Colors.blue,
                  )),
              TextSpan(
                  text: '을 좋아하시는군요\n새로운 ${favorite_bread_style}빵을 찾아 떠나볼까요?',
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