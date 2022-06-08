import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:breadgood_app/modules/register_review/screens/register_review.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_category.dart';
import 'package:flutter_svg/flutter_svg.dart';

class SelectBakeryCategoryPage extends StatefulWidget {
  const SelectBakeryCategoryPage({Key key}) : super(key: key);

  @override
  _SelectBakeryCategoryPageState createState() =>
      _SelectBakeryCategoryPageState();
}

class _SelectBakeryCategoryPageState extends State<SelectBakeryCategoryPage> {
  SearchData selectedBakery = Get.arguments;
  final controller = Get.put(BakeryController());
  Future<List<BakeryCategory>> bakeryCategoryList;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: SelectBakeryCategoryPageAppbar(),
        body: Container(
            width: double.infinity,
            child: Padding(
              padding: EdgeInsets.fromLTRB(31, 26, 30, 52),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  RichText(
                    text: TextSpan(
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 18.0,
                        fontWeight: FontWeight.w400,
                      ),
                      children: <TextSpan>[
                        TextSpan(
                            text: '${selectedBakery.title}',
                            style: TextStyle(
                              fontFamily: 'NanumSquareRoundEB',
                              fontSize: 26.0,
                            )
                        ),
                        TextSpan(
                          text: '의',
                        ),
                      ],
                    ),
                  ),
                  SizedBox(
                    height: 8,
                    width: 314,
                  ),
                  Text('빵집 카테고리를 설정해주세요.',
                      style: TextStyle(
                        fontSize: 18.0,
                      )),
                  GetBuilder<BakeryController>(
                    builder: (_) {
                      return buildFutureBakeryCategoryListBuilder();
                    }
                  ),
                  Spacer(),
                  Align(
                    alignment: Alignment.bottomCenter,
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        minimumSize: Size(double.infinity, 56),
                        primary: Color(0xFF4579FF),
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30)),
                        elevation: 0,
                      ),
                      child: Text("다음",
                          style: TextStyle(
                            color: Color(0xFFFFFFFF),
                            fontSize: 16,
                            fontWeight: FontWeight.w600,
                          )),
                      onPressed: () {
                        Get.to(RegisterReviewPage(), arguments: -1);
                      },
                    ),
                  ),
                ],
              ),
            )
        )
    );
  }

  FutureBuilder<List<BakeryCategory>> buildFutureBakeryCategoryListBuilder() {
    return FutureBuilder<List<BakeryCategory>>(
      future: bakeryCategoryList = fetchBakeryCategoryList(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          snapshot.data.sort((a, b) => a.sortNumber.compareTo(b.sortNumber));
          return Padding(
              padding: EdgeInsets.fromLTRB(0, 40, 0, 0),
              child: Column(
                  children: [
                    _createBakeryCategoryToggle(snapshot.data[0]),
                    SizedBox(height: 8),
                    _createBakeryCategoryToggle(snapshot.data[1])]
              )
          );
        } else if (snapshot.hasError) {
          /* TO BE DEFINED */
        }
        return Center(
            child: Padding(
              padding: EdgeInsets.only(top:88),
                child: CircularProgressIndicator(),
            )
        );
      },
    );
  }
  Widget _createBakeryCategoryToggle(BakeryCategory category) {
    return ButtonTheme(
        minWidth: 314,
        height: 88,
        child: Container(
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
                ]
            ),
            child: ElevatedButton(
              onPressed: () {
                controller.toggleButton(category.id - 1);
                },
              style: ElevatedButton.styleFrom(
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(16),
                    side: BorderSide(
                        width: 1.0,
                        color: controller.bakery_category_border_color[category.id - 1]
                    )
                ),
                elevation: 0,
                primary: Colors.white,
                padding: EdgeInsets.fromLTRB(22, 18, 25, 22),
              ),
              child: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: EdgeInsets.fromLTRB(0, 2, 16, 2),
                      child: Container(
                          width: 36,
                          height: 44,
                          child:
                          category.markerImgUrl == null
                          ? SvgPicture.asset(
                              'asset/images/icon/registerReview/x.svg')
                          : Image.network(
                            category.markerImgUrl,
                            fit: BoxFit.scaleDown,
                          )
                      ),
                    ),
                    Text(
                      category.content,
                      style: TextStyle(
                        fontSize: 16,
                        color: Colors.black,
                        height: 1.45,
                      ),
                    )
                  ]
              )
            )
        )
    );
  }
}

class SelectBakeryCategoryPageAppbar extends DefaultAppBar {
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
        onPressed: () => Navigator.of(context).pop(),
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

