import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:breadgood_app/modules/register_review/register_review.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_category.dart';

class SelectBakeryCategoryPage extends StatefulWidget {
  const SelectBakeryCategoryPage({Key key}) : super(key: key);

  @override
  _SelectBakeryCategoryPageState createState() =>
      _SelectBakeryCategoryPageState();
}

class _SelectBakeryCategoryPageState extends State<SelectBakeryCategoryPage> {
  SearchData selectedBakery = Get.arguments;
  final controller = Get.put(BakeryController());
  Future<List<BakeryCategory>> bakeryCategoryList;

  @override
  void initState() {
    // TODO: implement initState
    // fetchBakeryCategoryList();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    // Color borderC = Colors.transparent;

    return Scaffold(
        appBar: SelectBakeryCategoryPageAppbar(),
        body: Container(
            // margin: EdgeInsets.all(12.0),
            width: double.infinity,
            child: Padding(
              padding: EdgeInsets.fromLTRB(31, 26, 30, 127),
              child:
              // SingleChildScrollView(
              //   child:
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  RichText(
                    text: TextSpan(
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 18.0,
                        fontWeight: FontWeight.w400,
                      ),
                      children: <TextSpan>[
                        TextSpan(
                            text: '${selectedBakery.title}',
                            style: TextStyle(
                              fontWeight: FontWeight.w800,
                              fontFamily: 'NanumSquareRound',
                              fontSize: 26.0,
                            )),
                        TextSpan(
                          text: '의',
                        ),
                      ],
                    ),
                  ),
                  SizedBox(
                    height: 8,
                    width: 314,
                  ),
                  Text('빵집 카테고리를 설정해주세요.',
                      style: TextStyle(
                        fontSize: 18.0,
                      )),

                  GetBuilder<BakeryController>(
                    builder: (_) {
    // return Container(width:0, height:0);
    // }),
                      return
    buildFutureBakeryCategoryListBuilder();

                        //original
                      //   Column(
                      //   children: [
                      // Padding(
                      //   padding: EdgeInsets.fromLTRB(0, 40, 0, 8),
                      //   child:
                      //
                      //
                      //   ButtonTheme(
                      //       minWidth: 314,
                      //       height: 88,
                      //       child: ElevatedButton(
                      //           onPressed: () {
                      //             controller.toggleButton(0);
                      //             // borderC = (controller.selected) ?Color(0xFF007AFF):Colors.transparent;
                      //           },
                      //           style: ElevatedButton.styleFrom(
                      //               shape: RoundedRectangleBorder(
                      //                   borderRadius: BorderRadius.circular(8),
                      //                   side: BorderSide(
                      //                   width: 1.0,
                      //                   color: controller.border_color[0]
                      //                 // controller.selected
                      //                 //     ?Color(0xFF007AFF)
                      //                 //     :Colors.transparent,
                      //                   )),
                      //               elevation: 1.0,
                      //               primary: Colors.white,
                      //               padding: EdgeInsets.fromLTRB(22, 20, 25, 20),
                      //               ),
                      //
                      //           child: Row(
                      //               crossAxisAlignment: CrossAxisAlignment.start,
                      //               children: [
                      //                 Padding(
                      //                   padding: EdgeInsets.fromLTRB(0, 2, 16, 2),
                      //                 child: Image.asset(
                      //                     'asset/images/icon/map/with_bread.png',
                      //                     height: 36,
                      //                     width: 44),
                      //                 ),
                      //                 Text(
                      //                   '커피&차와 함께 빵을\n즐길 수 있는 베이커리 카페',
                      //                   // 'toggle? ${controller.selected}',
                      //                   style: TextStyle(
                      //                     fontSize: 16,
                      //                     color: Colors.black,
                      //                   ),
                      //                 ),
                      //                 // Text('color change'),
                      //               ])
                      //       ))
                      // ),
                      //
                      //       ButtonTheme(
                      //           minWidth: 314,
                      //           height: 88,
                      //           child: ElevatedButton(
                      //               onPressed: () {
                      //                 controller.toggleButton(1);
                      //                 // borderC = (controller.selected) ?Color(0xFF007AFF):Colors.transparent;
                      //               },
                      //               style: ElevatedButton.styleFrom(
                      //                 shape: RoundedRectangleBorder(
                      //                     borderRadius: BorderRadius.circular(8),
                      //                     side: BorderSide(
                      //                         width: 1.0,
                      //                         color: controller.border_color[1]
                      //                       // controller.selected
                      //                       //     ?Color(0xFF007AFF)
                      //                       //     :Colors.transparent,
                      //                     )),
                      //                 elevation: 1.0,
                      //                 primary: Colors.white,
                      //                 padding: EdgeInsets.fromLTRB(22, 20, 25, 20),
                      //               ),
                      //
                      //               child: Row(
                      //                   crossAxisAlignment: CrossAxisAlignment.start,
                      //                   children: [
                      //                     Padding(
                      //                       padding: EdgeInsets.fromLTRB(0, 2, 16, 2),
                      //                       child: Image.asset(
                      //                           'asset/images/icon/map/focus_bread.png',
                      //                           height: 36,
                      //                           width: 44),
                      //                     ),
                      //                     Text(
                      //                       '빵을 전문적으로 파는\n일반 베이커리',
                      //                       // 'toggle? ${controller.selected}',
                      //                       style: TextStyle(
                      //                         fontSize: 16,
                      //                         color: Colors.black,
                      //                       ),
                      //                     ),
                      //                     // Text('color change'),
                      //                   ])
                      //           )),
                      // ]);
                      //end of original


                        // Text('selected: ${_.selected}, border_color: ${_.border_color}');
                    }
                  ),




                  // Padding(
                  //   padding: EdgeInsets.fromLTRB(0, 40, 0, 8),
                  //   child: ButtonTheme(
                  //       minWidth: 314,
                  //       height: 88,
                  //       child: ElevatedButton(
                  //           onPressed: () {
                  //             controller.toggleButton();
                  //             borderC = (controller.selected) ?Color(0xFF007AFF):Colors.transparent;
                  //           },
                  //           style:
                  //           // ButtonStyle(
                  //           //   backgroundColor: MaterialStateProperty.all<Color>(Colors.white),
                  //           //   side: MaterialStateProperty.resolveWith<BorderSide>(
                  //           //               (Set<MaterialState> states) {
                  //           //             if (states.contains(MaterialState.pressed))
                  //           //               return BorderSide(
                  //           //                 color: Color(0xFF007AFF),
                  //           //               );
                  //           //             return null; // Defer to the widget's default.
                  //           //           },
                  //           //         ),
                  //           //     ),
                  //
                  //
                  //               ElevatedButton.styleFrom(
                  //               shape: RoundedRectangleBorder(
                  //                   borderRadius: BorderRadius.circular(8),
                  //                   side:
                  //                   // MaterialStateProperty.resolveWith<BorderSide>(
                  //                   //       (Set<MaterialState> states) {
                  //                   //     if (states.contains(MaterialState.pressed))
                  //                   //       return BorderSide(
                  //                   //         color: Color(0xFF007AFF),
                  //                   //       );
                  //                   //     return null; // Defer to the widget's default.
                  //                   //   },
                  //                   // ),
                  //
                  //                 BorderSide(
                  //                   width: 1.0,
                  //                   color: controller.border_color
                  //                 // controller.selected
                  //                 //     ?Color(0xFF007AFF)
                  //                 //     :Colors.transparent,
                  //                   )),
                  //               elevation: 1.0,
                  //               primary: Colors.white,
                  //               padding: EdgeInsets.fromLTRB(22, 20, 25, 20),
                  //               ),
                  //
                  //           child: Row(
                  //               crossAxisAlignment: CrossAxisAlignment.start,
                  //               children: [
                  //                 Padding(
                  //                   padding: EdgeInsets.fromLTRB(0, 2, 16, 2),
                  //                 child: Image.asset(
                  //                     'asset/images/icon/map/with_bread.png',
                  //                     height: 36,
                  //                     width: 44),
                  //                 ),
                  //                 Text(
                  //                   // '커피&차와 함께 빵을\n즐길 수 있는 베이커리 카페',
                  //                   'toggle? ${controller.selected}',
                  //                   style: TextStyle(
                  //                     fontSize: 16,
                  //                     color: Colors.black,
                  //                   ),
                  //                 ),
                  //                 // Text('color change'),
                  //               ])
                  //       )),
                  // ),




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
                    child:
                        // SizedBox(
                        //   width: 314,
                        //   height: 56,

                        // child:
                        ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        minimumSize: Size(double.infinity, 56),
                        primary: Color(0xFF4579FF),
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(
                                30)), // double.infinity is the width and 30 is the height
                      ),
                      child: Text("다음",
                          style: TextStyle(
                            color: Color(0xFFFFFFFF),
                            fontSize: 16,
                            fontWeight: FontWeight.w600,
                          )),
                      onPressed: () {
                        Get.toNamed('/register_review/register_reivew_page', arguments: -1);
                      },
                    ),
                    // ),
                  ),
                ],
              // ),
              ),
            )));
  }

  FutureBuilder<List<BakeryCategory>> buildFutureBakeryCategoryListBuilder() {
    print('future Bakery Category List builder');
    return FutureBuilder<List<BakeryCategory>>(

      future: bakeryCategoryList = fetchBakeryCategoryList(),
      builder: (context, snapshot) {
        print('builder');
        if (snapshot.hasData) {
          print('has data');
          return Column(
            children: [
          Padding(
            padding: EdgeInsets.fromLTRB(0, 40, 0, 8),
            child:
              _createBakeryCategoryToggle(snapshot.data[1]),),
              _createBakeryCategoryToggle(snapshot.data[0]),
        ]
          );
        } else if (snapshot.hasError) {
          // print(snapshot.data[0].name);
          return Text("this: ${snapshot.error}");
        }

        // By default, show a loading spinner.
        // return CircularProgressIndicator();
        return
          // Text('err');
          Text('none');
      },
    );
  }


  Widget _createBakeryCategoryToggle(BakeryCategory category) {
    print('createBakeryCategoryToggle');
    return ButtonTheme(
        minWidth: 314,
        height: 88,
        child: ElevatedButton(
            onPressed: () {
              print('pressed: ${category.id}');
              controller.toggleButton(category.id - 1);
              // borderC = (controller.selected) ?Color(0xFF007AFF):Colors.transparent;
            },
            style: ElevatedButton.styleFrom(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8),
                  side: BorderSide(
                      width: 1.0,
                      color: controller.bakery_category_border_color[category.id - 1]
                    // controller.selected
                    //     ?Color(0xFF007AFF)
                    //     :Colors.transparent,
                  )),
              elevation: 1.0,
              primary: Colors.white,
              padding: EdgeInsets.fromLTRB(22, 20, 25, 20),
            ),

            child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: EdgeInsets.fromLTRB(0, 2, 16, 2),
                    child: Image.network(
                        // 'asset/images/icon/map/with_bread.png',
                      category.makerImgUrl,
                        height: 36,
                        width: 44),
                  ),
                  Text(
                    (category.id == 1)
                    ?'커피&차와 함께 빵을\n즐길 수 있는 베이커리 카페'
                    :'빵을 전문적으로 파는\n일반 베이커리',
                    // 'toggle? ${controller.selected}',
                    style: TextStyle(
                      fontSize: 16,
                      color: Colors.black,
                    ),
                  ),
                  // Text('color change'),
                ])
        ));

  }
}

class SelectBakeryCategoryPageAppbar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
        icon: Image.asset('asset/images/Vector.png'),
        onPressed: () => Navigator.of(context).pop(),
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

