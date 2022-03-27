import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/modules/register_bakery/screens/select_bakery_category.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:word_break_text/word_break_text.dart';
import 'package:get/get.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:core';
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
                    fontFamily: 'NanumSquareRoundEB',
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
                    fontFamily: 'NanumSquareRoundB',
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
                    padding: EdgeInsets.only(bottom: 16),
                    child: Container(
                        // width: 335,
                        width: double.infinity,
                        // 도로명 주소 16자 이상 & 가게이름 14자 미만 이거나
                        // 도로명 주소 16자 미만 & 가게이름 14자 이상이면 높이: 103
                        // 도로명 주소 16자 미만 & 가게이름 14자 미만이면 높이: 86
                        // 도로명 주소 16자 이상 & 가게 이름 14자 이상이면 높이: 123
                        height: ((item.title.length > 14) &&
                                (item.roadAddress.length > 16))
                            ? 123
                            : ((item.title.length > 14) &&
                                    (item.roadAddress.length <= 16))
                                ? 103
                                : ((item.title.length <= 14) &&
                                        (item.roadAddress.length > 16))
                                    ? 103
                                    : 86,
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
      return Container(
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(16.0),
              color: Colors.white,
              boxShadow: [
                BoxShadow(
                  color: Color(0xFF1C2F85).withOpacity(0.15),
                  offset: Offset(2.0, 2.0),
                  blurRadius: 10.0,
                  spreadRadius: 0,
                )
              ]),
          child: Row(mainAxisAlignment: MainAxisAlignment.start, children: [
            Padding(
                padding: EdgeInsets.fromLTRB(14, 0, 0, 0),
                child: Center(
                    child: Container(
                        height: 44,
                        width: 46.4,
                        child: SvgPicture.asset(
                          'asset/images/icon/registerBakery/bread_black_and_white.svg',
                          fit: BoxFit.scaleDown,
                        )))),
            Padding(
                padding: EdgeInsets.fromLTRB(11.3, 21.48, 0, 26),
                child: Container(
                    width: 180,
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          SizedBox(
                              width: 180,
                              child: WordBreakText(widget.selectedBakery.title,
                                  style: TextStyle(
                                    fontFamily: 'NanumSquareRoundEB',
                                    fontSize: 14.0,
                                  ))),
                          SizedBox(
                            width: 180,
                            height: 5.04,
                          ),
                          SizedBox(
                            width: 180,
                            child: WordBreakText(
                                widget.selectedBakery.roadAddress,
                                style: TextStyle(
                                    fontSize: 12.0, color: Color(0xFFA4A4A4))),
                          )
                        ]))),
            Spacer(),
            Padding(
                padding: EdgeInsets.fromLTRB(0, 29, 14, 29),
                child: SizedBox(
                  width: 54,
                  height: 28,
                  //   height: 58,
                  //   height: 85,
                  child: RaisedButton(
                      // materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                      color: Color(0xFF4579FF),
                      elevation: 0,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20)),
                      child: Container(
                          height: 28,
                          child: Flexible(
                              child: Center(
                                  child: Text('선택',
                                      maxLines: 1,
                                      softWrap: false,
                                      overflow: TextOverflow.visible,
                                      style: TextStyle(
                                        fontSize: 14.0,
                                        color: Color(0xFFFFFFFF),
                                      ))))),
                      onPressed: () async {
                        print('onPressed');
                        controller.UpdateBakery(widget.selectedBakery);

                        var checkDuplicate = await checkRegisteredBakery(
                            widget.selectedBakery.roadAddress);
                        (checkDuplicate.idDuplicate == true)
                            ? Get.to(AlreadyRegisteredBakeryPage(),
                            arguments: [checkDuplicate.nickName, checkDuplicate.bakeryId])
                            : Get.to(SelectBakeryCategoryPage(),
                                arguments: widget.selectedBakery);
                        print('executed');
                      }),
                ))
          ]));
    }));
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
