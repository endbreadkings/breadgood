import 'dart:math';

import 'package:breadgood_app/modules/register_bakery/screens/no_result.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_card.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_constants.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/register_bakery_renewal_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class RegisterBakeryRenewal extends StatefulWidget {
  @override
  State<RegisterBakeryRenewal> createState() => _RegisterBakeryRenewalState();
}

class _RegisterBakeryRenewalState extends State<RegisterBakeryRenewal> {
  RegisterBakeryRenewalController controller;

  @override
  void initState() {
    controller = RegisterBakeryRenewalController(context, () => setState(() {}));
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Column(
            children: [
              _appBar(),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: EdgeInsets.fromLTRB(30.0, 26.0, 26.0, 0),
                    child: Text("최애 빵집 등록하기",
                        style: TextStyle(
                          fontSize: 26.0,
                          fontFamily: 'NanumSquareRoundEB',
                        )),
                  ),
                  Padding(
                    padding: EdgeInsets.fromLTRB(30, 14.0, 26, 50.0),
                    child: Text("진정한 빵덕후라면 남들이 발견못한\n최애빵집 하나 정도는 있겠죠?",
                        style: TextStyle(
                          fontSize: 16.0,
                        )),
                  ),
                  Padding(
                    padding: EdgeInsets.fromLTRB(30.0, 0.0, 26.0, 0),
                    child: RichText(
                      text: TextSpan(
                        style: TextStyle(
                          color: Colors.black,
                          fontSize: 16.0,
                          fontFamily: 'NanumSquareRoundB',
                        ),
                        children: <TextSpan>[
                          TextSpan(
                              text: '빵집명',
                              style: TextStyle(
                                color: Color(0xFF4579FF),
                              )),
                          TextSpan(
                            text: '을 검색해주세요!',
                          ),
                          TextSpan(
                            text: ' (지금은 서울만 가능해요)',
                            style: TextStyle(
                              fontSize: 12.0,
                              fontFamily: 'NanumSquareRoundR',
                            ),
                          ),
                        ], // buildFutureBuilder(),
                      ),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.fromLTRB(30, 37.0, 30.0, 0),
                    child: TextFormField(
                      controller: controller.searchController,
                      style: TextStyle(
                        fontSize: 16.0,
                      ),
                      onChanged: (value) {
                        controller.onSearch();
                      },
                      decoration: InputDecoration(
                        hintText: 'ex)빵긋의제왕',
                        hintStyle: TextStyle(
                          color: Color(0xFF909090),
                          fontSize: 16,
                        ),
                        border: UnderlineInputBorder(
                          borderSide: BorderSide(color: Color(0xFFC7C7C7), width: 1),
                        ),
                        focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: Color(0xFF4579FF), width: 1),
                        ),
                        isDense: true,
                        contentPadding: EdgeInsets.only(bottom: 7),
                      ),
                    ),
                  ),
                  _searchResult(),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _appBar() {
    return Row(
      children: [
        IconButton(
            icon: Container(
                height: 16,
                width: 8,
                child: SvgPicture.asset(
                  'asset/images/Vector.svg',
                  fit: BoxFit.scaleDown,
                )),
            onPressed: () => Navigator.pop(context)
        ),
        Spacer(),
      ],
    );
  }

  Widget _searchResult() {
    if (controller.searchedList.isEmpty) return getNoResult();

    return Padding(
      padding: EdgeInsets.fromLTRB(20, 40, 20, 0),
      child: Column(
        children: controller.searchedList.map((item) {
          final height = _calculateHeight(
              titleHeight: _calculateTitleHeight(context, item.title),
              addressHeight: min(
                  _calculateAddressHeight(context, item.roadAddress), 30));
          return Container(
              width: double.infinity,
              height: height,
              padding: EdgeInsets.only(bottom: 16),
              child: BakeryCard(selectedBakery: item));
        }).toList(),
      ),
    );
  }

  double _calculateTitleHeight(BuildContext context, String text) {
    final textSpan = TextSpan(
        text: text, style: SearchBakeryConstants.style.resultTitleTextStyle);
    final textPainter = TextPainter(
        text: textSpan,
        maxLines: SearchBakeryConstants.metric.titleMaxLine,
        textDirection: TextDirection.ltr);
    textPainter.layout(
        maxWidth: (MediaQuery.of(context).size.width) -
            SearchBakeryConstants.metric.cardWidth);
    final height = (textPainter.computeLineMetrics().length) * 22.0;
    return height;
  }

  double _calculateAddressHeight(BuildContext context, String text) {
    final textSpan = TextSpan(
        text: text, style: SearchBakeryConstants.style.addressTextStyle);
    final textPainter = TextPainter(
        text: textSpan,
        maxLines: SearchBakeryConstants.metric.addressMaxLine,
        textDirection: TextDirection.ltr);
    textPainter.layout(
        maxWidth: (MediaQuery.of(context).size.width) -
            SearchBakeryConstants.metric.cardWidth);
    final height = (textPainter.computeLineMetrics().length) * 15.0;
    return height;
  }

  double _calculateHeight({double titleHeight, double addressHeight}) {
    return titleHeight + addressHeight + 63;
  }
}
