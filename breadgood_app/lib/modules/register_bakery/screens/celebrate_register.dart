import 'package:breadgood_app/modules/main/screens/main_map.dart';
import 'package:breadgood_app/modules/register_review/register_review.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';

String nickname = '당신';
class CelebrateRegisterPage extends StatefulWidget {
  const CelebrateRegisterPage({Key key}) : super(key: key);

  @override
  _CelebrateRegisterPageState createState() => _CelebrateRegisterPageState();
}

class _CelebrateRegisterPageState extends State<CelebrateRegisterPage> {
  final controller = Get.put(BakeryController());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: DefaultAppBar(),
        body: GetBuilder<BakeryController>(
        builder: (_) {
        return Container(
          margin: EdgeInsets.all(12.0),

          child: Padding(
    padding: EdgeInsets.fromLTRB(31, 26, 30, 66),
    child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                "당신은 진정한 빵덕후",
                style: TextStyle(
                  fontSize: 26,
                    fontWeight: FontWeight.w800,
                fontFamily: 'NanumSquareExtraBold'),
              ),
              SizedBox(
                height:12,
              ),
              Text(
                // "다른 빵덕후들에게\n${controller.}의 최애빵집을\n널리 알릴 수 있게 되었어요",
                  "다른 빵덕후들에게\n${nickname}의 최애빵집을\n널리 알릴 수 있게 되었어요",
                  style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w400,
                    height: 1.3,
                )
              ),
              SizedBox(
                height: 69,
              ),
              Align(
                  alignment: Alignment.center,
                  child: Image.asset('asset/images/celebrate.png'),
              ),
              Align(
                alignment: Alignment.center,
                child: Image.asset('asset/images/cat_color.png'),
              ),
              Spacer(),
              SizedBox(
                width: double.infinity,
                height: 56,
                child: RaisedButton(
                  child: Text("메인으로 돌아가기",
                      style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.w600,
                        fontSize: 16,
                      )),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  ),
                  color: Color(0xFF4579FF),
                  onPressed:(){
                    print('onPressed');
                    Get.offAllNamed('/main');
                    // Get.to(BaseMapPage());
                  }
                ),
              ),
            ],
          )),
        );}));
  }
}
