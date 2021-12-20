import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ShapeBlueButton extends StatefulWidget {
  final String text;
  final bool is_able;
  final successActions;

  const ShapeBlueButton({Key key, this.text, this.is_able, this.successActions})
      : super(key: key);

  @override
  _ShapeBlueButtonState createState() => _ShapeBlueButtonState();
}

class _ShapeBlueButtonState extends State<ShapeBlueButton> {
  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ElevatedButton.styleFrom(
        primary: Color.fromRGBO(69, 121, 255, 1),
        padding: EdgeInsets.symmetric(horizontal: 10),
        textStyle: TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
        minimumSize: Size(314, 56),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(50),
        ),
        // primary: isButtonAbled(), // background
        // onPrimary: Color.fromRGBO(199, 199, 199, 1), // foreground
      ),
      child: Text(widget.text,
          style: TextStyle(fontSize: 16, color: Colors.white)),
      onPressed: isButtonAbled(),
    );
  }

  isButtonAbled() {
    return widget.is_able ? () {
        widget.successActions();
    } : null;
    //199, 199, 199, 1
  }
}
