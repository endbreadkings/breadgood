import 'dart:core';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:word_break_text/word_break_text.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/model/duplicated_bakery.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/modules/register_bakery/screens/select_bakery_category.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_constants.dart';

class BakeryCard extends StatefulWidget {
  final SearchData selectedBakery;
  BakeryCard({Key key, this.selectedBakery}) : super(key: key);
  @override
  _BakeryCardState createState() => _BakeryCardState();
}

class _BakeryCardState extends State<BakeryCard> {
  Future<CheckDuplicateBakery> futureCheckBakeryDuplicate;
  final controller = Get.put(BakeryController());
  CheckDuplicateBakery checkDup;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var _cardWidth = (MediaQuery.of(context).size.width) -
        SearchBakeryConstants.metric.cardWidth;
    return Scaffold(body: GetBuilder<BakeryController>(builder: (_) {
      return Container(
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(16.0),
              color: Colors.white,
              boxShadow: [
                BoxShadow(
                  color: Color(0xFF1C2F85).withOpacity(0.15),
                  offset: Offset(2.0, 2.0),
                  blurRadius: 10.0,
                  spreadRadius: 0,
                )
              ]),
          child: Row(mainAxisAlignment: MainAxisAlignment.start, children: [
            Padding(
                padding: EdgeInsets.fromLTRB(14, 0, 0, 0),
                child: Center(
                    child: Container(
                        height: 44,
                        width: 47,
                        child: SvgPicture.asset(
                          'asset/images/icon/registerBakery/bread_black_and_white.svg',
                          fit: BoxFit.scaleDown,
                        )))),
            Padding(
              padding: EdgeInsets.fromLTRB(11, 0, 14, 0),
              child: Container(
                margin: EdgeInsets.fromLTRB(0, 26, 0, 0),
                width: _cardWidth,
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Flexible(
                        child: WordBreakText(
                          widget.selectedBakery.title,
                          style:
                              SearchBakeryConstants.style.resultTitleTextStyle,
                        ),
                      ),
                      SizedBox(
                        height: 7,
                      ),
                      Flexible(
                        child: RichText(
                          overflow: TextOverflow.ellipsis,
                          maxLines: SearchBakeryConstants.metric.addressMaxLine,
                          strutStyle: StrutStyle(fontSize: 12.0),
                          text: TextSpan(
                            text: widget.selectedBakery.roadAddress,
                            style: SearchBakeryConstants.style.addressTextStyle,
                          ),
                        ),
                      )
                    ]),
              ),
            ),
            Spacer(),
            Container(
                padding: EdgeInsets.only(right: 14),
                alignment: Alignment.center,
                child: SizedBox(
                  width: 54,
                  height: 28,
                  child: RaisedButton(
                      color: Color(0xFF4579FF),
                      elevation: 0,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20)),
                      child: Container(
                          height: 28,
                              child: Center(
                                  child: Text('선택',
                                      maxLines: 1,
                                      softWrap: false,
                                      overflow: TextOverflow.visible,
                                      style: TextStyle(
                                        fontSize: 14.0,
                                        color: Color(0xFFFFFFFF),
                                      )))),
                      onPressed: () async {
                        controller.UpdateBakery(widget.selectedBakery);
                        var checkDuplicate =
                            await controller.checkRegisteredBakery(
                                widget.selectedBakery.roadAddress);
                        if (checkDuplicate.idDuplicate) {
                          Get.to(AlreadyRegisteredBakeryPage(),
                              arguments: [{"registerer": checkDuplicate.nickName},
                                          {"bakeryId":checkDuplicate.bakeryId}]);
                        }
                        else {
                          Get.to(SelectBakeryCategoryPage(),
                              arguments: widget.selectedBakery);
                        }}),
                )),
          ]));
    }));
  }

  FutureBuilder<CheckDuplicateBakery> buildFutureBuilder() {
    return FutureBuilder<CheckDuplicateBakery>(
      future: futureCheckBakeryDuplicate,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          controller.UpdateDuplicateCheck(snapshot.data);
          return Container(width: 0, height: 0);
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        return CircularProgressIndicator();
      },
    );
  }
}
