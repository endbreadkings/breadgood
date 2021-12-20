import 'dart:convert';

import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:http/http.dart' as http;

final String CONTENT_TYPE="application/json";
final String ACCEPT="application/json";
final String CHARSET="utf-8";

Tokens token=new Tokens();


Future<Map<String,String>> headers() async{

  String accessToken= await token.getAccessToken();

  final Map<String,String> default_header={
    'Content-Type':'application/json',
    'Accept':'application/json',
    'Authorization':'Bearer '+accessToken
  };

  return default_header;

}

Future<Map<String,String>> headers_post() async{

  String accessToken= await token.getAccessToken();

  final Map<String,String> default_header={
    'Accept':'application/json',
    'Authorization':'Bearer '+accessToken
  };

  return default_header;
}

