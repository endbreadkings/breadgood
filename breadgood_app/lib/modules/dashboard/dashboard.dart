import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:breadgood_app/modules/further_info/further_info.dart';
import 'package:breadgood_app/modules/main/screens/main_list.dart';
import 'package:breadgood_app/modules/my_page/screens/my_info_edit.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class Dashboard extends GetView<DashboardController> {
  Dashboard({Key key}) : super(key: key);

  //움직일 페이지 설정
  final _children = [
    "/main",
    "/register_bakery/search_bakery_page",
    "/my_page/my_info_edit",
    "/further_info/further_info"
  ];
  List<String> iconLabel = ["홈", "최애등록", "my빵긋", "더보기"];
  List<String> iconPath =
  ["asset/images/icon/bottomNavigation/home_off.svg",
    "asset/images/icon/bottomNavigation/add_off.svg",
    "asset/images/icon/bottomNavigation/my_off.svg",
    "asset/images/icon/bottomNavigation/more_off.svg",];
  List<String> iconSelectedPath =
  ["asset/images/icon/bottomNavigation/home_on.svg",
    "asset/images/icon/bottomNavigation/add_off.svg",
    "asset/images/icon/bottomNavigation/my_on.svg",
    "asset/images/icon/bottomNavigation/more_on.svg",];

  @override
  Widget build(BuildContext context) {

    return Scaffold(
        body: Obx(() {
        switch(RouteName.values[controller.currentIndex.value]) {
          case RouteName.Home:
            return MainList();
            break;
          case RouteName.Add:
            return SearchBakeryPage();
            break;
          case RouteName.My:
            return MyInfoEditPage();
            break;
          case RouteName.More:
            return FurtherInfo();
            break;
        }
        return Container();
      }),
      bottomNavigationBar: SizedBox(
        height: 97,
        child: Obx(
            () => BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
      currentIndex: controller.currentIndex.value,
      showSelectedLabels: true,
      selectedItemColor: Color(0xFF4579FF),
                selectedLabelStyle: TextStyle(fontSize: 10, fontFamily: 'NanumSquareRoundB'),
                unselectedLabelStyle: TextStyle(fontSize: 10, fontFamily: 'NanumSquareRoundB'),
                onTap: controller.changePageIndex,
      items: [
        for (var idx = 0; idx < _children.length; idx++)
        BottomNavigationBarItem(
            icon: Padding(
                padding: EdgeInsets.fromLTRB(0, 8, 0, 2),
                child: Container(
                    height: 32,
                    width: 32,
                    child: SvgPicture.asset(
                      iconPath[idx],
                      fit: BoxFit.scaleDown,
                    ))),
    activeIcon:
      Padding(
          padding: EdgeInsets.fromLTRB(0, 8, 0, 2),
          child: Container(
              height: 32,
              width: 32,
              child: SvgPicture.asset(
                iconSelectedPath[idx],
                fit: BoxFit.scaleDown,
              ))),
            label: iconLabel[idx],
        ),
      ]),),),
    );
  }
}