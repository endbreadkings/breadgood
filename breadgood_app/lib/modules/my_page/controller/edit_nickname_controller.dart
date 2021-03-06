import 'dart:core';
import 'dart:async';
import 'package:breadgood_app/modules/register_bakery/screens/celebrate_register.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/modules/signup/service/nick_name_service.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';

class EditNickNameController extends GetxController {
  Tokens _token = new Tokens();

  String errorMessage = '';

  validateNickName(nickName) async {
    if (nickName.isEmpty) {
      errorMessage = '별명을 입력해주세요';
      return;
    }

    if (nickName.length > 8) {
      errorMessage = '최대 7글자를 넘어갈 수 없습니다';
      return;
    }

    var products = await NickNameService.duplicatedNickName(nickName);
    if (products == null) {
      errorMessage = '별명을 입력해주세요';
      return;
    }

    if (products) {
      errorMessage = '이미 사용중인 별명이예요';
    } else {
      errorMessage = '';
    }
  }

  Future<http.Response> updateNickname(String newNickname) async {
    String accessToken = await _token.getAccessToken();
    final response = await http.patch(
        Uri.parse(
            'https://api.breadgood.com/api/v1/user/me/nickName/${newNickname}'),
        headers: {
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + accessToken
        },
        body: <String, String>{
          'nickName': 'nickName',
        });

    return response;
  }
}
