import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/utils/ui/breadStyleGrid.dart';
import 'package:breadgood_app/utils/ui/buttons/shape_blue_button.dart';
import 'package:breadgood_app/utils/ui/circle_check_box.dart';
import 'package:flutter/material.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/config/themes/light_theme.dart' as THEME;

class BreadStyle extends GetView<SignUpController> {
  const BreadStyle({Key key}) : super(key: key);

  Widget _createBody() {
    return GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        mainAxisSpacing: 4.0,
        crossAxisSpacing: 4.0,
        childAspectRatio: 1.5,
      ),
      primary: false,
      itemCount: controller.bread_style.value.length,
      scrollDirection: Axis.vertical,
      shrinkWrap: true,
      itemBuilder: (BuildContext context, int index) {
        return getGridTile(index);
      },
      padding: const EdgeInsets.all(0),
    );
  }

  GridTile getGridTile(int index) {
    return GridTile(
        child: GestureDetector(
      child: Container(
          decoration: new BoxDecoration(
            borderRadius: new BorderRadius.circular(16.0),
            color: controller.bread_style.value[index].check
                ? null
                : THEME.GRAY_244,
            image: (controller.bread_style.value[index].check)
                ? DecorationImage(
                    // image:SvgPicture.asset(
                    //     controller.bread_style.value[index].profileImgUrl,
                    //     semanticsLabel: 'Acme Logo'
                    // ),
                    // image:Image.asset("asset/images/icon/map/focus_bread.png"),
                    image: NetworkImage(
                        controller.bread_style.value[index].imgUrl),
                    fit: BoxFit.cover,
                  )
                : null,
          ),
          padding: EdgeInsets.all(10),
          // width:155,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Expanded(
                  child: Align(
                      alignment: Alignment.topLeft,
                      child: CircleCheckBox(
                          text: "${controller.bread_style.value[index].name}",
                          circle_size: 2,
                          icon_size: 14,
                          font_size: 16,
                          line_height: 1.4,
                          false_color: controller.false_color,
                          true_color: THEME.MAIN,
                          isClick: controller.bread_style.value[index].check))),
              Expanded(
                  child: Align(
                alignment: Alignment.topLeft,
                child: Text(
                  "${controller.bread_style.value[index].content}",
                  textAlign: TextAlign.left,
                  style: TextStyle(fontSize: 12),
                ),
              ))
            ],
          )),
      onTap: () {
        controller.setBreadStyle(index);
      },
    ));
  }

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(SignUpController());
    return Scaffold(
      appBar: AlreadyRegisteredBakeryAppbar(),
      body: SingleChildScrollView(
          child: Container(
              margin: EdgeInsets.only(top: 42, left: 30, right: 30),
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: <Widget>[
                    Container(
                        margin: EdgeInsets.only(top: 5),
                        child: Row(
                          children: [
                            Expanded(
                                child: Row(
                              children: [
                                Text('최애빵',
                                    style: TextStyle(
                                        color: THEME.MAIN,
                                        fontSize: 26,
                                        fontWeight: FontWeight.w800,
                                        fontFamily: 'NanumSquareRoundEB')),
                                Text(
                                  ' 스타일을 알려주세요!',
                                  style: TextStyle(
                                      fontSize: 26,
                                      fontWeight: FontWeight.w800,
                                      fontFamily: 'NanumSquareRoundEB'),
                                )
                              ],
                            )),
                          ],
                        )),
                    Container(
                        margin: EdgeInsets.only(top: 14, bottom: 48),
                        child: Row(
                          children: [
                            Text('언제든 수정 가능하니 고민하지 마세요!',
                                style: TextStyle(fontSize: 16, height: 0.8))
                          ],
                        )),
                    Padding(
                      padding: EdgeInsets.only(left: 0),
                      child: Text('중복 선택 불가',
                          style: TextStyle(
                            fontSize: 12.0,
                            color: Colors.grey,
                          )),
                    ),
                    // BreadStylePage(0),
                    Obx(() {
                      return _createBody();
                    }),
                    Container(
                        alignment: Alignment(0.0, 1.0),
                        margin: EdgeInsets.only(top: 88, bottom: 66),
                        child: Obx(() {
                          return ShapeBlueButton(
                            text: '빵긋 시작하기!',
                            is_able: controller.check_bread_style.value,
                            successActions: () {
                              controller.signUp();
                            },
                          );
                        }))
                  ]))),
    );
  }

  Color checkingBorder(is_check, true_color, false_color) {
    return is_check ? controller.true_color : controller.false_color;
  }
}
