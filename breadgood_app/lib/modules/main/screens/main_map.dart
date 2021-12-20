import 'dart:async';

import 'package:breadgood_app/modules/main/controller/main_map_controller.dart';
import 'package:breadgood_app/modules/main/model/main_map_models.dart';
import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:naver_map_plugin/naver_map_plugin.dart';
// import 'package:geolocator/geolocator.dart';
import 'package:breadgood_app/config/themes/light_theme.dart' as THEME;

class BaseMapPage extends StatefulWidget {
  @override
  _BaseMapPage createState() => _BaseMapPage();
}

class _BaseMapPage extends State<BaseMapPage> {
  static const MODE_ADD = 0xF1;
  int _currentMode = MODE_ADD;

  Completer<NaverMapController> _controller = Completer();
  List<Marker> _markers = [];

  LatLng _latLng;

  OverlayImage _myIcon;

  var controller = Get.put(MainMapController());
  MainMapController c_find = Get.find();

  List data = [];

  LatLng latLng = LatLng(37.555298, 126.855092);

  //커스텀 아이콘

  CarouselController buttonCarouselController = CarouselController();

  Timer _debounce;

  //현재 위치 좌표

  Future<OverlayImage> markImage(String type) {
    switch (type) {
      case 'with_bread':
        return OverlayImage.fromAssetImage(
            assetName: 'asset/images/icon/map/with_bread.png',
            context: context);
      case 'focus_bread':
        return OverlayImage.fromAssetImage(
            assetName: 'asset/images/icon/map/focus_bread.png',
            context: context);
      default:
        return OverlayImage.fromAssetImage(
            assetName: 'asset/images/icon/map/with_bread.png',
            context: context);
    }
  }

