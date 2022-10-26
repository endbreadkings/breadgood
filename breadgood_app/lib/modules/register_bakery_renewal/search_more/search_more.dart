import 'dart:math';

import 'package:breadgood_app/modules/register_bakery/screens/no_result.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_card.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_constants.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/search_more/search_more_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class SearchMore extends StatefulWidget {
  SearchMore({this.searchWord});

  String searchWord;

  @override
  State<SearchMore> createState() => _SearchMoreState();
}

class _SearchMoreState extends State<SearchMore> {
  SearchMoreController controller;

  @override
  void initState() {
    controller = SearchMoreController(context, () => setState(() {}), widget.searchWord);
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
              _searchResult(),
            ],
          ),
        ),
      )
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
    if (controller.searchList.isEmpty) return getNoResult();

    return Padding(
      padding: EdgeInsets.fromLTRB(20, 40, 20, 0),
      child: Column(
        children: [
          ...controller.searchList.map((item) {
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
          TextButton(
            onPressed: () {
              controller.onSeeMoreButtonClicked();
            },
            child: Text('빵집 더 보기'),
          )
        ]
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
