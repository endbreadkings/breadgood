import 'dart:async';

import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/utils/ui/buttons/shape_blue_button.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';

import 'package:breadgood_app/config/themes/light_theme.dart' as THEME;
import 'package:flutter/services.dart';
import 'package:get/get.dart';

class NickName extends GetView<SignUpController> {
  @override
  Widget build(BuildContext context) {
    final controller = Get.put(SignUpController());
    Timer _debounce;
    final _formKey = new GlobalKey<FormState>();
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
                        child: Row(
                      children: [
                        Expanded(
                            child: Text(
                          '빵긋에서 사용할',
                          style: TextStyle(
                              fontSize: 26,
                              fontWeight: FontWeight.w800,
                              fontFamily: 'NanumSquareRoundEB'),
                        )),
                      ],
                    )),
                    Container(
                        margin: EdgeInsets.only(top: 5),
                        child: Row(
                          children: [
                            Expanded(
                                child: Row(
                              children: [
                                Text('별명',
                                    style: TextStyle(
                                        color: THEME.MAIN,
                                        fontSize: 26,
                                        fontWeight: FontWeight.w800,
                                        fontFamily: 'NanumSquareRoundEB')),
                                Text(
                                  '을 입력해주세요!',
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
                      child: Text('최대 7글자, 숫자/특수문자/띄어쓰기 불가',
                          style: TextStyle(
                            fontSize: 12.0,
                            color: Colors.grey,
                          )),
                    ),
                    Padding(
                        padding: EdgeInsets.only(
                          top: 0,
                          left: 0,
                          right: 0,
                        ),
                        child: Obx(() {
                          return TextFormField(
                            onChanged: (text) {
                              if (controller.validationNickName(text)) {
                                if (_debounce?.isActive ?? false)
                                  _debounce.cancel();
                                _debounce = Timer(
                                    const Duration(milliseconds: 250), () {
                                  controller.duplicatedNickName(text);
                                });
                              }
                            },
                            autofocus: true,
                            style: TextStyle(fontSize: 16.0),
                            cursorColor: THEME.GRAY_177,
                            decoration: InputDecoration(
                              errorText: controller.error.value != ''
                                  ? controller.error.value
                                  : null,
                              hintText: "ex) 빵긋원정대",
                              hintStyle: TextStyle(
                                  fontSize: 16.0, color: THEME.GRAY_177),
                              border: new UnderlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: THEME.GRAY_177, width: 1.0)),
                              contentPadding:
                                  EdgeInsets.only(top: 10.0, bottom: 0.0),
                              focusedBorder: UnderlineInputBorder(
                                borderSide: const BorderSide(
                                    color: THEME.GRAY_177, width: 1.0),
                              ),
                            ),
                            inputFormatters: [
                              FilteringTextInputFormatter.deny(
                                  RegExp(r'^[_\-=@,\.;^]+$')),
                              FilteringTextInputFormatter.allow(
                                  RegExp('[A-z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]')),
                            ],
                          );
                        })),
                    Obx(() {
                      return Padding(
                          padding: EdgeInsets.only(
                            top: 5.0,
                          ),
                          child: (controller.check_name.value &&
                                  !controller.is_duplicate_name.value &&
                                  controller.error.value == '')
                              ? Text('사용 가능한 별명이예요!',
                                  style: TextStyle(
                                    color: THEME.MAIN,
                                    fontSize: 12.0,
                                  ))
                              : null);
                    }),
                    Container(
                        alignment: Alignment(0.0, 1.0),
                        margin: EdgeInsets.only(top: 88, bottom: 66),
                        child: Obx(() {
                          return ShapeBlueButton(
                            text: '다음 단계로 이동',
                            is_able: controller.check_name.value &&
                                !controller.is_duplicate_name.value &&
                                controller.error.value == '',
                            successActions: () {
                              controller.setNickName();
                              Get.toNamed('/signup/breadstyle');
                            },
                          );
                        }))
                  ]))),
    );
  }
}
