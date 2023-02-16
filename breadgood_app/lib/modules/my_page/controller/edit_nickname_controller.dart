import 'dart:core';
import 'dart:async';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/modules/signup/service/nick_name_service.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:breadgood_app/constant/api_path.dart' as api_path;

class EditNickNameController extends GetxController {
  Tokens _token = new Tokens();

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
    String accessToken = await _token.getAccessToken();
    final response = await http.patch(
        Uri.parse(
            'https://${api_path.restApiUrl}/user/me/nickName/${newNickname}'),
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
