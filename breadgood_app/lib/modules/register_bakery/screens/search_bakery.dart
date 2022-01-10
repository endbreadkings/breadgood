import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/modules/register_bakery/screens/select_bakery_category.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'dart:io';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:core';
import 'package:naver_map_plugin/naver_map_plugin.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/utils/services/rest_api_service.dart';

Future<NaverMapData> fetchSearchData(String searchKeyword) async {
  var endpointUrl = 'https://openapi.naver.com/v1/search/local.json';
  Map<String, String> queryParams = {
    'query': '$searchKeyword',
    'display': '10',
    'start': '1',
    'sort': 'random',
  };
  String queryString = Uri(queryParameters: queryParams).query;

  var requestUrl = endpointUrl +
      '?' +
      queryString; // result - https://www.myurl.com/api/v1/user?param1=1&param2=2
  var response = await http.get(
    Uri.parse(requestUrl),
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Charset': 'utf-8',
      'X-Naver-Client-id': 'p24UOax9LBbitflVcBAv',
      'X-Naver-Client-Secret': 'G7hXtfqhP3',
    },
  );

  final responseJson = json.decode(response.body);
  print(responseJson);
  // print('lastbuildDate: ${responseJson['lastBuildDate']}!');
  // print('total: ${responseJson['total!']}');
  // print('start: ${responseJson['start']}!');
  // print('display: ${responseJson['display']}!');

  return NaverMapData.fromJson(responseJson);
}

class SearchBakeryPage extends StatefulWidget {
  const SearchBakeryPage({Key key}) : super(key: key);

  @override
  _SearchBakeryPageState createState() => _SearchBakeryPageState();
}

class _SearchBakeryPageState extends State<SearchBakeryPage> {
  bool searched = false;
  Future<NaverMapData> FsearchedBakeries;

  _onChanged(String text) {
    print('searched?: ');
    print(searched);

    FsearchedBakeries = fetchSearchData(text);

    setState(() {
      (FsearchedBakeries != null) ? searched = true : searched = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: SearchBakeryPageAppbar(),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: EdgeInsets.fromLTRB(30.0, 26.0, 26.0, 0),
              child: Text("최애 빵집 등록하기",
                  style: TextStyle(
                    fontSize: 26.0,
                    fontWeight: FontWeight.w800,
                    fontFamily: 'NanumSquareExtraBold',
                  )),
            ),
            Padding(
              padding: EdgeInsets.fromLTRB(30, 14.0, 26, 50.0),
              child: Text("진정한 빵덕후라면 남들이 발견못한\n최애빵집 하나 정도는 있겠죠?",
                  style: TextStyle(
                    fontSize: 16.0,
                  )),
            ),
            Padding(
              padding: EdgeInsets.fromLTRB(30.0, 0.0, 26.0, 0),
              child: RichText(
                text: TextSpan(
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 16.0,
                    fontWeight: FontWeight.w600,
                  ),
                  children: <TextSpan>[
                    TextSpan(
                        text: '빵집명',
                        style: TextStyle(
                          fontWeight: FontWeight.w600,
                          color: Color(0xFF4579FF),
                        )),
                    TextSpan(
                      text: '을 검색해주세요!',
                    ),
                  ], // buildFutureBuilder(),
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.fromLTRB(30, 36.0, 30.0, 0),
              child: TextFormField(
                style: TextStyle(
                  fontSize: 16.0,
                ),
                onChanged: _onChanged,
                decoration: InputDecoration(
                  border: UnderlineInputBorder(),
                  contentPadding: EdgeInsets.all(0),
                ),
              ),
            ),
            (searched == false)
                ? Padding(
                    padding: EdgeInsets.only(top: 107),
                    child: Align(
                      alignment: Alignment.center,
                      child:
                          Image.asset('asset/images/cat_black_and_white.png'),
                    ))
                : Padding(
                    padding: EdgeInsets.fromLTRB(20, 40, 20, 0),
                    child: (FsearchedBakeries == null)
                        ? Text('!noSearchedBakeries')
                        : buildFutureSearchedBakeriesBuilder(),
                  ),
          ],
        ),
      ),