  @override
  void initState() {
    final controller = Get.put(MainMapController());
    //latLng
    if (controller.bakerys.isNotEmpty) {
      controller.bakerys.forEach((element) {
        setState(() {
          data.add(MapModels.fromJson(element));
        });
      });
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (_markers.length > 0) {
          setState(() {
            _markers.removeRange(0, _markers.length - 1);
          });
        }
        for (int i = 0; i < data.length; i++) {
          OverlayImage.fromAssetImage(
                  // assetName: data[i].makerImgUrl,
                  assetName: "asset/images/icon/map/with_bread.png",
                  context: context)
              .then((image) {
            setState(() {
              _markers.add(Marker(
                  markerId: data[i].id.toString(),
                  position: LatLng(data[i].mapX, data[i].mapY),
                  captionColor: Colors.indigo,
                  captionTextSize: 20.0,
                  alpha: 0.8,
                  icon: image,
                  anchor: AnchorPoint(0.5, 1),
                  width: 35,
                  height: 40,
                  onMarkerTab: _onMarkerTap));
            });
          });
        }
      });
    }

    super.initState();
  }

  void widgetBindingCarousel() {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (_markers.length > 0) {
        setState(() {
          _markers.removeRange(0, _markers.length - 1);
        });
      }
      for (int i = 0; i < data.length; i++) {
        OverlayImage.fromAssetImage(
                // assetName: data[i].makerImgUrl,
                assetName: "asset/images/icon/map/with_bread.png",
                context: context)
            .then((image) {
          setState(() {
            _markers.add(Marker(
                markerId: data[i].id.toString(),
                position: LatLng(data[i].mapX, data[i].mapY),
                captionColor: Colors.indigo,
                captionTextSize: 20.0,
                alpha: 0.8,
                icon: image,
                anchor: AnchorPoint(0.5, 1),
                width: 35,
                height: 40,
                onMarkerTab: _onMarkerTap));
          });
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: DefaultAppBar(),
        body: Stack(children: <Widget>[
          // _Buttons(),
          _naverMap(),
          data != []
              ? Align(
                  alignment: FractionalOffset.bottomCenter,
                  child: Padding(
                      padding: EdgeInsets.only(bottom: 10.0),
                      child: CarouselSlider(
                          options: CarouselOptions(
                              height: 163.0,
                              initialPage: 0,
                              enableInfiniteScroll: false,
                              onPageChanged: ((index, reason) {
                                double latitude = data[index].mapX;
                                double longitude = data[index].mapY;
                                setState(() {
                                  latLng = LatLng(latitude, longitude);
                                });
                              })),
                          carouselController: buttonCarouselController,
                          items: data.map((item) {
                            return Builder(
                              builder: (BuildContext context) {
                                return Container(
                                    width:
                                        MediaQuery.of(context).size.width + 120,
                                    margin:
                                        EdgeInsets.symmetric(horizontal: 10.0),
                                    decoration:
                                        BoxDecoration(color: Colors.white),
                                    child: GestureDetector(
                                      child: Container(
                                          margin: EdgeInsets.all(10),
                                          child: Column(
                                            children: <Widget>[
                                              Row(
                                                children: <Widget>[
                                                  Container(
                                                      margin: EdgeInsets.only(
                                                          right: 5, bottom: 8),
                                                      child: SizedBox(
                                                          width: 30,
                                                          height: 30,
                                                          child: Image.network(item
                                                              .profileImgUrl))),
                                                  Text(
                                                      item.nickName + ' 님 최애빵집')
                                                ],
                                              ),
                                              Row(
                                                children: <Widget>[
                                                  Container(
                                                      margin: EdgeInsets.only(
                                                          right: 10),
                                                      child: Text(
                                                        item.title,
                                                        style: TextStyle(
                                                            fontSize: 25,
                                                            fontWeight:
                                                                FontWeight
                                                                    .bold),
                                                      )),
                                                  Text(item.categoryTitle,
                                                      style: TextStyle(
                                                          color: item.categoryTitle ==
                                                                  '빵에집중'
                                                              ? Color.fromRGBO(
                                                                  249,
                                                                  171,
                                                                  40,
                                                                  1)
                                                              : item.categoryTitle
                                                                      .contains(
                                                                          '음료')
                                                                  ? Color
                                                                      .fromRGBO(
                                                                          69,
                                                                          121,
                                                                          255,
                                                                          1)
                                                                  : THEME
                                                                      .GRAY_177,
                                                          fontWeight:
                                                              FontWeight.bold))
                                                ],
                                              ),
                                              Row(children: [
                                                Container(
                                                    margin: EdgeInsets.only(
                                                        bottom: 8),
                                                    child: Text(
                                                        item.roadAddress,
                                                        style: TextStyle(
                                                            color:
                                                                Color.fromRGBO(
                                                                    154,
                                                                    165,
                                                                    165,
                                                                    2))))
                                              ]),
                                              Row(children: <Widget>[
                                                for (var item
                                                    in item.signatureMenus)

                                                  Container(
                                                    margin:EdgeInsets.only(right:5,bottom:5),
                                                    decoration: new BoxDecoration(
                                                        color:Color.fromRGBO(
                                                        237, 242, 255, 1),
                                                        borderRadius: new BorderRadius.circular(5.0)
                                                    ),
                                                    child:Row(children: [
                                                      Text(" # ",style:TextStyle(color:Color.fromRGBO(
                                                          154,
                                                          165,
                                                          165,
                                                          2))),
                                                      Text(item+" ",
                                                        style: TextStyle(
                                                            color: Color.fromRGBO(
                                                                102, 102, 119, 1),
                                                            )
                                                    )],)
                                                  )
                                              ]

                                                  // <Widget>[
                                                  //   item.signatureMenus.map((e){
                                                  //
                                                  //   Text(item.signatureMenus[0])
                                                  //   })
                                                  // ]
                                                  ),
                                              Row(
                                                children: [
                                                  Container(
                                                      child: Text(item.content,
                                                          style: TextStyle(
                                                              background: Paint()
                                                                ..color = Color
                                                                    .fromRGBO(
                                                                        246,
                                                                        246,
                                                                        246,
                                                                        1))))
                                                ],
                                              )
                                            ],
                                          )),
                                      onTap: () async {
                                        var id = await controller.getUserId();
                                        //, arguments: 'Giyeon'
                                        Get.toNamed('/details', arguments: {
                                          'userId': id,
                                          'bakeryId': item.id
                                        });
                                      },
                                    ));
                              },
                            );
                          }).toList()) //Your widget here,
                      ),
                )
              : Align(
                  alignment: FractionalOffset.bottomCenter,
                  child: Padding(
                      padding: EdgeInsets.only(bottom: 10.0),
                      child: CarouselSlider(
                          options: CarouselOptions(
                              height: 163.0,
                              initialPage: 0,
                              enableInfiniteScroll: false,
                              onPageChanged: ((index, reason) {
                                double latitude = data[index].mapX;
                                double longitude = data[index].mapY;
                                setState(() {
                                  latLng = LatLng(latitude, longitude);
                                });
                              })),
                          carouselController: buttonCarouselController,
                          items: data.map((item) {
                            return Builder(builder: (BuildContext context) {
                              return Container(
                                  width:
                                      MediaQuery.of(context).size.width + 120,
                                  margin:
                                      EdgeInsets.symmetric(horizontal: 10.0),
                                  decoration:
                                      BoxDecoration(color: Colors.white),
                                  child: Container(child: Text('test')));
                            });
                          }).toList()) //Your widget here,
                      ),
                )
        ]),
        bottomNavigationBar: BottomNavigation(),
      ),
    );
  }

  _naverMap() {
    return Positioned(
        child: Stack(
      children: <Widget>[
        NaverMap(
          onMapCreated: _onMapCreated,
          // onMapTap: _onMapTap,
          // onMapLongTap:_onMapTap,
          initialCameraPosition: CameraPosition(
            target: latLng,
            zoom: 17,
          ),
          markers: _markers,
          onCameraChange: _onCameraChange,
          onCameraIdle: _onCameraIdle,
          tiltGestureEnable: false,
          locationButtonEnable: true,
          initLocationTrackingMode: LocationTrackingMode.Follow,
        ),
      ],
    ));
  }

  _Buttons() {
    return Positioned(
        top: 100,
        child: Row(
          children: [
            RaisedButton(
              child: Text("카카오 로그인"),
              shape: RoundedRectangleBorder(
                borderRadius: new BorderRadius.circular(5.0),
              ),
            )
          ],
        ));
  }

  // ================== method ==========================

  void _onCameraChange(
      LatLng latLng, CameraChangeReason reason, bool isAnimated) {
    if (_debounce?.isActive ?? false) _debounce.cancel();
    _debounce = Timer(const Duration(milliseconds: 800), () async {
      List addr =
          await controller.fetchAddress(latLng.latitude, latLng.longitude);
      if (data.length > 0) {
        setState(() {
          data.removeRange(0, data.length - 1);
        });
      }
      controller.bakeryList(addr[0], addr[1]).then((list) {
        setState(() {
          data = [];
          _markers = [];
        });
        if (list != []) {
          list.forEach((element) {
            setState(() {
              data.add(MapModels.fromJson(element));
            });

            var dd = MapModels.fromJson(element);
            print(dd.toString());
          });
        }
        widgetBindingCarousel();
      });
    });
  }

  void _onCameraIdle() {
    print('카메라 움직임 멈춤');
  }

  void _onMapCreated(NaverMapController controller) {
    _controller.complete(controller);
  }

  List<Widget> signatory_bread(List<String> bread) {
    List<Widget> list = [];
    bread.map((item) => {list.add(Container(child: Text(item)))});
    return list;
  }

  void _onMapTap(LatLng latLng) {
    print('onMapTap latLang : ${latLng}');
    if (_currentMode == MODE_ADD) {
      if (_currentMode == MODE_ADD) {
        _markers.add(Marker(
          markerId: DateTime.now().toIso8601String(),
          position: latLng,
          infoWindow: '테스트',
          onMarkerTab: _onMarkerTap,
        ));
        setState(() {});
      }
    }
  }

  void _onMarkerTap(Marker marker, Map<String, int> iconSize) {
    int id = int.parse(marker.markerId);
    buttonCarouselController.animateToPage(id - 1,
        duration: Duration(milliseconds: 300), curve: Curves.linear);

    setState(() {
      _markers.forEach((element) {
        element.width = 35;
        element.height = 40;
      });
      _markers[id - 1].width = 40;
      _markers[id - 1].height = 45;
    });
  }
}
