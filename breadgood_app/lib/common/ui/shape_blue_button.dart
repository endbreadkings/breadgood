import 'package:flutter/material.dart';

class ShapeBlueButton extends StatefulWidget {
  final String text;
  final bool isEnabled;
  final dynamic successHandler;

  const ShapeBlueButton(
      {Key key, this.text, this.isEnabled, this.successHandler})
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
      ),
      child: Text(widget.text,
          style: TextStyle(fontSize: 16, color: Colors.white)),
      onPressed: isButtonAbled(),
    );
  }

  isButtonAbled() {
    return widget.isEnabled
        ? () {
            widget.successHandler();
          }
        : null;
  }
}