      // )
    );
  }

  FutureBuilder<NaverMapData> buildFutureSearchedBakeriesBuilder() {
    return FutureBuilder<NaverMapData>(
      future: FsearchedBakeries,
      builder: (context, AsyncSnapshot<NaverMapData> snapshot) {
        print('builder');
        print(snapshot.hasData);
        if (snapshot.hasData) {
          print('has data?');
          if (snapshot.data.items != null) {
            return Column(children: <Widget>[
              for (var item in snapshot.data.items)
                if ((item.category == '음식점>카페,디저트') ||
                    (item.category == '카페,디저트>베이커리') ||
                    (item.category == '음식점>브런치'))
                  Padding(
                    padding: EdgeInsets.only(bottom: 8),
                    child: Container(
                        // width: 335,
                        width: double.infinity,
                        height: 103,
                        //   height: 100,
                        //   height: 200,
                        child: BakeryCard(
                          selectedBakery: item,
                        )),
                    // BakeryCard(item),
                  ),
            ]);
          }
        } else if (snapshot.hasError) {
          print('error?');
          return Text("${snapshot.error}");
        }
        return Padding(
            padding: EdgeInsets.only(top: 107),
            child: Align(
              alignment: Alignment.center,
              child: Image.asset('asset/images/cat_black_and_white.png'),
            ));
      },
    );
  }
}

class SearchBakeryPageAppbar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
        icon: Image.asset('asset/images/Vector.png'),
        onPressed: () => Navigator.pushReplacementNamed(context, '/main'),
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

// Container BakeryCard(SearchData selectedBakery) {
// Container BakeryCard(String name, String roadAddress) {
// Future<CheckDuplicateBakery> result = checkRegisteredBakery(selectedBakery.roadAddress);
class BakeryCard extends StatefulWidget {
  final SearchData selectedBakery;
  BakeryCard({Key key, this.selectedBakery}) : super(key: key);
  // const UserInfo({Key? key}) : super(key: key);
  @override
  _BakeryCardState createState() => _BakeryCardState();
}

class _BakeryCardState extends State<BakeryCard> {
  Future<CheckDuplicateBakery> futureCheckBakeryDuplicate;
  final controller = Get.put(BakeryController());
  // bool duplicate;
  // String nickname;
  CheckDuplicateBakery checkDup;

  // print('_checkDuplicatedBakeryState');
  @override
  void initState() {
    // print('checkDuplicatedBakeryState initstate');
    super.initState();
    //   // futureCheckBakeryDuplicate = checkRegisteredBakery(widget.selectedBakery.roadAddress);
  }

