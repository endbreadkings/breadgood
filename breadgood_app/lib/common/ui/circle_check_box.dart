import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CircleCheckBox extends StatefulWidget {
  final String text;
  final double circleSize;
  final double iconSize;
  final double fontSize;
  final double lineHeight;

  final Color falseColor;
  final Color trueColor;

  final bool isClicked;
  final int index;

  const CircleCheckBox(
      {Key key,
      this.text,
      this.circleSize,
      this.iconSize,
      this.fontSize,
      this.lineHeight,
      this.falseColor,
      this.trueColor,
      this.isClicked,
      this.index})
      : super(key: key);

  @override
  _CircleCheckBoxState createState() => _CircleCheckBoxState();
}

class _CircleCheckBoxState extends State<CircleCheckBox> {
  final controller = Get.put(SignUpController());

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Container(
          child: Row(
        children: [
          Container(
              margin: EdgeInsets.only(right: 12),
              decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  color: widget.isClicked
                      ? controller.trueColor
                      : controller.falseColor),
              child: Padding(
                  padding: EdgeInsets.all(widget.circleSize),
                  child: Icon(
                    Icons.check,
                    size: widget.iconSize,
                    color: Colors.white,
                  ))),
          Text(widget.text,
              style: TextStyle(
                  fontSize: widget.fontSize, height: widget.lineHeight))
        ],
      )),
    );
  }
}
