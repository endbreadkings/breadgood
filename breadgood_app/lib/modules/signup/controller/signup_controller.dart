import 'package:breadgood_app/modules/signup/model/signup_models.dart';
import 'package:breadgood_app/modules/signup/service/bread_style_service.dart';
import 'package:breadgood_app/modules/signup/service/nick_name_service.dart';
import 'package:breadgood_app/modules/signup/service/sign_up_service.dart';
import 'package:flutter/material.dart';

import 'package:breadgood_app/modules/signup/service/policy_service.dart';
import 'package:get/get.dart';

import 'package:breadgood_app/config/themes/light_theme.dart' as THEME;

class SignUpController extends GetxController {
  SignupModels user;

  Color false_color = THEME.GRAY_199;
  Color true_color = THEME.MAIN;

  //이용약관 내용
  var models = List<dynamic>().obs;
  RxBool all_check = false.obs;

  //닉네임 중복 여부
  var is_duplicate_name = false.obs;
  var error = ''.obs;
  var check_name = false.obs;
  var nick_name = '';

  //최애빵 리스트
  var bread_style = List<dynamic>().obs;
  var check_bread_style = false.obs;

  @override
  void onInit() {
    super.onInit();
    policyList();
    getBreadStyle();
    user = SignupModels();
  }

  void policyList() async {
    var products = await PolicyService.fetchPolicy();
    if (products != null) {
      models.value = products;
    }
  }

  void clickCheck(index) {
    models.value[index].check = !models.value[index].check;
    models.refresh();

    int id = models.value[index].typeId;

    if (user.setTermsTypeIds.contains(id))
      user.setTermsTypeIds.remove(id);
    else
      user.setTermsTypeIds.add(id);
  }

  void isAllCheckAtclickCheck(index) {
    if (!models.value[index].check) {
      all_check.value = false;
      all_check.refresh();
    } else
      isAllcheck();
  }

  void clickAllCheck() {
    all_check.value = !all_check.value;
    models.value.forEach((element) {
      element.check = all_check.value;
    });

    if (all_check.value) {
      user.setTermsTypeIds.addAll(models.value.map((e) => e.typeId));
    } else {
      models.value.forEach((element) {
        user.setTermsTypeIds.removeAll(models.value.map((e) => e.typeId));
      });
    }

    all_check.refresh();
    models.refresh();
  }

  void isAllcheck() {
    List list = [];
    models.value.forEach((element) => list.add(element.check));
    if (!list.contains(false)) all_check.value = true;
    all_check.refresh();
  }

  bool validationNickName(String nickName) {
    if (nickName.length >= 8) {
      error.value = '최대 7글자를 넘어갈 수 없습니다.';
      error.refresh();
      return false;
    } else {
      error.value = '';
      error.refresh();
      return true;
    }
  }

  void duplicatedNickName(nickName) async {
    is_duplicate_name.value = false;
    check_name.value = false;
    if (nickName == '')
      return;
    else {
      var products = await NickNameService.duplicatedNickName(nickName);
      if (products != null) {
        if (products) {
          error.value = '이미 사용중인 별명이예요';
          is_duplicate_name.value = true;
          check_name.value = false;
        } else {
          error.value = '';
          is_duplicate_name.value = false;
          check_name.value = true;
          nick_name = nickName;
        }
      }
    }
    check_name.refresh();
    is_duplicate_name.refresh();
    error.refresh();
  }

  void setNickName() {
    user.nickName = nick_name;
  }

  void getBreadStyle() async {
    var products = await BreadStyleService.getBreadStyle();
    if (products != null) {
      bread_style.value = products;
    }
  }

  void setBreadStyle(index) {
    bread_style.value.forEach((e) {
      e.check = false;
    });
    bread_style.value[index].check = true;
    check_bread_style.value = true;

    user.breadStyleId = bread_style.value[index].id;

    check_bread_style.refresh();
    bread_style.refresh();
  }

  void signUp() async {
    if (await SignUpService.signUp(user)) Get.offAllNamed('/dashboard');
  }
}
