import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CircleCheckBox extends StatefulWidget {
  final String text;
  final double circle_size;
  final double icon_size;
  final double font_size;
  final double line_height;

  final Color false_color;
  final Color true_color;

  final bool isClick;
  final int index;

  const CircleCheckBox(
      {Key key,
      this.text,
      this.circle_size,
      this.icon_size,
      this.font_size,
      this.line_height,
      this.false_color,
      this.true_color,
      this.isClick,
      this.index
      })
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
                  color: widget.isClick
                      ? controller.true_color
                      : controller.false_color),
              child: Padding(
                  padding: EdgeInsets.all(widget.circle_size),
                  child: Icon(
                    Icons.check,
                    size: widget.icon_size,
                    color: Colors.white,
                  ))),
          Text(widget.text,
              style: TextStyle(
                  fontSize: widget.font_size, height: widget.line_height))
        ],
      )),
    );
  }
}
