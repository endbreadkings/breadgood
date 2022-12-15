import 'dart:async';
import 'dart:core';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/my_page/controller/mypage_controller.dart';
import 'package:breadgood_app/modules/my_page/repository/my_page_repository.dart';

class UserInfo extends StatefulWidget {
  UserInfo({Key key}) : super(key: key);
  @override
  _UserInfoState createState() => _UserInfoState();
}

class _UserInfoState extends State<UserInfo> {
  Future<User> futureUser;
  // User user;
  final controller = Get.put(MyPageController());

  @override
  void initState() {
    super.initState();
    futureUser = fetchUser();
    buildFutureBuilder();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          GetBuilder<MyPageController>(builder: (_) {
            return Container(
              width: 0,
              height: 0,
            );
          }),
        ],
      ),
    );
    // );
  }
  // )
  // );

  FutureBuilder<User> buildFutureBuilder() {
    return FutureBuilder<User>(
      future: futureUser,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Container(width: 0, height: 0);
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        return Text('err');
      },
    );
  }
}

class User {
  final int id;
  final String nickName;
  final String profileImgUrl;
  final int breadStyleId;
  final String breadStyleName;
  final bool withdrawal;

  User({
    this.id,
    this.nickName,
    this.profileImgUrl,
    this.breadStyleId,
    this.breadStyleName,
    this.withdrawal,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      nickName: json['nickName'],
      profileImgUrl: json['profileImgUrl'],
      breadStyleId: json['breadStyleId'],
      breadStyleName: json['breadStyleName'],
      withdrawal: json['withdrawal'],
    );
  }
}
