import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/common/ui/shape_blue_button.dart';
import 'package:breadgood_app/common/ui/circle_check_box.dart';
import 'package:breadgood_app/common/ui/already_registered_bakery_app_bar.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/common/theme/light_theme.dart' as THEME;

class BreadStyleScene extends GetView<SignUpController> {
  const BreadStyleScene({Key key}) : super(key: key);

  Widget _createBody() {
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        mainAxisSpacing: 4.0,
        crossAxisSpacing: 4.0,
        childAspectRatio: 1.5,
      ),
      primary: false,
      itemCount: controller.breadStyle.value.length,
      scrollDirection: Axis.vertical,
      shrinkWrap: true,
      itemBuilder: (BuildContext context, int index) {
        return getGridTile(index);
      },
      padding: const EdgeInsets.all(0),
    );
  }

  GridTile getGridTile(int index) {
    return GridTile(
        child: GestureDetector(
      child: Container(
          decoration: new BoxDecoration(
            borderRadius: new BorderRadius.circular(16.0),
            color: controller.breadStyle.value[index].check
                ? null
                : THEME.GRAY_244,
            image: (controller.breadStyle.value[index].check)
                ? DecorationImage(
                    image:
                        NetworkImage(controller.breadStyle.value[index].imgUrl),
                    fit: BoxFit.cover,
                  )
                : null,
          ),
          padding: EdgeInsets.all(10),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Expanded(
                  child: Align(
                      alignment: Alignment.topLeft,
                      child: CircleCheckBox(
                          text: "${controller.breadStyle.value[index].name}",
                          circleSize: 2,
                          iconSize: 14,
                          fontSize: 16,
                          lineHeight: 1.4,
                          falseColor: controller.falseColor,
                          trueColor: THEME.MAIN,
                          isClicked:
                              controller.breadStyle.value[index].check))),
              Expanded(
                  child: Align(
                alignment: Alignment.topLeft,
                child: Text(
                  "${controller.breadStyle.value[index].content}",
                  textAlign: TextAlign.left,
                  style: TextStyle(fontSize: 12),
                ),
              ))
            ],
          )),
      onTap: () {
        controller.setBreadStyle(index);
      },
    ));
  }

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(SignUpController());
    return Scaffold(
      appBar: AlreadyRegisteredBakeryAppbar(),
      body: SingleChildScrollView(
          child: Container(
              margin: EdgeInsets.only(top: 42, left: 30, right: 30),
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: <Widget>[
                    Container(
                        margin: EdgeInsets.only(top: 5),
                        child: Row(
                          children: [
                            Expanded(
                                child: Row(
                              children: [
                                Text('최애빵',
                                    style: TextStyle(
                                        color: THEME.MAIN,
                                        fontSize: 26,
                                        fontWeight: FontWeight.w800,
                                        fontFamily: 'NanumSquareRoundEB')),
                                Text(
                                  ' 스타일을 알려주세요!',
                                  style: TextStyle(
                                      fontSize: 26,
                                      fontWeight: FontWeight.w800,
                                      fontFamily: 'NanumSquareRoundEB'),
                                )
                              ],
                            )),
                          ],
                        )),
                    Container(
                        margin: EdgeInsets.only(top: 14, bottom: 48),
                        child: Row(
                          children: [
                            Text('언제든 수정 가능하니 고민하지 마세요!',
                                style: TextStyle(fontSize: 16, height: 0.8))
                          ],
                        )),
                    Padding(
                      padding: EdgeInsets.only(bottom: 18),
                      child: Text('중복 선택 불가',
                          style: TextStyle(
                            fontSize: 12.0,
                            color: Colors.grey,
                          )),
                    ),
                    // BreadStylePage(0),
                    Obx(() {
                      return _createBody();
                    }),
                    Container(
                        alignment: Alignment(0.0, 1.0),
                        margin: EdgeInsets.only(top: 88, bottom: 66),
                        child: Obx(() {
                          return ShapeBlueButton(
                            text: '빵긋 시작하기!',
                            isEnabled: controller.isCheckedBreadStyle.value,
                            successHandler: () {
                              controller.signUp();
                            },
                          );
                        }))
                  ]))),
    );
  }

  Color checkingBorder(isChecked, trueColor, falseColor) {
    return isChecked ? controller.trueColor : controller.falseColor;
  }
}
