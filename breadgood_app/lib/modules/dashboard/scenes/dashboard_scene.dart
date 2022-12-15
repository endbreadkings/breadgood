import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:breadgood_app/modules/further_info/further_info.dart';
import 'package:breadgood_app/modules/main/scene/main_scene.dart';
import 'package:breadgood_app/modules/my_page/scene/my_info_edit_scene.dart';
import 'package:breadgood_app/modules/register_bakery/scene/search_bakery_scene.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class DashboardScene extends GetView<DashboardController> {
  DashboardScene({Key key}) : super(key: key);

  final _children = [
    "/main",
    "/register_bakery/search_bakery_page",
    "/my_page/my_info_edit",
    "/further_info/further_info"
  ];
  final List<String> iconLabel = ["홈", "최애등록", "my빵긋", "더보기"];
  final List<String> iconPath = [
    "asset/images/icon/bottomNavigation/home_off.svg",
    "asset/images/icon/bottomNavigation/add_off.svg",
    "asset/images/icon/bottomNavigation/my_off.svg",
    "asset/images/icon/bottomNavigation/more_off.svg",
  ];
  final List<String> iconSelectedPath = [
    "asset/images/icon/bottomNavigation/home_on.svg",
    "asset/images/icon/bottomNavigation/add_on.svg",
    "asset/images/icon/bottomNavigation/my_on.svg",
    "asset/images/icon/bottomNavigation/more_on.svg",
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Obx(() {
        switch (RouteName.values[controller.currentIndex.value]) {
          case RouteName.Home:
            return MainScene();
            break;
          case RouteName.Add:
            return SearchBakeryScene();
            break;
          case RouteName.My:
            return MyInfoEditScene();
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
              selectedLabelStyle:
                  TextStyle(fontSize: 10, fontFamily: 'NanumSquareRoundB'),
              unselectedLabelStyle:
                  TextStyle(fontSize: 10, fontFamily: 'NanumSquareRoundB'),
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
                    activeIcon: Padding(
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
              ]),
        ),
      ),
    );
  }
}
