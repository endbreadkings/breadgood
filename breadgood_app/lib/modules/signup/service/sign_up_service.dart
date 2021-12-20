
import 'dart:convert';

import 'package:breadgood_app/modules/signup/model/signup_models.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as PATH;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as REST_API;

class SignUpService{

  static Future<bool> signUp(SignupModels user) async{
    final url=Uri.parse("${PATH.REST_API_URL}/user/social/signup");
    Map<String,String> headers=await REST_API.headers();
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