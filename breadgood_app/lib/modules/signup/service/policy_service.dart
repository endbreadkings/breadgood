import 'dart:convert';

import 'package:breadgood_app/modules/signup/model/policy_models.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;

class PolicyService{

  static Future<List<PolicyModels>> fetchPolicy() async{
    final url=Uri.parse("${api_path.restApiUrl}/termsType/list");
    Map<String,String> headers=await rest_api.headers();
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