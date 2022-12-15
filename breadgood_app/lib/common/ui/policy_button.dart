import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/common/ui/circle_check_box.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';

class PolicyButton extends GetView<SignUpController> {
  final String text;

  final double circleSize;
  final double iconSize;
  final double fontSize;
  final double lineHeight;

  final Color falseColor;
  final Color tureColor;

  final bool isClicked;
  final int index;

  final String link;

  const PolicyButton(
      {Key key,
      this.text,
      this.circleSize,
      this.iconSize,
      this.fontSize,
      this.lineHeight,
      this.falseColor,
      this.tureColor,
      this.isClicked,
      this.index,
      this.link})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(SignUpController());
    return Container(
        child: Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        CircleCheckBox(
            text: text,
            circleSize: 2,
            iconSize: 14,
            fontSize: 14,
            lineHeight: 1.4,
            falseColor: controller.falseColor,
            trueColor: controller.trueColor,
            isClicked: controller.models[index].check),
        TextButton(
          onPressed: () {
            Get.toNamed('/signup/policy/details',
                arguments: controller.models[index].termsLink);
          },
          child: Text("전문보기 〉",
              style: TextStyle(color: Color.fromRGBO(119, 119, 119, 1))),
        )
      ],
    ));
  }
}
