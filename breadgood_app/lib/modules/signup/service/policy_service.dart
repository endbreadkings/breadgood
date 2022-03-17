import 'dart:convert';

import 'package:breadgood_app/modules/signup/model/policy_models.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as PATH;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as REST_API;

class PolicyService{

  static Future<List<PolicyModels>> fetchPolicy() async{
    final url=Uri.parse("${PATH.REST_API_URL}/termsType/list");
    Map<String,String> headers=await REST_API.headers();
    final response=await http.get(url,headers:headers);
    if(response.statusCode==200){
      var jsonString=utf8.decode(response.bodyBytes);
      return policyFromJson(jsonString);
    }else{
      //show error
      return null;
    }
  }

}