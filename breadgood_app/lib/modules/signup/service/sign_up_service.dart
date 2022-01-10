
import 'dart:convert';

import 'package:breadgood_app/modules/signup/model/signup_models.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;

class SignUpService{

  static Future<bool> signUp(SignupModels user) async{
    final url=Uri.parse("${api_path.restApiUrl}/user/social/signup");
    Map<String,String> headers=await rest_api.headers();
    var object=signUpFromJsonToJson(user);
    final response=await http.post(url,headers:headers,body:object);
    if(response.statusCode==200){
      return true;
    }else{
      //show error
      return false;
    }
  }
}