import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:breadgood_app/modules/dashboard/dashboard.dart';
import 'package:breadgood_app/modules/main/screens/main_map.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:breadgood_app/modules/register_review/screens/register_review.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';

import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/utils/services/rest_api_service.dart';

class AlreadyRegisteredBakeryPage extends StatefulWidget {
  const AlreadyRegisteredBakeryPage({Key key}) : super(key: key);

  @override
  _AlreadyRegisteredBakeryPageState createState() =>
      _AlreadyRegisteredBakeryPageState();
}

class _AlreadyRegisteredBakeryPageState
    extends State<AlreadyRegisteredBakeryPage> {
  final controller = Get.put(BakeryController());
  final dController = Get.put(DashboardController());

  SearchData selectedBakery;
  final first_registerer = (Get.arguments)[0]["registerer"];
  final bakeryId = (Get.arguments)[1]["bakeryId"];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AlreadyRegisteredBakeryAppbar(),
        body: GetBuilder<BakeryController>(
            builder: (_) {
              print('GetBuilder alreadyregistered');
              selectedBakery = controller.selectedBakery;
              // print('selected Bakery from controller: ${selectedBakery.title}');
              // return Container(width:0, height: 0);
              return Container(
                // margin: EdgeInsets.all(12.0),
                  width: double.infinity,
                  child: Padding(
                    padding: EdgeInsets.fromLTRB(31, 26, 30, 126),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [

                        RichText(
                          text: TextSpan(
                            style: TextStyle(
                              color: Colors.black,
                              fontSize: 16.0,
                              fontWeight: FontWeight.w400,
                            ),
                            children: <TextSpan>[
                              TextSpan(
                                  text: '${selectedBakery.title}\n',
                                // text: 'example bakery',
                                  style: TextStyle(
                                    fontWeight: FontWeight.w800,
                                    fontFamily: 'NanumSquareRound',
                                    fontSize: 26.0,
                                  )),
                              TextSpan(
                                  text: '\n',
                                  style: TextStyle(
                                    fontSize: 12.0,
                                  )),
                              TextSpan(
                                text:
                                '앗! 이미 ${first_registerer}의\n'
                                    '최애빵집으로 등록되었어요\n'
                                    '대신 다른 빵덕후들과\n'
                                    '나만의 후기를 나눠보세요!',
                              ),
                            ], // buildFutureBuilder(),
                          ),
                        ),

                        // Text(
                        //     '${selectedBakery.title}',
                        // style: TextStyle(
                        //   fontWeight: FontWeight.w800,
                        //   fontFamily: 'NanumSquareRound',
                        //   fontSize: 26.0,
                        // )),
                        // Text(
                        //     '의\n빵집 카테고리를 설정해주세요.',
                        //     style: TextStyle(
                        //       fontWeight: FontWeight.w400,
                        //       fontSize: 18.0,
                        //     )),

                        // Text("표정으로 당신의 마음을 보여주세요!"),
                        // Card(
                        //   child: InkWell(
                        //
                        //   ),
                        //   SizedBox(
                        //     width: 300,
                        //     height: 100,
                        //     child: Text(
                        //       '커피&차와 함께 빵을 즐길 수 있는 베이커리',
                        //     ),
                        //   ),
                        // ),
                        Spacer(),
                        Align(
                          alignment: Alignment.bottomCenter,
                          child: Column(
                            // SizedBox(
                            //   width: 314,
                            //   height: 56,
                            children: [
                              Padding(
                                padding: EdgeInsets.only(bottom: 8),
                                child: ElevatedButton(
                                  style: ElevatedButton.styleFrom(
                                    minimumSize: Size(double.infinity, 56),
                                    primary: Color(0xFF4579FF),
                                    shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(
                                            30)), // double.infinity is the width and 30 is the height
                                  ),
                                  child: Text("지금 할래요",
                                      style: TextStyle(
                                        color: Color(0xFFFFFFFF),
                                        fontSize: 16,
                                        fontWeight: FontWeight.w600,
                                      )),
                                  onPressed: () {
                                    Get.to(RegisterReviewPage(), arguments: bakeryId);
                                  },
                                ),
                              ),
                              // child:
                              ElevatedButton(
                                style: ElevatedButton.styleFrom(
                                  minimumSize: Size(double.infinity, 56),
                                  primary: Color(0xFF888888),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(
                                          30)), // double.infinity is the width and 30 is the height
                                ),
                                child: Text("다음에 할게요",
                                    style: TextStyle(
                                      color: Color(0xFFFFFFFF),
                                      fontSize: 16,
                                      fontWeight: FontWeight.w600,
                                    )),
                                onPressed: () {
                                  dController.changePageIndex(RouteName.Home.index);
                                  Get.offAllNamed('/dashboard');
                                },
                              ),
                            ],
                          ),
                          // ),
                        ),
                      ],
                    ),
                  ));
            }
        ),

        // Container(
        //     // margin: EdgeInsets.all(12.0),
        //     width: double.infinity,
        //     child: Padding(
        //       padding: EdgeInsets.fromLTRB(31, 26, 30, 126),
        //       child: Column(
        //         crossAxisAlignment: CrossAxisAlignment.start,
        //         children: [
        //
        //           RichText(
        //             text: TextSpan(
        //               style: TextStyle(
        //                 color: Colors.black,
        //                 fontSize: 16.0,
        //                 fontWeight: FontWeight.w400,
        //               ),
        //               children: <TextSpan>[
        //                 TextSpan(
        //                     text: '${selectedBakery.title}\n',
        //                     style: TextStyle(
        //                       fontWeight: FontWeight.w800,
        //                       fontFamily: 'NanumSquareRound',
        //                       fontSize: 26.0,
        //                     )),
        //                 TextSpan(
        //                     text: '\n',
        //                     style: TextStyle(
        //                       fontSize: 12.0,
        //                     )),
        //                 TextSpan(
        //                   text:
        //                   '앗! 이미 ${first_registerer}의\n'
        //                       '최애빵집으로 등록되었어요\n'
        //                       '대신 다른 빵덕후들과\n'
        //                       '나만의 후기를 나눠보세요!',
        //                 ),
        //               ], // buildFutureBuilder(),
        //             ),
        //           ),
        //
        //           // Text(
        //           //     '${selectedBakery.title}',
        //           // style: TextStyle(
        //           //   fontWeight: FontWeight.w800,
        //           //   fontFamily: 'NanumSquareRound',
        //           //   fontSize: 26.0,
        //           // )),
        //           // Text(
        //           //     '의\n빵집 카테고리를 설정해주세요.',
        //           //     style: TextStyle(
        //           //       fontWeight: FontWeight.w400,
        //           //       fontSize: 18.0,
        //           //     )),
        //
        //           // Text("표정으로 당신의 마음을 보여주세요!"),
        //           // Card(
        //           //   child: InkWell(
        //           //
        //           //   ),
        //           //   SizedBox(
        //           //     width: 300,
        //           //     height: 100,
        //           //     child: Text(
        //           //       '커피&차와 함께 빵을 즐길 수 있는 베이커리',
        //           //     ),
        //           //   ),
        //           // ),
        //           Spacer(),
        //           Align(
        //             alignment: Alignment.bottomCenter,
        //             child: Column(
        //               // SizedBox(
        //               //   width: 314,
        //               //   height: 56,
        //               children: [
        //                 Padding(
        //                   padding: EdgeInsets.only(bottom: 8),
        //                 child: ElevatedButton(
        //                   style: ElevatedButton.styleFrom(
        //                     minimumSize: Size(double.infinity, 56),
        //                     primary: Color(0xFF4579FF),
        //                     shape: RoundedRectangleBorder(
        //                         borderRadius: BorderRadius.circular(
        //                             30)), // double.infinity is the width and 30 is the height
        //                   ),
        //                   child: Text("지금 할래요",
        //                       style: TextStyle(
        //                         color: Color(0xFFFFFFFF),
        //                         fontSize: 16,
        //                         fontWeight: FontWeight.w600,
        //                       )),
        //                   onPressed: () {
        //                     Get.to(RegisterReviewPage());
        //                   },
        //                 ),
        //                 ),
        //                 // child:
        //                 ElevatedButton(
        //                   style: ElevatedButton.styleFrom(
        //                     minimumSize: Size(double.infinity, 56),
        //                     primary: Color(0xFF888888),
        //                     shape: RoundedRectangleBorder(
        //                         borderRadius: BorderRadius.circular(
        //                             30)), // double.infinity is the width and 30 is the height
        //                   ),
        //                   child: Text("다음에 할게요",
        //                       style: TextStyle(
        //                         color: Color(0xFFFFFFFF),
        //                         fontSize: 16,
        //                         fontWeight: FontWeight.w600,
        //                       )),
        //                   onPressed: () {
        //                     Get.to(BaseMapPage());
        //                   },
        //                 ),
        //               ],
        //             ),
        //             // ),
        //           ),
        //         ],
        //       ),
        //     ))
    );
  }
}

class AlreadyRegisteredBakeryAppbar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
        icon: Image.asset('asset/images/Vector.png'),
        onPressed: () {Get.toNamed('/dashboard');}
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

Future<http.Response> checkBakeryInfo(int bakeryId) async {
  print('checkBakeryInfo');
  final response = await http.post(
      Uri.parse(
          'https://api.breadgood.com/api/v1/bakery/${bakeryId}'),
      headers: await headers(),
      body: <String, String>{
        'bakeryId': 'bakeryId',
      });
  if (response.statusCode == 200) {
    print('status 200!');
  } else {
    print('error code: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);
}