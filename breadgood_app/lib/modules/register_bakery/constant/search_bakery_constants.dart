import 'dart:core';
import 'package:flutter/material.dart';

class SearchBakeryConstants {
  static final metric = SearchBakeryMetric();
  static final style = SearchBakeryStyle();
}

class SearchBakeryMetric {
  final cardWidth = 199;
  final titleMaxLine = 2;
  final addressMaxLine = 2;
}

class SearchBakeryStyle {
  final resultTitleTextStyle = TextStyle(
    fontFamily: 'NanumSquareRoundEB',
    fontSize: 14.0,
  );
  final addressTextStyle = TextStyle(
    fontSize: 12.0,
    color: Color(0xFFA4A4A4),
  );
}
