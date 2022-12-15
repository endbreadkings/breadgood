import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/common/ui/shape_blue_button.dart';
import 'package:breadgood_app/common/ui/circle_check_box.dart';
import 'package:breadgood_app/common/ui/default_app_bar.dart';
import 'package:breadgood_app/common/ui/policy_button.dart';

import 'package:breadgood_app/common/theme/light_theme.dart' as THEME;

class PolicyAppBar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
        icon: Container(
            height: 16,
            width: 8,
            child: SvgPicture.asset(
              'asset/images/Vector.svg',
              fit: BoxFit.scaleDown,
            )),
        onPressed: () => Navigator.pushReplacementNamed(context, '/login'),
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

class AgreePolicyScene extends GetView<SignUpController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: PolicyAppBar(),
        body: SingleChildScrollView(
          child: Container(
            margin: EdgeInsets.only(top: 42, left: 30, right: 30),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Column(children: [
                  Container(
                    margin: EdgeInsets.only(bottom: 16),
                    child: //전체 체크 박스
                        Column(
                      children: [
                        Container(
                            child: Row(
                          children: [
                            Text(
                              '이용약관 동의',
                              style: TextStyle(
                                  fontSize: 26,
                                  fontWeight: FontWeight.w800,
                                  fontFamily: 'NanumSquareRoundEB'),
                            )
                          ],
                        )),
                        Container(
                            margin: EdgeInsets.only(top: 14, bottom: 36),
                            child: Row(
                              children: [
                                Text('빵긋을 이용하기 위해 약관에 동의해주세요',
                                    style: TextStyle(fontSize: 16, height: 0.8))
                              ],
                            )),
                      ],
                    ),
                  ),
                  //세부 체크박스
                  Column(
                    children: [
                      Container(
                          margin: EdgeInsets.only(bottom: 36),
                          child: Column(
                            children: [
                              Obx(
                                () => ListView.builder(
                                  physics: const NeverScrollableScrollPhysics(),
                                  shrinkWrap: true,
                                  itemCount: controller.models.value.length,
                                  itemBuilder:
                                      (BuildContext context, int index) {
                                    return GestureDetector(
                                      child: PolicyButton(
                                          text:
                                              "${controller.models.value[index].name}",
                                          circleSize: 2,
                                          iconSize: 14,
                                          fontSize: 14,
                                          lineHeight: 1.4,
                                          falseColor: controller.falseColor,
                                          tureColor: controller.trueColor,
                                          isClicked: controller
                                              .models.value[index].check,
                                          index: index),
                                      onTap: () {
                                        controller.clickCheck(index);
                                        controller
                                            .isAllCheckAtclickCheck(index);
                                      },
                                    );
                                  },
                                ),
                              )
                            ],
                          )),
                      Obx(() {
                        return GestureDetector(
                            child: Container(
                                padding: EdgeInsets.only(
                                    top: 15, bottom: 15, left: 62),
                                decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(50),
                                    border: Border.all(
                                        color: checkingBorder(
                                            controller.isAllChecked.value,
                                            controller.trueColor,
                                            Colors.white)),
                                    color: THEME.GRAY_244),
                                child: CircleCheckBox(
                                    text: '위 약관에 모두 동의합니다.',
                                    circleSize: 2,
                                    iconSize: 14,
                                    fontSize: 14,
                                    lineHeight: 1.4,
                                    falseColor: controller.falseColor,
                                    trueColor: controller.trueColor,
                                    isClicked: controller.isAllChecked.value)),
                            onTap: () {
                              controller.clickAllCheck();
                              // isAllChecking();
                            });
                      }),
                    ],
                  ),
                ]),
                Container(
                    alignment: Alignment(0.0, 1.0),
                    margin: EdgeInsets.only(top: 180, bottom: 66),
                    child: Obx(() {
                      return ShapeBlueButton(
                          text: '다음 단계로 이동',
                          isEnabled: controller.isAllChecked.value,
                          successHandler: () {
                            Get.toNamed('/signup/nickname');
                          });
                    }))
              ],
            ),
          ),
        ));
  }

  Color checkingBorder(isChecked, trueColor, falseColor) {
    return isChecked ? controller.trueColor : controller.falseColor;
  }
}
