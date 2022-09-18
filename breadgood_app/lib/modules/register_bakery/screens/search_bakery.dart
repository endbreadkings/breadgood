import 'dart:core';
import 'dart:convert';
import 'dart:math';
import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:breadgood_app/modules/dashboard/dashboard.dart';
import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/modules/register_bakery/screens/select_bakery_category.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/screens/no_result.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_card.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery_constants.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/utils/common/debounce.dart';

Future<NaverMapData> fetchSearchData(String searchKeyword) async {
  var endpointUrl = 'https://openapi.naver.com/v1/search/local.json';
  Map<String, String> queryParams = {
    'query': '$searchKeyword',
    'display': '10',
    'start': '1',
    'sort': 'random',
  };
  String queryString = Uri(queryParameters: queryParams).query;

  var requestUrl = endpointUrl + '?' + queryString;
  var response = await http.get(
    Uri.parse(requestUrl),
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Charset': 'utf-8',
      'X-Naver-Client-id': 'p24UOax9LBbitflVcBAv',
      'X-Naver-Client-Secret': 'G7hXtfqhP3',
    },
  );

  final responseJson = json.decode(response.body);
  return NaverMapData.fromJson(responseJson);
}

class SearchBakeryPage extends StatefulWidget {
  const SearchBakeryPage({Key key}) : super(key: key);

  @override
  _SearchBakeryPageState createState() => _SearchBakeryPageState();
}

class _SearchBakeryPageState extends State<SearchBakeryPage> {
  bool searched = false;
  Future<NaverMapData> fSearchedBakeries;

  final _debounce = Debounce(Duration(milliseconds: 300));

  _onChanged(String text) {
    _debounce.call(() {
      fSearchedBakeries = fetchSearchData(text);
      setState(() {
        (fSearchedBakeries != null) ? searched = true : searched = false;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: SearchBakeryPageAppbar(),
      body: SingleChildScrollView(
        child: Column(
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
                style: TextStyle(
                  fontSize: 16.0,
                ),
                onChanged: _onChanged,
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
            (searched == false)
                ? getNoResult()
                : Padding(
                    padding: EdgeInsets.fromLTRB(20, 40, 20, 0),
                    child: buildFutureSearchedBakeriesBuilder(context)),
          ],
        ),
      ),

      // )
    );
  }

  FutureBuilder<NaverMapData> buildFutureSearchedBakeriesBuilder(
      BuildContext context) {
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

    List<SearchData> searchedList = [];
    return FutureBuilder<NaverMapData>(
      future: fSearchedBakeries,
      builder: (context, AsyncSnapshot<NaverMapData> snapshot) {
        if (snapshot.hasData) {
          if (snapshot.data.items != null) {
            for (var item in snapshot.data.items) {
              if (((item.category == '음식점>카페,디저트') ||
                      (item.category == '카페,디저트>베이커리') ||
                      (item.category == '음식점>브런치')) &
                  (item.roadAddress.contains('서울특별시'))) {
                searchedList.add(item);
              }
            }
          }
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }

        if (searchedList.isEmpty) {
          return getNoResult();
        } else {
          return Column(
            children: searchedList.map((item) {
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
          );
        }
      },
    );
  }
}

class SearchBakeryPageAppbar extends DefaultAppBar {
  final dController = Get.put(DashboardController());

  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
        icon: Container(
            height: 16,
            width: 8,
            child: SvgPicture.asset(
              'asset/images/Vector.svg',
              fit: BoxFit.scaleDown,
            )),
        onPressed: () {
            dController.changePageIndex(RouteName.Home.index);
            Get.toNamed('/dashboard');
        }
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

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
    var _cardWidth = (MediaQuery.of(context).size.width) - 199;
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
                padding: EdgeInsets.fromLTRB(11, 26, 14, 30),
                child: Container(
                    width: _cardWidth,
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          SizedBox(
                              child: WordBreakText(widget.selectedBakery.title,
                                  style: TextStyle(
                                    fontFamily: 'NanumSquareRoundEB',
                                    fontSize: 14.0,
                                  ))),
                          SizedBox(
                            height: 10,
                          ),
                          SizedBox(
                            child: WordBreakText(
                                widget.selectedBakery.roadAddress,
                                spacing: 6,
                                style: TextStyle(
                                    fontSize: 12.0, color: Color(0xFFA4A4A4))),
                          )
                        ]))),
            Spacer(),
            Padding(
                padding: EdgeInsets.fromLTRB(0, 29, 14, 29),
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
                          child: Flexible(
                              child: Center(
                                  child: Text('선택',
                                      maxLines: 1,
                                      softWrap: false,
                                      overflow: TextOverflow.visible,
                                      style: TextStyle(
                                        fontSize: 14.0,
                                        color: Color(0xFFFFFFFF),
                                      ))))),
                      onPressed: () async {
                        controller.UpdateBakery(widget.selectedBakery);
                        var checkDuplicate = await checkRegisteredBakery(
                            widget.selectedBakery.roadAddress);
                        if (checkDuplicate.idDuplicate) {
                          Get.to(AlreadyRegisteredBakeryPage(), arguments: [
                            {"registerer": checkDuplicate.nickName}, {"bakeryId":checkDuplicate.bakeryId}]);
                        }
                        else {
                          Get.to(SelectBakeryCategoryPage(), arguments: widget.selectedBakery);
                        }
                      }),
                ))
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

Future<CheckDuplicateBakery> checkRegisteredBakery(String roadAddress) async {
  final response = await http.post(
      Uri.parse(
          'https://api.breadgood.com/api/v1/bakery/duplicate/roadAddress/${roadAddress}'),
      headers: await headers_post(),
      body: <String, String>{
        'roadAddress': 'roadAddress',
      });
  if (response.statusCode == 200) {
    print('status 200!');
  } else {
    print('error code: ');
    print(response.statusCode);
  }
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);

  return CheckDuplicateBakery.fromJson(responseJson);
}

class CheckDuplicateBakery {
  final int bakeryId;
  final bool idDuplicate;
  final String nickName;

  CheckDuplicateBakery({
    this.bakeryId,
    this.idDuplicate,
    this.nickName,
  });

  factory CheckDuplicateBakery.fromJson(Map<String, dynamic> json) {
    return CheckDuplicateBakery(
      bakeryId: json['bakeryId'],
      idDuplicate: json['idDuplicate'],
      nickName: json['nickName'],
    );
  }
}
