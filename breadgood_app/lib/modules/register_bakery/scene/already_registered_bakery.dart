import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/common/ui/already_registered_bakery_app_bar.dart';
import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:breadgood_app/modules/register_review/scene/register_review_scene.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';

class AlreadyRegisteredBakeryScene extends StatefulWidget {
  const AlreadyRegisteredBakeryScene({Key key}) : super(key: key);

  @override
  _AlreadyRegisteredBakerySceneState createState() =>
      _AlreadyRegisteredBakerySceneState();
}

class _AlreadyRegisteredBakerySceneState
    extends State<AlreadyRegisteredBakeryScene> {
  final controller = Get.put(BakeryController());
  final dController = Get.put(DashboardController());

  SearchData selectedBakery;
  final firstRegisterer = (Get.arguments)[0]["registerer"];
  final bakeryId = (Get.arguments)[1]["bakeryId"];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AlreadyRegisteredBakeryAppbar(),
      body: GetBuilder<BakeryController>(builder: (_) {
        selectedBakery = controller.selectedBakery;
        return Container(
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
                          text: '앗! 이미 $firstRegisterer의\n'
                              '최애빵집으로 등록되었어요\n'
                              '대신 다른 빵덕후들과\n'
                              '나만의 후기를 나눠보세요!',
                        ),
                      ], // buildFutureBuilder(),
                    ),
                  ),
                  Spacer(),
                  Align(
                    alignment: Alignment.bottomCenter,
                    child: Column(
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
                              Get.to(RegisterReviewScene(),
                                  arguments: bakeryId);
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
      }),
    );
  }
}
