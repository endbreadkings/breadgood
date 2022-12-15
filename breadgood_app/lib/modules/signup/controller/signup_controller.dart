import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/signup/model/signup_models.dart';
import 'package:breadgood_app/modules/signup/service/bread_style_service.dart';
import 'package:breadgood_app/modules/signup/service/nick_name_service.dart';
import 'package:breadgood_app/modules/signup/service/sign_up_service.dart';
import 'package:breadgood_app/storages/user_storage.dart';
import 'package:breadgood_app/modules/signup/service/policy_service.dart';
import 'package:breadgood_app/common/theme/light_theme.dart' as THEME;

class SignUpController extends GetxController {
  SignupModels user;

  Color falseColor = THEME.GRAY_199;
  Color trueColor = THEME.MAIN;

  var models = List<dynamic>().obs;
  RxBool isAllChecked = false.obs;

  var checkduplicatedName = false.obs;
  var error = ''.obs;
  var checkName = false.obs;
  var nickname = '';

  var breadStyle = List<dynamic>().obs;
  var isCheckedBreadStyle = false.obs;

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
      isAllChecked.value = false;
      isAllChecked.refresh();
    } else
      isAllcheck();
  }

  void clickAllCheck() {
    isAllChecked.value = !isAllChecked.value;
    models.value.forEach((element) {
      element.check = isAllChecked.value;
    });

    if (isAllChecked.value) {
      user.setTermsTypeIds.addAll(models.value.map((e) => e.typeId));
    } else {
      models.value.forEach((element) {
        user.setTermsTypeIds.removeAll(models.value.map((e) => e.typeId));
      });
    }

    isAllChecked.refresh();
    models.refresh();
  }

  void isAllcheck() {
    List list = [];
    models.value.forEach((element) => list.add(element.check));
    if (!list.contains(false)) isAllChecked.value = true;
    isAllChecked.refresh();
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
    checkduplicatedName.value = false;
    checkName.value = false;
    if (nickName == '')
      return;
    else {
      var products = await NickNameService.duplicatedNickName(nickName);
      if (products != null) {
        if (products) {
          error.value = '이미 사용중인 별명이예요';
          checkduplicatedName.value = true;
          checkName.value = false;
        } else {
          error.value = '';
          checkduplicatedName.value = false;
          checkName.value = true;
          nickname = nickName;
        }
      }
    }
    checkName.refresh();
    checkduplicatedName.refresh();
    error.refresh();
  }

  void setNickName() {
    user.nickName = nickname;
  }

  void getBreadStyle() async {
    var products = await BreadStyleService.getBreadStyle();
    if (products != null) {
      breadStyle.value = products;
    }
  }

  void setBreadStyle(index) {
    breadStyle.value.forEach((e) {
      e.check = false;
    });
    breadStyle.value[index].check = true;
    isCheckedBreadStyle.value = true;

    user.breadStyleId = breadStyle.value[index].id;

    isCheckedBreadStyle.refresh();
    breadStyle.refresh();
  }

  void signUp() async {
    if (await SignUpService.signUp(user)) {
      Get.offAllNamed('/dashboard');
      UserStorage().setLoggedIn(true);
    }
  }
}
