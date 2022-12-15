import 'dart:core';
import 'dart:async';
import 'package:breadgood_app/services/api/api_path.dart';
import 'package:breadgood_app/services/api/api_service.dart';
import 'package:http/http.dart' as http;
import 'package:get/get.dart';
import 'package:breadgood_app/modules/signup/service/nick_name_service.dart';
import 'package:breadgood_app/storages/user_storage.dart';

class EditNickNameController extends GetxController {
  UserStorage userStorage = new UserStorage();

  String errorMessage = '';

  validateNickName(nickName) async {
    if (nickName.isEmpty) {
      errorMessage = '별명을 입력해주세요';
      return;
    }

    if (nickName.length >= 8) {
      errorMessage = '최대 7글자를 넘어갈 수 없습니다';
      return;
    } else {
      errorMessage = '';
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
    String accessToken = await userStorage.getAccessToken();
    final response = await http.patch(
        Uri.parse('$restApiUrl/user/me/nickName/$newNickname'),
        headers: {
          'Accept': accept,
          'Authorization': bearer + accessToken
        },
        body: <String, String>{
          'nickName': 'nickName',
        });

    return response;
  }
}
