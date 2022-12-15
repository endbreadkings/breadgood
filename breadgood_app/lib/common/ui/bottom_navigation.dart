import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class BottomNavigation extends StatefulWidget {
  @override
  _BottomNavigationState createState() => _BottomNavigationState();
}

class _BottomNavigationState extends State<BottomNavigation> {
  int _selectedIndex = 0;

  final _children = [
    "/main",
    "/register_bakery/search_bakery_page",
    "/my_page/my_info_edit",
    "/further_info/further_info"
  ];

  void _onTap(int index) {
    setState(() {
      _selectedIndex = index;
      Get.offAndToNamed(_children[index]);
    });
  }

  @override
  Widget build(BuildContext context) {
    List<String> iconLabel = ["홈", "빵집등록", "my빵긋", "더보기"];
    List<String> iconPath = [
      "asset/images/icon/bottomNavigation/home_off.svg",
      "asset/images/icon/bottomNavigation/add_off.svg",
      "asset/images/icon/bottomNavigation/my_off.svg",
      "asset/images/icon/bottomNavigation/more_off.svg",
    ];
    return SizedBox(
        height: 96,
        child: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          backgroundColor: Colors.white,
          selectedFontSize: 14,
          unselectedFontSize: 14,
          currentIndex: _selectedIndex,
          onTap: _onTap,
          selectedIconTheme:
              IconThemeData(color: Color.fromRGBO(69, 121, 255, 1), size: 14),
          unselectedLabelStyle:
              TextStyle(color: Color.fromRGBO(136, 136, 136, 1)),
          selectedLabelStyle:
              TextStyle(color: Color.fromRGBO(69, 121, 255, 1), fontSize: 14),
          items: [
            for (var idx = 0; idx < _children.length; idx++)
              BottomNavigationBarItem(
                  label: iconLabel[idx],
                  icon: Container(
                      height: 32,
                      width: 32,
                      child: SvgPicture.asset(
                        iconPath[idx],
                        fit: BoxFit.scaleDown,
                      ))),
          ],
        ));
  }
}
