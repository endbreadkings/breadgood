
import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;

class NickNameService{
  static Future<bool> duplicatedNickName(nickName) async{
    final url=Uri.parse("${api_path.restApiUrl}/user/duplicate/nickName/${nickName}");
    Map<String,String> headers=await rest_api.headers();
    final response=await http.post(url,headers:headers);
    if(response.statusCode==200){
      return utf8.decode(response.bodyBytes).toLowerCase()=='true';
    }else{
      return null;
    }
  }
}