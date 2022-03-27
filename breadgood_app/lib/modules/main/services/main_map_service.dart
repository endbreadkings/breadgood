
import 'dart:convert';

import 'package:breadgood_app/modules/main/model/current_position_models.dart';
import 'package:breadgood_app/modules/main/model/current_positions/area.dart';
import 'package:breadgood_app/modules/main/model/current_positions/region.dart';
import 'package:breadgood_app/modules/main/model/user_models.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;

class MainMapService{
  static Future<List> getCurrentPosition(latitude,longitude) async{
    final url=Uri.parse("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=${longitude},${latitude}&sourcecrs=epsg:4326&orders=addr,roadaddr&output=json");
    Map<String,String> headers={
      "X-NCP-APIGW-API-KEY-ID":"2d930kvu3a",
      "X-NCP-APIGW-API-KEY":"wbXvfONWVMSf6BO5CF4E0P7r2ftJSqG8yxJ2CSF8",
      "Accept":"Accept: application/json"
    };
    final response=await http.get(url,headers:headers);
    var responseJson=jsonDecode(utf8.decode(response.bodyBytes));
    var region=CurrentPositionModels.fromJson(responseJson).results[0].region;
    var city=Area.fromJson(Region.fromJson(region).area1).name;
    var district=Area.fromJson(Region.fromJson(region).area2).name;
    return [city,district];

  }

  static Future<dynamic> fetchUserId() async{

    final url=Uri.parse("${api_path.restApiUrl}/user/me");
    Map<String,String> headers=await rest_api.headers();
    final response=await http.get(url,headers: headers);
    var responseJson=jsonDecode(utf8.decode(response.bodyBytes));
    return UserModels.fromJson(responseJson).id;
  }
}