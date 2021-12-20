import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/utils/ui/circle_check_box.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:get/get.dart';

class PolicyButtons extends GetView<SignUpController>{
  final String text;

  final double circle_size;
  final double icon_size;
  final double font_size;
  final double line_height;

  final Color false_color;
  final Color true_color;

  final bool isClick;
  final int index;
  
  final String link;


  const PolicyButtons(
      {Key key,
        this.text,
        this.circle_size,
        this.icon_size,
        this.font_size,
        this.line_height,
        this.false_color,
        this.true_color,
        this.isClick,
        this.index,
        this.link
        })
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
                circle_size: 2,
                icon_size: 14,
                font_size: 14,
                line_height: 1.4,
                false_color: controller.false_color,
                true_color: controller.true_color,
                isClick: controller.models.value[index].check),
            TextButton(
              onPressed: () {
                print(controller.models.value[index].termsLink);
                Get.toNamed('/signup/policy/details',arguments: controller.models.value[index].termsLink);
              },
              child: Text("전문보기 〉",style: TextStyle(color:Color.fromRGBO(119, 119, 119, 1))),
            )
          ],
        ));
  }
}