  @override
  Widget build(BuildContext context) {
    //   print('_checkDuplicatedBakeryState');
    return Scaffold(
        // resizeToAvoidBottomInset: false,
        body:
            // Column(
            // children: [
            GetBuilder<BakeryController>(builder: (_) {
      print("bakeryCard called");
      // return Text('getBuilder called');
      //   return Container(height: 0, width: 0);
      // }),
      return
        Container(
        // new ConstrainedBox(
        //     constraints: new BoxConstraints(
        //       minHeight: 103.0,
        //       minWidth: 5.0,
              // maxHeight: 123.0,
              // maxWidth: 30.0,
            // ),
          // child: DecoratedBox(
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(16.0),
              border: Border.all(color: Colors.blueAccent)),
          child: Padding(
              padding: EdgeInsets.fromLTRB(14, 21.48, 14, 26.0),
              child:
                  Row(crossAxisAlignment: CrossAxisAlignment.start, children: [
                    Padding(
                      padding: EdgeInsets.fromLTRB(0, 8.52, 11.3, 3),
                child: Image.asset('asset/images/bread_black_and_white.png',
                    height: 44, width: 46.4)),
                Padding(
                    padding: EdgeInsets.only(right: 14.0),
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(widget.selectedBakery.title,
                              // selectedBakery.title,
                              style: TextStyle(
                                fontFamily: 'NanumSquareRound',
                                fontSize: 14.0,
                                fontWeight: FontWeight.w800,
                              )),
                          SizedBox(
                            width: 181,
                            height: 5.52,
                          ),
                          Container (
                    width: 181,
                          child: Text(
                              // selectedBakery.roadAddress,
                              widget.selectedBakery.roadAddress,
                              style: TextStyle(
                                fontSize: 10.0,
                              ))),
                        ])),
                Spacer(),
                // checkDuplicatedBakery(selectedBakery: selectedBakery,),
                // Align(
                //     alignment: Alignment.topRight,
                //     child:
                    Padding(
                      padding: EdgeInsets.fromLTRB(0, 15.52, 0, 12),
                child: SizedBox(
                  width: 57,
                  height: 28,
                  //   height: 58,
                  //   height: 85,
                  child:
                      // checkDuplicatedBakery(selectedBakery: selectedBakery,)
                      RaisedButton(
                          // materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                          color: Color(0xFF4579FF),
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(20)),
                          child: Text('선택',
                              style: TextStyle(
                                fontSize: 14.0,
                                color: Color(0xFFFFFFFF),
                              )),
                          onPressed: () async {
                            print('onPressed');

                            // controller update
                            // selected bakery
                            // duplicate
                            // controller.UpdateDuplicateCheck(duplicate)
                            controller.UpdateBakery(widget.selectedBakery);

                            var checkDuplicate = await checkRegisteredBakery(
                                widget.selectedBakery.roadAddress);
                            print('In Builder');
                            // checkDup = checkDuplicatedBakery(selectedBakery: widget.selectedBakery);
                            // buildFutureBuilder();
                            // setState((){buildFutureBuilder();});

                            // (checkRegisteredBakery(selectedBakery.roadAddress).isDuplicate == true)
                            // (result.isDuplicate == true)
                            // (true)
                            // (controller.duplicateCheck == true)
                            (checkDuplicate.idDuplicate == true)
                                ? Get.toNamed('/register_bakery/already_registered_bakery',
                                    arguments: [checkDuplicate.nickName, checkDuplicate.bakeryId])
                                // :Get.to(SelectBakeryCategoryPage(), arguments: selectedBakery);
                                : Get.toNamed('/register_bakery/select_bakery_category',
                                    arguments: widget.selectedBakery);
                            print('executed');
                          }),
                ))
                // )
              ])));
    })
        // ]
        );
    // ),
  }

  FutureBuilder<CheckDuplicateBakery> buildFutureBuilder() {
    return FutureBuilder<CheckDuplicateBakery>(
      future: futureCheckBakeryDuplicate,
      builder: (context, snapshot) {
        print('futurebuilder called');
        if (snapshot.hasData) {
          print('has data!!!!!!!!');
          controller.UpdateDuplicateCheck(snapshot.data);
          // controller.UpdateBakery(widget.selectedBakery);
          // duplicate = snapshot.data.idDuplicate;
          // nickname = snapshot.data.nickName;
          return Container(width: 0, height: 0);
        } else if (snapshot.hasError) {
          print('error?');
          return Text("${snapshot.error}");
        }

        // By default, show a loading spinner.

        return CircularProgressIndicator();
        // return Text('err');
      },
    );
  }
}

// );}

// FutureBuilder<CheckDuplicateBakery>(
// future: futureCheckBakeryDuplicate,
//
// builder: (context, snapshot) {
// if (snapshot.hasData) {
// print('has data!!!!!!!!');
// controller.UpdateDuplicateCheck(snapshot.data);
// controller.UpdateBakery(widget.selectedBakery);
// // duplicate = snapshot.data.idDuplicate;
// // nickname = snapshot.data.nickName;
// return Container(width: 0, height: 0);
// }
// else if (snapshot.hasError) {
// return Text("${snapshot.error}");
// }
//
// // By default, show a loading spinner.
// return CircularProgressIndicator();
// // return Text('err');
// },
// );

