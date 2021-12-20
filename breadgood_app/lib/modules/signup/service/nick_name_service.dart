
import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as PATH;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as REST_API;

class NickNameService{
  static Future<bool> duplicatedNickName(nickName) async{
    final url=Uri.parse("${PATH.REST_API_URL}/user/duplicate/nickName/${nickName}");
    Map<String,String> headers=await REST_API.headers();
    final response=await http.post(url,headers:headers);
    if(response.statusCode==200){
      return utf8.decode(response.bodyBytes).toLowerCase()=='true';
    }else{
      return null;
    }
  }
}