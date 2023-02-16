import 'dart:async';
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/modules/my_page/controller/mypage_controller.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/services/rest_api_service.dart';
import 'package:breadgood_app/constant/api_path.dart' as api_path;

// String token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjI4MDA4MTE1LCJleHAiOjE2MjkyMTc3MTV9.UuC4zpdd95TPgO1Zj-8TsmvNHWLqG3PB9aB7FFeyNogBhD1A9PSwXuSEDA0NBxPKJYwwC7om1dxKCwpA0EBpNQ';
Future<User> fetchUser() async {
  final response = await http.get(
      Uri.parse('${api_path.restApiUrl}/user/me'),
      headers: await headers(),
      );
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);
  return User.fromJson(responseJson);
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

  // String get name => nickName;


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

// class UserManager {
//   User _user;
//   User get user => _user;
// }

// void main() => runApp(MyApp());
//
class UserInfo extends StatefulWidget {
  UserInfo({Key key}) : super(key: key);
  // const UserInfo({Key? key}) : super(key: key);
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
    // controller.UpdateUser(futureUser);
    buildFutureBuilder();
    // controller.UpdateUser(futureUser);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body:
        Column(
        children: [
        // GetBuilder<MyPageController>(builder: (_)
        GetBuilder<MyPageController>  (builder: (_)  {
          print("MyPageController called");
          return Container(width: 0, height: 0,);}),
          // controller.UpdateUser(futureUser);
          // var user = fetchUser();
          // return controller.UpdateUser(user);
          // }),

            //   Column(
            //     children: <Widget>[
            // buildFutureBuilder(),
            // FutureBuilder<User>(
            //   future: futureUser,
            //
            //   builder: (context, snapshot) {
            //     if (snapshot.hasData) {
            //       user = snapshot.data;
            //       controller.UpdateUser(user);
            //     }
            //     else if (snapshot.hasError) {
            //       return Text("${snapshot.error}");
            //     }
            //
            //     // By default, show a loading spinner.
            //     // return CircularProgressIndicator();
            //     return Text('err');
            //   },
            // ),
            Text("abc"),
          // RaisedButton(
          //     child: Text('upload Image >'),
          //     onPressed: () {
                // Get.to(UploadImage());
                // Get.to(BakeryCategoryInfo());
              // }
            // ),
            ],


            ),


          );
          // );
        }
        // )
    // );


  FutureBuilder<User> buildFutureBuilder() {
    // FutureBuilder buildFutureBuilder() {

      return FutureBuilder<User>(
    // return FutureBuilder(
      future: futureUser,
      builder: (context, snapshot) {
        print('futurebuilder of user called');
        if (snapshot.hasData) {
          print('has data!!!!!!!!');

          // controller.UpdateUser(snapshot.data);
          return Container(width: 0, height: 0);
        } else if (snapshot.hasError) {
          print('error?');
          return Text("${snapshot.error}");
        }

        // By default, show a loading spinner.

        // return CircularProgressIndicator();
        return Text('err');
      },
    );
  }
}
