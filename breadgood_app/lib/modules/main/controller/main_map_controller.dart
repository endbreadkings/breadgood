import 'dart:async';
import 'dart:convert';

import 'package:breadgood_app/modules/main/model/main_map_models.dart';
import 'package:breadgood_app/modules/main/services/main_map_service.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
// import 'package:geolocator/geolocator.dart';
import 'package:get/get.dart';
import 'package:naver_map_plugin/naver_map_plugin.dart';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;

class MainMapController extends GetxController {
  Completer<NaverMapController> naverController = Completer();
  List<Marker> markers = [];
  List bakerys=[];

  OverlayImage _myIcon;
//127.1114893,37.3614463
  LatLng latLng;

  List addr;

  int userId;

  CarouselController buttonCarouselController = CarouselController();


  Future<dynamic> locationDenied() async{
    bool serviceEnabled;
    // LocationPermission permission;

    // serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      // Location services are not enabled don't continue
      // accessing the position and request users of the
      // App to enable the location services.
      return Future.error('Location services are disabled.');
    }

    // permission = await Geolocator.checkPermission();
    // if (permission == LocationPermission.denied) {
    //   permission = await Geolocator.requestPermission();
    //   if (permission == LocationPermission.denied) {
        // Permissions are denied, next time you could try
        // requesting permissions again (this is also where
        // Android's shouldShowRequestPermissionRationale
        // returned true. According to Android guidelines
        // your App should show an explanatory UI now.
        // return Future.error('Location permissions are denied');
      // }
    }

    // if (permission == LocationPermission.deniedForever) {
    //   Permissions are denied forever, handle appropriately.
      // return Future.error(
      //     'Location permissions are permanently denied, we cannot request permissions.');
    // }

    // return true;
  // }

  Future<dynamic> getCurrentLatitudeLongtitude() async {

    // var position = await Geolocator.getCurrentPosition();
    // latLng=LatLng(position.latitude,position.longitude);

    // return [position.latitude,position.longitude];
  }

  Future<List> bakeryList(city,district) async {
    final url = Uri.parse("${api_path.restApiUrl}/bakery/search");
    Map<String,String> headers = await rest_api.headers();
    // var body=json.encode({"city": city, "district": district, "bakeryCategory1": "1"});
    var body=json.encode({"city": city, "district": district, "bakeryCategory": null});
    if(city=="서울특별시"){
      var response=await http.post(url, headers: headers,
          body: body);
      if(response.statusCode==200){
          // bakerys=jsonDecode(utf8.decode(response.bodyBytes));
          // print(bakerys);
          // return bakerys;
      }
    }else{
      Future.error('Not Serviced Region');
      return [];
    }
  }

  Future<List> fetchAddress(latitude,longitude) async{
    // addr=await MainMapService.getCurrentPosition(latitude, longitude);
    // return addr;
  }

  //api/v1/user/me

  Future<dynamic> getUserId() async{
    var response=await MainMapService.fetchUserId();
    return response;
  }


}
