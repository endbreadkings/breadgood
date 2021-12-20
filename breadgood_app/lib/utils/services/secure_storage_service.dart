import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

// Create storage
final storage = new FlutterSecureStorage();

class Tokens {
  String user_info = "";

  getUserInfo() async {
    user_info = await storage.read(key: "login");
    return user_info;
  }

  Future<String> getAccessToken() async{
    await getUserInfo();
    return user_info.split(' ')[1].trim();
  }

  setUserInfo(accessToken, refreshToken) async {
    await storage.write(
        key: "login",
        value: "accessToken " +
            accessToken.toString() +
            " " +
            "refreshToken " +
            refreshToken.toString());

    return getUserInfo()!=null;
  }

  deleteUserInfo() async{
    await storage.delete(key: "login");
  }
}
