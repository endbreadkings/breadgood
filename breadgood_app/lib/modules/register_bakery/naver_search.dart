// import 'dart:async';
// import 'dart:convert';
// import 'dart:io';
// import 'dart:ui';
// // import 'package:apitest2/bakery_category.dart';
// // import 'package:apitest2/upload_image.dart';
// import 'package:flutter/material.dart';
// import 'package:http/http.dart' as http;
//
// //
// import 'package:get/get.dart';
// Future<SearchData> fetchSearchData() async {
//   // final response = await http.get(
//   //     Uri.parse('https://${api_path.restApiUrl}/user/0'),
//   //   // Uri.parse('breadgoodapp://breadgood.com/login/oauth2/success'),
//   //   // Uri.parse('https://api.breadgood.com'),
//   //   headers: {
//   //     HttpHeaders.authorizationHeader: 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjI0OTAzNjM4LCJleHAiOjE2MjQ5MDU0Mzh9.vULw9yHN4npBa7hoIDKaZQo563ktB4AY3tfB3JPSMDLMwJZHo0K9IFdMVJBu8apoQZ9Igw4idhm4zjsxkT3LyA',
//   //   },
//   // );
//   final response = await http.get(
//       Uri.parse('https://openapi.naver.com/v1/search/local.json?query=UTF-8'),
//       headers: {
//         'Content-Type': 'application/json',
//         'Accept': 'application/json',
//         // 'Charset': 'utf-8',
//         // 'query': 'utf-8',
//         'client_id': '9g8N_E2UK09Z_tdtTLwc',
//         'client_secret': 'CYWCQRp3H4',
//       });
//   final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
//   print(responseJson);
//   // return SearchData.fromJson(responseJson);
//
//   if (response.statusCode == 200) {
//     // If the server did return a 200 OK response,
//     // then parse the JSON.
//     return SearchData.fromJson(responseJson);
//     // } else if (response.statusCode == 400) {
//     //   // If the server did not return a 200 OK response,
//     //   // then throw an exception.
//     //   throw Exception('Failed to load user 400');
//   } else {
//     // If the server did not return a 200 OK response,
//     // then throw an exception.
//     // throw Exception('Failed to load user ${response.statusCode}');
//     throw Exception('Failed to load user ${response.statusCode}');
//   }
// }
// //
// //
// //
// class SearchData {
//   final String description;
//
//   SearchData({
//     this.description,
//   });
//
//   factory SearchData.fromJson(Map<String, dynamic> json) {
//     return SearchData(
//       description: json['description'],
//     );
//   }
// }
// //
// // // void main() => runApp(MyApp());
// // //
// // class SearchDataInfo extends StatefulWidget {
// //   SearchDataInfo({Key key}) : super(key: key);
// //   // const UserInfo({Key? key}) : super(key: key);
// //   @override
// //   _SearchDataInfoState createState() => _SearchDataInfoState();
// // }
// //
// // class _SearchDataInfoState extends State<SearchDataInfo> {
// //   Future<SearchData> searchdata;
// //
// //   @override
// //   void initState() {
// //     super.initState();
// //     searchdata = fetchSearchData();
// //   }
// //
// //   @override
// //   Widget build(BuildContext context) {
// //     return
// //       // MaterialApp(
// //       // title: 'Fetch Data Example',
// //       // theme: ThemeData(
// //       //   primarySwatch: Colors.blue,
// //       // ),
// //       // home:
// //       Scaffold(
// //         appBar: AppBar(
// //           title: Text('User Info Example'),
// //         ),
// //         body:
// //         Column (
// //           children: <Widget>[
// //             // Text(${futureUser._nickName}),
// //             FutureBuilder<SearchData>(
// //               future: searchdata,
// //
// //               builder: (context, snapshot) {
// //                 if (snapshot.hasData) {
// //                   // String nickname = futureUser._nickName;
// //                   // print(futureUser.name);
// //                   // return Text(${nickname});
// //                   // return Text(snapshot.data!.nickName);
// //
// //                   // final String nickName = snapshot.data!.nickName;
// //                 } else if (snapshot.hasError) {
// //                   return Text("${snapshot.error}");
// //                 }
// //
// //                 // By default, show a loading spinner.
// //                 // return CircularProgressIndicator();
// //                 return Text('err');
// //               },
// //             ),
// //             Text("abc"),
// //             RaisedButton(
// //                 child:Text('search'),
// //                 onPressed: () {
// //                   // Get.to(UploadImage());
// //                   // Get.to(BakeryCategoryInfo());
// //                 }
// //             ),
// //           ],
// //         ),
// //       );
// //     // );
// //   }
// // }
// //
// // class SearchUser{
// //   static const String USER_ID = ".";
// //   static const String USER_KEY = ".";
// //
// //   static const Map<String, String> USER_HEADER_DATA = {
// //     "X-Naver-Client-Id" : SearchUser.USER_ID,
// //     "X-Naver-Client-Secret" : SearchUser.USER_KEY,
// //     "User-Agent":"Android; ko-kr",
// //     "Accept": "*/*"
// //   };
// // }
// //
// // class FetchDataModelWrapper{
// //   String lastBuildDate;
// //   int total;
// //   int start;
// //   int display;
// //   List items;
// //
// //   FetchDataModelWrapper({
// //     this.lastBuildDate,
// //     this.items,
// //     this.start,
// //     this.display,
// //     this.total
// //   }):assert(items != null);
// //
// //   factory FetchDataModelWrapper.toJson(Map<String, dynamic > e) =>
// //       FetchDataModelWrapper(
// //           lastBuildDate: e['lastBuildDate'],
// //           start: e['start'],
// //           display: e['display'],
// //           total: e['total'],
// //           items: e['items'].map((json) => FetchDataModel.toJson(json)).toList()
// //       );
// // }
// //
// //
// // class FetchDataModel{
// //   String title;
// //   String originallink;
// //   String link;
// //   String description;
// //   String pubDate;
// //
// //   FetchDataModel({
// //     this.title,
// //     this.originallink,
// //     this.link,
// //     this.description,
// //     this.pubDate
// //   }):assert(title != null), assert(description != null);
// //
// //   factory FetchDataModel.toJson(Map<String, dynamic> e) => FetchDataModel(
// //       title: e['title'],
// //       link: e['link'],
// //       description: e['description'],
// //       originallink: e['originallink'],
// //       pubDate: e['pubDate']
// //   );
// //
// //   class ConnectURL{
// //   // API ex : https://openapi.naver.com/v1/search/news.json?query=%EC%98%A4%EB%8A%98&start=10
// //
// //   String query = "속보";
// //   int ea = 10;
// //
// //   ConnectURL({ this.query, this.ea}){
// //   if(this.query == null) this.query = "속보";
// //   if(this.ea == null) this.ea = 10;
// //   if(this.ea > 999) this.ea = 999;
// //   }
// //
// //   String get userid => ConnectUser.USER_ID;
// //   String get userKey => ConnectUser.USER_KEY;
// //   Map<String, String> get userHeader => ConnectUser.USER_HEADER_DATA;
// //
// //   final String _endPoint = "https://openapi.naver.com/v1/search/news.json";
// //
// //   String get url => "$_endPoint?query=${this.query}&start=1&display=${this.ea}&sort=sim";
// //
// //   }