// Future<http.Response> checkRegisteredBakery(String roadAddress) async {
Future<CheckDuplicateBakery> checkRegisteredBakery(String roadAddress) async {
  print('checkRegisteredBakery');
  final response = await http.post(
      Uri.parse(
          'https://api.breadgood.com/api/v1/bakery/duplicate/roadAddress/${roadAddress}'),
      // headers: await headers(),
      headers: await headers_post(),
      body: <String, String>{
        'roadAddress': 'roadAddress',
      });
  if (response.statusCode == 200) {
    print('status 200!');
  } else {
    print('error code: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);

  return CheckDuplicateBakery.fromJson(responseJson);
}

class CheckDuplicateBakery {
  final int bakeryId;
  final bool idDuplicate;
  final String nickName;

  CheckDuplicateBakery({
    this.bakeryId,
    this.idDuplicate,
    this.nickName,
  });

  factory CheckDuplicateBakery.fromJson(Map<String, dynamic> json) {
    print("CheckDuplicateBakery data called");
    return CheckDuplicateBakery(
      bakeryId: json['bakeryId'],
      idDuplicate: json['idDuplicate'],
      nickName: json['nickName'],
    );
  }
}

// class checkDuplicatedBakery extends StatefulWidget {
//   final SearchData selectedBakery;
//   checkDuplicatedBakery({Key key, this.selectedBakery}) : super(key: key);
//   // const UserInfo({Key? key}) : super(key: key);
//   @override
//   _checkDuplicatedBakeryState createState() => _checkDuplicatedBakeryState();
// }
//
// class _checkDuplicatedBakeryState extends State<checkDuplicatedBakery> {
//   Future<CheckDuplicateBakery> futureCheckBakeryDuplicate;
//   final controller = Get.put(BakeryController());
//   // bool duplicate;
//   // String nickname;
//
//   // print('_checkDuplicatedBakeryState');
//   @override
//   void initState() {
//     print('checkDuplicatedBakeryState initstate');
//     super.initState();
//     futureCheckBakeryDuplicate = checkRegisteredBakery(widget.selectedBakery.roadAddress);
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     print('_checkDuplicatedBakeryState');
//     return Scaffold(
//       // resizeToAvoidBottomInset: false,
//       body:
//       // GetBuilder<BakeryController>(
//       //         builder: (_)
//     // {
//     //   print('getbuilder of search bakery: ${widget.selectedBakery.title}');
//
//       // return
//         FutureBuilder<CheckDuplicateBakery>(
//         future: futureCheckBakeryDuplicate,
//
//         builder: (context, snapshot) {
//           if (snapshot.hasData) {
//             print('has data!!!!!!!!');
//             controller.UpdateDuplicateCheck(snapshot.data);
//             controller.UpdateBakery(widget.selectedBakery);
//             // duplicate = snapshot.data.idDuplicate;
//             // nickname = snapshot.data.nickName;
//             return Container(width: 0, height: 0);
//           }
//           else if (snapshot.hasError) {
//             return Text("${snapshot.error}");
//           }
//
//           // By default, show a loading spinner.
//           return CircularProgressIndicator();
//           // return Text('err');
//         },
//       ),
//     // })
//
//
//
//
//       // Column(
//       //   children: [
//     //   GetBuilder<BakeryController>(
//     //       builder: (_) {
//     //         print('getbuilder of search bakery: ${widget.selectedBakery.title}');
//     //
//     //         // return Container(width:0, height: 0);
//     //         return Column(
//     //             children: [
//     //               FutureBuilder<CheckDuplicateBakery>(
//     //           future: futureCheckBakeryDuplicate,
//     //
//     //           builder: (context, snapshot) {
//     //             if (snapshot.hasData) {
//     //               duplicate = snapshot.data.idDuplicate;
//     //               nickname = snapshot.data.nickName;
//     //               return Container(width: 0, height:0);
//     //             }
//     //             else if (snapshot.hasError) {
//     //           return Text("${snapshot.error}");
//     //         }
//     //
//     //         // By default, show a loading spinner.
//     //         // return CircularProgressIndicator();
//     //         // return Text('err');
//     //
//     // },
//     //
//     //             ),
//     //                 // Align(
//     //                 //   alignment: Alignment.topRight,
//     //                 //   child:
//     //                 //   SizedBox(
//     //                 //     width: 57,
//     //                 //     height: 28,
//     //                 //     child:
//     //
//     //
//     //
//     //
//     //                 RaisedButton(
//     //                   // materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
//     //                     color: Color(0xFF4579FF),
//     //                     shape: RoundedRectangleBorder(
//     //                         borderRadius: BorderRadius.circular(20)),
//     //                     child: Text(
//     //                         '선택',
//     //                         style: TextStyle(
//     //                           fontSize: 14.0,
//     //                           color: Color(0xFFFFFFFF),
//     //                         )),
//     //                     onPressed: () {
//     //                       // (checkRegisteredBakery(selectedBakery.roadAddress) == true)
//     //                       controller.UpdateBakery(widget.selectedBakery);
//     //                       // print("중복확인? ${snapshot.data.idDuplicate}");
//     //                       // (snapshot.data.idDuplicate == true)
//     //                       print("duplicate: ${duplicate}");
//     //                       (duplicate == true)
//     //                           // ? Get.to(AlreadyRegisteredBakeryPage(), arguments: snapshot.data.nickName)
//     //                           ? Get.to(AlreadyRegisteredBakeryPage(), arguments: nickname)
//     //                           :Get.to(SelectBakeryCategoryPage(), arguments: widget.selectedBakery);
//     //                     }),
//     //
//     //
//     //
//     //
//     //               // ));
//     //           //   } else if (snapshot.hasError) {
//     //           //     return Text("${snapshot.error}");
//     //           //   }
//     //           //
//     //           //   // By default, show a loading spinner.
//     //           //   // return CircularProgressIndicator();
//     //           //   return Text('err');
//     //           // },
//     //         ],
//     //         );
//     //       }
//     //   ),
//
//
//
//
//
//
//           // FutureBuilder<CheckDuplicateBakery>(
//           //   future: futureCheckBakeryDuplicate,
//           //
//           //   builder: (context, snapshot) {
//           //     if (snapshot.hasData) {
//           //       return
//           //         // Align(
//           //         //   alignment: Alignment.topRight,
//           //         //   child:
//           //         //   SizedBox(
//           //         //     width: 57,
//           //         //     height: 28,
//           //         //     child:
//           //     RaisedButton(
//           //                 // materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
//           //                 color: Color(0xFF4579FF),
//           //                 shape: RoundedRectangleBorder(
//           //                     borderRadius: BorderRadius.circular(20)),
//           //                 child: Text(
//           //                     '선택',
//           //                     style: TextStyle(
//           //                       fontSize: 14.0,
//           //                       color: Color(0xFFFFFFFF),
//           //                     )),
//           //                 onPressed: () {
//           //                   // (checkRegisteredBakery(selectedBakery.roadAddress) == true)
//           //                   print("중복확인? ${snapshot.data.idDuplicate}");
//           //                   (snapshot.data.idDuplicate == true)
//           //                       ? Get.to(AlreadyRegisteredBakeryPage(), arguments: snapshot.data.nickName)
//           //                       :Get.to(SelectBakeryCategoryPage(), arguments: widget.selectedBakery);
//           //                 });
//           //           // ));
//           //     } else if (snapshot.hasError) {
//           //       return Text("${snapshot.error}");
//           //     }
//           //
//           //     // By default, show a loading spinner.
//           //     // return CircularProgressIndicator();
//           //     return Text('err');
//           //   },
//           // ),
//
//     // ],
//     // ),
//
//
//
//
//
//     );
//     // );
//   }
// }
